package cn.czfy.zsdx.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import cn.czfy.zsdx.R;
import cn.czfy.zsdx.tool.MyConstants;
import cn.czfy.zsdx.tool.Utility;
import cn.czfy.zsdx.ui.UIHelper;
import cn.czfy.zsdx.view.HorizontalProgressbarWithProgress;

/**
 * Created by sinyu on 2017/5/1.
 */

public class LibraryPersonalFragment extends Fragment {

    private View view;
    private LinearLayout bt_zx;
    private SharedPreferences sp;
    private HorizontalProgressbarWithProgress progressBar;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view=inflater.inflate(R.layout.fragment_library_personal, container, false);
        sp=LibraryPersonalFragment.this.getActivity().getSharedPreferences(MyConstants.LibraryLogin_FIRST, 0);
        if (!sp.getBoolean(MyConstants.LibraryLogin_FIRST, false)) {
            UIHelper.showLibraryLogin(LibraryPersonalFragment.this.getActivity());
        }
        bt_zx=(LinearLayout)view.findViewById(R.id.tv_per_zx);
        initonClik();
        try {
            initview();
        }catch (Exception e){
            e.printStackTrace();
        }

        return view;
    }

    private void initview(){
        ImageView img_sex= (ImageView) view.findViewById(R.id.img_sex);
        TextView tv_name= (TextView) view.findViewById(R.id.name);
        TextView tv_sex= (TextView) view.findViewById(R.id.sex);
        TextView tv_chenhao= (TextView) view.findViewById(R.id.chenhao);
        TextView tv_leiji= (TextView) view.findViewById(R.id.leiji);
        TextView tv_weizhang= (TextView) view.findViewById(R.id.weizhang);
        TextView tv_qiankuan= (TextView) view.findViewById(R.id.qiankuan);
        TextView tv_chaoyue= (TextView) view.findViewById(R.id.chaoyue);
        progressBar= (HorizontalProgressbarWithProgress) view.findViewById(R.id.progressBar);
        SharedPreferences sp = LibraryPersonalFragment.this.getActivity().getSharedPreferences(
                "Library_StuData", 0);
        tv_name.setText("姓名："+sp.getString("name",""));
        tv_sex.setText("性别："+sp.getString("sex",""));
        tv_leiji.setText("累计借阅："+sp.getString("leiji",""));
        tv_chenhao.setText("称号："+sp.getString("chenghao",""));
        tv_weizhang.setText("违章次数："+sp.getString("weizhangcishu","")+"次");
        tv_qiankuan.setText("欠款金额："+sp.getString("qiankuanjine","")+"元");
        String chaoyue="排在"+sp.getString("chaoyue","")+"的人之前";
        if(sp.getString("sex","").equals("女")){
            img_sex.setImageResource(R.drawable.woman);
        }
        Utility.showChapin(LibraryPersonalFragment.this.getActivity());
        int fstart=chaoyue.indexOf(sp.getString("chaoyue",""));
        int fend=fstart+sp.getString("chaoyue","").length();
        SpannableStringBuilder style=new SpannableStringBuilder(chaoyue);
        style.setSpan(new ForegroundColorSpan(Color.BLUE),fstart,fend, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        style.setSpan(new RelativeSizeSpan(2),fstart,fend, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        tv_chaoyue.setText(style);
        progressBar.setProgress(Integer.parseInt(sp.getString("chaoyue","").split("%")[0]));
    }
    private void initonClik(){
        bt_zx.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //注销
                AlertDialog.Builder builder=new AlertDialog.Builder(LibraryPersonalFragment.this.getActivity());  //先得到构造器
                builder.setTitle("提示"); //设置标题
                builder.setMessage("是否确认退出?"); //设置内容
                //builder.setIcon(R.mipmap.ic_launcher);//设置图标，图片id即可
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() { //设置确定按钮
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss(); //关闭dialog
                        sp.edit().putBoolean(MyConstants.LibraryLogin_FIRST, false).commit();

                        UIHelper.showLibraryLogin(LibraryPersonalFragment.this.getActivity());
                        // LibraryPersonalFragment.this.getActivity().finish();

                        //Toast.makeText(PerInfoActivity.this, "确认" + which, Toast.LENGTH_SHORT).show();
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() { //设置取消按钮
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        // Toast.makeText(MainActivity.this, "取消" + which, Toast.LENGTH_SHORT).show();
                    }
                });
                //参数都设置完成了，创建并显示出来
                builder.create().show();

            }
        });
    }

}
