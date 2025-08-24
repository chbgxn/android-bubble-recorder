package state

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import data.model.ToastState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ToastStateViewModel: ViewModel() {
    private val _toastState = MutableStateFlow(ToastState())
    val toastState: StateFlow<ToastState> = _toastState.asStateFlow()

    fun showToast(message: String){
        _toastState.value = _toastState.value.copyWith(
            message = message,
            isShow = true
        )

        viewModelScope.launch {
            delay(2000)
            _toastState.value = _toastState.value.copyWith(
                message = "",
                isShow = false
            )
        }
    }
}