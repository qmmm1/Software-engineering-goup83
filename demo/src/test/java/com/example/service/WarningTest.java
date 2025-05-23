package com.example.service;

import com.example.model.Record;
import com.example.model.Setting;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class WarningTest {

    private Setting setting;
    private List<Record> records;

    @BeforeEach
    public void setUp() {
        // 初始化 Setting 对象
        setting = new Setting();
        setting.setBudegt_ratewarning_low(30.0);
        setting.setBudegt_ratewarning_high(70.0);
        setting.setBudegt_ratewarning_max(100.0);
        setting.setLarge_amount_warning((int) 500.0);
        setting.setSequent_payment_warning(3);
        setting.setSame_amount_warning(5); // 5 minutes
        setting.setAmount(1000.0);

        // 初始化记录列表
        records = new ArrayList<>();
    }

    @Test
    public void testBudgetWarning() {
        // 测试预算警告
        assertEquals("max", warning.budgetWarning(setting, 101.0), "Should return 'max'");
        assertEquals("high", warning.budgetWarning(setting, 75.0), "Should return 'high'");
        assertEquals("low", warning.budgetWarning(setting, 40.0), "Should return 'low'");
        assertEquals("normal", warning.budgetWarning(setting, 10.0), "Should return 'normal'");
    }

    @Test
    public void testLargeAmountWarning() {
        // 测试大额警告
        records.add(new Record("1", dateFromLocalDateTime(LocalDateTime.now()), 600.0, "food", "Alice"));
        records.add(new Record("2", dateFromLocalDateTime(LocalDateTime.now()), 300.0, "transportation", "Bob"));
        records.add(new Record("3", dateFromLocalDateTime(LocalDateTime.now()), 400.0, "entertainment", "Charlie"));

        Map<String, Object> result = warning.large_amount_warning(setting, records);
        assertEquals("catch", result.get("code"), "Should catch large amount warning");
        List<Double> amountList = (List<Double>) result.get("amountList");
        assertTrue(amountList.contains(600.0), "Should contain 600.0 in amountList");
    }

    @Test
    public void testSequentAmountWarning() {
        // 测试连续支付警告
        List<Record> newRecords = new ArrayList<>();
        List<Record> oldRecords = new ArrayList<>();

        // 创建新记录
        newRecords.add(new Record("1", dateFromLocalDateTime(LocalDateTime.now()), 100.0, "food", "Alice"));
        newRecords.add(new Record("2", dateFromLocalDateTime(LocalDateTime.now().minusMinutes(2)), 200.0, "food", "Bob"));
        newRecords.add(new Record("3", dateFromLocalDateTime(LocalDateTime.now().minusMinutes(4)), 300.0, "food", "Charlie"));

        // 创建旧记录
        oldRecords.add(new Record("4", dateFromLocalDateTime(LocalDateTime.now().minusMinutes(6)), 400.0, "food", "David"));
        oldRecords.add(new Record("5", dateFromLocalDateTime(LocalDateTime.now().minusMinutes(8)), 500.0, "food", "Eve"));

        // 打印测试数据
        System.out.println("New Records:");
        newRecords.forEach(record -> System.out.println(record.getDetails()));
        System.out.println("Old Records:");
        oldRecords.forEach(record -> System.out.println(record.getDetails()));

        Map<String, Object> result = warning.sequent_amount_warning(setting, newRecords, oldRecords);
        assertEquals("catch", result.get("code"), "Should catch sequent payment warning");
        List<Record> recordList = (List<Record>) result.get("records");
        assertEquals(3, recordList.size(), "Should contain 3 records"); // 修正预期记录数为 3

        // 打印结果数据
        System.out.println("Result Records:");
        recordList.forEach(record -> System.out.println(record.getDetails()));
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
        assertEquals("catch", result.get("code"), "Should catch same amount warning");
        List<Record> recordList = (List<Record>) result.get("records");
        assertEquals(3, recordList.size(), "Should contain 3 records");
    }

    // 辅助方法：将 LocalDateTime 转换为 Date
    private Date dateFromLocalDateTime(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }
}