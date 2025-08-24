package com.chbgxn.bubblerecorder.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavHostController
import com.chbgxn.bubblerecorder.state.RecordListViewModel
import state.ToastStateViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecordListScreen(
    navController: NavHostController,
    recordListVM: RecordListViewModel,
    toastStateVM: ToastStateViewModel
) {
    val recordList by recordListVM.recordList.collectAsState()
    var showDialog by remember { mutableStateOf(false) }
    var inputText by remember { mutableStateOf("") }

    LazyColumn (
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        items(recordList){ record ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, Color.Blue, shape = RoundedCornerShape(18.dp))
                    .padding(8.dp)
                    .clickable(
                        onClick = {
                            navController.navigate("recordDetail/${record.rid}")
                        }
                    )
            ){
                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.Top
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = record.name,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Row {
                            IconButton(
                                onClick = {
                                    showDialog = true
                                }
                            ) {
                                Icon(
                                    Icons.Default.Edit,
                                    contentDescription = null
                                )
                            }

                            IconButton(
                                onClick = {
                                    val toDelete = recordList.find { it.rid == record.rid }
                                    if(toDelete != null){
                                        recordListVM.remove(toDelete)
                                    }
                                }
                            ) {
                                Icon(
                                    Icons.Default.Delete,
                                    contentDescription = null
                                )
                            }
                        }
                    }
                    Text(
                        text = "时长：${record.duration / 1000}s",
                        fontSize = 16.sp
                    )
                }
            }
            if(showDialog){
                BasicAlertDialog(
                    onDismissRequest = { showDialog = false},
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White),
                    properties = DialogProperties(),
                    content = {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth(),
                            verticalArrangement = Arrangement.spacedBy(
                                8.dp,
                                Alignment.CenterVertically
                            ),
                            horizontalAlignment = Alignment.CenterHorizontally

                        ){
                            Text(
                                text = "输入新名字",
                                fontSize = 16.sp
                            )
                            TextField(
                                value = inputText,
                                onValueChange = {inputText = it},
                                placeholder = { Text("请输入") }
                            )
                            TextButton(
                                onClick = {
                                    val toEdit = recordList.find{it.rid == record.rid}
                                    if(toEdit != null){
                                        val newRecord = toEdit.copy(name = inputText)
                                        recordListVM.add(newRecord)
                                        toastStateVM.showToast("修改成功")
                                    }
                                    showDialog = false
                                }
                            ){
                                Text(
                                    text = "确定",
                                    fontSize = 16.sp
                                )
                            }
                            TextButton(
                                onClick = {
                                    inputText = ""
                                    showDialog = false
                                }
                            ) {
                                Text(
                                    text = "取消",
                                    fontSize = 16.sp
                                )
                            }
                        }
                    }
                )
            }
        }
    }
}
