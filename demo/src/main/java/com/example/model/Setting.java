package com.example.model;

import java.util.Date;

public class Setting {
    private double budegt_ratewarning_low;
    private double budegt_ratewarning_high;
    private double budegt_ratewarning_max;
    private int large_amount_warning;
    private int sequent_payment_warning;
    private int same_amount_warning;
    private double amount;
    private int duration;
    private Date startDate;


    public double getBudegt_ratewarning_low() {
        return budegt_ratewarning_low;
    }

    public void setBudegt_ratewarning_low(double budegt_ratewarning_low) {
        this.budegt_ratewarning_low = budegt_ratewarning_low;
    }

    public double getBudegt_ratewarning_high() {
        return budegt_ratewarning_high;
    }

    public void setBudegt_ratewarning_high(double budegt_ratewarning_high) {
        this.budegt_ratewarning_high = budegt_ratewarning_high;
    }

    public double getBudegt_ratewarning_max() {
        return budegt_ratewarning_max;
    }

    public void setBudegt_ratewarning_max(double budegt_ratewarning_max) {
        this.budegt_ratewarning_max = budegt_ratewarning_max;
    }

    public int getLarge_amount_warning() {
        return large_amount_warning;
    }

    public void setLarge_amount_warning(int large_amount_warning) {
        this.large_amount_warning = large_amount_warning;
    }

    public int getSequent_payment_warning() {
        return sequent_payment_warning;
    }

    public void setSequent_payment_warning(int sequent_payment_warning) {
        this.sequent_payment_warning = sequent_payment_warning;
    }

    public int getSame_amount_warning() {
        return same_amount_warning;
    }

    public void setSame_amount_warning(int same_amount_warning) {
        this.same_amount_warning = same_amount_warning;
}
public double getAmount() {
    return amount;
}

// 设置金额的方法
public void setAmount(double amount) {
    if (amount >= 0) {
        this.amount = amount;
    } else {
        System.out.println("amount must be greater than or equal to zero");
    }
}

// 获取持续时间的方法
public int getDuration() {
    return duration;
}

// 设置持续时间的方法
public void setduration(int duration) {
    if (duration >= 0) {
        this.duration = duration;
    } else {
        System.out.println("duration must be greater than or equal to zero");
    }
}
public void setduration_week() {
        this.duration = 7;

}
public void setduration_month() {
        this.duration = 30;    
}
public Date getStartDate() {
return startDate;
}

public void setStartDate(Date startDate) {
this.startDate = startDate;
}
}