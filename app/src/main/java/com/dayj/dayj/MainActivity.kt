package com.dayj.dayj

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
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.dayj.dayj.friends.FriendsContainerScreen
import com.dayj.dayj.home.HomeScreen
import com.dayj.dayj.lounge.LoungeTagEnum
import com.dayj.dayj.lounge.lounge.LoungeScreen
import com.dayj.dayj.lounge.lounge.SearchPostingScreen
import com.dayj.dayj.lounge.posting.CommentEntity
import com.dayj.dayj.lounge.posting.LoungePostingEntity
import com.dayj.dayj.lounge.posting.LoungePostingScreen
import com.dayj.dayj.lounge.write.PostWritingScreen
import com.dayj.dayj.ui.theme.Background
import com.dayj.dayj.ui.theme.DayJTheme
import com.thedeanda.lorem.LoremIpsum
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import kotlin.random.Random

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            var postingId = -1
            DayJTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Background
                ) {
                    NavHost(navController = navController, startDestination = NavigatorScreens.Main.name) {
                        composable(NavigatorScreens.Main.name) {
                            MainScreen(
                                navToPostingDetail = {
                                    postingId = it
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
                                }
                            )
                        }
                        composable(NavigatorScreens.PostingDetail.name) {
                            LoungePostingScreen(
                                postingId = postingId,
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
                                    postingId = it
                                    navController.navigate(
                                        NavigatorScreens.PostingDetail.name,
                                    )
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
    navToPostingDetail: (postingId: Int) -> Unit,
    navToWritePosting: () -> Unit,
    navToSearchPosting: () -> Unit
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
                0 -> HomeScreen()
                1 -> StatisticsScreen()
                2 -> LoungeScreen(
                    navToPostingDetail = navToPostingDetail,
                    navToWritePosting = navToWritePosting,
                    navToSearchPosting = navToSearchPosting
                )
                3 -> FriendsContainerScreen()
                4 -> MyPageScreen()
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
fun BoxScope.BottomNavigationBar(bottomItems: List<BottomNavItem>, selectedTabIdx: Int, onClickTab: (Int) -> Unit) {
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
            painter = painterResource(id = if(selected) item.selectedIcon else item.unSelectedIcon),
            contentDescription = ""
        )
        Spacer(modifier = Modifier.padding(top = 5.dp))
        Text(
            text = item.title,
            style = TextStyle(
                fontWeight = FontWeight.Medium,
                fontSize = 10.sp,
                color = if(selected) Color.Black else Gray
            )
        )
    }
}

