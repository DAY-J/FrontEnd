package com.example.dayj.ui.friends.presentation.groups

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dayj.ui.friends.domain.entity.FriendsGroupEntity
import com.example.dayj.ui.friends.domain.repository.FriendsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GroupListViewModel @Inject constructor(
    private val friendsRepository: FriendsRepository
): ViewModel() {
    private val _groupList = MutableStateFlow<List<FriendsGroupEntity>>(listOf())
    val groupList = _groupList.asStateFlow()


    fun addGroup(group: FriendsGroupEntity) {
        groupList.value.toMutableList().also {
            it.add(group)
        }.also {
            _groupList.value = it
        }
    }

    /**
     * 친구 그룹 가져오기 [Remote]
     * **/
    fun fetchAllGroups() {
        viewModelScope.launch {
            friendsRepository.getAllGroups().collect {
                _groupList.value = it
            }
        }
    }

    /**
     * 친구 그룹 만들기 [Remote]
     * **/
    fun createFriendGroup(groupName: String) {
        viewModelScope.launch {
            friendsRepository.createFriendGroup(groupName).collect { success ->
                if(success) {
                    fetchAllGroups()
                }
            }
        }
    }
}