package com.chbgxn.bubblerecorder.state

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import data.model.RecordFile
import com.chbgxn.bubblerecorder.data.repository.RecordFileDao
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class RecordListViewModel @Inject constructor(
    private val dao: RecordFileDao
): ViewModel(){
    val recordList = dao.getAll()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    fun add(recordFile: RecordFile) = viewModelScope.launch {
        dao.insert(recordFile)
    }

    fun remove(recordFile: RecordFile) = viewModelScope.launch {
        dao.remove(recordFile)
        val file = File(recordFile.path)
        if(file.exists()) file.delete()
    }
}