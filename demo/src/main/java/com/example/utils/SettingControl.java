package com.example.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import com.example.model.Setting;

public class SettingControl {
     public static Setting readSettingFromFile(String filePath) throws IOException {
        Setting setting = new Setting();
        BufferedReader br = new BufferedReader(new FileReader(filePath));
        String line;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        while ((line = br.readLine()) != null) {
            if (line.contains("=")) {
                String[] parts = line.split("=");
                String key = parts[0].trim();
                String value = parts[1].trim();
                switch (key) {
                    case "budegt_ratewarning_low":
                        setting.setBudegt_ratewarning_low(Double.parseDouble(value));
                        break;
                    case "budegt_ratewarning_high":
                        setting.setBudegt_ratewarning_high(Double.parseDouble(value));
                        break;
                    case "budegt_ratewarning_max":
                        setting.setBudegt_ratewarning_max(Double.parseDouble(value));
                        break;
                    case "large_amount_warning":
                        setting.setLarge_amount_warning(Integer.parseInt(value));
                        break;
                    case "small_amount_warning":
                        setting.setSmall_amount_warning(Integer.parseInt(value));
                        break;
                    case "same_amount_warning":
                        setting.setSame_amount_warning(Integer.parseInt(value));
                        break;
                    case "budget":
                        setting.setAmount(Double.parseDouble(value));
                        break;
                    case "duration":
                        setting.setduration(Integer.parseInt(value));
                        break;
                    case "startDate":
                         try {
                                setting.setStartDate(dateFormat.parse(value));
                            } catch (ParseException e) {
                                System.err.println("无法解析日期：" + value);
                            }
                        break;
                    default:
                        System.err.println("未知属性：" + key);
                }
            }
        }
        br.close();
        return setting;
    }
   public static void writeSettingToFile(String filePath, Setting setting) throws IOException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        BufferedWriter bw = new BufferedWriter(new FileWriter(filePath));
        bw.write("budegt_ratewarning_low=" + setting.getBudegt_ratewarning_low());
        bw.newLine();
        bw.write("budegt_ratewarning_high=" + setting.getBudegt_ratewarning_high());
        bw.newLine();
        bw.write("budegt_ratewarning_max=" + setting.getBudegt_ratewarning_max());
        bw.newLine();
        bw.write("large_amount_warning=" + setting.getLarge_amount_warning());
        bw.newLine();
        bw.write("small_amount_warning=" + setting.getSmall_amount_warning());
        bw.newLine();
        bw.write("same_amount_warning=" + setting.getSame_amount_warning());
        bw.newLine();
        bw.write("budget=" + setting.getAmount());
        bw.newLine();
        bw.write("duration=" + setting.getDuration());
        bw.newLine();
        bw.write("startDate=" + dateFormat.format(setting.getStartDate()));
        bw.newLine();
        bw.close();
    }
}
