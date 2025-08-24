package com.chbgxn.bubblerecorder.state

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import data.model.RecordFile
import com.chbgxn.bubblerecorder.data.repository.RecordFileDao
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class RecordDetailViewModel @Inject constructor(
    dao: RecordFileDao,
    savadStateHandle: SavedStateHandle,
): ViewModel(){
    private val rid: String = checkNotNull(savadStateHandle["rid"]).toString()

    val theRecord: StateFlow<RecordFile?> = dao.getByRid(rid).
            stateIn(viewModelScope, SharingStarted.Lazily, null)
}