package com.example.practice.bean;

public class BannerBean{

        /**
         * desc : 享学~
         * id : 29
         * imagePath : https://wanandroid.com/blogimgs/bfcf57e5-aa5d-4ca3-9ca9-245dcbfd31e9.png
         * isVisible : 1
         * order : 0
         * title : 【Android开发教程】高级UI：自定义ViewGroup与UI性能优化
         * type : 0
         * url : https://www.bilibili.com/video/BV1Ka4y1j7HA
         */

        private String desc;
        private int id;
        private String imagePath;
        private int isVisible;
        private int order;
        private String title;
        private int type;
        private String url;

        public String getDesc(){
            return desc;
        }

        public void setDesc(String desc){
            this.desc = desc;
        }

        public int getId(){
            return id;
        }

        public void setId(int id){
            this.id = id;
        }

        public String getImagePath(){
            return imagePath;
        }

        public void setImagePath(String imagePath){
            this.imagePath = imagePath;
        }

        public int getIsVisible(){
            return isVisible;
        }

        public void setIsVisible(int isVisible){
            this.isVisible = isVisible;
        }

        public int getOrder(){
            return order;
        }

        public void setOrder(int order){
            this.order = order;
        }

        public String getTitle(){
            return title;
        }

        public void setTitle(String title){
            this.title = title;
        }

        public int getType(){
            return type;
        }

        public void setType(int type){
            this.type = type;
        }

        public String getUrl(){
            return url;
        }

        public void setUrl(String url){
            this.url = url;
        }
}
