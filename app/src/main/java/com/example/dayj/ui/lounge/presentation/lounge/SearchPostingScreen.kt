package com.example.dayj.ui.lounge.presentation.lounge

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.dayj.R
import com.example.dayj.ui.lounge.domain.entity.LoungePostingEntity
import com.example.dayj.ui.theme.Background
import com.example.dayj.ui.theme.Black2A
import com.example.dayj.ui.theme.GrayC0

@Composable
fun SearchPostingScreen(
    navHostController: NavHostController,
    navToPostingDetail: (posting: LoungePostingEntity) -> Unit,
    loungeViewModel: LoungeViewModel = hiltViewModel(),
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val searchQuery = loungeViewModel.searchQuery.collectAsState().value
    val filteredPostings =  loungeViewModel.filteredLoungeItems.collectAsState().value

    LaunchedEffect(key1 = Unit, block = {
        loungeViewModel.getAllPostings()
    })

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .background(color = Background)
            .clickable {
                keyboardController?.hide()
            }
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp)
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(16.dp)
                )
                .clip(
                    shape = RoundedCornerShape(16.dp)
                )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp)
                    .padding(horizontal = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    modifier = Modifier
                        .size(21.dp),
                    painter = painterResource(id = R.drawable.ic_search_gray),
                    contentDescription = ""
                )

                Spacer(modifier = Modifier.padding(start = 10.dp))

                BasicTextField(
                    modifier = Modifier
                        .weight(1f),
                    value = searchQuery,
                    textStyle = TextStyle(
                        color = Black2A,
                        fontWeight = FontWeight.Medium,
                        fontSize = 14.sp
                    ),
                    onValueChange = {
                        loungeViewModel.updateSearchQuery(it)
                    }
                ) { innerTextField ->

                    if(searchQuery.isEmpty()) {
                        Text(
                            text = "검색어를 입력해주세요.",
                            fontWeight = FontWeight.Medium,
                            fontSize = 14.sp,
                            color = GrayC0
                        )
                    }
                    innerTextField()
                }

                Spacer(modifier = Modifier.padding(start = 10.dp))

                Text(
                    modifier = Modifier.clickable {
                        navHostController.popBackStack()
                    },
                    text = "취소",
                    fontWeight = FontWeight.Medium,
                    fontSize = 14.sp,
                    color = Black2A
                )
            }
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 80.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)

        ) {
            items(
                items = filteredPostings,
                itemContent = {
                    LoungeItemView(loungePostingEntity = it) {
                        navToPostingDetail(it)
                    }
                }
            )
        }
    }
}