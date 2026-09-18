package com.pai.personalai

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.pai.personalai.presentation.navigation.PAiNavGraph
import com.pai.personalai.presentation.screens.settings.SettingsViewModel
import com.pai.personalai.presentation.theme.PAiTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val settingsViewModel: SettingsViewModel = hiltViewModel()
            val uiState by settingsViewModel.uiState.collectAsState()

            PAiTheme(themeMode = uiState.themeMode) {
                Surface(modifier = Modifier.fillMaxSize()) {
                    PAiNavGraph()
                }
            }
        }
    }
}
