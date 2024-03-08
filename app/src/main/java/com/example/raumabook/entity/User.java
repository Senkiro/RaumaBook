package com.example.raumabook.entity;

public class User {
    private String id;
    private String user;
    private  String password;
    private int role;
    private String phone;
    private String address;
    private String image;
    private String gender;

    private  String email;


    public User() {
    }

    public User(String id, String user, String password, int role, String phone, String address, String image, String gender, String email) {
        this.id = id;
        this.user = user;
        this.password = password;
        this.role = role;
        this.phone = phone;
        this.address = address;
        this.image = image;
        this.gender = gender;
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
