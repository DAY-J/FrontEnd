package com.example.dayj.ui.friends.presentation.groupdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dayj.data.PreferenceManager
import com.example.dayj.ui.friends.domain.entity.FriendsGroupEntity
import com.example.dayj.ui.friends.domain.entity.GoalByParicipantEntity
import com.example.dayj.ui.friends.domain.entity.GroupParticipantEntity
import com.example.dayj.ui.friends.domain.entity.UserEntity
import com.example.dayj.ui.friends.domain.repository.FriendsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GroupDetailViewModel @Inject constructor(
    private val friendsRepository: FriendsRepository,
    private val preferenceManager: PreferenceManager
): ViewModel() {
    private val _groupId = MutableStateFlow<Int>(-1)
    val groupId = _groupId.asStateFlow()

    private val _groupName = MutableStateFlow<String>("")
    val groupName = _groupName.asStateFlow()

    private val _groupGoal = MutableStateFlow<String>("")
    val groupGoal = _groupGoal.asStateFlow()

    private val _participants = MutableStateFlow<List<UserEntity>>(listOf())
    val participants = _participants.asStateFlow()

    private val _achievements = MutableStateFlow<List<GroupParticipantEntity>>(listOf())
    val achievements = _achievements.asStateFlow()

    private val _goalByUsers = MutableStateFlow<List<List<GoalByParicipantEntity>>>(listOf())
    val goalByUsers = _goalByUsers

    private val _selectedFriendIdx = MutableStateFlow(0)
    val selectedFriendIdx = _selectedFriendIdx.asStateFlow()

    fun updateGroupInfo(groupDetail: FriendsGroupEntity?) {
        if(groupDetail != null) {
            _groupId.value = groupDetail.groupId
            _groupName.value = groupDetail.groupName
            _groupGoal.value = groupDetail.groupGoal
            _participants.value = groupDetail.participants.map { it.user }
            _achievements.value = groupDetail.participants
            _goalByUsers.value = groupDetail.participants.map { it.plans }
        }
    }

    fun changeSelectedFriendIdx(idx: Int) {
        _selectedFriendIdx.value = idx
    }

    fun changeGroupId(groupId: Int) {
        _groupId.value = groupId
    }

    /**
     * 그룹 목표 수정하기 [Remote]
     * **/
    fun editGroupGoal(groupGoal: String) {
        viewModelScope.launch {
            friendsRepository.editGroupGoal(
                groupId = groupId.value,
                groupGoal = groupGoal,
                groupName = groupName.value
            ).collect { success ->
                if(success) {
                    _groupGoal.value = groupGoal
                }
            }
        }
    }

    /**
     * 친구 초대하기 [Remote]
     * **/
    fun inviteFriend(email: String) {
        viewModelScope.launch {
            friendsRepository.inviteFriend(
                email = email,
                groupId = groupId.value
            ).collect {
                updateGroupInfo()
            }
        }
    }

    fun updateGroupInfo() {
        viewModelScope.launch {
            friendsRepository.getSpecificGroup(
                groupId = groupId.value
            ).collect {
                updateGroupInfo(it.copy(groupId = groupId.value))
            }
        }
    }

    fun exitGroup(resultListener: () -> Unit) {
        viewModelScope.launch {
            friendsRepository.exitGroup(
                userId = preferenceManager.getUserId(),
                groupId = groupId.value
            ).collect {
                resultListener()
            }
        }
    }
}