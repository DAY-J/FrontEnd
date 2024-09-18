package com.dayj.dayj.friends.domain.repository

import com.dayj.dayj.friends.data.model.request.RequestGroupCreation
import com.dayj.dayj.friends.data.model.request.RequestGroupGoal
import com.dayj.dayj.friends.data.model.response.ResponseFriendGroups
import com.dayj.dayj.friends.data.source.FriendsDataSource
import com.dayj.dayj.friends.domain.entity.FriendsGroupEntity
import com.dayj.dayj.friends.domain.entity.GoalByParicipantEntity
import com.dayj.dayj.friends.domain.entity.GroupParticipantEntity
import com.dayj.dayj.friends.domain.entity.UserEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FriendsRepositoryImpl @Inject constructor(
    private val friendsDataSource: FriendsDataSource
): FriendsRepository {
    override suspend fun createFriendGroup(groupName: String): Flow<Boolean> {
        return friendsDataSource.createFriendGroup(
            RequestGroupCreation(groupName = groupName)
        )
    }

    override suspend fun inviteFriend(email: String, groupId: Int): Flow<Boolean> {
        return friendsDataSource.inviteFriend(email = email, groupId = groupId)
    }

    override suspend fun getAllGroups(): Flow<List<FriendsGroupEntity>> {
        return friendsDataSource.getAllGroups().map { groups ->
            groups.map { groupModel ->
                groupModel.toEntity()
            }
        }
    }

    override suspend fun getSpecificGroup(groupId: Int): Flow<FriendsGroupEntity> {
        return friendsDataSource.getSpecificGroup(groupId = groupId).map { groupModel ->
            groupModel.toEntity()
        }
    }

    override suspend fun editGroupGoal(groupId: Int, groupGoal: String, groupName: String): Flow<Boolean> {
        return friendsDataSource.editGroupGoal(
            groupId = groupId,
            requestGroupGoal = RequestGroupGoal(
                groupGoal = groupGoal,
                groupName = groupName
            )
        )
    }

    override suspend fun exitGroup(userId: Int, groupId: Int): Flow<Boolean> {
        return friendsDataSource.exitGroup(userId, groupId)
    }

    fun ResponseFriendGroups.toEntity(): FriendsGroupEntity {
        return FriendsGroupEntity(
            groupId = id,
            groupName = groupName ?: "",
            participantsCount = groupMemberList?.size ?: 0,
            groupGoal = groupGoal ?: "",
            participants = groupMemberList?.map { member ->
                val member = groupMemberList.find { it.appUserId == member.appUserId }
                if(member != null) {
                    GroupParticipantEntity(
                        user = UserEntity(
                            userName = member.nickname,
                            userId = member.appUserId
                        ),
                        achievementRate = member.achievementRate?.achievementRate ?: 0,
                        plans = member.groupMemberPlans?.map { plan ->
                            GoalByParicipantEntity(
                                userId = member.appUserId,
                                planId = plan.id,
                                goalTitle = plan.goal,
                                achieved = plan.complete
                            )
                        } ?: listOf()
                    )
                } else {
                    null
                }
            }?.filterNotNull() ?: listOf()
        )
    }
}