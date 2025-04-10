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
    public Map<String, Double> caculatePercentage(List<Record> records,int duration) {
   if (records.isEmpty()) {
            return Collections.emptyMap();
        }
        Calendar calendar = Calendar.getInstance();
      Date currentTime = calendar.getTime();
      calendar.add(Calendar.DAY_OF_MONTH, -duration);
      Date startTime = calendar.getTime();

        // 使用流计算每种类型的数量
        Map<String, Long> categoryCounts = records.stream()
        .filter(record -> !record.getPaymentDate().before(startTime) && !record.getPaymentDate().after(currentTime))
        .collect(Collectors.groupingBy(Record::getCategory, Collectors.counting()));

        long totalRecords = categoryCounts.values().stream().mapToLong(Long::longValue).sum();

        // 计算每种类型的占比
        Map<String, Double> categoryPercentages = new HashMap<>();
        for (Map.Entry<String, Long> entry : categoryCounts.entrySet()) {
            double percentage = (entry.getValue() * 100.0) / totalRecords;
            categoryPercentages.put(entry.getKey(), percentage);
        }

        return categoryPercentages;
}
public Map<String, Long> getCategoryCounts(List<Record> records, int duration) {
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
            .collect(Collectors.groupingBy(Record::getCategory, Collectors.counting()));
}
public double getDailyAmountSum(List<Record> records) {
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