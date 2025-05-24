package com.example.view;

import com.example.Main;
import com.example.view.mainWindows; // 正确导入mainWindows类
import com.example.model.Record;
import com.example.model.Setting;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import com.example.service.economySuggestion;

/**
 * The aiAssistant class defines a graphical interface for an AI Assistant
 * feature,
 * which provides economic suggestions based on the user's spending records.
 */
public class aiAssistant extends JFrame {
    private mainWindows mainFrame;
    private JTextArea outputArea;
    private RoundButton btnHomePage;
    private JButton btnRecordsView;
    private JButton btnAIAssistant;

    /**
     * Constructs the AI Assistant window.
     *
     * @param mainFrame Reference to the main application window to allow navigation
     *                  and access to data.
     */
    public aiAssistant(mainWindows mainFrame) {
        this.mainFrame = mainFrame;

        setTitle("AI Assistant");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // 标题区域
        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));

        // 主标题
        JLabel titleLabel = new JLabel("Welcome to AI assistant！");
        titleLabel.setFont(new Font("Serif", Font.BOLD, 30));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // 副标题
        JLabel subTitleLabel = new JLabel("Please choose one feature:");
        subTitleLabel.setFont(new Font("Serif", Font.PLAIN, 20));
        subTitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        titlePanel.add(Box.createVerticalStrut(15)); // 上边距增加到15px
        titlePanel.add(titleLabel);
        titlePanel.add(Box.createVerticalStrut(15)); // 主副标题间距增加到15px
        titlePanel.add(subTitleLabel);
        titlePanel.add(Box.createVerticalStrut(15)); // 下边距增加到15px

        add(titlePanel, BorderLayout.NORTH);

        // Center Panel
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.add(Box.createVerticalGlue());

        // Function Buttons
        JButton btnMonthly = createFunctionButton("Monthly Budget");
        JButton btnSaving = createFunctionButton("Saving Goal");
        JButton btnCostCut = createFunctionButton("Cost-cutting");

        centerPanel.add(btnMonthly);
        centerPanel.add(Box.createVerticalStrut(20));
        centerPanel.add(btnSaving);
        centerPanel.add(Box.createVerticalStrut(20));
        centerPanel.add(btnCostCut);
        centerPanel.add(Box.createVerticalGlue());

        // Output Area
        outputArea = new JTextArea(5, 20);
        outputArea.setEditable(false);
        outputArea.setFont(new Font("Serif", Font.PLAIN, 16));
        JScrollPane scrollOutput = new JScrollPane(outputArea);
        scrollOutput.setPreferredSize(new Dimension(600, 150));

        JPanel outputPanel = new JPanel();
        outputPanel.add(scrollOutput);

        // Combine components
        JPanel mainCenter = new JPanel(new BorderLayout());
        mainCenter.add(centerPanel, BorderLayout.CENTER);
        mainCenter.add(outputPanel, BorderLayout.SOUTH);
        add(mainCenter, BorderLayout.CENTER);

        // Bottom Buttons
        JPanel bottomPanel = createBottomPanel();
        add(bottomPanel, BorderLayout.SOUTH);

        // Button Listeners
        String monthlyAnalysis = "Monthly Budget Analysis: \n- Total Budget: ￥5000\n- Remaining: ￥3200";
        String savingProgress = "Saving Goal Progress: \n- Target: ￥20000\n- Achieved: ￥12500";
        String costCutSuggestions = "Cost-cutting Suggestions: \n1. Reduce dining out\n2. Cancel unused subscriptions";

        // 修改后的Button Listeners

        btnMonthly.addActionListener(e -> {
            List<Record> records = mainFrame.getRecords(); // 从主窗口中获取记录
            String suggestion = economySuggestion.BudgetSuggestion(records, 30); // 获取近30天预算建议
            outputArea.setText(suggestion);
        });

        btnSaving.addActionListener(e -> {
            List<Record> records = mainFrame.getRecords();
            String suggestion = economySuggestion.SavingsSuggestion(records, 30); // 获取近30天储蓄建议
            outputArea.setText(suggestion);
        });

        btnCostCut.addActionListener(e -> {
            List<Record> records = mainFrame.getRecords();
            String suggestion = economySuggestion.ExpensesSuggestion(records, 30); // 获取近30天消费建议
            outputArea.setText(suggestion);
        });

    }

    /**
     * Creates a styled feature button.
     *
     * @param text The label text for the button.
     * @return A JButton instance with standardized style.
     */
    private JButton createFunctionButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Serif", Font.BOLD, 20));
        btn.setPreferredSize(new Dimension(250, 40));
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        return btn;
    }

    /**
     * Creates the bottom navigation panel with Home, Records View, and AI Assistant
     * buttons.
     *
     * @return JPanel containing the navigation buttons.
     */
    private JPanel createBottomPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setPreferredSize(new Dimension(800, 120));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 5, 0, 5); // 按钮间距减少到10px
        gbc.weightx = 0.5; // 调整权重分配
        gbc.fill = GridBagConstraints.NONE;

        btnAIAssistant = new JButton("AI Assistant");
        styleNavButton(btnAIAssistant);

        btnHomePage = new RoundButton("Homepage");

        btnRecordsView = new JButton("Records View");
        styleNavButton(btnRecordsView);

        // 添加按钮时设置权重
        gbc.gridx = 0;
        gbc.weightx = 0.3; // 分配30%宽度
        panel.add(btnAIAssistant, gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.4; // 中间按钮分配40%宽度
        panel.add(btnHomePage, gbc);

        gbc.gridx = 2;
        gbc.weightx = 0.3; // 右侧按钮分配30%宽度
        panel.add(btnRecordsView, gbc);

        // // 添加按钮监听器
        // btnHomePage.addActionListener(e -> {
        // this.setVisible(false);
        // mainFrame.setVisible(true); // 返回主页面
        // });

        // btnRecordsView.addActionListener(e -> {
        // Main.initRecordsView(); // 初始化记录视图
        // this.setVisible(false);
        // Main.recordsFrame.setVisible(true); // 跳转
        // });

        return panel;
    }

    /**
     * Applies consistent styling to navigation buttons.
     *
     * @param btn The button to style.
     */
    private void styleNavButton(JButton btn) {
        btn.setFont(new Font("Serif", Font.BOLD, 18));

        // 调整按钮尺寸和边距
        btn.setPreferredSize(new Dimension(140, 40)); // 宽度增加20px
        btn.setMargin(new Insets(2, 5, 2, 5)); // 左右边距减少10px

        // 确保文本居中
        btn.setHorizontalTextPosition(SwingConstants.CENTER);
        btn.setVerticalTextPosition(SwingConstants.CENTER);

        // 其他样式设置
        btn.setBackground(null);
        btn.setOpaque(true);
        btn.setBorderPainted(true);
    }

    /**
     * RoundButton is a custom JButton with a circular appearance.
     */
    static class RoundButton extends JButton {
        public RoundButton(String text) {
            super(text);
            setContentAreaFilled(false);
            setFocusPainted(false);
            setBorderPainted(false);
            setFont(new Font("Serif", Font.BOLD, 20));
            setPreferredSize(new Dimension(140, 40));
            setVerticalTextPosition(SwingConstants.CENTER);
            setHorizontalTextPosition(SwingConstants.CENTER);
            setBackground(Color.WHITE);
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
        }
    }

    /*
     * Getters for Buttons
     */
    /**
     * Returns the homepage button.
     *
     * @return JButton instance for navigating to the homepage.
     */
    public JButton getBtnHomePage() {
        return btnHomePage;
    }

    /**
     * Returns the records view button.
     *
     * @return JButton instance for navigating to the records view.
     */
    public JButton getBtnRecordsView() {
        return btnRecordsView;
    }

}
