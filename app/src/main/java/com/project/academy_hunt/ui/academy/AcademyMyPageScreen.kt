package com.project.academy_hunt.ui.academy

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Assignment
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Help
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.School
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
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

@Composable
fun AcademyMyPageScreen(
    academyName     : String = "강남수학학원",
    academyEmail    : String = "academy@test.com",
    onEditProfile   : () -> Unit = {},
    onEditAcademy   : () -> Unit = {},
    onNotifications : () -> Unit = {},
    onHelp          : () -> Unit = {},
    onLogout        : () -> Unit = {}
) {
    var showLogoutDialog by remember { mutableStateOf(false) }

    if (showLogoutDialog) {
        AlertDialog(
            onDismissRequest = { showLogoutDialog = false },
            title   = { Text("로그아웃", fontWeight = FontWeight.Bold) },
            text    = { Text("정말 로그아웃 하시겠어요?") },
            confirmButton = {
                TextButton(onClick = { showLogoutDialog = false; onLogout() }) {
                    Text("로그아웃", color = Color(0xFFDC2626))
                }
            },
            dismissButton = {
                TextButton(onClick = { showLogoutDialog = false }) {
                    Text("취소", color = Gray500)
                }
            },
            shape = RoundedCornerShape(16.dp)
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Gray100)
            .verticalScroll(rememberScrollState())
    ) {
        // 상단 타이틀
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(White)
                .padding(horizontal = 16.dp, vertical = 16.dp)
        ) {
            Text(
                text       = "마이페이지",
                fontSize   = 20.sp,
                fontWeight = FontWeight.Bold,
                color      = Gray900
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        // 프로필 카드
        Card(
            modifier  = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
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
                // 아바타
                Box(
                    modifier         = Modifier
                        .size(56.dp)
                        .clip(CircleShape)
                        .background(Blue600),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text       = academyName.first().toString(),
                        fontSize   = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color      = White
                    )
                }

                Spacer(modifier = Modifier.width(14.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text       = academyName,
                        fontSize   = 17.sp,
                        fontWeight = FontWeight.Bold,
                        color      = Gray900
                    )
                    Spacer(modifier = Modifier.height(3.dp))
                    Text(
                        text     = academyEmail,
                        fontSize = 13.sp,
                        color    = Gray500
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Box(
                        modifier         = Modifier
                            .clip(RoundedCornerShape(20.dp))
                            .background(Blue50)
                            .padding(horizontal = 12.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text       = "학원",
                            fontSize   = 11.sp,
                            fontWeight = FontWeight.SemiBold,
                            color      = Blue600
                        )
                    }
                }

                TextButton(onClick = onEditProfile) {
                    Text(
                        text     = "수정",
                        fontSize = 13.sp,
                        color    = Blue600
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // 통계 카드
        Row(
            modifier              = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            AcademyStatMiniCard(
                modifier = Modifier.weight(1f),
                label    = "보낸 제안",
                value    = "12"
            )
            AcademyStatMiniCard(
                modifier = Modifier.weight(1f),
                label    = "수락됨",
                value    = "5"
            )
            AcademyStatMiniCard(
                modifier = Modifier.weight(1f),
                label    = "진행중",
                value    = "3"
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        // 메뉴 리스트
        Card(
            modifier  = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            shape     = RoundedCornerShape(16.dp),
            colors    = CardDefaults.cardColors(containerColor = White),
            elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
        ) {
            Column {
                AcademyMenuitem(
                    icon    = Icons.Default.Person,
                    label   = "내 프로필 관리",
                    onClick = onEditProfile
                )
                Divider(color = Gray100, thickness = 1.dp)
                AcademyMenuitem(
                    icon    = Icons.Default.School,
                    label   = "학원 정보 수정",
                    onClick = onEditAcademy
                )
                Divider(color = Gray100, thickness = 1.dp)
                AcademyMenuitem(
                    icon    = Icons.Default.Assignment,
                    label   = "제안 현황",
                    onClick = {}
                )
                Divider(color = Gray100, thickness = 1.dp)
                AcademyMenuitem(
                    icon    = Icons.Default.Notifications,
                    label   = "알림 설정",
                    onClick = onNotifications
                )
                Divider(color = Gray100, thickness = 1.dp)
                AcademyMenuitem(
                    icon    = Icons.Default.Help,
                    label   = "고객센터",
                    onClick = onHelp
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // 로그아웃
        Card(
            modifier  = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            shape     = RoundedCornerShape(16.dp),
            colors    = CardDefaults.cardColors(containerColor = White),
            elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
        ) {
            Row(
                modifier          = Modifier
                    .fillMaxWidth()
                    .clickable { showLogoutDialog = true }
                    .padding(horizontal = 16.dp, vertical = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector        = Icons.Default.ExitToApp,
                    contentDescription = null,
                    tint               = Color(0xFFDC2626),
                    modifier           = Modifier.size(22.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text       = "로그아웃",
                    fontSize   = 15.sp,
                    fontWeight = FontWeight.Medium,
                    color      = Color(0xFFDC2626),
                    modifier   = Modifier.weight(1f)
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text      = "v1.0.0",
            fontSize  = 12.sp,
            color     = Gray400,
            modifier  = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp),
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun AcademyStatMiniCard(
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
private fun AcademyMenuitem(
    icon   : ImageVector,
    label  : String,
    onClick: () -> Unit
) {
    Row(
        modifier          = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector        = icon,
            contentDescription = null,
            tint               = Gray700,
            modifier           = Modifier.size(22.dp)
        )
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text     = label,
            fontSize = 15.sp,
            color    = Gray900,
            modifier = Modifier.weight(1f)
        )
        Icon(
            imageVector        = Icons.Default.ChevronRight,
            contentDescription = null,
            tint               = Gray400,
            modifier           = Modifier.size(18.dp)
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AcademyMyPageScreenPreview() {
    AcademyHuntTheme {
        AcademyMyPageScreen()
    }
}