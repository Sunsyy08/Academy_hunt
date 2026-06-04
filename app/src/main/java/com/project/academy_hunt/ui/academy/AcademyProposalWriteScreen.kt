package com.project.academy_hunt.ui.academy

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.project.academy_hunt.ui.theme.AcademyHuntTheme
import com.project.academy_hunt.ui.theme.Blue50
import com.project.academy_hunt.ui.theme.Blue600
import com.project.academy_hunt.ui.theme.Gray200
import com.project.academy_hunt.ui.theme.Gray400
import com.project.academy_hunt.ui.theme.Gray500
import com.project.academy_hunt.ui.theme.Gray700
import com.project.academy_hunt.ui.theme.Gray900
import com.project.academy_hunt.ui.theme.White

@Composable
fun AcademyProposalWriteScreen(
    studentId : Int    = 1,
    onBack    : () -> Unit = {},
    onSubmit  : () -> Unit = {}
) {
    val studentGrade    = "고1"
    val studentLevel    = "중급"
    val studentSubjects = listOf("수학", "영어")
    val studentLocation = "강남구"
    val studentTimes    = "월 수 금 18:00"

    var proposalText     by remember { mutableStateOf("") }
    var tuitionFee       by remember { mutableStateOf("") }
    var isGenerating     by remember { mutableStateOf(false) }
    var isGenerated      by remember { mutableStateOf(false) }
    var showSubmitDialog by remember { mutableStateOf(false) }

    if (showSubmitDialog) {
        AlertDialog(
            onDismissRequest = { showSubmitDialog = false },
            title   = { Text("제안서 제출", fontWeight = FontWeight.Bold) },
            text    = { Text("이 학생에게 제안서를 제출하시겠어요?") },
            confirmButton = {
                TextButton(onClick = { showSubmitDialog = false; onSubmit() }) {
                    Text("제출하기", color = Blue600, fontWeight = FontWeight.SemiBold)
                }
            },
            dismissButton = {
                TextButton(onClick = { showSubmitDialog = false }) {
                    Text("취소", color = Gray500)
                }
            },
            shape = RoundedCornerShape(16.dp)
        )
    }

    Scaffold(
        containerColor = White,
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
                    text       = "제안서 작성",
                    fontSize   = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color      = Gray900
                )
            }
        },
        bottomBar = {
            Button(
                onClick  = { showSubmitDialog = true },
                enabled  = proposalText.isNotBlank() && tuitionFee.isNotBlank(),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp)
                    .height(52.dp),
                shape  = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor         = Blue600,
                    disabledContainerColor = Gray200
                )
            ) {
                Text(
                    text       = "제안서 제출하기",
                    fontSize   = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color      = White
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            // 학생 정보 요약 카드
            Card(
                modifier  = Modifier.fillMaxWidth(),
                shape     = RoundedCornerShape(14.dp),
                colors    = CardDefaults.cardColors(containerColor = Blue50),
                elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text       = "학생 조건 요약",
                        fontSize   = 13.sp,
                        fontWeight = FontWeight.SemiBold,
                        color      = Blue600
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Row(
                        modifier              = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        StudentInfoItem(label = "학년", value = studentGrade)
                        StudentInfoItem(label = "수준", value = studentLevel)
                        StudentInfoItem(label = "지역", value = studentLocation)
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier              = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        StudentInfoItem(
                            label = "과목",
                            value = studentSubjects.joinToString(", ")
                        )
                        StudentInfoItem(label = "가능시간", value = studentTimes)
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // AI 생성 카드
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, Blue600.copy(alpha = 0.3f), RoundedCornerShape(14.dp))
                    .padding(16.dp)
            ) {
                Column {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(text = "✨", fontSize = 18.sp)
                        Spacer(modifier = Modifier.width(8.dp))
                        Column {
                            Text(
                                text       = "AI 제안서 초안 생성",
                                fontSize   = 15.sp,
                                fontWeight = FontWeight.Bold,
                                color      = Gray900
                            )
                            Text(
                                text     = "학원 정보와 학생 조건을 분석하여 맞춤 제안서를 생성합니다",
                                fontSize = 12.sp,
                                color    = Gray500
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    Button(
                        onClick = {
                            isGenerating = true
                            proposalText = "안녕하세요! 저희 강남수학학원은 10년 경력의 전문 강사진이 고1 수학, 영어를 책임지고 지도합니다.\n\n학생의 현재 수준(중급)에 맞춘 맞춤형 커리큘럼을 제공하며, 취약한 단원을 집중 보완합니다.\n\n주 3회 수업(월 수 금 18:00)으로 실력을 빠르게 향상시킬 수 있으며, 매달 모의고사 분석 리포트도 함께 제공합니다.\n\n소수정예(최대 5명) 수업으로 개인별 맞춤 지도를 보장합니다.\n\n상담 후 첫 1주일 무료 체험 수업도 가능합니다!"
                            isGenerating = false
                            isGenerated  = true
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(44.dp),
                        shape  = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Blue600)
                    ) {
                        if (isGenerating) {
                            CircularProgressIndicator(
                                modifier    = Modifier.size(18.dp),
                                color       = White,
                                strokeWidth = 2.dp
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("생성 중...", color = White, fontSize = 14.sp)
                        } else {
                            Text(
                                text       = if (isGenerated) "다시 생성하기" else "AI 초안 생성하기",
                                fontSize   = 14.sp,
                                fontWeight = FontWeight.SemiBold,
                                color      = White
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // 제안서 내용 입력
            Text(
                text       = "제안 내용",
                fontSize   = 15.sp,
                fontWeight = FontWeight.SemiBold,
                color      = Gray900
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value         = proposalText,
                onValueChange = { proposalText = it },
                placeholder   = {
                    Text(
                        text     = "AI 초안 생성 후 수정하거나 직접 입력해주세요\n(최소 10자 ~ 최대 5000자)",
                        color    = Gray400,
                        fontSize = 13.sp
                    )
                },
                modifier  = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 200.dp),
                shape     = RoundedCornerShape(12.dp),
                colors    = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor      = Blue600,
                    unfocusedBorderColor    = Gray200,
                    focusedContainerColor   = White,
                    unfocusedContainerColor = White
                )
            )
            Text(
                text      = "${proposalText.length} / 5000",
                fontSize  = 11.sp,
                color     = Gray400,
                modifier  = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp),
                textAlign = TextAlign.End
            )

            Spacer(modifier = Modifier.height(20.dp))

            // 수강료 입력
            Text(
                text       = "월 수강료",
                fontSize   = 15.sp,
                fontWeight = FontWeight.SemiBold,
                color      = Gray900
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value         = tuitionFee,
                onValueChange = { tuitionFee = it.filter { c -> c.isDigit() } },
                placeholder   = { Text("수강료를 입력해주세요 (원)", color = Gray400) },
                modifier      = Modifier.fillMaxWidth(),
                shape         = RoundedCornerShape(12.dp),
                singleLine    = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                suffix        = { Text("원", color = Gray500) },
                colors        = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor      = Blue600,
                    unfocusedBorderColor    = Gray200,
                    focusedContainerColor   = White,
                    unfocusedContainerColor = White
                )
            )

            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
private fun StudentInfoItem(
    label: String,
    value: String
) {
    Column {
        Text(
            text     = label,
            fontSize = 11.sp,
            color    = Gray400
        )
        Spacer(modifier = Modifier.height(2.dp))
        Text(
            text       = value,
            fontSize   = 13.sp,
            fontWeight = FontWeight.SemiBold,
            color      = Gray700
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AcademyProposalWriteScreenPreview() {
    AcademyHuntTheme {
        AcademyProposalWriteScreen()
    }
}