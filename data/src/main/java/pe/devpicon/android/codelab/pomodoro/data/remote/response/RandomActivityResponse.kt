package pe.devpicon.android.codelab.pomodoro.data.remote.response

data class RandomActivityResponse(
    val accessibility: Double,
    val activity: String,
    val key: String,
    val link: String,
    val participants: Int,
    val price: Double,
    val type: String
)
