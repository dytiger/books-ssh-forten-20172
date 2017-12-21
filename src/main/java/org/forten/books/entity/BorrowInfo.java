package org.forten.books.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name="borrow_info")
public class BorrowInfo implements Serializable{
    @Id
    private int id;
    @Column
    private int uId;
    @Column
    private int bId;
    @Column(name="borrow_time")
    private Date borrowTime;
    @Column(name="borrow_status")
    private String borrowStatus;

    public BorrowInfo() {
        this.borrowTime = new Date();
        this.borrowStatus = "PB";
    }

    public BorrowInfo(int uId, int bId) {
        this();
        this.uId = uId;
        this.bId = bId;
    }

    public int getId() {
        return id;
    }

    public Date getBorrowTime() {
        return borrowTime;
    }

    public int getuId() {
        return uId;
    }

    public void setuId(int uId) {
        this.uId = uId;
    }

    public int getbId() {
        return bId;
    }

    public void setbId(int bId) {
        this.bId = bId;
    }

    public String getBorrowStatus() {
        return borrowStatus;
    }

    public void setBorrowStatus(String borrowStatus) {
        this.borrowStatus = borrowStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BorrowInfo that = (BorrowInfo) o;

        return id == that.id;
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public String toString() {
        return "BorrowInfo{" +
                "id=" + id +
                ", uId=" + uId +
                ", bId=" + bId +
                ", borrowTime=" + borrowTime +
                ", borrowStatus='" + borrowStatus + '\'' +
                '}';
    }
}
