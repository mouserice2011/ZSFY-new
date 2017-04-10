package cn.czfy.zsdx.fragment;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
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

import cn.czfy.zsdx.activity.MessageActivity;
import cn.czfy.zsdx.activity.PerInfoActivity;
import cn.czfy.zsdx.tool.checkUpdateAPK;
import cn.czfy.zsdx.ui.UIHelper;
import cn.czfy.zsdx.ui.pulltozoomview.PullToZoomScrollViewEx;

import java.io.File;

import cn.czfy.zsdx.R;
import cn.czfy.zsdx.activity.AboutActivity;
import cn.czfy.zsdx.tool.FeedbackDialog;
import cn.czfy.zsdx.view.CircleImageView;

public class MemberFragment extends Fragment {

    private static final String TAG = "MemberFragment";
    private Activity context;
    private View root;
    private PullToZoomScrollViewEx scrollView;
    private TextView tv_username;
    private CircleImageView iv_home_touxiang;
    private TextView text_newmsg;

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
        TextView Right_tv = (TextView) this.getActivity().findViewById(R.id.Right_tv);
        Right_tv.setVisibility(View.VISIBLE);
        Right_tv.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
        Right_tv.setText(text);
        Right_tv.setBackgroundDrawable(null);
        Right_tv.setOnClickListener(clickListener);
    }

    private void setTitle() {
        Spinner head_sp = (Spinner) this.getActivity().findViewById(R.id.head_sp);
        head_sp.setVisibility(View.GONE);
        TextView textHeadTitle = (TextView) this.getActivity().findViewById(R.id.textHeadTitle);
        textHeadTitle.setVisibility(View.VISIBLE);
        textHeadTitle.setText("我");
    }

    void initView() {
        scrollView = (PullToZoomScrollViewEx) root.findViewById(R.id.scrollView);
        View headView = LayoutInflater.from(context).inflate(R.layout.member_head_view, null, false);
        View zoomView = LayoutInflater.from(context).inflate(R.layout.member_zoom_view, null, false);
        View contentView = LayoutInflater.from(context).inflate(R.layout.member_content_view, null, false);
        scrollView.setHeaderView(headView);
        scrollView.setZoomView(zoomView);
        scrollView.setScrollContentView(contentView);
        tv_username = (TextView) headView.findViewById(R.id.tv_user_name);
        iv_home_touxiang = (CircleImageView) headView.findViewById(R.id.iv_user_head);
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
        text_newmsg = (TextView) scrollView.getPullRootView().findViewById(R.id.text_newmsg);
        Boolean user_msg = this.getActivity().getSharedPreferences("SHARE_APP_TAG", 0).getBoolean("user_Msg", true);
        if (!user_msg) {
            text_newmsg.setVisibility(View.VISIBLE);
        } else {
            text_newmsg.setVisibility(View.GONE);
        }

        scrollView.getPullRootView().findViewById(R.id.tv_per_detail).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MemberFragment.this.getActivity(), PerInfoActivity.class));
            }
        });
        scrollView.getPullRootView().findViewById(R.id.tv_msg).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text_newmsg.setVisibility(View.GONE);
                MemberFragment.this.getActivity().findViewById(R.id.textUnreadLabel).setVisibility(View.GONE);
                startActivity(new Intent(MemberFragment.this.getActivity(), MessageActivity.class));
            }
        });
        scrollView.getPullRootView().findViewById(R.id.tv_check_update).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkUpdateAPK checkUpdateAPK = new checkUpdateAPK(MemberFragment.this.getActivity());
                checkUpdateAPK.jianchagengxin();
            }
        });
        scrollView.getPullRootView().findViewById(R.id.tv_feedback).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FeedbackDialog.Builder builder = new FeedbackDialog.Builder(MemberFragment.this.getActivity());
                builder.setMessage("");
                builder.setTitle("反馈");
                builder.setPositiveButton("确定",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                //dialog.dismiss();

                                // 设置你的操作事项
                            }
                        });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,
                                        int which) {
                        dialog.dismiss();

                        // 设置你的操作事项
                    }
                });
                builder.create().show();
            }
        });
        scrollView.getPullRootView().findViewById(R.id.tv_share).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent shareIntent = new Intent();
                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.putExtra(Intent.EXTRA_TEXT,
                        "【掌上纺院】查课表，查成绩，查图书！http://app.sinyu1012.cn/");
                shareIntent.setType("text/plain");
                // 设置分享列表的标题，并且每次都显示分享列表
                startActivity(Intent.createChooser(shareIntent, "分享到"));

            }
        });

        scrollView.getPullRootView().findViewById(R.id.tv_about).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MemberFragment.this.getActivity(), AboutActivity.class));

            }
        });

//        DisplayMetrics localDisplayMetrics = new DisplayMetrics();
//        context.getWindowManager().getDefaultDisplay().getMetrics(localDisplayMetrics);
//        int mScreenHeight = localDisplayMetrics.heightPixels;
//        int mScreenWidth = localDisplayMetrics.widthPixels;
//        LinearLayout.LayoutParams localObject = new LinearLayout.LayoutParams(mScreenWidth, (int) (9.0F * (mScreenWidth / 16.0F)));
//        scrollView.setHeaderLayoutParams(localObject);
    }

    @Override
    public void onStart() {
        super.onStart();
        initData();
    }

    private void initData() {
        SharedPreferences sp = this.getActivity()
                .getSharedPreferences("StuData", 0);
        tv_username.setText(" " + sp.getString("name", "") + "");
        String type = sp.getString("logintype", "学生");
        System.out.println(type);
//        if (type.equals("教师")) {
//            name.setText(" " + sp.getString("name", "") + "，您好!");
//        }
        String sex = sp.getString("sex", "男");
        String xh = sp.getString("xh", "");
        // CircleImageView  iv_home_touxiang = (ImageView) view.findViewById(R.id.iv_home_touxiang);
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
            public void onClick(View v) {
                // TODO Auto-generated method stub
                startActivity(new Intent(MemberFragment.this.getActivity(), PerInfoActivity.class));

            }
        });
    }
}