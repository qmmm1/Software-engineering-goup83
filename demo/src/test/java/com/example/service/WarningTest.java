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

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WarningTest {

    private Setting setting;
    private List<Record> records;

    @BeforeEach
    public void setUp() {
        setting = new Setting();
        setting.setBudegt_ratewarning_max(1.0);
        setting.setBudegt_ratewarning_high(0.8);
        setting.setBudegt_ratewarning_low(0.5);
        setting.setLarge_amount_warning((int) 1000.0);
        setting.setSequent_payment_warning(3);
        setting.setSame_amount_warning(10);

        records = new ArrayList<>();
    }

    @Test
    public void testBudgetWarningMax() {
        assertEquals("max", warning.budgetWarning(setting, 1.2), "应该提示预算已用完");
    }

    @Test
    public void testBudgetWarningHigh() {
        assertEquals("high", warning.budgetWarning(setting, 0.85), "应该提示预算即将用完");
    }

    @Test
    public void testBudgetWarningLow() {
        assertEquals("low", warning.budgetWarning(setting, 0.6), "应该提示预算已使用较多");
    }

    @Test
    public void testBudgetWarningNormal() {
        assertEquals("normal", warning.budgetWarning(setting, 0.3), "应该提示预算正常");
    }

    @Test
    public void testLargeAmountWarning() {
        Record record = new Record("1", dateFromLocalDateTime(LocalDateTime.now()), 1200.0, "food", "Alice");
        assertEquals("catch", new warning().large_amount_warning(setting, record), "应该提示大额交易");
    }

    @Test
    public void testLargeAmountWarningNormal() {
        Record record = new Record("1", dateFromLocalDateTime(LocalDateTime.now()), 800.0, "food", "Alice");
        assertEquals("normal", new warning().large_amount_warning(setting, record), "应该提示正常");
    }

    @Test
    public void testSequentAmountWarning() {
        records.add(new Record("1", dateFromLocalDateTime(LocalDateTime.now().minusMinutes(1)), 100.0, "food", "Alice"));
        records.add(new Record("2", dateFromLocalDateTime(LocalDateTime.now().minusMinutes(2)), 200.0, "food", "Bob"));
        records.add(new Record("3", dateFromLocalDateTime(LocalDateTime.now().minusMinutes(3)), 300.0, "food", "Charlie"));
        records.add(new Record("4", dateFromLocalDateTime(LocalDateTime.now().minusMinutes(4)), 400.0, "food", "David"));

        assertEquals("catch", new warning().sequent_amount_warning(setting, records), "应该提示频繁交易");
    }

    @Test
    public void testSequentAmountWarningNormal() {
        records.add(new Record("1", dateFromLocalDateTime(LocalDateTime.now().minusMinutes(2)), 100.0, "food", "Alice"));
        records.add(new Record("2", dateFromLocalDateTime(LocalDateTime.now().minusMinutes(4)), 200.0, "food", "Bob"));
        records.add(new Record("3", dateFromLocalDateTime(LocalDateTime.now().minusMinutes(12)), 300.0, "food", "Charlie"));

        assertEquals("normal", new warning().sequent_amount_warning(setting, records), "应该提示正常");
    }

    @Test
    public void testSameAmountWarning() {
        records.add(new Record("1", dateFromLocalDateTime(LocalDateTime.now().minusMinutes(2)), 100.0, "food", "Alice"));
        records.add(new Record("2", dateFromLocalDateTime(LocalDateTime.now().minusMinutes(4)), 100.0, "food", "Bob"));
        records.add(new Record("3", dateFromLocalDateTime(LocalDateTime.now().minusMinutes(6)), 200.0, "food", "Charlie"));

        assertEquals("catch", new warning().same_amount_warning(setting, records), "应该提示相同金额交易");
    }

    @Test
    public void testSameAmountWarningNormal() {
        records.add(new Record("1", dateFromLocalDateTime(LocalDateTime.now().minusMinutes(2)), 100.0, "food", "Alice"));
        records.add(new Record("2", dateFromLocalDateTime(LocalDateTime.now().minusMinutes(4)), 200.0, "food", "Bob"));
        records.add(new Record("3", dateFromLocalDateTime(LocalDateTime.now().minusMinutes(6)), 300.0, "food", "Charlie"));

        assertEquals("normal", new warning().same_amount_warning(setting, records), "应该提示正常");
    }

    private Date dateFromLocalDateTime(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }
}