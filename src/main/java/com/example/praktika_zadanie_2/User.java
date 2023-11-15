package com.example.praktika_zadanie_2;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class User {

    private String defaultCurrency;
    private String name;
    private Integer id;

    public User(String defaultCurrency, String name) {
        this.defaultCurrency = defaultCurrency;
        this.name = name;
    }
    @JsonCreator
    public User (@JsonProperty("id") Integer id,@JsonProperty("defaultCurrency") String defaultCurrency,@JsonProperty("name") String name){
        this.id = id;
        this.defaultCurrency = defaultCurrency;
        this.name = name;
    }
    public Integer getId(){
        return id;
    }
    public void setId(Integer id){
        this.id = id;
    }
    public void setDefaultCurrency(String defaultCurrency) {
        this.defaultCurrency = defaultCurrency;
    }
    public String getDefaultCurrency() {
        return defaultCurrency;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}