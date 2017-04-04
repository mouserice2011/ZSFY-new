package cn.czfy.zsfy.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.qq.e.ads.banner.ADSize;
import com.qq.e.ads.banner.AbstractBannerADListener;
import com.qq.e.ads.banner.BannerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import cn.czfy.zsfy.R;
import cn.czfy.zsfy.activity.ImageGalleryActivity;
import cn.czfy.zsfy.http.HttpPostConn;
import cn.czfy.zsfy.http.QueryzaocaoHttp;
import cn.czfy.zsfy.tool.DateUtils;
import cn.czfy.zsfy.tool.Utility;
import cn.czfy.zsfy.tool.ZaocaoInfo;
import cn.czfy.zsfy.ui.loopviewpager.AutoLoopViewPager;
import cn.czfy.zsfy.ui.viewpagerindicator.CirclePageIndicator;

public class QueryzaocaoFragment extends Fragment {
    private ListView lv_detail;
    private ImageView back;
    private List<ZaocaoInfo> infos;
    private TextView tv_top;
    private EditText ed_search;
    private ProgressDialog pd;
    private ImageView bt_zaocao_query;
    private TextView tv_name1, tv_name2, tv_name3;
    Myadapter myadapter;
    String res;
    RelativeLayout bannerContainer;
    BannerView bv;
    private static final String LOG_TAG = "QueryzaocaoFragment";
    View view;
    private ImageView iv_detail;

    AutoLoopViewPager pager;
    CirclePageIndicator indicator;

    private GalleryPagerAdapter galleryAdapter;
    private int[] imageViewIds;
    private List<String> imageList = new ArrayList<String>(Arrays.asList(
            "http://202.119.168.66:8080/test/pic/daka_1.png",
            "http://202.119.168.66:8080/test/pic/daka_2.png",
            "http://202.119.168.66:8080/test/pic/daka_3.png"));

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.layout_queryzaocao, container, false);
        tv_name1 = (TextView)view.findViewById(R.id.tv_name1);
        tv_name2 = (TextView) view.findViewById(R.id.tv_name2);
        tv_name3 = (TextView) view.findViewById(R.id.tv_name3);
        lv_detail = (ListView) view.findViewById(R.id.lv_detail);
        ed_search = (EditText) view.findViewById(R.id.ed_lib_search);
        iv_detail= (ImageView) view.findViewById(R.id.iv_list);
        pager= (AutoLoopViewPager) view.findViewById(R.id.pager);
        indicator= (CirclePageIndicator) view.findViewById(R.id.indicator);

        infos = new ArrayList<>();
        myadapter = new Myadapter();
        lv_detail.setAdapter(myadapter);
        bannerContainer = (RelativeLayout) view.findViewById(R.id.bannerContainer);
        iv_detail.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                detail();
            }
        });
        //lin_query=(LinearLayout) findViewById(R.id.lin_query);

        SharedPreferences sp = this.getActivity().getSharedPreferences("StuData", 0);
        ed_search.setText(sp.getString("xh", ""));
        //�����ʱһ��
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Message msg=new Message();
                msg.what=3;
                myhandler.sendMessage(msg);
            }
        }).start();

        bt_zaocao_query = (ImageView) view.findViewById(R.id.bt_zaocao_query);

        bt_zaocao_query.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                infos.clear();
                final String str = ed_search.getText().toString().trim();
                if (str.isEmpty()) {
                    Toast.makeText(QueryzaocaoFragment.this.getActivity(), "������ѧ��", Toast.LENGTH_SHORT).show();
                    return;
                }
                tv_name1.setText("����");
                tv_name2.setText("�༶");
                tv_name3.setText("����");
                /* ��ʾProgressDialog */
                pd = ProgressDialog.show(QueryzaocaoFragment.this.getActivity(), "",
                        "��ѯ�У����Ժ󡭡�");

                new Thread() {
                    public void run() {
                        QueryzaocaoHttp q = new QueryzaocaoHttp();

                        SimpleDateFormat formatter = new SimpleDateFormat(
                                "yyyy-MM-dd");
                        Date curDate = new Date(System.currentTimeMillis());// ��ȡ��ǰʱ��
                        String time = formatter.format(curDate);
                        System.out.println(time);
                        res = q.Query(str, DateUtils.startDay, time);// ��ʱ�ķ���
                        if (res == null) {
                            pd.dismiss();// �ر�ProgressDialog
                            // showToastInAnyThread("��������ʱ");
                            myhandler.sendEmptyMessage(2);// ���̸߳���ui toast
                        } else
                            myhandler.sendEmptyMessage(0);// ִ�к�ʱ�ķ���֮��������handler
                    }

                    ;
                }.start();

            }
        });
        initpagerView();
        return view;
        
    }
    void initpagerView() {

        imageViewIds = new int[] { R.drawable.czfylib, R.drawable.advertisement, R.drawable.czfylib};

        galleryAdapter = new GalleryPagerAdapter();
        pager.setAdapter(galleryAdapter);
        indicator.setViewPager(pager);
        indicator.setPadding(5, 5, 10, 5);
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
            ImageView item = new ImageView(QueryzaocaoFragment.this.getActivity());
            item.setImageResource(imageViewIds[position]);
            Utility tool= new Utility();
            tool.setPicture(imageList.get(position),item);
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(-1, -1);
            item.setLayoutParams(params);
            item.setScaleType(ImageView.ScaleType.FIT_XY);
            container.addView(item);

            final int pos = position;
            item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(QueryzaocaoFragment.this.getActivity(), ImageGalleryActivity.class);
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
    public void detail() {
        infos.clear();
        final String str = ed_search.getText().toString().trim();
        if (str.isEmpty()) {
            Toast.makeText(QueryzaocaoFragment.this.getActivity(), "������ѧ��", Toast.LENGTH_LONG).show();
            return;
        }
        tv_name1.setText("���");
        tv_name2.setText("����");
        tv_name3.setText("ʱ��");
                /* ��ʾProgressDialog */
        pd = ProgressDialog.show(QueryzaocaoFragment.this.getActivity(), "",
                "��ѯ�У����Ժ󡭡�");

        new Thread() {
            public void run() {
                HttpPostConn q = new HttpPostConn();
                SimpleDateFormat formatter = new SimpleDateFormat(
                        "yyyyMMdd");
                Date curDate = new Date(System.currentTimeMillis());// ��ȡ��ǰʱ��
                String time = formatter.format(curDate);
                System.out.println(time);
                String stime = DateUtils.startDay.split("-")[0] + DateUtils.startDay.split("-")[1] + DateUtils.startDay.split("-")[2];
                String data = "xh=" + str + "&stime=" + stime.substring(0, 8) + "&etime=" + time;
                System.out.println(data);
                res = q.doPOST("http://202.119.168.66:8080/test/QueryzaocaoDetailServlet", data);// ��ʱ�ķ���
                System.out.println(res);
                if (res == null) {
                    pd.dismiss();// �ر�ProgressDialog
                    // showToastInAnyThread("��������ʱ");
                    myhandler.sendEmptyMessage(2);// ���̸߳���ui toast
                } else
                    myhandler.sendEmptyMessage(1);// ִ�к�ʱ�ķ���֮��������handler
            }

            ;
        }.start();
    }

    private void initBanner() {//���
        this.bv = new BannerView(QueryzaocaoFragment.this.getActivity(), ADSize.BANNER, "1105409129", "9060422047880836");
        // ע�⣺��������ߵ�banner����ʼ��չʾ����Ļ�еĻ�����ر��Զ�ˢ�£����򽫵����ع��ʹ��͡�
        // ����Ӧ�����д�����banner��������������Ļ�����ֶ�loadAD��
        bv.setRefresh(8);
        bv.setADListener(new AbstractBannerADListener() {
            @Override
            public void onNoAD(int i) {
                Log.i("AD_DEMO", "BannerNoAD��eCode=" + i);
            }

            @Override
            public void onADReceiv() {
                Log.i("AD_DEMO", "ONBannerReceive");
            }
        });
        //	bv.loadAD();
		 /* �����������յ�������ݺ��չʾ����   */
        bannerContainer.addView(bv);
    }

    Handler myhandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {// handler���յ���Ϣ��ͻ�ִ�д˷���
            if (msg.what == 0) {//�򿨴���
                pd.dismiss();// �ر�ProgressDialog
                try {
                    JSONObject json = new JSONObject(res);
                    if (json.getString("name").equals("0")) {
                        Toast.makeText(QueryzaocaoFragment.this.getActivity(), "���޲�ѯ�������Ϣ��",
                                Toast.LENGTH_SHORT).show();
                    } else if (json.getString("name").equals("1")) {
                        Toast.makeText(QueryzaocaoFragment.this.getActivity(), "��������ʱ", Toast.LENGTH_LONG)
                                .show();
                    } else {//����Ϣ
                        ZaocaoInfo info = new ZaocaoInfo();
                        info.setXuhao(json.getString("name"));
                        info.setName(json.getString("class"));
                        info.setTime(json.getString("cishu"));
                        infos.add(info);
                        myadapter.notifyDataSetChanged();
                        //lin_query.setVisibility(View.VISIBLE);
                        //Toast.makeText(QueryzaocaoFragment.this.getActivity(), res, 0).show();
                    }
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            } else if (msg.what == 1) {//����ϸ
                pd.dismiss();// �ر�ProgressDialog
                try {
                    JSONArray jarr = new JSONArray(res);
                    for (int i = 0; i < jarr.length(); i++) {
                        JSONObject jsonObject = jarr.getJSONObject(i);
                        if (jsonObject.getString("name").equals("0")) {
                            Toast.makeText(QueryzaocaoFragment.this.getActivity(), "���޲�ѯ�������Ϣ��",
                                    Toast.LENGTH_LONG).show();
                        } else if (jsonObject.getString("name").equals("1")) {
                            Toast.makeText(QueryzaocaoFragment.this.getActivity(), "��������ʱ", Toast.LENGTH_LONG)
                                    .show();
                        } else {//����Ϣ
                            ZaocaoInfo info = new ZaocaoInfo();
                            info.setXuhao(jsonObject.getString("xuhao"));
                            info.setName(jsonObject.getString("name"));
                            info.setTime(jsonObject.getString("time"));
                            infos.add(info);
                            //lin_query.setVisibility(View.VISIBLE);
                            //Toast.makeText(QueryzaocaoFragment.this.getActivity(), res, 0).show();
                        }
                    }
                    myadapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            } else if (msg.what == 2){

                Toast.makeText(QueryzaocaoFragment.this.getActivity(), "��������ʱ", Toast.LENGTH_LONG).show();
            }else if (msg.what == 3){//���
                try {
                    initBanner();
                    bv.loadAD();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    };

    class Myadapter extends BaseAdapter {

        @Override
        public int getCount() {
            return infos.size();
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
            View view1 = View.inflate(QueryzaocaoFragment.this.getActivity(), R.layout.list_daka, null);
            ZaocaoInfo info = infos.get(i);
            TextView tv1 = (TextView) view1.findViewById(R.id.tv_zaocao_name);
            tv1.setText(info.getXuhao());
            TextView tv2 = (TextView) view1.findViewById(R.id.tv_zaocao_class);
            tv2.setText(info.getName());
            TextView tv3 = (TextView) view1.findViewById(R.id.tv_zaocao_cishu);
            tv3.setText(info.getTime());
            return view1;
        }
    }

}
