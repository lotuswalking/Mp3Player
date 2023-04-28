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
    fun addAudioFile(vararg audioFile: AudioFile)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(audioFiles:List<AudioFile>)
    @Delete
    fun deleteAudioFile(vararg audioFile: AudioFile)

    @Query("SELECT * FROM user_table ORDER BY ID ASC")
    fun getAll(): Flow<List<AudioFile>>


}