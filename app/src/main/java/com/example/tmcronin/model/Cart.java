package com.example.tmcronin.model;

public class Cart
{
    //all database info needed from database to display info on cart https://www.youtube.com/watch?v=giHNsmE8-ug&list=PLxefhmF0pcPlqmH_VfWneUjfuqhreUz-O&index=24
    private String pid, pname, price, quantity, discount;

    public Cart() {

    }

    public Cart(String pid, String pname, String price, String quantity, String discount) {
        this.pid = pid;
        this.pname = pname;
        this.price = price;
        this.quantity = quantity;
        this.discount = discount;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }
}
