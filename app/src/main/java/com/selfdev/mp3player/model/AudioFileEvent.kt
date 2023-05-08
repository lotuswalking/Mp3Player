package com.selfdev.mp3player.model

import com.selfdev.mp3player.data.AudioFile

sealed interface AudioFileEvent {
    object SaveAudioFile: AudioFileEvent
    data class DeleteAudioFile(val audioFile: AudioFile) : AudioFileEvent
    object ScanAudioFile : AudioFileEvent
}