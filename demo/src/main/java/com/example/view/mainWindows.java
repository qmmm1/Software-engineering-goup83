package com.example.view;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import javax.swing.*;
import java.awt.*;

public class mainWindows extends JFrame {
    private DefaultPieDataset dataset;
    private JProgressBar progressBar;
    private double today_expense = 100; // 测试变量
    private JButton btnImportData; // 声明按钮变量
    private JButton btnRecordsView; // 声明 Records View 按钮
    private JButton btnAIAssistant;
    private JButton btnBudget;

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

    public mainWindows() {
        setTitle("记账软件");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 1000);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.weightx = 1;
        gbc.weighty = 0;

        // 顶部按钮面板
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 80, 10)); // 增加水平和垂直间距
        topPanel.setPreferredSize(new Dimension(800, 100)); // 增加高度以适应两行文本

        JButton btnTodayExpense = createButton("Today Expense", String.valueOf(today_expense) + "元");
        btnBudget = createButton("Budget", "+");
        btnImportData = createButton("Import Data", ""); // 初始化按钮

        topPanel.add(btnTodayExpense);
        topPanel.add(btnBudget);
        topPanel.add(btnImportData);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.weighty = 0.1; // 增加顶部面板的权重
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

        String[] categories = { "Food costs", "Hospitalization costs", "Utilities costs", "Transportation costs",
                "Other" };
        double[] categoryValues = { 10, 50, 30, 15, 25 };
        Color[] colors = { Color.RED, Color.BLUE, Color.GREEN, Color.ORANGE, Color.LIGHT_GRAY };

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
        btnAIAssistant = new JButton("AI assistant");
        RoundButton btnHomepage = new RoundButton("Homepage");
        btnRecordsView = new JButton("Records View"); // 初始化 Records View 按钮

        // 设置按钮点击事件
        btnHomepage.addActionListener(e -> {
            // 添加具体的点击处理逻辑
            JOptionPane.showMessageDialog(this, "已返回主界面");
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
        updateExpenseBudgetDisplay(1000, 3000); // 示例赋值

        setVisible(true);
    }

    private JButton createButton(String mainText, String subText) {
        JButton button = new JButton("<html>" + mainText + "<br><center>" + subText + "</center></html>");
        button.setFont(new Font("Serif", Font.BOLD, 18));
        button.setPreferredSize(new Dimension(180, 60)); // 设置按钮的大小
        return button;
    }

    private void updateChart() {
        String[] categories = { "Food costs", "Hospitalization costs", "Utilities costs", "Transportation costs",
                "Other" };
        double[] categoryValues = { 10, 50, 30, 15, 25 };

        for (int i = 0; i < categories.length; i++) {
            dataset.setValue(categories[i], categoryValues[i]);
        }

        JFreeChart chart = ChartFactory.createPieChart("Proportion of Expenses by Category", dataset, true, true,
                false);
        PiePlot plot = (PiePlot) chart.getPlot();
        plot.setLabelGenerator(new StandardPieSectionLabelGenerator("{0} = {1} ({2})")); // 设置标签格式为百分比

        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(500, 500)); // 增加饼图的大小

        getContentPane().add(chartPanel, new GridBagConstraints(0, 1, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER,
                GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
        pack();
    }

    public void updateExpenseBudgetDisplay(double expense, double budget) {
        double percentage = (expense / budget) * 100;
        progressBar.setValue((int) percentage);
        progressBar.setString("Expense/Budget : " + String.format("%.2f", percentage) + "%");
    }

    // 提供获取按钮的方法
    public JButton getBtnImportData() {
        return btnImportData;
    }

    public JButton getBtnRecordsView() {
        return btnRecordsView;
    }

    public JButton getBtnBudget() {
        return btnBudget;
    }

    public JButton getBtnAIAssistant() {
        return btnAIAssistant;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new mainWindows());
    }
}
