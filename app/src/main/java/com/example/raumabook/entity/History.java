package com.example.raumabook.entity;

import java.util.Date;

public class History {
    private String id;
    private Date time;
    private Book book;
    private User user;
//    private Page number_page;
    public History() {
    }

    public History(String id, Date time, Book book, User user) {
        this.id = id;
        this.time = time;
        this.book = book;
        this.user = user;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }



    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
