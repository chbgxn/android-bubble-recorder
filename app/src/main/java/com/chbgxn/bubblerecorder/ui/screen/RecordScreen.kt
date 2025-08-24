package com.chbgxn.bubblerecorder.ui.screen

import com.chbgxn.bubblerecorder.util.PermissionManager
import android.Manifest
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.chbgxn.bubblerecorder.state.RecordListViewModel
import state.ToastStateViewModel
import com.chbgxn.bubblerecorder.util.AudioRecorder

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecordScreen(
    permissionManager: PermissionManager,
    navController: NavHostController,
    recordListVM: RecordListViewModel,
    toastStateVM: ToastStateViewModel
) {
    val context = LocalContext.current
    val recorder = remember { AudioRecorder() }
    var isRecording by remember { mutableStateOf(false) }

    Column (
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(
            space = 8.dp,
            alignment = Alignment.CenterVertically
        ),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Button(
            modifier = Modifier
                .size(150.dp),
            onClick = {
               permissionManager.requestPermission(Manifest.permission.RECORD_AUDIO){ granted ->
                   if(granted){
                       if(isRecording){
                           val recordFile = recorder.stopAndSavaRecording()
                           recordListVM.add(recordFile)
                       }
                       else{
                           recorder.startRecording(context)
                       }
                       isRecording = !isRecording
                   }
                   else{
                       toastStateVM.showToast("录音权限被拒绝")
                   }
               }
            }
        ) {
            if(isRecording){
                Icon(
                    Icons.Default.Check,
                    contentDescription = null
                )
            }
            else{
                Icon(
                    Icons.Default.PlayArrow,
                    contentDescription = null
                )
            }

        }
        if(isRecording){
            Text(
                text = "正在录音...",
                fontSize = 16.sp
            )
        }
        else{
            Text(
                text = "点击开始录音",
                fontSize = 16.sp
            )
        }
        TextButton(
            onClick = { navController.navigate("recordList") }
        ) {
            Text(
                text = "查看所有录音",
                fontSize = 16.sp
            )
        }

    }
}
