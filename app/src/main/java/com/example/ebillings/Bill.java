package com.example.ebillings;

public class Bill {
    private String Amount;
    private String Mode;
    private String Phone;
    private String Type;

    public Bill() {
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

    public Bill(String amount, String mode, String phone, String type) {
        Amount = amount;
        Mode = mode;
        Phone = phone;
        Type = type;
    }
}
