package com.example.ebillings.Model;

import com.google.gson.JsonArray;

import org.json.JSONArray;

public class Bill {
    private String Amount;
    private String Mode;
    private String Phone;
    private String id;
    private String type;
    private String bill;
    private String Purchase_Date;

    public Bill(String amount, String mode, String phone, String id, String Type, String bill, String purchase_Date) {
        Amount = amount;
        Mode = mode;
        Phone = phone;
        this.id = id;
        type = Type;
        this.bill = bill;
        Purchase_Date = purchase_Date;
    }

    public String getPurchase_Date() {
        return Purchase_Date;
    }

    public void setPurchase_Date(String purchase_Date) {
        Purchase_Date = purchase_Date;
    }

    public Bill() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAmount() {
        return Amount;
    }

    public void setAmount(String amount) {
        Amount = amount;
    }

    public String getMode() {
        return Mode;
    }

    public void setMode(String mode) {
        Mode = mode;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getType() {
        return type;
    }

    public void setType(String Type) {
        type = Type;
    }

    public String getBill() {
        return bill;
    }

    public void setBill(String bill) {
        this.bill = bill;
    }

}
