package com.chbgxn.bubblerecorder.util

import android.content.Context
import android.media.MediaMetadataRetriever
import android.media.MediaRecorder
import android.os.Environment
import data.model.RecordFile
import java.io.File

@Suppress("DEPRECATION")
class AudioRecorder {
    private var recorder: MediaRecorder? = null
    private var filePath: String = ""

    fun startRecording(context: Context) {
        //App 私有的外部存储
        val fileName = "record_${System.currentTimeMillis()}.mp3"
        val dir = context.getExternalFilesDir(Environment.DIRECTORY_MUSIC)
        filePath = File(dir, fileName).absolutePath

        recorder = MediaRecorder().apply{
            setAudioSource(MediaRecorder.AudioSource.MIC)
            setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
            setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
            setOutputFile(filePath)
            prepare()
            start()
        }
    }

    fun stopAndSavaRecording(): RecordFile {
        recorder?.apply {
            stop()
            release()
        }
        recorder = null

        val retriever = MediaMetadataRetriever()
        retriever.setDataSource(filePath)
        val durationStr = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION)
        val duration = durationStr?.toLong() ?: 0L
        retriever.release()

        return RecordFile(
            rid = System.currentTimeMillis().toString(),
            path = filePath,
            name = "record_${System.currentTimeMillis()}",
            duration = duration
        )
    }
}
