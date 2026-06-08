package com.project.academy_hunt.ui.academy

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.project.academy_hunt.ui.theme.AcademyHuntTheme
import com.project.academy_hunt.ui.theme.Blue600
import com.project.academy_hunt.ui.theme.Gray100
import com.project.academy_hunt.ui.theme.Gray200
import com.project.academy_hunt.ui.theme.Gray400
import com.project.academy_hunt.ui.theme.Gray500
import com.project.academy_hunt.ui.theme.Gray900
import com.project.academy_hunt.ui.theme.White

private val dummyAcademyChatRooms = listOf(
    AcademyChatRoomItem(1, "홍길동 (고1·수학)", "네, 월요일 오후 5시 괜찮습니다!", "오후 3:24", 1),
    AcademyChatRoomItem(2, "김민수 (중3·수학)", "감사합니다. 내일 상담 가능할까요?",  "오후 1:10", 0),
    AcademyChatRoomItem(3, "이지영 (고2·영어)", "커리큘럼 자료 잘 받았습니다.",        "어제",      2),
    AcademyChatRoomItem(4, "박준혁 (초6·과학)", "체험 수업 신청하고 싶어요!",           "어제",      0),
)

data class AcademyChatRoomItem(
    val id          : Int,
    val studentInfo : String,
    val lastMessage : String,
    val lastTime    : String,
    val unreadCount : Int
)

@Composable
fun AcademyChatListScreen(
    onChatClick: (Int) -> Unit = {}
) {
    var searchQuery by remember { mutableStateOf("") }

    val filtered = if (searchQuery.isBlank()) dummyAcademyChatRooms
    else dummyAcademyChatRooms.filter { it.studentInfo.contains(searchQuery) }

    Scaffold(
        containerColor = Gray100,
        topBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(White)
                    .padding(horizontal = 16.dp, vertical = 12.dp)
            ) {
                Text(
                    text       = "채팅",
                    fontSize   = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color      = Gray900
                )
                Spacer(modifier = Modifier.height(10.dp))
                OutlinedTextField(
                    value         = searchQuery,
                    onValueChange = { searchQuery = it },
                    placeholder   = { Text("채팅방 검색", color = Gray400) },
                    leadingIcon   = {
                        Icon(
                            imageVector        = Icons.Default.Search,
                            contentDescription = null,
                            tint               = Gray400
                        )
                    },
                    modifier   = Modifier.fillMaxWidth(),
                    shape      = RoundedCornerShape(12.dp),
                    singleLine = true,
                    colors     = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor      = Blue600,
                        unfocusedBorderColor    = Gray200,
                        focusedContainerColor   = White,
                        unfocusedContainerColor = Gray100
                    )
                )
            }
        }
    ) { innerPadding ->
        if (filtered.isEmpty()) {
            Box(
                modifier         = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = "💬", fontSize = 48.sp)
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text     = "채팅방이 없어요",
                        fontSize = 16.sp,
                        color    = Gray500
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text     = "학생이 제안을 수락하면 채팅방이 생성돼요",
                        fontSize = 13.sp,
                        color    = Gray400
                    )
                }
            }
        } else {
            LazyColumn(
                modifier       = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentPadding = PaddingValues(vertical = 8.dp)
            ) {
                items(filtered) { chatRoom ->
                    AcademyChatRoomListItem(
                        chatRoom = chatRoom,
                        onClick  = { onChatClick(chatRoom.id) }
                    )
                    Divider(
                        modifier  = Modifier.padding(start = 76.dp),
                        color     = Gray100,
                        thickness = 1.dp
                    )
                }
            }
        }
    }
}

@Composable
private fun AcademyChatRoomListItem(
    chatRoom: AcademyChatRoomItem,
    onClick : () -> Unit
) {
    Row(
        modifier          = Modifier
            .fillMaxWidth()
            .background(White)
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier         = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .background(Blue600.copy(alpha = 0.12f)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text       = chatRoom.studentInfo.first().toString(),
                fontSize   = 18.sp,
                fontWeight = FontWeight.Bold,
                color      = Blue600
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text       = chatRoom.studentInfo,
                fontSize   = 15.sp,
                fontWeight = FontWeight.SemiBold,
                color      = Gray900
            )
            Spacer(modifier = Modifier.height(3.dp))
            Text(
                text     = chatRoom.lastMessage,
                fontSize = 13.sp,
                color    = Gray500,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }

        Spacer(modifier = Modifier.width(8.dp))

        Column(horizontalAlignment = Alignment.End) {
            Text(
                text     = chatRoom.lastTime,
                fontSize = 11.sp,
                color    = Gray400
            )
            Spacer(modifier = Modifier.height(4.dp))
            if (chatRoom.unreadCount > 0) {
                Box(
                    modifier         = Modifier
                        .size(20.dp)
                        .clip(CircleShape)
                        .background(Blue600),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text       = chatRoom.unreadCount.toString(),
                        fontSize   = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color      = White
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AcademyChatListScreenPreview() {
    AcademyHuntTheme {
        AcademyChatListScreen()
    }
}