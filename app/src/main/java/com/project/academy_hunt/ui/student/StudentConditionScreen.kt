package com.project.academy_hunt.ui.student

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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
import com.project.academy_hunt.ui.theme.Blue50
import com.project.academy_hunt.ui.theme.Blue600
import com.project.academy_hunt.ui.theme.Gray100
import com.project.academy_hunt.ui.theme.Gray200
import com.project.academy_hunt.ui.theme.Gray400
import com.project.academy_hunt.ui.theme.Gray500
import com.project.academy_hunt.ui.theme.Gray700
import com.project.academy_hunt.ui.theme.Gray900
import com.project.academy_hunt.ui.theme.White

private val gradeOptions = listOf(
    "초1", "초2", "초3", "초4", "초5", "초6",
    "중1", "중2", "중3",
    "고1", "고2", "고3"
)

private val subjectOptions = listOf(
    "수학", "영어", "국어", "과학", "사회", "한국사", "물리", "화학", "생물", "코딩"
)

private val levelOptions = listOf("초급", "중급", "고급")

private val dayOptions = listOf("월", "화", "수", "목", "금", "토", "일")

private val timeOptions = listOf("오전", "오후", "저녁")

@Composable
fun StudentConditionScreen(
    onBack  : () -> Unit = {},
    onSubmit: () -> Unit = {}
) {
    var currentStep     by remember { mutableStateOf(1) }
    var selectedGrade   by remember { mutableStateOf("") }
    val selectedSubjects = remember { mutableStateListOf<String>() }
    var selectedLevel   by remember { mutableStateOf("") }
    var location        by remember { mutableStateOf("") }
    val selectedTimes   = remember { mutableStateListOf<String>() }
    var busInput        by remember { mutableStateOf("") }
    val busTags         = remember { mutableStateListOf<String>() }

    Scaffold(
        containerColor = White,
        topBar = {
            ConditionTopBar(
                currentStep = currentStep,
                totalSteps  = 3,
                onBack      = {
                    if (currentStep > 1) currentStep-- else onBack()
                }
            )
        },
        bottomBar = {
            Button(
                onClick = {
                    if (currentStep < 3) currentStep++
                    else onSubmit()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp)
                    .height(52.dp),
                shape  = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Blue600)
            ) {
                Text(
                    text       = if (currentStep < 3) "다음" else "등록하기",
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
            when (currentStep) {
                1 -> StepOne(
                    selectedGrade    = selectedGrade,
                    onGradeSelect    = { selectedGrade = it },
                    selectedSubjects = selectedSubjects,
                    onSubjectToggle  = {
                        if (selectedSubjects.contains(it)) selectedSubjects.remove(it)
                        else selectedSubjects.add(it)
                    },
                    selectedLevel    = selectedLevel,
                    onLevelSelect    = { selectedLevel = it }
                )
                2 -> StepTwo(
                    location        = location,
                    onLocationChange = { location = it },
                    selectedTimes   = selectedTimes,
                    onTimeToggle    = {
                        if (selectedTimes.contains(it)) selectedTimes.remove(it)
                        else selectedTimes.add(it)
                    }
                )
                3 -> StepThree(
                    busInput   = busInput,
                    onBusInput = { busInput = it },
                    busTags    = busTags,
                    onBusAdd   = {
                        if (busInput.isNotBlank() && !busTags.contains(busInput)) {
                            busTags.add(busInput)
                            busInput = ""
                        }
                    },
                    onBusRemove = { busTags.remove(it) }
                )
            }
        }
    }
}

// ── 상단 바 + 스텝 인디케이터 ─────────────────────────
@Composable
private fun ConditionTopBar(
    currentStep: Int,
    totalSteps : Int,
    onBack     : () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(White)
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = onBack, modifier = Modifier.size(36.dp)) {
                Icon(
                    imageVector        = Icons.Default.ArrowBack,
                    contentDescription = "뒤로가기",
                    tint               = Gray900
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text       = "수강 조건 등록",
                fontSize   = 18.sp,
                fontWeight = FontWeight.Bold,
                color      = Gray900
            )
        }
        Spacer(modifier = Modifier.height(12.dp))
        // 스텝 프로그레스 바
        Row(
            modifier              = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            repeat(totalSteps) { index ->
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(4.dp)
                        .clip(RoundedCornerShape(2.dp))
                        .background(if (index < currentStep) Blue600 else Gray200)
                )
            }
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text     = "${currentStep} / ${totalSteps}",
            fontSize = 12.sp,
            color    = Gray400
        )
    }
}

// ── Step 1: 학년 / 과목 / 수준 ──────────────────────
@Composable
private fun StepOne(
    selectedGrade   : String,
    onGradeSelect   : (String) -> Unit,
    selectedSubjects: List<String>,
    onSubjectToggle : (String) -> Unit,
    selectedLevel   : String,
    onLevelSelect   : (String) -> Unit
) {
    SectionTitle("학년")
    Spacer(modifier = Modifier.height(10.dp))
    FlowChips(
        items      = gradeOptions,
        selected   = listOf(selectedGrade),
        onToggle   = onGradeSelect
    )

    Spacer(modifier = Modifier.height(24.dp))
    SectionTitle("배우고 싶은 과목")
    Spacer(modifier = Modifier.height(10.dp))
    FlowChips(
        items    = subjectOptions,
        selected = selectedSubjects,
        onToggle = onSubjectToggle
    )

    Spacer(modifier = Modifier.height(24.dp))
    SectionTitle("현재 수준")
    Spacer(modifier = Modifier.height(10.dp))
    Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
        levelOptions.forEach { level ->
            val selected = selectedLevel == level
            Box(
                modifier         = Modifier
                    .weight(1f)
                    .height(44.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(if (selected) Blue600 else White)
                    .border(1.dp, if (selected) Blue600 else Gray200, RoundedCornerShape(10.dp))
                    .clickable { onLevelSelect(level) },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text       = level,
                    fontSize   = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color      = if (selected) White else Gray500
                )
            }
        }
    }
}

// ── Step 2: 거주지 / 가능 시간대 ─────────────────────
@Composable
private fun StepTwo(
    location        : String,
    onLocationChange: (String) -> Unit,
    selectedTimes   : List<String>,
    onTimeToggle    : (String) -> Unit
) {
    SectionTitle("거주 지역")
    Spacer(modifier = Modifier.height(10.dp))
    OutlinedTextField(
        value         = location,
        onValueChange = onLocationChange,
        placeholder   = { Text("예: 강남구", color = Gray400) },
        modifier      = Modifier.fillMaxWidth(),
        shape         = RoundedCornerShape(12.dp),
        singleLine    = true,
        colors        = OutlinedTextFieldDefaults.colors(
            focusedBorderColor      = Blue600,
            unfocusedBorderColor    = Gray200,
            focusedContainerColor   = White,
            unfocusedContainerColor = White
        )
    )

    Spacer(modifier = Modifier.height(24.dp))
    SectionTitle("가능한 시간대")
    Spacer(modifier = Modifier.height(10.dp))

    // 요일 × 시간대 그리드
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        timeOptions.forEach { time ->
            Row(
                modifier              = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                verticalAlignment     = Alignment.CenterVertically
            ) {
                Text(
                    text     = time,
                    fontSize = 13.sp,
                    color    = Gray700,
                    modifier = Modifier.width(32.dp)
                )
                dayOptions.forEach { day ->
                    val key      = "$day $time"
                    val selected = selectedTimes.contains(key)
                    Box(
                        modifier         = Modifier
                            .weight(1f)
                            .height(36.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(if (selected) Blue600 else Gray100)
                            .clickable { onTimeToggle(key) },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text     = day,
                            fontSize = 12.sp,
                            color    = if (selected) White else Gray500
                        )
                    }
                }
            }
        }
    }
}

// ── Step 3: 버스 번호 ─────────────────────────────────
@Composable
private fun StepThree(
    busInput   : String,
    onBusInput : (String) -> Unit,
    busTags    : List<String>,
    onBusAdd   : () -> Unit,
    onBusRemove: (String) -> Unit
) {
    SectionTitle("이용 버스 번호 (선택)")
    Spacer(modifier = Modifier.height(4.dp))
    Text(
        text     = "버스 노선 기반으로 근처 학원을 추천해드려요",
        fontSize = 13.sp,
        color    = Gray500
    )
    Spacer(modifier = Modifier.height(12.dp))

    Row(
        modifier          = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        OutlinedTextField(
            value         = busInput,
            onValueChange = onBusInput,
            placeholder   = { Text("버스 번호 입력 (예: 147)", color = Gray400) },
            modifier      = Modifier.weight(1f),
            shape         = RoundedCornerShape(12.dp),
            singleLine    = true,
            colors        = OutlinedTextFieldDefaults.colors(
                focusedBorderColor      = Blue600,
                unfocusedBorderColor    = Gray200,
                focusedContainerColor   = White,
                unfocusedContainerColor = White
            )
        )
        Button(
            onClick = onBusAdd,
            shape   = RoundedCornerShape(12.dp),
            colors  = ButtonDefaults.buttonColors(containerColor = Blue600),
            modifier = Modifier.height(56.dp)
        ) {
            Text("추가", color = White, fontWeight = FontWeight.SemiBold)
        }
    }

    Spacer(modifier = Modifier.height(12.dp))

    // 버스 태그 목록
    if (busTags.isNotEmpty()) {
        Row(
            modifier     = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            busTags.forEach { tag ->
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(20.dp))
                        .background(Blue50)
                        .border(1.dp, Blue600.copy(alpha = 0.3f), RoundedCornerShape(20.dp))
                        .padding(horizontal = 12.dp, vertical = 6.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(text = tag, fontSize = 13.sp, color = Blue600)
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text     = "×",
                            fontSize = 13.sp,
                            color    = Blue600,
                            modifier = Modifier.clickable { onBusRemove(tag) }
                        )
                    }
                }
            }
        }
    }
}

// ── 공통 컴포넌트 ─────────────────────────────────────
@Composable
private fun SectionTitle(title: String) {
    Text(
        text       = title,
        fontSize   = 15.sp,
        fontWeight = FontWeight.SemiBold,
        color      = Gray900
    )
}

@Composable
private fun FlowChips(
    items   : List<String>,
    selected: List<String>,
    onToggle: (String) -> Unit
) {
    val rows = items.chunked(4)
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        rows.forEach { row ->
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                row.forEach { item ->
                    val isSelected = selected.contains(item)
                    Box(
                        modifier         = Modifier
                            .clip(RoundedCornerShape(20.dp))
                            .background(if (isSelected) Blue600 else White)
                            .border(1.dp, if (isSelected) Blue600 else Gray200, RoundedCornerShape(20.dp))
                            .clickable { onToggle(item) }
                            .padding(horizontal = 14.dp, vertical = 8.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text       = item,
                            fontSize   = 13.sp,
                            fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal,
                            color      = if (isSelected) White else Gray700
                        )
                    }
                }
            }
        }
    }
}

// ── Preview ───────────────────────────────────────────
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun StudentConditionScreenPreview() {
    AcademyHuntTheme {
        StudentConditionScreen()
    }
}