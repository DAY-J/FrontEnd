package com.dayj.dayj.lounge.lounge

import androidx.lifecycle.ViewModel
import com.dayj.dayj.lounge.LoungeTagEnum
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class LoungeViewModel @Inject constructor(

) : ViewModel() {
    val tags = LoungeTagEnum.entries

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    private val _selectedTag = MutableStateFlow<LoungeTagEnum>(LoungeTagEnum.ALL)
    val selectedTag = _selectedTag.asStateFlow()

    private val _loungeItems = MutableStateFlow<List<LoungeListEntity>>(
        listOf(
            LoungeListEntity(
                id = Random.nextInt(1000),
                title = "테스트 게시물 ${Random.nextInt(1000)}",
                writerId = Random.nextInt(1000),
                writerName = "글쓴이 ${Random.nextInt(1000)}",
                createdDate = "2024-05-10T10:00:00",
                isNameHidden = Random.nextBoolean(),
                tags = listOf(
                    LoungeTagEnum.HEALTH,
                    LoungeTagEnum.POPULAR
                ),
                likeCount = Random.nextInt(0, 30),
                commentCount = Random.nextInt(0, 23)
            ),
            LoungeListEntity(
                id = Random.nextInt(1000),
                title = "테스트 게시물 ${Random.nextInt(1000)}",
                writerId = Random.nextInt(1000),
                writerName = "글쓴이 ${Random.nextInt(1000)}",
                createdDate = "2024-05-10T10:00:00",
                isNameHidden = Random.nextBoolean(),
                tags = listOf(
                    LoungeTagEnum.HEALTH,
                    LoungeTagEnum.POPULAR
                ),
                likeCount = Random.nextInt(0, 30),
                commentCount = Random.nextInt(0, 23)
            ),
            LoungeListEntity(
                id = Random.nextInt(1000),
                title = "테스트 게시물 ${Random.nextInt(1000)}",
                writerId = Random.nextInt(1000),
                writerName = "글쓴이 ${Random.nextInt(1000)}",
                createdDate = "2024-05-10T10:00:00",
                isNameHidden = Random.nextBoolean(),
                tags = listOf(
                    LoungeTagEnum.HEALTH,
                    LoungeTagEnum.POPULAR
                ),
                likeCount = Random.nextInt(0, 30),
                commentCount = Random.nextInt(0, 23)
            ),
            LoungeListEntity(
                id = Random.nextInt(1000),
                title = "테스트 게시물 ${Random.nextInt(1000)}",
                writerName = "글쓴이 ${Random.nextInt(1000)}",
                writerId = Random.nextInt(1000),
                createdDate = "2024-05-10T10:00:00",
                isNameHidden = Random.nextBoolean(),
                tags = listOf(
                    LoungeTagEnum.HEALTH,
                    LoungeTagEnum.POPULAR
                ),
                likeCount = Random.nextInt(0, 30),
                commentCount = Random.nextInt(0, 23)
            ),
            LoungeListEntity(
                id = Random.nextInt(1000),
                title = "테스트 게시물 ${Random.nextInt(1000)}",
                writerId = Random.nextInt(1000),
                writerName = "글쓴이 ${Random.nextInt(1000)}",
                createdDate = "2024-05-10T10:00:00",
                isNameHidden = Random.nextBoolean(),
                tags = listOf(
                    LoungeTagEnum.HEALTH,
                    LoungeTagEnum.POPULAR
                ),
                likeCount = Random.nextInt(0, 30),
                commentCount = Random.nextInt(0, 23)
            ),
            LoungeListEntity(
                id = Random.nextInt(1000),
                title = "테스트 게시물 ${Random.nextInt(1000)}",
                writerId = Random.nextInt(1000),
                writerName = "글쓴이 ${Random.nextInt(1000)}",
                createdDate = "2024-05-11T22:00:00",
                isNameHidden = Random.nextBoolean(),
                tags = listOf(
                    LoungeTagEnum.HEALTH,
                    LoungeTagEnum.POPULAR
                ),
                likeCount = Random.nextInt(0, 30),
                commentCount = Random.nextInt(0, 23)
            ),
            LoungeListEntity(
                id = Random.nextInt(1000),
                title = "테스트 게시물 ${Random.nextInt(1000)}",
                writerId = Random.nextInt(1000),
                writerName = "글쓴이 ${Random.nextInt(1000)}",
                createdDate = "2024-05-18T20:00:00",
                isNameHidden = Random.nextBoolean(),
                tags = listOf(
                    LoungeTagEnum.HEALTH,
                    LoungeTagEnum.POPULAR
                ),
                likeCount = Random.nextInt(0, 30),
                commentCount = Random.nextInt(0, 23)
            ),
        )
    )
    val loungeItems = _loungeItems.asStateFlow()

    private val _filteredLoungeItems = MutableStateFlow<List<LoungeListEntity>>(listOf())
    val filteredLoungeItems = _filteredLoungeItems.asStateFlow()

    fun changeSelectedTag(tag: LoungeTagEnum) {
        _selectedTag.value = tag
    }

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
        if(query.isNotEmpty()) {
            _filteredLoungeItems.value = loungeItems.value
                .toMutableList()
                .filter {
                    if(it.isNameHidden) "익명".contains(query) else it.writerName.contains(query) ||
                    it.title.contains(query)
                        || it.tags.map { it.tagText }.contains(query)
            }
        } else {
            _filteredLoungeItems.value = loungeItems.value
        }

    }

    init {
        _filteredLoungeItems.value = loungeItems.value
    }

}