package com.example.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import java.util.ArrayList;
import java.util.Date;

import com.example.model.Record;
import com.example.utils.RecordControl;
import com.example.utils.AIcontrol;

/**
 * The recordsView class displays a detailed list of spending records.
 * It allows users to view, edit, delete, and classify records using an AI
 * assistant.
 * It is designed as a standalone JFrame with interactive UI components.
 */
public class recordsView extends JFrame {
    private mainWindows mainFrame;
    private JButton btnAIAssistant;
    private JButton btnRecordsView;
    private JButton btnHomePage;
    private final List<Record> recordList;

    private JPanel recordsPanel;
    private JScrollPane scrollPane;

    private String[] categoryOptions = {
            "food", "transportation", "entertainment", "education", "living expenses", "other"
    };

    /**
     * Constructs the recordsView window.
     *
     * @param mainFrame  The main window to return to.
     * @param recordList A list of records to display.
     */
    public recordsView(mainWindows mainFrame, List<Record> recordList) {
        this.mainFrame = mainFrame;
        this.recordList = (recordList != null) ? recordList : new ArrayList<>();

        setTitle("Records View");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        btnHomePage = new RoundButton("Homepage");

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

        loadRecordObjects();

        scrollPane.getVerticalScrollBar().setUnitIncrement(20);
        scrollPane.getVerticalScrollBar().setBlockIncrement(100);
    }

    /**
     * Loads the records into the recordsPanel for display.
     * Each record is shown with summary information and clickable for detailed
     * view.
     */
    private void loadRecordObjects() {
        recordsPanel.removeAll();
        Font recordFont = new Font("Serif", Font.PLAIN, 18);

        for (Record record : recordList) {
            String displayText = String.format(
                    Locale.ENGLISH,
                    "At %tR on %<te %<tB %<tY, you paid ￥%.2f to %s, on category %s",
                    record.getPaymentDate(), record.getAmount(), record.getPayee(), record.getCategory());

            JPanel itemContainer = new JPanel();
            itemContainer.setLayout(new BoxLayout(itemContainer, BoxLayout.Y_AXIS));
            itemContainer.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

            JPanel textPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
            JLabel recordLabel = new JLabel(displayText);
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

    /**
     * Opens a detailed dialog for a specific record.
     * Allows the user to view and modify the record details, including
     * reclassification and deletion.
     *
     * @param record The record to display in detail.
     */
    private void showRecordDetail(Record record) {
        // 使用 Locale.ENGLISH 格式化日期和时间
        String time = String.format(Locale.ENGLISH, "%tR", record.getPaymentDate());
        String date = String.format(Locale.ENGLISH, "%te %<tB %<tY", record.getPaymentDate());
        String amount = String.format("%.2f", record.getAmount());
        String recipient = record.getPayee();
        String category = record.getCategory();

        JPanel detailPanel = new JPanel(new GridLayout(5, 2));

        detailPanel.add(new JLabel("Time:"));
        JTextField timeField = new JTextField(time);
        detailPanel.add(timeField);

        detailPanel.add(new JLabel("Date:"));
        JTextField dateField = new JTextField(date);
        detailPanel.add(dateField);

        detailPanel.add(new JLabel("Amount of Money:"));
        JTextField amountField = new JTextField(amount);
        detailPanel.add(amountField);

        detailPanel.add(new JLabel("Recipient:"));
        JTextField recipientField = new JTextField(recipient);
        detailPanel.add(recipientField);

        detailPanel.add(new JLabel("Category:"));
        JComboBox<String> categoryComboBox = new JComboBox<>(categoryOptions);
        categoryComboBox.setSelectedItem(category);
        detailPanel.add(categoryComboBox);

        // 创建按钮面板
        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 10, 0));
        JButton aiButton = new JButton("AI classification");
        JButton deleteButton = new JButton("Delete");

        // AI分类按钮事件
        aiButton.addActionListener(e -> {
            AIcontrol.singalAIcategory(record);
            categoryComboBox.setSelectedItem(record.getCategory());
        });

        // 删除按钮事件
        deleteButton.addActionListener(e -> {
            recordList.remove(record);
            RecordControl.updateRecordsToCsv(recordList);
            loadRecordObjects();
            ((JDialog) SwingUtilities.getWindowAncestor(detailPanel)).dispose();
        });

        buttonPanel.add(aiButton);
        buttonPanel.add(deleteButton);

        // 创建包含垂直间距和按钮的面板
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.Y_AXIS));
        bottomPanel.add(Box.createVerticalStrut(25)); // 增大垂直间距至25像素
        bottomPanel.add(buttonPanel);

        // 主面板布局
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(detailPanel, BorderLayout.CENTER);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH); // 将组合后的面板放在SOUTH区域

        int option = JOptionPane.showOptionDialog(
                this,
                mainPanel,
                "Record Details",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.PLAIN_MESSAGE,
                null,
                new String[] { "OK", "Cancel" },
                "OK");

        if (option == 0 && recordList.contains(record)) {
            try {
                String newTime = timeField.getText();
                String newDate = dateField.getText();
                String dateTimeStr = newTime + " " + newDate;

                // 确保解析时的格式与显示的格式一致
                SimpleDateFormat formatter = new SimpleDateFormat("HH:mm d MMMM yyyy", Locale.ENGLISH);
                Date parsedDate = formatter.parse(dateTimeStr);

                record.setPaymentDate(parsedDate);
                record.setAmount(Double.parseDouble(amountField.getText()));
                record.setPayee(recipientField.getText());
                record.setCategory((String) categoryComboBox.getSelectedItem());

                loadRecordObjects(); // 刷新显示
                RecordControl.updateRecordsToCsv(recordList);
                mainFrame.refreshAll();
            } catch (ParseException | NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Failed to update record. Check your input format.");
            }
        }
    }

    /**
     * A custom round JButton used for the homepage button.
     */
    static class RoundButton extends JButton {
        /**
         * Constructs a round-style button with specified label text.
         *
         * @param text the button label
         */
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

        /**
         * Paints the background of the button as a circle.
         *
         * @param g the Graphics context
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
         * Suppresses painting the border.
         *
         * @param g the Graphics context
         */
        @Override
        protected void paintBorder(Graphics g) {
        }
    }

    /**
     * Updates the displayed record list and reloads the user interface.
     *
     * @param records the new list of records
     */
    public void updateTableData(List<Record> records) {
        this.recordList.clear();
        this.recordList.addAll(records);
        System.out.println("1");
        loadRecordObjects();
    }

    /**
     * Gets the homepage button.
     *
     * @return the homepage JButton
     */
    public JButton getBtnHomepage() {
        return btnHomePage;
    }

    /**
     * Gets the AI assistant button.
     *
     * @return the AI assistant JButton
     */
    public JButton getBtnAIAssistant() {
        return btnAIAssistant;
    }
}
