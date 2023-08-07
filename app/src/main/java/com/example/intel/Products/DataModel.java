package com.example.intel.Products;

public class DataModel {

    private String headline;
    private String price;
    private String brand;
    private String image;
    private String id;
    private String amount;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    private String description ;
    public DataModel(){

    }

    public String getHeadline() {
        return headline;
    }

    public void setHeadline(String headline) {
        this.headline = headline;
    }



    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }


    public DataModel(String headline , String price , String brand , String image, String id, String amount){
        this.brand = brand;
        this.headline = headline;
        this.price = price;
        this.brand = image;
        this.id = id;
        this.amount = amount;

    }

}
