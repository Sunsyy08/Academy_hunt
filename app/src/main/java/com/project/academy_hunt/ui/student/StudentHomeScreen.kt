package com.project.academy_hunt.ui.student

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
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
import com.project.academy_hunt.ui.theme.AppError
import com.project.academy_hunt.ui.theme.Blue600
import com.project.academy_hunt.ui.theme.Blue700
import com.project.academy_hunt.ui.theme.Gray100
import com.project.academy_hunt.ui.theme.Gray200
import com.project.academy_hunt.ui.theme.Gray400
import com.project.academy_hunt.ui.theme.Gray500
import com.project.academy_hunt.ui.theme.Gray700
import com.project.academy_hunt.ui.theme.Gray900
import com.project.academy_hunt.ui.theme.Success
import com.project.academy_hunt.ui.theme.Warning
import com.project.academy_hunt.ui.theme.White

// ── 더미 데이터 ───────────────────────────────────────
data class ProposalItem(
    val id          : Int,
    val academyName : String,
    val subjects    : List<String>,
    val tuitionFee  : String,
    val matchScore  : Int,
    val status      : String   // "검토중" | "수락됨" | "거절됨"
)

private val dummyProposals = listOf(
    ProposalItem(1, "강남수학학원", listOf("수학", "영어"), "월 300,000원", 85, "검토중"),
    ProposalItem(2, "스카이영어학원", listOf("영어"),        "월 250,000원", 72, "수락됨"),
    ProposalItem(3, "대치과학학원",   listOf("과학", "수학"), "월 280,000원", 65, "거절됨"),
)

// ── 메인 화면 ─────────────────────────────────────────
@Composable
fun StudentHomeScreen(
    userName      : String = "홍길동",
    conditionCount: Int    = 2,
    onConditionClick: () -> Unit = {},
    onProposalClick : (Int) -> Unit = {},
    onNavHome       : () -> Unit = {},
    onNavProposals  : () -> Unit = {},
    onNavChat       : () -> Unit = {},
    onNavMyPage     : () -> Unit = {}
) {
    var selectedNav by remember { mutableStateOf(0) }

    Scaffold(
        containerColor = Gray100,
        bottomBar = {
            StudentBottomNav(
                selected  = selectedNav,
                onSelect  = { selectedNav = it },
                onNavHome      = onNavHome,
                onNavProposals = onNavProposals,
                onNavChat      = onNavChat,
                onNavMyPage    = onNavMyPage
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier            = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentPadding      = PaddingValues(bottom = 16.dp)
        ) {
            // 상단 앱바
            item { TopBar() }

            // 환영 배너
            item {
                WelcomeBanner(
                    userName       = userName,
                    conditionCount = conditionCount,
                    onConditionClick = onConditionClick
                )
            }

            // 섹션 타이틀
            item {
                Row(
                    modifier         = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text       = "새로 받은 제안",
                        fontSize   = 17.sp,
                        fontWeight = FontWeight.Bold,
                        color      = Gray900
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    // 카운트 뱃지
                    Box(
                        modifier         = Modifier
                            .size(22.dp)
                            .clip(CircleShape)
                            .background(Blue600),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text     = dummyProposals.size.toString(),
                            color    = White,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            // 제안 카드 목록
            items(dummyProposals) { proposal ->
                ProposalCard(
                    proposal = proposal,
                    onClick  = { onProposalClick(proposal.id) }
                )
                Spacer(modifier = Modifier.height(10.dp))
            }
        }
    }
}

// ── 상단 앱바 ─────────────────────────────────────────
@Composable
private fun TopBar() {
    Row(
        modifier          = Modifier
            .fillMaxWidth()
            .background(White)
            .padding(horizontal = 16.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // 로고
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
        // 알림 아이콘
        Box {
            Icon(
                imageVector        = Icons.Default.Notifications,
                contentDescription = "알림",
                tint               = Gray500,
                modifier           = Modifier.size(26.dp)
            )
            // 빨간 점
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

// ── 환영 배너 ─────────────────────────────────────────
@Composable
private fun WelcomeBanner(
    userName        : String,
    conditionCount  : Int,
    onConditionClick: () -> Unit
) {
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
                text       = "안녕하세요, ${userName}님 👋",
                fontSize   = 18.sp,
                fontWeight = FontWeight.Bold,
                color      = White
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text     = "등록된 수강 조건 ${conditionCount}개",
                fontSize = 13.sp,
                color    = White.copy(alpha = 0.85f)
            )
            Spacer(modifier = Modifier.height(14.dp))
            OutlinedButton(
                onClick      = onConditionClick,
                shape        = RoundedCornerShape(8.dp),
                border       = ButtonDefaults.outlinedButtonBorder.copy(width = 1.dp),
                colors       = ButtonDefaults.outlinedButtonColors(
                    containerColor = White.copy(alpha = 0.15f),
                    contentColor   = White
                ),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Text(
                    text       = "조건 확인하기",
                    fontSize   = 13.sp,
                    fontWeight = FontWeight.Medium,
                    color      = White
                )
            }
        }
    }
}

// ── 제안 카드 ─────────────────────────────────────────
@Composable
private fun ProposalCard(
    proposal: ProposalItem,
    onClick : () -> Unit
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
            // 학원 이니셜 아바타
            Box(
                modifier         = Modifier
                    .size(42.dp)
                    .clip(CircleShape)
                    .background(Blue600.copy(alpha = 0.12f)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text       = proposal.academyName.first().toString(),
                    fontSize   = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color      = Blue600
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            // 내용
            Column(modifier = Modifier.weight(1f)) {
                // 학원명 + 매칭 점수
                Row(
                    modifier          = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text       = proposal.academyName,
                        fontSize   = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color      = Gray900,
                        modifier   = Modifier.weight(1f)
                    )
                    MatchScoreBadge(score = proposal.matchScore)
                }

                Spacer(modifier = Modifier.height(4.dp))

                // 과목 칩
                Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    proposal.subjects.forEach { subject ->
                        SubjectChip(subject)
                    }
                }

                Spacer(modifier = Modifier.height(6.dp))

                // 수강료 + 상태
                Row(
                    modifier          = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text       = proposal.tuitionFee,
                        fontSize   = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        color      = Blue600,
                        modifier   = Modifier.weight(1f)
                    )
                    StatusChip(status = proposal.status)
                    Spacer(modifier = Modifier.width(4.dp))
                    Icon(
                        imageVector        = Icons.Default.ChevronRight,
                        contentDescription = null,
                        tint               = Gray400,
                        modifier           = Modifier.size(18.dp)
                    )
                }
            }
        }
    }
}

// ── 매칭 점수 뱃지 ────────────────────────────────────
@Composable
private fun MatchScoreBadge(score: Int) {
    val color = when {
        score >= 80 -> Success
        score >= 60 -> Warning
        else        -> Gray400
    }
    Box(
        modifier         = Modifier
            .clip(RoundedCornerShape(20.dp))
            .background(color.copy(alpha = 0.12f))
            .padding(horizontal = 8.dp, vertical = 3.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text       = "${score}점",
            fontSize   = 12.sp,
            fontWeight = FontWeight.Bold,
            color      = color
        )
    }
}

// ── 과목 칩 ───────────────────────────────────────────
@Composable
private fun SubjectChip(subject: String) {
    Box(
        modifier         = Modifier
            .clip(RoundedCornerShape(6.dp))
            .background(Blue600.copy(alpha = 0.08f))
            .padding(horizontal = 7.dp, vertical = 2.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text     = subject,
            fontSize = 11.sp,
            color    = Blue600
        )
    }
}

// ── 상태 칩 ───────────────────────────────────────────
@Composable
private fun StatusChip(status: String) {
    val (bgColor, textColor) = when (status) {
        "검토중" -> Color(0xFFFEF9C3) to Color(0xFFCA8A04)
        "수락됨" -> Color(0xFFDCFCE7) to Color(0xFF16A34A)
        "거절됨" -> Gray200          to Gray500
        else    -> Gray200          to Gray500
    }
    Box(
        modifier         = Modifier
            .clip(RoundedCornerShape(6.dp))
            .background(bgColor)
            .padding(horizontal = 8.dp, vertical = 3.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text       = status,
            fontSize   = 11.sp,
            fontWeight = FontWeight.Medium,
            color      = textColor
        )
    }
}

// ── 바텀 네비게이션 ───────────────────────────────────
@Composable
private fun StudentBottomNav(
    selected      : Int,
    onSelect      : (Int) -> Unit,
    onNavHome     : () -> Unit,
    onNavProposals: () -> Unit,
    onNavChat     : () -> Unit,
    onNavMyPage   : () -> Unit
) {
    val items = listOf(
        Triple("홈",       Icons.Default.Home,   onNavHome),
        Triple("제안목록", Icons.Default.List,   onNavProposals),
        Triple("채팅",     Icons.Default.Chat,   onNavChat),
        Triple("마이페이지", Icons.Default.Person, onNavMyPage)
    )

    NavigationBar(
        containerColor = White,
        tonalElevation = 0.dp
    ) {
        items.forEachIndexed { index, (label, icon, action) ->
            NavigationBarItem(
                selected = selected == index,
                onClick  = {
                    onSelect(index)
                    action()
                },
                icon     = {
                    Icon(
                        imageVector        = icon,
                        contentDescription = label,
                        modifier           = Modifier.size(22.dp)
                    )
                },
                label    = {
                    Text(
                        text     = label,
                        fontSize = 10.sp
                    )
                },
                colors   = NavigationBarItemDefaults.colors(
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
fun StudentHomeScreenPreview() {
    AcademyHuntTheme {
        StudentHomeScreen(
            userName       = "홍길동",
            conditionCount = 2
        )
    }
}