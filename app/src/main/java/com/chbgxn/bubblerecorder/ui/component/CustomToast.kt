package com.chbgxn.bubblerecorder.ui.component


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun CustomToast(message: String){
    Box(
        modifier = Modifier
            .size(width = 200.dp, height = 50.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(Color.Blue)
            .padding(8.dp),
        contentAlignment = Alignment.Center,
    ){
        Text(
            text = message,
            color = Color.White
        )
    }
}