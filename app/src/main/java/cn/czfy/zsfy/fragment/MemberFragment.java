package cn.czfy.zsfy.fragment;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.File;

import cn.czfy.zsfy.R;
import cn.czfy.zsfy.ui.UIHelper;
import cn.czfy.zsfy.ui.pulltozoomview.PullToZoomScrollViewEx;
import cn.czfy.zsfy.view.CircleImageView;

public class MemberFragment extends Fragment {

    private Activity context;
    private View root;
    private PullToZoomScrollViewEx scrollView;
    private TextView tv_username;
    private CircleImageView iv_home_touxiang;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return root = inflater.inflate(R.layout.fragment_member, container, false);
    }
    @Override
    public void onResume() {
        super.onResume();

    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        context = getActivity();

        initView();
        initData();
    }
    public void showTitleRightBtnWithText(String text, View.OnClickListener clickListener) {
        TextView Right_tv = (TextView)this.getActivity().findViewById(R.id.Right_tv);
        Right_tv.setVisibility(View.VISIBLE);
        Right_tv.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
        Right_tv.setText(text);
        Right_tv.setBackgroundDrawable(null);
        Right_tv.setOnClickListener(clickListener);
    }
    private void setTitle(){
        Spinner head_sp = (Spinner) this.getActivity().findViewById(R.id.head_sp);
        head_sp.setVisibility(View.GONE);
        TextView textHeadTitle= (TextView) this.getActivity().findViewById(R.id.textHeadTitle);
        textHeadTitle.setVisibility(View.VISIBLE);
        textHeadTitle.setText("我");
    }
    void initView() {
        //setTitle();

        scrollView = (PullToZoomScrollViewEx) root.findViewById(R.id.scrollView);
        View headView = LayoutInflater.from(context).inflate(R.layout.member_head_view, null, false);
        View zoomView = LayoutInflater.from(context).inflate(R.layout.member_zoom_view, null, false);
        View contentView = LayoutInflater.from(context).inflate(R.layout.member_content_view, null, false);
        scrollView.setHeaderView(headView);
        scrollView.setZoomView(zoomView);
        scrollView.setScrollContentView(contentView);
        tv_username= (TextView) headView.findViewById(R.id.tv_user_name);
        iv_home_touxiang= (CircleImageView) headView.findViewById(R.id.iv_user_head);
        headView.findViewById(R.id.tv_register).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UIHelper.showLogin(getActivity());
            }
        });

        headView.findViewById(R.id.tv_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UIHelper.showLogin(getActivity());
            }
        });


        scrollView.getPullRootView().findViewById(R.id.textBalance).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        scrollView.getPullRootView().findViewById(R.id.textRecord).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        scrollView.getPullRootView().findViewById(R.id.textAttention).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        scrollView.getPullRootView().findViewById(R.id.textHelp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        scrollView.getPullRootView().findViewById(R.id.textCalculator).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        scrollView.getPullRootView().findViewById(R.id.textSetting).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

//        DisplayMetrics localDisplayMetrics = new DisplayMetrics();
//        context.getWindowManager().getDefaultDisplay().getMetrics(localDisplayMetrics);
//        int mScreenHeight = localDisplayMetrics.heightPixels;
//        int mScreenWidth = localDisplayMetrics.widthPixels;
//        LinearLayout.LayoutParams localObject = new LinearLayout.LayoutParams(mScreenWidth, (int) (9.0F * (mScreenWidth / 16.0F)));
//        scrollView.setHeaderLayoutParams(localObject);
    }

    private void initData() {
        SharedPreferences sp =this.getActivity()
                .getSharedPreferences("StuData", 0);
        tv_username.setText(" " + sp.getString("name", "") + "");
        String type = sp.getString("logintype", "学生");
        System.out.println(type);
//        if (type.equals("教师")) {
//            name.setText(" " + sp.getString("name", "") + "，您好!");
//        }
        String sex=sp.getString("sex", "男");
        String xh = sp.getString("xh", "");
       // CircleImageView  iv_home_touxiang = (ImageView) view.findViewById(R.id.iv_home_touxiang);
        String touxiangpath = sp.getString("touxiangpath", "");
        if (touxiangpath.equals("")) {
            //默认头像
            if (sex.equals("男")) {
                iv_home_touxiang.setImageResource(R.drawable.boy);
            }else
                iv_home_touxiang.setImageResource(R.drawable.girl);
        }else
        {
            try
            {//读取本地头像
                Uri uri = Uri.fromFile(new File(touxiangpath));
                ContentResolver cr = this.getActivity().getContentResolver();
                Bitmap bitmap = BitmapFactory.decodeStream(cr.openInputStream(uri));
                /* 将Bitmap设定到ImageView */
                iv_home_touxiang.setImageBitmap(bitmap);
            }catch (Exception e){
                if (sex.equals("男")) {
                    iv_home_touxiang.setImageResource(R.drawable.boy);
                }else
                    iv_home_touxiang.setImageResource(R.drawable.girl);
            }
        }
        iv_home_touxiang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
//                Fragment newContent = new PerInfoFragment();
//                String title = getString(R.string.perinfo);
//                switchFragment(newContent, title);
            }
        });
    }
}