package com.example.remitlyhomeexercise.model;

import jakarta.persistence.*;

@Entity
@Table(name = "code types")
public class CodeType {

    @Id
    @GeneratedValue
    private long id;

    @Column(length = 64)
    private String codeTypeName;

    public CodeType(){}

    public CodeType(String codeTypeName){
        this.codeTypeName = codeTypeName;
    }

    public String getCodeTypeName() {
        return codeTypeName;
    }

    public void setCodeTypeName(String codeTypeName) {
        this.codeTypeName = codeTypeName;
    }
}
