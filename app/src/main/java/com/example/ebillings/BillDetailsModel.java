package com.example.ebillings;

public class BillDetailsModel {
    String Product;
    String Quantity;
    String Total;
    String MRP;

    public BillDetailsModel() {
    }

    public BillDetailsModel(String product, String quantity, String total, String MRP) {
        Product = product;
        Quantity = quantity;
        Total = total;
        this.MRP = MRP;
    }

    public String getProduct() {
        return Product;
    }

    public void setProduct(String product) {
        Product = product;
    }

    public String getQuantity() {
        return Quantity;
    }

    public void setQuantity(String quantity) {
        Quantity = quantity;
    }

    public String getTotal() {
        return Total;
    }

    public void setTotal(String total) {
        Total = total;
    }

    public String getMRP() {
        return MRP;
    }

    public void setMRP(String MRP) {
        this.MRP = MRP;
    }
}
