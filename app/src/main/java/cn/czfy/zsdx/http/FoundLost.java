package cn.czfy.zsdx.http;


import java.util.Random;

public class FoundLost {
	public static void Add(String title, String type, String name, String phone, String describe, String time) {
		try {
			Random r=new Random();
			int x=r.nextInt(9);
			// httpclient get �����ύ����
			String path = "http://202.119.168.66:8080/test"+x+"/FoundLostServer";
			String data = "title=" + title + "&type=" + type+ "&name=" + name+ "&phone=" + phone+ "&describe=" + describe+ "&time=" + time;
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
