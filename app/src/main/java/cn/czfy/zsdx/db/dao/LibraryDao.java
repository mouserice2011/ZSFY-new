package cn.czfy.zsdx.db.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import cn.czfy.zsdx.db.StudentDBOpenhelper;
import cn.czfy.zsdx.domain.LibraryLoginBean;

/**
 * Created by sinyu on 2017/5/3.
 */

public class LibraryDao {
    private StudentDBOpenhelper helper;

    public LibraryDao(Context context) {
        helper = new StudentDBOpenhelper(context);
        helper.getWritableDatabase();
        // System.out.println("创建1");
    }

    public long addNow(String name, String auther, String stime,
                    String etime, String address, String xujieno) {
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("auther", auther);
        values.put("stime", stime);
        values.put("etime", etime);
        values.put("address", address);
        values.put("xujieno", xujieno);
        long rowid = db.insert("libnow", null, values);
        db.close();
        return rowid;
    }

    public int clearNow() {
        SQLiteDatabase db = helper.getWritableDatabase();
        int res = db.delete("libnow", null, null);
        return res;

    }

    public List<LibraryLoginBean.NowListBean> findAll_Now() {
        SQLiteDatabase db = helper.getReadableDatabase();
        List<LibraryLoginBean.NowListBean> infos = new ArrayList<LibraryLoginBean.NowListBean>();
        Cursor cursor = db.query("libnow", new String[] { "name", "auther",
                        "stime", "etime", "address", "xujieno"}, null,
                null, null, null, null);
        while (cursor.moveToNext()) {
            LibraryLoginBean.NowListBean info = new LibraryLoginBean.NowListBean();
            info.setBookName(cursor.getString(0));
            info.setAuthor(cursor.getString(1));
            info.setStartTime(cursor.getString(2));
            info.setEndTime(cursor.getString(3));
            info.setAddress(cursor.getString(4));
            info.setXujieno(cursor.getString(5));
            infos.add(info);
        }
        cursor.close();

        db.close();
        return infos;
    }
    //历史借阅记录
    public long addHis(String name, String auther, String stime,
                       String etime, String address) {
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("auther", auther);
        values.put("stime", stime);
        values.put("etime", etime);
        values.put("address", address);
        long rowid = db.insert("libhis", null, values);
        db.close();
        return rowid;
    }

    public int clearHis() {
        SQLiteDatabase db = helper.getWritableDatabase();
        int res = db.delete("libhis", null, null);
        return res;

    }

    public List<LibraryLoginBean.HisListBean> findAll_His() {
        SQLiteDatabase db = helper.getReadableDatabase();
        List<LibraryLoginBean.HisListBean> infos = new ArrayList<LibraryLoginBean.HisListBean>();
        Cursor cursor = db.query("libhis", new String[] { "name", "auther",
                        "stime", "etime", "address"}, null,
                null, null, null, null);
        while (cursor.moveToNext()) {
            LibraryLoginBean.HisListBean info = new LibraryLoginBean.HisListBean();
            info.setBookName(cursor.getString(0));
            info.setAuthor(cursor.getString(1));
            info.setStartTime(cursor.getString(2));
            info.setEndTime(cursor.getString(3));
            info.setAddress(cursor.getString(4));
            infos.add(info);
        }
        cursor.close();

        db.close();
        return infos;
    }
}
