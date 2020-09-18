package com.msg.laza.project.model;

public class Account {
    private int id;
    private int userId;
    private String createdAt;
    private double funds;

    public Account(int id, int userId, String createdAt, double funds) {
        this.id = id;
        this.userId = userId;
        this.createdAt = createdAt;
        this.funds = funds;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public double getfunds() {
        return funds;
    }

    public void setfunds(double funds) {
        this.funds = funds;
    }

    @Override
    public String toString() {
        return "ServletAccount{" +
                "id=" + id +
                ", userId=" + userId +
                ", createdAt='" + createdAt + '\'' +
                ", funds=" + funds +
                '}';
    }}
