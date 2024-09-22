package com.example.dayj.ui.friends.presentation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.dayj.ui.friends.domain.entity.FriendsGroupEntity
import com.example.dayj.ui.friends.presentation.groupdetail.GroupDetailScreen
import com.example.dayj.ui.friends.presentation.groups.GroupListScreen

enum class FriendsScreens {
    GroupList,
    GroupDetail
}

@Composable
fun FriendsContainerScreen() {
    val groupDetailKey = "GROUP_DETAIL_KEY"

    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = FriendsScreens.GroupList.name) {
        composable(FriendsScreens.GroupList.name) {
            GroupListScreen(
                navToGroupDetail = { groupEntity ->
                    navController.currentBackStackEntry?.savedStateHandle?.set(groupDetailKey, groupEntity)
                    navController.navigate(FriendsScreens.GroupDetail.name)
                }
            )
        }
        composable(FriendsScreens.GroupDetail.name) {
            val groupDetail = navController.previousBackStackEntry?.savedStateHandle?.get<FriendsGroupEntity>(groupDetailKey)
            GroupDetailScreen(
                groupDetail = groupDetail,
                onClickBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}