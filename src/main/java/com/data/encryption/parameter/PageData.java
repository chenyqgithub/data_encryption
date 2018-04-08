package com.data.encryption.parameter;

import org.springframework.data.domain.Sort;

import java.util.List;

/**
 * Created by admin on 2018/4/4.
 */
public class PageData<T> {
    /**
     * 分页页码
     */
    private String pageNo;
    /**
     * 分页大小
     */
    private String pageSize;
    /**
     * 排序字段
     */
    private String sortField;
    /**
     * 排序方式
     */
    private String sortWay;
    /**
     * 排序对象
     */
    private Sort sort;
    /**
     * 返回参数
     */
    private List<T> pageConent;
    /**
     * 数据总条数
     */
    private int countNum;

    /**
     * 条件对象
     */
    private List<RepositoryParamParsing> paramParsing;

    public String getPageNo() {
        return pageNo;
    }

    public void setPageNo(String pageNo) {
        this.pageNo = pageNo;
    }

    public String getPageSize() {
        return pageSize;
    }

    public void setPageSize(String pageSize) {
        this.pageSize = pageSize;
    }

    public Sort getSort() {
        return sort;
    }

    public void setSort(Sort sort) {
        this.sort = sort;
    }

//    public T getPageConent() {
//        return pageConent;
//    }
//
//    public void setPageConent(T pageConent) {
//        this.pageConent = pageConent;
//    }

    public List<T> getPageConent() {
        return pageConent;
    }

    public void setPageConent(List<T> pageConent) {
        this.pageConent = pageConent;
    }

    public int getCountNum() {
        return countNum;
    }

    public void setCountNum(int countNum) {
        this.countNum = countNum;
    }


    public String getSortField() {
        return sortField;
    }

    public void setSortField(String sortField) {
        this.sortField = sortField;
    }

    public String getSortWay() {
        return sortWay;
    }

    public void setSortWay(String sortWay) {
        this.sortWay = sortWay;
    }

    /**
     * 构建sort
     */
    public void build() {
        this.sort = new Sort(Sort.Direction.DESC, sortField);
    }

    public List<RepositoryParamParsing> getParamParsing() {
        return paramParsing;
    }

    public void setParamParsing(List<RepositoryParamParsing> paramParsing) {
        this.paramParsing = paramParsing;
    }
}