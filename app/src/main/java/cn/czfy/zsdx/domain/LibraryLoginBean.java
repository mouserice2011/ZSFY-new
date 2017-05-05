package cn.czfy.zsdx.domain;

import java.util.List;

/**
 * Created by sinyu on 2017/5/3.
 */

public class LibraryLoginBean  {

    /**
     * code : 1
     * stuName : 刘叶
     * chaoyue : 69%
     * chenghao : 阅读达人
     * leiji : 12册次
     * Email :
     * phone :
     * weizhangcishu :
     * qiankuanjine :
     * banji :
     * now_list : [{"bookName":"英语四级词汇速成词根+词缀记忆法.第3版 ","author":" 刘金龙, 刘晓民主编","startTime":"2017-03-22","endTime":"2017-06-05","address":"第三借阅室","xujieno":"1"}]
     * his_list : [{"bookName":"英语四级词汇随身记","author":"主编张福元","startTime":"2017-03-22","endTime":"2017-04-23","address":"第三借阅室"},{"bookName":"英语四级词汇速成:词根+词缀记忆法.第2版","author":"主编刘金龙, 刘晓民","startTime":"2016-11-16","endTime":"2016-12-20","address":"第三借阅室"},{"bookName":"山海经:珍藏版","author":"(西汉) 刘向，刘歆校刊","startTime":"2016-09-04","endTime":"2016-10-18","address":"第二借阅室"},{"bookName":"鬼谷子新解","author":"鬼谷子著","startTime":"2016-09-02","endTime":"2016-10-18","address":"第三借阅室"},{"bookName":"英语四级词汇速成词根+词缀记忆法.第3版","author":"刘金龙, 刘晓民主编","startTime":"2016-09-02","endTime":"2016-10-18","address":"第三借阅室"},{"bookName":"英语四级词汇速成:词根+词缀记忆法.第2版","author":"主编刘金龙, 刘晓民","startTime":"2016-05-20","endTime":"2016-06-22","address":"第三借阅室"},{"bookName":"JAVA程序设计实用教程","author":"金保华主编","startTime":"2016-05-16","endTime":"2016-06-06","address":"第一借阅室"},{"bookName":"Java程序设计24学时轻松掌握","author":"匡松主编","startTime":"2016-05-08","endTime":"2016-05-16","address":"第一借阅室"},{"bookName":"Android移动应用开发从入门到精通","author":"刘帅旗编著","startTime":"2015-11-30","endTime":"2015-12-31","address":"第一借阅室"},{"bookName":"Java程序设计基础","author":"吴晓东编著","startTime":"2015-04-16","endTime":"2015-05-26","address":"第一借阅室"}]
     */

    private int code;
    private String stuName;
    private String sex;
    private String chaoyue;
    private String chenghao;
    private String leiji;
    private String Email;
    private String phone;
    private String weizhangcishu;
    private String qiankuanjine;
    private String banji;
    private List<NowListBean> now_list;
    private List<HisListBean> his_list;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getStuName() {
        return stuName;
    }

    public void setStuName(String stuName) {
        this.stuName = stuName;
    }

    public String getChaoyue() {
        return chaoyue;
    }

    public void setChaoyue(String chaoyue) {
        this.chaoyue = chaoyue;
    }

    public String getChenghao() {
        return chenghao;
    }

    public void setChenghao(String chenghao) {
        this.chenghao = chenghao;
    }

    public String getLeiji() {
        return leiji;
    }

    public void setLeiji(String leiji) {
        this.leiji = leiji;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getWeizhangcishu() {
        return weizhangcishu;
    }

    public void setWeizhangcishu(String weizhangcishu) {
        this.weizhangcishu = weizhangcishu;
    }

    public String getQiankuanjine() {
        return qiankuanjine;
    }

    public void setQiankuanjine(String qiankuanjine) {
        this.qiankuanjine = qiankuanjine;
    }

    public String getBanji() {
        return banji;
    }

    public void setBanji(String banji) {
        this.banji = banji;
    }

    public List<NowListBean> getNow_list() {
        return now_list;
    }

    public void setNow_list(List<NowListBean> now_list) {
        this.now_list = now_list;
    }

    public List<HisListBean> getHis_list() {
        return his_list;
    }

    public void setHis_list(List<HisListBean> his_list) {
        this.his_list = his_list;
    }

    public static class NowListBean {
        /**
         * bookName : 英语四级词汇速成词根+词缀记忆法.第3版
         * author :  刘金龙, 刘晓民主编
         * startTime : 2017-03-22
         * endTime : 2017-06-05
         * address : 第三借阅室
         * xujieno : 1
         */

        private String bookName;
        private String author;
        private String startTime;
        private String endTime;
        private String address;
        private String xujieno;

        public String getBookName() {
            return bookName;
        }

        public void setBookName(String bookName) {
            this.bookName = bookName;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public String getStartTime() {
            return startTime;
        }

        public void setStartTime(String startTime) {
            this.startTime = startTime;
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getXujieno() {
            return xujieno;
        }

        public void setXujieno(String xujieno) {
            this.xujieno = xujieno;
        }
    }

    public static class HisListBean {
        /**
         * bookName : 英语四级词汇随身记
         * author : 主编张福元
         * startTime : 2017-03-22
         * endTime : 2017-04-23
         * address : 第三借阅室
         */

        private String bookName;
        private String author;
        private String startTime;
        private String endTime;
        private String address;

        public String getBookName() {
            return bookName;
        }

        public void setBookName(String bookName) {
            this.bookName = bookName;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public String getStartTime() {
            return startTime;
        }

        public void setStartTime(String startTime) {
            this.startTime = startTime;
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }
    }
}
