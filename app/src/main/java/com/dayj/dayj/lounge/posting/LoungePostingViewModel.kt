package com.dayj.dayj.lounge.posting

import android.util.Log
import androidx.lifecycle.ViewModel
import com.dayj.dayj.DateUtils.dateFormat
import com.dayj.dayj.lounge.LoungeTagEnum
import com.thedeanda.lorem.LoremIpsum
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.Date
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class LoungePostingViewModel @Inject constructor(

): ViewModel(){
    private val lorem = LoremIpsum.getInstance()

    val myUserName = lorem.name
    val myUserId = Random.nextInt(0, 100000)

    private val _postingId = MutableStateFlow(-1)
    val postingId = _postingId.asStateFlow()

    private val _posting = MutableStateFlow<LoungePostingEntity?>(null)
    val posting = _posting.asStateFlow()

    private val _commentWritten = MutableStateFlow("")
    val commentWritten = _commentWritten.asStateFlow()

    private val _nameHiddenChecked = MutableStateFlow(false)
    val nameHiddenChecked = _nameHiddenChecked.asStateFlow()

    fun updatePostingId(id: Int) {
        _postingId.value = id
        updateDummyPosting()
    }

    private fun updateDummyPosting() {
        _posting.value = LoungePostingEntity(
            tags = listOf(LoungeTagEnum.HEALTH),
            id = 123123 ,
            title = lorem.getTitle(2, 6),
            content = lorem.getParagraphs(1, 2),
            images = listOf(
                "https://picsum.photos/id/${Random.nextInt(100, 200)}/300/300",
                "https://picsum.photos/id/${Random.nextInt(100, 200)}/300/300",
                "https://picsum.photos/id/${Random.nextInt(100, 200)}/300/300",
                "https://picsum.photos/id/${Random.nextInt(100, 200)}/300/300",
                "https://picsum.photos/id/${Random.nextInt(100, 200)}/300/300",
            ),
            userName = lorem.name,
            createdDate = "2024-05-22T08:00:00",
            isNameHidden = Random.nextBoolean(),
            likeCount = Random.nextInt(0, 50),
            comments = (0..Random.nextInt(1,6)).map {
                CommentEntity(
                    userId = Random.nextInt(0, 10000),
                    userName = lorem.name,
                    createdDate = "2024-05-22T${Random.nextInt(10, 23)}:${Random.nextInt(10, 55)}:00",
                    isNameHidden = Random.nextBoolean(),
                    comment = lorem.getWords(5, 10)
                )
            },
            liked = false
        )
    }

    fun updateCommentWritten(comment: String) {
        _commentWritten.value = comment
    }

    fun updateNameHiddenChecked(nameHidden: Boolean) {
        _nameHiddenChecked.value = nameHidden
    }

    fun uploadComment() {
        posting.value?.let { updatedPosting ->
            val updatedComments = updatedPosting.comments.toMutableList()
            updatedComments.add(
                CommentEntity(
                    userId = myUserId,
                    userName = if(nameHiddenChecked.value) "익명" else myUserName,
                    isNameHidden = false,
                    createdDate = dateFormat.format(Date().time),
                    comment = commentWritten.value
                )
            )

            _posting.value = updatedPosting.copy(comments = updatedComments)
            _commentWritten.value = ""
        }
    }
}