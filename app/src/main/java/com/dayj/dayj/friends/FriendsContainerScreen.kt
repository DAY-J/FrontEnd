package com.dayj.dayj.friends

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

enum class FriendsScreens {
    GroupList,
    GroupDetail
}

@Composable
fun FriendsContainerScreen() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = FriendsScreens.GroupList.name) {
        composable(FriendsScreens.GroupList.name) {
            FriendsGroupListScreen(
                navToGroupDetail = {
                    navController.navigate(FriendsScreens.GroupDetail.name)
                }
            )
        }
        composable(FriendsScreens.GroupDetail.name) {
            GroupDetailScreen(
                onClickBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}