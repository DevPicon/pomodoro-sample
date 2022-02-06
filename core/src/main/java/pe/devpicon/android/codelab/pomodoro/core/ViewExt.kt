package pe.devpicon.android.codelab.pomodoro.core

import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar

fun Fragment.showSnackBar(
    message: String,
    duration: Int,
    @ColorRes color: Int
) {
    view?.let {
        val snackBar = Snackbar.make(it, message, duration)
        context?.let {
            snackBar.view.setBackgroundColor(ContextCompat.getColor(it, color))
        }
        snackBar.show()
    }
}

fun Fragment.showSnackBarError(
    message: String,
    duration: Int
) {
    showSnackBar(message, duration, com.google.android.material.R.color.design_default_color_error)
}
