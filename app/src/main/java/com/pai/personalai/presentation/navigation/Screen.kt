package com.pai.personalai.presentation.navigation

sealed class Screen(val route: String) {
    data object Splash : Screen("splash")
    data object Login : Screen("login")
    data object ServerConfig : Screen("server_config")
    data object Chat : Screen("chat")
    data object Settings : Screen("settings")
}
