package org.forten.books.dto.qo;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class BookQo {
    private String name;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date begin;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date end;
    private int pageNo;
    private int pageSize;

    public BookQo() {
    }

    public BookQo(String name, Date begin, Date end, int pageNo, int pageSize) {
        this.name = name;
        this.begin = begin;
        this.end = end;
        this.pageNo = pageNo;
        this.pageSize = pageSize;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBegin() {
        return begin;
    }

    public void setBegin(Date begin) {
        this.begin = begin;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    @Override
    public String toString() {
        return "BookQo{" +
                "name='" + name + '\'' +
                ", begin=" + begin +
                ", end=" + end +
                ", pageNo=" + pageNo +
                ", pageSize=" + pageSize +
                '}';
    }
}
