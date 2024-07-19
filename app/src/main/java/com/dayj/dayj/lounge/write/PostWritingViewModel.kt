package com.dayj.dayj.lounge.write

import androidx.lifecycle.ViewModel
import com.dayj.dayj.lounge.LoungeTagEnum
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class PostWritingViewModel @Inject constructor(

): ViewModel() {
    private val _title = MutableStateFlow("")
    val title = _title.asStateFlow()

    private val _content = MutableStateFlow("")
    val content = _content.asStateFlow()

    private val _nameHiddenChecked = MutableStateFlow(false)
    val nameHiddenChecked = _nameHiddenChecked.asStateFlow()

    private val _selectedTag = MutableStateFlow(LoungeTagEnum.HEALTH)
    val selectedTag = _selectedTag.asStateFlow()

    fun updateTitle(updatedTitle: String) {
        _title.value = updatedTitle
    }

    fun updateContent(updatedContent: String) {
        _content.value = updatedContent
    }

    fun updateNameHiddenChecked(nameHidden: Boolean) {
        _nameHiddenChecked.value = nameHidden
    }

    fun updateSelectedTag(tag: LoungeTagEnum) {
        _selectedTag.value = tag
    }

    fun completeWriting(resultListener: (Boolean) -> Unit) {
        resultListener(true)
    }
}