package com.youhe.utils.page;

import java.io.Serializable;

/**
 * <p> 类说明 </p>
 *
 * @author hanrs
 * Date: 2017年08月07日
 * @version 1.01
 * 分页参数
 */
public class Page implements Serializable {

    /**
     * 属性说明
     */
    private static final long serialVersionUID = 1L;

    private int pageNum = 1;

    private int pageSize = 10;

    private int fromRow;

    private int endRow;

    private int page;
    private int limit;


    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    /**
     * Return property pageNum
     */
    public int getPageNum() {
        return pageNum;
    }

    /**
     * Sets property pageNum
     */
    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    /**
     * Return property pageSize
     */
    public int getPageSize() {
        return pageSize;
    }

    /**
     * Sets property pageSize
     */
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    /**
     * Return property fromRow
     */
    public int getFromRow() {
        return fromRow;
    }

    /**
     * Sets property fromRow
     */
    public void setFromRow(int fromRow) {
        this.fromRow = fromRow;
    }

    /**
     * Return property endRow
     */
    public int getEndRow() {
        return endRow;
    }

    /**
     * Sets property endRow
     */
    public void setEndRow(int endRow) {
        this.endRow = endRow;
    }

}
