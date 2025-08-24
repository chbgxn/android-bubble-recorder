package com.chbgxn.bubblerecorder.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import com.chbgxn.bubblerecorder.state.RecordDetailViewModel
import androidx.media3.common.Player
import kotlinx.coroutines.delay

@Composable
fun RecordDetailScreen(
    recordDetailVM: RecordDetailViewModel = hiltViewModel()
) {
    val record by recordDetailVM.theRecord.collectAsState()
    record?.let {
        val context = LocalContext.current
        val exoPlayer = remember { ExoPlayer.Builder(context).build().apply {
                val mediaItem = MediaItem.fromUri(it.path.toUri())
                setMediaItem(mediaItem)
                prepare()
            }
        }
        var isPlaying by remember { mutableStateOf(false) }
        var progress by remember { mutableFloatStateOf(0f) }

        DisposableEffect(Unit) {
            val listener = object : Player.Listener {
                override fun onIsPlayingChanged(playing: Boolean) {
                    isPlaying = playing
                }
            }
            exoPlayer.addListener(listener)
            onDispose {
                exoPlayer.release()
            }
        }

        LaunchedEffect(exoPlayer)  {
            while (true){
                val duration = exoPlayer.duration.takeIf { it > 0 }?: 1L
                progress = exoPlayer.contentPosition / duration.toFloat()
                delay(500)
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(
                space = 16.dp,
                alignment = Alignment.CenterVertically
            ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // 播放 暂停按钮
            Button (
                onClick = {
                    if (isPlaying) exoPlayer.pause()
                    else exoPlayer.play()
                }
            ) {
                Text(
                    text = if (isPlaying) "暂停" else "播放",
                    fontSize = 16.sp
                )
            }

            // 进度条
            Slider(
                value = progress,
                onValueChange = { value ->
                    progress = value
                },
                onValueChangeFinished = {
                    val duration = exoPlayer.duration
                    if (duration > 0) {
                        exoPlayer.seekTo((progress * duration).toLong())
                    }
                }
            )

            TextButton(
                onClick = {}
            ) {
                Text(
                    text = "裁剪",
                    fontSize = 16.sp
                )
            }
        }
    } ?: Text(
        text = "加载中...",
        fontSize = 16.sp
    )
}
