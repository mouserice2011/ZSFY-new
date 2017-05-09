package cn.czfy.zsdx.domain;

import java.util.List;

/**
 * Created by sinyu on 2017/5/7.
 */

public class GetClickUrlBean {

    private List<ResBean> res;

    public List<ResBean> getRes() {
        return res;
    }

    public void setRes(List<ResBean> res) {
        this.res = res;
    }

    public static class ResBean {
        /**
         * url : http://app.sinyu1012.cn
         * memo : APP¹ÙÍø
         * flag : 1
         */

        private String url;
        private String memo;
        private String flag;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getMemo() {
            return memo;
        }

        public void setMemo(String memo) {
            this.memo = memo;
        }

        public String getFlag() {
            return flag;
        }

        public void setFlag(String flag) {
            this.flag = flag;
        }
    }
}
