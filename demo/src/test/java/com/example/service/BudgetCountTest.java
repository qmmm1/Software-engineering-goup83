package com.example.service;

import com.example.model.Record;
import com.example.model.Setting;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BudgetCountTest {
    private Setting setting;
    private List<Record> records;

    @BeforeEach
    public void setUp() {
        // 初始化 Setting 对象并手动赋值
        setting = new Setting();
        setting.setAmount(5000.0);
        setting.setduration(30);

        // 设置开始日期
        Date startDate = Date.from(LocalDateTime.of(2021, 1, 1, 0, 0).atZone(ZoneId.systemDefault()).toInstant());
        setting.setStartDate(startDate);

        // 初始化记录列表
        records = new ArrayList<>();
    }

    @Test
    public void testCalculateBudget() {
        // 测试预算消耗百分比计算
        records.add(new Record("1", dateFromLocalDateTime(LocalDateTime.of(2021, 1, 5, 0, 0)), 1000.0, "food", "Alice"));
        records.add(new Record("2", dateFromLocalDateTime(LocalDateTime.of(2021, 1, 10, 0, 0)), 2000.0, "transportation", "Bob"));
        records.add(new Record("3", dateFromLocalDateTime(LocalDateTime.of(2021, 1, 15, 0, 0)), 1500.0, "entertainment", "Charlie"));

        double result = budgetCount.calculateBudget(setting, records);
        assertEquals(90.0, result, "Should return 90.0% budget consumption");
    }

    @Test
    public void testCalculateBudgetWithNoRecords() {
        // 测试没有记录时的预算消耗百分比计算
        double result = budgetCount.calculateBudget(setting, records);
        assertEquals(0.0, result, "Should return 0.0% budget consumption when there are no records");
    }

    @Test
    public void testCalculateBudgetWithRecordsOutsidePeriod() {
        // 测试记录在预算周期外的预算消耗百分比计算
        records.add(new Record("1", dateFromLocalDateTime(LocalDateTime.of(2020, 12, 30, 0, 0)), 1000.0, "food", "Alice"));
        records.add(new Record("2", dateFromLocalDateTime(LocalDateTime.of(2021, 2, 1, 0, 0)), 2000.0, "transportation", "Bob"));

        double result = budgetCount.calculateBudget(setting, records);
        assertEquals(0.0, result, "Should return 0.0% budget consumption when records are outside the budget period");
    }

    @Test
    public void testCalculateBudgetWithExactPeriod() {
        // 测试记录在预算周期内的预算消耗百分比计算
        records.add(new Record("1", dateFromLocalDateTime(LocalDateTime.of(2021, 1, 1, 0, 0)), 5000.0, "food", "Alice"));

        double result = budgetCount.calculateBudget(setting, records);
        assertEquals(100.0, result, "Should return 100.0% budget consumption when records exactly match the budget period");
    }

    // 辅助方法：将 LocalDateTime 转换为 Date
    private Date dateFromLocalDateTime(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }
}