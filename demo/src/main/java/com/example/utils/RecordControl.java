package com.example.utils;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Date;
import com.example.model.Record;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvValidationException;


public class RecordControl {

    // 从CSV文件中导入记录,并重命名为“record.csv”到resources文件夹
    public static List<Record> importRecordsFromCsv(String filePath) {
        List<Record> records = new ArrayList<>();
         SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm"); // 修改日期格式为"yyyy-MM-dd HH:mm"

         try {
            // 获取资源文件夹的路径
            Path resourcePath = new File(RecordControl.class.getClassLoader().getResource("").getPath()).toPath();
            Path targetFilePath = resourcePath.resolve("record.csv");

            // 将源文件复制到资源文件夹并重命名为record.csv
            Files.copy(new File(filePath).toPath(), targetFilePath, StandardCopyOption.REPLACE_EXISTING);

            // 从目标文件路径读取CSV文件
            try (CSVReader reader = new CSVReader(new FileReader(targetFilePath.toFile()))) {
                String[] line;
                reader.readNext(); // Skip the header line
                while ((line = reader.readNext()) != null) {
                    String paymentId = line[0];
                    Date paymentDate = dateFormat.parse(line[1]);
                    double amount = Double.parseDouble(line[2]);
                    String category = line[3];
                    String payee = line[4];
                    Record record = new Record(paymentId, paymentDate, amount, category, payee);
                    records.add(record);
                }
            }

        } catch (IOException | CsvValidationException | ParseException e) {
            e.printStackTrace();
        }

        return records;
     }
     // 从资源文件夹中的“record.csv”读取记录，应用开启时调用。
    public static List<Record> readRecordFromResource(){
        List<Record> records = new ArrayList<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm"); // 修改日期格式为"yyyy-MM-dd HH:mm"

        try {
           // 获取资源文件夹的路径
           Path resourcePath = new File(RecordControl.class.getClassLoader().getResource("").getPath()).toPath();
           Path targetFilePath = resourcePath.resolve("record.csv");


           // 从目标文件路径读取CSV文件
           try (CSVReader reader = new CSVReader(new FileReader(targetFilePath.toFile()))) {
               String[] line;
               reader.readNext(); // Skip the header line
               while ((line = reader.readNext()) != null) {
                   String paymentId = line[0];
                   Date paymentDate = dateFormat.parse(line[1]);
                   double amount = Double.parseDouble(line[2]);
                   String category = line[3];
                   String payee = line[4];
                   Record record = new Record(paymentId, paymentDate, amount, category, payee);
                   records.add(record);
               }
           }

       } catch (IOException | CsvValidationException | ParseException e) {
           e.printStackTrace();
       }

       return records;
    }
    // 用于查找最大的paymentId
    public static int findMaxId(List<Record> records) {
        if (records == null || records.isEmpty()) {
            return 0;
        }
        return Integer.parseInt(Collections.max(records, Comparator.comparingInt(record -> Integer.parseInt(record.getPaymentId()))).getPaymentId());
    }
    //插入单个record
    public static List<Record> insertRecord(List<Record> records,Date paymentDate, double amount, String category,String payee) {
        if (records == null) {
            records = new ArrayList<>();
        }
        int maxId = findMaxId(records);
        String newPaymentId = String.valueOf(maxId + 1);
        Record newRecord = new Record(newPaymentId, paymentDate, amount, category, payee);
        if(!newRecord.validatePayment())
        {
            throw new IllegalArgumentException("Invalid payment record");
        }
        records.add(newRecord);
        return records;
    }
    //删除单个record
    public static List<Record> deleteRecord(List<Record> records, String paymentId) {
        if (records == null || records.isEmpty()) {
            return null;
        }
        for (Record record : records) {
            if (record.getPaymentId().equals(paymentId)) {
                records.remove(record);
                return records;
            }
    }
    return records;
}
// 用于更新记录到CSV文件，记录修改时调用
    public static void updateRecordsToCsv(List<Record> records) {
   SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm"); // 精确到分钟

        // 获取资源文件夹的路径
        Path resourcePath = new File(RecordControl.class.getClassLoader().getResource("").getPath()).toPath();
        Path targetFilePath = resourcePath.resolve("record.csv");

        try (CSVWriter writer = new CSVWriter(new FileWriter(targetFilePath.toFile()))){
            // 写入表头
            String[] header = {"Payment ID", "Payment Date", "Amount", "Category", "Payee"};
            writer.writeNext(header);

            // 遍历records列表并写入每一行数据
            for (Record record : records) {
                String paymentId = record.getPaymentId();
                String paymentDate = dateFormat.format(record.getPaymentDate());
                double amount = record.getAmount();
                String category = record.getCategory();
                String payee = record.getPayee();

                String[] data = {paymentId, paymentDate, String.valueOf(amount), category, payee};
                writer.writeNext(data);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
}
}
