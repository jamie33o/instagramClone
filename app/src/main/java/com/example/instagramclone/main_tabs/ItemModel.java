package com.example.instagramclone.main_tabs;


import com.parse.ParseUser;

public class ItemModel {
    private String image;
    private String name;
    private String age;
    private String county;

    public ItemModel(String image, String name, String age, String county, ParseUser userClassPointer) {
        this.image = image;
        this.name = name;
        this.age = age;
        this.county = county;
        this.userClassPointer=userClassPointer;
    }

    public ParseUser getUserClassPointer() {
        return userClassPointer;
    }

    public void setUserClassPointer(ParseUser userClassPointer) {
        this.userClassPointer = userClassPointer;
    }

    private ParseUser userClassPointer;

    public ItemModel() {
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
