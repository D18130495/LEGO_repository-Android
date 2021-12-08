package com.yushun.lego_repo.pojo;

public class Order {
    private int _id;
    private String order_number;
    private String order_date;
    private String set_name;
    private String set_number;
    private String set_price;
    private String set_quantity;

    public Order() {}

    public Order(String order_number, String order_date, String set_name, String set_number, String set_price, String set_quantity) {
        this.order_number = order_number;
        this.order_date = order_date;
        this.set_name = set_name;
        this.set_number = set_number;
        this.set_price = set_price;
        this.set_quantity = set_quantity;
    }

    public String getOrder_number() {
        return order_number;
    }

    public void setOrder_number(String order_number) {
        this.order_number = order_number;
    }

    public String getOrder_date() {
        return order_date;
    }

    public void setOrder_date(String order_date) {
        this.order_date = order_date;
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

    public String getSet_quantity() {
        return set_quantity;
    }

    public void setSet_quantity(String set_quantity) {
        this.set_quantity = set_quantity;
    }
}
