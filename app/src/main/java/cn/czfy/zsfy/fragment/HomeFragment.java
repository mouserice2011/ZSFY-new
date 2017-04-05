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
import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cn.czfy.zsfy.R;
import cn.czfy.zsfy.activity.DangjiActivity;
import cn.czfy.zsfy.activity.ImageGalleryActivity;
import cn.czfy.zsfy.activity.MoreArticleActivity;
import cn.czfy.zsfy.activity.MyWebActivity;
import cn.czfy.zsfy.activity.PerInfoActivity;
import cn.czfy.zsfy.domain.BookRecommendBean;
import cn.czfy.zsfy.http.DJKnowledgeHttp;
import cn.czfy.zsfy.tool.SaveBookRecommend;
import cn.czfy.zsfy.tool.Utility;
import cn.czfy.zsfy.ui.loopviewpager.AutoLoopViewPager;
import cn.czfy.zsfy.ui.viewpagerindicator.CirclePageIndicator;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author sinyu
 * @description 今日
 */
public class HomeFragment extends Fragment {
    private GridView gv;
    private TextView name,tv_bookname,tv_zuozhe,tv_tuijianyu,tv_morebook;
    private MyAdapter adapter;
    private ImageView iv_book;
    private Fragment mContent;
    private ImageView iv_home_touxiang;
    private int[] one = new int[]{
            R.drawable.foundlost, R.drawable.knowledge_icon, R.drawable.home_xiaoweb,
            R.drawable.more};
    private String[] two = new String[]{"ʧ������",
            "����ѧϰ", "У԰����", "���๦��"};

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
        iv_book= (ImageView) view.findViewById(R.id.iv_book);
        tv_morebook= (TextView) view.findViewById(R.id.tv_morebook);
        tv_bookname= (TextView) view.findViewById(R.id.tv_bookname);
        tv_zuozhe= (TextView) view.findViewById(R.id.tv_zuozhe);
        tv_tuijianyu= (TextView) view.findViewById(R.id.tv_tuijianyu);
        pager = (AutoLoopViewPager) view.findViewById(R.id.pager);
        indicator = (CirclePageIndicator) view.findViewById(R.id.indicator);
        LinearLayout lay_book = (LinearLayout) view.findViewById(R.id.lay_book);
        lay_book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utility tool = new Utility();
                tool.searchBook(HomeFragment.this.getActivity(),tv_bookname.getText().toString());
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
                        // ����ѧϰ
                        pd = ProgressDialog.show(HomeFragment.this.getActivity(),
                                "", "�����У����Ժ󡭡�");// �ȴ��ĶԻ���
                        new Thread() {
                            @Override
                            public void run() {
                                // TODO Auto-generated method stub
                                int ok;

                                ok = DJKnowledgeHttp.getKnowledge(HomeFragment.this
                                        .getActivity());
                                if (ok == 1)
                                    handler.sendEmptyMessage(1);// ִ�к�ʱ�ķ���֮��������handler
                                else if (ok == 0)
                                    handler.sendEmptyMessage(0);

                            }
                        }.start();

                        break;
                    case 2:
                        startActivity(new Intent(HomeFragment.this.getActivity(), MyWebActivity.class).putExtra("url", "http://www.cztgi.edu.cn/").putExtra("title", "���ݷ�Ժ����"));
                        break;
                    case 3:
                        Toast.makeText(HomeFragment.this.getActivity(), "�����У������ڴ���", Toast.LENGTH_LONG).show();
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
        getBook();
        return view;
    }

    void initpagerView() {//�ֲ�
        imageViewIds = new int[]{R.drawable.home_czfy, R.drawable.home_czfy, R.drawable.home_czfy};
        galleryAdapter = new GalleryPagerAdapter();
        pager.setAdapter(galleryAdapter);
        indicator.setViewPager(pager);
        indicator.setPadding(5, 5, 10, 5);
    }

    public void getBook() {
        final Utility utility=new Utility();

        //����okHttpClient����
        OkHttpClient mOkHttpClient = new OkHttpClient();
        //����һ��Request
        final Request request = new Request.Builder()
                .url("http://202.119.168.66:8080/test/BookRecommendServlet")
                .build();
        //new call
        Call call = mOkHttpClient.newCall(request);
        //����������
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //���� response.body().string()ֻ��ʹ��һ��
                tv_morebook.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(HomeFragment.this.getActivity(), MoreArticleActivity.class).putExtra("title","�鼮�Ƽ�"));
                    }
                });
                Gson gson=new Gson();
                BookRecommendBean bookRecommendBean=new BookRecommendBean();
                bookRecommendBean=gson.fromJson(response.body().string().toString(),BookRecommendBean.class);
                SaveBookRecommend.save(bookRecommendBean.getBooks());
                utility.setPicture("http://202.119.168.66:8080/test/pic/book"+bookRecommendBean.getBooks().size()+".png",iv_book);
                tv_bookname.setText(bookRecommendBean.getBooks().get(0).getBookname());
                tv_zuozhe.setText("���ߣ�"+bookRecommendBean.getBooks().get(0).getBookname());
                tv_tuijianyu.setText("�Ƽ��"+bookRecommendBean.getBooks().get(0).getTuijianyu());
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        refreshInfo();
    }

    private void refreshInfo() {//ˢ��
        SharedPreferences sp = HomeFragment.this.getActivity().getSharedPreferences(
                "StuData", 0);
        String type = sp.getString("logintype", "ѧ��");
        name.setText(" " + sp.getString("name", "") + "�����!");
        if (type.equals("��ʦ")) {
            name.setText(" " + sp.getString("name", "") + "������!");
        }
        String sex = sp.getString("sex", "��");

        String touxiangpath = sp.getString("touxiangpath", "");
        if (touxiangpath.equals("")) {
            //Ĭ��ͷ��
            if (sex.equals("��")) {
                iv_home_touxiang.setImageResource(R.drawable.boy);
            } else
                iv_home_touxiang.setImageResource(R.drawable.girl);
        } else {
            try {//��ȡ����ͷ��
                Uri uri = Uri.fromFile(new File(touxiangpath));
                ContentResolver cr = this.getActivity().getContentResolver();
                Bitmap bitmap = BitmapFactory.decodeStream(cr.openInputStream(uri));
                /* ��Bitmap�趨��ImageView */
                iv_home_touxiang.setImageBitmap(bitmap);
            } catch (Exception e) {
                if (sex.equals("��")) {
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

    //�ֲ�ͼ������
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
        public void handleMessage(Message msg) {// handler���յ���Ϣ��ͻ�ִ�д˷���
            pd.dismiss();// �ر�ProgressDialog
            if (msg.what == 1) {
                Intent intent = new Intent(HomeFragment.this.getActivity(), DangjiActivity.class);
                startActivity(intent);
            } else if (msg.what == 0) {
                showToastInAnyThread("����ʧ�ܣ����Ժ�����");
            } else if (msg.what == 2) {
                showToastInAnyThread("������ӵ�����Ժ�����");
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
