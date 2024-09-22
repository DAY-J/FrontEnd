package com.example.dayj.ui.friends.presentation.groupdetail

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.dayj.R
import com.example.dayj.component.FriendListItem
import com.example.dayj.ui.friends.domain.entity.FriendsGroupEntity
import com.example.dayj.ui.friends.domain.entity.GoalByParicipantEntity
import com.example.dayj.ui.friends.domain.entity.GroupParticipantEntity
import com.example.dayj.ui.friends.domain.entity.UserEntity
import com.example.dayj.ui.theme.Background
import com.example.dayj.ui.theme.Black3A
import com.example.dayj.ui.theme.GrayAFAF
import com.example.dayj.ui.theme.GrayDDD
import com.example.dayj.ui.theme.TextBlack
import com.example.dayj.ui.theme.TextGray


@Composable
fun GroupDetailScreen(
    groupDetail: FriendsGroupEntity?,
    viewModel: GroupDetailViewModel = hiltViewModel(),
    onClickBack: () -> Unit
) {
    val context = LocalContext.current
    val inviteFriendDialogOpened = remember { mutableStateOf(false) }
    val exitGroupDialogOpened = remember { mutableStateOf(false) }
    val editGroupGoalDialogOpened = remember { mutableStateOf(false) }

    val selectedFriendIdx = viewModel.selectedFriendIdx.collectAsState().value
    val participants = viewModel.participants.collectAsState().value
    val goals = viewModel.goalByUsers.collectAsState().value

    LaunchedEffect(key1 = Unit) {
        viewModel.updateGroupInfo(groupDetail)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Background)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            GroupToolbar(
                groupName = groupDetail?.groupName ?: "",
                onClickBack = onClickBack,
                onClickInviteFriend = {
                    inviteFriendDialogOpened.value = true
                },
                onClickExit = {
                    exitGroupDialogOpened.value = true
                }
            )

            LazyColumn(
                modifier = Modifier,
                contentPadding = PaddingValues(top = 28.dp)
            ) {
                item {
                    GroupJointGoalView(
                        groupGoal = viewModel.groupGoal.collectAsState().value,
                        onClickEdit = {
                            editGroupGoalDialogOpened.value = true
                        }
                    )
                    Spacer(modifier = Modifier.padding(top = 28.dp))
                    AchievementListView(achievements = viewModel.achievements.collectAsState().value)
                    HorizontalDivider(height = 8, verticalPadding = 20)
                    FriendListView(
                        selectedFriendIdx = selectedFriendIdx,
                        friends = participants,
                        onClickFriend = { idx ->
                            viewModel.changeSelectedFriendIdx(idx)
                        }
                    )
                    HorizontalDivider(height = 1, verticalPadding = 20)
                }

                if(goals.flatten().isNotEmpty() && participants.isNotEmpty()) {
                    items(
                        items = goals.find {
                            if(it.isNotEmpty()) {
                                it.first().userId == participants.get(selectedFriendIdx).userId
                            } else {
                                false
                            }
                        } ?: emptyList(),
                        itemContent = {
                            GoalByFriendItem(it)
                        }
                    )
                }
            }
        }

        if(inviteFriendDialogOpened.value) {
            InviteFriendDialog(
                onDismiss = {
                    inviteFriendDialogOpened.value = false
                },
                onClickOk = { friendEmail ->
                    viewModel.inviteFriend(friendEmail)
                    Toast.makeText(context, "초대 요청을 보냈습니다.", Toast.LENGTH_SHORT).show()
                    inviteFriendDialogOpened.value = false
                }
            )
        }

        if(exitGroupDialogOpened.value) {
            ExitGroupDialog(
                groupName = viewModel.groupName.value,
                onDismiss = {
                    exitGroupDialogOpened.value = false
                },
                onClickExit = {
                    viewModel.exitGroup {
                        exitGroupDialogOpened.value = false
                        Toast.makeText(context, "${viewModel.groupName.value} 그룹을 나갔습니다.", Toast.LENGTH_SHORT).show()
                        onClickBack()
                    }
                }
            )
        }

        if(editGroupGoalDialogOpened.value) {
            EditGroupGoalDialog(
                groupGoal = viewModel.groupGoal.collectAsState().value,
                onDismiss = {
                    editGroupGoalDialogOpened.value = false
                },
                onClickComplete = { groupGoal ->

                    editGroupGoalDialogOpened.value = false
                    viewModel.editGroupGoal(groupGoal = groupGoal)
                    Toast.makeText(context, "그룹 공동 목표를 수정하였습니다.", Toast.LENGTH_SHORT).show()
                }
            )
        }
    }
}

@Composable
fun ColumnScope.HorizontalDivider(height: Int, verticalPadding: Int = 0) {
    Spacer(modifier = Modifier.padding(top = verticalPadding.dp))
    Box(
        modifier = Modifier
            .height(height.dp)
            .fillMaxWidth()
            .background(color = GrayDDD)
    )
    Spacer(modifier = Modifier.padding(top = verticalPadding.dp))
}

@Composable
fun GroupToolbar(
    groupName: String,
    onClickBack: () -> Unit,
    onClickInviteFriend: () -> Unit,
    onClickExit: () -> Unit
) {
    val groupSettingDialogVisible = remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .padding(start = 20.dp, top = 24.dp, end = 20.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            modifier = Modifier
                .clickable {
                    onClickBack()
                },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.left_arrow_black),
                contentDescription = ""
            )
            Spacer(modifier = Modifier.padding(end = 4.dp))
            Text(
                text = groupName,
                style = TextStyle(
                    color = Black3A,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        GroupSettingDialog(
            expanded = groupSettingDialogVisible.value,
            onClickInvite = {
                onClickInviteFriend()
                groupSettingDialogVisible.value = false
            },
            onClickExit = {
                onClickExit()
                groupSettingDialogVisible.value = false
            },
            onDismiss = {
                groupSettingDialogVisible.value = false
            }
        )

        Image(
            modifier = Modifier
                .clickable {
                    groupSettingDialogVisible.value = true
                },
            painter = painterResource(id = R.drawable.ic_more),
            contentDescription = ""
        )
    }

}

@Composable
fun GroupJointGoalView(
    groupGoal: String,
    onClickEdit: () -> Unit
) {
    Box(
        modifier = Modifier
            .padding(start = 20.dp, end = 20.dp)
            .background(
                color = GrayDDD,
                shape = RoundedCornerShape(16.dp)
            )
    ) {
        Row(
            modifier = Modifier
                .padding(vertical = 9.dp, horizontal = 9.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "공동 목표",
                style = TextStyle(
                    color = Black3A,
                    fontWeight = FontWeight.Medium,
                    fontSize = 14.sp
                )
            )

            Spacer(modifier = Modifier.padding(end = 8.dp))

            Box(
                modifier = Modifier
                    .width(1.dp)
                    .height(14.dp)
                    .background(color = Black3A)
            )

            Spacer(modifier = Modifier.padding(end = 8.dp))

            Text(
                text = groupGoal,
                style = TextStyle(
                    color = Black3A,
                    fontWeight = FontWeight.Medium,
                    fontSize = 14.sp
                )
            )

            Spacer(modifier = Modifier.weight(1f))

            Image(
                modifier = Modifier
                    .clickable {
                        onClickEdit()
                    },
                painter = painterResource(id = R.drawable.bounding_box),
                contentDescription = ""
            )
        }
    }
}

@Composable
fun AchievementListView(achievements: List<GroupParticipantEntity>) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp),
    ) {
        Text(
            text = "달성률",
            style = TextStyle(
                color = TextGray,
                fontWeight = FontWeight.Medium,
                fontSize = 14.sp
            )
        )

        Spacer(modifier = Modifier.padding(top = 15.dp))

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            items(
                items = achievements,
                itemContent = {
                    AchievementByMember(achievements.indexOf(it) + 1, it)
                }
            )
        }
    }
}

@Composable
fun AchievementByMember(rank: Int, achievement: GroupParticipantEntity) {
    Column(
        modifier = Modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box {
            Image(
                painter = painterResource(id = R.drawable.account_circle),
                contentDescription = ""
            )
            Box(
                modifier = Modifier
                    .width(18.dp)
                    .height(18.dp)
                    .align(Alignment.BottomEnd)
                    .background(
                        color = if (rank <= 3) Black3A else TextGray,
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = rank.toString(),
                    style = TextStyle(
                        color = Color.White,
                        fontWeight = FontWeight.Medium,
                        fontSize = 10.sp
                    )
                )
            }
        }

        Spacer(modifier = Modifier.padding(top = 6.dp))

        Text(
            text = achievement.user.userName,
            style = TextStyle(
                color = TextBlack,
                fontWeight = FontWeight.Medium,
                fontSize = 14.sp
            )
        )

        Spacer(modifier = Modifier.padding(top = 3.dp))

        Text(
            text = "${achievement.achievementRate.toInt()}%",
            style = TextStyle(
                color = TextBlack,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp
            )
        )
    }
}

@Composable
fun FriendListView(
    selectedFriendIdx:Int,
    friends: List<UserEntity>,
    onClickFriend: ((Int) -> Unit)
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
        ) {
            Text(
                text = "친구 목록",
                style = TextStyle(
                    color = TextGray,
                    fontWeight = FontWeight.Medium,
                    fontSize = 14.sp
                )
            )

            Spacer(modifier = Modifier.weight(1f))

        }

        Spacer(modifier = Modifier.padding(top = 15.dp))

        LazyRow(
            modifier = Modifier.padding(start = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            items(
                items = friends,
                itemContent = {
                    FriendListItem(
                        selected = selectedFriendIdx == friends.indexOf(it),
                        user = it,
                        onClick = {
                            onClickFriend(friends.indexOf(it))
                        }
                    )
                }
            )
        }
    }
}

@Composable
fun GoalByFriendItem(goalEntity: GoalByParicipantEntity) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp, end = 20.dp, bottom = 12.dp)
            .background(
                color = Color.White,
                shape = RoundedCornerShape(16.dp)
            )
    ) {
        Column(
            modifier = Modifier.padding(
                vertical = 19.dp,
                horizontal = 16.dp
            )
        ) {
            Text(
                text = goalEntity.goalTitle,
                style = TextStyle(
                    color = Black3A,
                    fontWeight = FontWeight.Medium,
                    fontSize = 14.sp
                )
            )

            Spacer(
                modifier = Modifier
                    .padding(top = if(goalEntity.achieved) 4.dp else 8.dp)
            )

            Row {
                if(goalEntity.achieved) {
                    Image(
                        modifier = Modifier.padding(end = 8.dp),
                        painter = painterResource(id = R.drawable.ic_achieved_badge),
                        contentDescription = ""
                    )
                }

                Text(
                    text = if (goalEntity.achieved) "달성" else "미달성",
                    style = TextStyle(
                        color = GrayAFAF,
                        fontWeight = FontWeight.Medium,
                        fontSize = 14.sp
                    )
                )
            }
        }
    }
}