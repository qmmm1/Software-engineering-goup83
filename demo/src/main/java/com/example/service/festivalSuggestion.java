package com.example.service;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class festivalSuggestion {
    private Map<Date, String> festivals = new HashMap<>();

    public festivalSuggestion() {
        // 初始化节日数据
        initializeFestivals();
    }
    private Date getDate(int month, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set( month, day);
        return calendar.getTime();
    }
    private void initializeFestivals() {
        // 添加一些节日示例
        festivals.put(getDate( 0, 1), "新年"); // 注意：月份从0开始计数
        festivals.put(getDate( 2, 8), "妇女节");
        festivals.put(getDate(4, 1), "劳动节");
        festivals.put(getDate( 9, 1), "国庆节");
        // 根据需要添加更多节日
    }
    private boolean isSameDay(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal1.setTime(date1);
        cal2.setTime(date2);

        return cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH) &&
               cal1.get(Calendar.DAY_OF_MONTH) == cal2.get(Calendar.DAY_OF_MONTH);
    }
    public String getSuggestionForToday(double percentage) {
        Date today = new Date();
        String festival = null;

        for (Map.Entry<Date, String> entry : festivals.entrySet()) {
            if (isSameDay(entry.getKey(), today)) {
                festival = entry.getValue();
                break;
            }
        }

        if (festival != null) {
            if(percentage>=1)
            {return "今天是" + festival + "，您的预算已经用完，请提前准备！";}
            if(percentage>=0.8)
            {return "今天是" + festival + "，您的预算即将消耗完，请注意减少日常花费！";}
            else if(percentage>=0.5)
            {return "今天是" + festival + "，您的预算还可以，请继续保持, 勿超前消费！";}
            else if(percentage>=0.2)
            {return "今天是" + festival + "，您的预算还有许多，可以购置些"+festival+"相关的物件！";}
        } else {
            return "今天没有节日，祝您工作愉快！";
        }
        return "someting wrong";
    }

}
