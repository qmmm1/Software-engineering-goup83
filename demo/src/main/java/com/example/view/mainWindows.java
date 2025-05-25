package com.example.view;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

import com.example.service.budgetCount;
import com.example.service.categoryPercentage;

import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import javax.swing.*;
import java.awt.*;
import java.util.Map;
import java.util.List;
import com.example.model.Record;
import com.example.utils.RecordControl;
import com.example.model.Setting;

/**
 * Main GUI window for the expense tracking application.
 * This class displays today's expenses, budget information, pie charts of
 * categorized expenses,
 * and various control buttons for navigating and interacting with the
 * application.
 */
public class mainWindows extends JFrame {
    private DefaultPieDataset dataset;
    private JProgressBar progressBar;
    private double today_expense = 100; // 测试变量
    private JButton btnImportData; // 声明按钮变量
    private JButton btnRecordsView; // 声明 Records View 按钮
    private JButton btnAIAssistant;
    private JButton btnBudget;
    private List<Record> records; // 原始数据集
    private Setting setting; // 全局设置

    /**
     * A custom JButton implementation that displays as a round button.
     * This button is visually distinct from standard rectangular buttons and
     * supports
     * enhanced font styling and sizing. It is useful for navigation or emphasis in
     * GUI applications.
     */
    static class RoundButton extends JButton {
        /**
         * Constructs a RoundButton with the specified text label.
         * The button has a circular appearance, no default border, and increased font
         * size.
         *
         * @param text the text to display on the button
         */
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

        /**
         * Paints the circular background of the button.
         * This overrides the default rectangular painting behavior to draw a filled
         * oval.
         *
         * @param g the Graphics object used for painting
         */
        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(getBackground());
            g2.fillOval(0, 0, getWidth(), getHeight());
            super.paintComponent(g2);
            g2.dispose();
        }

        /**
         * Overrides the default border painting to do nothing.
         * This ensures the button retains its round, borderless style.
         *
         * @param g the Graphics object used for painting
         */
        @Override
        protected void paintBorder(Graphics g) {
            // 不绘制默认边框
        }
    }

    /**
     * Constructs the main window of the application with the given records and
     * settings.
     *
     * @param records the list of financial records to display
     * @param setting the global setting configuration
     */
    public mainWindows(List<Record> records, Setting setting) {
        this.records = records;
        this.setting = setting;
        setTitle("记账软件");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.weightx = 1;
        gbc.weighty = 0;

        // 顶部按钮面板
        // 顶部按钮面板（改用GridBagLayout）
        JPanel topPanel = new JPanel(new GridBagLayout()); // 修改点：FlowLayout -> GridBagLayout
        topPanel.setPreferredSize(new Dimension(800, 100));

        // 创建约束对象
        GridBagConstraints topGbc = new GridBagConstraints();
        topGbc.fill = GridBagConstraints.BOTH; // 允许按钮填充空间
        topGbc.insets = new Insets(5, 20, 5, 20); // 设置左右间距为20
        topGbc.weightx = 1.0; // 水平均匀分配权重
        topGbc.gridy = 0; // 所有按钮位于第一行

        // 创建按钮（保持原有逻辑）
        JButton btnTodayExpense = createButton("Today Expense", categoryPercentage.getDailyAmountSum(records) + " yuan");
        btnBudget = createButton("Budget", "+");
        btnImportData = createButton("Import Data", "");

        // 依次添加三个按钮到不同列
        topGbc.gridx = 0; // 第一列
        topPanel.add(btnTodayExpense, topGbc);

        topGbc.gridx = 1; // 第二列
        topPanel.add(btnBudget, topGbc);

        topGbc.gridx = 2; // 第三列
        topPanel.add(btnImportData, topGbc);

        // 主布局添加topPanel（保持原有约束）
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.weighty = 0.1;
        add(topPanel, gbc);

        // 初始化数据集
        dataset = new DefaultPieDataset();
        updateChart();

        // 中间面板
        JPanel centerPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbcCenter = new GridBagConstraints();
        gbcCenter.fill = GridBagConstraints.BOTH;
        gbcCenter.insets = new Insets(5, 5, 5, 5);
        gbcCenter.weightx = 1;
        gbcCenter.weighty = 0.7; // 增加中间面板的权重

        // 图例
        JPanel legendPanel = new JPanel(new GridLayout(2, 5, 10, 10));
        legendPanel.setBackground(Color.WHITE);

        Map<String, Double> categoryCounts = categoryPercentage.getCategoryCounts(records, 30);
        String[] categories = categoryCounts.keySet().toArray(new String[0]);
        double[] categoryValues = categoryCounts.values().stream().mapToDouble(Double::doubleValue).toArray();
        Color[] colors = { Color.RED, Color.BLUE, Color.GREEN, Color.ORANGE, Color.LIGHT_GRAY, Color.PINK };

        double total = 0;
        for (double value : categoryValues) {
            total += value;
        }

        for (int i = 0; i < categories.length; i++) {
            double percentage = (categoryValues[i] / total) * 100;
            JLabel categoryLabel = new JLabel(categories[i] + ": " + String.format("%.2f", percentage) + "%",
                    JLabel.CENTER);
            categoryLabel.setBackground(colors[i]);
            legendPanel.add(categoryLabel);
        }

        gbcCenter.gridx = 0;
        gbcCenter.gridy = 1;
        gbcCenter.gridwidth = 1;
        centerPanel.add(legendPanel, gbcCenter);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.weighty = 0.7; // 增加中间面板的权重
        add(centerPanel, gbc);

        // 底部面板
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        bottomPanel.setPreferredSize(new Dimension(800, 100));

        progressBar = new JProgressBar(0, 100);
        progressBar.setValue(0); // 设置进度条的初始值为0
        progressBar.setStringPainted(true); // 显示进度条的文本
        progressBar.setPreferredSize(new Dimension(600, 25)); // 拉长进度条

        bottomPanel.add(progressBar);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.weighty = 0.1; // 增加底部面板的权重
        add(bottomPanel, gbc);

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

        // 创建按钮（增大尺寸）
        btnAIAssistant = new JButton("AI Assistant");
        RoundButton btnHomepage = new RoundButton("Homepage");
        btnRecordsView = new JButton("Records View"); // 初始化 Records View 按钮

        // 设置按钮点击事件
        btnHomepage.addActionListener(e -> {
            // 添加具体的点击处理逻辑
            //JOptionPane.showMessageDialog(this, "已返回主界面");
        });

        // 统一按钮样式
        btnAIAssistant.setPreferredSize(new Dimension(200, 60)); // 增大尺寸
        btnRecordsView.setPreferredSize(new Dimension(200, 60));
        btnAIAssistant.setFont(new Font("Serif", Font.BOLD, 18)); // 原为16
        btnRecordsView.setFont(new Font("Serif", Font.BOLD, 18));

        btnHomepage.setBackground(Color.WHITE);
        btnHomepage.setForeground(Color.BLACK);

        // 添加按钮到面板
        btnGbc.gridx = 0;
        bottomButtonPanel.add(btnAIAssistant, btnGbc);
        btnGbc.gridx = 1;
        bottomButtonPanel.add(btnHomepage, btnGbc);
        btnGbc.gridx = 2;
        bottomButtonPanel.add(btnRecordsView, btnGbc);

        // ===== 布局调整 =====
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weighty = 0.15; // 减少空间分配
        gbc.anchor = GridBagConstraints.SOUTH;
        gbc.insets = new Insets(5, 0, 5, 0); // 减少底部边距
        add(bottomButtonPanel, gbc);

        // 初始化进度条的值和文本
        updateExpenseBudgetDisplay(); // 实际值应从数据源获取

        setVisible(true);
    }

    /**
     * Creates a custom-styled JButton with main and sub text stacked vertically.
     *
     * @param mainText the main label of the button
     * @param subText  the sub-label displayed below the main label
     * @return a customized JButton
     */
    private JButton createButton(String mainText, String subText) {
        JButton button = new JButton("<html>" + mainText + "<br><center>" + subText + "</center></html>");
        button.setFont(new Font("Serif", Font.BOLD, 18));
        button.setPreferredSize(new Dimension(180, 60)); // 设置按钮的大小
        return button;
    }

    private ChartPanel currentChartPanel; // 添加类成员变量保存当前图表

    /**
     * Updates the pie chart to reflect the latest category distribution of
     * expenses.
     */
    private void updateChart() {
        // 移除旧的图表
        if (currentChartPanel != null) {
            getContentPane().remove(currentChartPanel);
        }

        Map<String, Double> categoryCounts = categoryPercentage.getCategoryCounts(records, 300);
        dataset.clear(); // 清空旧数据
        categoryCounts.forEach(dataset::setValue);

        JFreeChart chart = ChartFactory.createPieChart(
                "Proportion of Expenses by Category",
                dataset,
                true,
                true,
                false // 这个参数确保图例不会被创建
        );
        PiePlot plot = (PiePlot) chart.getPlot();
        plot.setLabelGenerator(new StandardPieSectionLabelGenerator("{0} = {1} ({2})"));

        // 确保图例不会被显示
        chart.getLegend().setVisible(false);

        // 如果图例仍然存在，尝试从图表中移除它
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setMouseWheelEnabled(true);
        chartPanel.setPreferredSize(new Dimension(500, 500));

        // 使用与初始布局一致的约束参数
        GridBagConstraints gbcChart = new GridBagConstraints();
        gbcChart.gridx = 0;
        gbcChart.gridy = 1;
        gbcChart.weightx = 1.0;
        gbcChart.weighty = 0.7; // 与初始中间面板权重一致
        gbcChart.fill = GridBagConstraints.BOTH;
        gbcChart.insets = new Insets(5, 5, 5, 5);

        getContentPane().add(chartPanel, gbcChart);
        revalidate(); // 强制布局更新
        repaint(); // 重绘界面
    }

    // 修改后的方法（移除 budget 参数）
    /**
     * Updates the progress bar to show the percentage of expenses relative to the
     * budget.
     * This method uses the budget calculation logic to determine the current ratio.
     */
    public void updateExpenseBudgetDisplay() {
        double percentage = budgetCount.calculateBudget(setting, records);
        progressBar.setValue((int) percentage);
        progressBar.setString("Expense/Budget : " + String.format("%.2f", percentage) + "%");

    }

    // 提供获取按钮的方法
    /**
     * Returns the "Import Data" button for adding event listeners externally.
     *
     * @return the import data button
     */
    public JButton getBtnImportData() {
        return btnImportData;
    }

    /**
     * Returns the "Records View" button for navigation to detailed record view.
     *
     * @return the records view button
     */
    public JButton getBtnRecordsView() {
        return btnRecordsView;
    }

    /**
     * Returns the "Budget" button for accessing budget setting functionality.
     *
     * @return the budget button
     */
    public JButton getBtnBudget() {
        return btnBudget;
    }

    /**
     * Returns the "AI Assistant" button for interacting with the AI assistant
     * feature.
     *
     * @return the AI assistant button
     */
    public JButton getBtnAIAssistant() {
        return btnAIAssistant;
    }

    /**
     * Returns the current list of financial records used in the view.
     *
     * @return list of records
     */
    public List<Record> getRecords() {
        return records;
    }

    // 在 mainWindows.java 中确认 refreshAll 方法包含以下逻辑
    /**
     * Reloads the financial records from the CSV resource and refreshes all visual
     * components,
     * including the pie chart and the progress bar.
     */
    public void refreshAll() {
        this.records = RecordControl.readRecordFromResource(); // 从CSV重新加载数据
        updateChart(); // 更新饼图
        updateExpenseBudgetDisplay(); // 更新进度条
        revalidate();
        repaint();
    }

    /**
     * Reloads records from CSV and updates only the progress bar.
     * Useful for scenarios where records are modified elsewhere and progress needs
     * reflecting.
     */
    public void refreshProgressBar() {
        // 强制重新从CSV加载最新数据（确保包含recordView的修改）
        this.records = RecordControl.readRecordFromResource();
        updateExpenseBudgetDisplay();
    }

}