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
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class WarningTest {
    private Setting setting;
    private List<Record> records;

    @BeforeEach
    public void setUp() {
        // 初始化 Setting 对象并手动赋值
        setting = new Setting();
        setting.setBudegt_ratewarning_low(0.5);
        setting.setBudegt_ratewarning_high(0.8);
        setting.setBudegt_ratewarning_max(1.0);
        setting.setLarge_amount_warning(10000);
        setting.setSequent_payment_warning(10);
        setting.setSame_amount_warning(5);
        setting.setAmount(5000);
        setting.setduration(30);

        // 设置开始日期
        Date startDate = Date.from(LocalDateTime.of(2021, 1, 1, 0, 0).atZone(ZoneId.systemDefault()).toInstant());
        setting.setStartDate(startDate);

        // 初始化记录列表
        records = new ArrayList<>();
    }

    @Test
    public void testBudgetWarning() {
        // 测试预算警告
        assertEquals("max", warning.budgetWarning(setting, 1.1), "Should return 'max'");
        assertEquals("high", warning.budgetWarning(setting, 0.85), "Should return 'high'");
        assertEquals("low", warning.budgetWarning(setting, 0.6), "Should return 'low'");
        assertEquals("normal", warning.budgetWarning(setting, 0.3), "Should return 'normal'");
    }

    @Test
    public void testLargeAmountWarning() {
        // 测试大额警告
        records.add(new Record("1", dateFromLocalDateTime(LocalDateTime.now()), 15000.0, "food", "Alice"));
        records.add(new Record("2", dateFromLocalDateTime(LocalDateTime.now().minusMinutes(2)), 8000.0, "food", "Bob"));
        records.add(new Record("3", dateFromLocalDateTime(LocalDateTime.now().minusMinutes(4)), 2000.0, "food", "Charlie"));
        records.add(new Record("4", dateFromLocalDateTime(LocalDateTime.now().minusMinutes(6)), 12000.0, "food", "David"));

        Map<String, Object> result = warning.large_amount_warning(setting, records);
        assertEquals("catch", result.get("code"), "Should catch large amount warning");
        List<Double> amountList = (List<Double>) result.get("amountList");
        assertTrue(amountList.contains(15000.0), "Should contain 15000.0 in amountList");
        assertTrue(amountList.contains(12000.0), "Should contain 12000.0 in amountList");
        assertFalse(amountList.contains(8000.0), "Should not contain 8000.0 in amountList");
        assertFalse(amountList.contains(2000.0), "Should not contain 2000.0 in amountList");
    }


    @Test
    public void testSequentAmountWarning() {
        // 测试连续支付警告
        List<Record> newRecords = new ArrayList<>();
        List<Record> oldRecords = new ArrayList<>();

        // 创建新记录
        newRecords.add(new Record("1", dateFromLocalDateTime(LocalDateTime.now()), 100.0, "food", "Alice"));
        newRecords.add(new Record("2", dateFromLocalDateTime(LocalDateTime.now().minusMinutes(2)), 100.0, "food", "Bob"));
        newRecords.add(new Record("3", dateFromLocalDateTime(LocalDateTime.now().minusMinutes(4)), 200.0, "food", "Charlie"));

        // 创建旧记录
        oldRecords.add(new Record("4", dateFromLocalDateTime(LocalDateTime.now().minusMinutes(6)), 100.0, "food", "David"));
        oldRecords.add(new Record("5", dateFromLocalDateTime(LocalDateTime.now().minusMinutes(8)), 300.0, "food", "Eve"));

        Map<String, Object> result = warning.sequent_amount_warning(setting, newRecords, oldRecords);
        assertEquals("normal", result.get("code"), "Should return 'normal' because the number of records does not meet the threshold");
    }

    @Test
    public void testSameAmountWarning() {
        // 测试相同金额警告
        List<Record> newRecords = new ArrayList<>();
        List<Record> oldRecords = new ArrayList<>();

        // 创建新记录
        newRecords.add(new Record("1", dateFromLocalDateTime(LocalDateTime.now()), 100.0, "food", "Alice"));
        newRecords.add(new Record("2", dateFromLocalDateTime(LocalDateTime.now().minusMinutes(2)), 100.0, "food", "Bob"));
        newRecords.add(new Record("3", dateFromLocalDateTime(LocalDateTime.now().minusMinutes(4)), 200.0, "food", "Charlie"));

        // 创建旧记录
        oldRecords.add(new Record("4", dateFromLocalDateTime(LocalDateTime.now().minusMinutes(6)), 100.0, "food", "David"));
        oldRecords.add(new Record("5", dateFromLocalDateTime(LocalDateTime.now().minusMinutes(8)), 300.0, "food", "Eve"));

        Map<String, Object> result = warning.same_amount_warning(setting, newRecords, oldRecords);
        assertEquals("normal", result.get("code"), "Should return 'normal' because payees are different");
    }

    // 辅助方法：将 LocalDateTime 转换为 Date
    private Date dateFromLocalDateTime(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }
}