package com.example.utils;

import java.util.List;
import org.json.JSONObject; 

import com.alibaba.dashscope.app.*;
import com.alibaba.dashscope.exception.ApiException;
import com.alibaba.dashscope.exception.InputRequiredException;
import com.alibaba.dashscope.exception.NoApiKeyException;

import com.example.model.Record;
public class AIcontrol {
    public static String appCall(String text)
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
 public static void AIcategory(List<Record> records){
    try{
    for (Record record : records) {
        String result = appCall(record.getPayee());
        JSONObject jsonResult = new JSONObject(result);
        String category = jsonResult.getString("Label");
        record.setCategory(category);
    }
} catch (ApiException  | NoApiKeyException  | InputRequiredException e) {
    e.printStackTrace();
 }
 }
}
