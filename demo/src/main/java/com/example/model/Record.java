package com.example.model;

import java.util.Date;
import java.util.Calendar;

public class Record {
    private String paymentId;
    private Date paymentDate;
    private double amount;
    private String category;
    private String payee;

    // 构造方法
    public Record(String paymentId, Date paymentDate, double amount, String category,String payee) {
        this.paymentId = paymentId;
        this.paymentDate = paymentDate;
        this.amount = amount;
        this.category = category;
        this.payee = payee;
    }

    // 获取支付详情
    public String getDetails() {
        return "Payment ID: " + paymentId + ", Date: " + paymentDate + ", Amount: " + amount + ", Type: " + category + ", Account ID: " + payee;
    }

    // 更新支付信息
    public void updatePayment(Date newPaymentDate,double newAmount, String newcategory, String payee) {
        this.paymentDate = newPaymentDate;
        this.amount = newAmount;
        this.category = newcategory;
        this.payee = payee;
    }

    // 验证支付信息
    public boolean validatePayment() {
       if (amount <= 0 )
            return false;
        else if(category.equals("food") || category.equals("transportation") || category.equals("entertainment")|| category.equals("education")|| category.equals("living expenses")|| category.equals("other"))
            return false;
        else if(paymentDate.after(Calendar.getInstance().getTime()))
            return false;
        else if(payee.equals(""))
            return false;
        else
            return true;
    }
       // 这里可以添加各种验证逻辑，例如支付金额是否大于0，支付类型和方式是否在预设范围内等
    // Getters and Setters
    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPayee() {
        return payee;
    }

    public void setPayee(String payee) {
        this.payee = payee;
    }
}
