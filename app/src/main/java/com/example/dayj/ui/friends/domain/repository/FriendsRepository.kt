package com.example.dayj.ui.friends.domain.repository

import com.example.dayj.ui.friends.domain.entity.FriendsGroupEntity
import kotlinx.coroutines.flow.Flow

interface FriendsRepository {
    suspend fun createFriendGroup(
        groupName: String
    ): Flow<Boolean>

    suspend fun inviteFriend(
        email: String,
        groupId: Int
    ): Flow<Boolean>

    suspend fun getAllGroups(): Flow<List<FriendsGroupEntity>>

    suspend fun getSpecificGroup(groupId: Int): Flow<FriendsGroupEntity>

    suspend fun editGroupGoal(
        groupId: Int,
        groupGoal: String,
        groupName: String
    ): Flow<Boolean>

    suspend fun exitGroup(
        userId: Int,
        groupId: Int
    ): Flow<Boolean>
}