package com.project.academy_hunt.ui.academy

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
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
import com.project.academy_hunt.ui.theme.Success
import com.project.academy_hunt.ui.theme.Warning
import com.project.academy_hunt.ui.theme.White

private val statusFilters = listOf("전체", "검토중", "수락됨", "거절됨", "취소됨")

data class SentProposalItem(
    val id          : Int,
    val studentGrade: String,
    val subjects    : List<String>,
    val tuitionFee  : String,
    val matchScore  : Int,
    val status      : String,
    val sentDate    : String
)

private val dummySentProposals = listOf(
    SentProposalItem(1, "고1", listOf("수학", "영어"), "월 300,000원", 85, "검토중", "2024.03.15"),
    SentProposalItem(2, "중3", listOf("수학"),         "월 200,000원", 72, "수락됨", "2024.03.12"),
    SentProposalItem(3, "고2", listOf("영어", "국어"), "월 280,000원", 65, "거절됨", "2024.03.10"),
    SentProposalItem(4, "초6", listOf("수학", "과학"), "월 180,000원", 90, "검토중", "2024.03.08"),
    SentProposalItem(5, "고3", listOf("수학"),         "월 350,000원", 78, "수락됨", "2024.03.05"),
)

@Composable
fun AcademyProposalStatusScreen(
    onProposalClick: (Int) -> Unit = {}
) {
    var selectedFilter by remember { mutableStateOf("전체") }

    val filtered = if (selectedFilter == "전체") dummySentProposals
    else dummySentProposals.filter { it.status == selectedFilter }

    // 상태별 카운트
    val pendingCount  = dummySentProposals.count { it.status == "검토중" }
    val acceptedCount = dummySentProposals.count { it.status == "수락됨" }
    val rejectedCount = dummySentProposals.count { it.status == "거절됨" }

    Scaffold(
        containerColor = Gray100,
        topBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(White)
            ) {
                Row(
                    modifier          = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text       = "제안 현황",
                        fontSize   = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color      = Gray900
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Text(
                        text     = "총 ${filtered.size}건",
                        fontSize = 13.sp,
                        color    = Gray500
                    )
                }

                // 요약 통계
                Row(
                    modifier              = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .padding(bottom = 12.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    ProposalStatChip(
                        modifier = Modifier.weight(1f),
                        label    = "검토중",
                        count    = pendingCount,
                        color    = Color(0xFFCA8A04),
                        bgColor  = Color(0xFFFEF9C3)
                    )
                    ProposalStatChip(
                        modifier = Modifier.weight(1f),
                        label    = "수락됨",
                        count    = acceptedCount,
                        color    = Color(0xFF16A34A),
                        bgColor  = Color(0xFFDCFCE7)
                    )
                    ProposalStatChip(
                        modifier = Modifier.weight(1f),
                        label    = "거절됨",
                        count    = rejectedCount,
                        color    = Gray500,
                        bgColor  = Gray100
                    )
                }

                // 필터 칩
                LazyRow(
                    contentPadding        = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(statusFilters) { filter ->
                        val selected = selectedFilter == filter
                        FilterChip(
                            selected = selected,
                            onClick  = { selectedFilter = filter },
                            label    = {
                                Text(
                                    text       = filter,
                                    fontSize   = 13.sp,
                                    fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Normal
                                )
                            },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = Blue600,
                                selectedLabelColor     = White,
                                containerColor         = White,
                                labelColor             = Gray500
                            ),
                            border = FilterChipDefaults.filterChipBorder(
                                enabled             = true,
                                selected            = selected,
                                borderColor         = Gray200,
                                selectedBorderColor = Blue600
                            )
                        )
                    }
                }
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
                    Text(text = "📋", fontSize = 48.sp)
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text     = "보낸 제안이 없어요",
                        fontSize = 16.sp,
                        color    = Gray500
                    )
                }
            }
        } else {
            LazyColumn(
                modifier            = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentPadding      = PaddingValues(vertical = 12.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(filtered) { proposal ->
                    SentProposalCard(
                        proposal = proposal,
                        onClick  = { onProposalClick(proposal.id) }
                    )
                }
            }
        }
    }
}

@Composable
private fun ProposalStatChip(
    modifier: Modifier = Modifier,
    label   : String,
    count   : Int,
    color   : Color,
    bgColor : Color
) {
    Box(
        modifier         = modifier
            .clip(RoundedCornerShape(10.dp))
            .background(bgColor)
            .padding(vertical = 10.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text       = count.toString(),
                fontSize   = 20.sp,
                fontWeight = FontWeight.Bold,
                color      = color
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text     = label,
                fontSize = 11.sp,
                color    = color
            )
        }
    }
}

@Composable
private fun SentProposalCard(
    proposal: SentProposalItem,
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
            // 아바타
            Box(
                modifier         = Modifier
                    .size(44.dp)
                    .clip(CircleShape)
                    .background(Blue600.copy(alpha = 0.12f)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text       = proposal.studentGrade,
                    fontSize   = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color      = Blue600
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                // 학년 + 매칭 점수
                Row(
                    modifier          = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text       = "${proposal.studentGrade} 학생",
                        fontSize   = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color      = Gray900,
                        modifier   = Modifier.weight(1f)
                    )
                    // 매칭 점수
                    val scoreColor = when {
                        proposal.matchScore >= 80 -> Success
                        proposal.matchScore >= 60 -> Warning
                        else                      -> Gray400
                    }
                    Box(
                        modifier         = Modifier
                            .clip(RoundedCornerShape(20.dp))
                            .background(scoreColor.copy(alpha = 0.12f))
                            .padding(horizontal = 8.dp, vertical = 3.dp)
                    ) {
                        Text(
                            text       = "${proposal.matchScore}점",
                            fontSize   = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color      = scoreColor
                        )
                    }
                }

                Spacer(modifier = Modifier.height(5.dp))

                // 과목 칩
                Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    proposal.subjects.forEach { subject ->
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

                Spacer(modifier = Modifier.height(6.dp))

                // 수강료 + 상태 + 날짜
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
                    SentProposalStatusChip(status = proposal.status)
                    Spacer(modifier = Modifier.width(4.dp))
                    Icon(
                        imageVector        = Icons.Default.ArrowForwardIos,
                        contentDescription = null,
                        tint               = Gray400,
                        modifier           = Modifier.size(14.dp)
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text     = "제출일: ${proposal.sentDate}",
                    fontSize = 11.sp,
                    color    = Gray400
                )
            }
        }
    }
}

@Composable
private fun SentProposalStatusChip(status: String) {
    val (bgColor, textColor) = when (status) {
        "검토중" -> Color(0xFFFEF9C3) to Color(0xFFCA8A04)
        "수락됨" -> Color(0xFFDCFCE7) to Color(0xFF16A34A)
        "거절됨" -> Gray200           to Gray500
        "취소됨" -> Gray200           to Gray500
        else    -> Gray200           to Gray500
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

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AcademyProposalStatusScreenPreview() {
    AcademyHuntTheme {
        AcademyProposalStatusScreen()
    }
}