package com.selfdev.mp3player

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.selfdev.mp3player.Model.HomeViewModel
import com.selfdev.mp3player.data.AudioDatabase
import com.selfdev.mp3player.data.AudioFileState

class MainActivity : ComponentActivity() {

    private val db by lazy {
        Room.databaseBuilder(
            applicationContext,
            AudioDatabase::class.java,
            "audio.db"
        ).build()
    }

    private val myview by viewModels<HomeViewModel>(
        factoryProducer = {
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return HomeViewModel(db.audioFileDao()) as T
                }
            }
        }
    )
    private lateinit var context: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        context = this
        val result = ContextCompat.checkSelfPermission(
            this@MainActivity,
            Manifest.permission.READ_MEDIA_AUDIO
        )

        if (result !== PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this@MainActivity,
                    Manifest.permission.READ_MEDIA_AUDIO
                )
            ) {
                Toast.makeText(
                    this@MainActivity, "Storage permission is requires,please allow from settings",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                Toast.makeText(this@MainActivity, "需要授予读取本地音频的权限", Toast.LENGTH_SHORT)
                    .show()
                ActivityCompat.requestPermissions(
                    this@MainActivity,
                    arrayOf(Manifest.permission.READ_MEDIA_AUDIO),
                    1
                )
            }
        }

        super.onCreate(savedInstanceState)
        setContent {
            val state by myview.state.collectAsState()
            Mp3App(state, context)
        }

    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun Mp3App(
        state: AudioFileState,
        context: Context
    ) {
        val navController = rememberNavController()

        var currentScreen: NavDestination by remember { mutableStateOf(Main) }
        Scaffold(
            topBar = {
                MyTabRow(
                    allScreen = AllScreen,
                    currentScreen = currentScreen
                )
            }
        ) {
            it
            Navigation(
                state = state,
                navController = navController,
                context = context,
                myview,
                modifier = Modifier.padding(it)
            )

        }
    }
}

