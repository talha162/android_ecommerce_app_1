package com.example.project.models;

import java.sql.Time;
import java.util.Date;

public class ProductsModel {
    Date date;
    Time t;
    String price,productname,description,size,color;
    String pic1,pic2,pic3,productid;

public  ProductsModel(){}

    public ProductsModel(String pid,String productname, String price, String description, String size, String color, String pic1, String pic2, String pic3) {
        this.productid=pid;
        this.price = price;
        this.productname = productname;
        this.description = description;
        this.size = size;
        this.color = color;
        this.pic1 = pic1;
        this.pic2 = pic2;
        this.pic3 = pic3;
    }

    public ProductsModel(String price, String product_name, Date date, Time t) {

        this.price = price;
        this.productname = product_name;
        this.date = date;
        this.t = t;
    }
    public ProductsModel(String product_name,  String price,String description, String size,String color) {

        this.price = price;
        this.productname = product_name;
        this.description = description;
        this.size = size;
        this.color = color;
    }
    public ProductsModel(String product_name,  String price) {
        this.price = price;
        this.productname = product_name;
    }



    public String getProductid() {
        return productid;
    }

    public void setProductid(String productid) {
        this.productid = productid;
    }


    public String getProductname() {
        return productname;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Time getT() {
        return t;
    }

    public void setT(Time t) {
        this.t = t;
    }



    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPic1() {
        return pic1;
    }

    public void setPic1(String pic1) {
        this.pic1 = pic1;
    }

    public String getPic2() {
        return pic2;
    }

    public void setPic2(String pic2) {
        this.pic2 = pic2;
    }

    public String getPic3() {
        return pic3;
    }

    public void setPic3(String pic3) {
        this.pic3 = pic3;
    }


}
