package com.project.academy_hunt.ui.student

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.project.academy_hunt.ui.theme.AcademyHuntTheme
import com.project.academy_hunt.ui.theme.Blue50
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

@Composable
fun StudentProposalDetailScreen(
    proposalId    : Int    = 1,
    onBack        : () -> Unit = {},
    onAccept      : () -> Unit = {},
    onReject      : () -> Unit = {}
) {
    // 더미 데이터
    val academyName  = "강남수학학원"
    val rating       = 4.5f
    val reviewCount  = 23
    val matchScore   = 85
    val subjects     = listOf("수학", "영어")
    val tuitionFee   = "300,000원"
    val proposalText = "안녕하세요! 저희 강남수학학원은 10년 경력의 전문 강사진이 고1 수학을 책임지고 지도합니다.\n\n학생의 현재 수준(중급)에 맞춘 맞춤형 커리큘럼을 제공하며, 취약한 단원을 집중 보완합니다.\n\n주 3회 수업으로 실력을 빠르게 향상시킬 수 있으며, 매달 모의고사 분석 리포트도 함께 제공합니다.\n\n상담 후 첫 1주일 무료 체험 수업도 가능합니다. 궁금한 점은 채팅으로 편하게 문의해주세요!"
    val scoreBreakdown = listOf(
        Triple("과목 일치", 40, 40),
        Triple("지역 일치", 15, 20),
        Triple("시간대 일치", 18, 20),
        Triple("수준 일치", 12, 20)
    )

    var showRejectDialog by remember { mutableStateOf(false) }

    // 거절 확인 다이얼로그
    if (showRejectDialog) {
        AlertDialog(
            onDismissRequest = { showRejectDialog = false },
            title = { Text("제안 거절", fontWeight = FontWeight.Bold) },
            text  = { Text("${academyName}의 제안을 거절하시겠어요?") },
            confirmButton = {
                TextButton(onClick = { showRejectDialog = false; onReject() }) {
                    Text("거절하기", color = Color(0xFFDC2626))
                }
            },
            dismissButton = {
                TextButton(onClick = { showRejectDialog = false }) {
                    Text("취소", color = Gray500)
                }
            },
            shape = RoundedCornerShape(16.dp)
        )
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
                Text(
                    text       = "제안 상세",
                    fontSize   = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color      = Gray900
                )
            }
        },
        bottomBar = {
            Row(
                modifier              = Modifier
                    .fillMaxWidth()
                    .background(White)
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                // 거절 버튼
                OutlinedButton(
                    onClick  = { showRejectDialog = true },
                    modifier = Modifier
                        .weight(1f)
                        .height(52.dp),
                    shape  = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = White,
                        contentColor   = Gray700
                    ),
                    border = ButtonDefaults.outlinedButtonBorder.copy(width = 1.dp)
                ) {
                    Text(
                        text       = "거절하기",
                        fontSize   = 15.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
                // 수락 버튼
                Button(
                    onClick  = onAccept,
                    modifier = Modifier
                        .weight(1f)
                        .height(52.dp),
                    shape  = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Blue600)
                ) {
                    Text(
                        text       = "수락하기",
                        fontSize   = 15.sp,
                        fontWeight = FontWeight.SemiBold,
                        color      = White
                    )
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
        ) {
            // 학원 정보 카드
            Card(
                modifier  = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                shape     = RoundedCornerShape(16.dp),
                colors    = CardDefaults.cardColors(containerColor = White),
                elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
            ) {
                Row(
                    modifier          = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier         = Modifier
                            .size(50.dp)
                            .clip(CircleShape)
                            .background(Blue600.copy(alpha = 0.12f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text       = academyName.first().toString(),
                            fontSize   = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color      = Blue600
                        )
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(
                            text       = academyName,
                            fontSize   = 17.sp,
                            fontWeight = FontWeight.Bold,
                            color      = Gray900
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector        = Icons.Default.Star,
                                contentDescription = null,
                                tint               = Color(0xFFFACC15),
                                modifier           = Modifier.size(15.dp)
                            )
                            Spacer(modifier = Modifier.width(3.dp))
                            Text(
                                text     = "$rating",
                                fontSize = 13.sp,
                                color    = Gray700,
                                fontWeight = FontWeight.SemiBold
                            )
                            Text(
                                text     = " (리뷰 ${reviewCount}개)",
                                fontSize = 12.sp,
                                color    = Gray500
                            )
                        }
                        Spacer(modifier = Modifier.height(6.dp))
                        Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                            subjects.forEach { subject ->
                                Box(
                                    modifier         = Modifier
                                        .clip(RoundedCornerShape(6.dp))
                                        .background(Blue600.copy(alpha = 0.08f))
                                        .padding(horizontal = 7.dp, vertical = 2.dp)
                                ) {
                                    Text(text = subject, fontSize = 11.sp, color = Blue600)
                                }
                            }
                        }
                    }
                }
            }

            // 매칭 점수 카드
            Card(
                modifier  = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                shape     = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
                colors    = CardDefaults.cardColors(containerColor = Color.Transparent)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            Brush.horizontalGradient(
                                colors = listOf(Blue600, Blue700)
                            ),
                            RoundedCornerShape(16.dp)
                        )
                        .padding(20.dp)
                ) {
                    Column {
                        Text(
                            text       = "매칭 적합도",
                            fontSize   = 13.sp,
                            color      = White.copy(alpha = 0.8f)
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text       = "${matchScore}점",
                            fontSize   = 36.sp,
                            fontWeight = FontWeight.Bold,
                            color      = White
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        // 세부 점수
                        scoreBreakdown.forEach { (label, score, maxScore) ->
                            ScoreBreakdownRow(
                                label    = label,
                                score    = score,
                                maxScore = maxScore
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // 제안 내용 카드
            Card(
                modifier  = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                shape     = RoundedCornerShape(16.dp),
                colors    = CardDefaults.cardColors(containerColor = White),
                elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text       = "제안 내용",
                        fontSize   = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color      = Gray900
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text       = proposalText,
                        fontSize   = 14.sp,
                        color      = Gray700,
                        lineHeight = 22.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // 수강료 카드
            Card(
                modifier  = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                shape     = RoundedCornerShape(16.dp),
                colors    = CardDefaults.cardColors(containerColor = Blue50),
                elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
            ) {
                Row(
                    modifier          = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text       = "월 수강료",
                        fontSize   = 14.sp,
                        color      = Gray700,
                        modifier   = Modifier.weight(1f)
                    )
                    Text(
                        text       = tuitionFee,
                        fontSize   = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color      = Blue600
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
private fun ScoreBreakdownRow(
    label   : String,
    score   : Int,
    maxScore: Int
) {
    Column {
        Row(
            modifier          = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text     = label,
                fontSize = 12.sp,
                color    = White.copy(alpha = 0.85f),
                modifier = Modifier.weight(1f)
            )
            Text(
                text       = "${score}/${maxScore}점",
                fontSize   = 12.sp,
                fontWeight = FontWeight.SemiBold,
                color      = White
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(4.dp)
                .clip(RoundedCornerShape(2.dp))
                .background(White.copy(alpha = 0.25f))
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(score.toFloat() / maxScore.toFloat())
                    .height(4.dp)
                    .clip(RoundedCornerShape(2.dp))
                    .background(White)
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun StudentProposalDetailScreenPreview() {
    AcademyHuntTheme {
        StudentProposalDetailScreen()
    }
}