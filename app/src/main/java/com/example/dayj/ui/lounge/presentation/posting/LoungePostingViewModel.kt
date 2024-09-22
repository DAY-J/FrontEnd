package com.example.dayj.ui.lounge.presentation.posting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dayj.data.PreferenceManager
import com.example.dayj.ui.lounge.domain.entity.LoungePostingEntity
import com.example.dayj.ui.lounge.domain.repository.LoungeRepository
import com.thedeanda.lorem.LoremIpsum
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoungePostingViewModel @Inject constructor(
    private val loungeRepository: LoungeRepository,
    private val preferenceManager: PreferenceManager
): ViewModel(){
    private val lorem = LoremIpsum.getInstance()

    val myUserName = preferenceManager.getUserName()
    val myUserId = preferenceManager.getUserId()

    private val _postingId = MutableStateFlow(-1)
    val postingId = _postingId.asStateFlow()

    private val _posting = MutableStateFlow<LoungePostingEntity?>(null)
    val posting = _posting.asStateFlow()

    private val _commentWritten = MutableStateFlow("")
    val commentWritten = _commentWritten.asStateFlow()

    private val _nameHiddenChecked = MutableStateFlow(false)
    val nameHiddenChecked = _nameHiddenChecked.asStateFlow()

    private val _selectedParentCommentId = MutableStateFlow<Int?>(null)
    val selectedParentCommentId = _selectedParentCommentId.asStateFlow()

    private val _selectedCommentIdForSetting = MutableStateFlow<Int?>(null)
    val selectedCommentIdForSetting = _selectedCommentIdForSetting.asStateFlow()

    fun updateSelectedParentCommentId(commentId: Int) {
        _selectedParentCommentId.value = commentId
    }

    fun updatePosting(posting: LoungePostingEntity?) {
        posting?.let {
            _postingId.value = it.id
            _posting.value = it
        }
    }

    fun updateCommentWritten(comment: String) {
        _commentWritten.value = comment
    }

    fun updateNameHiddenChecked(nameHidden: Boolean) {
        _nameHiddenChecked.value = nameHidden
    }

    fun uploadComment() {
        viewModelScope.launch {
            posting.value?.let { posting ->
                loungeRepository.writeComment(
                    postingId = posting.id,
                    comment = commentWritten.value,
                    anonymous = nameHiddenChecked.value
                ).collect { uploaded ->
                    uploaded?.let {
                        val updatedComments = posting.comments.toMutableList()
                        updatedComments.add(it)
                        _posting.value = posting.copy(comments = updatedComments)
                        _commentWritten.value = ""
                    }
                }
            }
        }
    }

    fun uploadChildComment() {
        viewModelScope.launch {
            selectedParentCommentId.value?.let { parentCommentId ->
                posting.value?.let { posting ->
                    loungeRepository.writeChildComment(
                        postingId = posting.id,
                        comment = commentWritten.value,
                        commentId = parentCommentId,
                        anonymous = nameHiddenChecked.value
                    ).collect { uploaded ->
                        uploaded?.let { uploaded ->
                            val updatedComments = posting.comments.toMutableList()
                            updatedComments.find { it.id == parentCommentId }?.let { parent ->
                                val parentIdx = updatedComments.indexOf(parent)
                                val updatedChildren = parent.children.toMutableList()
                                updatedChildren.add(uploaded)
                                updatedComments.set(parentIdx, parent.copy(children = updatedChildren))
                                _posting.value = posting.copy(comments = updatedComments)
                                _commentWritten.value = ""
                                _selectedParentCommentId.value = null
                            }
                        }
                    }
                }
            }
        }
    }

    fun updateSelectedCommentIdForSetting(commentId: Int?) {
        _selectedCommentIdForSetting.value = commentId
    }

    fun editComment() {
        viewModelScope.launch {
            posting.value?.let { posting ->
                selectedCommentIdForSetting.value?.let { commentId ->
                    loungeRepository.editComment(
                        postingId = posting.id,
                        commentId = commentId,
                        comment = commentWritten.value,
                        anonymous = nameHiddenChecked.value
                    ).collect { uploaded ->
                        val updatedComments = posting.comments.toMutableList()
                        val originalComment = updatedComments.find { it.id == commentId }

                        uploaded?.let {
                            if(originalComment != null) {
                                val updateIdx = updatedComments.indexOf(originalComment)
                                updatedComments.set(updateIdx, uploaded)
                                _posting.value = posting.copy(comments = updatedComments)
                            } else {
                                val originalChildComment = updatedComments
                                    .map { it.children }
                                    .flatten()
                                    .find { it.id == commentId }

                                if(originalChildComment != null) {
                                    val parentComment = updatedComments.find { it.id == originalChildComment.parentId }

                                    if(parentComment != null) {
                                        val parentIdx = updatedComments.indexOf(parentComment)
                                        val updatedChildren = parentComment.children.toMutableList()
                                        val childIdx = parentComment.children.indexOf(originalChildComment)
                                        updatedChildren.set(childIdx, uploaded)
                                        updatedComments.set(parentIdx,  parentComment.copy(children = updatedChildren))
                                        _posting.value = posting.copy(comments = updatedComments)
                                    }
                                }
                            }
                        }
                        _commentWritten.value = ""
                        _selectedCommentIdForSetting.value = null
                    }
                }
            }
        }
    }

    fun deleteComment() {
        viewModelScope.launch {
            posting.value?.let { posting ->
                selectedCommentIdForSetting.value?.let { commentId ->
                    loungeRepository.deleteComment(
                        postingId = posting.id,
                        commentId = commentId
                    ).collect {
                        val updatedComments = posting.comments.toMutableList()
                        val originalComment = updatedComments.find { it.id == commentId }
                        if(originalComment != null) {
                            val updateIdx = updatedComments.indexOf(originalComment)
                            updatedComments.removeAt(updateIdx)
                            _posting.value = posting.copy(comments = updatedComments)
                        } else {
                            val originalChildComment = updatedComments
                                .map { it.children }
                                .flatten()
                                .find { it.id == commentId }

                            if(originalChildComment != null) {
                                val parentComment = updatedComments.find { it.id == originalChildComment.parentId }
                                if(parentComment != null) {
                                    val parentIdx = updatedComments.indexOf(parentComment)
                                    val updatedChildren = parentComment.children.toMutableList()
                                    val childIdx = parentComment.children.indexOf(originalChildComment)
                                    updatedChildren.removeAt(childIdx)
                                    updatedComments.set(parentIdx,  parentComment.copy(children = updatedChildren))
                                    _posting.value = posting.copy(comments = updatedComments)
                                }
                            }
                        }

                        _selectedCommentIdForSetting.value = null
                    }
                }
            }
        }
    }

    fun likePosting() {
        viewModelScope.launch {
            posting.value?.let { loungePosting ->
                loungeRepository.likePosting(
                    postingId = loungePosting.id
                ).collect { success ->
                    if(success) {
                        _posting.value = loungePosting.copy(
                            likeCount = loungePosting.likeCount +1,
                            isLiked = true
                        )
                    }
                }

            }
        }
    }
}