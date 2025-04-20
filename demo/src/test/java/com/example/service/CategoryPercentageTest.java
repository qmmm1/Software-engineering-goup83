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

    private List<Record> records;

    @BeforeEach
    public void setUp() {
        records = new ArrayList<>();
        records.add(new Record("1", dateFromLocalDate(LocalDate.now().minusDays(1)), 100.0, "food", "Alice"));
        records.add(new Record("2", dateFromLocalDate(LocalDate.now().minusDays(1)), 200.0, "food", "Bob"));
        records.add(new Record("3", dateFromLocalDate(LocalDate.now().minusDays(1)), 50.0, "transportation", "Charlie"));
        records.add(new Record("4", dateFromLocalDate(LocalDate.now().minusDays(10)), 150.0, "food", "David")); // 超出时间范围
        records.add(new Record("5", dateFromLocalDate(LocalDate.now()), 300.0, "entertainment", "Eve")); // 当天记录
    }

    @Test
    public void testCalculatePercentage() {
        Map<String, Double> result = categoryPercentage.calculatePercentage(records, 5);

        assertEquals(3, result.size(), "应该有3个类别");
        assertEquals(46.15, result.get("food"), 0.01, "food 类别的占比应约为 46.15%"); // 修正预期值
        assertEquals(7.69, result.get("transportation"), 0.01, "transportation 类别的占比应约为 7.69%"); // 50 / 650
        assertEquals(46.15, result.get("entertainment"), 0.01, "entertainment 类别的占比应约为 46.15%"); // 300 / 650
    }

    @Test
    public void testCalculatePercentageEmptyRecords() {
        List<Record> emptyRecords = new ArrayList<>();
        Map<String, Double> result = categoryPercentage.calculatePercentage(emptyRecords, 10);
        assertTrue(result.isEmpty(), "当记录为空时，应返回空的 Map");
    }

    @Test
    public void testGetCategoryCounts() {
        Map<String, Double> result = categoryPercentage.getCategoryCounts(records, 5);

        assertEquals(3, result.size(), "应该有3个类别");
        assertEquals(300.0, result.get("food"), 0.01, "food 类别的总金额应为 300.0");
        assertEquals(50.0, result.get("transportation"), 0.01, "transportation 类别的总金额应为 50.0");
        assertEquals(300.0, result.get("entertainment"), 0.01, "entertainment 类别的总金额应为 300.0");
    }

    @Test
    public void testGetCategoryCountsEmptyRecords() {
        List<Record> emptyRecords = new ArrayList<>();
        Map<String, Double> result = categoryPercentage.getCategoryCounts(emptyRecords, 10);
        assertTrue(result.isEmpty(), "当记录为空时，应返回空的 Map");
    }

    @Test
    public void testGetDailyAmountSum() {
        double result = categoryPercentage.getDailyAmountSum(records);
        assertEquals(300.0, result, 0.01, "当天的金额总和应为 300.0");
    }

    @Test
    public void testGetDailyAmountSumEmptyRecords() {
        List<Record> emptyRecords = new ArrayList<>();
        double result = categoryPercentage.getDailyAmountSum(emptyRecords);
        assertEquals(0.0, result, 0.01, "当记录为空时，金额总和应为 0.0");
    }

    // 辅助方法：将 LocalDate 转换为 Date
    private Date dateFromLocalDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
    }
}