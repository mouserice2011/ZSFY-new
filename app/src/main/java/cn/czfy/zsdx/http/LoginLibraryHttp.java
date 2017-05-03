package cn.czfy.zsdx.http;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import java.util.Random;

import cn.czfy.zsdx.db.dao.LibraryDao;
import cn.czfy.zsdx.domain.LibraryLoginBean;

/**
 * Created by sinyu on 2017/5/3.
 */

public class LoginLibraryHttp {
    private static LibraryLoginBean libraryLoginBean;
    private static LibraryDao dao;
    public static int Login(final String xh, final String pwd,
                            final Context context) {
        boolean ok = true;
        try {
            Random r = new Random();
            int x = r.nextInt(8);
            // httpclient get «Î«ÛÃ·Ωª ˝æ›
            String path = "http://202.119.168.66:8080/test/LoginLibraryServlet";
            String data = "xh=" + xh + "&pwd=" + pwd ;
            String result = HttpPostConn.doPOST(path, data);
            System.out.println(result);
            //gsonΩ‚Œˆ
            Gson gson=new Gson();
            libraryLoginBean=gson.fromJson(result.toString(),LibraryLoginBean.class);

            int code =libraryLoginBean.getCode();

            if (code==1) {
                ok = true;
                dao=new LibraryDao(context);
                //Ã·≥ˆ now his
                try {
                    String name = libraryLoginBean.getStuName();
                   // String sex = libraryLoginBean.get;
                    String banji = libraryLoginBean.getBanji();
                    String chaoyue =libraryLoginBean.getChaoyue();
                    String chenghao =libraryLoginBean.getChenghao();
                    String leiji =libraryLoginBean.getLeiji();
                    String Email =libraryLoginBean.getEmail();
                    String phone =libraryLoginBean.getPhone();
                    String weizhangcishu =libraryLoginBean.getWeizhangcishu();
                    String qiankuanjine =libraryLoginBean.getQiankuanjine();
                    //System.out.println(name);
                    SharedPreferences sp = context.getSharedPreferences(
                            "Library_StuData", 0);
                    final SharedPreferences.Editor et = sp.edit();
                    System.out.println("±£¥Ê°£°£°£°£");
                    System.out.println(name + " " + banji);
                    et.putString("name", name);
//                    et.putString("sex", sex);
                    et.putString("chaoyue", chaoyue);
                    et.putString("banji", banji);
                    et.putString("chenghao", chenghao);
                    et.putString("leiji", leiji);
                    et.putString("email", Email);
                    et.putString("phone", phone);
                    et.putString("weizhangcishu", weizhangcishu);
                    et.putString("qiankuanjine", qiankuanjine);

                    et.commit();
                } catch (Exception e) {
                    e.printStackTrace();
                    String name = libraryLoginBean.getStuName();

                    SharedPreferences sp = context.getSharedPreferences(
                            "Library_StuData", 0);
                    final SharedPreferences.Editor et = sp.edit();
                    //System.out.println("±£¥Ê°£°£°£°£");
                    //System.out.println(sex+" "+banji);

                    et.putString("name", name);
                    et.commit();

                    // showToastInAnyThread("«Î«Û ß∞‹");
                }
                //ÃÌº”sql
                for(int i=0;i<libraryLoginBean.getNow_list().size();i++){
                    LibraryLoginBean.NowListBean item=libraryLoginBean.getNow_list().get(i);
                    dao.addNow(item.getBookName(),item.getAuthor(),item.getStartTime(),item.getEndTime(),item.getAddress(),item.getXujieno());

                }
                for(int i=0;i<libraryLoginBean.getHis_list().size();i++){
                    LibraryLoginBean.HisListBean item=libraryLoginBean.getHis_list().get(i);
                    dao.addHis(item.getBookName(),item.getAuthor(),item.getStartTime(),item.getEndTime(),item.getAddress());
                }

//                String chengji = json.getString("chengji");
//                if (!chengji.equals("0")) {
//                  //  jsonAnalysisCJ(chengji, context);
//                    System.out.println("chengji");
//                }
//
//                //Ω‚ŒˆøŒ±Ì
//                String kebiao = json.getString("kebiao");
               // jsonAnalysisKB(kebiao, context);
                //System.out.println(kebiao);

            } else if (code==-1) {
                ok = false;
                System.out.println("µ«¬º ß∞‹");//’À∫≈√‹¬Î¥ÌŒÛ
            } else {
                System.out.println("∑˛ŒÒ∆˜”µº∑«Î…‘∫Û÷ÿ ‘");//’À∫≈√‹¬Î¥ÌŒÛ
                return 2;
            }


            if (ok) {
                return 1;
            } else {
                return 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("∑˛ŒÒ∆˜”µº∑«Î…‘∫Û÷ÿ ‘");
            return 2;
            // showToastInAnyThread("«Î«Û ß∞‹");
        }

    }

}
