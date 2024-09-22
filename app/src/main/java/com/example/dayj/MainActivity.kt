package com.example.dayj

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.dayj.component.BottomNavItem
import com.example.dayj.data.PreferenceManager
import com.example.dayj.ext.LocalDateFormat
import com.example.dayj.ext.formatLocalDate
import com.example.dayj.ui.login.LoginScreen
import com.example.dayj.ui.mypage.presentation.ChangeNickNameScreen
import com.example.dayj.ui.mypage.presentation.LinkedAccountScreen
import com.example.dayj.network.api.response.PlanResponse
import com.example.dayj.ui.friends.domain.entity.UserEntity
import com.example.dayj.ui.friends.presentation.FriendsContainerScreen
import com.example.dayj.ui.home.HomeScreen
import com.example.dayj.ui.home.addtodo.TodoScreen
import com.example.dayj.ui.lounge.domain.entity.LoungePostingEntity
import com.example.dayj.ui.lounge.presentation.lounge.LoungeScreen
import com.example.dayj.ui.lounge.presentation.lounge.SearchPostingScreen
import com.example.dayj.ui.lounge.presentation.posting.LoungePostingScreen
import com.example.dayj.ui.lounge.presentation.write.PostWritingScreen
import com.example.dayj.ui.mypage.presentation.MyPageScreen
import com.example.dayj.ui.register.RegisterScreen
import com.example.dayj.ui.statistics.StatisticsScreen
import com.example.dayj.ui.theme.Background
import com.example.dayj.ui.theme.DayJTheme
import com.example.dayj.util.NavigationUtil
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    val userEntityKey = "USER_ENTITY_KEY"
    val selectedPostingKey = "SELECTED_POSTING_KEY"

    @Inject
    lateinit var userPreferenceManager: PreferenceManager

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()

            userPreferenceManager.putUserId(1)
            userPreferenceManager.putUserName("이신형")

            DayJTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Background
                ) {
                    NavHost(
                        navController = navController,
                        startDestination = NavigatorScreens.Login.name
                    ) {

                        composable(NavigatorScreens.Login.name) {
                            LoginScreen(
                                navToHome = {
                                    navController.navigate(NavigatorScreens.Main.name) {
                                        popUpTo(navController.graph.id) { inclusive = true }
                                        launchSingleTop = true
                                    }
                                },
                                navToRegister = { email ->
                                    navController.navigate(ScreenType.Register(email).sendRoute)
                                }
                            )
                        }

                        composable(ScreenType.Register().route, arguments = listOf(
                            navArgument("email") {
                                type = NavType.StringType
                            }
                        )) {
                            RegisterScreen(
                                navToLogin = {
                                    navController.navigateUp()
                                },
                                navToHome = {
                                    navController.navigate(NavigatorScreens.Main.name) {
                                        popUpTo(navController.graph.id) { inclusive = true }
                                        launchSingleTop = true
                                    }
                                }
                            )
                        }

                        composable(NavigatorScreens.Main.name) {
                            MainScreen(
                                navToPostingDetail = {
                                    navController.currentBackStackEntry?.savedStateHandle?.set(
                                        selectedPostingKey,
                                        it
                                    )
                                    navController.navigate(
                                        NavigatorScreens.PostingDetail.name,
                                    )
                                },
                                navToWritePosting = {
                                    navController.navigate(
                                        NavigatorScreens.WritePosting.name,
                                    )
                                },
                                navToSearchPosting = {
                                    navController.navigate(
                                        NavigatorScreens.SearchPosting.name
                                    )
                                },
                                navToAddToDo = { selectDate ->
                                    navController.navigate(
                                        ScreenType.AddTodo(
                                            formatLocalDate(
                                                selectDate,
                                                LocalDateFormat.STATISTIC_FORMAT
                                            )
                                        ).sendRoute
                                    )
                                },
                                navToUpdateTodo = { item ->
                                    navController.navigate(
                                        ScreenType.UpdateTodo(NavigationUtil.passItem(item)).sendRoute
                                    )
                                },
                                navToChangeNickName = { userEntity ->
                                    navController.currentBackStackEntry?.savedStateHandle?.set(
                                        userEntityKey,
                                        userEntity
                                    )
                                    navController.navigate(
                                        NavigatorScreens.ChangeNickName.name,
                                    )
                                },
                                navToLinkedAccount = {
                                    navController.navigate(
                                        NavigatorScreens.LinkedAccount.name,
                                    )
                                }
                            )
                        }

                        composable(NavigatorScreens.PostingDetail.name) {
                            val posting =
                                navController.previousBackStackEntry?.savedStateHandle?.get<LoungePostingEntity>(
                                    selectedPostingKey
                                )
                            LoungePostingScreen(
                                loungePostingEntity = posting,
                                navHostController = navController
                            )
                        }

                        composable(NavigatorScreens.WritePosting.name) {
                            PostWritingScreen(
                                navHostController = navController
                            )
                        }

                        composable(NavigatorScreens.SearchPosting.name) {
                            SearchPostingScreen(
                                navHostController = navController,
                                navToPostingDetail = {
                                    navController.currentBackStackEntry?.savedStateHandle?.set(
                                        selectedPostingKey,
                                        it
                                    )
                                    navController.navigate(
                                        NavigatorScreens.PostingDetail.name,
                                    )
                                }
                            )
                        }

                        composable(
                            route = ScreenType.AddTodo().route,
                            arguments = listOf(
                                navArgument(ScreenType.AddTodo().selectDate) {
                                    type = NavType.StringType
                                }
                            )) {
                            TodoScreen.Add(navController = navController)
                        }

                        composable(
                            route = ScreenType.UpdateTodo().route,
                            arguments = listOf(
                                navArgument(ScreenType.UpdateTodo().passItem) {
                                    type = NavigationUtil.assetParamTypeOf<PlanResponse>()
                                }
                            )
                        ) {
                            TodoScreen.Update(navController = navController)
                        }

                        composable(NavigatorScreens.LinkedAccount.name) {
                            LinkedAccountScreen(
                                onClickBack = {
                                    navController.popBackStack()
                                }
                            )
                        }
                        composable(NavigatorScreens.ChangeNickName.name) {
                            val userEntity =
                                navController.previousBackStackEntry?.savedStateHandle?.get<UserEntity>(
                                    userEntityKey
                                )
                            ChangeNickNameScreen(
                                userEntity = userEntity,
                                onClickBack = {
                                    navController.popBackStack()
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MainScreen(
    navToPostingDetail: (posting: LoungePostingEntity) -> Unit,
    navToWritePosting: () -> Unit,
    navToSearchPosting: () -> Unit,
    navToAddToDo: (LocalDate) -> Unit,
    navToUpdateTodo: (PlanResponse) -> Unit,
    navToLinkedAccount: () -> Unit,
    navToChangeNickName: (UserEntity) -> Unit,
) {
    val bottomItems = listOf(
        BottomNavItem.HOME,
        BottomNavItem.STATISTICS,
        BottomNavItem.LOUNGE,
        BottomNavItem.FRIENDS,
        BottomNavItem.MY
    )
    val coroutineScope = rememberCoroutineScope()
    val pagerState = rememberPagerState(initialPage = 0) { bottomItems.size }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        HorizontalPager(
            state = pagerState,
        ) { page ->
            when (page) {
                0 -> HomeScreen(navToAddToDo = navToAddToDo, navToUpdateTodo = navToUpdateTodo)
                1 -> StatisticsScreen()
                2 -> LoungeScreen(
                    navToPostingDetail = navToPostingDetail,
                    navToWritePosting = navToWritePosting,
                    navToSearchPosting = navToSearchPosting
                )

                3 -> FriendsContainerScreen()
                4 -> MyPageScreen(
                    navToChangeNickName = navToChangeNickName,
                    navToLinkedAccount = navToLinkedAccount
                )
            }
        }

        BottomNavigationBar(
            bottomItems = bottomItems,
            selectedTabIdx = pagerState.targetPage,
            onClickTab = { idx ->
                coroutineScope.launch {
                    pagerState.scrollToPage(idx)
                }
            }
        )
    }
}


@Composable
fun BoxScope.BottomNavigationBar(
    bottomItems: List<BottomNavItem>,
    selectedTabIdx: Int,
    onClickTab: (Int) -> Unit
) {
    val navWidth = LocalConfiguration.current.screenWidthDp - 40

    Box(
        modifier = Modifier
            .padding(start = 20.dp, end = 20.dp, bottom = 20.dp)
            .fillMaxWidth()
            .shadow(elevation = 5.dp, shape = RoundedCornerShape(16.dp))
            .clip(RoundedCornerShape(16.dp))
            .background(
                color = Color.White,
                shape = RoundedCornerShape(16.dp)
            )
            .align(Alignment.BottomCenter)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            bottomItems.forEachIndexed { idx, item ->
                BottomNavTabItem(
                    item,
                    navWidth / 5,
                    idx == selectedTabIdx
                ) {
                    onClickTab(idx)
                }
            }
        }
    }
}

@Composable
fun BottomNavTabItem(
    item: BottomNavItem,
    width: Int,
    selected: Boolean,
    onClickTab: () -> Unit
) {
    Column(
        modifier = Modifier
            .width(width.dp)
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ) { onClickTab() },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = if (selected) item.selectedIcon else item.unSelectedIcon),
            contentDescription = ""
        )
        Spacer(modifier = Modifier.padding(top = 5.dp))
        Text(
            text = item.title,
            style = TextStyle(
                fontWeight = FontWeight.Medium,
                fontSize = 10.sp,
                color = if (selected) Color.Black else Gray
            )
        )
    }
}

