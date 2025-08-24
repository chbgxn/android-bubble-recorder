package data.repository

import androidx.room.Database
import androidx.room.RoomDatabase
import com.chbgxn.bubblerecorder.data.repository.RecordFileDao
import data.model.RecordFile

@Database(entities = [RecordFile::class], version = 1,exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun recordFileDao(): RecordFileDao
}
