package com.example.service;

import java.util.Calendar;
import java.util.List;
import java.util.Date;
import java.util.ArrayList;

import com.example.model.Setting;
import com.example.model.Record;
public class warning {
    public void budgetWarning(Setting setting,double percentage){
      if(percentage>setting.getBudegt_ratewarning_max())
      {
        System.out.println("You have run out of budget");
      }
      else if(percentage>setting.getBudegt_ratewarning_high())
      {
        System.out.println("You are approaching the limit of your budget");
      }
      else if(percentage>setting.getBudegt_ratewarning_low())
      {
        System.out.println("You have used a lot of your budget");
    }
} 
 public void large_amount_warning(Setting setting,Record record){
     if(record.getAmount()>=setting.getLarge_amount_warning())
     {
        System.out.println("You have spent a large amount of money");
     }
}
 public void small_amount_warning(Setting setting, List<Record> records){
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
 }
}
 public void same_amount_warning(Setting setting, List<Record> records){
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
            return ;
      }
   }
 }
}
}