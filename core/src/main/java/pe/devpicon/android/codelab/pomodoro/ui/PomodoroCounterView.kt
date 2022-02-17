package pe.devpicon.android.codelab.pomodoro.ui

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View.OnClickListener
import androidx.constraintlayout.widget.ConstraintLayout
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import pe.devpicon.android.codelab.pomodoro.core.databinding.ViewPomodoroCounterActionBinding

@ExperimentalCoroutinesApi
class PomodoroCounterView
@JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    var count: Int = 1
        set(value) {
            binding.count.text = value.toString()
            field = value
        }
    private val binding: ViewPomodoroCounterActionBinding =
        ViewPomodoroCounterActionBinding.inflate(LayoutInflater.from(context), this, true)

    init {
        count = 1
    }

    fun onCounterClicked() = callbackFlow {
        val decrementListener = OnClickListener {
            count = count.dec()
            trySend(count)
        }

        val incrementListener = OnClickListener {
            count = count.inc()
            trySend(count)
        }
        binding.decrement.setOnClickListener(decrementListener)
        binding.increment.setOnClickListener(incrementListener)

        awaitClose {
            binding.decrement.setOnClickListener(null)
            binding.increment.setOnClickListener(null)
        }
    }
}
