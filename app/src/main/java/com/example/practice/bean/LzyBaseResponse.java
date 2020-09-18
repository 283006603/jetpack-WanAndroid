package com.example.practice.bean;

public class LzyBaseResponse<T> {

    private int errorCode;
    private String errorMsg;
    private T data;

    @Override
    public String toString(){
        return "LzyBaseResponse{" + "errorCode=" + errorCode + ", errorMsg='" + errorMsg + '\'' + ", data=" + data + '}';
    }

    public int getErrorCode(){
        return errorCode;
    }

    public void setErrorCode(int errorCode){
        this.errorCode = errorCode;
    }

    public String getErrorMsg(){
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg){
        this.errorMsg = errorMsg;
    }

    public T getData(){
        return data;
    }

    public void setData(T data){
        this.data = data;
    }
}