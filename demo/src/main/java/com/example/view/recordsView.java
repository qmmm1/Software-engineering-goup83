package com.example.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class recordsView extends JFrame {
    private mainWindows mainFrame;
    private JButton btnAIAssistant;
    private JButton btnRecordsView;
    private JButton btnHomePage;  // 新增成员变量

    private final List<String> allRecords = new ArrayList<>();
    private JPanel recordsPanel;
    private JScrollPane scrollPane;

    // 类别选项
    private String[] categoryOptions = {"Category 1", "Category 2", "Category 3", "Category 4", "Category 5", "Others"};

    public recordsView(mainWindows mainFrame) {
        this.mainFrame = mainFrame;

        setTitle("Records View");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        btnHomePage = new RoundButton("Homepage");  // 原局部变量改为成员变量

        // 初始化所有记录
        for (int i = 1; i <= 100; i++) {
            allRecords.add(String.format(
                    "At 10:00 on 9 April 2025, you paid ￥%d to Recipient %d, on category Category %d", i * 100, i, i));
        }

        JLabel titleLabel = new JLabel("Your records view", JLabel.CENTER);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 24));
        add(titleLabel, BorderLayout.NORTH);

        recordsPanel = new JPanel();
        recordsPanel.setLayout(new BoxLayout(recordsPanel, BoxLayout.Y_AXIS));
        scrollPane = new JScrollPane(recordsPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        add(scrollPane, BorderLayout.CENTER);

        JPanel bottomButtonPanel = new JPanel(new GridBagLayout()) {
            @Override
            public Dimension getPreferredSize() {
                return new Dimension(800, 120);
            }
        };
        GridBagConstraints btnGbc = new GridBagConstraints();
        btnGbc.insets = new Insets(0, 20, 0, 20);
        btnGbc.weightx = 1.0;
        btnGbc.fill = GridBagConstraints.NONE;

        btnAIAssistant = new JButton("AI Assistant");
        btnRecordsView = new JButton("Records View");

        btnAIAssistant.setPreferredSize(new Dimension(150, 30));
        btnRecordsView.setPreferredSize(new Dimension(150, 30));
        btnAIAssistant.setFont(new Font("Serif", Font.BOLD, 18));
        btnRecordsView.setFont(new Font("Serif", Font.BOLD, 18));

        btnHomePage.setBackground(Color.WHITE);
        btnHomePage.setForeground(Color.BLACK);

        btnHomePage.addActionListener(e -> {
            setVisible(false);
            mainFrame.setVisible(true);
        });

        btnGbc.gridx = 0;
        bottomButtonPanel.add(btnAIAssistant, btnGbc);
        btnGbc.gridx = 1;
        bottomButtonPanel.add(btnHomePage, btnGbc);
        btnGbc.gridx = 2;
        bottomButtonPanel.add(btnRecordsView, btnGbc);

        add(bottomButtonPanel, BorderLayout.SOUTH);

        // 加载所有记录
        loadAllRecords();

        // 设置滚动条的滚动速度
        JScrollBar verticalScrollBar = scrollPane.getVerticalScrollBar();
        verticalScrollBar.setUnitIncrement(20);  // 设置每次滚动的单位增量
        verticalScrollBar.setBlockIncrement(100);  // 设置每次滚动的块增量
    }

    private void loadAllRecords() {
        recordsPanel.removeAll();
        Font recordFont = new Font("Serif", Font.PLAIN, 18);

        for (String record : allRecords) {
            JPanel itemContainer = new JPanel();
            itemContainer.setLayout(new BoxLayout(itemContainer, BoxLayout.Y_AXIS));
            itemContainer.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

            JPanel textPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
            JLabel recordLabel = new JLabel(record);
            recordLabel.setFont(recordFont);
            textPanel.add(recordLabel);

            textPanel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    showRecordDetail(record);
                }
            });

            itemContainer.add(Box.createVerticalStrut(10));
            itemContainer.add(textPanel);
            itemContainer.add(Box.createVerticalStrut(10));
            itemContainer.add(new JSeparator());

            recordsPanel.add(itemContainer);
        }

        recordsPanel.revalidate();
        recordsPanel.repaint();
    }

    private void showRecordDetail(String record) {
        // 解析记录内容
        String[] parts = record.split(", you paid | to |, on category ");
        String timeAndDate = parts[0].replace("At ", "").trim();
        String amount = parts[1].replace("￥", "").trim();
        String recipient = parts[2].trim();
        String category = parts[3].trim();

        // 分割时间和日期
        String[] timeAndDateParts = timeAndDate.split(" on ");
        String time = timeAndDateParts[0];
        String date = timeAndDateParts[1];

        // 创建弹窗面板
        JPanel detailPanel = new JPanel(new GridLayout(5, 2)); // 修改为5行2列

        // 添加时间、日期、金额、收款方的文本框
        detailPanel.add(new JLabel("Time:"));
        JTextField timeField = new JTextField(time);
        detailPanel.add(timeField);

        detailPanel.add(new JLabel("Data:"));
        JTextField dateField = new JTextField(date);
        detailPanel.add(dateField);

        detailPanel.add(new JLabel("Amount of Money:"));
        JTextField amountField = new JTextField(amount);
        detailPanel.add(amountField);

        detailPanel.add(new JLabel("Recipient:"));
        JTextField recipientField = new JTextField(recipient);
        detailPanel.add(recipientField);

        detailPanel.add(new JLabel("Category:"));
        JComboBox<String> categoryComboBox = new JComboBox<>(categoryOptions); // 创建下拉选择框
        categoryComboBox.setSelectedItem(category); // 设置默认选中项
        detailPanel.add(categoryComboBox);

        // 显示弹窗
        String dialogTitle = "Records Details";

        Object[] options = {"OK", "Cancel"};
        int option = JOptionPane.showOptionDialog(
                this,
                detailPanel,
                dialogTitle,
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.PLAIN_MESSAGE,
                null,
                options,
                options[0]
        );
        if (option == 0) {
            String newTime = timeField.getText();
            String newDate = dateField.getText();
            String newAmount = amountField.getText();
            String newRecipient = recipientField.getText();
            String newCategory = (String) categoryComboBox.getSelectedItem();

            // 更新记录
            int index = allRecords.indexOf(record);
            if (index != -1) {
                allRecords.set(index, String.format(
                        "At %s on %s, you paid ￥%s to %s, on category %s",
                        newTime, newDate, newAmount, newRecipient, newCategory));
                loadAllRecords(); // 重新加载所有记录
            }
        }
    }

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

    public JButton getBtnHomepage() {
        return btnHomePage;
    }
}