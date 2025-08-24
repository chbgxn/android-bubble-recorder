package data.model

data class ToastState (
    val message: String = "",
    val isShow : Boolean = false
){
    fun copyWith(
        message: String? = null,
        isShow: Boolean? = null
    ): ToastState{
        return ToastState(
            message = message ?: this.message,
            isShow = isShow ?: this.isShow
        )
    }
}