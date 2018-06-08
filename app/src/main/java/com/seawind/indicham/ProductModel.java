package com.seawind.indicham;

import java.io.Serializable;

/**
 * Created by admin on 28-May-18.
 */

public class ProductModel implements Serializable{

    private int qty=1;
    private int price=299;
    private int val = 299;
    private String P_desc;

    public String getProd_image() {
        return prod_image;
    }

    public void setProd_image(String prod_image) {
        this.prod_image = prod_image;
    }

    public String getProd_name() {
        return prod_name;
    }

    public void setProd_name(String prod_name) {
        this.prod_name = prod_name;
    }

    private String prod_image;
    private String prod_name;

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public int getPrice() {
        return price;
    }

    public int getVal() {
        return val;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setVal(int val) {
        this.val = val;
    }

    public String getP_desc() {
        return P_desc;
    }

    public void setP_desc(String p_desc) {
        P_desc = p_desc;
    }
}
