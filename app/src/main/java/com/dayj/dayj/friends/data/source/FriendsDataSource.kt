package com.dayj.dayj.friends.data.source

import com.dayj.dayj.friends.data.model.request.RequestGroupCreation
import com.dayj.dayj.friends.data.model.request.RequestGroupGoal
import com.dayj.dayj.friends.data.model.response.ResponseFriendGroups
import kotlinx.coroutines.flow.Flow

interface FriendsDataSource {
    suspend fun createFriendGroup(
        requestGroupCreation: RequestGroupCreation
    ): Flow<Boolean>

    suspend fun inviteFriend(
        email: String,
        groupId: Int
    ):Flow<Boolean>

    suspend fun getAllGroups(): Flow<List<ResponseFriendGroups>>

    suspend fun getSpecificGroup(groupId: Int): Flow<ResponseFriendGroups>

    suspend fun editGroupGoal(
        groupId: Int,
        requestGroupGoal: RequestGroupGoal
    ): Flow<Boolean>
}