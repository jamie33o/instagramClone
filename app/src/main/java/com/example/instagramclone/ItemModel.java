package com.example.instagramclone;



public class ItemModel {
    private String image;
    private String name, age, county;

    public ItemModel() {
    }

    public ItemModel(String image, String name, String age, String county) {
        this.image = image;
        this.name = name;
        this.age = age;
        this.county = county;
    }

    public String getImage() {
        return image;
    }

    public String getName() {
        return name;
    }

    public String getAge() {
        return age;
    }

    public String getCounty() {
        return county;
    }
}
