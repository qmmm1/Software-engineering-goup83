package com.example.utils;

import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Date;
import com.example.model.Record;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;


public class InputRecord {

    public static List<Record> importRecordsFromCsv(String filePath) {
        List<Record> Records = new ArrayList<>();
         SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm"); // 修改日期格式为"yyyy-MM-dd HH:mm"

        try (CSVReader reader = new CSVReader(new FileReader(filePath))) {
            String[] line;
            reader.readNext(); // Skip the header line
            while ((line = reader.readNext()) != null) {
                String paymentId = line[0];
                Date paymentDate = dateFormat.parse(line[1]);
                double amount = Double.parseDouble(line[2]);
                String category = line[3];
                String payee = line[4];
                Record record = new Record(paymentId, paymentDate, amount, category, payee);
                Records.add(record);
            }
        } catch (IOException | CsvValidationException | ParseException e) {
            e.printStackTrace();
        }

        return Records;
     }
    public static int findMaxId(List<Record> records) {
        if (records == null || records.isEmpty()) {
            return 0;
        }
        return Integer.parseInt(Collections.max(records, Comparator.comparingInt(record -> Integer.parseInt(record.getPaymentId()))).getPaymentId());
    }
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
}
