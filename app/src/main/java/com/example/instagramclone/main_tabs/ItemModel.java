package com.example.instagramclone.main_tabs;


import com.parse.ParseUser;

public class ItemModel {


    private String image1;

    public void setImage1(String image1) {
        this.image1 = image1;
    }

    public void setImage2(String image2) {
        this.image2 = image2;
    }

    public void setImage3(String image3) {
        this.image3 = image3;
    }

    private String image2;
    private String image3;

    private String name;
    private String age;
    private String county;
    private boolean isPayed,showLocation,showAge;


    public ItemModel( String name, String age, String county, ParseUser userClassPointer, boolean isPayed, boolean showLocation, boolean showAge) {

        this.name = name;
        this.age = age;
        this.county = county;
        this.userClassPointer=userClassPointer;
        this.isPayed = isPayed;
        this.showAge = showAge;
        this.showLocation = showLocation;
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



    public String getImage1() {
        return image1;
    }

    public String getImage2() {
        return image2;
    }

    public String getImage3() {
        return image3;
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

    public boolean getIsPayed() {
        return isPayed;
    }

    public boolean getShowLocation() {
        return showLocation;
    }

    public boolean getShowAge() {
        return showAge;
    }

}
