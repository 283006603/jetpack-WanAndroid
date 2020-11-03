package com.example.practice.bean;

public class GankLzyBaseResponse<T> {

    private int status;
    private T data;
    int page;
    int page_count;
    int total_counts;

    @Override
    public String toString(){
        return "GankLzyBaseResponse{" + "status=" + status + ", data=" + data + ", page=" + page + ", page_count=" + page_count + ", total_counts=" + total_counts + '}';
    }

    public int getStatus(){
        return status;
    }

    public void setStatus(int status){
        this.status = status;
    }

    public T getData(){
        return data;
    }

    public void setData(T data){
        this.data = data;
    }

    public int getPage(){
        return page;
    }

    public void setPage(int page){
        this.page = page;
    }

    public int getPage_count(){
        return page_count;
    }

    public void setPage_count(int page_count){
        this.page_count = page_count;
    }

    public int getTotal_counts(){
        return total_counts;
    }

    public void setTotal_counts(int total_counts){
        this.total_counts = total_counts;
    }
}