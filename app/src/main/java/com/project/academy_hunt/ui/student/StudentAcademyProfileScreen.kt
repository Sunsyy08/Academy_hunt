package com.project.academy_hunt.ui.student

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Star
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
import com.project.academy_hunt.ui.theme.Blue50
import com.project.academy_hunt.ui.theme.Blue600
import com.project.academy_hunt.ui.theme.Gray100
import com.project.academy_hunt.ui.theme.Gray200
import com.project.academy_hunt.ui.theme.Gray400
import com.project.academy_hunt.ui.theme.Gray500
import com.project.academy_hunt.ui.theme.Gray700
import com.project.academy_hunt.ui.theme.Gray900
import com.project.academy_hunt.ui.theme.White

data class ReviewItem(
    val id         : Int,
    val studentName: String,
    val rating     : Int,
    val comment    : String,
    val date       : String
)

private val dummyReviews = listOf(
    ReviewItem(1, "홍*동", 5, "강사님이 정말 친절하고 설명을 잘 해주세요. 성적이 많이 올랐어요!", "2024.03.15"),
    ReviewItem(2, "김*수", 4, "커리큘럼이 체계적이고 수업 분위기가 좋아요. 숙제가 좀 많긴 하지만 실력이 느는 게 느껴져요.", "2024.03.10"),
    ReviewItem(3, "이*영", 5, "처음에 걱정했는데 선생님이 수준에 맞게 잘 가르쳐주세요. 강력 추천합니다!", "2024.02.28"),
    ReviewItem(4, "박*민", 3, "수업 내용은 좋은데 주차 공간이 좀 부족해요.", "2024.02.20"),
)

@Composable
fun StudentAcademyProfileScreen(
    onBack: () -> Unit = {}
) {
    val academyName   = "강남수학학원"
    val address       = "서울 강남구 테헤란로 123"
    val avgRating     = 4.5f
    val reviewCount   = dummyReviews.size
    val subjects      = listOf("수학", "과학", "코딩")
    val curriculum    = "저희 학원은 개념 완성 → 유형 훈련 → 실전 적용의 3단계 커리큘럼으로 운영됩니다.\n\n• 1단계: 개념 완성 (매주 월, 수)\n• 2단계: 유형 훈련 (매주 금)\n• 3단계: 월말 모의고사 및 분석 리포트 제공\n\n소수정예 수업(최대 5명)으로 개인별 맞춤 지도를 보장합니다."
    val teacherSpec   = "• 서울대학교 수학과 졸업\n• 수능 수학 만점 강사\n• 강남 대형학원 10년 경력\n• 전 학생 수능 1등급 달성률 87%"
    val availableTime = "월 ~ 금: 오후 2시 ~ 오후 10시\n토: 오전 10시 ~ 오후 6시"

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
                    text       = "학원 정보",
                    fontSize   = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color      = Gray900
                )
            }
        }
    ) { innerPadding ->
        LazyColumn(
            modifier       = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentPadding = PaddingValues(bottom = 24.dp)
        ) {
            // 학원 기본 정보
            item {
                Card(
                    modifier  = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    shape     = RoundedCornerShape(16.dp),
                    colors    = CardDefaults.cardColors(containerColor = White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        // 아바타 + 이름
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier         = Modifier
                                    .size(56.dp)
                                    .clip(CircleShape)
                                    .background(Blue600.copy(alpha = 0.12f)),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text       = academyName.first().toString(),
                                    fontSize   = 22.sp,
                                    fontWeight = FontWeight.Bold,
                                    color      = Blue600
                                )
                            }
                            Spacer(modifier = Modifier.width(12.dp))
                            Column {
                                Text(
                                    text       = academyName,
                                    fontSize   = 18.sp,
                                    fontWeight = FontWeight.Bold,
                                    color      = Gray900
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        imageVector        = Icons.Default.LocationOn,
                                        contentDescription = null,
                                        tint               = Gray400,
                                        modifier           = Modifier.size(14.dp)
                                    )
                                    Spacer(modifier = Modifier.width(2.dp))
                                    Text(
                                        text     = address,
                                        fontSize = 12.sp,
                                        color    = Gray500
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(14.dp))
                        Divider(color = Gray100)
                        Spacer(modifier = Modifier.height(14.dp))

                        // 평점
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            repeat(5) { index ->
                                Icon(
                                    imageVector        = Icons.Default.Star,
                                    contentDescription = null,
                                    tint               = if (index < avgRating.toInt()) Color(0xFFFACC15) else Gray200,
                                    modifier           = Modifier.size(18.dp)
                                )
                            }
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text       = "$avgRating",
                                fontSize   = 15.sp,
                                fontWeight = FontWeight.Bold,
                                color      = Gray900
                            )
                            Text(
                                text     = " (리뷰 ${reviewCount}개)",
                                fontSize = 13.sp,
                                color    = Gray500
                            )
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        // 과목 칩
                        Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            subjects.forEach { subject ->
                                Box(
                                    modifier         = Modifier
                                        .clip(RoundedCornerShape(20.dp))
                                        .background(Blue50)
                                        .padding(horizontal = 12.dp, vertical = 5.dp)
                                ) {
                                    Text(
                                        text       = subject,
                                        fontSize   = 12.sp,
                                        fontWeight = FontWeight.Medium,
                                        color      = Blue600
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // 정보 카드 3개
            item {
                Row(
                    modifier              = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    InfoMiniCard(
                        modifier = Modifier.weight(1f),
                        title    = "운영시간",
                        content  = "평일 14~22시\n토 10~18시"
                    )
                    InfoMiniCard(
                        modifier = Modifier.weight(1f),
                        title    = "수업 인원",
                        content  = "최대 5명\n소수정예"
                    )
                    InfoMiniCard(
                        modifier = Modifier.weight(1f),
                        title    = "경력",
                        content  = "10년\n이상"
                    )
                }
            }

            item { Spacer(modifier = Modifier.height(16.dp)) }

            // 커리큘럼
            item {
                Spacer(modifier = Modifier.height(16.dp))
                ProfileSection(title = "커리큘럼") {
                    Text(
                        text       = curriculum,
                        fontSize   = 14.sp,
                        color      = Gray700,
                        lineHeight = 22.sp
                    )
                }
            }

            // 강사 스펙
            item {
                Spacer(modifier = Modifier.height(12.dp))
                ProfileSection(title = "강사 스펙") {
                    Text(
                        text       = teacherSpec,
                        fontSize   = 14.sp,
                        color      = Gray700,
                        lineHeight = 22.sp
                    )
                }
            }

            // 리뷰 섹션 타이틀
            item {
                Spacer(modifier = Modifier.height(12.dp))
                Row(
                    modifier          = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text       = "리뷰",
                        fontSize   = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color      = Gray900
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Box(
                        modifier         = Modifier
                            .size(22.dp)
                            .clip(CircleShape)
                            .background(Blue600),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text       = "$reviewCount",
                            fontSize   = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color      = White
                        )
                    }
                }
            }

            // 리뷰 카드 목록
            items(dummyReviews) { review ->
                Spacer(modifier = Modifier.height(10.dp))
                ReviewCard(review = review)
            }
        }
    }
}

@Composable
private fun InfoMiniCard(
    modifier: Modifier = Modifier,
    title   : String,
    content : String
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
                text       = title,
                fontSize   = 11.sp,
                color      = Gray500,
                fontWeight = FontWeight.Medium
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text       = content,
                fontSize   = 12.sp,
                fontWeight = FontWeight.SemiBold,
                color      = Gray900,
                textAlign  = androidx.compose.ui.text.style.TextAlign.Center
            )
        }
    }
}

@Composable
private fun ProfileSection(
    title  : String,
    content: @Composable () -> Unit
) {
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
                text       = title,
                fontSize   = 15.sp,
                fontWeight = FontWeight.Bold,
                color      = Gray900
            )
            Spacer(modifier = Modifier.height(10.dp))
            content()
        }
    }
}

@Composable
private fun ReviewCard(review: ReviewItem) {
    Card(
        modifier  = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        shape     = RoundedCornerShape(14.dp),
        colors    = CardDefaults.cardColors(containerColor = White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                modifier          = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // 이니셜 아바타
                Box(
                    modifier         = Modifier
                        .size(34.dp)
                        .clip(CircleShape)
                        .background(Gray100),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text       = review.studentName.first().toString(),
                        fontSize   = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color      = Gray500
                    )
                }
                Spacer(modifier = Modifier.width(10.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text       = review.studentName,
                        fontSize   = 13.sp,
                        fontWeight = FontWeight.SemiBold,
                        color      = Gray900
                    )
                    Text(
                        text     = review.date,
                        fontSize = 11.sp,
                        color    = Gray400
                    )
                }
                // 별점
                Row {
                    repeat(review.rating) {
                        Icon(
                            imageVector        = Icons.Default.Star,
                            contentDescription = null,
                            tint               = Color(0xFFFACC15),
                            modifier           = Modifier.size(14.dp)
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text       = review.comment,
                fontSize   = 13.sp,
                color      = Gray700,
                lineHeight = 20.sp
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun StudentAcademyProfileScreenPreview() {
    AcademyHuntTheme {
        StudentAcademyProfileScreen()
    }
}