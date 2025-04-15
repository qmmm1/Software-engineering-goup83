package com.example.view;

import javax.swing.*;
import java.awt.*;

public class largeRemittanceWarning {

  public static void showWarning(double moneyAmount) {
    // build a frame
    JFrame frame = new JFrame();
    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    frame.setSize(450, 400);
    frame.setLocationRelativeTo(null);
    // build a panel
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
    Color redColor = new Color(200, 50, 30);
    Font regularFont = new Font("Serif", Font.PLAIN, 28);

    /**
     * Warning Content
     */

    // Dear client
    JLabel lblDC = new JLabel("Dear client,");
    lblDC.setFont(regularFont);
    panel.add(lblDC, gbc);
    // You have made
    gbc.gridy = 1;
    JLabel lblYHM = new JLabel("You have made");
    lblYHM.setFont(regularFont);
    panel.add(lblYHM, gbc);
    // A large remittance
    gbc.gridy = 2;
    JLabel lblALR = new JLabel("A large remittance");
    lblALR.setForeground(redColor);
    lblALR.setFont(new Font("Serif", Font.BOLD, 28));
    panel.add(lblALR, gbc);
    // In the amount of __
    gbc.gridy = 3;
    JLabel lblITAO = new JLabel("In the amount of  ¥" + moneyAmount);
    lblITAO.setFont(regularFont);
    panel.add(lblITAO, gbc);
    // Please make sure about that
    gbc.gridy = 4;
    JLabel lblPMSAT = new JLabel("Please make sure about that.");
    lblPMSAT.setFont(regularFont);
    panel.add(lblPMSAT, gbc);
    // Check to see if you have
    gbc.gridy = 5;
    JLabel lblCTSIYH = new JLabel("Check to see if you have");
    lblCTSIYH.setFont(regularFont);
    panel.add(lblCTSIYH, gbc);
    // Encountered a scam
    gbc.gridy = 6;
    JLabel lblEAS = new JLabel("Encountered a scam.");
    lblEAS.setForeground(redColor);
    lblEAS.setFont(new Font("Serif", Font.BOLD, 28));
    panel.add(lblEAS, gbc);

    // set visible
    frame.setVisible(true);
  }

  public static void main(String[] args) {
    SwingUtilities.invokeLater(() -> {
      showWarning(10000);
    });
  }
}
