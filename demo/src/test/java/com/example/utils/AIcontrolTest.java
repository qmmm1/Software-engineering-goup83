package com.example.utils;

import com.example.model.Record;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class AIcontrolTest {

    private List<Record> records;

    @BeforeEach
    public void setUp() {
        records = new ArrayList<>();
        records.add(new Record("1", new Date(), 100.0, "food", "Alice"));
        records.add(new Record("2", new Date(), 200.0, "transportation", "Bob"));
        records.add(new Record("3", new Date(), 50.0, "entertainment", "Charlie"));
    }

    @Test
    public void testCategoryAppCall() {
        try {
            String result = AIcontrol.categoryAppCall("Alice");
            assertNotNull(result, "Result should not be null");
        } catch (Exception e) {
            fail("Unexpected exception: " + e.getMessage());
        }
    }

    @Test
    public void testSuggestionAppCall() {
        try {
            String result = AIcontrol.suggestionAppCall(records, "how to save money");
            assertNotNull(result, "Result should not be null");
        } catch (Exception e) {
            fail("Unexpected exception: " + e.getMessage());
        }
    }

    @Test
    public void testSingalAIcategory() {
        Record record = new Record("1", new Date(), 100.0, "", "Alice");
        try {
            AIcontrol.singalAIcategory(record);
            assertNotNull(record.getCategory(), "Category should be set");
        } catch (Exception e) {
            fail("Unexpected exception: " + e.getMessage());
        }
    }

    @Test
    public void testAIcategory() {
        try {
            AIcontrol.AIcategory(records, 30);
            for (Record record : records) {
                assertNotNull(record.getCategory(), "Category should be set for each record");
            }
        } catch (Exception e) {
            fail("Unexpected exception: " + e.getMessage());
        }
    }
}