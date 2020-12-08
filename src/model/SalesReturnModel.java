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
public class SalesReturnModel {
    
    private long orderId;
    private String invoiceDate;
    private String partyName;
    private float totalQuantity;
    private float totalAmount;
    private float otherAmount;
    private float totalPaybleAmount;
    private float totalPaidAmount;
    private float totalDueAmount;

    public SalesReturnModel() {}

    public SalesReturnModel(long orderId, String invoiceDate, String partyName, float totalQuantity, float totalAmount, float otherAmount, float totalPaybleAmount, float totalPaidAmount, float totalDueAmount) {
        this.orderId = orderId;
        this.invoiceDate = invoiceDate;
        this.partyName = partyName;
        this.totalQuantity = totalQuantity;
        this.totalAmount = totalAmount;
        this.otherAmount = otherAmount;
        this.totalPaybleAmount = totalPaybleAmount;
        this.totalPaidAmount = totalPaidAmount;
        this.totalDueAmount = totalDueAmount;
    }

    public long getOrderId() {
        return orderId;
    }

    public String getInvoiceDate() {
        return invoiceDate;
    }

    public String getPartyName() {
        return partyName;
    }

    public float getTotalQuantity() {
        return totalQuantity;
    }

    public float getTotalAmount() {
        return totalAmount;
    }

    public float getOtherAmount() {
        return otherAmount;
    }

    public float getTotalPaybleAmount() {
        return totalPaybleAmount;
    }

    public float getTotalPaidAmount() {
        return totalPaidAmount;
    }

    public float getTotalDueAmount() {
        return totalDueAmount;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public void setInvoiceDate(String invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public void setPartyName(String partyName) {
        this.partyName = partyName;
    }

    public void setTotalQuantity(float totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public void setTotalAmount(float totalAmount) {
        this.totalAmount = totalAmount;
    }

    public void setOtherAmount(float otherAmount) {
        this.otherAmount = otherAmount;
    }

    public void setTotalPaybleAmount(float totalPaybleAmount) {
        this.totalPaybleAmount = totalPaybleAmount;
    }

    public void setTotalPaidAmount(float totalPaidAmount) {
        this.totalPaidAmount = totalPaidAmount;
    }

    public void setTotalDueAmount(float totalDueAmount) {
        this.totalDueAmount = totalDueAmount;
    }
}
