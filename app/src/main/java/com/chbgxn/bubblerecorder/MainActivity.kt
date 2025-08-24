package com.chbgxn.bubblerecorder

import com.chbgxn.bubblerecorder.util.PermissionManager
import android.Manifest
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.chbgxn.bubblerecorder.ui.component.AppShell
import com.chbgxn.bubblerecorder.ui.theme.BubbleRecorderTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private  lateinit var permissionManager: PermissionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        permissionManager = PermissionManager(this)
        permissionManager.initPermission(
            listOf(
                Manifest.permission.RECORD_AUDIO,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
        )

        enableEdgeToEdge()
        setContent {
            BubbleRecorderTheme {
                AppShell(permissionManager)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    BubbleRecorderTheme {
//        AppShell(permissionManager)
    }
}