package com.selfdev.mp3player.Model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.ViewModelFactoryDsl
import com.selfdev.mp3player.data.AudioFile
import com.selfdev.mp3player.data.AudioFileDao
import com.selfdev.mp3player.data.AudioFileState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch


@ViewModelFactoryDsl
class HomeViewModel(
    private val audioFileDao: AudioFileDao
) : ViewModel(){
    private val _audioFile = audioFileDao.getAll()
        .stateIn(viewModelScope,SharingStarted.WhileSubscribed(), emptyList())

    private val _state = MutableStateFlow(AudioFileState())
    val state = combine(_state,_audioFile) { state,audioFiles ->
        state.copy(
            audioFiles = audioFiles
        )
    }.stateIn(viewModelScope,SharingStarted.WhileSubscribed(5000), AudioFileState())

    fun onEvent(event: AudioFileEvent) {
        when(event) {
            is AudioFileEvent.DeleteAudioFile ->
                viewModelScope.launch {
                    audioFileDao.deleteAudioFile(event.audioFile)
                }
            AudioFileEvent.SaveAudioFile ->
                viewModelScope.launch {
                    audioFileDao.addAudioFile(AudioFile(-1L,"testAudio",5000L,"testPath"))
                }

            is AudioFileEvent.ScanAudioFile ->
                viewModelScope.launch {
//                    ContentResolverHelper().searchAllAudios()
                }
        }
    }


}