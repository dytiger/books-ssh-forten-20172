package org.forten.books.dto.vo;

import org.forten.util.DateUtil;

import java.util.Date;

public class BookForShow {
    private int id;
    private String name;
    private String author;
    private String press;
    private String pubDateStr;
    private int price;
    private int amount;

    public BookForShow() {

    }

    public BookForShow(int id, String name, String author, String press, Date pubDate, int price, int amount) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.press = press;
        this.setPubDate(pubDate);
        this.price = price;
        this.amount = amount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPress() {
        return press;
    }

    public void setPress(String press) {
        this.press = press;
    }

    public void setPubDate(Date pubDate) {
        if(pubDate==null){
            pubDateStr = "";
        }else {
            this.pubDateStr = DateUtil.convertToString(pubDate,"yyyy年MM月");
        }
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getPubDateStr() {
        return pubDateStr;
    }

    public void setPubDateStr(String pubDateStr) {
        this.pubDateStr = pubDateStr;
    }

    @Override
    public String toString() {
        return "BookForShow{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", author='" + author + '\'' +
                ", press='" + press + '\'' +
                ", pubDateStr=" + pubDateStr +
                ", price=" + price +
                ", amount=" + amount +
                '}';
    }
}
