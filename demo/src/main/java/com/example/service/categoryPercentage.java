package com.example.service;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.example.model.Record;

public class categoryPercentage {
    public Map<String, Double> caculatePercentage(List<Record> records) {
   if (records.isEmpty()) {
            return Collections.emptyMap();
        }

        // 获取所有支付类型的总数
        long totalRecords = records.size();

        // 使用流计算每种类型的数量
        Map<String, Long> categoryCounts = records.stream()
                .collect(Collectors.groupingBy(Record::getCategory, Collectors.counting()));

        // 计算每种类型的占比
        Map<String, Double> categoryPercentages = new HashMap<>();
        for (Map.Entry<String, Long> entry : categoryCounts.entrySet()) {
            double percentage = (entry.getValue() * 100.0) / totalRecords;
            categoryPercentages.put(entry.getKey(), percentage);
        }

        return categoryPercentages;
}
}