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
                //ע��
                AlertDialog.Builder builder=new AlertDialog.Builder(LibraryPersonalFragment.this.getActivity());  //�ȵõ�������
                builder.setTitle("��ʾ"); //���ñ���
                builder.setMessage("�Ƿ�ȷ���˳�?"); //��������
                //builder.setIcon(R.mipmap.ic_launcher);//����ͼ�꣬ͼƬid����
                builder.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() { //����ȷ����ť
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss(); //�ر�dialog
                        sp.edit().putBoolean(MyConstants.FIRST, false).commit();
                        MainActivity.Intaface.finish();
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
        return view;
    }


}
