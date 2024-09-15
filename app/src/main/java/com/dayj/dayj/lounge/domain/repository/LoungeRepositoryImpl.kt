package com.dayj.dayj.lounge.domain.repository

import com.dayj.dayj.data.PreferenceManager
import com.dayj.dayj.lounge.data.model.RequestCommentModel
import com.dayj.dayj.lounge.data.model.RequestPostingCreation
import com.dayj.dayj.lounge.data.model.ResponsePosting
import com.dayj.dayj.lounge.data.source.LoungeDataSource
import com.dayj.dayj.lounge.domain.LoungeTagEnum
import com.dayj.dayj.lounge.domain.entity.CommentEntity
import com.dayj.dayj.lounge.domain.entity.LoungePostingEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import okhttp3.MultipartBody
import javax.inject.Inject

class LoungeRepositoryImpl @Inject constructor(
    private val loungeDataSource: LoungeDataSource,
    private val preferenceManager: PreferenceManager
): LoungeRepository {
    override suspend fun writePosting(title: String, content: String, tag: String, anonymous: Boolean, images: List<MultipartBody.Part>): Flow<Boolean> {
        val request = RequestPostingCreation(
            postTitle = title,
            postContent = content,
            postTag = tag,
            isAnonymous = anonymous
        )
        return loungeDataSource.writePosting(
            userId = preferenceManager.getUserId(),
            requestPostingCreation = request,
            images = images
        )
    }

    override suspend fun writeComment(
        postingId: Int,
        comment: String,
        anonymous: Boolean
    ): Flow<CommentEntity?> {
        val request = RequestCommentModel(
            content = comment,
            isAnonymous = anonymous
        )

        return loungeDataSource.writeComment(
            postingId = postingId,
            userId = preferenceManager.getUserId(),
            requestCommentModel = request
        ).map {
            it?.toCommentEntity()
        }
    }

    override suspend fun writeChildComment(
        postingId: Int,
        comment: String,
        commentId: Int,
        anonymous: Boolean
    ): Flow<CommentEntity?> {
        val request = RequestCommentModel(
            content = comment,
            isAnonymous = anonymous
        )

        return loungeDataSource.writeChildComment(
            postingId = postingId,
            userId = preferenceManager.getUserId(),
            commentId = commentId,
            requestCommentModel = request
        ).map {
            it?.toCommentEntity()
        }
    }

    override suspend fun deleteComment(postingId: Int, commentId: Int): Flow<Boolean> {
        return loungeDataSource.deleteComment(postingId = postingId, commentId = commentId)
    }

    override suspend fun editComment(
        postingId: Int,
        commentId: Int,
        comment: String,
        anonymous: Boolean
    ): Flow<CommentEntity?> {
        val request = RequestCommentModel(
            content = comment,
            isAnonymous = anonymous
        )

        return loungeDataSource.editComment(
            postingId = postingId,
            commentId = commentId,
            requestCommentModel = request
        ).map {
            it?.toCommentEntity()
        }
    }

    override suspend fun getAllPostings(): Flow<List<LoungePostingEntity>> {
        return loungeDataSource.getAllPostings().map { responseBody ->
            responseBody.map { model ->
                val childComments = mutableSetOf<ResponsePosting.CommentModel>()

                LoungePostingEntity(
                    id = model.id,
                    title = model.postTitle,
                    writerId = model.appUserId,
                    content = model.postContent,
                    images = model.postPhoto,
                    isNameHidden = model.isAnonymous,
                    writerName = model.author,
                    createdDate = model.createdAt,
                    tag = LoungeTagEnum.findTagUsingDto(model.postTag),
                    likeCount = model.postLike,
                    comments = model.comment.map { commentModel ->
                        val children = model.comment.filter { it.parentId == commentModel.id }
                        childComments.addAll(children)
                        CommentEntity(
                            id = commentModel.id,
                            parentId = commentModel.parentId,
                            userId = -1,
                            userName = commentModel.author,
                            isNameHidden = commentModel.isAnonymous,
                            createdDate = commentModel.createdAt,
                            comment = commentModel.content,
                            children = children.map { it.toCommentEntity() }
                        )
                    }.filter { comment ->
                        !childComments.map { it.id }.contains(comment.id)
                    },
                    commentCount = model.postView,
                )
            }
        }
    }

    fun ResponsePosting.CommentModel.toCommentEntity(): CommentEntity {
        return CommentEntity(
            id = id,
            parentId = parentId,
            userId = -1,
            userName = author,
            isNameHidden = isAnonymous,
            createdDate = createdAt,
            comment = content,
            children = listOf()
        )
    }
}