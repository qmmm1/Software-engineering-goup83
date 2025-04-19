package com.example.service;

import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.example.model.Record;

public class categoryPercentage {
    // 计算指定时间段内每种类型的占比
    public static Map<String, Double> calculatePercentage(List<Record> records, int duration) {
        if (records.isEmpty()) {
            return Collections.emptyMap();
        }
        Calendar calendar = Calendar.getInstance();
        Date currentTime = calendar.getTime();
        calendar.add(Calendar.DAY_OF_MONTH, -duration);
        Date startTime = calendar.getTime();
    
        // 使用流计算每种类型的金额
        Map<String, Double> categoryAmounts = records.stream()
                .filter(record -> !record.getPaymentDate().before(startTime) && !record.getPaymentDate().after(currentTime))
                .collect(Collectors.groupingBy(Record::getCategory, Collectors.summingDouble(Record::getAmount)));
    
        double totalAmount = categoryAmounts.values().stream().mapToDouble(Double::doubleValue).sum();
    
        // 计算每种类型的金额占比
        Map<String, Double> categoryPercentages = new HashMap<>();
        for (Map.Entry<String, Double> entry : categoryAmounts.entrySet()) {
            double percentage = (entry.getValue() * 100.0) / totalAmount;
            categoryPercentages.put(entry.getKey(), percentage);
        }
    
        return categoryPercentages;
    }
//计算指定时间段内每种类型的数量
public static Map<String, Double> getCategoryCounts(List<Record> records, int duration) {
    if (records.isEmpty()) {
        return Collections.emptyMap();
    }
    Calendar calendar = Calendar.getInstance();
    Date currentTime = calendar.getTime();
    calendar.add(Calendar.DAY_OF_MONTH, -duration);
    Date startTime = calendar.getTime();

    // 使用流计算每种类型的数量
    return records.stream()
    .filter(record -> !record.getPaymentDate().before(startTime) && !record.getPaymentDate().after(currentTime))
    .collect(Collectors.groupingBy(Record::getCategory, Collectors.summingDouble(Record::getAmount)));
}
//当天的记录的金额总和
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

    // 使用流计算当天记录的金额总和
    return records.stream()
            .filter(record -> !record.getPaymentDate().before(startTime) && !record.getPaymentDate().after(endTime))
            .mapToDouble(Record::getAmount)
            .sum();
}
}