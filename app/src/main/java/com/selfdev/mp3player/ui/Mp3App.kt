package com.selfdev.mp3player.ui.theme

import android.content.Context
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.selfdev.mp3player.data.AudioFile
import com.selfdev.mp3player.data.AudioFileState
import com.selfdev.mp3player.model.AudioFileEvent
import com.selfdev.mp3player.model.HomeViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Mp3App(
    state: AudioFileState,
    onEvent: (AudioFileEvent) -> Unit

) {
    Scaffold(
        floatingActionButton = {
            Row(modifier = Modifier.padding(16.dp)) {
                FloatingActionButton(onClick = {
                    onEvent(AudioFileEvent.ScanAudioFile)
                }) {
                    Icon(imageVector = Icons.Default.Search, contentDescription = "Scan Audios")

                }
                FloatingActionButton(onClick = {
                    onEvent(AudioFileEvent.SaveAudioFile)
                }) {
                    Icon(imageVector = Icons.Default.Add, contentDescription = "Save Audios")

                }

            }
        }

    ) {
        AudioList(
            contentPadding = it,
            audioList = state.audioFiles,
            onEvent = onEvent
        )
    }

}


@Composable
fun AudioList(
    contentPadding: PaddingValues,
    audioList: List<AudioFile>,
    onEvent: (AudioFileEvent) -> Unit
) {
    LazyColumn(
        contentPadding = contentPadding
    ) {
        items(audioList) { item ->
            AudioRow(item, onEvent = onEvent)

        }
    }
}

@Composable
fun AudioRow(audioFile: AudioFile,
    onEvent: (AudioFileEvent) -> Unit) {
    Row() {
//        Text(audioFile.id.toString(), modifier = Modifier.padding(16.dp))
        Text(audioFile.title, modifier = Modifier.padding(16.dp))
        Text(audioFile.filePath, modifier = Modifier.padding(16.dp))
        IconButton(
            onClick = {
                onEvent(AudioFileEvent.DeleteAudioFile(audioFile))
            },

            ) {
            Icon(imageVector = Icons.Filled.Delete, contentDescription = "delete Item")

        }

    }
}

@Preview
@Composable
fun AudioListPreview() {
    val audioFiles: List<AudioFile> = getTestAudios()
    AudioList(audioList = audioFiles, contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp), onEvent = {})
}

fun getTestAudios() = List(10) { i ->
    AudioFile(i.toLong(), "testAudio#$i", 5000L, "test path #$i")
}