package com.example.streesakha

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.streesakha.NotificationChannelConstants.channelDescription
import com.example.streesakha.NotificationChannelConstants.channelId
import com.example.streesakha.NotificationChannelConstants.channelName
import com.example.streesakha.ui.navigation.StreeSakhaApp
import com.example.streesakha.ui.theme.StreeSakhaTheme
import org.koin.androidx.compose.KoinAndroidContext

@Suppress("ConstPropertyName")
object NotificationChannelConstants {
    const val channelId = "1"
    const val channelName = "Mensinator"
    const val channelDescription = "Reminders about upcoming periods"
}

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            StreeSakhaTheme {
                KoinAndroidContext {
                    StreeSakhaApp(onScreenProtectionChanged = { enabled ->
                        handleScreenProtection(enabled)
                    })
                }
            }
        }
        createNotificationChannel(this)
    }

    private fun createNotificationChannel(context: Context) {
        val importance = NotificationManager.IMPORTANCE_HIGH
        val notificationChannel = NotificationChannel(channelId, channelName, importance).apply {
            description = channelDescription
        }

        val notificationManager = context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(notificationChannel)
    }

    private fun handleScreenProtection(isScreenProtectionEnabled: Boolean) {
        Log.d("screenProtectionUI", "protect screen value $isScreenProtectionEnabled")
        // Sets the flags for screen protection if
        // isScreenProtectionEnabled == true
        // If isScreenProtectionEnabled == false it removes the flags
        if (isScreenProtectionEnabled) {
            window?.setFlags(
                WindowManager.LayoutParams.FLAG_SECURE,
                WindowManager.LayoutParams.FLAG_SECURE
            )
        } else {
            window?.clearFlags(WindowManager.LayoutParams.FLAG_SECURE)
        }
    }
}