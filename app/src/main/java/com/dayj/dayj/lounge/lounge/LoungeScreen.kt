package com.dayj.dayj.lounge.lounge

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.dayj.dayj.R
import com.dayj.dayj.lounge.LoungeTag
import com.dayj.dayj.ui.theme.Black3A
import com.dayj.dayj.ui.theme.Gray6F
import com.dayj.dayj.ui.theme.Green12

@Composable
fun LoungeScreen(
    loungeViewModel: LoungeViewModel = hiltViewModel(),
    navToPostingDetail: (postingId: Int) -> Unit,
    navToWritePosting: () -> Unit,
    navToSearchPosting: () -> Unit
) {
    val selectedLoungeTag = loungeViewModel.selectedTag.collectAsState().value
    val loungeItems = loungeViewModel.loungeItems.collectAsState().value

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp),
    ) {
        Spacer(modifier = Modifier.height(20.dp))

        LoungeToolbar(
            onClickSearch = {
                navToSearchPosting()
            },
            onClickAdd = {
                navToWritePosting()
            }
        )

        Spacer(modifier = Modifier.height(20.dp))

        LazyRow(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            items(
                items = loungeViewModel.tags,
                itemContent = {
                    Box(
                        modifier = Modifier
                            .padding(end =  8.dp)
                    ) {
                        LoungeTag(
                            selected = selectedLoungeTag == it,
                            tagText = it.tagText,
                            onClickListener = {
                                loungeViewModel.changeSelectedTag(it)
                            }
                        )
                    }
                }
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        LoungeSortView(onClickSort = {})

        Spacer(modifier = Modifier.height(24.dp))

        LazyColumn(
            modifier = Modifier,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(
                items = loungeItems,
                itemContent = {
                    LoungeItemView(loungeListEntity = it) {
                        navToPostingDetail(it.id)
                    }
                }
            )

            item {
                Spacer(modifier = Modifier.height(100.dp))
            }
        }
    }
}

@Composable
fun LoungeToolbar(
    onClickSearch: () -> Unit,
    onClickAdd: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "라운지",
            style = TextStyle(
                color = Black3A,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 28.sp
            )
        )

        Spacer(modifier = Modifier.weight(1f))

        Image(
            modifier = Modifier
                .clickable { onClickSearch()  },
            painter = painterResource(id = R.drawable.search) ,
            contentDescription = ""
        )

        Image(
            modifier = Modifier
                .padding(start = 16.dp)
                .clickable { onClickAdd() },
            painter = painterResource(id = R.drawable.add) ,
            contentDescription = ""
        )
    }
}


@Composable
fun LoungeSortView(
    onClickSort: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.End
    ) {

        Image(
            modifier = Modifier
                .clickable { onClickSort()  },
            painter = painterResource(id = R.drawable.ic_sorting) ,
            contentDescription = ""
        )

        Spacer(modifier = Modifier.width(8.dp))

        Text(
            text = "정렬 기준",
            style = TextStyle(
                color = Gray6F,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 12.sp
            )
        )

        Spacer(modifier = Modifier.width(5.dp))

        Text(
            text = "최신순",
            style = TextStyle(
                color = Green12,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 12.sp
            )
        )

    }
}