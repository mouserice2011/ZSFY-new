package cn.czfy.zsfy.activity;

import android.os.Bundle;

import cn.czfy.zsfy.R;

public class DashangActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashang);
        showBackBtn();
        showTitle("支持开发者",null);
        showTitleRightBtnWithText("谢谢",null);

    }
}
