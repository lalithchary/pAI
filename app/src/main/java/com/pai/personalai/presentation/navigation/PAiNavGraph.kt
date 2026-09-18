package com.pai.personalai.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.pai.personalai.presentation.screens.chat.ChatScreen
import com.pai.personalai.presentation.screens.config.ServerConfigScreen
import com.pai.personalai.presentation.screens.settings.SettingsViewModel
import com.pai.personalai.presentation.screens.splash.SplashScreen
import com.pai.personalai.presentation.screens.splash.SplashViewModel

@Composable
fun PAiNavGraph() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route
    ) {
        composable(Screen.Splash.route) {
            val splashViewModel: SplashViewModel = hiltViewModel()
            SplashScreen(
                onSplashFinished = {
                    val nextRoute = if (splashViewModel.isServerConfigured()) {
                        Screen.Chat.route
                    } else {
                        Screen.ServerConfig.route
                    }
                    navController.navigate(nextRoute) {
                        popUpTo(Screen.Splash.route) { inclusive = true }
                    }
                }
            )
        }

        composable(Screen.ServerConfig.route) {
            ServerConfigScreen(
                onSaved = {
                    navController.navigate(Screen.Chat.route) {
                        popUpTo(Screen.ServerConfig.route) { inclusive = true }
                    }
                },
                onBack = {
                    navController.popBackStack()
                }
            )
        }

        composable(Screen.Chat.route) {
            ChatScreen(
                onNavigateToSettings = {
                    navController.navigate(Screen.Settings.route)
                }
            )
        }

        composable(Screen.Settings.route) {
            val settingsViewModel: SettingsViewModel = hiltViewModel()
            val uiState by settingsViewModel.uiState.collectAsState()

            com.pai.personalai.presentation.screens.settings.SettingsScreen(
                onBack = { navController.popBackStack() },
                onLogout = {
                    navController.navigate(Screen.ServerConfig.route) {
                        popUpTo(0) { inclusive = true }
                    }
                },
                onEditServerConfig = {
                    navController.navigate(Screen.ServerConfig.route)
                },
                viewModel = settingsViewModel
            )
        }
    }
}
