package cn.czfy.xyq.domain;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by sinyu on 2017/4/13.
 */

public class User extends BmobObject {

    String xh;
    String xibu;
    String banji;
    //姓名
    String name;
    //头像
    BmobFile touxiang;
    //密码
    String pwd;
    //手机号码
    String phone;

    public String getXh() {
        return xh;
    }

    public void setXh(String xh) {
        this.xh = xh;
    }

    public String getXibu() {
        return xibu;
    }

    public void setXibu(String xibu) {
        this.xibu = xibu;
    }

    public String getBanji() {
        return banji;
    }

    public void setBanji(String banji) {
        this.banji = banji;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BmobFile getTouxiang() {
        return touxiang;
    }

    public void setTouxiang(BmobFile touxiang) {
        this.touxiang = touxiang;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
