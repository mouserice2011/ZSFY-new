package cn.czfy.zsdx.tool;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import cn.czfy.zsdx.R;

/**
 * Created by sinyu on 2017/3/31.
 */

public class checkUpdateAPK {

    private static final String TAG = "context";
    private String localVersion;
    private UpdataInfo info;
    private String detail;
    private final int UPDATA_NONEED = 0;
    private final int UPDATA_CLIENT = 1;
    private final int GET_UNDATAINFO_ERROR = 2;
    private final int SDCARD_NOMOUNTED = 3;
    private final int DOWN_ERROR = 4;
    Context context;

    public checkUpdateAPK(Context context) {
        this.context = context;
        //jianchagengxin();
    }

    public void jianchagengxin() {
        try {
            localVersion = getVersionName();
            CheckVersionTask cv = new CheckVersionTask();
            new Thread(cv).start();

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public class CheckVersionTask implements Runnable {
        InputStream is;

        public void run() {
            try {
                String path = context.getResources().getString(R.string.url_server);
                URL url = new URL(path);
                HttpURLConnection conn = (HttpURLConnection) url
                        .openConnection();
                conn.setConnectTimeout(5000);
                conn.setRequestMethod("GET");
                int responseCode = conn.getResponseCode();
                if (responseCode == 200) {
                    // �ӷ��������һ��������
                    is = conn.getInputStream();
                }
                info = UpdataInfoParser.getUpdataInfo(is);
                if (info.getVersion().equals(localVersion)) {
                    Log.i(TAG, "�汾����ͬ");
                    Message msg = new Message();
                    msg.what = UPDATA_NONEED;
                    handler.sendMessage(msg);
                    // LoginMain();
                } else {
                    Log.i(TAG, "�汾�Ų���ͬ ");
                    Message msg = new Message();
                    msg.what = UPDATA_CLIENT;
                    handler.sendMessage(msg);
                }
            } catch (Exception e) {
                Message msg = new Message();
                msg.what = GET_UNDATAINFO_ERROR;
                handler.sendMessage(msg);
                e.printStackTrace();
            }
        }
    }

    /*
     * ��ȡ��ǰ����İ汾��
     */
    private String getVersionName() throws Exception {
        // ��ȡpackagemanager��ʵ��
        PackageManager packageManager = context.getPackageManager();
        // getPackageName()���㵱ǰ��İ�����0�����ǻ�ȡ�汾��Ϣ
        PackageInfo packInfo = packageManager.getPackageInfo(context.getPackageName(),
                0);
        return packInfo.versionName;
    }

    public void showNoticeDialog() {
        // ����Ի���
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.update_fw_dialog, null);
        builder.setView(view);
        final Dialog noticeDialog = builder.create();
        noticeDialog.show();
        TextView confirm = (TextView) noticeDialog.findViewById(R.id.confirm);
        // text.setOnClickListener(new DialogInterface.OnClickListener()
        confirm.setOnClickListener(new android.view.View.OnClickListener() {

            @Override
            public void onClick(View v) {
                noticeDialog.dismiss();
                downLoadApk();
                // showDownloadDialog();
            }
        });
        TextView desc_tv = (TextView) noticeDialog.findViewById(R.id.desc);
        desc_tv.setText(info.getDescription());
    }

    protected void showUpdataDialog() {
        AlertDialog.Builder builer = new AlertDialog.Builder(context);
        builer.setTitle("�汾����");
        builer.setMessage("��鵽�µİ汾����������������˸��๦�ܣ��Ƿ�������");
        // ����ȷ����ťʱ�ӷ����������� �µ�apk Ȼ��װ ?
        builer.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Log.i(TAG, "����apk,����");
                downLoadApk();
            }
        });
        builer.setNegativeButton("ȡ��", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                // do sth
            }
        });
        AlertDialog dialog = builer.create();
        dialog.show();
    }

    protected void downLoadApk() {
        final ProgressDialog pd; // �������Ի���
        pd = new ProgressDialog(context);
        pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        pd.setMessage("�������ظ���(��λ��KB)");
        pd.show();

        new Thread() {
            @Override
            public void run() {
                try {
                    File file = DownLoadManager.getFileFromServer(
                            info.getUrl(), pd);
                    sleep(3000);
                    installApk(file);
                    pd.dismiss(); // �������������Ի���
                } catch (Exception e) {
                    Message msg = new Message();
                    msg.what = DOWN_ERROR;
                    handler.sendMessage(msg);
                    e.printStackTrace();
                }
            }
        }.start();
    }

    // ��װapk
    protected void installApk(File file) {
        Intent intent = new Intent();
        // ִ�ж���
        intent.setAction(Intent.ACTION_VIEW);
        // ִ�е���������
        intent.setDataAndType(Uri.fromFile(file),
                "application/vnd.android.package-archive");
        context.startActivity(intent);
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            super.handleMessage(msg);
            // UIHelper.ToastMessage(context, info.getUrl());
            switch (msg.what) {
                case UPDATA_NONEED:
//                    Toast.makeText(context, "����Ӧ��Ϊ���°汾",
//                            Toast.LENGTH_SHORT).show();
                    handler.sendEmptyMessage(8);
                    break;
                case UPDATA_CLIENT:
                    // �Ի���֪ͨ�û���������
                    handler.sendEmptyMessage(8);
//                    showUpdataDialog();
                    showNoticeDialog();
                    break;
                case GET_UNDATAINFO_ERROR:
                    // ��������ʱ
                    handler.sendEmptyMessage(8);
                    // Toast.makeText(this.getActivity(), "��ȡ������������Ϣʧ��", 1).show();
                    break;
                case DOWN_ERROR:
                    // ����apkʧ��
                    handler.sendEmptyMessage(8);
                    Toast.makeText(context, "�����°汾ʧ��", Toast.LENGTH_LONG).show();
                    break;
                case 5:
//                    SharedPreferences.Editor et = setting.edit();
//                    // System.out.println("���档������");
//                    et.putBoolean("user_Msg", false);
//                    et.commit();
                    // msgButton.setImageResource(R.drawable.newmessage);
                    break;
                case 6://��ʾdialog
                    // showdia("",msg.obj.toString());
                    break;
            }
        }
    };

}
