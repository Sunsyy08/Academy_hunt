package com.project.academy_hunt.ui.academy

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Assignment
import androidx.compose.material.icons.filled.Forum
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.project.academy_hunt.ui.theme.AcademyHuntTheme
import com.project.academy_hunt.ui.theme.Blue600
import com.project.academy_hunt.ui.theme.Blue700
import com.project.academy_hunt.ui.theme.Gray100
import com.project.academy_hunt.ui.theme.Gray200
import com.project.academy_hunt.ui.theme.Gray400
import com.project.academy_hunt.ui.theme.Gray500
import com.project.academy_hunt.ui.theme.Gray900
import com.project.academy_hunt.ui.theme.Success
import com.project.academy_hunt.ui.theme.Warning
import com.project.academy_hunt.ui.theme.White

data class StudentCardItem(
    val id            : Int,
    val grade         : String,
    val level         : String,
    val subjects      : List<String>,
    val location      : String,
    val availableTimes: String
)

private val dummyStudents = listOf(
    StudentCardItem(1, "고1", "중급", listOf("수학", "영어"), "강남구", "월 수 금 18:00"),
    StudentCardItem(2, "중3", "초급", listOf("수학"),         "서초구", "화 목 17:00"),
    StudentCardItem(3, "고2", "고급", listOf("영어", "국어"), "송파구", "월 수 19:00"),
)

@Composable
fun AcademyHomeScreen(
    academyName    : String = "강남수학학원",
    onStudentClick : (Int) -> Unit = {},
    onNavHome      : () -> Unit = {},
    onNavStudents  : () -> Unit = {},
    onNavProposals : () -> Unit = {},
    onNavChat      : () -> Unit = {},
    onNavMyPage    : () -> Unit = {}
) {
    var selectedNav by remember { mutableStateOf(0) }

    Scaffold(
        containerColor = Gray100,
        bottomBar = {
            AcademyBottomNav(
                selected       = selectedNav,
                onSelect       = { selectedNav = it },
                onNavHome      = onNavHome,
                onNavStudents  = onNavStudents,
                onNavProposals = onNavProposals,
                onNavChat      = onNavChat,
                onNavMyPage    = onNavMyPage
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier       = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentPadding = PaddingValues(bottom = 16.dp)
        ) {
            // 상단 앱바
            item { AcademyTopBar() }

            // 환영 배너
            item { AcademyWelcomeBanner(academyName = academyName) }

            // 통계 카드
            item {
                Row(
                    modifier              = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    AcademyStatCard(modifier = Modifier.weight(1f), label = "보낸 제안", value = "12")
                    AcademyStatCard(modifier = Modifier.weight(1f), label = "수락됨",   value = "5")
                    AcademyStatCard(modifier = Modifier.weight(1f), label = "대기중",   value = "7")
                }
            }

            // 섹션 타이틀
            item {
                Row(
                    modifier          = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text       = "매칭 가능한 학생",
                        fontSize   = 17.sp,
                        fontWeight = FontWeight.Bold,
                        color      = Gray900,
                        modifier   = Modifier.weight(1f)
                    )
                    TextButton(onClick = onNavStudents) {
                        Text(
                            text     = "더보기",
                            fontSize = 13.sp,
                            color    = Blue600
                        )
                    }
                }
            }

            // 학생 카드 목록
            items(dummyStudents) { student ->
                AcademyStudentCard(
                    student = student,
                    onClick = { onStudentClick(student.id) }
                )
                Spacer(modifier = Modifier.height(10.dp))
            }
        }
    }
}

@Composable
private fun AcademyTopBar() {
    Row(
        modifier          = Modifier
            .fillMaxWidth()
            .background(White)
            .padding(horizontal = 16.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier         = Modifier
                .size(32.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(Blue600),
            contentAlignment = Alignment.Center
        ) {
            Text("A", color = White, fontWeight = FontWeight.Bold, fontSize = 16.sp)
        }
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text       = "Academy Hunt",
            fontSize   = 17.sp,
            fontWeight = FontWeight.Bold,
            color      = Gray900
        )
        Spacer(modifier = Modifier.weight(1f))
        Box {
            Icon(
                imageVector        = Icons.Default.Notifications,
                contentDescription = "알림",
                tint               = Gray500,
                modifier           = Modifier.size(26.dp)
            )
            Box(
                modifier = Modifier
                    .size(8.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFEF4444))
                    .align(Alignment.TopEnd)
            )
        }
    }
}

@Composable
private fun AcademyWelcomeBanner(academyName: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(
                Brush.horizontalGradient(
                    colors = listOf(Blue600, Blue700)
                )
            )
            .padding(20.dp)
    ) {
        Column {
            Text(
                text       = "${academyName} 님",
                fontSize   = 18.sp,
                fontWeight = FontWeight.Bold,
                color      = White
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text     = "오늘도 좋은 제안을 해보세요 🎯",
                fontSize = 13.sp,
                color    = White.copy(alpha = 0.85f)
            )
        }
    }
}

@Composable
private fun AcademyStatCard(
    modifier: Modifier = Modifier,
    label   : String,
    value   : String
) {
    Card(
        modifier  = modifier,
        shape     = RoundedCornerShape(12.dp),
        colors    = CardDefaults.cardColors(containerColor = White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(
            modifier            = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text       = value,
                fontSize   = 22.sp,
                fontWeight = FontWeight.Bold,
                color      = Blue600
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text     = label,
                fontSize = 11.sp,
                color    = Gray500
            )
        }
    }
}

@Composable
private fun AcademyStudentCard(
    student: StudentCardItem,
    onClick: () -> Unit
) {
    Card(
        onClick   = onClick,
        modifier  = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        shape     = RoundedCornerShape(14.dp),
        colors    = CardDefaults.cardColors(containerColor = White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            modifier          = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // 아바타
            Box(
                modifier         = Modifier
                    .size(44.dp)
                    .clip(CircleShape)
                    .background(Blue600.copy(alpha = 0.12f)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text       = student.grade,
                    fontSize   = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color      = Blue600
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                // 학년 + 수준
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier         = Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .background(Blue600.copy(alpha = 0.08f))
                            .padding(horizontal = 8.dp, vertical = 3.dp)
                    ) {
                        Text(
                            text       = "${student.grade} · ${student.level}",
                            fontSize   = 12.sp,
                            fontWeight = FontWeight.SemiBold,
                            color      = Blue600
                        )
                    }
                }

                Spacer(modifier = Modifier.height(6.dp))

                // 과목 칩
                Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    student.subjects.forEach { subject ->
                        Box(
                            modifier         = Modifier
                                .clip(RoundedCornerShape(6.dp))
                                .background(Gray100)
                                .padding(horizontal = 7.dp, vertical = 2.dp)
                        ) {
                            Text(text = subject, fontSize = 11.sp, color = Gray500)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(6.dp))

                // 위치 + 시간
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text     = "📍 ${student.location}",
                        fontSize = 12.sp,
                        color    = Gray500
                    )
                    Text(
                        text     = "  ·  ",
                        fontSize = 12.sp,
                        color    = Gray400
                    )
                    Text(
                        text     = "🕐 ${student.availableTimes}",
                        fontSize = 12.sp,
                        color    = Gray500
                    )
                }
            }

            // 제안하기 버튼
            OutlinedButton(
                onClick        = onClick,
                shape          = RoundedCornerShape(8.dp),
                contentPadding = PaddingValues(horizontal = 10.dp, vertical = 6.dp),
                colors         = ButtonDefaults.outlinedButtonColors(
                    containerColor = White,
                    contentColor   = Blue600
                ),
                border = ButtonDefaults.outlinedButtonBorder.copy(width = 1.dp)
            ) {
                Text(
                    text       = "제안하기",
                    fontSize   = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    color      = Blue600
                )
            }
        }
    }
}

@Composable
private fun AcademyBottomNav(
    selected      : Int,
    onSelect      : (Int) -> Unit,
    onNavHome     : () -> Unit,
    onNavStudents : () -> Unit,
    onNavProposals: () -> Unit,
    onNavChat     : () -> Unit,
    onNavMyPage   : () -> Unit
) {
    val items = listOf(
        Triple("홈",       Icons.Default.Home,       onNavHome),
        Triple("학생목록", Icons.Default.Person,     onNavStudents),
        Triple("제안현황", Icons.Default.Assignment, onNavProposals),
        Triple("채팅",      Icons.Default.Forum,      onNavChat),
        Triple("마이페이지", Icons.Default.Person,    onNavMyPage)
    )

    NavigationBar(
        containerColor = White,
        tonalElevation = 0.dp
    ) {
        items.forEachIndexed { index, (label, icon, action) ->
            NavigationBarItem(
                selected = selected == index,
                onClick  = { onSelect(index); action() },
                icon     = {
                    Icon(
                        imageVector        = icon,
                        contentDescription = label,
                        modifier           = Modifier.size(22.dp)
                    )
                },
                label  = { Text(text = label, fontSize = 10.sp) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor   = Blue600,
                    selectedTextColor   = Blue600,
                    unselectedIconColor = Gray400,
                    unselectedTextColor = Gray400,
                    indicatorColor      = Blue600.copy(alpha = 0.1f)
                )
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AcademyHomeScreenPreview() {
    AcademyHuntTheme {
        AcademyHomeScreen()
    }
}