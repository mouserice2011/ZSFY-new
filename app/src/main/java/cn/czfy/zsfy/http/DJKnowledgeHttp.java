package cn.czfy.zsfy.http;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Random;

import cn.czfy.zsfy.db.dao.StudentDao;

public class DJKnowledgeHttp {

    public static int getKnowledge(Context context) {

        try {
            Random r = new Random();
            int x = r.nextInt(9);
            // httpclient get 请求提交数据
            String path = "http://202.119.168.66:8080/test" + x + "/DJKnowledgeServlet";

            String result = HttpPostConn.doGET(path, "");
            System.out.println(result);
            setDate(context, result);
            // Toast.makeText(context, "反馈成功", 0).show();
            return 1;

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("请求失败");
            return 0;
            // showToastInAnyThread("请求失败");
        }

    }

    /**
     * 解析数据 并保存
     *
     * @param context
     * @throws JSONException
     */
    public static void setDate(Context context, String result)
            throws JSONException {
        StudentDao dao = new StudentDao(context);
        JSONArray jsarr = new JSONArray(result);
        dao.clearDJK();
        for (int i = 0; i < jsarr.length(); i++) {
            JSONObject json = jsarr.getJSONObject(i);
            String type = "";
            try {
                type = json.getString("type");
            } catch (Exception e) {
                // TODO: handle exception
                type = " ";
            }
            String title = "";
            try {
                title = json.getString("title");
            } catch (Exception e) {
                // TODO: handle exception
                title = " ";
            }
            String content = "";
            try {
                content = json.getString("content");
            } catch (Exception e) {
                // TODO: handle exception
                content = " ";
            }

            String answerA = "";
            try {
                answerA = json.getString("answerA");
            } catch (Exception e) {
                // TODO: handle exception
                answerA = " ";
            }
            String answerB = "";
            try {
                answerB = json.getString("answerB");
            } catch (Exception e) {
                // TODO: handle exception
                answerB = " ";
            }
            String answerC = "";
            try {
                answerC = json.getString("answerC");
            } catch (Exception e) {
                // TODO: handle exception
                answerC = " ";
            }

            String answerD = "";
            try {
                answerD = json.getString("answerD");
            } catch (Exception e) {
                // TODO: handle exception
                answerD = " ";
            }
            String answer = "";
            try {
                answer = json.getString("answer");
            } catch (Exception e) {
                // TODO: handle exception
                answer = " ";
            }

            System.out.println("成功");
            dao.addDJ(type.trim(), title.trim(), content.trim(),
                    answerA.trim(), answerB.trim(), answerC.trim(),
                    answerD.trim(), answer.trim());
        }
    }

}
