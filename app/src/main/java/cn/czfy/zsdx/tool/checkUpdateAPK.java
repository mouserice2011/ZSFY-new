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
                    // 从服务器获得一个输入流
                    is = conn.getInputStream();
                }
                info = UpdataInfoParser.getUpdataInfo(is);
                if (info.getVersion().equals(localVersion)) {
                    Log.i(TAG, "版本号相同");
                    Message msg = new Message();
                    msg.what = UPDATA_NONEED;
                    handler.sendMessage(msg);
                    // LoginMain();
                } else {
                    Log.i(TAG, "版本号不相同 ");
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
     * 获取当前程序的版本号
     */
    private String getVersionName() throws Exception {
        // 获取packagemanager的实例
        PackageManager packageManager = context.getPackageManager();
        // getPackageName()是你当前类的包名，0代表是获取版本信息
        PackageInfo packInfo = packageManager.getPackageInfo(context.getPackageName(),
                0);
        return packInfo.versionName;
    }

    public void showNoticeDialog() {
        // 构造对话框
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
        builer.setTitle("版本升级");
        builer.setMessage("检查到新的版本，本次升级，添加了更多功能！是否升级？");
        // 当点确定按钮时从服务器上下载 新的apk 然后安装 ?
        builer.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Log.i(TAG, "下载apk,更新");
                downLoadApk();
            }
        });
        builer.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                // do sth
            }
        });
        AlertDialog dialog = builer.create();
        dialog.show();
    }

    protected void downLoadApk() {
        final ProgressDialog pd; // 进度条对话框
        pd = new ProgressDialog(context);
        pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        pd.setMessage("正在下载更新(单位：KB)");
        pd.show();

        new Thread() {
            @Override
            public void run() {
                try {
                    File file = DownLoadManager.getFileFromServer(
                            info.getUrl(), pd);
                    sleep(3000);
                    installApk(file);
                    pd.dismiss(); // 结束掉进度条对话框
                } catch (Exception e) {
                    Message msg = new Message();
                    msg.what = DOWN_ERROR;
                    handler.sendMessage(msg);
                    e.printStackTrace();
                }
            }
        }.start();
    }

    // 安装apk
    protected void installApk(File file) {
        Intent intent = new Intent();
        // 执行动作
        intent.setAction(Intent.ACTION_VIEW);
        // 执行的数据类型
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
//                    Toast.makeText(context, "您的应用为最新版本",
//                            Toast.LENGTH_SHORT).show();
                    handler.sendEmptyMessage(8);
                    break;
                case UPDATA_CLIENT:
                    // 对话框通知用户升级程序
                    handler.sendEmptyMessage(8);
//                    showUpdataDialog();
                    showNoticeDialog();
                    break;
                case GET_UNDATAINFO_ERROR:
                    // 服务器超时
                    handler.sendEmptyMessage(8);
                    // Toast.makeText(this.getActivity(), "获取服务器更新信息失败", 1).show();
                    break;
                case DOWN_ERROR:
                    // 下载apk失败
                    handler.sendEmptyMessage(8);
                    Toast.makeText(context, "下载新版本失败", Toast.LENGTH_LONG).show();
                    break;
                case 5:
//                    SharedPreferences.Editor et = setting.edit();
//                    // System.out.println("保存。。。。");
//                    et.putBoolean("user_Msg", false);
//                    et.commit();
                    // msgButton.setImageResource(R.drawable.newmessage);
                    break;
                case 6://显示dialog
                    // showdia("",msg.obj.toString());
                    break;
            }
        }
    };

}
