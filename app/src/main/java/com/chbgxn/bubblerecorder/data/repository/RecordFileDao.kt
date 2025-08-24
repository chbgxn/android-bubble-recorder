package com.chbgxn.bubblerecorder.data.repository

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import data.model.RecordFile
import kotlinx.coroutines.flow.Flow

@Dao
interface RecordFileDao {
    @Query("SELECT * FROM recordfiles ORDER BY createAt DESC")
    fun getAll(): Flow<List<RecordFile>>

    @Query("SELECT * FROM recordfiles WHERE rid = :rid LIMIT 1")
    fun getByRid(rid: String): Flow<RecordFile?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(recordFile: RecordFile)

    @Delete
    suspend fun remove(recordFile: RecordFile)
}