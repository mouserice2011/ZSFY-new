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
        tv_name.setText("������"+sp.getString("name",""));
        tv_sex.setText("�Ա�"+sp.getString("sex",""));
        tv_leiji.setText("�ۼƽ��ģ�"+sp.getString("leiji",""));
        tv_chenhao.setText("�ƺţ�"+sp.getString("chenghao",""));
        tv_weizhang.setText("Υ�´�����"+sp.getString("weizhangcishu","")+"��");
        tv_qiankuan.setText("Ƿ���"+sp.getString("qiankuanjine","")+"Ԫ");
        String chaoyue="����"+sp.getString("chaoyue","")+"����֮ǰ";
        if(sp.getString("sex","").equals("Ů")){
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
                //ע��
                AlertDialog.Builder builder=new AlertDialog.Builder(LibraryPersonalFragment.this.getActivity());  //�ȵõ�������
                builder.setTitle("��ʾ"); //���ñ���
                builder.setMessage("�Ƿ�ȷ���˳�?"); //��������
                //builder.setIcon(R.mipmap.ic_launcher);//����ͼ�꣬ͼƬid����
                builder.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() { //����ȷ����ť
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss(); //�ر�dialog
                        sp.edit().putBoolean(MyConstants.LibraryLogin_FIRST, false).commit();

                        UIHelper.showLibraryLogin(LibraryPersonalFragment.this.getActivity());
                        // LibraryPersonalFragment.this.getActivity().finish();

                        //Toast.makeText(PerInfoActivity.this, "ȷ��" + which, Toast.LENGTH_SHORT).show();
                    }
                });
                builder.setNegativeButton("ȡ��", new DialogInterface.OnClickListener() { //����ȡ����ť
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        // Toast.makeText(MainActivity.this, "ȡ��" + which, Toast.LENGTH_SHORT).show();
                    }
                });
                //��������������ˣ���������ʾ����
                builder.create().show();

            }
        });
    }

}
