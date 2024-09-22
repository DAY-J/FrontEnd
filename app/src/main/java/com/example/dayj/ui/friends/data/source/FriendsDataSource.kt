package com.example.dayj.ui.friends.data.source

import com.example.dayj.ui.friends.data.model.request.RequestGroupCreation
import com.example.dayj.ui.friends.data.model.request.RequestGroupGoal
import com.example.dayj.ui.friends.data.model.response.ResponseFriendGroups
import kotlinx.coroutines.flow.Flow

interface FriendsDataSource {
    suspend fun createFriendGroup(
        requestGroupCreation: com.example.dayj.ui.friends.data.model.request.RequestGroupCreation
    ): Flow<Boolean>

    suspend fun inviteFriend(
        email: String,
        groupId: Int
    ):Flow<Boolean>

    suspend fun getAllGroups(): Flow<List<com.example.dayj.ui.friends.data.model.response.ResponseFriendGroups>>

    suspend fun getSpecificGroup(groupId: Int): Flow<com.example.dayj.ui.friends.data.model.response.ResponseFriendGroups>

    suspend fun editGroupGoal(
        groupId: Int,
        requestGroupGoal: com.example.dayj.ui.friends.data.model.request.RequestGroupGoal
    ): Flow<Boolean>

    suspend fun exitGroup(
        userId: Int,
        groupId: Int
    ): Flow<Boolean>
}