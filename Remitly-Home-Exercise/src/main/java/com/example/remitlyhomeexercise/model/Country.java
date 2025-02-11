package com.example.remitlyhomeexercise.model;

import jakarta.persistence.*;
@Entity
@Table(name = "countries")
public class Country {
    @Id
    @GeneratedValue
    private long id;

    @Column(length = 64)
    private String iso2;

    @Column(length = 64)
    private String name;

    @Column(length = 64)
    private String time_zone;

    public Country(){}

    public Country(String iso2, String name, String time_zone){
        this.iso2 = iso2;
        this.name = name;
        this.time_zone = time_zone;
    }

    public String getIso2() {
        return iso2;
    }

    public String getName() {
        return name;
    }

    public String getTime_zone() {
        return time_zone;
    }

    public void setIso2(String iso2) {
        this.iso2 = iso2;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTime_zone(String time_zone) {
        this.time_zone = time_zone;
    }
}
