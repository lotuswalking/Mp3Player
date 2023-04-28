package com.selfdev.mp3player.data

import InsertAllAsyncTask
import android.content.Context
import android.provider.MediaStore
import android.widget.Toast
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject



class ContentResolverHelper @Inject constructor(@ApplicationContext val context: Context) {

    fun searchAllAudios() {
        val audioList = mutableListOf<AudioFile>()
        val audioFileDao: AudioFileDao = AudioDatabase.getDatabase(context = context).audioFileDao()

        val uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
        val projection = arrayOf(
            MediaStore.Audio.Media._ID,
            MediaStore.Audio.Media.TITLE,
            MediaStore.Audio.Media.ARTIST,
            MediaStore.Audio.Media.ALBUM,
            MediaStore.Audio.Media.DURATION,
            MediaStore.Audio.Media.DATA
        )

        val sortOrder = "${MediaStore.Audio.Media.TITLE} ASC"

        val selection = "${MediaStore.Audio.Media.IS_MUSIC} != 0"

        context.contentResolver.query(uri, projection, selection, null, sortOrder)?.use { cursor ->
            val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID)
            val titleColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE)
            val durationColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION)
            val dataColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA)

            while (cursor.moveToNext()) {
                val id = cursor.getLong(idColumn)
                val title = cursor.getString(titleColumn)
                val duration = cursor.getLong(durationColumn)
                val data = cursor.getString(dataColumn)

                val audio = AudioFile(id, title, duration, data)
                audioList.add(audio)
            }
            Toast.makeText(
                context,
                "there are ${audioList.size} audio files in your system!",
                Toast.LENGTH_LONG
            ).show()
            println("there are ${audioList.size} audio files in your system")
            InsertAllAsyncTask(audioFileDao, audioList).execute()
        }

    }
}