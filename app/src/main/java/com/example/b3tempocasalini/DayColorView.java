package com.example.b3tempocasalini;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

import androidx.core.content.ContextCompat;

/**
 * TODO: document your custom view class.
 */
public class DayColorView extends View {
    private static final float CIRCLE_SCALE = 0.9f; // circle will occupy 90% of the room's view.
    private String captionText;
    private int captionTextColor = Color.BLACK;
    private float captionTextSize = 0;
    private int dayCircleColor = Color.GRAY;

    private Context context;
    private Paint circlePaint;

    private TextPaint textPaint;
    private float mTextWidth;
    private float mTextHeight;

    public DayColorView(Context context) {
        super(context);
        init(context, null, 0);
    }

    public DayColorView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public DayColorView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs, defStyle);
    }

    private void init(Context context, AttributeSet attrs, int defStyle) {
        this.context = context;

        // Load attributes
        final TypedArray a = getContext().obtainStyledAttributes(
                attrs, R.styleable.DayColorView, defStyle, 0);

        try {
            captionText = a.getString(R.styleable.DayColorView_captionText);
            captionTextColor = a.getColor(R.styleable.DayColorView_captionTextColor, captionTextColor);
            captionTextSize = a.getDimension(R.styleable.DayColorView_captionTextSize, getResources().getDimension(R.dimen.tempo_days_left_text_size));
            dayCircleColor = a.getColor(R.styleable.DayColorView_dayCircleColor, ContextCompat.getColor(context, R.color.tempo_undecided_day_bg));
        }
        finally {
            a.recycle();
        }

        // Set up a default TextPaint object
        textPaint = new TextPaint();
        setTextPaintAndMeasurements();

        // set up a default Paint object
        circlePaint = new Paint();
        setCirclePaint();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // TODO: consider storing these as member variables to reduce
        // allocations per draw cycle.
        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        int paddingRight = getPaddingRight();
        int paddingBottom = getPaddingBottom();

        int contentWidth = getWidth() - paddingLeft - paddingRight;
        int contentHeight = getHeight() - paddingTop - paddingBottom;

        float radius = Math.min(contentHeight, contentWidth) * 0.5f * CIRCLE_SCALE;
        canvas.drawCircle(contentWidth * 0.5f, contentHeight * 0.5f, radius, circlePaint);

        // Draw the text.
        canvas.drawText(captionText,
                paddingLeft + (contentWidth - mTextWidth) / 2,
                paddingTop + (contentHeight + mTextHeight) / 2,
                textPaint);

    }


    private void setTextPaintAndMeasurements() {
        // set-up a default TextPaint object.
        textPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        textPaint.setTextAlign(Paint.Align.LEFT);
        textPaint.setTextSize(captionTextSize);
        textPaint.setColor(captionTextColor);

        // compute the dimensions that will be used to draw the text.
        mTextWidth = textPaint.measureText(captionText);
        Paint.FontMetrics fontMetrics = textPaint.getFontMetrics();
        mTextHeight = fontMetrics.bottom;
    }


    public void setCaptionText(String text) {
        captionText = text;
        setTextPaintAndMeasurements();
        invalidate();
    }


    private void setCirclePaint() {
        // set-up a paint object to draw a circle.
        circlePaint.setStyle(Paint.Style.FILL);
        circlePaint.setColor(dayCircleColor);
    }


    public void setDayCircleColor(TempoColor color) {
        dayCircleColor = ContextCompat.getColor(context, color.getResId());
        setCirclePaint();
        invalidate();
    }


}