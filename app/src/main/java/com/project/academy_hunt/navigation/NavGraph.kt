package com.project.academy_hunt.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.project.academy_hunt.data.auth.AuthRepository
import com.project.academy_hunt.data.core.RetrofitClient
import com.project.academy_hunt.data.core.TokenDataStore
import com.project.academy_hunt.ui.academy.AcademyChatListScreen
import com.project.academy_hunt.ui.academy.AcademyChatRoomScreen
import com.project.academy_hunt.ui.academy.AcademyHomeScreen
import com.project.academy_hunt.ui.academy.AcademyMyPageScreen
import com.project.academy_hunt.ui.academy.AcademyProposalStatusScreen
import com.project.academy_hunt.ui.academy.AcademyProposalWriteScreen
import com.project.academy_hunt.ui.academy.AcademyStudentListScreen
import com.project.academy_hunt.ui.auth.LoginScreen
import com.project.academy_hunt.ui.auth.RegisterScreen
import com.project.academy_hunt.ui.onboarding.OnboardingScreen
import com.project.academy_hunt.ui.splash.SplashScreen
import com.project.academy_hunt.ui.student.StudentAcademyProfileScreen
import com.project.academy_hunt.ui.student.StudentChatListScreen
import com.project.academy_hunt.ui.student.StudentChatRoomScreen
import com.project.academy_hunt.ui.student.StudentConditionScreen
import com.project.academy_hunt.ui.student.StudentHomeScreen
import com.project.academy_hunt.ui.student.StudentMyPageScreen
import com.project.academy_hunt.ui.student.StudentProposalDetailScreen
import com.project.academy_hunt.ui.student.StudentProposalListScreen
import com.project.academy_hunt.viewmodel.LoginViewModel
import com.project.academy_hunt.viewmodel.OnboardingViewModel
import com.project.academy_hunt.viewmodel.RegisterViewModel
import com.project.academy_hunt.viewmodel.ViewModelFactory

object Routes {
    const val SPLASH                  = "splash"
    const val ONBOARDING              = "onboarding"
    const val LOGIN                   = "login"
    const val REGISTER                = "register"

    // 학생
    const val STUDENT_HOME            = "student_home"
    const val STUDENT_CONDITION       = "student_condition"
    const val STUDENT_PROPOSAL_LIST   = "student_proposal_list"
    const val STUDENT_PROPOSAL_DETAIL = "student_proposal_detail/{proposalId}"
    const val STUDENT_ACADEMY_PROFILE = "student_academy_profile/{academyId}"
    const val STUDENT_CHAT_LIST       = "student_chat_list"
    const val STUDENT_CHAT_ROOM       = "student_chat_room/{chatRoomId}"
    const val STUDENT_MY_PAGE         = "student_my_page"

    // 학원
    const val ACADEMY_HOME            = "academy_home"
    const val ACADEMY_STUDENT_LIST    = "academy_student_list"
    const val ACADEMY_PROPOSAL_WRITE  = "academy_proposal_write/{studentId}"
    const val ACADEMY_PROPOSAL_STATUS = "academy_proposal_status"
    const val ACADEMY_MY_PAGE         = "academy_my_page"
    const val ACADEMY_CHAT_LIST = "academy_chat_list"
    const val ACADEMY_CHAT_ROOM = "academy_chat_room/{chatRoomId}"
}

@Composable
fun NavGraph(
    navController : NavHostController,
    tokenDataStore: TokenDataStore
) {
    val factory = ViewModelFactory(
        repository     = AuthRepository(RetrofitClient.authApi),
        tokenDataStore = tokenDataStore
    )

    NavHost(
        navController    = navController,
        startDestination = Routes.SPLASH
    ) {
        // ── 공통 ──────────────────────────────────────────
        composable(Routes.SPLASH) {
            SplashScreen(
                onNavigateToOnboarding = {
                    navController.navigate(Routes.ONBOARDING) {
                        popUpTo(Routes.SPLASH) { inclusive = true }
                    }
                }
            )
        }

        composable(Routes.ONBOARDING) {
            val vm: OnboardingViewModel = viewModel(factory = factory)
            OnboardingScreen(
                viewModel         = vm,
                onNavigateToLogin = {
                    navController.navigate(Routes.LOGIN) {
                        popUpTo(Routes.ONBOARDING) { inclusive = true }
                    }
                }
            )
        }

        composable(Routes.LOGIN) {
            val vm: LoginViewModel = viewModel(factory = factory)
            LoginScreen(
                viewModel            = vm,
                onNavigateToRegister = { navController.navigate(Routes.REGISTER) },
                onLoginSuccess       = { role ->
                    val dest = if (role == "student") Routes.STUDENT_HOME else Routes.ACADEMY_HOME
                    navController.navigate(dest) {
                        popUpTo(Routes.LOGIN) { inclusive = true }
                    }
                }
            )
        }

        composable(Routes.REGISTER) {
            val vm: RegisterViewModel = viewModel(factory = factory)
            RegisterScreen(
                viewModel         = vm,
                onNavigateToLogin = { navController.popBackStack() },
                onRegisterSuccess = { role ->
                    val dest = if (role == "student") Routes.STUDENT_HOME else Routes.ACADEMY_HOME
                    navController.navigate(dest) {
                        popUpTo(Routes.ONBOARDING) { inclusive = true }
                    }
                }
            )
        }

        // ── 학생 ──────────────────────────────────────────
        composable(Routes.STUDENT_HOME) {
            StudentHomeScreen(
                userName         = "홍길동",
                conditionCount   = 2,
                onConditionClick = { navController.navigate(Routes.STUDENT_CONDITION) },
                onProposalClick  = { id ->
                    navController.navigate("student_proposal_detail/$id")
                },
                onNavHome        = {},
                onNavProposals   = { navController.navigate(Routes.STUDENT_PROPOSAL_LIST) },
                onNavChat        = { navController.navigate(Routes.STUDENT_CHAT_LIST) },
                onNavMyPage      = { navController.navigate(Routes.STUDENT_MY_PAGE) }
            )
        }

        composable(Routes.STUDENT_CONDITION) {
            StudentConditionScreen(
                onBack   = { navController.popBackStack() },
                onSubmit = { navController.popBackStack() }
            )
        }

        composable(Routes.STUDENT_PROPOSAL_LIST) {
            StudentProposalListScreen(
                onProposalClick = { id ->
                    navController.navigate("student_proposal_detail/$id")
                }
            )
        }

        composable(Routes.STUDENT_PROPOSAL_DETAIL) { backStackEntry ->
            val proposalId = backStackEntry.arguments?.getString("proposalId")?.toIntOrNull() ?: 1
            StudentProposalDetailScreen(
                proposalId = proposalId,
                onBack     = { navController.popBackStack() },
                onAccept   = { navController.navigate(Routes.STUDENT_CHAT_LIST) },
                onReject   = { navController.popBackStack() }
            )
        }

        composable(Routes.STUDENT_ACADEMY_PROFILE) { backStackEntry ->
            val academyId = backStackEntry.arguments?.getString("academyId")?.toIntOrNull() ?: 1
            StudentAcademyProfileScreen(
                onBack = { navController.popBackStack() }
            )
        }

        composable(Routes.STUDENT_CHAT_LIST) {
            StudentChatListScreen(
                onChatClick = { id ->
                    navController.navigate("student_chat_room/$id")
                }
            )
        }

        composable(Routes.STUDENT_CHAT_ROOM) { backStackEntry ->
            val chatRoomId = backStackEntry.arguments?.getString("chatRoomId")?.toIntOrNull() ?: 1
            StudentChatRoomScreen(
                chatRoomId  = chatRoomId,
                academyName = "강남수학학원",
                onBack      = { navController.popBackStack() }
            )
        }

        composable(Routes.STUDENT_MY_PAGE) {
            StudentMyPageScreen(
                userName  = "홍길동",
                userEmail = "hong@test.com",
                onLogout  = {
                    navController.navigate(Routes.LOGIN) {
                        popUpTo(Routes.STUDENT_HOME) { inclusive = true }
                    }
                }
            )
        }

        // ── 학원 ──────────────────────────────────────────
        composable(Routes.ACADEMY_HOME) {
            AcademyHomeScreen(
                academyName    = "강남수학학원",
                onStudentClick = { id ->
                    navController.navigate("academy_proposal_write/$id")
                },
                onNavHome      = {},
                onNavStudents  = { navController.navigate(Routes.ACADEMY_STUDENT_LIST) },
                onNavProposals = { navController.navigate(Routes.ACADEMY_PROPOSAL_STATUS) },
                onNavChat      = { navController.navigate(Routes.ACADEMY_CHAT_LIST) },
                onNavMyPage    = { navController.navigate(Routes.ACADEMY_MY_PAGE) }
            )
        }

        composable(Routes.ACADEMY_STUDENT_LIST) {
            AcademyStudentListScreen(
                onBack         = { navController.popBackStack() },
                onStudentClick = { id ->
                    navController.navigate("academy_proposal_write/$id")
                }
            )
        }

        composable(Routes.ACADEMY_PROPOSAL_WRITE) { backStackEntry ->
            val studentId = backStackEntry.arguments?.getString("studentId")?.toIntOrNull() ?: 1
            AcademyProposalWriteScreen(
                studentId = studentId,
                onBack    = { navController.popBackStack() },
                onSubmit  = {
                    navController.navigate(Routes.ACADEMY_PROPOSAL_STATUS) {
                        popUpTo(Routes.ACADEMY_HOME) { inclusive = false }
                    }
                }
            )
        }

        composable(Routes.ACADEMY_PROPOSAL_STATUS) {
            AcademyProposalStatusScreen(
                onProposalClick = {}
            )
        }

        composable(Routes.ACADEMY_MY_PAGE) {
            AcademyMyPageScreen(
                academyName  = "강남수학학원",
                academyEmail = "academy@test.com",
                onLogout     = {
                    navController.navigate(Routes.LOGIN) {
                        popUpTo(Routes.ACADEMY_HOME) { inclusive = true }
                    }
                }
            )
        }

        composable(Routes.ACADEMY_CHAT_LIST) {
            AcademyChatListScreen(
                onChatClick = { id ->
                    navController.navigate("academy_chat_room/$id")
                }
            )
        }

        composable(Routes.ACADEMY_CHAT_ROOM) { backStackEntry ->
            val chatRoomId = backStackEntry.arguments?.getString("chatRoomId")?.toIntOrNull() ?: 1
            AcademyChatRoomScreen(
                chatRoomId  = chatRoomId,
                studentInfo = "홍길동 (고1·수학)",
                onBack      = { navController.popBackStack() }
            )
        }
    }
}