package org.forten.books.dto.vo;

import org.forten.util.DateUtil;

import java.util.Date;

public class BorrowedBooksVo {
    private int id;
    private String userName;
    private String bookName;
    private String borrowStatus;
    private String borrowStatusDes;
    private String borrowTimeStr;

    public BorrowedBooksVo() {
    }

    public BorrowedBooksVo(int id, String userName, String bookName, String borrowStatus, Date borrowTime) {
        this.id = id;
        this.userName = userName;
        this.bookName = bookName;
        this.borrowStatus = borrowStatus;
        setBorrowStatus(borrowStatus);
        setBorrowTime(borrowTime);
    }

    public void setBorrowStatus(String borrowStatus) {
        if (borrowStatus == null) {
            this.borrowStatusDes = "未知";
        } else {
            switch (borrowStatus) {
                case "PB":
                    this.borrowStatusDes = "预借";
                    break;
                case "BD":
                    this.borrowStatusDes = "借入";
                    break;
                default:
                    this.borrowStatusDes = "未知";
            }
        }
    }

    public void setBorrowTime(Date borrowTime) {
        if (borrowTime == null) {
            this.borrowTimeStr = "";
        } else {
            this.borrowTimeStr = DateUtil.convertToString(borrowTime,"yyyy年MM月dd日");
        }
    }

    public String getBorrowStatus() {
        return borrowStatus;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getBorrowStatusDes() {
        return borrowStatusDes;
    }

    public void setBorrowStatusDes(String borrowStatusDes) {
        this.borrowStatusDes = borrowStatusDes;
    }

    public String getBorrowTimeStr() {
        return borrowTimeStr;
    }

    public void setBorrowTimeStr(String borrowTimeStr) {
        this.borrowTimeStr = borrowTimeStr;
    }

    @Override
    public String toString() {
        return "BorrowedBooksVo{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", bookName='" + bookName + '\'' +
                ", borrowStatusDes='" + borrowStatusDes + '\'' +
                ", borrowTimeStr='" + borrowTimeStr + '\'' +
                '}';
    }
}
