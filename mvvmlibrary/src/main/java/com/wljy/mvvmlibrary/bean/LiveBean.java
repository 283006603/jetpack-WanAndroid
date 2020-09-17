package com.wljy.mvvmlibrary.bean;

public class LiveBean {
    private String key;
    private Object data;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public LiveBean(String key, Object data) {
        this.key = key;
        this.data = data;
    }
}
