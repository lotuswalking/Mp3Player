package com.selfdev.mp3player.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [AudioFile::class], version = 1, exportSchema = false)
abstract class AudioDatabase: RoomDatabase() {
    abstract fun audioFileDao() : AudioFileDao
    companion object {
        @Volatile
        private var INSTANCE: AudioDatabase? = null
        fun getDatabase(context: Context): AudioDatabase {
            var tempInstance = INSTANCE
            if(tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instant = Room.databaseBuilder(
                    context.applicationContext,
                    AudioDatabase:: class.java,
                    name = "audio_database"
                ).build()
                INSTANCE = instant
                return instant
            }
        }
    }
}