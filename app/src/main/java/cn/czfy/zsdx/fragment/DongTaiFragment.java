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
        //�����ʱһ��
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


    private void initBanner() {//���
        this.bv = new BannerView(DongTaiFragment.this.getActivity(), ADSize.BANNER, "1105409129", "1030321136683684");
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
            if (msg.what == 1){//���
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
