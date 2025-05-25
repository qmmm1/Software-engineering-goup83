package com.example.service;

import java.util.Calendar;
import java.util.List;

import java.util.Date;
import com.example.model.Setting;
import com.example.model.Record;

/**
 * Budget consumption percentage calculation category
 */
    public class budgetCount {
/**
 * calculateBudget.
 * Calculate the percentage of budget consumption
 * @param setting User Settings
 * @param records Global billing records
 * @return Budget consumption percentage
 */
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
