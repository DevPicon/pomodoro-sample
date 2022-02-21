package pe.devpicon.android.codelab.pomodoro.timer.customview

import android.content.Context
import android.content.res.Resources
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.os.Bundle
import android.os.Parcelable
import android.util.AttributeSet
import android.view.View
import pe.devpicon.android.codelab.pomodoro.timer.R
import java.text.DecimalFormat
import kotlin.math.cos

class ArcProgress @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var paint: Paint? = null
    private val rectF = RectF()
    var progress = 0f
        set(value) {
            field = java.lang.Float.valueOf(DecimalFormat("#.##").format(value.toDouble()))
            if (field > max) {
                field %= max.toFloat()
            }
            invalidate()
        }

    private var max = 0
        set(max) {
            if (max > 0) {
                field = max
                invalidate()
            }
        }
    private var finishedStrokeColor = 0
        set(value) {
            field = value
            this.invalidate()
        }
    private var unfinishedStrokeColor = 0
        set(value) {
            field = value
            this.invalidate()
        }
    private var arcAngle = 0f
        set(value) {
            field = value
            this.invalidate()
        }

    private var arcBottomHeight = 0f
    private var arcStrokeWidth = 0f
        set(value) {
            field = value
            this.invalidate()
        }
    private val defaultFinishedColor = Color.WHITE
    private val defaultUnfinishedColor = Color.rgb(72, 106, 176)
    private val defaultStrokeWidth: Float = dp2px(resources, 4f)
    private val defaultMax = 100
    private val defaultArcAngle = 360F
    private val minSize: Int = dp2px(resources, 100f).toInt()
    private fun initByAttributes(attributes: TypedArray) {
        finishedStrokeColor =
            attributes.getColor(R.styleable.ArcProgress_arc_finished_color, defaultFinishedColor)
        unfinishedStrokeColor = attributes.getColor(
            R.styleable.ArcProgress_arc_unfinished_color,
            defaultUnfinishedColor
        )
        arcAngle = attributes.getFloat(R.styleable.ArcProgress_arc_angle, defaultArcAngle)
        max = attributes.getInt(R.styleable.ArcProgress_arc_max, defaultMax)
        progress = (attributes.getFloat(R.styleable.ArcProgress_arc_progress, 0f))
        arcStrokeWidth =
            attributes.getDimension(R.styleable.ArcProgress_arc_stroke_width, defaultStrokeWidth)
    }

    private fun initPainters() {
        paint = Paint()
        paint?.apply {
            color = defaultUnfinishedColor
            isAntiAlias = true
            strokeWidth = arcStrokeWidth
            style = Paint.Style.STROKE
            strokeCap = Paint.Cap.ROUND
        }
    }

    override fun invalidate() {
        initPainters()
        super.invalidate()
    }

    override fun getSuggestedMinimumHeight(): Int {
        return minSize
    }

    override fun getSuggestedMinimumWidth(): Int {
        return minSize
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec)
        val width = MeasureSpec.getSize(widthMeasureSpec)
        rectF[arcStrokeWidth / 2f, arcStrokeWidth / 2f, width - arcStrokeWidth / 2f] =
            MeasureSpec.getSize(heightMeasureSpec) - arcStrokeWidth / 2f
        val radius = width / 2f
        val angle = (360 - arcAngle) / 2f
        arcBottomHeight = radius * (1 - cos(angle / 180 * Math.PI)).toFloat()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val startAngle = 270 - arcAngle / 2f
        val finishedSweepAngle = progress / max.toFloat() * arcAngle
        var finishedStartAngle = startAngle
        if (progress == 0f) finishedStartAngle = 0.01f
        paint!!.color = unfinishedStrokeColor
        canvas.drawArc(rectF, startAngle, arcAngle, false, paint!!)
        paint!!.color = finishedStrokeColor
        canvas.drawArc(rectF, finishedStartAngle, finishedSweepAngle, false, paint!!)

        if (arcBottomHeight == 0f) {
            val radius = width / 2f
            val angle = (360 - arcAngle) / 2f
            arcBottomHeight = radius * (1 - cos(angle / 180 * Math.PI)).toFloat()
        }
    }

    override fun onSaveInstanceState(): Parcelable {
        val bundle = Bundle()
        bundle.putParcelable(INSTANCE_STATE, super.onSaveInstanceState())
        bundle.putFloat(INSTANCE_STROKE_WIDTH, arcStrokeWidth)
        bundle.putFloat(INSTANCE_PROGRESS, progress)
        bundle.putInt(INSTANCE_MAX, max)
        bundle.putInt(INSTANCE_FINISHED_STROKE_COLOR, finishedStrokeColor)
        bundle.putInt(INSTANCE_UNFINISHED_STROKE_COLOR, unfinishedStrokeColor)
        bundle.putFloat(INSTANCE_ARC_ANGLE, arcAngle)
        return bundle
    }

    override fun onRestoreInstanceState(state: Parcelable) {
        if (state is Bundle) {
            arcStrokeWidth = state.getFloat(INSTANCE_STROKE_WIDTH)
            max = state.getInt(INSTANCE_MAX)
            progress = state.getFloat(INSTANCE_PROGRESS)
            finishedStrokeColor = state.getInt(INSTANCE_FINISHED_STROKE_COLOR)
            unfinishedStrokeColor = state.getInt(INSTANCE_UNFINISHED_STROKE_COLOR)
            initPainters()
            super.onRestoreInstanceState(state.getParcelable(INSTANCE_STATE))
            return
        }
        super.onRestoreInstanceState(state)
    }

    companion object {
        private const val INSTANCE_STATE = "saved_instance"
        private const val INSTANCE_STROKE_WIDTH = "stroke_width"
        private const val INSTANCE_PROGRESS = "progress"
        private const val INSTANCE_MAX = "max"
        private const val INSTANCE_FINISHED_STROKE_COLOR = "finished_stroke_color"
        private const val INSTANCE_UNFINISHED_STROKE_COLOR = "unfinished_stroke_color"
        private const val INSTANCE_ARC_ANGLE = "arc_angle"

        fun dp2px(resources: Resources, dp: Float): Float {
            val scale = resources.displayMetrics.density
            return dp * scale + 0.5f
        }
    }

    init {
        val attributes =
            context.theme.obtainStyledAttributes(attrs, R.styleable.ArcProgress, defStyleAttr, 0)
        initByAttributes(attributes)
        attributes.recycle()
        initPainters()
    }
}
