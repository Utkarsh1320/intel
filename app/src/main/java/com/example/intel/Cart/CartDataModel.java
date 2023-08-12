package com.example.intel.Cart;

public class CartDataModel {
    private String id;
    private String name;
    private String price;

    private String image;
    private String quantity;

    public String getHeadline() {
        return headline;
    }

    public void setHeadline(String headline) {
        this.headline = headline;
    }

    private String headline ;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }



    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
    public CartDataModel(String id, String name , String price , String image ,String quantity){
        this.id = id;
        this.name= name;
        this.price = price;
        this.image = image ;
        this.quantity = quantity;

    }
    public CartDataModel(){

    }
}
