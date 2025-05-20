package com.example.service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

import com.example.model.Setting;
import com.example.model.Record;
/**
 * @className warning
 * @description Warning pop-up recognition
 */
public class warning {
    /**
     * @methodName budgetWarning
     * @description Budget warning detection
     * @param setting User Settings
     * @param percentage Percentage of budget used
     * @return budget warning level
     */
    public static String budgetWarning(Setting setting,double percentage){
        if(percentage>setting.getBudegt_ratewarning_max())
        {
            System.out.println("You have run out of budget");
            return "max";
        }
        else if(percentage>setting.getBudegt_ratewarning_high())
        {
            System.out.println("You are approaching the limit of your budget");
            return "high";
        }
        else if(percentage>setting.getBudegt_ratewarning_low())
        {
            System.out.println("You have used a lot of your budget");
            return "low";
        }
        else{
            return "normal";
        }
    }
    /**
     * @methodName large_amount_warning
     * @description Large amount warning detection
     * @param setting User Settings
     * @param records signal import record
     * @return whether large amount warning catched
     */
    public static Map<String,Object> large_amount_warning(Setting setting,List<Record> records){
        Map<String,Object> result=new HashMap<>();
        List<Double> amountList=new ArrayList<>();

        result.put("code","normal");
        result.put("amountList",amountList);

         for (Record record : records) {
            if(record.getAmount()>=setting.getLarge_amount_warning())
            {
                amountList.add(record.getAmount());
            }
        }

         if(!amountList.isEmpty()){
             result.put("code","catch");
             result.put("amountList",amountList);
             return result;
         }

        return result;
    }
    /**
     * 高频
     * @methodName sequent_amount_warning
     * @description Sequent payment warning detection
     * @param setting User Settings
     * @param records Global billing records
     * @return whether sequent payment warning catched
     */

    public static Map<String, Object> sequent_amount_warning(Setting setting, List<Record> records) {
        Map<String, Object> result = new HashMap<>();
        result.put("code", "normal");
        Set<Record> recordSet = new LinkedHashSet<>(); // 使用 Set 去重，保持顺序

        int threshold = setting.getSequent_payment_warning();
        if (records.size() < threshold) {
            return result;
        }

        // 按时间排序
        records.sort(Comparator.comparing(Record::getPaymentDate));

        // 滑动窗口：检测连续 threshold+1 次支付是否在 5 分钟内
        int start = 0;
        for (int end = 0; end < records.size(); end++) {
            Instant currentTime = records.get(end).getPaymentDate().toInstant();

            // 移动左指针，确保窗口内时间差 ≤5 分钟
            while (start <= end) {
                Instant startTime = records.get(start).getPaymentDate().toInstant();
                long secondsDiff  = ChronoUnit.SECONDS.between(startTime, currentTime);
                //300秒
                if (secondsDiff  >300) {
                    start++;
                } else {
                    break;
                }
            }

            // 当前窗口大小 >= threshold+1 时，记录所有元素
            if (end - start + 1 >= threshold) {
                for (int i = start; i <= end; i++) {
                    recordSet.add(records.get(i));
                }
            }
        }

        if (!recordSet.isEmpty()) {
            result.put("code", "catch");
            result.put("records", new ArrayList<>(recordSet));
        }
        return result;
    }
    /**
     * @methodName same_amount_warning
     * @description Same amount warning detection
     * @param setting User Settings
     * @param records Global billing records
     * @return whether same amount warning catched
     */
    public static Map<String,Object>  same_amount_warning(Setting setting, List<Record> records){

        Map<String,Object> result=new HashMap<>();
        Set<Record> recordSet = new LinkedHashSet<>();


        Map<String, Map<Double, List<Record>>> groupedRecords = records.stream()
                .collect(Collectors.groupingBy(
                        Record::getPayee,
                        Collectors.groupingBy(Record::getAmount)
                ));

            //  遍历每个分组，检查时间窗口
        for (Map<Double, List<Record>> amountGroup : groupedRecords.values()) {
            for (List<Record> group : amountGroup.values()) {
                // 按支付时间排序
                group.sort(Comparator.comparing(Record::getPaymentDate));

                // 滑动窗口检测
                int start = 0;
                for (int end = 0; end < group.size(); end++) {

                    Instant startTime = group.get(start).getPaymentDate().toInstant();
                    Instant currentTime = group.get(end).getPaymentDate().toInstant();
                    // 动态调整窗口左边界，确保时间差 ≤5 分钟
                    while (start < end &&
                            ChronoUnit.SECONDS.between(startTime, currentTime)>(setting.getSame_amount_warning()*60)) {
                        start++;
                    }

                    // 窗口内存在至少两个记录，则记录所有相关条目
                    if (end - start + 1 >= 2) {
                        for (int i = start; i <= end; i++) {
                            recordSet.add(group.get(i));
                        }
                    }
                }
            }
        }


        if(recordSet.size()!=0){
            System.out.println("You have made the same amount of money in the past ");
            result.put("code","catch");
            result.put("records",new ArrayList<>(recordSet));
            return result;
        }

        result.put("code","normal");
        return result;
    }
}