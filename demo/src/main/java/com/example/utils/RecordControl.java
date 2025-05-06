package com.example.utils;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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


/**
 * @className RecordControl
 * @description Tool class for managing Record objects
 */
public class RecordControl {

    /**
     * @methodName importRecordsFromCsv
     * @description Import records from CSV file and rename them to "record. csv" in the resources folder
     * @param filePath CSV file path
     * @return List of Record objects
     */
    public static List<Record> importRecordsFromCsv(String filePath) {
        List<Record> records = new ArrayList<>();
         SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm"); // Change the date format to 'yyyy MM dd HH: mm'

         try {
            // Get the path of the resource folder
            String projectRoot = System.getProperty("user.dir");
            // Build a complete file path
            Path targetFilePath = Paths.get(projectRoot, "demo","data", "record.csv");

            // Copy the source file to the resource folder and rename it to record.csv
            Files.copy(new File(filePath).toPath(), targetFilePath, StandardCopyOption.REPLACE_EXISTING);

            // Read CSV file from the target file path
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
    /**
     * @methodName readRecordFromResource
     * @description Read records from the data folder and return a list of Record objects, When the application is launched, it should be called
     * @return List of Record objects from the data folder
     */
    public static List<Record> readRecordFromResource(){
        List<Record> records = new ArrayList<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm"); //Change the date format to 'yyyy MM dd HH: mm'

        try {
         String projectRoot = System.getProperty("user.dir");
        Path targetFilePath = Paths.get(projectRoot, "demo","data", "record.csv"    );


           try (CSVReader reader = new CSVReader(new FileReader(targetFilePath.toFile()))) {
               String[] line;
               reader.readNext(); 
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
    // Used to find the largest paymentId
    public static int findMaxId(List<Record> records) {
        if (records == null || records.isEmpty()) {
            return 0;
        }
        return Integer.parseInt(Collections.max(records, Comparator.comparingInt(record -> Integer.parseInt(record.getPaymentId()))).getPaymentId());
    }
    /**
     * @methodName insertRecord
     * @description Insert a new record to the records list and return the updated list
     * @param records original records list
     * @param paymentDate 
     * @param amount
     * @param category
     * @param payee
     * @return List of Record objects with the new record added
     */
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
    /**
     * @methodName deleteRecord
     * @description Delete a record from the records list and return the updated list
     * @param records
     * @param paymentId paymentId of the record to be deleted
     * @return List of Record objects with the deleted record removed
     */
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
/**
 * @methodName updateRecord
 * @dscrpition Update records and store them in the data folder, After modifying the billing record, it should be called
 * @param records Global records list
 */
    public static void updateRecordsToCsv(List<Record> records) {
   SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm"); 

        String projectRoot = System.getProperty("user.dir");
        Path targetFilePath = Paths.get(projectRoot, "demo","data", "record.csv");

        try (CSVWriter writer = new CSVWriter(new FileWriter(targetFilePath.toFile()))){
            String[] header = {"Payment ID", "Payment Date", "Amount", "Category", "Payee"};
            writer.writeNext(header);

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
