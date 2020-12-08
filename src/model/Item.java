/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author Ramesh Godara
 */
public class Item {
    private String item;
    private String uom;
    private Float quantity;
    private Float price;
    private Float amount;
    private String location;
    private long itemId;

    public Item() {
    }

    public Item(String item, String uom, Float quantity, Float price, Float amount, String location, long itemId) {
        this.item = item;
        this.uom = uom;
        this.quantity = quantity;
        this.price = price;
        this.amount = amount;
        this.location = location;
        this.itemId = itemId;
    }

    public String getItem() {
        return item;
    }

    public String getUom() {
        return uom;
    }

    public Float getQuantity() {
        return quantity;
    }

    public Float getPrice() {
        return price;
    }

    public Float getAmount() {
        return amount;
    }

    public String getLocation() {
        return location;
    }

    public long getItemId() {
        return itemId;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public void setUom(String uom) {
        this.uom = uom;
    }

    public void setQuantity(Float quantity) {
        this.quantity = quantity;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setItemId(long itemId) {
        this.itemId = itemId;
    }
}
