package com.dayj.dayj.lounge.posting

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.dayj.dayj.DateUtils.calculateUpdatedAgo
import com.dayj.dayj.R
import com.dayj.dayj.ui.theme.Black2A
import com.dayj.dayj.ui.theme.Black3A
import com.dayj.dayj.ui.theme.Gray6F
import com.dayj.dayj.ui.theme.Gray9A
import com.dayj.dayj.ui.theme.GrayCB
import com.dayj.dayj.ui.theme.GrayE3
import com.dayj.dayj.ui.theme.Green12
import com.dayj.dayj.ui.theme.LightGrayAB
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun LoungePostingScreen(
    postingId: Int,
    navHostController: NavHostController,
    viewModel: LoungePostingViewModel = hiltViewModel()
) {
    val posting = viewModel.posting.collectAsState().value
    val keyboardController = LocalSoftwareKeyboardController.current
    val scrollState = rememberScrollState()
    val coroutineScope = rememberCoroutineScope()
    val nameHiddenChecked = viewModel.nameHiddenChecked.collectAsState().value

    LaunchedEffect(key1 = Unit) {
        viewModel.updatePostingId(postingId)
    }

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
                .scrollable(
                    state = scrollState,
                    orientation = Orientation.Vertical
                ),
        ) {
            item {
                if (posting != null) {
                    Box(
                        modifier = Modifier
                            .padding(horizontal = 20.dp, vertical = 20.dp)
                            .fillMaxWidth(),
                    ) {
                        Image(
                            modifier = Modifier
                                .align(Alignment.CenterStart)
                                .clickable {
                                    navHostController.popBackStack()
                                },
                            painter = painterResource(id = R.drawable.left_arrow_black),
                            contentDescription = ""
                        )

                        Text(
                            modifier = Modifier
                                .align(Alignment.Center),
                            text = posting.tags.first().tagText,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            color = Black3A
                        )
                    }

                    PostingItem(posting = posting)

                    Spacer(modifier = Modifier.height(12.dp))

                    CommentListView(posting.comments)

                    Spacer(modifier = Modifier.height(12.dp))

                    CommentInputView(
                        onCommentChanged = {
                            viewModel.updateCommentWritten(it)
                        },
                        nameHiddenChecked = nameHiddenChecked,
                        onClickNameHidden = { checked ->
                            viewModel.updateNameHiddenChecked(checked)
                        },
                        onClickSend = {
                            viewModel.uploadComment()
                            coroutineScope.launch {
                                delay(500L)
                                scrollState.scrollTo(scrollState.maxValue)
                            }
                        }
                    )
                } else {
                    Box(modifier = Modifier.height(300.dp)) {
                        CircularProgressIndicator()
                    }
                }
            }

        }
    }
}

@Composable
fun PostingItem(
    posting: LoungePostingEntity
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .background(
                color = Color.White,
                shape = RoundedCornerShape(16.dp)
            )
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 12.dp, vertical = 6.dp)
        ) {
            Row(
                modifier = Modifier,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    modifier = Modifier
                        .size(24.dp),
                    painter = painterResource(id = R.drawable.account_circle),
                    contentDescription = ""
                )
                
                Spacer(modifier = Modifier.width(5.dp))
                
                Text(
                    modifier = Modifier,
                    text = if(posting.isNameHidden) "익명" else posting.userName,
                    fontWeight = FontWeight.Normal,
                    fontSize = 12.sp,
                    color = Black3A
                )

                Text(
                    modifier = Modifier,
                    text = "・",
                    fontWeight = FontWeight.Normal,
                    fontSize = 12.sp,
                    color = Black3A
                )

                Text(
                    modifier = Modifier,
                    text = posting.createdDate.calculateUpdatedAgo(),
                    fontWeight = FontWeight.Normal,
                    fontSize = 12.sp,
                    color = Gray6F
                )
            }

            Spacer(modifier = Modifier.height(7.dp))

            Text(
                modifier = Modifier,
                text = posting.title,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                color = Black3A
            )

            Spacer(modifier = Modifier.height(7.dp))

            Text(
                modifier = Modifier,
                text = posting.content,
                fontWeight = FontWeight.Medium,
                fontSize = 12.sp,
                color = Black3A
            )

            Spacer(modifier = Modifier.height(7.dp))

            LazyRow(
                modifier = Modifier,
                horizontalArrangement = Arrangement.spacedBy(13.dp)
            ) {
               items(
                   items = posting.images,
                   itemContent = {
                       AsyncImage(
                           modifier = Modifier
                               .size(70.dp)
                               .clip(RoundedCornerShape(5.dp)),
                           model = it,
                           contentDescription = null
                       )
                   }
               )
            }

            Spacer(modifier = Modifier.height(7.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
               Image(
                   painter = painterResource(id = R.drawable.thumb_up),
                   contentDescription = ""
               )

                Text(
                    modifier = Modifier
                        .padding(start = 3.dp),
                    text = posting.likeCount.toString(),
                    fontWeight = FontWeight.Medium,
                    fontSize = 12.sp,
                    color = Black3A
                )

                Image(
                    modifier = Modifier
                        .padding(start = 3.dp),
                    painter = painterResource(id = R.drawable.ic_comment),
                    contentDescription = ""
                )

                Text(
                    modifier = Modifier
                        .padding(start = 3.dp),
                    text = posting.comments.size.toString(),
                    fontWeight = FontWeight.Medium,
                    fontSize = 12.sp,
                    color = Black3A
                )
            }
        }
    }
}

@Composable
fun CommentListView(
    comments: List<CommentEntity>
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .background(
                color = Color.White,
                shape = RoundedCornerShape(16.dp)
            )
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 12.dp, vertical = 6.dp)
        ) {
            Text(
                text = "댓글 ${comments.size}",
                fontWeight = FontWeight.Medium,
                fontSize = 12.sp,
                color = Black2A
            )

            Spacer(modifier = Modifier.height(5.dp))

            Box(
               modifier = Modifier
                   .height(1.dp)
                   .fillMaxWidth()
                   .background(shape = RectangleShape, color = LightGrayAB)
           )

            Spacer(modifier = Modifier.height(5.dp))

            comments.forEach {
                Column {
                    CommentItem(it)
                    Spacer(modifier = Modifier.height(8.dp))
                }

            }
        }
    }
}

@Composable
fun CommentItem(
    comment: CommentEntity
) {
    Row(
        modifier = Modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            modifier = Modifier
                .size(24.dp),
            painter = painterResource(id = R.drawable.account_circle),
            contentDescription = ""
        )

        Spacer(modifier = Modifier.width(5.dp))

        Text(
            modifier = Modifier,
            text = if(comment.isNameHidden) "익명" else comment.userName,
            fontWeight = FontWeight.Bold,
            fontSize = 12.sp,
            color = Black3A
        )

        Text(
            modifier = Modifier,
            text = "・",
            fontWeight = FontWeight.Normal,
            fontSize = 12.sp,
            color = Black3A
        )

        Text(
            modifier = Modifier,
            text = comment.createdDate.calculateUpdatedAgo(),
            fontWeight = FontWeight.Normal,
            fontSize = 12.sp,
            color = Gray6F
        )
    }

    Spacer(modifier = Modifier.height(5.dp))

    Text(
        modifier = Modifier,
        text = comment.comment,
        fontWeight = FontWeight.Medium,
        fontSize = 12.sp,
        color = Black2A
    )
}


@Composable
fun BoxScope.CommentInputView(
    nameHiddenChecked: Boolean,
    onClickNameHidden: (Boolean) -> Unit,
    onCommentChanged: (String) -> Unit,
    onClickSend: () -> Unit
) {
    val commentWritten = remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .padding(start = 20.dp, end = 20.dp, top = 0.dp, bottom = 30.dp)
            .background(color = GrayE3, shape = RoundedCornerShape(8.dp))
            .align(Alignment.BottomCenter)
    ) {
        Row(
            modifier = Modifier
                .padding(vertical = 13.dp, horizontal = 14.dp)
        ) {

            Image(
                modifier = Modifier.clickable {
                    onClickNameHidden(!nameHiddenChecked)
                },
                painter = painterResource(
                    id = if(nameHiddenChecked) R.drawable.check_box_selected else R.drawable.check_box_outline_blank),
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

            Spacer(modifier = Modifier.width(4.dp))

            BasicTextField(
                modifier = Modifier
                    .weight(1f),
                value = commentWritten.value,
                textStyle = TextStyle(fontSize = 14.sp),
                onValueChange = {
                    onCommentChanged(it)
                    commentWritten.value = it
                },
                decorationBox = { innerTextField ->
                    if(commentWritten.value.isEmpty()) {
                        Text(
                            modifier = Modifier,
                            text = "댓글을 입력하세요.",
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                            color = Gray9A
                        )
                    }
                    innerTextField()
                }
            )

            Image(
                modifier = Modifier
                    .width(20.dp)
                    .height(20.dp)
                    .clickable {
                        onClickSend()
                        commentWritten.value = ""
                    },
                painter = painterResource(id = R.drawable.ic_send),
                contentScale = ContentScale.Inside,
                contentDescription = ""
            )
        }
    }
}