package cn.czfy.zsfy.tool;

import cn.czfy.zsfy.domain.ArticleWeixinBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sinyu on 2017/4/5. ΢�����»���
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