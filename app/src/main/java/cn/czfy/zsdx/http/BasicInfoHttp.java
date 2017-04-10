package cn.czfy.zsdx.http;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Random;

import cn.czfy.zsdx.tool.DateUtils;

/**
 * Created by sinyu on 2017/2/14.
 */


public class BasicInfoHttp {

    private static final String TAG = "BasicInfoHttp";

    public static String BasicInfo() {

        try {
            Random r = new Random();
            int x = r.nextInt(9);
            // httpclient get �����ύ����
            String path = "http://202.119.168.66:8080/test" + x + "/BasicInfoServlet";
            //String path = "http://202.119.168.53:8080/test/LoginServlet";


            String result = HttpPostConn.doGET(path, null);
            JSONArray jsarr = new JSONArray(result);
            JSONObject json = jsarr.getJSONObject(0);
            String kaixueriqi = json.getString("kaixueriqi");
            DateUtils.startDay = kaixueriqi;
            Log.d(TAG, "BasicInfo: " + DateUtils.startDay);
            //String title=json.getString("title");
            //System.out.println(result);
            // Toast.makeText(context, "�����ɹ�", 0).show();
            return result;

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("����ʧ��");
            return "0";
            // showToastInAnyThread("����ʧ��");
        }


    }

}

