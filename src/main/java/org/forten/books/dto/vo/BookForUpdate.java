package org.forten.books.dto.vo;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class BookForUpdate {
    private int id;
    private String name;
    private String author;
    private String press;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date pubDate;
    private int price;
    private int amount;

    public BookForUpdate() {
    }

    public BookForUpdate(int id, String name, String author, String press, Date pubDate, int price, int amount) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.press = press;
        this.pubDate = pubDate;
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

    public Date getPubDate() {
        return pubDate;
    }

    public void setPubDate(Date pubDate) {
        this.pubDate = pubDate;
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

    @Override
    public String toString() {
        return "BookForUpdate{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", author='" + author + '\'' +
                ", press='" + press + '\'' +
                ", pubDate=" + pubDate +
                ", price=" + price +
                ", amount=" + amount +
                '}';
    }
}
