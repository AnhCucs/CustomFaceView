package com.drawap.library

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View

class HappyView : View {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        setupAttributes(context = context, attrs = attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttrs: Int) : super(
        context,
        attrs,
        defStyleAttrs
    ) {
        setupAttributes(context = context, attrs = attrs)
    }

    private var paint = Paint()
    private var faceColor = DEFAULT_FACE_COLOR
    private var eyesColor = DEFAULT_EYES_COLOR
    private var mouthColor = DEFAULT_MOUTH_COLOR
    private var borderColor = DEFAULT_BORDER_COLOR
    private var borderWidth = DEFAULT_BORDER_WIDTH
    private var size = 0
    private val mouthPath = Path()
    var happinessState = StateFace.HAPPY
        set(state) {
            field = state
            // 4
            invalidate()
        }

    init {
        paint.isAntiAlias = true
    }

    private fun setupAttributes(context: Context, attrs: AttributeSet) {
        val typedArray = context.theme.obtainStyledAttributes(
            attrs, R.styleable.EmotionalFaceView,
            0, 0
        )

        happinessState = StateFace.values()[typedArray.getInt(
            R.styleable.EmotionalFaceView_stateFace,
            happinessState.ordinal
        )]
        faceColor = typedArray.getColor(R.styleable.EmotionalFaceView_faceColor, DEFAULT_FACE_COLOR)
        eyesColor = typedArray.getColor(R.styleable.EmotionalFaceView_eyesColor, DEFAULT_EYES_COLOR)
        mouthColor =
            typedArray.getColor(R.styleable.EmotionalFaceView_mouthColor, DEFAULT_MOUTH_COLOR)
        borderColor =
            typedArray.getColor(R.styleable.EmotionalFaceView_borderColor, DEFAULT_BORDER_COLOR)
        borderWidth = typedArray.getDimension(
            R.styleable.EmotionalFaceView_borderWidthFace,
            DEFAULT_BORDER_WIDTH
        )

        typedArray.recycle()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        size = measuredWidth.coerceAtMost(measuredHeight)
        setMeasuredDimension(size, size)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        drawFaceBackground(canvas)
        drawEyes(canvas)
        drawMouth(canvas)
    }

    private fun drawFaceBackground(canvas: Canvas) {
        paint.apply {
            color = faceColor
            // style này cho phép vẽ full hết hình với màu được fill hết vào size hình.
            style = Paint.Style.FILL
        }

        val radius = size / 2f
        canvas.drawCircle(size / 2f, size / 2f, radius, paint)

        paint.apply {
            color = borderColor
            // style này cho phép vẽ đường viền bo chứ không vẽ hết cả hình.
            style = Paint.Style.STROKE
            strokeWidth = borderWidth
        }
        canvas.drawCircle(size / 2f, size / 2f, radius - borderWidth / 2f, paint)
    }

    private fun drawEyes(canvas: Canvas) {
        paint.apply {
            color = eyesColor
            style = Paint.Style.FILL
        }

        val leftEye = RectF(size * 0.32f, size * 0.23f, size * 0.43f, size * 0.50f)
        canvas.drawOval(leftEye, paint)

        val rightEye = RectF(size * 0.57f, size * 0.23f, size * 0.68f, size * 0.50f)
        canvas.drawOval(rightEye, paint)
    }

    private fun drawMouth(canvas: Canvas) {
        // Clear
        mouthPath.reset()
        paint.apply {
            color = mouthColor
            style = Paint.Style.FILL
        }

        // điểm bắt đầu
        mouthPath.moveTo(size * 0.22f, size * 0.7f) // lineTo vẽ đường thẳng

        if (happinessState == StateFace.HAPPY) {
            // Happy mouth path
            mouthPath.quadTo(size * 0.5f, size * 0.80f, size * 0.78f, size * 0.7f)
            mouthPath.quadTo(size * 0.5f, size * 0.90f, size * 0.22f, size * 0.7f)
        } else {
            // Sad mouth path
            mouthPath.quadTo(size * 0.5f, size * 0.50f, size * 0.78f, size * 0.7f)
            mouthPath.quadTo(size * 0.5f, size * 0.60f, size * 0.22f, size * 0.7f)
        }
        // quadTo() vẽ đường cong dựa vào vị trí tọa độ của 2 điểm
        canvas.drawPath(mouthPath, paint)
    }

    companion object {
        private const val DEFAULT_FACE_COLOR = Color.YELLOW
        private const val DEFAULT_EYES_COLOR = Color.BLACK
        private const val DEFAULT_MOUTH_COLOR = Color.BLACK
        private const val DEFAULT_BORDER_COLOR = Color.BLACK
        private const val DEFAULT_BORDER_WIDTH = 4.0f
    }
}