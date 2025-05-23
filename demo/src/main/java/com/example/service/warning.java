package com.example.service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

import com.example.model.Setting;
import com.example.model.Record;

/**
 * Warning pop-up recognition service.
 */
public class warning {
    /**
     * Detects the budget warning level based on the percentage of budget used.
     *
     * @param setting    the user settings containing threshold values
     * @param percentage the percentage of budget already used
     * @return a string indicating the budget warning level: "max", "high", "low",
     *         or "normal"
     */
    public static String budgetWarning(Setting setting, double percentage) {
        if (percentage > setting.getBudegt_ratewarning_max()) {
            System.out.println("You have run out of budget");
            return "max";
        } else if (percentage > setting.getBudegt_ratewarning_high()) {
            System.out.println("You are approaching the limit of your budget");
            return "high";
        } else if (percentage > setting.getBudegt_ratewarning_low()) {
            System.out.println("You have used a lot of your budget");
            return "low";
        } else {
            return "normal";
        }
    }

    /**
     * Detects if there are any records with amounts exceeding the large amount
     * warning threshold.
     *
     * @param setting the user settings containing the large amount warning
     *                threshold
     * @param records the list of transaction records to check
     * @return a map containing:
     *         <ul>
     *         <li>"code": "catch" if warning triggered, "normal" otherwise</li>
     *         <li>"amountList": list of amounts exceeding the threshold</li>
     *         </ul>
     */
    public static Map<String, Object> large_amount_warning(Setting setting, List<Record> records) {
        Map<String, Object> result = new HashMap<>();
        List<Double> amountList = new ArrayList<>();

        result.put("code", "normal");
        result.put("amountList", amountList);

        for (Record record : records) {
            if (record.getAmount() >= setting.getLarge_amount_warning()) {
                amountList.add(record.getAmount());
            }
        }

        if (!amountList.isEmpty()) {
            result.put("code", "catch");
            result.put("amountList", amountList);
            return result;
        }

        return result;
    }

    /**
     * Detects warnings for frequent (sequent) payments within a 5-minute window.
     *
     * @param setting    the user settings containing the threshold for frequent
     *                   payments
     * @param newRecords the new payment records to check
     * @param oldRecords the old payment records for context to check overlap in
     *                   time windows
     * @return a map containing:
     *         <ul>
     *         <li>"code": "catch" if warning triggered, "normal" otherwise</li>
     *         <li>"records": list of records that triggered the warning (if
     *         any)</li>
     *         </ul>
     */
    public static Map<String, Object> sequent_amount_warning(Setting setting, List<Record> newRecords,
            List<Record> oldRecords) {
        Map<String, Object> result = new HashMap<>();
        result.put("code", "normal");
        List<Record> records = new ArrayList<>();
        Set<Record> recordSet = new LinkedHashSet<>(); // 使用 Set 去重，保持顺序

        // sort according to time
        newRecords.sort(Comparator.comparing(Record::getPaymentDate));
        records.addAll(newRecords);
        // get records 5min ago~5min after, merge with newRecords
        if (!oldRecords.isEmpty()) {
            Date endmin = newRecords.get(0).getPaymentDate();
            Date startmin2 = newRecords.get(newRecords.size() - 1).getPaymentDate();

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(endmin);
            calendar.add(Calendar.MINUTE, -5);
            Calendar calendar2 = Calendar.getInstance();
            calendar2.setTime(startmin2);
            calendar2.add(Calendar.MINUTE, 5);

            Date startmin = calendar.getTime();
            Date endmin2 = calendar2.getTime();
            for (Record r : oldRecords) {
                Date nowTime = r.getPaymentDate();
                if ((nowTime.after(startmin) || nowTime.equals(startmin))
                        && (nowTime.before(endmin2) || nowTime.equals(endmin2))) {
                    records.add(r);
                }
            }
            records.sort(Comparator.comparing(Record::getPaymentDate));

        }

        int threshold = setting.getSequent_payment_warning();
        if (records.size() < threshold) {
            return result;
        }

        // 滑动窗口：检测连续 threshold+1 次支付是否在 5 分钟内
        int start = 0;
        for (int end = 0; end < records.size(); end++) {
            Instant currentTime = records.get(end).getPaymentDate().toInstant();

            // 移动左指针，确保窗口内时间差 ≤5 分钟
            while (start <= end) {
                Instant startTime = records.get(start).getPaymentDate().toInstant();
                long secondsDiff = ChronoUnit.SECONDS.between(startTime, currentTime);
                // 300秒
                if (secondsDiff > 300) {
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
            System.out.println("warning: catch sequent payment");
            result.put("records", new ArrayList<>(recordSet));
        }
        return result;
    }

    /**
     * Detects warnings for repeated payments of the same amount to the same payee
     * within a configured time window.
     *
     * @param setting    the user settings containing the time window threshold in
     *                   minutes
     * @param newRecords the new payment records to check
     * @param oldRecords the old payment records for context to check overlap in
     *                   time windows
     * @return a map containing:
     *         <ul>
     *         <li>"code": "catch" if warning triggered, "normal" otherwise</li>
     *         <li>"records": list of records that triggered the warning (if
     *         any)</li>
     *         </ul>
     */
    public static Map<String, Object> same_amount_warning(Setting setting, List<Record> newRecords,
            List<Record> oldRecords) {

        Map<String, Object> result = new HashMap<>();
        Set<Record> recordSet = new LinkedHashSet<>();
        List<Record> records = new ArrayList<>();

        // sort according to time
        newRecords.sort(Comparator.comparing(Record::getPaymentDate));
        records.addAll(newRecords);

        // get records 5min ago~5min after, merge with newRecords
        if (!oldRecords.isEmpty()) {
            Date endmin = newRecords.get(0).getPaymentDate();
            Date startmin2 = newRecords.get(newRecords.size() - 1).getPaymentDate();

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(endmin);
            calendar.add(Calendar.MINUTE, -setting.getSame_amount_warning());
            Calendar calendar2 = Calendar.getInstance();
            calendar2.setTime(startmin2);
            calendar2.add(Calendar.MINUTE, setting.getSame_amount_warning());

            Date startmin = calendar.getTime();
            Date endmin2 = calendar2.getTime();
            for (Record r : oldRecords) {
                Date nowTime = r.getPaymentDate();
                if ((nowTime.after(startmin) || nowTime.equals(startmin))
                        && (nowTime.before(endmin2) || nowTime.equals(endmin2))) {
                    records.add(r);
                }
            }
            records.sort(Comparator.comparing(Record::getPaymentDate));

        }

        Map<String, Map<Double, List<Record>>> groupedRecords = records.stream()
                .collect(Collectors.groupingBy(
                        Record::getPayee,
                        Collectors.groupingBy(Record::getAmount)));

        // 遍历每个分组，检查时间窗口
        for (Map<Double, List<Record>> amountGroup : groupedRecords.values()) {
            for (List<Record> group : amountGroup.values()) {
                // 按支付时间排序
                group.sort(Comparator.comparing(Record::getPaymentDate));

                // 滑动窗口检测
                int start = 0;
                for (int end = 0; end < group.size(); end++) {

                    Instant startTime = group.get(start).getPaymentDate().toInstant();
                    Instant currentTime = group.get(end).getPaymentDate().toInstant();
                    // 动态调整窗口左边界，确保时间差 ≤t 分钟
                    while (start < end &&
                            ChronoUnit.SECONDS.between(startTime,
                                    currentTime) > (setting.getSame_amount_warning() * 60)) {
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

        if (recordSet.size() != 0) {
            System.out.println("You have made the same amount of money in the past ");
            result.put("code", "catch");
            System.out.println("warning: catch same amount to the same payee");
            result.put("records", new ArrayList<>(recordSet));
            return result;
        }

        result.put("code", "normal");
        return result;
    }
}