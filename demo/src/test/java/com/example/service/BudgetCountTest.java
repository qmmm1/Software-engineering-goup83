package com.example.service;

import com.example.model.Record;
import com.example.model.Setting;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

class BudgetCountTest {

    @Test
    void testCalculateBudget_WithValidData() {
        // 创建一个 Setting 对象
        Calendar startCalendar = Calendar.getInstance();
        startCalendar.set(2025, Calendar.APRIL, 10); // 2025年4月10日
        Date startDate = startCalendar.getTime();
        Setting setting = new Setting();
        setting.setStartDate(startDate);
        setting.setAmount(1000.0); // 预算金额为 1000.0
        setting.setduration(30); // 预算持续时间为 30 天

        // 创建一些 Record 对象
        List<Record> records = new ArrayList<>();
        Calendar recordCalendar = Calendar.getInstance();
        recordCalendar.set(2025, Calendar.APRIL, 15); // 2025年4月15日
        records.add(new Record("1", recordCalendar.getTime(), 200.0, "food", "Alice"));
        recordCalendar.set(2025, Calendar.APRIL, 20); // 2025年4月20日
        records.add(new Record("2", recordCalendar.getTime(), 300.0, "transportation", "Bob"));
        recordCalendar.set(2025, Calendar.MAY, 5); // 2025年5月5日
        records.add(new Record("3", recordCalendar.getTime(), 150.0, "entertainment", "Charlie"));

        // 创建 BudgetCount 对象并调用 calculateBudget 方法
        budgetCount budgetCount = new budgetCount();
        double result = budgetCount.calculateBudget(setting, records);

        // 验证结果
        assertEquals(65.0, result, "Total consumed should be 65.0%");
    }

    @Test
    void testCalculateBudget_WithNoRecords() {
        // 创建一个 Setting 对象
        Calendar startCalendar = Calendar.getInstance();
        startCalendar.set(2025, Calendar.APRIL, 10); // 2025年4月10日
        Date startDate = startCalendar.getTime();
        Setting setting = new Setting();
        setting.setStartDate(startDate);
        setting.setAmount(1000.0); // 预算金额为 1000.0
        setting.setduration(30); // 预算持续时间为 30 天

        // 创建一个空的 Record 列表
        List<Record> records = new ArrayList<>();

        // 创建 BudgetCount 对象并调用 calculateBudget 方法
        budgetCount budgetCount = new budgetCount();
        double result = budgetCount.calculateBudget(setting, records);

        // 验证结果
        assertEquals(0.0, result, "Total consumed should be 0.0%");
    }

    @Test
    void testCalculateBudget_WithRecordsOutsideRange() {
        // 创建一个 Setting 对象
        Calendar startCalendar = Calendar.getInstance();
        startCalendar.set(2025, Calendar.APRIL, 10); // 2025年4月10日
        Date startDate = startCalendar.getTime();
        Setting setting = new Setting();
        setting.setStartDate(startDate);
        setting.setAmount(1000.0); // 预算金额为 1000.0
        setting.setduration(30); // 预算持续时间为 30 天

        // 创建一些 Record 对象，这些记录的日期在设置的范围之外
        List<Record> records = new ArrayList<>();
        Calendar recordCalendar = Calendar.getInstance();
        recordCalendar.set(2025, Calendar.APRIL, 5); // 2025年4月5日
        records.add(new Record("1", recordCalendar.getTime(), 200.0, "food", "Alice"));
        recordCalendar.set(2025, Calendar.MAY, 15); // 2025年5月15日
        records.add(new Record("2", recordCalendar.getTime(), 300.0, "transportation", "Bob"));

        // 创建 BudgetCount 对象并调用 calculateBudget 方法
        budgetCount budgetCount = new budgetCount();
        double result = budgetCount.calculateBudget(setting, records);

        // 验证结果
        assertEquals(0.0, result, "Total consumed should be 0.0%");
    }
}