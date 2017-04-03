package cn.czfy.zsfy.tool;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;
import android.widget.Toast;

import org.apache.http.client.ClientProtocolException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import cn.czfy.zsfy.activity.LibraryActivity;

/**
 * Created by sinyu on 2017/3/25.
 */

public class Utility {

    private String uri;
    private ImageView imageView;
    private byte[] picByte;
    Context context;

    /**
     * ���ߣ�����ͼƬ����
     * @param uri
     * @param imageView
     */
    public void setPicture(String uri, ImageView imageView) {
        this.uri = uri;
        this.imageView = imageView;
        new Thread(runnable).start();
    }

    @SuppressLint("HandlerLeak")
    Handler handle = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                if (picByte != null) {
                    Bitmap bitmap = BitmapFactory.decodeByteArray(picByte, 0, picByte.length);
                    imageView.setImageBitmap(bitmap);
                }
            }else if (msg.what == 0) {
                lib_pd.dismiss();// �ر�ProgressDialog
                context. startActivity(lib_intent);
            } else {
                Toast.makeText(context, "��������ʱ", Toast.LENGTH_LONG).show();
            }
        }
    };

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            try {
                URL url = new URL(uri);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setReadTimeout(10000);

                if (conn.getResponseCode() == 200) {
                    InputStream fis = conn.getInputStream();
                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    byte[] bytes = new byte[1024];
                    int length = -1;
                    while ((length = fis.read(bytes)) != -1) {
                        bos.write(bytes, 0, length);
                    }
                    picByte = bos.toByteArray();
                    bos.close();
                    fis.close();

                    Message message = new Message();
                    message.what = 1;
                    handle.sendMessage(message);
                }


            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    };
    List<BookData> bd;
    Intent lib_intent;
     ProgressDialog lib_pd;

    /**
     * ���ߣ� ͼ�������ͼ��
     * @param context
     * @param str
     */
    public void searchBook(Context context, final String str) {
       // final List<BookData> bd=new ArrayList<>();
        this.context=context;
        lib_intent = new Intent(context, LibraryActivity.class);
                /* ��ʾProgressDialog */
        lib_pd = ProgressDialog.show(context, "", "�����У����Ժ󡭡�");

        new Thread() {
            public void run() {
                SearchBook s = new SearchBook();
                try {
                    SaveBookData.clear1();
                    bd = s.search(str);// ��ʱ�ķ���
                } catch (ClientProtocolException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                if (bd== null) {
                    lib_pd.dismiss();// �ر�ProgressDialog
//							showToastInAnyThread("��������ʱ");
                    handle.sendEmptyMessage(2);//���̸߳���ui  toast
                } else
                    handle.sendEmptyMessage(0);// ִ�к�ʱ�ķ���֮��������handler
            }

            ;
        }.start();
    }

}
