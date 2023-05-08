package com.selfdev.mp3player.model

import android.app.Application
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.selfdev.mp3player.data.AudioDatabase
import com.selfdev.mp3player.data.AudioFile
import com.selfdev.mp3player.data.AudioFileState
import com.selfdev.mp3player.data.ContentResolverHelper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch


class HomeViewModel(
    application: Application
) : AndroidViewModel(application) {

    private val _audioFileDao = AudioDatabase.getDatabase(getApplication()).audioFileDao()
    private val _audioFile = _audioFileDao.getAll()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())


    private val _state = MutableStateFlow(AudioFileState())
    val state = combine(_state, _audioFile) { state, audioFiles ->
        state.copy(
            audioFiles = audioFiles
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), AudioFileState())

    private val _testAudioList = getTestAudios().toMutableStateList()
    val testAudioList: List<AudioFile> get() = _testAudioList
    private fun getTestAudios() = List(10) { i ->
        AudioFile(i.toLong(), "testAudio#$i", 5000L, "test path #$i")
    }

    fun onEvent(event: AudioFileEvent) {
        when(event) {
            is AudioFileEvent.DeleteAudioFile ->
                viewModelScope.launch {
                    _audioFileDao.deleteAudioFile(event.audioFile)
                }

            is AudioFileEvent.SaveAudioFile ->
                viewModelScope.launch {
                    _audioFileDao.addAudioFile(AudioFile(-1L,"testAudio",500L,"testPath"))
                }
            is AudioFileEvent.ScanAudioFile ->
            {
                val audioFiles = ContentResolverHelper(getApplication()).searchAllAudios()
                viewModelScope.launch {
                    _audioFileDao.insertAll(audioFiles)
                }
            }

        }
    }
}