package com.dayj.dayj.lounge.write

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.dayj.dayj.R
import com.dayj.dayj.lounge.LoungeTag
import com.dayj.dayj.lounge.LoungeTagEnum
import com.dayj.dayj.ui.theme.Black3A
import com.dayj.dayj.ui.theme.Gray9A
import com.dayj.dayj.ui.theme.Green12
import com.dayj.dayj.ui.theme.TextBlack

@Composable
fun PostWritingScreen(
    navHostController: NavHostController,
    viewModel: PostWritingViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val keyboardController = LocalSoftwareKeyboardController.current
    val scrollState = rememberScrollState()
    val title = viewModel.title.collectAsState().value
    val content = viewModel.content.collectAsState().value
    val nameHiddenChecked = viewModel.nameHiddenChecked.collectAsState().value
    val selectedTag = viewModel.selectedTag.collectAsState().value

    Box(
        modifier = Modifier
            .fillMaxSize()
            .clickable {
                keyboardController?.hide()
            }
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp)
                .scrollable(state = scrollState, orientation = Orientation.Vertical),
        ) {
            item {
                Row(
                    modifier = Modifier
                        .padding(vertical = 20.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        modifier = Modifier
                            .clickable {
                                navHostController.popBackStack()
                            },
                        painter = painterResource(id = R.drawable.left_arrow_black),
                        contentDescription = ""
                    )

                    Spacer(modifier = Modifier.padding(end = 5.dp))

                    Text(
                        modifier = Modifier,
                        text = "글 작성하기",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = Black3A
                    )

                    Spacer(modifier = Modifier.weight(1f))

                    Text(
                        modifier = Modifier
                            .clickable {
                                if(title.isEmpty()) {
                                    Toast.makeText(context, "제목을 입력해주세요.", Toast.LENGTH_SHORT).show()
                                } else {
                                    viewModel.completeWriting { success ->
                                        if(success) {
                                            Toast.makeText(context, "게시글 작성을 완료하였습니다.", Toast.LENGTH_SHORT).show()
                                            navHostController.popBackStack()
                                        } else {
                                            Toast.makeText(context, "게시글 작성을 완료하지 못했습니다.", Toast.LENGTH_SHORT).show()

                                        }
                                    }
                                }
                            },
                        text = "완료",
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        color = Green12
                    )
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            color = Color.White,
                            shape = RoundedCornerShape(10.dp)
                        )
                        .padding(
                            vertical = 13.dp,
                            horizontal = 14.dp
                        )
                ) {
                    BasicTextField(
                        value = title,
                        singleLine = true,
                        textStyle = TextStyle.Default
                            .copy(
                                color = TextBlack,
                                fontSize = 14.sp
                            ),
                        decorationBox = { innerTextField ->
                            if(title.isEmpty()) {
                                Text(
                                    text = "제목을 입력하세요.",
                                    color = Gray9A,
                                    fontWeight = FontWeight.Medium,
                                    fontSize = 14.sp
                                )
                            }
                            innerTextField()
                        },
                        onValueChange = {
                            viewModel.updateTitle(it)
                        }
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            color = Color.White,
                            shape = RoundedCornerShape(10.dp)
                        )
                        .padding(
                            vertical = 13.dp,
                            horizontal = 14.dp
                        )
                ) {
                    LazyRow(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        items(
                            items = LoungeTagEnum.values()
                                .filter { it != LoungeTagEnum.ALL && it != LoungeTagEnum.POPULAR },
                            itemContent = {
                                Box(
                                    modifier = Modifier
                                        .padding(end =  8.dp)
                                ) {
                                    LoungeTag(selected = selectedTag == it, tagText = it.tagText) {
                                        viewModel.updateSelectedTag(it)
                                    }
                                }
                            }
                        )
                    }

                    Spacer(modifier = Modifier.height(15.dp))

                    BasicTextField(
                        modifier = Modifier
                            .height(120.dp)
                            .scrollable(
                                state = rememberScrollState(),
                                orientation = Orientation.Vertical
                            ),
                        value = content,
                        textStyle = TextStyle.Default
                            .copy(
                                color = TextBlack,
                                fontSize = 14.sp
                            ),
                        decorationBox = { innerTextField ->
                            if(content.isEmpty()) {
                                Text(
                                    text = "내용을 입력하세요.",
                                    color = Gray9A,
                                    fontWeight = FontWeight.Medium,
                                    fontSize = 14.sp
                                )
                            }
                            innerTextField()
                        },
                        onValueChange = {
                            viewModel.updateContent(it)
                        }
                    )

                    Spacer(modifier = Modifier.height(15.dp))

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(1.dp)
                            .background(color = Gray9A)
                    )

                    Row(
                        modifier = Modifier
                            .padding(top = 13.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            modifier = Modifier.clickable {
                                viewModel.updateNameHiddenChecked(!nameHiddenChecked)
                            },
                            painter = painterResource(
                                id = if(nameHiddenChecked) R.drawable.check_box_selected else R.drawable.check_box_outline_blank
                            ),
                            contentDescription = ""
                        )

                        Spacer(modifier = Modifier.width(3.dp))

                        Text(
                            modifier = Modifier,
                            text = "익명",
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp,
                            color = Green12
                        )
                    }
                }
            }
        }
    }
}