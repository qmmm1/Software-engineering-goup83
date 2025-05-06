package com.example.service;

import java.util.Calendar;
import java.util.List;
import java.util.Date;
import java.util.ArrayList;

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
 * @param record signal import record
 * @return whether large amount warning catched
 */
 public static String large_amount_warning(Setting setting,Record record){
     if(record.getAmount()>=setting.getLarge_amount_warning())
     {
        System.out.println("You have spent a large amount of money");
        return "catch";
     }
     return "normal";
}
/**
 * @methodName sequent_amount_warning
 * @description Sequent payment warning detection
 * @param setting User Settings
 * @param records Global billing records
 * @return whether sequent payment warning catched
 */
 public static String sequent_amount_warning(Setting setting, List<Record> records){
      int recordCount = 0;
        
        // 获取当前时间
        Calendar calendar = Calendar.getInstance();
        Date currentTime = calendar.getTime();
        
        // 设置时间范围为5分钟前到当前时间
        calendar.add(Calendar.MINUTE, -5);
        Date startTime = calendar.getTime();
        
        for (Record record : records) {
            Date paymentDate = record.getPaymentDate();
            if (!paymentDate.before(startTime) && !paymentDate.after(currentTime)) {
                recordCount++;
            }
        }
        
        if (recordCount >= setting.getSequent_payment_warning()) {
            System.out.println("You have made a lot of payments recently");
            return "catch";
 }
    return "normal";
}
/**
 * @methodName same_amount_warning
 * @description Same amount warning detection
 * @param setting User Settings
 * @param records Global billing records
 * @return whether same amount warning catched
 */
 public static String same_amount_warning(Setting setting, List<Record> records){
    Calendar calendar = Calendar.getInstance();
    Date currentTime = calendar.getTime();
    List<Record> newRecords = new ArrayList<>();
    
    calendar.add(Calendar.MINUTE, -setting.getSame_amount_warning());
    Date startTime = calendar.getTime();
    
    for (Record record : records) {
        Date paymentDate = record.getPaymentDate();
        if (!paymentDate.before(startTime) && !paymentDate.after(currentTime)) {
            newRecords.add(record);
        }
    }
    for(int i=0;i<newRecords.size();i++)
   {
      for(int j=i+1;j<newRecords.size();j++)
      {
         if(newRecords.get(i).getAmount()==newRecords.get(j).getAmount())
         {
            System.out.println("You have made the same amount of money in the past ");
            return "catch";
      }
   }
 }
 return "normal";
}
}