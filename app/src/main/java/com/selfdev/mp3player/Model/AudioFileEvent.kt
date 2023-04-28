package com.selfdev.mp3player.Model

import com.selfdev.mp3player.data.AudioFile

sealed interface AudioFileEvent {
    object SaveAudioFile: AudioFileEvent
    data class DeleteAudioFile(val audioFile: AudioFile) : AudioFileEvent
    class  ScanAudioFile():AudioFileEvent
}