package com.project.academy_hunt.ui.academy

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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

private val subjectFilters = listOf("전체", "수학", "영어", "국어", "과학", "코딩")
private val gradeFilters   = listOf("전체", "초등", "중등", "고등")
private val levelFilters   = listOf("전체", "초급", "중급", "고급")

private val dummyAllStudents = listOf(
    StudentCardItem(1, "고1", "중급", listOf("수학", "영어"), "강남구", "월 수 금 18:00"),
    StudentCardItem(2, "중3", "초급", listOf("수학"),         "서초구", "화 목 17:00"),
    StudentCardItem(3, "고2", "고급", listOf("영어", "국어"), "송파구", "월 수 19:00"),
    StudentCardItem(4, "초6", "초급", listOf("수학", "과학"), "마포구", "월 화 수 16:00"),
    StudentCardItem(5, "중1", "중급", listOf("영어"),         "강동구", "목 금 18:00"),
    StudentCardItem(6, "고3", "고급", listOf("수학", "영어"), "강남구", "월 수 금 20:00"),
)

@Composable
fun AcademyStudentListScreen(
    onBack        : () -> Unit = {},
    onStudentClick: (Int) -> Unit = {}
) {
    var selectedSubject by remember { mutableStateOf("전체") }
    var selectedGrade   by remember { mutableStateOf("전체") }
    var selectedLevel   by remember { mutableStateOf("전체") }

    val filtered = dummyAllStudents.filter { student ->
        val subjectMatch = selectedSubject == "전체" || student.subjects.contains(selectedSubject)
        val gradeMatch   = selectedGrade == "전체" || when (selectedGrade) {
            "초등" -> student.grade.startsWith("초")
            "중등" -> student.grade.startsWith("중")
            "고등" -> student.grade.startsWith("고")
            else   -> true
        }
        val levelMatch = selectedLevel == "전체" || student.level == selectedLevel
        subjectMatch && gradeMatch && levelMatch
    }

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
                        text       = "학생 찾기",
                        fontSize   = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color      = Gray900,
                        modifier   = Modifier.weight(1f)
                    )
                    Text(
                        text     = "총 ${filtered.size}명",
                        fontSize = 13.sp,
                        color    = Gray500,
                        modifier = Modifier.padding(end = 16.dp)
                    )
                }

                // 과목 필터
                LazyRow(
                    contentPadding        = PaddingValues(horizontal = 16.dp, vertical = 4.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(subjectFilters) { filter ->
                        val selected = selectedSubject == filter
                        FilterChip(
                            selected = selected,
                            onClick  = { selectedSubject = filter },
                            label    = {
                                Text(
                                    text       = filter,
                                    fontSize   = 12.sp,
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

                // 학년 필터
                LazyRow(
                    contentPadding        = PaddingValues(horizontal = 16.dp, vertical = 4.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(gradeFilters) { filter ->
                        val selected = selectedGrade == filter
                        FilterChip(
                            selected = selected,
                            onClick  = { selectedGrade = filter },
                            label    = {
                                Text(
                                    text       = filter,
                                    fontSize   = 12.sp,
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

                // 수준 필터
                LazyRow(
                    contentPadding        = PaddingValues(horizontal = 16.dp, vertical = 4.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(levelFilters) { filter ->
                        val selected = selectedLevel == filter
                        FilterChip(
                            selected = selected,
                            onClick  = { selectedLevel = filter },
                            label    = {
                                Text(
                                    text       = filter,
                                    fontSize   = 12.sp,
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

                Spacer(modifier = Modifier.height(4.dp))
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
                    Text(text = "🔍", fontSize = 48.sp)
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text     = "조건에 맞는 학생이 없어요",
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
                items(filtered) { student ->
                    StudentListCard(
                        student = student,
                        onClick = { onStudentClick(student.id) }
                    )
                }
            }
        }
    }
}

@Composable
private fun StudentListCard(
    student: StudentCardItem,
    onClick: () -> Unit
) {
    Card(
        modifier  = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        shape     = RoundedCornerShape(14.dp),
        colors    = CardDefaults.cardColors(containerColor = White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier          = Modifier.fillMaxWidth(),
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
                        fontSize   = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color      = Blue600
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                Column(modifier = Modifier.weight(1f)) {
                    // 학년 + 수준 칩
                    Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        Box(
                            modifier         = Modifier
                                .clip(RoundedCornerShape(6.dp))
                                .background(Blue600.copy(alpha = 0.08f))
                                .padding(horizontal = 8.dp, vertical = 3.dp)
                        ) {
                            Text(
                                text       = student.grade,
                                fontSize   = 12.sp,
                                fontWeight = FontWeight.SemiBold,
                                color      = Blue600
                            )
                        }
                        Box(
                            modifier         = Modifier
                                .clip(RoundedCornerShape(6.dp))
                                .background(Gray100)
                                .padding(horizontal = 8.dp, vertical = 3.dp)
                        ) {
                            Text(
                                text       = student.level,
                                fontSize   = 12.sp,
                                fontWeight = FontWeight.Medium,
                                color      = Gray500
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
                }

                Icon(
                    imageVector        = Icons.Default.ArrowForwardIos,
                    contentDescription = null,
                    tint               = Gray400,
                    modifier           = Modifier.size(14.dp)
                )
            }

            Spacer(modifier = Modifier.height(10.dp))
            Divider(color = Gray100)
            Spacer(modifier = Modifier.height(10.dp))

            // 위치 + 시간
            Row(
                modifier              = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text     = "📍 ${student.location}",
                    fontSize = 12.sp,
                    color    = Gray500
                )
                Text(
                    text     = "🕐 ${student.availableTimes}",
                    fontSize = 12.sp,
                    color    = Gray500
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            // AI 제안서 작성 버튼
            Button(
                onClick  = onClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp),
                shape  = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Blue600)
            ) {
                Text(
                    text       = "✨ AI 제안서 작성",
                    fontSize   = 13.sp,
                    fontWeight = FontWeight.SemiBold,
                    color      = White
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AcademyStudentListScreenPreview() {
    AcademyHuntTheme {
        AcademyStudentListScreen()
    }
}