package com.example.raumabook.entity;

public class Page {
    private int id;
    private String content;
    private Book book;
    private int number;

    public Page() {
    }

    public Page(int id, String content, Book book, int number) {
        this.id = id;
        this.content = content;
        this.book = book;
        this.number = number;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }
}
