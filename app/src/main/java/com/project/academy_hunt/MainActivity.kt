package com.project.academy_hunt

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.academyhunt.ui.theme.AcademyHuntTheme
import com.project.academy_hunt.data.core.TokenDataStore
import com.project.academy_hunt.navigation.NavGraph

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val tokenDataStore = TokenDataStore(applicationContext)
        setContent {
            AcademyHuntTheme {
                val navController = rememberNavController()
                NavGraph(
                    navController  = navController,
                    tokenDataStore = tokenDataStore
                )
            }
        }
    }
}