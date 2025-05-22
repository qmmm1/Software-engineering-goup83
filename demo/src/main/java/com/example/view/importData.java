package com.example.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import com.example.utils.RecordControl;
import com.example.Main;
import com.example.model.Record;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.io.File;
import java.text.SimpleDateFormat;

public class importData extends JFrame {
    private JTextField largeAmountField;
    private JTextField frequentPaymentField;
    private JButton btnHomepage; // 声明按钮变量
    private JButton btnRecordsView;
    private JButton btnAIAssistant;
    private List<Record> records = new ArrayList<>();
    private String[] categoryOptions = {
            "food", "transportation", "entertainment", "education", "living expenses", "other"
    };

    // variables for warning pop up
    private List<Record> newRecords = new ArrayList<>();
    private List<Record> oldRecords = new ArrayList<>();

    public importData(List<Record> originRecords) {

        records = originRecords;
        oldRecords = new ArrayList<>(originRecords);
        Font largerFont = new Font("Serif", Font.PLAIN, 20);
        largeAmountField = new JTextField(20);
        largeAmountField.setFont(largerFont);

        frequentPaymentField = new JTextField(20);
        frequentPaymentField.setFont(largerFont);

        setTitle("Import Data");

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 600); // 调整页面大小为600x800
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(3, 5, 3, 5);
        gbc.weightx = 1;
        gbc.weighty = 0;

        // 导入CSV文件部分
        JLabel csvLabel = new JLabel("Import your CSV file below");
        csvLabel.setFont(largerFont); // 设置字体
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.weighty = 0.1;
        add(csvLabel, gbc);

        JButton csvButton = new JButton("+");
        csvButton.setFont(largerFont); // 设置字体
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.weighty = 0.1;
        add(csvButton, gbc);

        csvButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int result = fileChooser.showOpenDialog(importData.this);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    List<Record> imported = RecordControl.importRecordsFromCsv(selectedFile.getAbsolutePath());

                    if (imported != null && !imported.isEmpty()) {
                        records = imported; // 把新导入的记录替换原有列表
                        newRecords = getNewImport(oldRecords, records);
                        RecordControl.updateRecordsToCsv(records);
                        JOptionPane.showMessageDialog(importData.this,
                                "Successfully imported " + imported.size() + " records.",
                                "Import Success", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(importData.this,
                                "No records were imported. Please check your file.",
                                "Import Failed", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        // 添加支付历史记录部分
        JLabel paymentLabel = new JLabel("You can add payments history below:");
        paymentLabel.setFont(largerFont); // 设置字体

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.weighty = 0.1;
        add(paymentLabel, gbc);

        JButton paymentInfoButton = new JButton("Click to add payment info");
        paymentInfoButton.setFont(largerFont); // 设置字体
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        gbc.weighty = 0.1;
        add(paymentInfoButton, gbc);

        paymentInfoButton.addActionListener(e -> {
            // 输入字段
            JTextField timeField = new JTextField(5); // 时间：14:00
            JTextField dayField = new JTextField(2);
            JTextField monthField = new JTextField(2);
            JTextField yearField = new JTextField(4);
            JTextField amountField = new JTextField(10);
            JTextField payeeField = new JTextField(10);
            JComboBox<String> categoryComboBox = new JComboBox<>(categoryOptions);
            categoryComboBox.setSelectedIndex(5); // 默认选中第一项

            JPanel panel = new JPanel(new GridLayout(0, 2));
            panel.add(new JLabel("Hour:Minute (e.g. 14:00)"));
            panel.add(timeField);
            panel.add(new JLabel("Day"));
            panel.add(dayField);
            panel.add(new JLabel("Month"));
            panel.add(monthField);
            panel.add(new JLabel("Year"));
            panel.add(yearField);
            panel.add(new JLabel("Amount"));
            panel.add(amountField);
            panel.add(new JLabel("Payee"));
            panel.add(payeeField);
            // 修改面板中的组件：
            panel.add(new JLabel("Category"));
            panel.add(categoryComboBox); // 替换原来的 categoryField

            Object[] options = { "OK", "Cancel" }; // 自定义按钮文本
            int result = JOptionPane.showOptionDialog(
                    null,
                    panel,
                    "Add Payment Info",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    options, // 传入自定义按钮数组
                    options[0] // 默认选中第一个按钮（OK）
            );
            if (result == JOptionPane.YES_OPTION) {
                try {
                    String dateTimeStr = yearField.getText().trim() + "-" +
                            monthField.getText().trim() + "-" +
                            dayField.getText().trim() + " " +
                            timeField.getText().trim();

                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                    Date paymentDate = sdf.parse(dateTimeStr);

                    double amount = Double.parseDouble(amountField.getText().trim());
                    String payee = payeeField.getText().trim();
                    String category = categoryComboBox.getSelectedItem().toString();
                    // 调用后端添加记录
                    records = RecordControl.insertRecord(records, paymentDate, amount, category, payee);
                    newRecords = RecordControl.insertRecord(newRecords, paymentDate, amount, category, payee);
                    RecordControl.updateRecordsToCsv(records);
                    JOptionPane.showMessageDialog(this, "Payment added. Total records: " + records.size());

                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
                }
            }
        });

        // 大金额提醒部分
        JLabel largeAmountLabel = new JLabel(
                "Fill in the figure that you think is a large amount so that we can remind you");
        largeAmountLabel.setFont(largerFont);
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        gbc.weighty = 0.1;
        add(largeAmountLabel, gbc);

        // 创建包含文本框和按钮的面板
        JPanel largeAmountPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        largeAmountField = new JTextField(60); // 缩短文本框
        largeAmountPanel.add(largeAmountField);

        // 调整 Confirm 按钮高度与文本框一致，减小字体大小，并减小按钮内边距
        JButton confirmLargeAmount = new JButton("Confirm");
        confirmLargeAmount.setFont(new Font("Serif", Font.PLAIN, 18)); // 减小字体大小
        confirmLargeAmount.setPreferredSize(new Dimension(80, largeAmountField.getPreferredSize().height)); // 设置按钮高度与文本框一致
        confirmLargeAmount.setMargin(new Insets(1, 2, 1, 2)); // 减小按钮内边距（上下2像素，左右5像素）
        largeAmountPanel.add(confirmLargeAmount);

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 1;
        gbc.weighty = 0.1;
        add(largeAmountPanel, gbc); // 添加面板替代原文本框

        // 频繁支付提醒部分
        JLabel frequentPaymentLabel = new JLabel(
                "Fill in the figure that you think is the number of times you make payments too often so that we can remind you");
        frequentPaymentLabel.setFont(largerFont);
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 1;
        gbc.weighty = 0.1;
        add(frequentPaymentLabel, gbc);

        // 创建包含文本框和按钮的面板
        JPanel frequentPaymentPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        frequentPaymentField = new JTextField(60); // 缩短文本框
        frequentPaymentPanel.add(frequentPaymentField);

        // 调整 Confirm 按钮高度与文本框一致，减小字体大小，并减小按钮内边距
        JButton confirmFrequentPayment = new JButton("Confirm");
        confirmFrequentPayment.setFont(new Font("Serif", Font.PLAIN, 18)); // 减小字体大小
        confirmFrequentPayment.setPreferredSize(new Dimension(80, frequentPaymentField.getPreferredSize().height)); // 设置按钮高度与文本框一致
        confirmFrequentPayment.setMargin(new Insets(1, 2, 1, 2)); // 减小按钮内边距（上下2像素，左右5像素）
        frequentPaymentPanel.add(confirmFrequentPayment);

        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridwidth = 1;
        gbc.weighty = 0.1;
        add(frequentPaymentPanel, gbc); // 添加面板替代原文本框

        // 底部导航按钮
        JPanel bottomButtonPanel = new JPanel(new GridBagLayout()) {
            @Override
            public Dimension getPreferredSize() {
                return new Dimension(800, 120); // 增加面板高度
            }
        };
        GridBagConstraints btnGbc = new GridBagConstraints();
        btnGbc.insets = new Insets(0, 20, 0, 20); // 调整水平间距
        btnGbc.weightx = 1.0;
        btnGbc.fill = GridBagConstraints.NONE;

        btnAIAssistant = new JButton("AI Assistant");
        btnHomepage = new RoundButton("Homepage");
        btnRecordsView = new JButton("Records View");

        btnHomepage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 添加具体的点击处理逻辑
                JOptionPane.showMessageDialog(importData.this, "已返回主界面");
            }
        });

        btnAIAssistant.setPreferredSize(new Dimension(200, 60)); // 增大尺寸
        btnRecordsView.setPreferredSize(new Dimension(200, 60));
        btnAIAssistant.setFont(new Font("Serif", Font.BOLD, 18)); // 原为16
        btnRecordsView.setFont(new Font("Serif", Font.BOLD, 18));

        btnHomepage.setBackground(Color.WHITE);
        btnHomepage.setForeground(Color.BLACK);

        btnGbc.gridx = 0;
        bottomButtonPanel.add(btnAIAssistant, btnGbc);
        btnGbc.gridx = 1;
        bottomButtonPanel.add(btnHomepage, btnGbc);
        btnGbc.gridx = 2;
        bottomButtonPanel.add(btnRecordsView, btnGbc);

        gbc.gridx = 0;
        gbc.gridy = 8;
        gbc.weighty = 0.15; // 减少空间分配
        gbc.anchor = GridBagConstraints.SOUTH;
        gbc.insets = new Insets(5, 0, 5, 0); // 减少底部边距
        add(bottomButtonPanel, gbc);

        setVisible(true);
    }

    static class RoundButton extends JButton {
        public RoundButton(String text) {
            super(text);
            setContentAreaFilled(false);
            setFocusPainted(false);
            setBorderPainted(false);
            setFont(new Font("Serif", Font.BOLD, 20)); // 增大字号
            setPreferredSize(new Dimension(220, 120)); // 增大按钮尺寸
            setVerticalTextPosition(SwingConstants.CENTER);
            setHorizontalTextPosition(SwingConstants.CENTER);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(getBackground());
            g2.fillOval(0, 0, getWidth(), getHeight());
            super.paintComponent(g2);
            g2.dispose();
        }

        @Override
        protected void paintBorder(Graphics g) {
            // 不绘制默认边框
        }
    }

    // 提供获取按钮的方法
    public JButton getBtnHomepage() {
        return btnHomepage;
    }

    public JButton getBtnRecordsView() {
        return btnRecordsView;
    }

    public JButton getBtnAIAssistant() {
        return btnAIAssistant;
    }

    // methods for warning
    List<Record> getNewImport(List<Record> oldRecords, List<Record> records) {
        List<Record> newRecords = new ArrayList<>();
        int oldSize = oldRecords.size();
        if (records.size() > oldSize) {
            newRecords = records.subList(oldSize, records.size());
        }
        return new ArrayList<>(newRecords);
    }

    public List<Record> getNewRecords() {
        return newRecords;
    }

    public List<Record> getOldRecords() {
        return oldRecords;
    }

    public void clearNewRecords() {
        oldRecords.addAll(newRecords);
        newRecords.clear();
    }
}
