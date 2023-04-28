package com.selfdev.mp3player.ui.theme

import android.media.MediaPlayer
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TabPosition
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.selfdev.mp3player.data.AudioFileState

private val emptyTabIndicator: @Composable (List<TabPosition>) -> Unit = {}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AudioFileRow(state: AudioFileState) {

        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ){
            items(state.audioFiles) { audio ->
                Text(text = audio.id.toString(), modifier = Modifier.size(20.dp))
                Text(text = audio.filePath.toString(), modifier = Modifier.size(20.dp))
                IconButton(
                    onClick = {
                        val mediaPlayer = MediaPlayer()
                        mediaPlayer.setDataSource(audio.filePath)
                        mediaPlayer.prepare()
                        mediaPlayer.start()

                    },
                ) {
                    Icon(
                        imageVector = Icons.Filled.PlayArrow,
                        contentDescription = "Play This Music"
                    )
                }
            }
        }


}
