package cn.czfy.zsdx.http;


import java.util.Random;

public class FoundLost {
	public static void Add(String title, String type, String name, String phone, String describe, String time) {
		try {
			Random r=new Random();
			int x=r.nextInt(9);
			// httpclient get 请求提交数据
			String path = "http://202.119.168.66:8080/test"+x+"/FoundLostServer";
			String data = "title=" + title + "&type=" + type+ "&name=" + name+ "&phone=" + phone+ "&describe=" + describe+ "&time=" + time;
			String result = HttpPostConn.doPOST(path, data);

				System.out.println("保存成功");
				//Toast.makeText(context, "反馈成功", 0).show();

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("请求失败");
			// showToastInAnyThread("请求失败");
		}

	}
}
