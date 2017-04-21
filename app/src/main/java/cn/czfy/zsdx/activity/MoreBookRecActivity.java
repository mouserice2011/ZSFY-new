package cn.czfy.zsdx.activity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import cn.czfy.zsdx.R;
import cn.czfy.zsdx.domain.BookRecommendBean;
import cn.czfy.zsdx.tool.ListCache.SaveBookRecommend;
import cn.czfy.zsdx.tool.Utility;

public class MoreBookRecActivity extends BaseActivity{

    private ListView listView;
    private List<BookRecommendBean.Book> books;
    int bookpngid;
    MyBooksAdapter myBooksAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_article);
        books= SaveBookRecommend.books;
        bookpngid=books.size();
        initview();
    }

    private void initview() {
        showBackBtn();
        showTitle(this.getIntent().getStringExtra("title"),null);
        listView= (ListView) findViewById(R.id.list);
        myBooksAdapter=new MyBooksAdapter();
        listView.setAdapter(myBooksAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Utility tool = new Utility();
                tool.searchBook(MoreBookRecActivity.this, books.get(i).getBookname());
            }
        });
    }
    public class MyBooksAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return books.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            View v=View.inflate(MoreBookRecActivity.this,R.layout.list_bookrecom,null);
            ImageView im= (ImageView) v.findViewById(R.id.img);
            Utility tool=new Utility();
            tool.setPicture("http://202.119.168.66:8080/test/pic/book"+bookpngid--+".png",im);
            BookRecommendBean.Book book=books.get(i);
            TextView tv_bookname= (TextView) v.findViewById(R.id.tv_name);
            TextView tv_zuozhe= (TextView) v.findViewById(R.id.tv_zuozhe);
            TextView tv_tuijianyu= (TextView) v.findViewById(R.id.tv_tuijianyu);
//            tv_bookname.setText(book.getZuozhe());
            tv_bookname.setText(book.getBookname());
            tv_zuozhe.setText("×÷Õß£º"+book.getZuozhe());
            tv_tuijianyu.setText("ÍÆ¼öÓï£º"+book.getTuijianyu());
            return v;
        }
    }
}
