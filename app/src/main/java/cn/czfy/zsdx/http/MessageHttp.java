package cn.czfy.zsdx.http;

import java.util.Random;

public class MessageHttp {

    public static String Message() {

        try {
            Random r = new Random();
            int x = r.nextInt(9);
            // httpclient get �����ύ����
            String path = "http://202.119.168.66:8080/test" + x + "/MessageServlet";

            String result = HttpPostConn.doGET(path, "");
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
