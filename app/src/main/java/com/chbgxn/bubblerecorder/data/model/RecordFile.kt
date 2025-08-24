package data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recordFiles")
data class RecordFile(
    @PrimaryKey val rid: String,
    val path: String,
    val name: String,
    val createAt: Long = System.currentTimeMillis(),
    val updateAt: Long? = null,
    val duration: Long = 0L
)