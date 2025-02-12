package com.example.remitlyhomeexercise.model;

import jakarta.persistence.*;

@Entity
@Table(name = "banks")
public class Bank {

    @Id
    @GeneratedValue
    private long id;

    @ManyToOne(optional = false)
    private Country country;

    @Column(length = 64)
    private String swift;

    @ManyToOne()
    private CodeType codeType;

    @Column(length = 256)
    private String name;

    @Column(length = 256)
    private String address = "ND";

    @Column(length = 128)
    private String townName = "ND";

    private boolean isHeadquarters;

    public Bank(){}
    public Bank(Country country, String swift, CodeType codeType, String name, String address, String townName, boolean isHeadquarters) {
        this.country = country;
        this.swift = swift;
        this.codeType = codeType;
        this.name = name;
        this.address = address;
        this.townName = townName;
        this.isHeadquarters = isHeadquarters;
    }

    public Country getCountry() {
        return country;
    }

    public String getSwift() {
        return swift;
    }

    public CodeType getCodeType() {
        return codeType;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getTownName() {
        return townName;
    }

    public boolean isHeadquarters() {
        return isHeadquarters;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public void setSwift(String swift) {
        this.swift = swift;
    }

    public void setCodeType(CodeType codeType) {
        this.codeType = codeType;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setTownName(String townName) {
        this.townName = townName;
    }

    public void setHeadquarters(boolean headquarters) {
        isHeadquarters = headquarters;
    }
}
