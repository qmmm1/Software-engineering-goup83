package com.example;

import com.example.view.mainWindows;
import com.example.view.setBudget;
import com.example.view.importData;
import com.example.view.recordsView;

import java.util.List;

import javax.swing.JButton; // 新增导入
import com.example.utils.RecordControl;
import com.example.model.Record;

public class Main {
    private static mainWindows mainFrame;
    private static importData importFrame;
    private static setBudget budgetFrame;
    public static recordsView recordsFrame;
    private static List<Record> originRecords;

    public static void main(String[] args) {
        List<Record> resourceRecord = RecordControl.readRecordFromResource();
        originRecords = resourceRecord;
        initMainWindow(); // initialize mainWindows & build listeners
        mainFrame.setVisible(true); // run
    }

    /**
     * Page Navigation Logic for mainWindows
     */

    private static void initMainWindow() {
        mainFrame = new mainWindows();

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
            if (recordsFrame == null)
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
            if (recordsFrame == null)
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
        budgetFrame = new setBudget();

        // budget -> main
        budgetFrame.getBtnHomepage().addActionListener(e -> {
            budgetFrame.setVisible(false);
            mainFrame.setVisible(true);
        });

        // budget -> recordsView
        budgetFrame.getBtnRecordsView().addActionListener(e -> {
            if (recordsFrame == null)
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
            mainFrame.updateExpenseBudgetDisplay(1000, userBudget); // 假设当前支出为 1000，需替换为实际值
        }
    }
}
