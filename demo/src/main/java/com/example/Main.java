package com.example;

import com.example.view.mainWindows;
import com.example.view.setBudget;
import com.example.view.importData;
import com.example.view.recordsView;
import com.example.view.happyFestival;
import com.example.view.aiAssistant;
import com.example.view.*;

import com.example.utils.RecordControl;
import com.example.utils.SettingControl;

import com.example.model.Record;
import com.example.model.Setting;
import com.example.service.*;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import javax.swing.JButton; // 新增导入
import javax.swing.SwingUtilities;

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
        } catch (IOException e) {
            setting = new Setting();
            System.out.println("No existing setting found, creating new setting.");
        }
        List<Record> resourceRecord = RecordControl.readRecordFromResource();
        originRecords = resourceRecord;

        initMainWindow(); // initialize mainWindows & build listeners
        mainFrame.setVisible(true); // run

        SwingUtilities.invokeLater(() -> {
            // logic to show happyFestival
            happyFestival.showReminder(LocalDate.now());
            // happyFestival.showReminder(LocalDate.of(2025, 2, 13)); // logic test
        });
    }

    /**
     * Page Navigation Logic for mainWindows
     */

    private static void initMainWindow() {
        mainFrame = new mainWindows(originRecords, setting);

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
            aiAssistant aiFrame = new aiAssistant(mainFrame);
            mainFrame.setVisible(false);
            aiFrame.setVisible(true);
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
            aiAssistant aiFrame = new aiAssistant(mainFrame);
            importFrame.setVisible(false);
            aiFrame.setVisible(true);
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
            aiAssistant aiFrame = new aiAssistant(mainFrame);
            budgetFrame.setVisible(false);
            aiFrame.setVisible(true);
        });
    }

    public static void initRecordsView() {
        recordsFrame = new recordsView(mainFrame, originRecords);

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

    /**
     * Add Logic to Show warning pup-up
     */
    private static void showWarningsIfNeeded() {
        List<Record> newRecords = importFrame.getNewRecords();
        List<Record> oldRecords = importFrame.getOldRecords();
        List<Record> records = RecordControl.readRecordFromResource();

        Map<String, Object> largeAmountResult = warning.large_amount_warning(setting, newRecords);
        Map<String, Object> sequentAmountResult = warning.sequent_amount_warning(setting, newRecords, oldRecords);
        Map<String, Object> sameAmountResult = warning.same_amount_warning(setting, newRecords, oldRecords);
        String nearBudgetResult = warning.budgetWarning(setting,
                budgetCount.calculateBudget(setting, records));

        if ("catch".equals(largeAmountResult.get("code"))) {
            List<Double> amountList = (List<Double>) largeAmountResult.get("amountList");
            for (Double amount : amountList) {
                largeRemittanceWarning.showWarning(amount);
            }
        }
        if ("catch".equals(sequentAmountResult.get("code"))) {
            frequentPaymentsWarning frequentPOP = new frequentPaymentsWarning();
            List<Record> sequentRecords = (List<Record>) sequentAmountResult.get("records");
            frequentPOP.showWarning(sequentRecords);

        }
        if ("catch".equals(sameAmountResult.get("code"))) {
            sameAmountWarning samePOP = new sameAmountWarning();
            List<Record> sameRecords = (List<Record>) sameAmountResult.get("records");
            samePOP.showWarning(sameRecords);
        }
        if ("max".equals(nearBudgetResult) || "high".equals(nearBudgetResult)) {
            double percentage = budgetCount.calculateBudget(setting, records);
            int duration = setting.getDuration();
            int is_Week_Month;
            if (duration == 7)
                is_Week_Month = 0;
            else
                is_Week_Month = 1;
            nearBudgetWarning.showWarning(is_Week_Month, percentage);
        }

    }
}
