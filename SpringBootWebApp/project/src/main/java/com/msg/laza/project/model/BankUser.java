package com.msg.laza.project.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BankUser {
    @JsonProperty("id")
    private int id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("surname")
    private String surname;
    @JsonProperty("city")
    private String city;
    @JsonProperty("born")
    private String born;

    public BankUser() {
    }

    public BankUser(int id, String name, String surname, String city, String born) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.city = city;
        this.born = born;
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

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getBorn() {
        return born;
    }

    public void setBorn(String born) {
        this.born = born;
    }

    @Override
    public String toString() {
        return "BankUser{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", city='" + city + '\'' +
                ", born='" + born + '\'' +
                '}';
    }
}
