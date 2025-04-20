package com.example.service;

import java.util.Calendar;
import java.util.List;

import java.util.Date;
import com.example.model.Setting;
import com.example.model.Record;


public class budgetCount {
    //返回预算消耗百分比
    public static double calculateBudget(Setting setting, List<Record> records) {
           double totalConsumed = 0.0;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(setting.getStartDate());
        calendar.add(Calendar.DAY_OF_MONTH, setting.getDuration());
        Date endDate = calendar.getTime();

        for (Record record : records) {
            if (!record.getPaymentDate().before(setting.getStartDate()) && !record.getPaymentDate().after(endDate)) {
                totalConsumed += record.getAmount();
            }
        }

        return (totalConsumed / setting.getAmount()) * 100;
    }
    
}
