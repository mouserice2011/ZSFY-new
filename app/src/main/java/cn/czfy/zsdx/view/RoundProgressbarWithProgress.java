package cn.czfy.zsdx.view;

/**
 * Created by sinyu on 2017/5/3.
 */

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;

import cn.czfy.zsdx.R;


/**
 * Created by kuangxiaoguo on 16/9/8.
 *
 * Բ�ν�����
 */
public class RoundProgressbarWithProgress extends HorizontalProgressbarWithProgress {

    private int mRadius = dp2px(30);
    private int mMaxPaintWidth;

    public RoundProgressbarWithProgress(Context context) {
        this(context, null);
    }

    public RoundProgressbarWithProgress(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RoundProgressbarWithProgress(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.RoundProgressbarWithProgress);
        mRadius = (int) ta.getDimension(R.styleable.RoundProgressbarWithProgress_radius_1, mRadius);
        ta.recycle();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
    }

    @Override
    protected synchronized void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        /**
         * ��ȡ���Ļ��ƿ��
         */
        mMaxPaintWidth = Math.max(mReachHeight, mUNReachHeight);
        //Ĭ���ĸ�paddingһ��
        int expect = mRadius * 2 + mMaxPaintWidth + getPaddingLeft() + getPaddingRight();

        /**
         * resolveSize()�����������������Լ�����MeasureSpec�Լ�����view�Ŀ�͸�
         */
        int width = resolveSize(expect, widthMeasureSpec);
        int height = resolveSize(expect, heightMeasureSpec);

        int realWidth = Math.min(width, height);
        mRadius = (realWidth - getPaddingLeft() - getPaddingRight() - mMaxPaintWidth) / 2;
        setMeasuredDimension(realWidth, height);
    }

    public    String text;
    @Override
    protected synchronized void onDraw(Canvas canvas) {

        float textWidth = mPaint.measureText(text);
        /**
         * descent()�����ֵĵײ�y����, ascent()�����ֶ���y����
         */
        float textHeight = (mPaint.descent() + mPaint.ascent()) / 2;

        canvas.save();
        canvas.translate(getPaddingLeft() + mMaxPaintWidth / 2, getPaddingTop() + mMaxPaintWidth / 2);
        //draw unreach bar
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(mUnReachColor);
        canvas.drawCircle(mRadius, mRadius, mRadius, mPaint);

        //draw reach bar
        mPaint.setColor(mReachColor);
        mPaint.setStrokeWidth(mReachHeight);
        float sweepAngle = getProgress() * 1.0f / getMax() * 360;
        canvas.drawArc(new RectF(0, 0, mRadius * 2, mRadius * 2), 0, sweepAngle, false, mPaint);

        //draw text
        mPaint.setColor(mTextColor);
        mPaint.setTextSize(mTextSize);
        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawText(text, mRadius - textWidth / 2, mRadius - textHeight, mPaint);
        canvas.restore();
    }
}