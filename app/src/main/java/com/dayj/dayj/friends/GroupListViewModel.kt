package com.dayj.dayj.friends

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class GroupListViewModel @Inject constructor(

): ViewModel() {
    private val _groupList = MutableStateFlow<List<FriendsGroupEntity>>(listOf())
    val groupList = _groupList.asStateFlow()


    init {
        _groupList.value = mutableListOf(
            FriendsGroupEntity(
                groupId = 1,
                groupName = "그룹 1",
                participantsCount = 7,
                goal = "그룹 1 목표"
            ),
            FriendsGroupEntity(
                groupId = 2,
                groupName = "테스트 그룹 22",
                participantsCount = 10,
                goal = "그룹 2 목표"
            ),
            FriendsGroupEntity(
                groupId = 3,
                groupName = "테스트 그룹 33",
                participantsCount = 12,
                goal = "그룹 3 목표"
            ),
            FriendsGroupEntity(
                groupId = 4,
                groupName = "테스트 그룹 44",
                participantsCount = 5,
                goal = "그룹 4 목표"
            ),
        )
    }
    fun addGroup(group: FriendsGroupEntity) {
        groupList.value.toMutableList().also {
            it.add(group)
        }.also {
            _groupList.value = it
        }
    }
}