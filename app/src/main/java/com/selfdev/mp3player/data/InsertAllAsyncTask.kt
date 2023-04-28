import android.os.AsyncTask
import com.selfdev.mp3player.data.AudioFile
import com.selfdev.mp3player.data.AudioFileDao

class InsertAllAsyncTask(private val dao: AudioFileDao, private val entities: List<AudioFile>) :
    AsyncTask<Unit, Unit, Unit>() {

    override fun doInBackground(vararg params: Unit?) {
        dao.insertAll(entities)
    }
}
