package com.selfdev.mp3player.data

import androidx.lifecycle.LiveData
import kotlinx.coroutines.flow.Flow

class AudioRepository(private val audioFileDao: AudioFileDao) {


    val readAllData : Flow<List<AudioFile>> = audioFileDao.getAll()


}