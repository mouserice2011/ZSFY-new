package cn.czfy.zsdx.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.client.ClientProtocolException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cn.czfy.zsdx.R;
import cn.czfy.zsdx.activity.ImageGalleryActivity;
import cn.czfy.zsdx.activity.LibraryActivity;
import cn.czfy.zsdx.tool.BookData;
import cn.czfy.zsdx.tool.ListCache.SaveBookData;
import cn.czfy.zsdx.tool.SearchBook;
import cn.czfy.zsdx.tool.Utility;
import cn.czfy.zsdx.ui.loopviewpager.AutoLoopViewPager;
import cn.czfy.zsdx.ui.viewpagerindicator.CirclePageIndicator;

/**
 * @author sinyu
 * @description 浠婃棩
 */
public class LibraryFragment extends Fragment implements OnClickListener {

    private EditText ed_search;
    private ProgressDialog pd;
    private ImageView bt_lib_search;
    List<BookData> bd;
    private TextView tv_search_re1;
    private TextView tv_search_re2;
    private TextView tv_search_re3;
    private TextView tv_search_re4;
    private TextView tv_search_re5;
    private TextView tv_search_re6;
    private TextView tv_search_re7;
    private TextView tv_search_re8;
    private TextView tv_search_re9;
    private TextView tv_search_re10;
    private TextView tv_search_re11;
    private TextView tv_search_re12;
    Intent intent;
    AutoLoopViewPager pager;
    CirclePageIndicator indicator;
    private GalleryPagerAdapter galleryAdapter;
    private int[] imageViewIds;
    private List<String> imageList = new ArrayList<String>(Arrays.asList(
            "http://202.119.168.66:8080/test/pic/lib_1.png",
            "http://202.119.168.66:8080/test/pic/lib_2.png",
            "http://202.119.168.66:8080/test/pic/lib_3.png"));
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
        View view = inflater.inflate(R.layout.frag_library, null);
        ed_search = (EditText) view.findViewById(R.id.ed_lib_search);
        bt_lib_search = (ImageView) view.findViewById(R.id.bt_lib_search);
        tv_search_re1 = (TextView) view.findViewById(R.id.tv_search_re1);
        tv_search_re2 = (TextView) view.findViewById(R.id.tv_search_re2);
        tv_search_re3 = (TextView) view.findViewById(R.id.tv_search_re3);
        tv_search_re4 = (TextView) view.findViewById(R.id.tv_search_re4);
        tv_search_re5 = (TextView) view.findViewById(R.id.tv_search_re5);
        tv_search_re6 = (TextView) view.findViewById(R.id.tv_search_re6);
        tv_search_re7 = (TextView) view.findViewById(R.id.tv_search_re7);
        tv_search_re8 = (TextView) view.findViewById(R.id.tv_search_re8);
        tv_search_re9 = (TextView) view.findViewById(R.id.tv_search_re9);
        tv_search_re10 = (TextView) view.findViewById(R.id.tv_search_re10);
        tv_search_re11 = (TextView) view.findViewById(R.id.tv_search_re11);
        tv_search_re12 = (TextView) view.findViewById(R.id.tv_search_re12);
        pager = (AutoLoopViewPager) view.findViewById(R.id.pager);
        indicator = (CirclePageIndicator) view.findViewById(R.id.indicator);

        tv_search_re1.setOnClickListener(this);

        tv_search_re2.setOnClickListener(this);
        tv_search_re3.setOnClickListener(this);
        tv_search_re4.setOnClickListener(this);
        tv_search_re5.setOnClickListener(this);
        tv_search_re6.setOnClickListener(this);
        tv_search_re7.setOnClickListener(this);
        tv_search_re8.setOnClickListener(this);
        tv_search_re9.setOnClickListener(this);
        tv_search_re10.setOnClickListener(this);
        tv_search_re11.setOnClickListener(this);
        tv_search_re12.setOnClickListener(this);

        initpagerView();
        bt_lib_search.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                final String str = ed_search.getText().toString().trim();
                if (str.isEmpty()) {
                    Toast.makeText(LibraryFragment.this.getActivity(), "请输入检索词", Toast.LENGTH_SHORT).show();
                    return;
                }
                intent = new Intent(LibraryFragment.this.getActivity(), LibraryActivity.class);
                intent.putExtra("strBookname",str);
                /* 显示ProgressDialog */
                pd = ProgressDialog.show(LibraryFragment.this.getActivity(), "", "加载中，请稍后……");
                String xh=LibraryFragment.this.getActivity().getSharedPreferences("StuData",0).getString("xh","访客");
                Utility.setSearchBookLog(xh,str);
                new Thread() {
                    public void run() {
                        SearchBook s = new SearchBook();
                        try {
                            SaveBookData.clear1();
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
//							showToastInAnyThread("网络请求超时");
                            handler.sendEmptyMessage(2);//子线程更新ui  toast
                        } else
                            handler.sendEmptyMessage(0);// 执行耗时的方法之后发送消给handler
                    }

                    ;
                }.start();

            }
        });
        return view;
    }
    void initpagerView() {//轮播
        imageViewIds = new int[]{R.drawable.czfylib, R.drawable.home_czfy, R.drawable.home_3};
        galleryAdapter = new GalleryPagerAdapter();
        pager.setAdapter(galleryAdapter);
        indicator.setViewPager(pager);
        indicator.setPadding(5, 5, 10, 5);
    }
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {// handler接收到消息后就会执行此方法
            if (msg.what == 0) {
                pd.dismiss();// 关闭ProgressDialog
                startActivity(intent);
            } else {
                Toast.makeText(LibraryFragment.this.getActivity(), "网络请求超时", Toast.LENGTH_LONG).show();
            }
        }
    };

    public void onClick(View v) {//侧滑菜单的点击事??

        switch (v.getId()) {
            case R.id.tv_search_re1:
                searchText(tv_search_re1.getText().toString().trim());
                break;
            case R.id.tv_search_re2:
                searchText(tv_search_re2.getText().toString().trim());
                break;
            case R.id.tv_search_re3:
                searchText(tv_search_re3.getText().toString().trim());
                break;
            case R.id.tv_search_re4:
                searchText(tv_search_re4.getText().toString().trim());
                break;
            case R.id.tv_search_re5:
                searchText(tv_search_re5.getText().toString().trim());
                break;
            case R.id.tv_search_re6:
                searchText(tv_search_re6.getText().toString().trim());
                break;
            case R.id.tv_search_re7:
                searchText(tv_search_re7.getText().toString().trim());
                break;
            case R.id.tv_search_re8:
                searchText(tv_search_re8.getText().toString().trim());
                break;
            case R.id.tv_search_re9:
                searchText(tv_search_re9.getText().toString().trim());
                break;
            case R.id.tv_search_re10:
                searchText(tv_search_re10.getText().toString().trim());
                break;
            case R.id.tv_search_re11:
                searchText(tv_search_re11.getText().toString().trim());
                break;
            case R.id.tv_search_re12:
                searchText(tv_search_re12.getText().toString().trim());
                break;

            default:
                break;
        }
    }

    public void searchText(final String str1) {

        intent = new Intent(LibraryFragment.this.getActivity(), LibraryActivity.class);
        intent.putExtra("strBookname",str1);
        /* 显示ProgressDialog */
        pd = ProgressDialog.show(LibraryFragment.this.getActivity(), "", "加载中，请稍后……");
        String xh=LibraryFragment.this.getActivity().getSharedPreferences("StuData",0).getString("xh","访客");
        Utility.setSearchBookLog(xh,str1);
        new Thread() {
            public void run() {
                SearchBook s = new SearchBook();
                try {
                    SaveBookData.clear1();
                    bd = s.search(str1);// 耗时的方法
                } catch (ClientProtocolException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                handler.sendEmptyMessage(0);// 执行耗时的方法之后发送消给handler
            }

            ;
        }.start();
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
            ImageView item = new ImageView(LibraryFragment.this.getActivity());
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
                    Intent intent = new Intent(LibraryFragment.this.getActivity(), ImageGalleryActivity.class);
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

//	private void switchFragment(Fragment fragment, String title) {
//		if (getActivity() == null) {
//			return;
//		}
//		if (getActivity() instanceof com.fyzs.activity.MainActivity) {
//			com.fyzs.activity.MainActivity fca = (com.fyzs.activity.MainActivity) getActivity();
//			fca.switchConent(fragment, title);
//		}
//

}
