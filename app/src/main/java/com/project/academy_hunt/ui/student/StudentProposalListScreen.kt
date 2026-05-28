package com.project.academy_hunt.ui.student

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

private val filterOptions = listOf("전체", "검토중", "수락됨", "거절됨")

private val dummyProposalList = listOf(
    ProposalItem(1, "강남수학학원",   listOf("수학", "영어"), "월 300,000원", 85, "검토중"),
    ProposalItem(2, "스카이영어학원", listOf("영어"),         "월 250,000원", 72, "수락됨"),
    ProposalItem(3, "대치과학학원",   listOf("과학", "수학"), "월 280,000원", 65, "거절됨"),
    ProposalItem(4, "강북수학학원",   listOf("수학"),         "월 200,000원", 90, "검토중"),
    ProposalItem(5, "종로영어학원",   listOf("영어", "국어"), "월 320,000원", 58, "검토중"),
)

@Composable
fun StudentProposalListScreen(
    onProposalClick: (Int) -> Unit = {}
) {
    var selectedFilter by remember { mutableStateOf("전체") }

    val filtered = if (selectedFilter == "전체") dummyProposalList
    else dummyProposalList.filter { it.status == selectedFilter }

    Scaffold(
        containerColor = Gray100,
        topBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(White)
            ) {
                // 타이틀
                Row(
                    modifier          = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text       = "받은 제안",
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

                // 필터 칩
                LazyRow(
                    modifier            = Modifier.fillMaxWidth(),
                    contentPadding      = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(filterOptions) { filter ->
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
                                selectedContainerColor     = Blue600,
                                selectedLabelColor         = White,
                                containerColor             = White,
                                labelColor                 = Gray500
                            ),
                            border = FilterChipDefaults.filterChipBorder(
                                enabled          = true,
                                selected         = selected,
                                borderColor      = Gray200,
                                selectedBorderColor = Blue600
                            )
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        if (filtered.isEmpty()) {
            // 빈 상태
            Box(
                modifier         = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = "📭", fontSize = 48.sp)
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text     = "받은 제안이 없어요",
                        fontSize = 16.sp,
                        color    = Gray500
                    )
                }
            }
        } else {
            LazyColumn(
                modifier       = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentPadding = PaddingValues(vertical = 12.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(filtered) { proposal ->
                    ProposalListCard(
                        proposal = proposal,
                        onClick  = { onProposalClick(proposal.id) }
                    )
                }
            }
        }
    }
}

@Composable
private fun ProposalListCard(
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
            // 아바타
            Box(
                modifier         = Modifier
                    .size(44.dp)
                    .clip(CircleShape)
                    .background(Blue600.copy(alpha = 0.12f)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text       = proposal.academyName.first().toString(),
                    fontSize   = 17.sp,
                    fontWeight = FontWeight.Bold,
                    color      = Blue600
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

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
                    ProposalMatchBadge(score = proposal.matchScore)
                }

                Spacer(modifier = Modifier.height(5.dp))

                // 과목 칩
                Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    proposal.subjects.forEach { subject ->
                        ProposalSubjectChip(subject)
                    }
                }

                Spacer(modifier = Modifier.height(6.dp))

                // 수강료 + 상태 + 화살표
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
                    ProposalStatusChip(status = proposal.status)
                    Spacer(modifier = Modifier.width(4.dp))
                    Icon(
                        imageVector        = Icons.Default.ArrowForwardIos,
                        contentDescription = null,
                        tint               = Gray400,
                        modifier           = Modifier.size(14.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun ProposalMatchBadge(score: Int) {
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

@Composable
private fun ProposalSubjectChip(subject: String) {
    Box(
        modifier         = Modifier
            .clip(RoundedCornerShape(6.dp))
            .background(Blue600.copy(alpha = 0.08f))
            .padding(horizontal = 7.dp, vertical = 2.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(text = subject, fontSize = 11.sp, color = Blue600)
    }
}

@Composable
private fun ProposalStatusChip(status: String) {
    val (bgColor, textColor) = when (status) {
        "검토중" -> Color(0xFFFEF9C3) to Color(0xFFCA8A04)
        "수락됨" -> Color(0xFFDCFCE7) to Color(0xFF16A34A)
        "거절됨" -> Gray200           to Gray500
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
fun StudentProposalListScreenPreview() {
    AcademyHuntTheme {
        StudentProposalListScreen()
    }
}