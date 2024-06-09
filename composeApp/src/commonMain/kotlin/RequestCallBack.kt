data class RequestCallBack(
    val onPositive: () -> Unit = {},
    val onNegative: () -> Unit = {},
)
