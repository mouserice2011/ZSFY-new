package cn.czfy.zsfy.fragment;

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
import android.widget.TextView;
import android.widget.Toast;

import com.bmob.lostfound.MainFoundActivity;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cn.czfy.zsfy.R;
import cn.czfy.zsfy.activity.DangjiActivity;
import cn.czfy.zsfy.activity.ImageGalleryActivity;
import cn.czfy.zsfy.activity.PerInfoActivity;
import cn.czfy.zsfy.http.DJKnowledgeHttp;
import cn.czfy.zsfy.tool.Utility;
import cn.czfy.zsfy.ui.loopviewpager.AutoLoopViewPager;
import cn.czfy.zsfy.ui.viewpagerindicator.CirclePageIndicator;

/**
 * @author sinyu
 * @description 浠婃棩
 */
public class HomeFragment extends Fragment {
    private GridView gv;
    private TextView name;
    private MyAdapter adapter;
    private Fragment mContent;
    private ImageView iv_home_touxiang;
    private int[] one = new int[]{
            R.drawable.foundlost, R.drawable.knowledge_icon, R.drawable.home_wifi,
            R.drawable.more};
    private String[] two = new String[]{"失物招领",
            "党基学习","校园WIFI", "更多功能"};

    private ProgressDialog pd;

    AutoLoopViewPager pager;
    CirclePageIndicator indicator;

    private GalleryPagerAdapter galleryAdapter;
    private int[] imageViewIds;
    private List<String> imageList = new ArrayList<String>(Arrays.asList(
            "http://202.119.168.66:8080/test/pic/home_1.png",
            "http://202.119.168.66:8080/test/pic/home_2.png",
            "http://202.119.168.66:8080/test/pic/home_3.png"));

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
        pager = (AutoLoopViewPager) view.findViewById(R.id.pager);
        indicator = (CirclePageIndicator) view.findViewById(R.id.indicator);
        LinearLayout lay_book= (LinearLayout) view.findViewById(R.id.lay_book);
        lay_book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utility tool=new Utility();
                tool.searchBook(HomeFragment.this.getActivity(),"平凡的世界");
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
                        Toast.makeText(HomeFragment.this.getActivity(), "开发中，敬请期待！", Toast.LENGTH_LONG).show();
                        break;
                    case 3:
                        Toast.makeText(HomeFragment.this.getActivity(), "开发中，敬请期待！", Toast.LENGTH_LONG).show();
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
        return view;
    }

    void initpagerView() {

        imageViewIds = new int[]{R.drawable.home_czfy, R.drawable.home_czfy, R.drawable.home_czfy};

        galleryAdapter = new GalleryPagerAdapter();
        pager.setAdapter(galleryAdapter);
        indicator.setViewPager(pager);
        indicator.setPadding(5, 5, 10, 5);
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
}
