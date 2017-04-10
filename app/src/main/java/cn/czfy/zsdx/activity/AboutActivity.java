package cn.czfy.zsdx.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.LinearLayout;

import cn.czfy.zsdx.R;


/**
 * 
 * @author sinyu
 * @description 今日
 */
public class AboutActivity extends BaseActivity {

	private LinearLayout tv_per_share;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.frag_about);
		tv_per_share = (LinearLayout) findViewById(R.id.tv_per_share);
		tv_per_share.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				Intent shareIntent = new Intent();
				shareIntent.setAction(Intent.ACTION_SEND);
				shareIntent.putExtra(Intent.EXTRA_TEXT,
						"�����Ϸ�Ժ����α���ɼ�����ͼ�飡http://app.sinyu1012.cn/");
				shareIntent.setType("text/plain");
				// ���÷����б�ı��⣬����ÿ�ζ���ʾ�����б�
				startActivity(Intent.createChooser(shareIntent, "����"));

			}
		});
		showBackBtn();
		showTitle("����",null);
		showTitleRightBtnWithText("����", new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				startActivity(new Intent(AboutActivity.this,DashangActivity.class));
			}
		});
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub

		super.onResume();
		//getFocus();
	}

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
		super.onCreate(savedInstanceState, persistentState);
	}

	public void feedback(View v) {
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>");
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}
}
