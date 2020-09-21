package com.example.practice.bean;

import java.util.List;

public class PageList<T> {
    /**当前页数*/
    private int curPage; //当前页数
    /**每页记录数*/
    private int size; //每页记录数
    /**总页数*/
    private int pageCount; //总页数
    /**总记录数*/
    private int total;//总记录数
    private List<T> datas;

    @Override
    public String toString(){
        return "PageList{" + "curPage=" + curPage + ", size=" + size + ", pageCount=" + pageCount + ", total=" + total + ", datas=" + datas + '}';
    }

    public int getCurPage(){
        return curPage;
    }

    public void setCurPage(int curPage){
        this.curPage = curPage;
    }

    public int getSize(){
        return size;
    }

    public void setSize(int size){
        this.size = size;
    }

    public int getPageCount(){
        return pageCount;
    }

    public void setPageCount(int pageCount){
        this.pageCount = pageCount;
    }

    public int getTotal(){
        return total;
    }

    public void setTotal(int total){
        this.total = total;
    }

    public List<T> getDatas(){
        return datas;
    }

    public void setDatas(List<T> datas){
        this.datas = datas;
    }
}
