package com.chbgxn.bubblerecorder.util

import android.content.pm.PackageManager
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat

class PermissionManager(private val activity: ComponentActivity) {
    private val launchers = mutableMapOf<String, ActivityResultLauncher<String>>()
    private val callbacks = mutableMapOf<String, (Boolean) -> Unit>()

    fun initPermission(permissions: List<String>){
        permissions.forEach { permission ->
            if(!launchers.containsKey(permission)){
                val launcher = activity.registerForActivityResult(
                    ActivityResultContracts.RequestPermission()
                ) { granted ->
                    callbacks[permission]?.invoke(granted)
                    callbacks.remove(permission)
                }
                launchers[permission] = launcher
            }
        }
    }

    fun requestPermission(
        permission: String,
        onResult: (Boolean) -> Unit
    ) {
        val granted = ContextCompat.checkSelfPermission(activity, permission) ==
                PackageManager.PERMISSION_GRANTED
        if(granted){
            onResult(true)
        }
        else{
            callbacks[permission] = onResult
            launchers[permission]?.launch(permission)
        }
    }
}
