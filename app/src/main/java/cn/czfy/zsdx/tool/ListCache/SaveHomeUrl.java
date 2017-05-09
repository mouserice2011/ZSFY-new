package cn.czfy.zsdx.tool.ListCache;

import java.util.ArrayList;
import java.util.List;

import cn.czfy.zsdx.domain.FoundLostListBean;
import cn.czfy.zsdx.domain.GetClickUrlBean;

/**
 * Created by sinyu on 2017/5/7.
 */

public class SaveHomeUrl {
    public static List<GetClickUrlBean.ResBean> urls = new ArrayList<GetClickUrlBean.ResBean>();

    public static void save(List<GetClickUrlBean.ResBean> url) {
        urls = url;
    }

    public static void clear() {
        urls.clear();
    }
}
