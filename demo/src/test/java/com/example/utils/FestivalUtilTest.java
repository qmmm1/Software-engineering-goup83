package com.example.utils;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

class FestivalUtilTest {

    @Test
    void testGetFestivalName_SolarFestival() {
        // 测试元旦
        LocalDate newYear = LocalDate.of(2025, 1, 1);
        assertEquals("New Year's Day", FestivalUtil.getFestivalName(newYear), "Should return New Year's Day");

        // 测试圣诞节
        LocalDate christmas = LocalDate.of(2025, 12, 25);
        assertEquals("Christmas", FestivalUtil.getFestivalName(christmas), "Should return Christmas");

        // 测试情人节
        LocalDate valentinesDay = LocalDate.of(2025, 2, 14);
        assertEquals("Valentine's Day", FestivalUtil.getFestivalName(valentinesDay), "Should return Valentine's Day");
        // 测试清明节（2025年清明节是4月4日）
        LocalDate qingmingFestival = LocalDate.of(2025, 4, 4);
        assertEquals("Qingming Festival", FestivalUtil.getFestivalName(qingmingFestival), "Should return Qingming Festival");
    }

    @Test
    void testGetFestivalName_LunarFestival() {
        // 测试春节
        LocalDate chineseNewYear = LocalDate.of(2025, 1, 29);
        assertEquals("Chinese New Year", FestivalUtil.getFestivalName(chineseNewYear), "Should return Chinese New Year");

        // 测试元宵节
        LocalDate lanternFestival = LocalDate.of(2025, 2, 12);
        assertEquals("Lantern Festival", FestivalUtil.getFestivalName(lanternFestival), "Should return Lantern Festival");


    }

    @Test
    void testGetFestivalName_Thanksgiving() {
        // 测试感恩节（2025年感恩节是11月27日）
        LocalDate thanksgiving = FestivalUtil.getThanksgivingDate(2025);
        assertEquals("Thanksgiving", FestivalUtil.getFestivalName(thanksgiving), "Should return Thanksgiving");
    }

    @Test
    void testGetFestivalName_NoFestival() {
        // 测试非节日日期
        LocalDate noFestival = LocalDate.of(2025, 3, 15);
        assertNull(FestivalUtil.getFestivalName(noFestival), "Should return null for non-festival date");
    }
}