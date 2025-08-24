package com.chbgxn.bubblerecorder.ui.component

import com.chbgxn.bubblerecorder.util.PermissionManager
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import state.ToastStateViewModel
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.chbgxn.bubblerecorder.ui.screen.RecordDetailScreen
import com.chbgxn.bubblerecorder.ui.screen.RecordListScreen
import com.chbgxn.bubblerecorder.ui.screen.RecordScreen
import com.chbgxn.bubblerecorder.state.RecordListViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppShell(
    permissionManager: PermissionManager,
    navController: NavHostController = rememberNavController(),
    toastStateVM: ToastStateViewModel = viewModel(),
    recordListVM: RecordListViewModel = hiltViewModel(),
){
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route
    val toastState by toastStateVM.toastState.collectAsState()

    Box(modifier = Modifier.fillMaxSize()){
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            when{
                                currentRoute == "record" -> "录音机"
                                currentRoute == "recordList" -> "录音列表"
                                currentRoute?.startsWith("recordDetail") == true -> "录音详情"
                                else -> ""
                            }
                        )
                    }
                )
            },
        ){ padding->
            NavHost(
                navController = navController,
                startDestination = "record",
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
            ){
                composable("record"){ RecordScreen(
                    permissionManager,
                    navController,
                    recordListVM,
                    toastStateVM)
                }
                composable("recordList"){ RecordListScreen(
                    navController,
                    recordListVM,
                    toastStateVM)
                }
                composable("recordDetail/{rid}"){ backStackEntry ->
                    val rid = backStackEntry.arguments?.getString("rid")
                    RecordDetailScreen()
                }
            }
        }
        if (toastState.isShow){
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 100.dp),
                contentAlignment = Alignment.TopCenter
            ){
                CustomToast(message = toastState.message)
            }
        }
    }
}

