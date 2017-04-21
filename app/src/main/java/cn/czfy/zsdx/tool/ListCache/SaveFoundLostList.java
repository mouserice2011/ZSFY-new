package cn.czfy.zsdx.tool.ListCache;

import java.util.ArrayList;
import java.util.List;

import cn.czfy.zsdx.domain.FoundLostListBean;

/**
 * Created by sinyu on 2017/4/21.
 */

public class SaveFoundLostList {
    public static List<FoundLostListBean.ListBean> foundlosts = new ArrayList<FoundLostListBean.ListBean>();

    public static void save(List<FoundLostListBean.ListBean> foundlost) {
        foundlosts = foundlost;
    }

    public static void clear() {
        foundlosts.clear();
    }
}
