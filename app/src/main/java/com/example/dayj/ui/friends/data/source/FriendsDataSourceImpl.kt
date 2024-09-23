package com.example.dayj.ui.friends.data.source

import com.example.dayj.data.PreferenceManager
import com.example.dayj.datastore.SelfUserAccountDataStore
import com.example.dayj.ui.friends.data.model.request.RequestGroupCreation
import com.example.dayj.ui.friends.data.model.request.RequestGroupGoal
import com.example.dayj.ui.friends.data.model.response.ResponseFriendGroups
import com.example.dayj.network.ApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FriendsDataSourceImpl @Inject constructor(
    private val apiService: ApiService,
    private val userAccountDataSource: SelfUserAccountDataStore
): FriendsDataSource {
    override suspend fun createFriendGroup(requestGroupCreation: com.example.dayj.ui.friends.data.model.request.RequestGroupCreation): Flow<Boolean> = flow {
        userAccountDataSource.userInfoFlow.collect { userInfo ->
            userInfo?.let {
                val response = apiService.postGroupCreation(
                    userId = it.id,
                    requestGroupCreation = requestGroupCreation
                )
                emit(response.isSuccessful)
            }
        }
    }

    override suspend fun inviteFriend(email: String, groupId: Int): Flow<Boolean> = flow {
        val response = apiService.postFriendInvitation(email = email, groupId = groupId)
        emit(response.isSuccessful)
    }

    override suspend fun getAllGroups(): Flow<List<com.example.dayj.ui.friends.data.model.response.ResponseFriendGroups>> = flow {
        userAccountDataSource.userInfoFlow.collect { userInfo ->
            userInfo?.let {
                val response = apiService.getAllGroups(userId = it.id)
                if(response.isSuccessful && response.body() != null) {
                    emit(response.body()!!)
                }
            }
        }
    }

    override suspend fun getSpecificGroup(groupId: Int): Flow<com.example.dayj.ui.friends.data.model.response.ResponseFriendGroups> = flow {
        userAccountDataSource.userInfoFlow.collect { userInfo ->
            userInfo?.let {
                val response = apiService.getSpecificGroup(
                    userId = it.id,
                    groupId = groupId
                )
                if(response.isSuccessful && response.body() != null) {
                    emit(response.body()!!)
                }
            }
        }
    }

    override suspend fun editGroupGoal(groupId: Int, requestGroupGoal: com.example.dayj.ui.friends.data.model.request.RequestGroupGoal) = flow {
        userAccountDataSource.userInfoFlow.collect { userInfo ->
            userInfo?.let {
                val response = apiService.patchGroupGoalEdit(
                    groupId = groupId,
                    requestGroupGoal = requestGroupGoal,
                    userId = it.id
                )
                emit(response.isSuccessful)
            }
        }
    }

    override suspend fun exitGroup( groupId: Int): Flow<Boolean> = flow {
        userAccountDataSource.userInfoFlow.collect { userInfo ->
            userInfo?.let {
                val response = apiService.exitGroup(
                    appUserId = it.id,
                    groupId = groupId
                )
                emit(response.isSuccessful)
            }
        }
    }
}