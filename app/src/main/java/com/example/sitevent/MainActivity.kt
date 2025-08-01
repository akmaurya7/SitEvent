package com.example.sitevent

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import com.example.sitevent.Chat.Navigation.ChatAppNavigation
import com.example.sitevent.ui.Navigation.AppNavigation
import com.example.sitevent.ui.theme.SitEventTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.R)
//    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SitEventTheme {
                AppNavigation()
//           NoteScreen()
//                ChatAppNavigation()
            }
        }
    }
}

