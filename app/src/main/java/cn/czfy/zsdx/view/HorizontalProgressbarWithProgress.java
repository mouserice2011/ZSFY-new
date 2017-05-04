package cn.czfy.zsdx.view;

/**
 * Created by sinyu on 2017/5/3.
 */

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.ProgressBar;

import cn.czfy.zsdx.R;


/**
 * Created by kuangxiaoguo on 16/9/8.
 *
 * ˮƽ������
 */
public class HorizontalProgressbarWithProgress extends ProgressBar {

    private static final int DEFAULT_TEXT_SIZE = 10;
    private static final int DEFAULT_TEXT_COLOR = 0xFFFC00D1;
    private static final int DEFAULT_COLOR_UNREACH = 0XFFD3D6DA;
    private static final int DEFAULT_HEIGHT_UNREACH = 2;
    private static final int DEFAULT_COLOR_REACH = DEFAULT_TEXT_COLOR;
    private static final int DEFAULT_HEIGHT_REACH = 2;
    private static final int DEFAULT_TEXT_OFFSET = 10;

    protected int mTextSize = sp2px(DEFAULT_TEXT_SIZE);
    protected int mTextColor = DEFAULT_TEXT_COLOR;
    protected int mUnReachColor = DEFAULT_COLOR_UNREACH;
    protected int mUNReachHeight = dp2px(DEFAULT_HEIGHT_UNREACH);
    protected int mReachColor = DEFAULT_COLOR_REACH;
    protected int mReachHeight = dp2px(DEFAULT_HEIGHT_REACH);
    protected int mTextOffSet = dp2px(DEFAULT_TEXT_OFFSET);

    protected Paint mPaint = new Paint();
    protected int mRealWidth;

    public HorizontalProgressbarWithProgress(Context context) {
        this(context, null);
    }

    public HorizontalProgressbarWithProgress(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HorizontalProgressbarWithProgress(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        obtainStyleAttrs(attrs);
    }

    /**
     * ��ȡ�Զ�������
     */
    private void obtainStyleAttrs(AttributeSet attrs) {
        TypedArray ta = getContext().obtainStyledAttributes(attrs, R.styleable.HorizontalProgressbarWithProgress);
        mTextSize = (int) ta.getDimension(R.styleable.HorizontalProgressbarWithProgress_progress_text_size, mTextSize);
        mTextColor = ta.getColor(R.styleable.HorizontalProgressbarWithProgress_progress_text_color, mTextColor);
        mUnReachColor = ta.getColor(R.styleable.HorizontalProgressbarWithProgress_progress_unreach_color, mUnReachColor);
        mUNReachHeight = (int) ta.getDimension(R.styleable.HorizontalProgressbarWithProgress_progress_unreach_height, mUNReachHeight);
        mReachColor = ta.getColor(R.styleable.HorizontalProgressbarWithProgress_progress_reach_color, mReachColor);
        mReachHeight = (int) ta.getDimension(R.styleable.HorizontalProgressbarWithProgress_progress_reach_height, mReachHeight);
        mTextOffSet = (int) ta.getDimension(R.styleable.HorizontalProgressbarWithProgress_progress_text_offset, mTextOffSet);
        ta.recycle();

        mPaint.setTextSize(mTextSize);
    }

    @Override
    protected synchronized void onDraw(Canvas canvas) {

        /**
         * save����������Canvas��״̬��save֮�󣬿��Ե���Canvas��ƽ�ơ���������ת�����С��ü��Ȳ�����
         */
        canvas.save();
        canvas.translate(getPaddingLeft(), getHeight() / 2);

        /**
         * �ж��Ƿ���Ҫ�����ұߵĲ���
         */
        boolean noNeedUnReach = false;
        /**
         * getProgress() ��ȡ��ǰ����
         * getMax()��ȡprogressBar��������
         */
        float radio = getProgress() * 1.0f / getMax();
        String text = getProgress() + "%";
        int textWidth = (int) mPaint.measureText(text);
        float progressX = radio * mRealWidth;
        if (progressX + textWidth > mRealWidth) {
            progressX = mRealWidth - textWidth;
            noNeedUnReach = true;
        }
        float endX = progressX - mTextOffSet / 2;
        if (endX > 0) {
            mPaint.setColor(mReachColor);
            mPaint.setStrokeWidth(mReachHeight);
            canvas.drawLine(0, 0, endX, 0, mPaint);
        }

        //draw text
        mPaint.setColor(mTextColor);
        /**
         * descent()�����ֵĵײ�y����, ascent()�����ֶ���y����
         */
        int y = (int) -(mPaint.descent() + mPaint.ascent() / 2);
        canvas.drawText(text, progressX, y, mPaint);

        //draw unReach bar
        if (!noNeedUnReach) {
            float start = progressX + mTextOffSet / 2 + textWidth;
            mPaint.setColor(mUnReachColor);
            mPaint.setStrokeWidth(mUNReachHeight);
            canvas.drawLine(start, 0, mRealWidth, 0, mPaint);
        }
        /**
         * restore�������ָ�Canvas֮ǰ�����״̬����ֹsave���Canvasִ�еĲ����Ժ����Ļ�����Ӱ�졣
         * ����,save��restoreҪ���ʹ�ã�restore���Ա�save�٣������ࣩܶ�����restore���ô�����save�࣬������Error.
         */
        canvas.restore();
    }

    @Override
    protected synchronized void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = measureHeight(heightMeasureSpec);
        setMeasuredDimension(width, height);
        /**
         * �����Ѿ�ͨ��setMeasuredDimension()ȷ����view�Ŀ�Ⱥ͸߶�
         * ���Կ���ֱ��ͨ��getMeasuredWidth()��ȡview�Ŀ��
         */
        mRealWidth = getMeasuredWidth() - getPaddingLeft() - getPaddingRight();
    }

    private int measureHeight(int heightMeasureSpec) {
        int result;
        int mode = MeasureSpec.getMode(heightMeasureSpec);
        int size = MeasureSpec.getSize(heightMeasureSpec);

        if (mode == MeasureSpec.EXACTLY) {
            result = size;
        } else {
            /**
             * descent()�����ֵĵײ�y����, ascent()�����ֶ���y����
             * ��������֮�Ϊ���ָ߶�
             */
            int textHeight = (int) (mPaint.descent() - mPaint.ascent());
            /**
             * ����֮�е����ֵ��Ϊ���������Ƶ�view�ĸ߶�.
             */
            result = getPaddingTop() + getPaddingBottom() + Math.max(Math.max(mReachHeight, mUNReachHeight), Math.abs(textHeight));
            /**
             * ���Ϊwrap_content�Ļ�,��resultȡ����result��size����Сֵ
             */
            if (mode == MeasureSpec.AT_MOST) {
                result = Math.min(result, size);
            }
        }
        return result;
    }

    protected int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics());
    }

    protected int sp2px(int sp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, getResources().getDisplayMetrics());
    }
}