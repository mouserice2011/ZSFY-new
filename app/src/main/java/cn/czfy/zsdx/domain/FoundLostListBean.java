package cn.czfy.zsdx.domain;

import java.util.List;

/**
 * Created by sinyu on 2017/4/21.
 */

public class FoundLostListBean {


    private List<ListBean> list;

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * type : Lost
         * title : Ò»¿¨Í¨   ³ÂÏ¼   ·Ä¼ì
         * content : ??
         * phone : 15895071229
         */

        private String type;
        private String title;
        private String content;
        private String phone;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }
    }
}
