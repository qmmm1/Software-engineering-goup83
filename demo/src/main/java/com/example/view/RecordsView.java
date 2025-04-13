package com.example.view;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class recordsView extends JFrame {
    private JButton btnHomePage; // 确保有一个引用指向按钮
    private mainWindows mainFrame; // 引用主窗口实例

    private final List<String> allRecords = new ArrayList<>(); // 所有记录数据
    private int currentPage = 0; // 当前页数
    private static final int RECORDS_PER_PAGE = 10; // 每页显示的记录条数

    private JPanel recordsPanel; // 用来显示记录的面板
    private JScrollPane scrollPane; // 用来包裹记录面板的滚动面板

    // 修改构造函数，接收主窗口实例
    public recordsView(mainWindows mainFrame) {
        this.mainFrame = mainFrame; // 保持对主窗口实例的引用

        setTitle("Records View");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // 生成测试记录，模拟100条记录
        for (int i = 1; i <= 100; i++) {
            allRecords.add(String.format(
                    "   At 10:00 on 9 April 2025, you paid ￥%d to Category X, on category Category Y", i * 100));
        }

        // 创建顶部标签
        JLabel titleLabel = new JLabel("Your records view", JLabel.CENTER);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 24));
        add(titleLabel, BorderLayout.NORTH);

        // 创建记录面板，使用BoxLayout来使记录居中显示
        recordsPanel = new JPanel();
        recordsPanel.setLayout(new BoxLayout(recordsPanel, BoxLayout.Y_AXIS)); // 每条记录按纵向排列

        // 使用滚动面板来包含记录
        scrollPane = new JScrollPane(recordsPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS); // 始终显示垂直滚动条
        add(scrollPane, BorderLayout.CENTER);

        // 创建底部按钮面板
        JPanel bottomButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton btnAIAssistant = new JButton("AI Assistant");
        btnHomePage = new JButton("Homepage");
        JButton btnRecordsView = new JButton("Records View");

        // 设置按钮样式
        btnAIAssistant.setPreferredSize(new Dimension(200, 60));
        btnHomePage.setPreferredSize(new Dimension(200, 60));
        btnRecordsView.setPreferredSize(new Dimension(200, 60));

        // 为主页按钮添加事件
        btnHomePage.addActionListener(e -> {
            // 跳转回主页，不创建新的实例
            JOptionPane.showMessageDialog(this, "Returning to homepage...");
            setVisible(false); // 关闭当前窗口
            mainFrame.setVisible(true); // 显示主窗口
        });

        // 为AI Assistant按钮添加事件
        btnAIAssistant.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Opening AI Assistant...");
            // 可以添加 AI Assistant 页面的跳转逻辑
        });

        // 为Records View按钮添加事件
        btnRecordsView.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "You are already on the Records View page.");
        });

        // 按钮顺序修改为 AI Assistant, Homepage, Records View
        bottomButtonPanel.add(btnAIAssistant);
        bottomButtonPanel.add(btnHomePage);
        bottomButtonPanel.add(btnRecordsView);

        add(bottomButtonPanel, BorderLayout.SOUTH);

        // 初始加载10条记录
        updateRecordsPanel(RECORDS_PER_PAGE);

        // 添加滚动条监听
        scrollPane.getVerticalScrollBar().addAdjustmentListener(e -> {
            if (scrollPane.getVerticalScrollBar().getValue() + scrollPane.getVerticalScrollBar()
                    .getVisibleAmount() >= scrollPane.getVerticalScrollBar().getMaximum()) {
                loadNextPage(); // 滚动到底部时加载更多记录
            }
        });
    }

    // 更新当前页面的记录显示
    private void updateRecordsPanel(int recordCount) {
        // 清空记录面板
        recordsPanel.removeAll();

        // 设置字体大小
        Font recordFont = new Font("Serif", Font.PLAIN, 22); // 设置字体为Serif，大小为16

        // 填充当前页的记录
        for (int i = 0; i < recordCount && i < allRecords.size(); i++) {
            // 增加空白标签以增加上下间距
            recordsPanel.add(Box.createVerticalStrut(22)); // 记录前方的间距

            // 创建记录标签
            JLabel recordLabel = new JLabel(allRecords.get(i), JLabel.CENTER);
            recordLabel.setFont(recordFont); // 设置字体
            recordsPanel.add(recordLabel);

            // 添加横线分割
            JSeparator separator = new JSeparator();
            separator.setPreferredSize(new Dimension(800, 2)); // 横线宽度和高度
            recordsPanel.add(separator); // 将分隔线加入到面板中

            // 增加空白标签以增加上下间距
            recordsPanel.add(Box.createVerticalStrut(10)); // 记录下方的间距
        }

        // 强制重新布局和重绘
        recordsPanel.revalidate();
        recordsPanel.repaint();
    }

    // 加载下一页的记录
    private void loadNextPage() {
        // 如果还有更多记录，加载下一页
        if ((currentPage + 1) * RECORDS_PER_PAGE < allRecords.size()) {
            currentPage++;
            // 只加载10条记录，并保证页面最大显示10条
            updateRecordsPanel(Math.min((currentPage + 1) * RECORDS_PER_PAGE, allRecords.size()));
        }
    }

    // Getter method for Homepage button
    public JButton getBtnHomePage() {
        return btnHomePage;
    }
}
