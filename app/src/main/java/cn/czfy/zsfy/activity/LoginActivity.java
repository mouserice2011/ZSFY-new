package cn.czfy.zsfy.activity;

import android.os.Bundle;
import android.view.View;

import cn.czfy.zsfy.ui.swipebacklayout.SwipeBackActivity;
import cn.czfy.zsfy.R;


/**
 * Created by tiansj on 15/7/31.
 */
public class LoginActivity extends SwipeBackActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        findViewById(R.id.btnClose).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

}
