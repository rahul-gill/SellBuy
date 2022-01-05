package com.github.rahul_gill.sellbuy.models;

import org.json.JSONException;
import org.json.JSONObject;

public class ItemModel {
    public String date;
    public String time;
    public String name;
    public int pricePerItem;
    public String unit;
    public int quantity;
    public int totalPrice;
    public String typeOfTransaction;


    public ItemModel(String date, String time, String name, int pricePerItem, String unit, int quantity, int totalPrice, String typeOfTransaction) {
        this.date = date;
        this.time = time;
        this.name = name;
        this.pricePerItem = pricePerItem;
        this.unit = unit;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
        this.typeOfTransaction = typeOfTransaction;
    }

    public boolean isEqualTo(ItemModel other){
        return date.equals(other.date) &&
                time.equals(other.time) &&
                name.equals(other.name) &&
                pricePerItem == other.pricePerItem &&
                unit.equals(other.unit) &&
                quantity == other.quantity &&
                totalPrice == other.totalPrice &&
                typeOfTransaction.equals(other.typeOfTransaction);
    }
    public JSONObject toJsonObject() throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("date",date);
        jsonObject.put("time",time);
        jsonObject.put("name",name);
        jsonObject.put("pricePerItem",pricePerItem);
        jsonObject.put("unit",unit);
        jsonObject.put("quantity",quantity);
        jsonObject.put("totalPrice",totalPrice);
        jsonObject.put("typeOfTransaction",typeOfTransaction);
        return jsonObject;
    }
    public JSONObject toJsonObjectWithId(int id) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id",id);
        jsonObject.put("name",name);
        jsonObject.put("pricePerItem",pricePerItem);
        jsonObject.put("unit",unit);
        jsonObject.put("quantity",quantity);
        jsonObject.put("totalPrice",totalPrice);
        jsonObject.put("typeOfTransaction",typeOfTransaction);
        return jsonObject;
    }
}
