package cn.czfy.zsdx.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import cn.czfy.zsdx.db.DJKnowledgeData;
import cn.czfy.zsdx.db.dao.StudentDao;

import java.util.List;

import cn.czfy.zsdx.R;

public class DangjiActivity extends BaseActivity {


	private ListView lv_dj;
	StudentDao dao;
	List<DJKnowledgeData> infos;
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.layout_dangji);
		
		dao=new StudentDao(this);
		infos=dao.findDJK();
		showBackBtn();
		showTitle("党基学习",null);
		lv_dj=(ListView) findViewById(R.id.lv_dj);
		lv_dj.setAdapter(new myAdapter());
		lv_dj.setOnItemClickListener(new OnItemClickListener() {// 点击事件
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
                                    final int position, long id) {
				
				Intent intent=new Intent(DangjiActivity.this, DJDatiActivity.class);
				intent.putExtra("position", position);
				startActivity(intent);
			}

		});
		
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

			View view = View.inflate(DangjiActivity.this, R.layout.list_dangji, null);
			DJKnowledgeData info=infos.get(position);
			TextView title = (TextView) view.findViewById(R.id.tv_dj_title);
			if(info.getTitle().length()>50)
				title.setText(position+1+".【单选】"+info.getTitle().substring(0, 50)+"...");
			else
				title.setText(position+1+".【单选】"+info.getTitle());
			return view;
		}

	}
}
