package com.example.raumabook.entity;

public class BookTag {
    private int id;
    private Book book;
    private Tag tag;

    public BookTag() {
    }

    public BookTag(int id, Book book, Tag tag) {
        this.id = id;
        this.book = book;
        this.tag = tag;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Tag getTag() {
        return tag;
    }

    public void setTag(Tag tag) {
        this.tag = tag;
    }
}
