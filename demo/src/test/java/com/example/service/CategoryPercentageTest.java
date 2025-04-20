package com.example.service;

import com.example.model.Record;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CategoryPercentageTest {

    private categoryPercentage service; // 被测试的服务类实例

    @BeforeEach
    public void setUp() {
        service = new categoryPercentage(); // 在每个测试方法之前初始化服务类实例
    }

    @Test
    public void testCalculatePercentage() {
        // 创建测试数据
        List<Record> records = new ArrayList<>();
        records.add(new Record("1", dateFromLocalDate(LocalDate.now().minusDays(1)), 100.0, "food", "Alice"));
        records.add(new Record("2", dateFromLocalDate(LocalDate.now().minusDays(1)), 200.0, "food", "Bob"));
        records.add(new Record("3", dateFromLocalDate(LocalDate.now().minusDays(1)), 50.0, "transportation", "Charlie"));
        records.add(new Record("4", dateFromLocalDate(LocalDate.now().minusDays(10)), 150.0, "food", "David")); // 超出时间范围

        // 调用方法并获取结果
        Map<String, Double> result = service.caculatePercentage(records, 5);

        // 验证结果
        assertEquals(2, result.size(), "应该有2个类别");
        assertEquals(66.66, result.get("food"), 0.01, "food 类别的占比应约为 66.66%"); // 2 out of 3 records
        assertEquals(33.33, result.get("transportation"), 0.01, "transportation 类别的占比应约为 33.33%"); // 1 out of 3 records
    }

    @Test
    public void testCalculatePercentageEmptyRecords() {
        // 测试空记录的情况
        List<Record> records = new ArrayList<>();
        Map<String, Double> result = service.caculatePercentage(records, 10);
        assertTrue(result.isEmpty(), "当记录为空时，应返回空的 Map");
    }

    @Test
    public void testGetCategoryCounts() {
        // 创建测试数据
        List<Record> records = new ArrayList<>();
        records.add(new Record("1", dateFromLocalDate(LocalDate.now().minusDays(1)), 100.0, "food", "Alice"));
        records.add(new Record("2", dateFromLocalDate(LocalDate.now().minusDays(1)), 200.0, "food", "Bob"));
        records.add(new Record("3", dateFromLocalDate(LocalDate.now().minusDays(1)), 50.0, "transportation", "Charlie"));
        records.add(new Record("4", dateFromLocalDate(LocalDate.now().minusDays(10)), 150.0, "food", "David")); // 超出时间范围

        // 调用方法并获取结果
        Map<String, Long> result = service.getCategoryCounts(records, 5);

        // 验证结果
        assertEquals(2, result.size(), "应该有2个类别");
        assertEquals(2L, result.get("food"), "food 类别的数量应为 2");
        assertEquals(1L, result.get("transportation"), "transportation 类别的数量应为 1");
    }

    @Test
    public void testGetCategoryCountsEmptyRecords() {
        // 测试空记录的情况
        List<Record> records = new ArrayList<>();
        Map<String, Long> result = service.getCategoryCounts(records, 10);
        assertTrue(result.isEmpty(), "当记录为空时，应返回空的 Map");
    }

    @Test
    public void testGetDailyAmountSum() {
        // 创建测试数据
        List<Record> records = new ArrayList<>();
        records.add(new Record("1", dateFromLocalDate(LocalDate.now()), 100.0, "food", "Alice"));
        records.add(new Record("2", dateFromLocalDate(LocalDate.now()), 200.0, "transportation", "Bob"));
        records.add(new Record("3", dateFromLocalDate(LocalDate.now().minusDays(1)), 50.0, "food", "Charlie")); // 不在当天

        // 调用方法并获取结果
        double result = service.getDailyAmountSum(records);
        assertEquals(300.0, result, 0.01, "当天的金额总和应为 300.0");
    }

    @Test
    public void testGetDailyAmountSumEmptyRecords() {
        // 测试空记录的情况
        List<Record> records = new ArrayList<>();
        double result = service.getDailyAmountSum(records);
        assertEquals(0.0, result, 0.01, "当记录为空时，金额总和应为 0.0");
    }

    // 辅助方法：将 LocalDate 转换为 Date
    private Date dateFromLocalDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
    }
}