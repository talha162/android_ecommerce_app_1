package com.example.project.models;

public final class CartsModel {
    String price, product_name, qty, productid,pic;



    public CartsModel() {
        }

    public CartsModel( String productid,String pic, String qty, String price, String product_name){
        this.productid=productid;
        this.qty = qty;
            this.pic = pic;
            this.price = price;
            this.product_name = product_name;
        }

        public String getQty () {
            return qty;
        }

        public void setQty (String qty){
            this.qty = qty;
        }

    public String getProductid() {
        return productid;
    }

    public void setProductid(String productid) {
        this.productid = productid;
    }
        public String getPic () {
            return pic;
        }

        public void setPic ( String pic){
            this.pic = pic;
        }

        public String getPrice () {

            return price;
        }

        public void setPrice (String price){
            this.price = price;
        }

        public String getProduct_name () {
            return product_name;
        }

        public void setProduct_name (String product_name){
            this.product_name = product_name;
        }
    }
