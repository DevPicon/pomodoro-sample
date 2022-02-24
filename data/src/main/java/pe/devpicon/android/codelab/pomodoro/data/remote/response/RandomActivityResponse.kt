package pe.devpicon.android.codelab.pomodoro.data.remote.response

data class RandomActivityResponse(
    val accessibility: Int,
    val activity: String,
    val key: String,
    val link: String,
    val participants: Int,
    val price: Int,
    val type: String
)
