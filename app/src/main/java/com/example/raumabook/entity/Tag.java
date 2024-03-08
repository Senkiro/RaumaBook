package com.example.raumabook.entity;

import java.util.List;

public class Tag {

    private int id;
    private String name;
    private List<Book> books;

    public Tag(String name, List<Book> books) {
        this.name = name;
        this.books = books;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }
}
