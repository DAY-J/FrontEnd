package com.dayj.dayj.lounge.presentation.write

import android.content.Context
import android.net.Uri
import android.text.format.DateUtils
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dayj.dayj.lounge.domain.LoungeTagEnum
import com.dayj.dayj.lounge.domain.repository.LoungeRepository
import com.dayj.dayj.util.DateUtils.dateFormat
import com.dayj.dayj.util.Extensions.asMultipart
import com.dayj.dayj.util.Extensions.extractInteger

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class PostWritingViewModel @Inject constructor(
    private val loungeRepository: LoungeRepository
): ViewModel() {
    private val _title = MutableStateFlow("")
    val title = _title.asStateFlow()

    private val _content = MutableStateFlow("")
    val content = _content.asStateFlow()

    private val _nameHiddenChecked = MutableStateFlow(false)
    val nameHiddenChecked = _nameHiddenChecked.asStateFlow()

    private val _images = MutableStateFlow<List<Uri>>(listOf())
    val images = _images.asStateFlow()

    private val _selectedTag = MutableStateFlow(LoungeTagEnum.HEALTH)
    val selectedTag = _selectedTag.asStateFlow()

    fun updateImages(uri: Uri) {
        images.value.toMutableList().let {
            it.add(uri)
            _images.value = it
        }
    }

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


    /**
     * 게시물 등록하기 [Remote]
     * **/
    fun completeWritePosting(context: Context) {
        viewModelScope.launch {
            loungeRepository.writePosting(
                title = title.value,
                content = content.value,
                tag = selectedTag.value.dto,
                anonymous = nameHiddenChecked.value,
                images = images.value.map {
                    val currentDate = dateFormat.format(Date()).extractInteger()
                    it.asMultipart(name = "images", contentResolver = context.contentResolver)
                }.filterNotNull()
            ).collect {

            }
        }
    }
}