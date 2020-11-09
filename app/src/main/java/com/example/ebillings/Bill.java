package com.example.ebillings;

import com.google.gson.JsonArray;

import org.json.JSONArray;

public class Bill {
    private String Amount;
    private String Mode;
    private String Phone;
    private String id;
    private String Type;
    private String bill;

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
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getBill() {
        return bill;
    }

    public void setBill(String bill) {
        this.bill = bill;
    }

    public Bill(String amount, String mode, String phone, String id, String type, String bill) {
        Amount = amount;
        Mode = mode;
        Phone = phone;
        this.id = id;
        Type = type;
        this.bill = bill;
    }
}
