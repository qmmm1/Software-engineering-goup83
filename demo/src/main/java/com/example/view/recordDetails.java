package com.example.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class recordDetails extends JFrame {

  private String categoryChoice; // record user's choice of category

  public recordDetails(LocalTime tradeTime, LocalDate tradeDate, double moneyAmount, String payee, String category) {
    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    setSize(600, 450);
    setLocationRelativeTo(null);
    // build a panel
    JPanel panel = new JPanel(new GridBagLayout());
    panel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
    panel.setBackground(Color.WHITE);
    this.setContentPane(panel);
    // set layout
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(5, 20, 5, 5);
    gbc.anchor = GridBagConstraints.WEST;
    gbc.gridx = 0;
    gbc.gridwidth = 3;
    gbc.weightx = 1.0;
    // set font
    Font regularFont = new Font("Serif", Font.PLAIN, 28);

    /**
     * Show Content
     */

    // details:
    gbc.gridy = 0;
    JLabel lblDetails = new JLabel("Details:");
    lblDetails.setFont(new Font("Serif", Font.PLAIN, 34));
    panel.add(lblDetails, gbc);
    // time + money + payee + category
    gbc.gridy = 1;
    JLabel lblTMPC = new JLabel();
    lblTMPC.setFont(regularFont);
    panel.add(lblTMPC, gbc);
    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MMMM d, yyyy").withLocale(Locale.ENGLISH);
    DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
    final String[] currentCategory = { category };
    Runnable updateLabel = () -> {
      String text = String.format(
          "<html><div style='width:400px;'>At %s on %s,<br>" +
              "you paid ¥%.2f to %s, on %s.<br>" +
              "If you wish to change the category,<br>" +
              "Choose from below:</div></html>",
          tradeTime.format(timeFormatter),
          tradeDate.format(dateFormatter),
          moneyAmount, payee, currentCategory[0]);
      lblTMPC.setText(text);
    };
    updateLabel.run();

    /**
     * Category Buttons
     */

    gbc.gridwidth = 1;
    gbc.insets = new Insets(10, 5, 10, 5);
    gbc.anchor = GridBagConstraints.CENTER;
    String[] buttonLabels = { "Category 1", "Category 2", "Category 3", "Category 4", "Category 5", "Others" };

    for (int i = 0; i < buttonLabels.length; i++) {
      gbc.gridx = i % 3;
      gbc.gridy = 2 + i / 3;

      JButton btnCategory = new JButton(buttonLabels[i]);
      btnCategory.setFont(new Font("Serif", Font.PLAIN, 26));
      btnCategory.addActionListener((ActionEvent e) -> {
        currentCategory[0] = btnCategory.getText();
        categoryChoice = currentCategory[0];
        System.out.println(categoryChoice);
        updateLabel.run();
      });
      panel.add(btnCategory, gbc);
    }

    setVisible(true);
  }

  /**
   * Methods
   */

  public String getCategoryChoice() {
    return categoryChoice;
  }

  public static void main(String[] args) {
    SwingUtilities.invokeLater(() -> {
      new recordDetails(LocalTime.of(14, 30), LocalDate.of(2025, 4, 14), 120.50, "Starbucks", "Category 1");
    });
  }
}
