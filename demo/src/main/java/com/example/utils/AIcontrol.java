package com.example.utils;

import java.util.Date;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

import org.json.JSONObject; 

import com.alibaba.dashscope.app.*;
import com.alibaba.dashscope.exception.ApiException;
import com.alibaba.dashscope.exception.InputRequiredException;
import com.alibaba.dashscope.exception.NoApiKeyException;

import com.example.model.Record;
/**
 * AI control class, used to call AI models and obtain classification results
 */
public class AIcontrol {
/**
 * categoryAppCall.
 * call AI model to classify payee information
 * @param text payee information
 * @return classification result of payee information
 * @exception ApiException
 * @exception NoApiKeyException
 * @exception InputRequiredException
 */
    public static String categoryAppCall(String text)
    throws ApiException, NoApiKeyException, InputRequiredException {
ApplicationParam param = ApplicationParam.builder()
        .apiKey("sk-56a0710b710448e49dca7a9789a6a29e")
        .appId("5a2fc67e573b48b9bdb983065f1e4054")
        .prompt(text)
        .build(); 
// create an application instance
Application application = new Application("https://dashscope.aliyuncs.com/api/v1/");
ApplicationResult result = application.call(param);
return  result.getOutput().getText();
}
/**
 * suggestionAppCall.
 * call AI model to suggest
 * @param records Global billing records
 * @param suggestion suggestion request
 * @return suggestion result
 * @exception ApiException
 * @exception NoApiKeyException
 * @exception InputRequiredException
 */
public static String suggestionAppCall(List<Record> records,String suggestion) throws ApiException, NoApiKeyException, InputRequiredException{
    String text=records.stream()
                .map(Record::getDetails)
                .collect(Collectors.joining("\n"));
    ApplicationParam param = ApplicationParam.builder()
    .apiKey("sk-56a0710b710448e49dca7a9789a6a29e")
    .appId("94a411eaebee444f81fd9be0900cd421")
    .prompt(text+"\n"+"According to the billing records given above,give me a suggestion about "+suggestion)
    .build(); 
    Application application = new Application("https://dashscope.aliyuncs.com/api/v1/");
    ApplicationResult result = application.call(param);
    return  result.getOutput().getText();
   }
/**
 * singalAIcategory.
 * n call AI model to classify payee information for a single record
 * @param record single record to be classified
 */
public static void singalAIcategory(Record record){
    try{
        String result = categoryAppCall(record.getPayee());
        JSONObject jsonResult = new JSONObject(result);
        String category = jsonResult.getString("Label");
        record.setCategory(category);
    }
    catch (ApiException  | NoApiKeyException  | InputRequiredException e) {
        e.printStackTrace();
    }
}
/**
 * AIcategory.
 * call AI model to classify payee information for records within a certain duration
 * @param records Global billing records
 * @param duration duration of records to be classified
 */
 public static void AIcategory(List<Record> records,int duration){
    try{
        Calendar calendar = Calendar.getInstance();
        // 减去duration天
        calendar.add(Calendar.DAY_OF_YEAR, -duration);
        Date startDate = calendar.getTime();
    for (Record record : records) {

        Date recordDate = record.getPaymentDate();
         if (recordDate.after(startDate) || recordDate.equals(startDate)){
        String result = categoryAppCall(record.getPayee());
        JSONObject jsonResult = new JSONObject(result);
        String category = jsonResult.getString("Label");
        record.setCategory(category);
    }
}
} catch (ApiException  | NoApiKeyException  | InputRequiredException e) {
    e.printStackTrace();
 }
 }
}
