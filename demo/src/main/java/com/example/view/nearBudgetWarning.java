package com.example.view;

import javax.swing.*;
import java.awt.*;
import java.text.DecimalFormat;

/**
 * This class provides a graphical warning popup to inform users when they are
 * approaching their weekly or monthly budget limit.
 * <p>
 * The popup displays a warning message and the percentage of the budget that
 * has
 * already been spent, helping users to manage their spending.
 * </p>
 */
public class nearBudgetWarning {
  /**
   * Displays a warning popup indicating how much of the weekly or monthly budget
   * has already been spent.
   *
   * @param is_Week_Month an integer indicating the time period:
   *                      0 for "week", 1 for "month"
   * @param percentage    the percentage of the budget that has already been spent
   *                      (0–100)
   */
  public static void showWarning(int is_Week_Month, double percentage) {
    DecimalFormat df = new DecimalFormat("0.0");

    // build frame
    JFrame frame = new JFrame();
    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    frame.setSize(450, 400);
    frame.setLocationRelativeTo(null);
    // build panel
    JPanel panel = new JPanel(new GridBagLayout());
    panel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
    panel.setBackground(Color.WHITE);
    frame.setContentPane(panel);
    // set layout
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(5, 5, 5, 5);
    gbc.anchor = GridBagConstraints.WEST;
    gbc.gridx = 0;
    // set font
    Font regularFont = new Font("Serif", Font.PLAIN, 28);

    /*
     * Warning Content
     */

    // Dear client
    gbc.gridy = 0;
    JLabel lblDC = new JLabel("Dear client,");
    lblDC.setFont(regularFont);
    panel.add(lblDC, gbc);
    // This week/month you have spent
    gbc.gridy = 1;
    String periodText = is_Week_Month == 0 ? "week" : "month";
    JLabel lblYHS = new JLabel("This " + periodText + " you have spent");
    lblYHS.setFont(regularFont);
    panel.add(lblYHS, gbc);
    // __ %
    gbc.gridy = 2;
    gbc.anchor = GridBagConstraints.CENTER;
    JLabel lblPercent = new JLabel(df.format(percentage) + "%");
    lblPercent.setFont(new Font("Serif", Font.BOLD, 80));
    panel.add(lblPercent, gbc);
    // of your budget
    gbc.gridy = 3;
    // gbc.anchor = GridBagConstraints.WEST;
    JLabel lblOYB = new JLabel("of your budget");
    lblOYB.setFont(regularFont);
    panel.add(lblOYB, gbc);

    // set visible
    frame.setVisible(true);
  }

  /**
   * Main method to test the near budget warning popup.
   *
   * @param args command-line arguments (not used)
   */
  public static void main(String[] args) {
    SwingUtilities.invokeLater(() -> {
      showWarning(1, 60);
    });
  }
}
