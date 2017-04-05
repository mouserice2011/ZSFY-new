package cn.czfy.zsfy.tool;

import java.util.ArrayList;
import java.util.List;

import cn.czfy.zsfy.domain.BookRecommendBean;

/**
 * Created by sinyu on 2017/4/4.
 */

public class SaveBookRecommend {
    public static List<BookRecommendBean.Book> books=new ArrayList<BookRecommendBean.Book>();
    public static List<BookRecommendBean.Book> books1=new ArrayList<BookRecommendBean.Book>();
    public static void save(List<BookRecommendBean.Book> books1)
    {
        books=books1;
    }
    public static void save1(BookRecommendBean.Book books2)
    {
        books1.add(books2);
    }
    public static void clear()
    {
        books1.clear();
    }
    public static void clear1()
    {
        books.clear();
    }
}
