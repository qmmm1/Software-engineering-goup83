package com.example.service;

import com.example.model.Record;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class EconomySuggestionTest {

    private List<Record> records;

    @BeforeEach
    public void setUp() {
        // 初始化记录列表
        records = new ArrayList<>();
        records.add(new Record("1", new Date(), 100.0, "food", "Alice"));
        records.add(new Record("2", new Date(), 200.0, "transportation", "Bob"));
        records.add(new Record("3", new Date(), 50.0, "entertainment", "Charlie"));
    }

    @Test
    public void testBudgetSuggestion() {
        // 调用方法并获取结果
        String result = economySuggestion.BudgetSuggestion(records, 30);

        // 验证结果
        assertNotNull(result, "Should return a non-null budget suggestion");
        assertTrue(result.contains("budget"), "Should contain the word 'budget'");
    }

    @Test
    public void testSavingsSuggestion() {
        // 调用方法并获取结果
        String result = economySuggestion.SavingsSuggestion(records, 30);

        // 验证结果
        assertNotNull(result, "Should return a non-null savings suggestion");
        assertTrue(result.contains("savings"), "Should contain the word 'savings'");
    }

    @Test
    public void testExpensesSuggestion() {
        // 调用方法并获取结果
        String result = economySuggestion.ExpensesSuggestion(records, 30);

        // 验证结果
        assertNotNull(result, "Should return a non-null expenses suggestion");
        assertTrue(result.contains("expenses"), "Should contain the word 'expenses'");
    }
}