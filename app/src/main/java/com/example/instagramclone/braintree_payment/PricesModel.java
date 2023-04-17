package com.example.instagramclone.braintree_payment;



public class PricesModel {

    private String month;
    private String price;

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getSaving() {
        return saving;
    }

    public void setSaving(String saving) {
        this.saving = saving;
    }

    private String value;
    private String saving;

    public PricesModel(String value, String month, String price, String saving) {
        this.month = month;
        this.price = price;
        this.value = value;
        this.saving = saving;
    }

}
