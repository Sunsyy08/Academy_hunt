package com.project.academy_hunt.ui.academy

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
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

private val dummyAcademyMessages = listOf(
    AcademyMessageItem(1, "안녕하세요! 제안서 잘 받았습니다.", true,  "오후 2:10"),
    AcademyMessageItem(2, "네, 안녕하세요! 관심 가져주셔서 감사합니다 😊", false, "오후 2:11"),
    AcademyMessageItem(3, "체험 수업이 가능한지 여쭤봐도 될까요?", true,  "오후 2:12"),
    AcademyMessageItem(4, "물론이죠! 다음 주 월요일이나 수요일 어떠세요?", false, "오후 2:13"),
    AcademyMessageItem(5, "월요일 오후 5시 가능할 것 같아요!", true,  "오후 2:15"),
    AcademyMessageItem(6, "네 좋습니다! 월요일 오후 5시로 예약해드릴게요.", false, "오후 2:16"),
)

data class AcademyMessageItem(
    val id     : Int,
    val content: String,
    val isMe   : Boolean,
    val time   : String
)

@Composable
fun AcademyChatRoomScreen(
    chatRoomId  : Int    = 1,
    studentInfo : String = "홍길동 (고1·수학)",
    onBack      : () -> Unit = {}
) {
    var inputText by remember { mutableStateOf("") }
    val messages   = remember { mutableStateListOf(*dummyAcademyMessages.toTypedArray()) }
    val listState  = rememberLazyListState()

    LaunchedEffect(messages.size) {
        if (messages.isNotEmpty()) {
            listState.animateScrollToItem(messages.size - 1)
        }
    }

    Scaffold(
        containerColor = Gray100,
        topBar = {
            Row(
                modifier          = Modifier
                    .fillMaxWidth()
                    .background(White)
                    .padding(horizontal = 8.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onBack) {
                    Icon(
                        imageVector        = Icons.Default.ArrowBack,
                        contentDescription = "뒤로가기",
                        tint               = Gray900
                    )
                }
                Box(
                    modifier         = Modifier
                        .size(36.dp)
                        .clip(CircleShape)
                        .background(Blue600.copy(alpha = 0.12f)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text       = studentInfo.first().toString(),
                        fontSize   = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color      = Blue600
                    )
                }
                Spacer(modifier = Modifier.width(10.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text       = studentInfo,
                        fontSize   = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color      = Gray900
                    )
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(7.dp)
                                .clip(CircleShape)
                                .background(Color(0xFF16A34A))
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text     = "온라인",
                            fontSize = 11.sp,
                            color    = Gray500
                        )
                    }
                }
            }
        },
        bottomBar = {
            Row(
                modifier          = Modifier
                    .fillMaxWidth()
                    .background(White)
                    .padding(horizontal = 12.dp, vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value         = inputText,
                    onValueChange = { inputText = it },
                    placeholder   = { Text("메시지를 입력하세요", color = Gray400) },
                    modifier      = Modifier.weight(1f),
                    shape         = RoundedCornerShape(24.dp),
                    singleLine    = true,
                    colors        = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor      = Blue600,
                        unfocusedBorderColor    = Gray200,
                        focusedContainerColor   = White,
                        unfocusedContainerColor = Gray100
                    )
                )
                Spacer(modifier = Modifier.width(8.dp))
                Box(
                    modifier         = Modifier
                        .size(46.dp)
                        .clip(CircleShape)
                        .background(if (inputText.isNotBlank()) Blue600 else Gray200),
                    contentAlignment = Alignment.Center
                ) {
                    IconButton(
                        onClick = {
                            if (inputText.isNotBlank()) {
                                messages.add(
                                    AcademyMessageItem(
                                        id      = messages.size + 1,
                                        content = inputText,
                                        isMe    = false,
                                        time    = "방금"
                                    )
                                )
                                inputText = ""
                            }
                        }
                    ) {
                        Icon(
                            imageVector        = Icons.Default.Send,
                            contentDescription = "전송",
                            tint               = White,
                            modifier           = Modifier.size(20.dp)
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        LazyColumn(
            state               = listState,
            modifier            = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentPadding      = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(messages) { message ->
                AcademyMessageBubble(message = message)
            }
        }
    }
}

@Composable
private fun AcademyMessageBubble(message: AcademyMessageItem) {
    Row(
        modifier              = Modifier.fillMaxWidth(),
        horizontalArrangement = if (message.isMe) Arrangement.Start else Arrangement.End
    ) {
        if (message.isMe) {
            Column(horizontalAlignment = Alignment.Start) {
                Box(
                    modifier = Modifier
                        .widthIn(max = 260.dp)
                        .clip(
                            RoundedCornerShape(
                                topStart    = 4.dp,
                                topEnd      = 16.dp,
                                bottomStart = 16.dp,
                                bottomEnd   = 16.dp
                            )
                        )
                        .background(White)
                        .padding(horizontal = 14.dp, vertical = 10.dp)
                ) {
                    Text(
                        text       = message.content,
                        fontSize   = 14.sp,
                        color      = Gray900,
                        lineHeight = 20.sp
                    )
                }
                Spacer(modifier = Modifier.height(3.dp))
                Text(
                    text     = message.time,
                    fontSize = 10.sp,
                    color    = Gray400
                )
            }
        } else {
            Column(horizontalAlignment = Alignment.End) {
                Box(
                    modifier = Modifier
                        .widthIn(max = 260.dp)
                        .clip(
                            RoundedCornerShape(
                                topStart    = 16.dp,
                                topEnd      = 4.dp,
                                bottomStart = 16.dp,
                                bottomEnd   = 16.dp
                            )
                        )
                        .background(Blue600)
                        .padding(horizontal = 14.dp, vertical = 10.dp)
                ) {
                    Text(
                        text       = message.content,
                        fontSize   = 14.sp,
                        color      = White,
                        lineHeight = 20.sp
                    )
                }
                Spacer(modifier = Modifier.height(3.dp))
                Text(
                    text     = message.time,
                    fontSize = 10.sp,
                    color    = Gray400
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AcademyChatRoomScreenPreview() {
    AcademyHuntTheme {
        AcademyChatRoomScreen()
    }
}