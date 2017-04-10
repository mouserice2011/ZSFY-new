package cn.czfy.zsdx.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.qq.e.ads.banner.ADSize;
import com.qq.e.ads.banner.AbstractBannerADListener;
import com.qq.e.ads.banner.BannerView;

import cn.czfy.zsdx.R;


public class DongTaiFragment extends Fragment {
    RelativeLayout bannerContainer;
    BannerView bv;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dong_tai, container, false);
        bannerContainer = (RelativeLayout) view.findViewById(R.id.bannerContainer);
        //广告延时一秒
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    Thread.sleep(1000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                Message msg=new Message();
//                msg.what=1;
//                myhandler.sendMessage(msg);
//            }
//        }).start();
        return view;
    }


    private void initBanner() {//广告
        this.bv = new BannerView(DongTaiFragment.this.getActivity(), ADSize.BANNER, "1105409129", "1030321136683684");
        // 注意：如果开发者的banner不是始终展示在屏幕中的话，请关闭自动刷新，否则将导致曝光率过低。
        // 并且应该自行处理：当banner广告区域出现在屏幕后，再手动loadAD。
        bv.setRefresh(8);
        bv.setADListener(new AbstractBannerADListener() {
            @Override
            public void onNoAD(int i) {
                Log.i("AD_DEMO", "BannerNoAD，eCode=" + i);
            }

            @Override
            public void onADReceiv() {
                Log.i("AD_DEMO", "ONBannerReceive");
            }
        });
        //	bv.loadAD();
		 /* 发起广告请求，收到广告数据后会展示数据   */
        bannerContainer.addView(bv);
    }
    Handler myhandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {// handler接收到消息后就会执行此方法
            if (msg.what == 1){//广告
               // UIHelper.ToastMessage(DongTaiFragment.this.getActivity(),"hello");
                  try {
                      initBanner();
                      bv.loadAD();
                  }catch (Exception e){
                      e.printStackTrace();
                  }


            }
        }
    };
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DongTaiFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DongTaiFragment newInstance(String param1, String param2) {
        DongTaiFragment fragment = new DongTaiFragment();

        return fragment;
    }



}
