package cn.czfy.zsdx.http;

import java.util.Random;

public class SetUser {
    public static void AddUser(String xh, String name, String sfz, String banji, String xibu, String email, String phone) {

        try {
            // httpclient get �����ύ����
            Random r = new Random();
            int x = r.nextInt(9);
            String path = "http://202.119.168.66:8080/test" + x + "/UserServlet";
            String data = "xh=" + xh + "&name=" + name + "&sfz=" + sfz + "&banji=" + banji + "&xibu=" + xibu + "&email=" + email + "&phone=" + phone;
            String result = HttpPostConn.doPOST(path, data);
            System.out.println("����ɹ�");
            //Toast.makeText(context, "�����ɹ�", 0).show();

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("����ʧ��");
            // showToastInAnyThread("����ʧ��");
        }

    }
    public static void AddLibUser(String xh, String name, String pwd, String banji, String email, String phone, String time, String total) {

        try {
            // httpclient get �����ύ����
            Random r = new Random();
            int x = r.nextInt(9);
            String path = "http://202.119.168.66:8080/test/AddLibraryUserServlet";
            String data = "xh=" + xh + "&name=" + name + "&pwd=" + pwd + "&banji=" + banji + "&time=" + time + "&email=" + email + "&phone=" + phone+ "&total=" + total;
            String result = HttpPostConn.doPOST(path, data);
            System.out.println("����ɹ�");
            //Toast.makeText(context, "�����ɹ�", 0).show();

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("����ʧ��");
            // showToastInAnyThread("����ʧ��");
        }

    }
}
