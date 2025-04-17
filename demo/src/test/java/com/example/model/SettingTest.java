package com.example.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Calendar;
import java.util.Date;

class SettingTest {
    // 测试设置和获取金额的功能
    @Test
    void testSetAndGetAmount() {
        Setting setting = new Setting();
        setting.setAmount(1000.0); // 设置金额为 1000.0
        assertEquals(1000.0, setting.getAmount(), "Amount should be set correctly"); // 验证金额是否正确设置
    }

    // 测试设置金额为负值时的行为
    @Test
    void testSetAmountWithNegativeValue() {
        Setting setting = new Setting();
        setting.setAmount(-100.0); // 尝试设置金额为 -100.0
        assertEquals(0.0, setting.getAmount(), "Amount should not be set with negative value"); // 验证金额是否被设置为 0.0（因为金额不能为负）
    }

    // 测试设置和获取持续时间的功能
    @Test
    void testSetAndGetDuration() {
        Setting setting = new Setting();
        setting.setduration(30); // 设置持续时间为 30 天
        assertEquals(30, setting.getDuration(), "Duration should be set correctly"); // 验证持续时间是否正确设置
    }

    // 测试设置持续时间为负值时的行为
    @Test
    void testSetDurationWithNegativeValue() {
        Setting setting = new Setting(); // 创建一个 Setting 对象
        setting.setduration(-10); // 尝试设置持续时间为 -10 天
        assertEquals(0, setting.getDuration(), "Duration should not be set with negative value"); // 验证持续时间是否被设置为 0（因为持续时间不能为负）
    }

    // 测试将持续时间设置为一周的功能
    @Test
    void testSetDurationWeek() {
        Setting setting = new Setting(); // 创建一个 Setting 对象
        setting.setduration_week(); // 调用方法将持续时间设置为一周
        assertEquals(7, setting.getDuration(), "Duration should be set to 7 days"); // 验证持续时间是否被正确设置为 7 天
    }

    // 测试将持续时间设置为一个月的功能
    @Test
    void testSetDurationMonth() {
        Setting setting = new Setting(); // 创建一个 Setting 对象
        setting.setduration_month(); // 调用方法将持续时间设置为一个月
        assertEquals(30, setting.getDuration(), "Duration should be set to 30 days"); // 验证持续时间是否被正确设置为 30 天
    }

    // 测试设置和获取开始日期的功能
    @Test
    void testSetAndGetStartDate() {
        Setting setting = new Setting(); // 创建一个 Setting 对象
        Calendar calendar = Calendar.getInstance(); // 获取当前时间
        calendar.set(2025, Calendar.APRIL, 10); // 设置日期为 2025 年 4 月 10 日
        Date startDate = calendar.getTime(); // 获取设置的日期
        setting.setStartDate(startDate); // 设置开始日期
        assertEquals(startDate, setting.getStartDate(), "Start date should be set correctly"); // 验证开始日期是否正确设置
    }
}