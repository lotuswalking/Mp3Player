package com.selfdev.mp3player

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.selfdev.mp3player.model.HomeViewModel
import com.selfdev.mp3player.ui.theme.Mp3App

class MainActivity : ComponentActivity() {

    private val myView: HomeViewModel by viewModels<HomeViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val state by myView.state.collectAsState()
            Mp3App(
                state = state, onEvent = myView::onEvent
            )
        }

    }


}

