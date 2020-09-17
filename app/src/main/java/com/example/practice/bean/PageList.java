package com.example.practice.bean;

import java.util.List;

public class PageList<T> {
    /**当前页数*/
    private int pageIndex; //当前页数
    /**每页记录数*/
    private int pageSize; //每页记录数
    /**总页数*/
    private int pageCount; //总页数
    /**总记录数*/
    private int records;//总记录数
    private List<T> rows;

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }

    public int getPage() {
        return pageIndex;
    }

    public void setPage(int page) {
        this.pageIndex = page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }


    public int getRecords() {
        return records;
    }

    public void setRecords(int records) {
        this.records = records;
    }

    @Override
    public String toString(){
        return "PageList{" + "pageIndex=" + pageIndex + ", pageSize=" + pageSize + ", pageCount=" + pageCount + ", records=" + records + ", rows=" + rows + '}';
    }
}
