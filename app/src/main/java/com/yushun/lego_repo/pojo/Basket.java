package com.yushun.lego_repo.pojo;

public class Basket {
    private int _id;
    private String set_name;
    private String set_number;
    private String set_price;
    private String set_image;
    private String set_quantity;

    public Basket() {}

    public Basket(String set_name, String set_number, String set_price, String set_image, String quantity) {
        this.set_name = set_name;
        this.set_number = set_number;
        this.set_price = set_price;
        this.set_image = set_image;
        this.set_quantity = quantity;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getSet_name() {
        return set_name;
    }

    public void setSet_name(String set_name) {
        this.set_name = set_name;
    }

    public String getSet_number() {
        return set_number;
    }

    public void setSet_number(String set_number) {
        this.set_number = set_number;
    }

    public String getSet_price() {
        return set_price;
    }

    public void setSet_price(String set_price) {
        this.set_price = set_price;
    }

    public String getSet_image() {
        return set_image;
    }

    public void setSet_image(String set_image) {
        this.set_image = set_image;
    }

    public String getSet_quantity() {
        return set_quantity;
    }

    public void setSet_quantity(String set_quantity) {
        this.set_quantity = set_quantity;
    }
}
