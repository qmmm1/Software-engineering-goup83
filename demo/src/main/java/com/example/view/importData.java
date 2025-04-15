package com.example.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class importData extends JFrame {
    private JTextField largeAmountField;
    private JTextField frequentPaymentField;
    private JButton btnHomepage; // 声明按钮变量
    private JButton btnRecordsView;
    private JButton btnAIAssistant;

    public importData() {
        Font largerFont = new Font("Serif", Font.PLAIN, 20);
        largeAmountField = new JTextField(20);
        largeAmountField.setFont(largerFont);
        largeAmountField.setPreferredSize(new Dimension(
                largeAmountField.getPreferredSize().width, // 保持原宽度
                30 // 增加高度到30像素
        ));

        frequentPaymentField = new JTextField(20);
        frequentPaymentField.setFont(largerFont);
        frequentPaymentField.setPreferredSize(new Dimension(
                frequentPaymentField.getPreferredSize().width,
                30));

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

        paymentInfoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JTextField timeField = new JTextField(10);
                JTextField amountField = new JTextField(10);
                JTextField recipientField = new JTextField(10);
                JTextField categoryField = new JTextField(10);
                JTextField dayField = new JTextField(10);
                JTextField monthField = new JTextField(10);
                JTextField yearField = new JTextField(10);

                JPanel panel = new JPanel(new GridLayout(4, 2, 5, 5));
                panel.add(new JLabel("At (e.g.  14:00)"));
                panel.add(timeField);
                panel.add(new JLabel("on Day "));
                panel.add(dayField);
                panel.add(new JLabel("Month "));
                panel.add(monthField);
                panel.add(new JLabel("Year "));
                panel.add(yearField);
                panel.add(new JLabel(", you paid ¥"));
                panel.add(amountField);
                panel.add(new JLabel("to "));
                panel.add(recipientField);
                panel.add(new JLabel(", on category "));
                panel.add(categoryField);

                int result = JOptionPane.showConfirmDialog(null, panel, "Payment Information",
                        JOptionPane.OK_CANCEL_OPTION);
                if (result == JOptionPane.OK_OPTION) {
                    String time = timeField.getText();
                    String day = dayField.getText();
                    String month = monthField.getText();
                    String year = yearField.getText();
                    String amt = amountField.getText();
                    String rec = recipientField.getText();
                    String cat = categoryField.getText();

                    JOptionPane.showMessageDialog(null, "Time: " + time + " " + day + "/" + month + "/" + year +
                            "\nAmount: " + amt + "\nRecipient: " + rec + "\nCategory: " + cat);
                }
            }
        });

        // 大金额提醒部分
        JLabel largeAmountLabel = new JLabel(
                "Fill in the figure that you think is a large amount so that we can remind you");
        largeAmountLabel.setFont(largerFont); // 设置字体
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        gbc.weighty = 0.1;
        add(largeAmountLabel, gbc);

        largeAmountField = new JTextField(20);
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 1;
        gbc.weighty = 0.1;
        add(largeAmountField, gbc);

        // 频繁支付提醒部分
        JLabel frequentPaymentLabel = new JLabel(
                "Fill in the figure that you think is the number of times you make payments too often so that we can remind you");
        frequentPaymentLabel.setFont(largerFont); // 设置字体
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 1;
        gbc.weighty = 0.1;
        add(frequentPaymentLabel, gbc);

        frequentPaymentField = new JTextField(20);
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridwidth = 1;
        gbc.weighty = 0.1;
        add(frequentPaymentField, gbc);

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

        btnAIAssistant = new JButton("AI assistant");
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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            importData frame = new importData();
        });
    }
}
