package cn.czfy.zsfy.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import cn.czfy.zsfy.R;
import cn.czfy.zsfy.domain.ArticleWeixinBean;
import cn.czfy.zsfy.tool.SaveWeixinArticle;
import cn.czfy.zsfy.tool.Utility;

public class MoreArticleActivity extends BaseActivity{

    private ListView listView;
    private List<ArticleWeixinBean.newsList> articles;
    MyarticlesAdapter myarticlesAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_article);
        articles= SaveWeixinArticle.articles;
        initview();
    }

    private void initview() {
        showBackBtn();
        showTitle(this.getIntent().getStringExtra("title"),null);
        listView= (ListView) findViewById(R.id.list);
        myarticlesAdapter=new MyarticlesAdapter();
        listView.setAdapter(myarticlesAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                startActivity(new Intent(MoreArticleActivity.this,MyWebActivity.class).putExtra("url",articles.get(i).getUrl()).putExtra("title",articles.get(i).getTitle()));
          }
        });
    }
    public class MyarticlesAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return articles.size();
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
            View v=View.inflate(MoreArticleActivity.this,R.layout.list_article,null);
            ImageView im= (ImageView) v.findViewById(R.id.iv_article);
            Utility tool=new Utility();
            ArticleWeixinBean.newsList article=articles.get(i);
            tool.setPicture(article.getPicUrl(),im);
            TextView tv_articlename= (TextView) v.findViewById(R.id.tv_name);
            TextView tv_zuozhe= (TextView) v.findViewById(R.id.tv_zuozhe);
            tv_articlename.setText(article.getTitle());
            tv_zuozhe.setText("¹«ÖÚºÅ£º"+article.getDescription());
            return v;
        }
    }
}
