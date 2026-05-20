package com.project.academy_hunt.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.project.academy_hunt.data.auth.AuthRepository
import com.project.academy_hunt.data.core.RetrofitClient
import com.project.academy_hunt.data.core.TokenDataStore
import com.project.academy_hunt.ui.auth.LoginScreen
import com.project.academy_hunt.ui.auth.RegisterScreen
import com.project.academy_hunt.ui.onboarding.OnboardingScreen
import com.project.academy_hunt.ui.splash.SplashScreen
import com.project.academy_hunt.viewmodel.LoginViewModel
import com.project.academy_hunt.viewmodel.OnboardingViewModel
import com.project.academy_hunt.viewmodel.RegisterViewModel
import com.project.academy_hunt.viewmodel.ViewModelFactory

object Routes {
    const val SPLASH       = "splash"
    const val ONBOARDING   = "onboarding"
    const val LOGIN        = "login"
    const val REGISTER     = "register"
    const val STUDENT_HOME = "student_home"
    const val ACADEMY_HOME = "academy_home"
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
    }
}