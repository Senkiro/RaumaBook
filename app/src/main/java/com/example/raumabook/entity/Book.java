package com.example.raumabook.entity;

import java.io.Serializable;

public class Book implements Serializable {
    private int id;
    private String name;
    private String description;
    private String author;
    private int like;
    private String image;
    private int views;


    public Book() {
    }

    public Book(int id, String name, String description, String author, int like, String image, int views) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.author = author;
        this.like = like;
        this.image = image;
        this.views = views;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }


}
