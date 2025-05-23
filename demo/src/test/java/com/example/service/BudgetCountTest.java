package com.example.service;

import com.example.model.Record;
import com.example.model.Setting;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BudgetCountTest {

    private Setting setting;
    private List<Record> records;

    @BeforeEach
    public void setUp() {
        // 初始化测试数据
        setting = new Setting();
        setting.setStartDate(dateFromLocalDate(LocalDate.now().minusDays(10))); // 设置开始日期为10天前
        setting.setduration(5); // 设置持续时间为5天
        setting.setAmount(1000.0); // 设置总预算为1000.0

        records = new ArrayList<>();
    }

    @Test
    public void testCalculateBudgetWithRecordsWithinRange() {
        // 添加在时间范围内的记录
        records.add(new Record("1", dateFromLocalDate(LocalDate.now().minusDays(5)), 200.0, "food", "Alice"));
        records.add(new Record("2", dateFromLocalDate(LocalDate.now().minusDays(4)), 300.0, "transportation", "Bob"));
        records.add(new Record("3", dateFromLocalDate(LocalDate.now().minusDays(3)), 100.0, "entertainment", "Charlie"));

        // 调用方法并获取结果
        double result = budgetCount.calculateBudget(setting, records);

        // 验证结果
        assertEquals(60.0, result, 0.01, "预算消耗百分比应为60.0%");
    }

    @Test
    public void testCalculateBudgetWithRecordsOutsideRange() {
        // 添加超出时间范围的记录
        records.add(new Record("1", dateFromLocalDate(LocalDate.now().minusDays(15)), 200.0, "food", "Alice")); // 超出时间范围
        records.add(new Record("2", dateFromLocalDate(LocalDate.now()), 300.0, "transportation", "Bob")); // 超出时间范围

        // 调用方法并获取结果
        double result = budgetCount.calculateBudget(setting, records);

        // 验证结果
        assertEquals(0.0, result, 0.01, "预算消耗百分比应为0.0%");
    }

    @Test
    public void testCalculateBudgetWithEmptyRecords() {
        // 测试空记录的情况
        records = new ArrayList<>();

        // 调用方法并获取结果
        double result = budgetCount.calculateBudget(setting, records);

        // 验证结果
        assertEquals(0.0, result, 0.01, "预算消耗百分比应为0.0%");
    }

    @Test
    public void testCalculateBudgetWithPartialRecords() {
        // 添加部分在时间范围内，部分超出时间范围的记录
        records.add(new Record("1", dateFromLocalDate(LocalDate.now().minusDays(5)), 200.0, "food", "Alice")); // 在范围内
        records.add(new Record("2", dateFromLocalDate(LocalDate.now().minusDays(15)), 300.0, "transportation", "Bob")); // 超出范围
        records.add(new Record("3", dateFromLocalDate(LocalDate.now().minusDays(3)), 100.0, "entertainment", "Charlie")); // 在范围内

        // 调用方法并获取结果
        double result = budgetCount.calculateBudget(setting, records);

        // 验证结果
        assertEquals(30.0, result, 0.01, "预算消耗百分比应为30.0%");
    }

    @Test
    public void testCalculateBudgetWithExactBoundaryRecords() {
        // 添加在边界上的记录
        records.add(new Record("1", dateFromLocalDate(LocalDate.now().minusDays(10)), 200.0, "food", "Alice")); // 开始日期
        records.add(new Record("2", dateFromLocalDate(LocalDate.now().minusDays(5)), 300.0, "transportation", "Bob")); // 结束日期

        // 调用方法并获取结果
        double result = budgetCount.calculateBudget(setting, records);

        // 验证结果
        assertEquals(50.0, result, 0.01, "预算消耗百分比应为50.0%");
    }

    // 辅助方法：将 LocalDate 转换为 Date
    private Date dateFromLocalDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
    }
}