package com.example.view;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import com.example.model.Record;

public class sameAmountWarning {
  private static JButton btnRecordsView;

  public static void showWarning(List<Record> sameAmountRecords) {
    // convert frequentRecords to String
    List<String> sameAmountRecords_String = new ArrayList<>();
    for (Record record : sameAmountRecords) {
      String displayText = String.format(
          Locale.ENGLISH,
          "At %tR on %<te %<tB %<tY, you paid ￥%.2f to %s, on category %s",
          record.getPaymentDate(), record.getAmount(), record.getPayee(), record.getCategory());
      sameAmountRecords_String.add(displayText);
    }
    // build a frame
    JFrame frame = new JFrame();
    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    frame.setSize(700, 600);
    frame.setLocationRelativeTo(null);
    frame.setLayout(new GridBagLayout());
    // set layout
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(5, 20, 5, 5);
    gbc.anchor = GridBagConstraints.WEST;
    gbc.gridx = 0;
    gbc.weightx = 1;
    // set font
    Color redColor = new Color(200, 50, 30);
    Font regularFont = new Font("Serif", Font.PLAIN, 28);

    /**
     * Warning Content
     */

    // creat a panel
    JPanel contentPanel = new JPanel(new GridBagLayout());
    // set layout
    GridBagConstraints Cgbc = new GridBagConstraints();
    Cgbc.insets = new Insets(5, 20, 5, 5);
    Cgbc.anchor = GridBagConstraints.WEST;
    Cgbc.gridx = 0;
    Cgbc.weightx = 1;

    // Dear client, we noticed
    Cgbc.gridy = 0;
    JLabel lblDCWN = new JLabel("Dear client, we noticed");
    lblDCWN.setFont(regularFont);
    contentPanel.add(lblDCWN, Cgbc);
    // multiple payments of the same amount
    Cgbc.gridy = 1;
    JLabel lblMPOTSA = new JLabel("Multiple Payments of the Same Amount ");
    lblMPOTSA.setFont(new Font("Serif", Font.BOLD, 32));
    lblMPOTSA.setForeground(redColor);
    contentPanel.add(lblMPOTSA, Cgbc);
    // to the same recipient.
    Cgbc.gridy = 2;
    Cgbc.gridx = 0;
    JLabel lblTTSR = new JLabel("to the same recipient.");
    lblTTSR.setFont(regularFont);
    contentPanel.add(lblTTSR, Cgbc);
    // Kindly confirm their validity.
    Cgbc.gridy = 3;
    JLabel lblKCTV = new JLabel("Kindly confirm their validity.");
    lblKCTV.setFont(regularFont);
    contentPanel.add(lblKCTV, Cgbc);

    // add to frame
    gbc.gridy = 0;
    frame.add(contentPanel, gbc);

    /**
     * Show Records
     */

    // creat a panel
    JPanel recordsPanel = new JPanel(new GridBagLayout());
    recordsPanel.setBackground(Color.WHITE);
    GridBagConstraints rGbc = new GridBagConstraints();
    rGbc.insets = new Insets(5, 20, 5, 5);
    rGbc.gridx = 0;
    rGbc.gridy = GridBagConstraints.RELATIVE;
    rGbc.anchor = GridBagConstraints.WEST;
    rGbc.weightx = 1;
    // set font
    Font recordFont = new Font("Serif", Font.PLAIN, 18);
    // show records
    for (String record : sameAmountRecords_String) {
      JLabel recordLabel = new JLabel(record);
      recordLabel.setFont(recordFont);
      recordLabel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY));
      recordsPanel.add(recordLabel, rGbc);
    }
    JScrollPane scrollPane = new JScrollPane(recordsPanel);
    scrollPane.setPreferredSize(new Dimension(650, 250));
    scrollPane.getVerticalScrollBar().setUnitIncrement(15);
    scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);

    // add to frame
    gbc.gridy = 1;
    frame.add(scrollPane, gbc);

    /**
     * Records View Button
     */

    // creat a panel
    JPanel buttonPanel = new JPanel(new GridBagLayout());
    // set layout
    GridBagConstraints btngbc = new GridBagConstraints();
    btngbc.insets = new Insets(5, 5, 5, 5);
    btngbc.anchor = GridBagConstraints.CENTER;
    btngbc.gridx = 0;
    btngbc.gridy = 0;
    btngbc.weightx = 1;
    // records view button
    btnRecordsView = new JButton("Records View");
    btnRecordsView.setFont(new Font("Serif", Font.PLAIN, 22));
    btnRecordsView.addActionListener(e -> {
      System.out.println("go to Records View");
    });
    // add button to panel
    buttonPanel.add(btnRecordsView);
    gbc.anchor = GridBagConstraints.CENTER;
    // add to frame
    gbc.gridy = 2;
    frame.add(buttonPanel, gbc);

    // set visible
    frame.setVisible(true);
  }

  public static void main(String[] args) {
    List<Record> sameAmountRecords = new ArrayList<>();

    Calendar calendar = Calendar.getInstance();
    calendar.set(2025, Calendar.APRIL, 9, 10, 0);
    Date fixedDate = calendar.getTime();

    for (int i = 1; i <= 10; i++) {
      String paymentId = "ID" + i;
      double amount = i * 100;
      String category = "Category " + i;
      String payee = "Recipient " + i;

      Record record = new Record(paymentId, fixedDate, amount, category, payee);
      sameAmountRecords.add(record);
    }
    showWarning(sameAmountRecords);
  }
}
