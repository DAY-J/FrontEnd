package com.dayj.dayj.lounge.presentation.lounge

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dayj.dayj.lounge.domain.repository.LoungeRepository
import com.dayj.dayj.lounge.domain.LoungeTagEnum
import com.dayj.dayj.lounge.domain.entity.CommentEntity
import com.dayj.dayj.lounge.domain.entity.LoungePostingEntity
import com.thedeanda.lorem.LoremIpsum
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class LoungeViewModel @Inject constructor(
    private val loungeRepository: LoungeRepository
) : ViewModel() {
    private val lorem = LoremIpsum.getInstance()

    val tags = LoungeTagEnum.entries

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    private val _selectedTag = MutableStateFlow<LoungeTagEnum>(LoungeTagEnum.ALL)
    val selectedTag = _selectedTag.asStateFlow()

    private val _postings = MutableStateFlow<List<LoungePostingEntity>>(listOf())
    val postings = _postings.asStateFlow()

    private val _filteredLoungeItems = MutableStateFlow<List<LoungePostingEntity>>(listOf())
    val filteredLoungeItems = _filteredLoungeItems.asStateFlow()

    private val _sort = MutableStateFlow(0)
    val sort = _sort.asStateFlow()


    fun changeSort(sort: Int) {
        _sort.value = sort
        Log.e("====", "${sort}")
        _filteredLoungeItems.value = when (sort) {
            0 -> {
                postings.value.sortedByDescending {
                    it.createdDate
                }
            }
            1 -> {
                postings.value.sortedByDescending {
                    it.likeCount
                }
            }
            else -> {
                postings.value.sortedByDescending {
                    it.commentCount
                }
            }
        }
    }

    fun changeSelectedTag(tag: LoungeTagEnum) {
        _selectedTag.value = tag
        if(tag == LoungeTagEnum.ALL) {
            _filteredLoungeItems.value = postings.value
        } else {
            _filteredLoungeItems.value = postings.value.toMutableList().filter {
                it.tag == tag
            }
        }
    }

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
        if(query.isNotEmpty()) {
            _filteredLoungeItems.value = postings.value
                .toMutableList()
                .filter {
                    (if(it.isNameHidden) "익명".contains(query) else it.writerName.contains(query)) ||
                    it.title.contains(query)
                        || it.tag.tagText.contains(query)
            }
        } else {
            _filteredLoungeItems.value = postings.value
        }
    }


    /**
     * 모든 게시물 가져오기 [Remote]
     * **/
    fun getAllPostings() {
        viewModelScope.launch {
            loungeRepository.getAllPostings().collect { response ->
                var sortedResponse = when (sort.value) {
                    0 -> {
                        response.sortedByDescending {
                            it.createdDate
                        }
                    }
                    1 -> {
                        response.sortedByDescending {
                            it.likeCount
                        }
                    }
                    else -> {
                        response.sortedByDescending {
                            it.commentCount
                        }
                    }
                }
                _postings.value = sortedResponse

                changeSelectedTag(selectedTag.value)
            }
        }
    }

    private fun createDummyComments(): List<CommentEntity> {
        return (0..Random.nextInt(1,6)).map {
            CommentEntity(
                id = Random.nextInt(0, 10000),
                parentId = null,
                userId = Random.nextBoolean().let { if(it) 1 else 0 },
                userName = lorem.name,
                createdDate = "2024-05-22T${Random.nextInt(10, 23)}:${Random.nextInt(10, 55)}:00",
                isNameHidden = Random.nextBoolean(),
                comment = lorem.getWords(5, 10),
                children = listOf()
            )
        }
    }
}