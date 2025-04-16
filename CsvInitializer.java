package com.example.utils;

import com.opencsv.CSVWriter;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class CsvInitializer {

    private static final String BASE_PATH = "demo/src/main/resources/";

    public static void main(String[] args) {
        // 创建标准的 CSV 文件
        createCsvFile("record.csv", new String[][]{
                {"Payment ID", "Payment Date", "Amount", "Category", "Payee"},
                {"1", "2025-04-10 12:00", "58.5", "food", "alice"},
                {"2", "2025-04-11 09:30", "120.0", "transportation", "bob"},
                {"3", "2025-04-12 20:15", "75.0", "entertainment", "charlie"},
        });

        // 序号列错乱的 CSV 文件
        createCsvFile("record_food.csv", new String[][]{
                {"Payment ID", "Payment Date", "Amount", "Category", "Payee"},
                {"1", "2025-04-01 08:15", "32.0", "food", "david"},
                {"3", "2025-04-06 18:30", "27.3", "food", "frank"},
                {"2", "2025-04-03 13:45", "45.5", "food", "emma"},
        });

        // 创建错误的 CSV 文件：类别错误
        createCsvFile("record_education.csv", new String[][]{
                {"Payment ID", "Payment Date", "Amount", "Category", "Payee"},
                {"1", "2025-03-20 10:00", "300.0", "education", "george"},
                {"2", "2025-03-25 16:30", "150.0", "education", "hannah"},
                {"3", "2025-03-26 16:45", "200.0", "eating", "sen"},
        });

        // 创建错误的 CSV 文件：缺少日期列
        createCsvFile("record_missing_date.csv", new String[][]{
                {"Payment ID", "Payment Date", "Amount", "Category", "Payee"},
                {"1", "", "100.0", "food", "John"},
                {"2", "200.0", "transportation", "Mary"},
        });

        // 创建错误的 CSV 文件：金额列非数字
        createCsvFile("record_invalid_amount.csv", new String[][]{
                {"Payment ID", "Payment Date", "Amount", "Category", "Payee"},
                {"1", "2025-04-01 10:00", "abc", "food", "John"},
                {"2", "2025-04-02 11:00", "150.75", "transportation", "Mary"},
        });

        // 创建错误的 CSV 文件：日期格式错误
        createCsvFile("record_invalid_date_format.csv", new String[][]{
                {"Payment ID", "Payment Date", "Amount", "Category", "Payee"},
                {"1", "2025/04/01", "100.0", "food", "John"},
                {"2", "2025-04-02T11:00", "200.0", "transportation", "Mary"},
                {"3", "2025-03-25", "150.0", "education", "hannah"}
        });

        // 创建错误的 CSV 文件：缺少一列（Payee 列缺失）
        createCsvFile("record_missing_column.csv", new String[][]{
                {"Payment ID", "Payment Date", "Amount", "Category"},
                {"1", "2025-04-03 12:00", "50.0", "entertainment"},
                {"2", "2025-04-04 14:00", "75.0", "education"},
        });

        // 创建错误的 CSV 文件：支付金额为 0 或负数
        createCsvFile("record_zero_or_negative_amount.csv", new String[][]{
                {"Payment ID", "Payment Date", "Amount", "Category", "Payee"},
                {"1", "2025-04-09 10:30", "0", "food", "John"},
                {"2", "2025-04-10 11:00", "-50.0", "transportation", "Mary"},
        });

        System.out.println("✅ 所有 CSV 文件已初始化完毕！");
    }

    private static void createCsvFile(String fileName, String[][] data) {
        try (CSVWriter writer = new CSVWriter(new FileWriter(BASE_PATH + fileName, StandardCharsets.UTF_8))) {
            for (String[] row : data) {
                writer.writeNext(row);
            }
        } catch (IOException e) {
            System.err.println("❌ 写入 " + fileName + " 失败！");
            e.printStackTrace();
        }
    }
}
