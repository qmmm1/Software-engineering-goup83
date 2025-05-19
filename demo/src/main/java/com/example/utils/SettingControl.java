package com.example.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.example.model.Setting;

/**
 * @className SettingControl
 * @description Used to read the settings. txt file and write the Setting object to the settings. txt file.
 */
public class SettingControl {
    /**
     * @methodName readSettingFromFile
     * @description Read the Setting object from the data/settings. txt file, which should be called when the application is launched.
     * @return Setting object
     * @throws IOException
     */
    public static Setting readSettingFromFile() throws IOException {
    Setting setting = new Setting();
            String projectRoot = System.getProperty("user.dir");
        // 构建完整文件路径
        Path filePath = Paths.get(projectRoot, "demo","data", "setting.txt");
    InputStream inputStream = Files.newInputStream(filePath);
    BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
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
                case "sequent_amount_warning":
                    setting.setSequent_payment_warning(Integer.parseInt(value));
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
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(setting.getStartDate());
    calendar.add(Calendar.DAY_OF_MONTH, setting.getDuration());
    Date endDate = calendar.getTime();
    if (endDate.before(Calendar.getInstance().getTime())) {
        System.err.println("Budget expired, please set a new budget.");
    }
    br.close();
    return setting;
}
/**
 * @methodName writeSettingToFile
 * @description Write the Setting object to the data/settings. txt file.When the setting is modified, it should be called.
 * @param setting 
 * @throws IOException
 */
public static void writeSettingToFile(Setting setting) throws IOException {
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    // 获取资源文件的路径
    String projectRoot = System.getProperty("user.dir");
    // 构建完整文件路径
    String filePath = Paths.get(projectRoot, "demo","data", "setting.txt").toString();
    BufferedWriter bw = new BufferedWriter(new FileWriter(filePath));
    
    bw.write("budegt_ratewarning_low=" + setting.getBudegt_ratewarning_low());
    bw.newLine();
    bw.write("budegt_ratewarning_high=" + setting.getBudegt_ratewarning_high());
    bw.newLine();
    bw.write("budegt_ratewarning_max=" + setting.getBudegt_ratewarning_max());
    bw.newLine();
    bw.write("large_amount_warning=" + setting.getLarge_amount_warning());
    bw.newLine();
    bw.write("sequent_amount_warning=" + setting.getSequent_payment_warning());
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
