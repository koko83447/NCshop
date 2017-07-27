package com.NCnendoroid.model;

public class CartItem {
    private Dress product;
    private int quantity;

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }


    //取得購物車中的產品
    public Dress getProduct() {
        return product;
    }

    public void setProduct(Dress product) {
        this.product = product;
    }

}
