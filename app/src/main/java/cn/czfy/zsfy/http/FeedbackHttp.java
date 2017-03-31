package cn.czfy.zsfy.http;

import android.content.Context;

import java.util.Random;

public class FeedbackHttp {

    public static void Back(final String email, final String content,
                            final Context context) {

        try {
            Random r = new Random();
            int x = r.nextInt(9);
            // httpclient get 请求提交数据
            String path = "http://202.119.168.66:8080/test" + x + "/feedbackServlet";
            String data = "email=" + email + "&content=" + content;
            String result = HttpPostConn.doPOST(path, data);
            System.out.println("反馈成功");
            //Toast.makeText(context, "反馈成功", 0).show();

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("请求失败");
            // showToastInAnyThread("请求失败");
        }

    }

}
