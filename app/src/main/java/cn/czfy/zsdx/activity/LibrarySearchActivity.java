package cn.czfy.zsdx.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import cn.czfy.zsdx.tool.BookData;

import org.apache.http.client.ClientProtocolException;

import java.io.IOException;
import java.util.List;

import cn.czfy.zsdx.R;

import cn.czfy.zsdx.tool.SearchBook;

public class LibrarySearchActivity extends Activity {

	private EditText ed_search;
	private ProgressDialog pd;
	List<BookData> bd;
	Intent intent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_lib_search);
		ed_search = (EditText) findViewById(R.id.ed_lib_search);

	}

	public void search(View v) {
		final String str = ed_search.getText().toString().trim();
		if(str.isEmpty())
		{
			showToastInAnyThread("请输入检索词");
			return;
		}
		intent = new Intent(LibrarySearchActivity.this, LibraryActivity.class);
		/* 显示ProgressDialog */
		pd = ProgressDialog.show(LibrarySearchActivity.this, "", "加载中，请稍后……");

		new Thread() {
			public void run() {
				SearchBook s = new SearchBook();
				try {
					bd = s.search(str);// 耗时的方法
				} catch (ClientProtocolException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (bd == null) {
					pd.dismiss();// 关闭ProgressDialog
					showToastInAnyThread("网络请求超时");
				} else
					handler.sendEmptyMessage(0);// 执行耗时的方法之后发送消给handler
			};
		}.start();

	}

	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {// handler接收到消息后就会执行此方法
			pd.dismiss();// 关闭ProgressDialog
			startActivity(intent);
		}
	};
	public void showToastInAnyThread(final String text){
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				Toast.makeText(LibrarySearchActivity.this, text, Toast.LENGTH_LONG).show();
			}
		});
	}


}
