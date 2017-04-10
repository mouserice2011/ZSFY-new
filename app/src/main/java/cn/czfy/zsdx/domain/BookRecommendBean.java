package cn.czfy.zsdx.domain;

import java.util.List;

/**
 * Created by sinyu on 2017/4/4.
 */

public class BookRecommendBean {

    private List<Book> books;

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }


    public class Book{
        private String bookname;
        private String zuozhe;
        private String tuijianyu;
        private String jianjie;

        public String getBookname() {
            return bookname;
        }

        public void setBookname(String bookname) {
            this.bookname = bookname;
        }

        public String getZuozhe() {
            return zuozhe;
        }

        public void setZuozhe(String zuozhe) {
            this.zuozhe = zuozhe;
        }

        public String getTuijianyu() {
            return tuijianyu;
        }

        public void setTuijianyu(String tuijianyu) {
            this.tuijianyu = tuijianyu;
        }

        public String getJianjie() {
            return jianjie;
        }

        public void setJianjie(String jianjie) {
            this.jianjie = jianjie;
        }
    }

}
