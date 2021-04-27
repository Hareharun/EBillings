package com.example.ebillings;

public class BillDetailsModel {
    String ProductCode;
    String ProductName;
    String Quantity;
    String Total;
    String MRP;

    public BillDetailsModel() {
    }

    public BillDetailsModel(String productCode, String productName, String quantity, String total, String MRP) {
        ProductCode = productCode;
        ProductName = productName;
        Quantity = quantity;
        Total = total;
        this.MRP = MRP;
    }

    public String getProductCode() {
        return ProductCode;
    }

    public void setProductCode(String productCode) {
        ProductCode = productCode;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
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
