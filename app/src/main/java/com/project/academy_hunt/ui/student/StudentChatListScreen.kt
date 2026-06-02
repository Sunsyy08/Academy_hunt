package com.project.academy_hunt.ui.student

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

data class ChatRoomItem(
    val id          : Int,
    val academyName : String,
    val lastMessage : String,
    val lastTime    : String,
    val unreadCount : Int
)

private val dummyChatRooms = listOf(
    ChatRoomItem(1, "강남수학학원",   "네, 다음 주 월요일에 체험 수업 가능합니다!", "오후 3:24", 2),
    ChatRoomItem(2, "스카이영어학원", "감사합니다. 확인 후 연락드릴게요.",         "오후 1:10", 0),
    ChatRoomItem(3, "대치과학학원",   "커리큘럼 자료 보내드렸습니다. 확인해보세요.", "어제",      1),
    ChatRoomItem(4, "종로영어학원",   "안녕하세요! 문의 주셔서 감사합니다.",         "어제",      0),
)

@Composable
fun StudentChatListScreen(
    onChatClick: (Int) -> Unit = {}
) {
    var searchQuery by remember { mutableStateOf("") }

    val filtered = if (searchQuery.isBlank()) dummyChatRooms
    else dummyChatRooms.filter { it.academyName.contains(searchQuery) }

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
                    modifier  = Modifier.fillMaxWidth(),
                    shape     = RoundedCornerShape(12.dp),
                    singleLine = true,
                    colors    = OutlinedTextFieldDefaults.colors(
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
                        text     = "제안을 수락하면 채팅방이 생성돼요",
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
                    ChatRoomListItem(
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
private fun ChatRoomListItem(
    chatRoom: ChatRoomItem,
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
        // 아바타
        Box(
            modifier         = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .background(Blue600.copy(alpha = 0.12f)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text       = chatRoom.academyName.first().toString(),
                fontSize   = 18.sp,
                fontWeight = FontWeight.Bold,
                color      = Blue600
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text       = chatRoom.academyName,
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
fun StudentChatListScreenPreview() {
    AcademyHuntTheme {
        StudentChatListScreen()
    }
}