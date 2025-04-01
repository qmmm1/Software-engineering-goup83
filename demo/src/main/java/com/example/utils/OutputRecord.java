package com.example.utils;

import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

import com.opencsv.CSVWriter;
import com.example.model.Record;

public class OutputRecord {
      public static void exportRecordsToCsv(List<Record> records, String filePath) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm"); // 精确到分钟

        try (CSVWriter writer = new CSVWriter(new FileWriter(filePath))) {
            // 写入表头
            String[] header = {"Payment ID", "Payment Date", "Amount", "Category","Payee"};
            writer.writeNext(header);

            // 遍历records列表并写入每一行数据
            for (Record record : records) {
                String paymentId = record.getPaymentId();
                String paymentDate = dateFormat.format(record.getPaymentDate());
                double amount = record.getAmount();
                String category = record.getCategory();
                String payee = record.getPayee();

                String[] data = {paymentId, paymentDate, String.valueOf(amount), category,payee};
                writer.writeNext(data);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
}
}
