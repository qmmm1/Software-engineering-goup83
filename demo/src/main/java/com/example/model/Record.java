package com.example.model;

import java.util.Date;
import java.util.Calendar;


/**
 * This class represents a record of a payment made by a user.
 * It contains the payment ID, payment date, amount, category, and payee.
 * It also provides methods to get and update the payment details, and to validate the payment information.
 */
public class Record {
    private String paymentId;
    private Date paymentDate;
    private double amount;
    private String category;
    private String payee;

    // constructor
    public Record(String paymentId, Date paymentDate, double amount, String category,String payee) {
        this.paymentId = paymentId;
        this.paymentDate = paymentDate;
        this.amount = amount;
        this.category = category;
        this.payee = payee;
    }

    // get payment details
    public String getDetails() {
        return "Payment ID: " + paymentId + ", Date: " + paymentDate + ", Amount: " + amount + ", Type: " + category + ", Account ID: " + payee;
    }

    // update payment details
    public void updatePayment(Date newPaymentDate,double newAmount, String newcategory, String payee) {
        this.paymentDate = newPaymentDate;
        this.amount = newAmount;
        this.category = newcategory;
        this.payee = payee;
    }
    // validate payment details
    public boolean validatePayment() {
       if (amount <= 0 )
            return false;
        else if(!category.equals("food") &&!category.equals("transportation") &&!category.equals("entertainment")&&! category.equals("education")&&!  category.equals("living expenses")&&! category.equals("other"))
            return false;
        else if(paymentDate == null||paymentDate.after(Calendar.getInstance().getTime()))
            return false;
        else if(payee.equals(""))
            return false;
        else
            return true;
    }
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
