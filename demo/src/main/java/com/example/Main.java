package com.example;

import com.example.view.mainWindows;
import com.example.view.setBudget;
import com.example.view.importData;
import com.example.view.recordsView;
import java.io.IOException;
import java.util.List;

import javax.swing.JButton; // 新增导入
import com.example.utils.RecordControl;
import com.example.utils.SettingControl;
import com.example.model.Record;
import com.example.model.Setting;

public class Main {
    private static mainWindows mainFrame;
    private static importData importFrame;
    private static setBudget budgetFrame;
    public static recordsView recordsFrame;
    private static List<Record> originRecords;
    private static Setting setting;
    

    public static void main(String[] args) {
    	
    	try {
    	    setting = SettingControl.readSettingFromFile();
            System.out.println("1");
    	} catch (IOException e) {
    	    setting = new Setting();
    	    System.out.println("No existing setting found, creating new setting.");
    	}
        List<Record> resourceRecord = RecordControl.readRecordFromResource();
        originRecords = resourceRecord;
        initMainWindow(); // initialize mainWindows & build listeners
        mainFrame.setVisible(true); // run
    }

    /**
     * Page Navigation Logic for mainWindows
     */

    private static void initMainWindow() {
        mainFrame = new mainWindows(originRecords,setting);

        // main -> importData
        mainFrame.getBtnImportData().addActionListener(e -> {
            if (importFrame == null)
                initImportData();
            mainFrame.setVisible(false);
            importFrame.setVisible(true);
        });

        // main -> setBudget
        mainFrame.getBtnBudget().addActionListener(e -> {
            if (budgetFrame == null)
                initBudgetFrame();
            mainFrame.setVisible(false);
            budgetFrame.setVisible(true);
        });

        // main -> recordsView
        mainFrame.getBtnRecordsView().addActionListener(e -> {
                initRecordsView();
            mainFrame.setVisible(false);
            recordsFrame.setVisible(true);
        });

        // main -> aiAssistant
        mainFrame.getBtnAIAssistant().addActionListener(e -> {
            // TODO: 实现 AI Assistant 页面
        });
    }

    /**
     * Page Navigation Logic for importData
     */

    private static void initImportData() {
        importFrame = new importData(originRecords);

        // importData -> main
        importFrame.getBtnHomepage().addActionListener(e -> {
            importFrame.setVisible(false);
            mainFrame.setVisible(true);
        });

        // importData -> recordsView
        importFrame.getBtnRecordsView().addActionListener(e -> {
                initRecordsView();
            importFrame.setVisible(false);
            recordsFrame.setVisible(true);
        });

        // importData -> aiAssistant
        importFrame.getBtnAIAssistant().addActionListener(e -> {
            // TODO: 添加 AI Assistant 页面跳转
        });
    }

    /**
     * Page Navigation Logic for setBudget
     */

    private static void initBudgetFrame() {
        budgetFrame = new setBudget(setting);

        // budget -> main
        budgetFrame.getBtnHomepage().addActionListener(e -> {
            budgetFrame.setVisible(false);
            mainFrame.setVisible(true);
        });

        // budget -> recordsView
        budgetFrame.getBtnRecordsView().addActionListener(e -> {
                initRecordsView();
            budgetFrame.setVisible(false);
            recordsFrame.setVisible(true);
        });

        // budget -> aiAssistant
        budgetFrame.getBtnAIAssistant().addActionListener(e -> {
            // TODO: 添加 AI Assistant 页面跳转
        });
    }

    public static void initRecordsView() {
    	recordsFrame= new recordsView(mainFrame,originRecords );

        recordsFrame.getBtnHomepage().addActionListener(e -> {
            recordsFrame.setVisible(false);
            mainFrame.setVisible(true);
        });
    }

    public static void updateBudgetButtonText(double userBudget) {
        if (mainFrame != null) {
            mainFrame.getBtnBudget().setText("<html>Budget<br><center>" + userBudget + "</center></html>");
            mainFrame.updateExpenseBudgetDisplay(); // 假设当前支出为 1000，需替换为实际值
        }
    }
}
