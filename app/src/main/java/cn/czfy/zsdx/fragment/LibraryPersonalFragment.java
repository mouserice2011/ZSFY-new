package cn.czfy.zsdx.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import cn.czfy.zsdx.R;
import cn.czfy.zsdx.activity.MainActivity;
import cn.czfy.zsdx.tool.MyConstants;
import cn.czfy.zsdx.ui.UIHelper;

/**
 * Created by sinyu on 2017/5/1.
 */

public class LibraryPersonalFragment extends Fragment {

    private View view;

    private LinearLayout bt_zx;
    private SharedPreferences sp;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view=inflater.inflate(R.layout.fragment_library_personal, container, false);
        sp=LibraryPersonalFragment.this.getActivity().getSharedPreferences(MyConstants.LibraryLogin_FIRST, 0);
        if (!sp.getBoolean(MyConstants.FIRST, false)) {
            UIHelper.showLibraryLogin(LibraryPersonalFragment.this.getActivity());
        }
        bt_zx=(LinearLayout)view.findViewById(R.id.tv_per_zx);

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
                        sp.edit().putBoolean(MyConstants.FIRST, false).commit();
                        MainActivity.Intaface.finish();
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
        return view;
    }


}
