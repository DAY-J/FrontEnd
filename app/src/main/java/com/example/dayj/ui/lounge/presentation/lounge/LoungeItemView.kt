package com.example.dayj.ui.lounge.presentation.lounge

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dayj.R
import com.example.dayj.ui.lounge.domain.entity.LoungePostingEntity
import com.example.dayj.ui.theme.Black3A
import com.example.dayj.ui.theme.Gray6F
import com.example.dayj.ui.theme.Green12
import com.example.dayj.util.DateUtils.calculateUpdatedAgo

@Composable
fun LoungeItemView(
    loungePostingEntity: LoungePostingEntity,
    onClickItem: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = Color.White,
                shape = RoundedCornerShape(16.dp)
            )
            .clip(
                shape = RoundedCornerShape(16.dp)
            )
            .clickable {
                onClickItem()
            }
    ) {
        Column(
            modifier = Modifier
                .padding(
                    vertical = 15.dp,
                    horizontal = 16.dp
                )
        ) {
            Row(
                modifier = Modifier,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "${if(loungePostingEntity.isNameHidden) "익명" else loungePostingEntity.writerName} ・ ",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = Black3A
                )

                Text(
                    text = loungePostingEntity.createdDate.calculateUpdatedAgo(),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = Gray6F
                )
            }
            
            Spacer(modifier = Modifier.height(9.dp))

            Text(
                text = loungePostingEntity.title,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    modifier = Modifier,
                    painter = painterResource(id = R.drawable.thumb_up),
                    contentDescription = ""
                )

                Text(
                    modifier = Modifier
                        .padding(start = 3.dp),
                    text = loungePostingEntity.likeCount.toString(),
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
                    text = loungePostingEntity.comments.size.toString(),
                    fontWeight = FontWeight.Medium,
                    fontSize = 12.sp,
                    color = Black3A
                )

                Spacer(modifier = Modifier.weight(1f))
                Text(
                    modifier = Modifier
                        .padding(start = 2.dp),
                    text = "#${loungePostingEntity.tag.tagText}",
                    fontWeight = FontWeight.Normal,
                    fontSize = 10.sp,
                    color = Green12
                )
            }
        }
    }

}