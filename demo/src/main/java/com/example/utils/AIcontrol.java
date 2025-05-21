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
 * @className AIcontrol
 * @description AI control class, used to call AI models and obtain classification results
 */
public class AIcontrol {
/**
 * @methodName categoryAppCall
 * @description call AI model to classify payee information
 * @param text payee information
 * @return classification result of payee information
 * @throws ApiException
 * @throws NoApiKeyException
 * @throws InputRequiredException
 */
    public static String categoryAppCall(String text)
    throws ApiException, NoApiKeyException, InputRequiredException {
ApplicationParam param = ApplicationParam.builder()
        // 若没有配置环境变量，可用百炼API Key将下行替换为：.apiKey("sk-xxx")。但不建议在生产环境中直接将API Key硬编码到代码中，以减少API Key泄露风险。
        .apiKey("sk-56a0710b710448e49dca7a9789a6a29e")
        .appId("5a2fc67e573b48b9bdb983065f1e4054")
        .prompt(text)
        .build(); 
// 配置私网终端节点
Application application = new Application("https://dashscope.aliyuncs.com/api/v1/");
ApplicationResult result = application.call(param);
return  result.getOutput().getText();
}
/**
 * @methodName suggestionAppCall
 * @description call AI model to suggest
 * @param records Global billing records
 * @param suggestion suggestion request
 * @return suggestion result
 * @throws ApiException
 * @throws NoApiKeyException
 * @throws InputRequiredException
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
 * @methodName singalAIcategory
 * @description call AI model to classify payee information for a single record
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
 * @methodName AIcategory
 * @description call AI model to classify payee information for records within a certain duration
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
