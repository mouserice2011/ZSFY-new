
package cn.czfy.zsfy.http;

import java.util.Random;

public class QueryzaocaoHttp {

	public String Query(final String xh, final String stime, final String etime) {

		String res=null;
		try {
			Random r=new Random();
			int x=r.nextInt(9);
			// httpclient get 请求提交数据
			String path = "http://202.119.168.66:8080/test"+x+"/QueryzaocaoServlet";
			String data = "xh=" + xh + "&stime=" + stime+ "&etime=" + etime;
			res = HttpPostConn.doPOST(path, data);

				System.out.println(res);
				//Toast.makeText(context, "反馈成功", 0).show();


		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("请求失败");
			// showToastInAnyThread("请求失败");
		}
		return res;

	}

}
