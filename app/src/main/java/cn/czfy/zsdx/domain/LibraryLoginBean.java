package cn.czfy.zsdx.domain;

import java.util.List;

/**
 * Created by sinyu on 2017/5/3.
 */

public class LibraryLoginBean  {

    /**
     * code : 1
     * stuName : ��Ҷ
     * chaoyue : 69%
     * chenghao : �Ķ�����
     * leiji : 12���
     * Email :
     * phone :
     * weizhangcishu :
     * qiankuanjine :
     * banji :
     * now_list : [{"bookName":"Ӣ���ļ��ʻ��ٳɴʸ�+��׺���䷨.��3�� ","author":" ������, ����������","startTime":"2017-03-22","endTime":"2017-06-05","address":"����������","xujieno":"1"}]
     * his_list : [{"bookName":"Ӣ���ļ��ʻ������","author":"�����Ÿ�Ԫ","startTime":"2017-03-22","endTime":"2017-04-23","address":"����������"},{"bookName":"Ӣ���ļ��ʻ��ٳ�:�ʸ�+��׺���䷨.��2��","author":"����������, ������","startTime":"2016-11-16","endTime":"2016-12-20","address":"����������"},{"bookName":"ɽ����:��ذ�","author":"(����) �������У��","startTime":"2016-09-04","endTime":"2016-10-18","address":"�ڶ�������"},{"bookName":"������½�","author":"�������","startTime":"2016-09-02","endTime":"2016-10-18","address":"����������"},{"bookName":"Ӣ���ļ��ʻ��ٳɴʸ�+��׺���䷨.��3��","author":"������, ����������","startTime":"2016-09-02","endTime":"2016-10-18","address":"����������"},{"bookName":"Ӣ���ļ��ʻ��ٳ�:�ʸ�+��׺���䷨.��2��","author":"����������, ������","startTime":"2016-05-20","endTime":"2016-06-22","address":"����������"},{"bookName":"JAVA�������ʵ�ý̳�","author":"�𱣻�����","startTime":"2016-05-16","endTime":"2016-06-06","address":"��һ������"},{"bookName":"Java�������24ѧʱ��������","author":"��������","startTime":"2016-05-08","endTime":"2016-05-16","address":"��һ������"},{"bookName":"Android�ƶ�Ӧ�ÿ��������ŵ���ͨ","author":"��˧�����","startTime":"2015-11-30","endTime":"2015-12-31","address":"��һ������"},{"bookName":"Java������ƻ���","author":"����������","startTime":"2015-04-16","endTime":"2015-05-26","address":"��һ������"}]
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
         * bookName : Ӣ���ļ��ʻ��ٳɴʸ�+��׺���䷨.��3��
         * author :  ������, ����������
         * startTime : 2017-03-22
         * endTime : 2017-06-05
         * address : ����������
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
         * bookName : Ӣ���ļ��ʻ������
         * author : �����Ÿ�Ԫ
         * startTime : 2017-03-22
         * endTime : 2017-04-23
         * address : ����������
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
