package com.youhe.utils.page;

import com.baomidou.mybatisplus.annotation.TableField;

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

    @TableField(exist = false)
    private int pageNum = 1;

    @TableField(exist = false)
    private int pageSize = 10;

    @TableField(exist = false)
    private int fromRow;

    @TableField(exist = false)
    private int endRow;

    @TableField(exist = false)
    private int page;
    @TableField(exist = false)
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
