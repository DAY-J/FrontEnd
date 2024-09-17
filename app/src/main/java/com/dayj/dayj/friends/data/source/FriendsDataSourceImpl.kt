package com.dayj.dayj.friends.data.source

import com.dayj.dayj.data.PreferenceManager
import com.dayj.dayj.friends.data.model.request.RequestGroupCreation
import com.dayj.dayj.friends.data.model.request.RequestGroupGoal
import com.dayj.dayj.friends.data.model.response.ResponseFriendGroups
import com.dayj.dayj.network.ApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FriendsDataSourceImpl @Inject constructor(
    private val apiService: ApiService,
    private val preferenceManager: PreferenceManager
): FriendsDataSource {
    override suspend fun createFriendGroup(requestGroupCreation: RequestGroupCreation): Flow<Boolean> = flow {
        val response = apiService.postGroupCreation(
            userId = preferenceManager.getUserId(),
            requestGroupCreation = requestGroupCreation
        )
        emit(response.isSuccessful)
    }

    override suspend fun inviteFriend(email: String, groupId: Int): Flow<Boolean> = flow {
        val response = apiService.postFriendInvitation(email = email, groupId = groupId)
        emit(response.isSuccessful)
    }

    override suspend fun getAllGroups(): Flow<List<ResponseFriendGroups>> = flow {
        val response = apiService.getAllGroups(userId = preferenceManager.getUserId())
        if(response.isSuccessful && response.body() != null) {
            emit(response.body()!!)
        }
    }

    override suspend fun getSpecificGroup(groupId: Int): Flow<ResponseFriendGroups> = flow {
        val response = apiService.getSpecificGroup(
            userId = preferenceManager.getUserId(),
            groupId = groupId
        )
        if(response.isSuccessful && response.body() != null) {
            emit(response.body()!!)
        }
    }

    override suspend fun editGroupGoal(groupId: Int, requestGroupGoal: RequestGroupGoal) = flow {
        val response = apiService.patchGroupGoalEdit(
            groupId = groupId,
            requestGroupGoal = requestGroupGoal,
            userId = preferenceManager.getUserId()
        )
        emit(response.isSuccessful)
    }

    override suspend fun exitGroup(userId: Int, groupId: Int): Flow<Boolean> = flow {
        val response = apiService.exitGroup(
            appUserId = userId,
            groupId = groupId
        )
        emit(response.isSuccessful)
    }
}