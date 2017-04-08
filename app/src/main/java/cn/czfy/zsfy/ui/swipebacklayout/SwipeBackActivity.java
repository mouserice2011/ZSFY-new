
package cn.czfy.zsfy.ui.swipebacklayout;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import cn.czfy.zsfy.activity.BaseFragmentActivity;

import cn.czfy.zsfy.R;

public class SwipeBackActivity extends BaseFragmentActivity implements SwipeBackActivityBase {
    private SwipeBackActivityHelper mHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHelper = new SwipeBackActivityHelper(this);
        mHelper.onActivityCreate();
    }
    /**
     * œ‘ æ∂ÃToast
     */

    public void showToastShort(String text) {
        Toast toast = Toast.makeText(this, text, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    /**
     * µ◊≤øœ‘ æ∂ÃToast
     */
    public void showToastShortBot(String text) {
        Toast toast = Toast.makeText(this, text, Toast.LENGTH_SHORT);
        toast.show();
    }
    public void showTitleLeftBtnWithText(String resource, View.OnClickListener clickListener) {
        Button Right_tv = (Button) findViewById(R.id.btnBack);
        Right_tv.setVisibility(View.VISIBLE);
        Right_tv.setText(resource);
        Right_tv.setOnClickListener(clickListener);

    }
    public void showBackBtn() {
        Button btn = (Button) findViewById(R.id.btnBack);

        if (btn == null) return;

        btn.setVisibility(View.VISIBLE);
        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    @SuppressLint("NewApi")
    public void showTitleRightBtnWithText(String text, View.OnClickListener clickListener) {
        TextView Right_tv = (TextView) findViewById(R.id.Right_tv);
        Right_tv.setVisibility(View.VISIBLE);
        Right_tv.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
        Right_tv.setText(text);
        Right_tv.setBackgroundDrawable(null);
        Right_tv.setOnClickListener(clickListener);

    }
    public void showTitle(String title, View.OnClickListener listener) {
        TextView Right_tv = (TextView) findViewById(R.id.textHeadTitle);
        if (Right_tv != null) {
            Right_tv.setOnClickListener(listener);
            Right_tv.setText(title);
        }
    }
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mHelper.onPostCreate();
    }

    @Override
    public View findViewById(int id) {
        View v = super.findViewById(id);
        if (v == null && mHelper != null)
            return mHelper.findViewById(id);
        return v;
    }

    @Override
    public SwipeBackLayout getSwipeBackLayout() {
        return mHelper.getSwipeBackLayout();
    }

    @Override
    public void setSwipeBackEnable(boolean enable) {
        getSwipeBackLayout().setEnableGesture(enable);
    }

    @Override
    public void scrollToFinishActivity() {
        Utils.convertActivityToTranslucent(this);
        getSwipeBackLayout().scrollToFinishActivity();
    }

}
