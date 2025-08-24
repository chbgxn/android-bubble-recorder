package data.repository

import android.app.Application
import androidx.room.Room
import com.chbgxn.bubblerecorder.data.repository.RecordFileDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import javax.inject.Singleton
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(app: Application): AppDatabase =
         Room.databaseBuilder(
            app,
            AppDatabase::class.java,
            "record_db"
        ).build()

    @Provides
    fun provideRecordFileDao(db: AppDatabase): RecordFileDao = db.recordFileDao()
}

