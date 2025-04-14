package com.example.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class recordDetails extends JFrame {
  public recordDetails(LocalTime tradeTime, LocalDate tradeDate, double moneyAmount, String payee, String category) {
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
    Font regularFont = new Font("Serif", Font.PLAIN, 28);

  }

}
