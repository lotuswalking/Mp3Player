package com.selfdev.mp3player.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_table")
data class AudioFile(
    @PrimaryKey(autoGenerate = true)
    val id:Long,
    val title:String,
    val duration:Long,
    val filePath:String
)
