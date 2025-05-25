package com.example.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Date;
import com.example.model.Record;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvValidationException;

/**
 * Tool class for managing Record objects
 */
public class RecordControl {

    /**
     * importRecordsFromCsv.
     * mport records from CSV file and rename them to "record. csv" in
     *              the resources folder
     * @param filePath CSV file path
     * @return List of Record objects
     */
    public static List<Record> importRecordsFromCsv(String filePath) {
        List<Record> records = new ArrayList<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm"); // Change the date format to 'yyyy MM dd
                                                                                // HH: mm'

        try {
            // 1. 检查源文件是否存在并可读
            File sourceFile = new File(filePath);
            if (!sourceFile.exists() || !sourceFile.isFile()) {
                System.err.println("❌ 文件不存在: " + filePath);
                return Collections.emptyList();
            }
            if (!sourceFile.canRead()) {
                System.err.println("❌ 文件不可读: " + filePath);
                return Collections.emptyList();
            }


            // Get the path of the resource folder
            String projectRoot = System.getProperty("user.dir");
            // Build a complete file path
            Path targetFilePath = Paths.get(projectRoot, "demo", "data", "record.csv");
            Files.createDirectories(targetFilePath.getParent());

            // Copy the source file to the resource folder and rename it to record.csv
            Files.copy(sourceFile.toPath(), targetFilePath, StandardCopyOption.REPLACE_EXISTING);
            System.out.println("✅ 已复制文件到: " + targetFilePath);

            // Read CSV file from the target file path
            try (CSVReader reader = new CSVReader(
                    new InputStreamReader(new FileInputStream(targetFilePath.toFile()), StandardCharsets.UTF_8))) {

                String[] line;
                int lineNumber = 0;

                // 5. 读取头部
                line = reader.readNext(); // Skip the header line
                lineNumber++;
                if (line == null || line.length < 5) {
                    System.err.println("❌ CSV 文件头部无效或字段不足");
                    return Collections.emptyList();
                }

                while ((line = reader.readNext()) != null) {
                    lineNumber++;

                    // 跳过空行
                    if (line.length < 5 || Arrays.stream(line).allMatch(String::isEmpty)) {
                        System.out.println("⚠️ 第 " + lineNumber + " 行为空或字段不足，已跳过: " + Arrays.toString(line));
                        continue;
                    }

                    try {
                        String paymentId = line[0];
                        Date paymentDate = dateFormat.parse(line[1]);
                        double amount = Double.parseDouble(line[2]);
                        String category = line[3];
                        String payee = line[4];

                        Record record = new Record(paymentId, paymentDate, amount, category, payee);
                        records.add(record);
                    } catch (ParseException e) {
                        System.err.println("❌ 第 " + lineNumber + " 行日期格式错误: " + Arrays.toString(line));
                    } catch (NumberFormatException e) {
                        System.err.println("❌ 第 " + lineNumber + " 行金额格式错误: " + Arrays.toString(line));
                    } catch (Exception e) {
                        System.err.println("❌ 第 " + lineNumber + " 行处理时发生未知错误: " + Arrays.toString(line));
                        e.printStackTrace();
                    }
                }
            }

        } catch (IOException | CsvValidationException e) {
            System.err.println("❌ 文件读写或 CSV 解析错误！");
            e.printStackTrace();
        }

        System.out.println("✅ 成功导入记录数: " + records.size());

        return records;
    }

    /**
     * readRecordFromResource.
     *  Read records from the data folder and return a list of Record
     *              objects, When the application is launched, it should be called
     * @return List of Record objects from the data folder
     */
    public static List<Record> readRecordFromResource() {
        List<Record> records = new ArrayList<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm"); // Change the date format to 'yyyy MM dd
                                                                                // HH: mm'

        try {
            String projectRoot = System.getProperty("user.dir");
            Path targetFilePath = Paths.get(projectRoot, "demo", "data", "record.csv");

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
        return Integer.parseInt(
                Collections.max(records, Comparator.comparingInt(record -> Integer.parseInt(record.getPaymentId())))
                        .getPaymentId());
    }

    /**
     *  insertRecord.
     * Insert a new record to the records list and return the updated
     *              list
     * @param records     original records list
     * @param paymentDate
     * @param amount
     * @param category
     * @param payee
     * @return List of Record objects with the new record added
     */
    public static List<Record> insertRecord(List<Record> records, Date paymentDate, double amount, String category,
            String payee) {
        if (records == null) {
            records = new ArrayList<>();
        }
        int maxId = findMaxId(records);
        String newPaymentId = String.valueOf(maxId + 1);
        Record newRecord = new Record(newPaymentId, paymentDate, amount, category, payee);
        if (!newRecord.validatePayment()) {
            throw new IllegalArgumentException("Invalid payment record");
        }
        records.add(newRecord);
        return records;
    }

    /**
     * deleteRecord.
     * Delete a record from the records list and return the updated
     *              list
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
     * updateRecord.
     * Update records and store them in the data folder, After modifying
     *             the billing record, it should be called
     * @param records Global records list
     */
    public static void updateRecordsToCsv(List<Record> records) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        String projectRoot = System.getProperty("user.dir");
        Path targetFilePath = Paths.get(projectRoot, "demo", "data", "record.csv");

        try (CSVWriter writer = new CSVWriter(new FileWriter(targetFilePath.toFile()))) {
            String[] header = { "Payment ID", "Payment Date", "Amount", "Category", "Payee" };
            writer.writeNext(header);

            for (Record record : records) {
                String paymentId = record.getPaymentId();
                String paymentDate = dateFormat.format(record.getPaymentDate());
                double amount = record.getAmount();
                String category = record.getCategory();
                String payee = record.getPayee();

                String[] data = { paymentId, paymentDate, String.valueOf(amount), category, payee };
                writer.writeNext(data);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
