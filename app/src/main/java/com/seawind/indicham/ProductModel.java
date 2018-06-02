package com.seawind.indicham;

/**
 * Created by admin on 28-May-18.
 */

public class ProductModel {
    public int getProd_image() {
        return prod_image;
    }

    public void setProd_image(int prod_image) {
        this.prod_image = prod_image;
    }

    public String getProd_name() {
        return prod_name;
    }

    public void setProd_name(String prod_name) {
        this.prod_name = prod_name;
    }

    private int prod_image;
    private String prod_name;
}
