package cn.czfy.zsfy.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import cn.czfy.zsfy.R;
import cn.czfy.zsfy.db.dao.Message;
import cn.czfy.zsfy.db.dao.StudentDao;

public class MsgDetailActivity extends Activity {

	private TextView tv_top_text;
	private ImageView bt_top_return;
	private TextView tv_title;
	private TextView tv_type;
	private TextView tv_time;
	private TextView tv_content;
	StudentDao dao;
	List<Message> infos;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.msgdetail);
	
		dao=new StudentDao(this);
		infos=dao.findMsg();
		
		tv_top_text=(TextView) findViewById(R.id.tv_top_lib);
		tv_top_text.setText("œ˚œ¢œÍ«È");
		bt_top_return = (ImageView) findViewById(R.id.bt_top_return);
		bt_top_return.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				finish();
			}
		});
		tv_title=(TextView) findViewById(R.id.tv_msg_title);
		tv_type=(TextView) findViewById(R.id.tv_msg_who);
		tv_time=(TextView) findViewById(R.id.tv_msg_time);
		tv_content=(TextView) findViewById(R.id.tv_msg_content);
		
		Intent intent=getIntent();
		int index=intent.getIntExtra("msg_index", 0);
		Message msg=infos.get(index);
		
		tv_title.setText(msg.getTitle());
		tv_content.setText("	\n"+msg.getContent());
		tv_type.setText(""+msg.getType());
		tv_time.setText(""+msg.getTime());
	}
	
}
