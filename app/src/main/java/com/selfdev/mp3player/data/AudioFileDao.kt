package com.selfdev.mp3player.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface AudioFileDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addAudioFile(vararg audioFile: AudioFile)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(audioFiles:List<AudioFile>)
    @Delete
    suspend fun deleteAudioFile(audioFile: AudioFile)

    @Query("SELECT * FROM Audio_File ORDER BY id ASC")
    fun getAll(): Flow<List<AudioFile>>


}