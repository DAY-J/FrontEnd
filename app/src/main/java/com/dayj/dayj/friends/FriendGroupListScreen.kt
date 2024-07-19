package com.dayj.dayj.friends

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.dayj.dayj.R
import com.dayj.dayj.ui.theme.TextBlack
import kotlin.random.Random

@Composable
fun FriendsGroupListScreen(
    navToGroupDetail: () -> Unit,
    groupListViewModel: GroupListViewModel = hiltViewModel()
) {
    var isCreateGroupDialogOpen = remember{ mutableStateOf(false) }
    val groupList = groupListViewModel.groupList.collectAsState().value

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp)
    ) {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 35.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "친구",
                    style = TextStyle(
                        color = TextBlack,
                        fontWeight = FontWeight.Bold,
                        fontSize = 28.sp
                    )
                )
                Spacer(modifier = Modifier.weight(1f))

                Image(
                    modifier = Modifier.clickable {
                        isCreateGroupDialogOpen.value = true
                    },
                    painter = painterResource(R.drawable.add),
                    contentDescription = ""
                )
            }

            LazyColumn(
                modifier = Modifier
                    .padding(top = 20.dp)
            ) {

                items(
                    items = groupList,
                    itemContent = {
                        Box(
                            modifier = Modifier
                                .padding(bottom = 10.dp)
                                .clickable { navToGroupDetail() }
                        ) {
                            ItemFriendGroup(group = it)
                        }
                    },
                )
            }
        }

        if(isCreateGroupDialogOpen.value) {
            CreateGroupDialog(
                onDismiss = {
                    isCreateGroupDialogOpen.value = false
                            },
                onCreateGroup = { groupName ->
                    isCreateGroupDialogOpen.value = false
                    groupListViewModel.addGroup(
                        FriendsGroupEntity(
                            groupId = Random.nextInt(),
                            groupName = groupName,
                            participantsCount = 1,
                            goal = ""
                        )
                    )
                }
            )

        }
    }
}

@Composable
fun ItemFriendGroup(group: FriendsGroupEntity) {
    Row(
        modifier = Modifier
            .background(
                color = Color.White,
                shape = RoundedCornerShape(16.dp)
            )
            .clip(shape = RoundedCornerShape(16.dp))
            .padding(
                horizontal = 12.dp,
                vertical = 15.dp
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = R.drawable.account_circle),
            contentDescription = ""
        )

        Column {
            Text(
                text = group.groupName,
                style = TextStyle(
                    color = TextBlack,
                    fontWeight = FontWeight.Medium,
                    fontSize = 14.sp
                )
            )
            Text(
                text = group.goal,
                style = TextStyle(
                    color = TextBlack,
                    fontWeight = FontWeight.Medium,
                    fontSize = 12.sp
                )
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = "${group.participantsCount}/20",
            style = TextStyle(
                color = TextBlack,
                fontWeight = FontWeight.Medium,
                fontSize = 12.sp
            )
        )
    }
}