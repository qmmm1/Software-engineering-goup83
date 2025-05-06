package com.example.service;

import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.example.model.Record;

/**
 * @className categoryPercentage
 * @Dscription Calculate the proportion and total amount of each type within a specified time period
 */
public class categoryPercentage {
/**
 * @methodName calculatePercentage
 * @Description: Calculate the proportion of each type within a specified time period
 * @param records Global billing records
 * @param duration Time period (days)
 * @return A map of category and percentage
 */
    public static Map<String, Double> calculatePercentage(List<Record> records, int duration) {
        if (records.isEmpty()) {
            return Collections.emptyMap();
        }
        Calendar calendar = Calendar.getInstance();
        Date currentTime = calendar.getTime();
        calendar.add(Calendar.DAY_OF_MONTH, -duration);
        Date startTime = calendar.getTime();
    
        // Calculate the amount for each type using flow calculation
        Map<String, Double> categoryAmounts = records.stream()
                .filter(record -> !record.getPaymentDate().before(startTime) && !record.getPaymentDate().after(currentTime))
                .collect(Collectors.groupingBy(Record::getCategory, Collectors.summingDouble(Record::getAmount)));
    
        double totalAmount = categoryAmounts.values().stream().mapToDouble(Double::doubleValue).sum();
    
        // Calculate the proportion of each type of amount
        Map<String, Double> categoryPercentages = new HashMap<>();
        for (Map.Entry<String, Double> entry : categoryAmounts.entrySet()) {
            double percentage = (entry.getValue() * 100.0) / totalAmount;
            categoryPercentages.put(entry.getKey(), percentage);
        }
    
        return categoryPercentages;
    }
/**
 * @methodName getCategoryCounts
 * @Description: Calculate the total amount of each type within a specified time period
 * @param records Global billing records
 * @param duration Time period (days)
 * @return A map of category and total amount
 */
public static Map<String, Double> getCategoryCounts(List<Record> records, int duration) {
    if (records.isEmpty()) {
        return Collections.emptyMap();
    }
    Calendar calendar = Calendar.getInstance();
    Date currentTime = calendar.getTime();
    calendar.add(Calendar.DAY_OF_MONTH, -duration);
    Date startTime = calendar.getTime();

    return records.stream()
    .filter(record -> !record.getPaymentDate().before(startTime) && !record.getPaymentDate().after(currentTime))
    .collect(Collectors.groupingBy(Record::getCategory, Collectors.summingDouble(Record::getAmount)));
}
/**
 * @methodName getDailyAmountSum
 * @Description: Calculate the total amount of today's records
 * @param records Global billing records
 * @return A double value of total amount of today's records
 */
public static double getDailyAmountSum(List<Record> records) {
    if (records.isEmpty()) {
        return 0.0;
    }
    Calendar calendar = Calendar.getInstance();
    calendar.set(Calendar.HOUR_OF_DAY, 0);
    calendar.set(Calendar.MINUTE, 0);
    calendar.set(Calendar.SECOND, 0);
    calendar.set(Calendar.MILLISECOND, 0);
    Date startTime = calendar.getTime();

    calendar.add(Calendar.DAY_OF_MONTH, 1);
    Date endTime = calendar.getTime();

    return records.stream()
            .filter(record -> !record.getPaymentDate().before(startTime) && !record.getPaymentDate().after(endTime))
            .mapToDouble(Record::getAmount)
            .sum();
}
}