package cn.czfy.zsdx.tool;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.apache.http.client.ClientProtocolException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import cn.czfy.zsdx.activity.LibraryActivity;
import cn.czfy.zsdx.domain.ArticleWeixinBean;
import cn.czfy.zsdx.domain.BookRecommendBean;
import cn.czfy.zsdx.domain.FoundLostListBean;
import cn.czfy.zsdx.http.HttpPostConn;
import cn.czfy.zsdx.tool.ListCache.SaveBookData;
import cn.czfy.zsdx.tool.ListCache.SaveBookRecommend;
import cn.czfy.zsdx.tool.ListCache.SaveFoundLostList;
import cn.czfy.zsdx.tool.ListCache.SaveWeixinArticle;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by sinyu on 2017/3/25.
 */

public class Utility {

    private String uri;
    private ImageView imageView;
    private byte[] picByte;
    Context context;

    /**
     * 工具：网络图片加载
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
                lib_pd.dismiss();// 关闭ProgressDialog
                context. startActivity(lib_intent);
            } else {
                Toast.makeText(context, "网络请求超时", Toast.LENGTH_LONG).show();
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
     * 工具： 图书馆搜索图书
     * @param context
     * @param str
     */
    public void searchBook(Context context, final String str) {
       // final List<BookData> bd=new ArrayList<>();
        this.context=context;
        String xh=context.getSharedPreferences("StuData",0).getString("xh","访客");
        setSearchBookLog(xh,str);
        lib_intent = new Intent(context, LibraryActivity.class);
        lib_intent.putExtra("strBookname",str);
                /* 显示ProgressDialog */
        lib_pd = ProgressDialog.show(context, "", "加载中，请稍后……");

        new Thread() {
            public void run() {
                SearchBook s = new SearchBook();
                try {
                    SaveBookData.clear1();
                    bd = s.search(str);// 耗时的方法
                } catch (ClientProtocolException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                if (bd== null) {
                    lib_pd.dismiss();// 关闭ProgressDialog
//							showToastInAnyThread("网络请求超时");
                    handle.sendEmptyMessage(2);//子线程更新ui  toast
                } else
                    handle.sendEmptyMessage(0);// 执行耗时的方法之后发送消给handler
            }

            ;
        }.start();
    }
    public static  void setSearchBookLog(final String xh,final String bookname) {
        Log.d("setSearchBookLog", "setSearchBookLog: " + bookname);
        new Thread(new Runnable() {
            @Override
            public void run() {
                String path = "http://202.119.168.66:8080/test/SearchBookLogServlet";
                String data = "xh=" + xh + "&bookname=" + bookname + "&time=" + Utility.getnowTime();
                String result = HttpPostConn.doPOST(path, data);
            }
        }).start();


    }
    public static String getnowTime(){
        SimpleDateFormat formatter = new SimpleDateFormat(
                "yyyy年MM月dd日HH:mm:ss");
        Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
        String time = formatter.format(curDate);
        return time;
    }

    public static void getBookRem() {
        final Utility utility=new Utility();
        //创建okHttpClient对象
        OkHttpClient mOkHttpClient = new OkHttpClient();
        //创建一个Request
        final Request request = new Request.Builder()
                .url("http://202.119.168.66:8080/test/BookRecommendServlet")
                .build();
        //new call
        Call call = mOkHttpClient.newCall(request);
        //请求加入调度
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //醉了 response.body().string()只能使用一次
                Gson gson=new Gson();
                BookRecommendBean bookRecommendBean=new BookRecommendBean();
                bookRecommendBean=gson.fromJson(response.body().string().toString(),BookRecommendBean.class);
                SaveBookRecommend.save(bookRecommendBean.getBooks());
           }
        });
    }
    public static void getFoundLost() {
        final Utility utility=new Utility();
        //创建okHttpClient对象
        OkHttpClient mOkHttpClient = new OkHttpClient();
        //创建一个Request
        final Request request = new Request.Builder()
                .url("http://202.119.168.66:8080/test/GetFoundLostListServlet")
                .build();
        //new call
        Call call = mOkHttpClient.newCall(request);
        //请求加入调度
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //醉了 response.body().string()只能使用一次
                Gson gson=new Gson();
                FoundLostListBean foundLostListBean=new FoundLostListBean();
                foundLostListBean=gson.fromJson(response.body().string().toString(),FoundLostListBean.class);
                SaveFoundLostList.save(foundLostListBean.getList());

            }
        });
    }

    public static void getWeixinArticle() {//微信缓存
        final Utility utility=new Utility();
        //创建okHttpClient对象
        OkHttpClient mOkHttpClient = new OkHttpClient();
        //创建一个Request
        final Request request = new Request.Builder()
                .url("https://api.tianapi.com/wxnew/?key=2e31ce9b9f0ddd581df3157015bcadc8&num=20&rand=1")
                .build();
        //new call
        Call call = mOkHttpClient.newCall(request);
        //请求加入调度
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //醉了 response.body().string()只能使用一次
                Gson gson=new Gson();
                ArticleWeixinBean articleWeixinBean=new ArticleWeixinBean();
                articleWeixinBean=gson.fromJson(response.body().string().toString(),ArticleWeixinBean.class);
                SaveWeixinArticle.save(articleWeixinBean.getNewslist());
            }
        });
    }
}
