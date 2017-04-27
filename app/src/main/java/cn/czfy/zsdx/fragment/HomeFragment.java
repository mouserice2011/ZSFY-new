package cn.czfy.zsdx.fragment;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bmob.lostfound.MainFoundActivity;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cn.czfy.zsdx.R;
import cn.czfy.zsdx.activity.DangjiActivity;
import cn.czfy.zsdx.activity.ImageGalleryActivity;
import cn.czfy.zsdx.activity.MoreArticleActivity;
import cn.czfy.zsdx.activity.MoreBookRecActivity;
import cn.czfy.zsdx.activity.MyWebActivity;
import cn.czfy.zsdx.activity.PerInfoActivity;
import cn.czfy.zsdx.domain.ArticleWeixinBean;
import cn.czfy.zsdx.domain.BookRecommendBean;
import cn.czfy.zsdx.domain.FoundLostListBean;
import cn.czfy.zsdx.http.DJKnowledgeHttp;
import cn.czfy.zsdx.tool.ImageGallery2Activity;
import cn.czfy.zsdx.tool.ListCache.SaveBookRecommend;
import cn.czfy.zsdx.tool.ListCache.SaveFoundLostList;
import cn.czfy.zsdx.tool.ListCache.SaveWeixinArticle;
import cn.czfy.zsdx.tool.Utility;
import cn.czfy.zsdx.ui.UIHelper;
import cn.czfy.zsdx.ui.loopviewpager.AutoLoopViewPager;
import cn.czfy.zsdx.ui.viewpagerindicator.CirclePageIndicator;

/**
 * @author sinyu
 * @description 浠婃棩
 */
public class HomeFragment extends Fragment {
    private GridView gv;
    private TextView name, tv_bookname, tv_zuozhe, tv_tuijianyu, tv_morebook, tv_morearticle, tv_morefoundlost;
    private MyAdapter adapter;
    private ImageView iv_book;
    private ListView list_Article, lv_foundlost;
    private Fragment mContent;
    private ImageView iv_home_touxiang;
    private int[] one = new int[]{
            R.drawable.foundlost, R.drawable.knowledge_icon, R.drawable.home_xiaoweb,
            R.drawable.home_xiaopic};
    private String[] two = new String[]{"失物招领",
            "党基学习", "校园官网", "美丽纺院"};
    private ScrollView scrollView;
    private ProgressDialog pd;
    AutoLoopViewPager pager;
    CirclePageIndicator indicator;

    private GalleryPagerAdapter galleryAdapter;
    private int[] imageViewIds;
    private List<String> imageList = new ArrayList<String>(Arrays.asList(
            "http://202.119.168.66:8080/test/pic/home_1.png",
            "http://202.119.168.66:8080/test/pic/home_2.png",
            "http://202.119.168.66:8080/test/pic/home_3.png"));
    private List<ArticleWeixinBean.newsList> articles;
    private List<FoundLostListBean.ListBean> foundlostlistBean;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_homepage, null);
        name = (TextView) view.findViewById(R.id.name);
        iv_book = (ImageView) view.findViewById(R.id.iv_book);
        tv_morebook = (TextView) view.findViewById(R.id.tv_morebook);
        tv_morearticle = (TextView) view.findViewById(R.id.tv_morearticle);
        tv_bookname = (TextView) view.findViewById(R.id.tv_bookname);
        tv_zuozhe = (TextView) view.findViewById(R.id.tv_zuozhe);
        tv_tuijianyu = (TextView) view.findViewById(R.id.tv_tuijianyu);
        pager = (AutoLoopViewPager) view.findViewById(R.id.pager);
        indicator = (CirclePageIndicator) view.findViewById(R.id.indicator);
        list_Article = (ListView) view.findViewById(R.id.list_Article);
        tv_morefoundlost = (TextView) view.findViewById(R.id.tv_morefoundlost);
        lv_foundlost = (ListView) view.findViewById(R.id.lv_foundlost);
        scrollView= (ScrollView) view.findViewById(R.id.scrollView);

        LinearLayout lay_book = (LinearLayout) view.findViewById(R.id.lay_book);
        lay_book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utility tool = new Utility();
                tool.searchBook(HomeFragment.this.getActivity(), tv_bookname.getText().toString());
            }
        });

        SharedPreferences sp = HomeFragment.this.getActivity()
                .getSharedPreferences("StuData", 0);
        iv_home_touxiang = (ImageView) view.findViewById(R.id.iv_home_touxiang);

        gv = (GridView) view.findViewById(R.id.gv);
        adapter = new MyAdapter();
        gv.setAdapter(adapter);
        gv.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                Fragment newContent = null;
                String title = null;
                switch (arg2) {
                    case 0:
                        Intent intent = new Intent(HomeFragment.this.getActivity(),
                                MainFoundActivity.class);
                        startActivity(intent);
                        break;
                    case 1:
                        // 党基学习
                        pd = ProgressDialog.show(HomeFragment.this.getActivity(),
                                "", "加载中，请稍后……");// 等待的对话框
                        new Thread() {
                            @Override
                            public void run() {
                                // TODO Auto-generated method stub
                                int ok;

                                ok = DJKnowledgeHttp.getKnowledge(HomeFragment.this
                                        .getActivity());
                                if (ok == 1)
                                    handler.sendEmptyMessage(1);// 执行耗时的方法之后发送消给handler
                                else if (ok == 0)
                                    handler.sendEmptyMessage(0);

                            }
                        }.start();

                        break;
                    case 2:
                        startActivity(new Intent(HomeFragment.this.getActivity(), MyWebActivity.class).putExtra("url", "http://www.cztgi.edu.cn/").putExtra("title", "常州纺院官网"));
                        break;
                    case 3:
                        List<String> imageList = new ArrayList<String>();
                        String src = "http://app2.sinyu1012.cn/img/img (";
                        for (int i = 1; i <= 40; i++) {
                            imageList.add(src + i + ").jpg");
                        }
                        Intent intent3 = new Intent(HomeFragment.this.getActivity(), ImageGallery2Activity.class);
                        intent3.putStringArrayListExtra("images", (ArrayList<String>) imageList);
                        UIHelper.ToastMessage(HomeFragment.this.getActivity(), "多图，建议在WIFI下查看");
                        intent3.putExtra("position", 0);
                        intent3.putExtra("type", "imgs");
                        startActivity(intent3);
                        break;
                    case 4:
                        break;
                    case 5:
                        break;
                    default:
                        break;
                }

            }
        });
        initpagerView();
        tv_morefoundlost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeFragment.this.getActivity(), MainFoundActivity.class));
            }
        });
        tv_morebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeFragment.this.getActivity(), MoreBookRecActivity.class).putExtra("title", "书籍推荐"));
            }
        });
        tv_morearticle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeFragment.this.getActivity(), MoreArticleActivity.class).putExtra("title", "文章推荐"));
            }
        });
        getBook();
        getArticle();
        getFoundlost();
        return view;
    }

    void initpagerView() {//轮播
        imageViewIds = new int[]{R.drawable.home_czfy, R.drawable.home_czfy, R.drawable.home_3};
        galleryAdapter = new GalleryPagerAdapter();
        pager.setAdapter(galleryAdapter);
        indicator.setViewPager(pager);
        indicator.setPadding(5, 5, 10, 5);
    }

    public void getFoundlost() {
        try {
            System.out.println(SaveFoundLostList.foundlosts.get(0).getContent());
            foundlostlistBean = SaveFoundLostList.foundlosts;
            MyFoundLostAdapter myFoundLostAdapter = new MyFoundLostAdapter();
            lv_foundlost.setAdapter(myFoundLostAdapter);
            lv_foundlost.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    startActivity(new Intent(HomeFragment.this.getActivity(), MainFoundActivity.class));
                }
            });
            /*listview 与 scrollview 滑动冲突解决*/
//            lv_foundlost.setOnTouchListener(new View.OnTouchListener() {
//
//                public boolean onTouch(View v, MotionEvent event) {
//                    if (event.getAction() == MotionEvent.ACTION_MOVE) {
//                        scrollView.requestDisallowInterceptTouchEvent(true);
//                    }
//                    return false;
//                }
//            });

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void getArticle() {
        try {
            articles = SaveWeixinArticle.articles;
            MyarticlesAdapter myarticlesAdapter = new MyarticlesAdapter();
            list_Article.setAdapter(myarticlesAdapter);
            list_Article.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    startActivity(new Intent(HomeFragment.this.getActivity(), MyWebActivity.class).putExtra("url", articles.get(i).getUrl()).putExtra("title", articles.get(i).getTitle()));
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void getBook() {
        try {
            List<BookRecommendBean.Book> books = SaveBookRecommend.books;
            if (books.size() > 0) {
                System.out.print("------------------------" + books.size());
                final Utility utility = new Utility();
                utility.setPicture("http://202.119.168.66:8080/test/pic/book" + books.size() + ".png", iv_book);
                tv_bookname.setText(books.get(0).getBookname());
                tv_zuozhe.setText("作者：" + books.get(0).getZuozhe());
                tv_tuijianyu.setText("推荐语：" + books.get(0).getTuijianyu());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onStart() {
        super.onStart();
        refreshInfo();
    }

    private void refreshInfo() {//刷新
        SharedPreferences sp = HomeFragment.this.getActivity().getSharedPreferences(
                "StuData", 0);
        String type = sp.getString("logintype", "学生");
        name.setText(" " + sp.getString("name", "") + "，你好!");
        if (type.equals("教师")) {
            name.setText(" " + sp.getString("name", "") + "，您好!");
        }
        String sex = sp.getString("sex", "男");

        String touxiangpath = sp.getString("touxiangpath", "");
        if (touxiangpath.equals("")) {
            //默认头像
            if (sex.equals("男")) {
                iv_home_touxiang.setImageResource(R.drawable.boy);
            } else
                iv_home_touxiang.setImageResource(R.drawable.girl);
        } else {
            try {//读取本地头像
                Uri uri = Uri.fromFile(new File(touxiangpath));
                ContentResolver cr = this.getActivity().getContentResolver();
                Bitmap bitmap = BitmapFactory.decodeStream(cr.openInputStream(uri));
                /* 将Bitmap设定到ImageView */
                iv_home_touxiang.setImageBitmap(bitmap);
            } catch (Exception e) {
                if (sex.equals("男")) {
                    iv_home_touxiang.setImageResource(R.drawable.boy);
                } else
                    iv_home_touxiang.setImageResource(R.drawable.girl);
            }
        }
        iv_home_touxiang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeFragment.this.getActivity(), PerInfoActivity.class));
            }
        });
    }


    //轮播图适配器
    public class GalleryPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return imageViewIds.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView item = new ImageView(HomeFragment.this.getActivity());
            item.setImageResource(imageViewIds[position]);
            Utility tool = new Utility();
            tool.setPicture(imageList.get(position), item);
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(-1, -1);
            item.setLayoutParams(params);
            item.setScaleType(ImageView.ScaleType.FIT_XY);
            container.addView(item);

            final int pos = position;
            item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(HomeFragment.this.getActivity(), ImageGalleryActivity.class);
                    intent.putStringArrayListExtra("images", (ArrayList<String>) imageList);
                    intent.putExtra("position", pos);
                    startActivity(intent);
                }
            });

            return item;
        }

        @Override
        public void destroyItem(ViewGroup collection, int position, Object view) {
            collection.removeView((View) view);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        pager.startAutoScroll();
    }

    @Override
    public void onPause() {
        super.onPause();
        pager.stopAutoScroll();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return two.length;
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
            ViewHoder hoder = null;
            if (convertView == null) {
                convertView = View.inflate(HomeFragment.this.getActivity(),
                        R.layout.grid, null);
                hoder = new ViewHoder();
                hoder.iv = (ImageView) convertView.findViewById(R.id.t);
                hoder.tv = (TextView) convertView.findViewById(R.id.tv);
                convertView.setTag(hoder);
            } else {
                hoder = (ViewHoder) convertView.getTag();
            }
            hoder.iv.setImageResource(one[position]);
            hoder.tv.setText(two[position]);
            return convertView;
        }
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {// handler接收到消息后就会执行此方法
            pd.dismiss();// 关闭ProgressDialog
            if (msg.what == 1) {
                Intent intent = new Intent(HomeFragment.this.getActivity(), DangjiActivity.class);
                startActivity(intent);
            } else if (msg.what == 0) {
                showToastInAnyThread("加载失败，请稍后重试");
            } else if (msg.what == 2) {
                showToastInAnyThread("服务器拥挤请稍后重试");
            } else if (msg.what == 3) {

            }

        }
    };

    public void showToastInAnyThread(final String text) {
        HomeFragment.this.getActivity().runOnUiThread(new Runnable() {
            public void run() {
                Toast.makeText(HomeFragment.this.getActivity(), text, Toast.LENGTH_LONG).show();
            }
        });
    }

    private class ViewHoder {
        ImageView iv;
        TextView tv;
    }

    class MyArticle extends BaseAdapter {

        @Override
        public int getCount() {
            return 0;
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


            return view;
        }
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
            View v = View.inflate(HomeFragment.this.getActivity(), R.layout.list_article, null);
            ImageView im = (ImageView) v.findViewById(R.id.iv_article);
            Utility tool = new Utility();
            ArticleWeixinBean.newsList article = articles.get(i);
            tool.setPicture(article.getPicUrl(), im);
            TextView tv_articlename = (TextView) v.findViewById(R.id.tv_name);
            TextView tv_zuozhe = (TextView) v.findViewById(R.id.tv_zuozhe);
            tv_articlename.setText(article.getTitle());
            tv_zuozhe.setText("公众号：" + article.getDescription());
            return v;
        }
    }

    public class MyFoundLostAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return foundlostlistBean.size();
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
            View v = View.inflate(HomeFragment.this.getActivity(), R.layout.item_list, null);
            TextView textView1 = (TextView) v.findViewById(R.id.tv_title);
            TextView textView2 = (TextView) v.findViewById(R.id.tv_describe);
            TextView textView3 = (TextView) v.findViewById(R.id.tv_time);
            TextView textView4 = (TextView) v.findViewById(R.id.tv_photo);
            //缺个time字段
            FoundLostListBean.ListBean listBean = foundlostlistBean.get(i);
            textView1.setText(listBean.getTitle());
            textView2.setText(listBean.getContent());
            textView3.setText(listBean.getTime());
            textView4.setText(listBean.getPhone());


            return v;
        }
    }
}
