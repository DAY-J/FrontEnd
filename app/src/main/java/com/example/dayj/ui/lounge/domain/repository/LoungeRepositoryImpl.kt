package com.example.dayj.ui.lounge.domain.repository

import com.example.dayj.data.PreferenceManager
import com.example.dayj.datastore.SelfUserAccountDataStore
import com.example.dayj.ui.lounge.data.source.LoungeDataSource
import com.example.dayj.ui.lounge.domain.LoungeTagEnum
import com.example.dayj.ui.lounge.domain.entity.CommentEntity
import com.example.dayj.ui.lounge.domain.entity.LoungePostingEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import okhttp3.MultipartBody
import javax.inject.Inject

class LoungeRepositoryImpl @Inject constructor(
    private val loungeDataSource: LoungeDataSource,
    private val dataStore: SelfUserAccountDataStore
): LoungeRepository {
    override suspend fun writePosting(title: String, content: String, tag: String, anonymous: Boolean, images: List<MultipartBody.Part>): Flow<Boolean> {
        val request = com.example.dayj.ui.lounge.data.model.RequestPostingCreation(
            postTitle = title,
            postContent = content,
            postTag = tag,
            isAnonymous = anonymous
        )
        val userId = dataStore.userInfoFlow.first()?.id;
        return loungeDataSource.writePosting(
            userId = userId ?: -1,
            requestPostingCreation = request,
            images = images
        )
    }

    override suspend fun writeComment(
        postingId: Int,
        comment: String,
        anonymous: Boolean
    ): Flow<CommentEntity?> {
        val userId = dataStore.userInfoFlow.first()?.id ?: -1
        val request = com.example.dayj.ui.lounge.data.model.RequestCommentModel(
            content = comment,
            isAnonymous = anonymous
        )

        return loungeDataSource.writeComment(
            postingId = postingId,
            userId = userId,
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
        val userId = dataStore.userInfoFlow.first()?.id ?: -1

        val request = com.example.dayj.ui.lounge.data.model.RequestCommentModel(
            content = comment,
            isAnonymous = anonymous
        )

        return loungeDataSource.writeChildComment(
            postingId = postingId,
            userId = userId,
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
        val request = com.example.dayj.ui.lounge.data.model.RequestCommentModel(
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
                val childComments = mutableSetOf<com.example.dayj.ui.lounge.data.model.ResponsePosting.CommentModel>()

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
                    commentCount = model.comment.size,
                    isLiked = false
                )
            }
        }
    }

    override suspend fun likePosting(postingId: Int): Flow<Boolean> = loungeDataSource.likePosting(postingId = postingId)

    fun com.example.dayj.ui.lounge.data.model.ResponsePosting.CommentModel.toCommentEntity(): CommentEntity {
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