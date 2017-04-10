package cn.czfy.zsdx.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import cn.czfy.zsdx.R;

/**
 * Created by sinyu on 2017/3/30.
 */

public class BaseActivity extends Activity {
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
        TextView Left_tv = (TextView) findViewById(R.id.Left_tv);
        Left_tv.setVisibility(View.VISIBLE);
        Left_tv.setText(resource);
        Left_tv.setOnClickListener(clickListener);
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

}
