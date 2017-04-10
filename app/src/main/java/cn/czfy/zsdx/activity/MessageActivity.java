package cn.czfy.zsdx.activity;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import cn.czfy.zsdx.db.dao.StudentDao;

import java.util.List;

import cn.czfy.zsdx.R;
import cn.czfy.zsdx.db.dao.Message;

public class MessageActivity extends BaseActivity {

	private TextView tv_top_text;
	private ImageView bt_top_return;
	private ListView lv_msg;
	StudentDao dao;
	SharedPreferences setting;
	List<Message> infos;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.message);
		showBackBtn();
		showTitle("消息",null);
		dao=new StudentDao(this);
		infos=dao.findMsg();
		lv_msg=(ListView) findViewById(R.id.lv_msg);
		lv_msg.setAdapter(new myAdapter());
		lv_msg.setOnItemClickListener(new OnItemClickListener() {// 点击事件
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
                                    final int position, long id) {
				Intent intent=new Intent(MessageActivity.this, MsgDetailActivity.class);
				intent.putExtra("msg_index", position);
				startActivity(intent);
			}

		});

		setting = getSharedPreferences("SHARE_APP_TAG", 0);
		SharedPreferences.Editor et = setting.edit();
		// System.out.println("保存。。。。");
		et.putBoolean("user_Msg", true);
		et.commit();
		
	}
	private class myAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return infos.size();
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

			View view = View.inflate(MessageActivity.this, R.layout.msg_list, null);
			TextView title = (TextView) view.findViewById(R.id.tv_msg_title);
			TextView content = (TextView) view
					.findViewById(R.id.tv_msg_gaiyao);
			TextView type = (TextView) view
					.findViewById(R.id.tv_msg_who);
			TextView time = (TextView) view
					.findViewById(R.id.tv_msg_time);
			Message m=infos.get(position);
			title.setText(m.getTitle());
			if(m.getContent().length()>40)
			{
				content.setText(m.getContent().substring(0,40)+"...");
			}
			else
				content.setText(m.getContent());
			type.setText("来自："+m.getType());
			time.setText("时间："+m.getTime());
			return view;
		}

	}
}
