package com.example.view;

import javax.swing.*;
import java.awt.*;
import java.text.DecimalFormat;

public class nearBudgetWarning {

  public static void showWarning(int is_Week_Month, double userBudget, double haveSpent) {
    // calculate %
    double percentage = (haveSpent / userBudget) * 100;
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

    /**
     * Warning Content
     */

    // Dear client
    JLabel lblDC = new JLabel("Dear client,");
    lblDC.setFont(new Font("Serif", Font.PLAIN, 28));
    panel.add(lblDC, gbc);
    // This week/month you have spent
    gbc.gridy = 1;
    String periodText = is_Week_Month == 0 ? "week" : "month";
    JLabel lblYHS = new JLabel("This " + periodText + " you have spent");
    lblYHS.setFont(new Font("Serif", Font.PLAIN, 28));
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
    lblOYB.setFont(new Font("Serif", Font.PLAIN, 28));
    panel.add(lblOYB, gbc);

    // set visible
    frame.setVisible(true);
  }

  public static void main(String[] args) {
    SwingUtilities.invokeLater(() -> {
      showWarning(1, 6000, 3800);
    });
  }
}
