package cn.czfy.zsdx.tool.ListCache;

import java.util.ArrayList;
import java.util.List;

import cn.czfy.zsdx.domain.ArticleWeixinBean;

/**
 * Created by sinyu on 2017/4/5. н╒пендуб╩╨╢Ф
 */

public class SaveWeixinArticle {
    public static List<ArticleWeixinBean.newsList> articles = new ArrayList<ArticleWeixinBean.newsList>();

    public static void save(List<ArticleWeixinBean.newsList> books1) {
        articles = books1;
    }

    public static void clear() {
        articles.clear();
    }
}
