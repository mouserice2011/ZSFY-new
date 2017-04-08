package cn.czfy.zsfy.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import cn.czfy.zsfy.tool.BookData;

import org.apache.http.client.ClientProtocolException;

import java.io.IOException;
import java.util.List;

import cn.czfy.zsfy.R;

import cn.czfy.zsfy.tool.SaveBookData;
import cn.czfy.zsfy.tool.SearchBook;

public class LibraryActivity extends BaseActivity {

	private ListView lv_lib;
	private String strBookname = "";
	private List<BookData> bd;
	private int pageNum = 1, maxNum = 10;
	private TextView tv_page;
	private TextView tv_uppage;
	private TextView tv_nextpage;
	private ProgressDialog pd;
	List<BookData> bd1;
	Intent intent;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.layout_library);
		showTitle("图书查询",null);
		showBackBtn();
		Intent intent = getIntent();
		strBookname = intent.getStringExtra("str");
		lv_lib = (ListView) findViewById(R.id.lv_lib);
		tv_page = (TextView) findViewById(R.id.tv_page);
		tv_nextpage = (TextView) findViewById(R.id.tv_nextpage);
		tv_uppage = (TextView) findViewById(R.id.tv_uppage);
		tv_uppage.setOnClickListener(new OnClickListener() {// 上一页
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						if (pageNum == 1) {
							Toast.makeText(LibraryActivity.this, "已经是第一页", Toast.LENGTH_SHORT)
									.show();
							return;
						}
						pageNum--;
						update(pageNum);
						tv_page.setText(pageNum + "/" + maxNum);

					}
				});
		tv_nextpage.setOnClickListener(new OnClickListener() {// 下一页
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						if (pageNum == maxNum) {
							Toast.makeText(LibraryActivity.this, "已经是最后一页",  Toast.LENGTH_SHORT)
									.show();
							return;
						}
						pageNum++;
						update(pageNum);
						tv_page.setText(pageNum + "/" + maxNum);
					}
				});
		bd = SaveBookData.bd;
		lv_lib.setAdapter(new myAdapter());
		lv_lib.setOnItemClickListener(new OnItemClickListener() {// 点击事件
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
                                    final int position, long id) {
				// TODO Auto-generated method stub
				pd = ProgressDialog.show(LibraryActivity.this, "", "加载中，请稍后……");

				new Thread() {
					public void run() {
						SearchBook s = new SearchBook();
						List<BookData> bd1 = null;
						try {
							bd1 = s.Detail(position);// 耗时的方法
						} catch (ClientProtocolException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						if (bd1 == null) {
							pd.dismiss();// 关闭ProgressDialog
							showToastInAnyThread("网络请求超时");
						} else
							handler.sendEmptyMessage(1);// 执行耗时的方法之后发送消给handler
					};
				}.start();
			}

		});
	}

	public void update(final int Num) {
		pd = ProgressDialog.show(LibraryActivity.this, "", "加载中，请稍后……");

		new Thread() {
			public void run() {
				SearchBook s = new SearchBook();
				try {
					bd = s.nextPage(Num);// 耗时的方法
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
			if (msg.what == 1) {
				Intent intent = new Intent(LibraryActivity.this,
						DetailBookActivity.class);
				startActivity(intent);
			} else {
				lv_lib.setAdapter(new myAdapter());
			}

		}
	};

	private class myAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return bd.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub

			View view = View.inflate(LibraryActivity.this, R.layout.lib_list,
					null);
			TextView tvname = (TextView) view.findViewById(R.id.tv_lv_name);
			TextView tvnameinfo = (TextView) view
					.findViewById(R.id.tv_lv_nameinfo);
			TextView guancang = (TextView) view
					.findViewById(R.id.tv_lv_guancanginfo);
			TextView suoyinno = (TextView) view
					.findViewById(R.id.tv_lv_suoshuno);
			BookData b = bd.get(position);
			tvname.setText(b.getName());
			tvnameinfo.setText("书名信息：" + b.getNameinfo());
			guancang.setText("馆藏信息：" + b.getGuancanginfo());
			suoyinno.setText("索书号：" + b.getSuoshuno());
			tv_page.setText(b.getPage());

			maxNum = Integer.parseInt(b.getPage().split("/")[1]);
			return view;
		}

	}

	public void showToastInAnyThread(final String text) {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				Toast.makeText(LibraryActivity.this, text,  Toast.LENGTH_SHORT).show();
			}
		});
	}
}
