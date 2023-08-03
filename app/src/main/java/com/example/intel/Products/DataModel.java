package com.example.intel.Products;

public class DataModel {
    private String headline , price , brand , image  ;
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



//    public String getId() {
//        return id;
//    }
//
//    public void setId(String id) {
//        this.id = id;
//    }
//
//    public String getAmount() {
//        return amount;
//    }
//
//    public void setAmount(String amount) {
//        this.amount = amount;
//    }

    public DataModel(String headline , String price , String brand , String image, String id, String amount){
        this.brand =brand;
        this.headline =headline;
        this.price =price;
        this.brand =image;
//        this.id =id;
//        this.amount =amount;

    }

}
