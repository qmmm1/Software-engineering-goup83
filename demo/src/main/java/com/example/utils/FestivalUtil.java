package com.example.utils;

import cn.hutool.core.date.ChineseDate;

import java.time.*;
import java.time.temporal.TemporalAdjusters;
import java.util.HashMap;
import java.util.Map;

/**
 * Utility class for detecting known solar and lunar calendar festivals.
 */
public class FestivalUtil {

  private static final Map<String, String> FIXED_FESTIVALS = new HashMap<>();

  static {
    // Solar calendar festival（MM-dd）
    FIXED_FESTIVALS.put("01-01", "New Year's Day");
    FIXED_FESTIVALS.put("12-25", "Christmas");
    FIXED_FESTIVALS.put("10-31", "Halloween");
    FIXED_FESTIVALS.put("02-14", "Valentine's Day");
    FIXED_FESTIVALS.put("04-04", "Qingming Festival");

    // Lunar calendar festival（LMM-dd）
    FIXED_FESTIVALS.put("L01-01", "Chinese New Year");
    FIXED_FESTIVALS.put("L01-15", "Lantern Festival");
    FIXED_FESTIVALS.put("L05-05", "Dragon Boat Festival");
    FIXED_FESTIVALS.put("L08-15", "Mid-Autumn Festival");
  }

  /**
   * Returns the name of the festival (if any) corresponding to the given date.
   * It checks solar calendar festivals, lunar calendar festivals, and
   * Thanksgiving.
   *
   * @param date the date to check
   * @return the name of the festival if matched; {@code null} otherwise
   */
  public static String getFestivalName(LocalDate date) {
    // Solar
    String solarKey = String.format("%02d-%02d", date.getMonthValue(), date.getDayOfMonth());
    if (FIXED_FESTIVALS.containsKey(solarKey)) {
      return FIXED_FESTIVALS.get(solarKey);
    }

    // Lunar
    ChineseDate chineseDate = new ChineseDate(date);
    String lunarKey = String.format("L%02d-%02d", chineseDate.getMonth(), chineseDate.getDay());
    if (FIXED_FESTIVALS.containsKey(lunarKey)) {
      return FIXED_FESTIVALS.get(lunarKey);
    }

    // Thanksgiving
    if (date.equals(getThanksgivingDate(date.getYear()))) {
      return "Thanksgiving";
    }

    return null;
  }

  /**
   * Calculates the date of Thanksgiving for a given year.
   * Thanksgiving is celebrated on the fourth Thursday of November.
   *
   * @param year the year to calculate Thanksgiving for
   * @return the date of Thanksgiving in the given year
   */
  static LocalDate getThanksgivingDate(int year) {
    return LocalDate.of(year, Month.NOVEMBER, 1)
        .with(TemporalAdjusters.dayOfWeekInMonth(4, DayOfWeek.THURSDAY));
  }

}
