package com.example.view;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import com.example.utils.FestivalUtil;

/**
 * This class provides a UI popup reminder if tomorrow is a recognized festival.
 * It displays a message encouraging users to adjust their budget accordingly.
 */
public class happyFestival {
  /**
   * Displays a reminder popup if the given date's next day is a recognized
   * festival.
   * <p>
   * The popup includes the festival name and a short advisory message about
   * budgeting.
   * If there is no festival the next day, the method exits without showing
   * anything.
   * </p>
   *
   * @param todayDate the current date used to determine if tomorrow is a festival
   */
  public static void showReminder(LocalDate todayDate) {
    /*
     * If Tomorrow is a Festival, Show the Reminder
     * Otherwise, Do Not Show
     */

    LocalDate tomorrowDate = todayDate.plusDays(1);
    String festivalName = FestivalUtil.getFestivalName(tomorrowDate);
    if (festivalName == null)
      return;

    /*
     * Build the Reminder Frame
     */

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
    gbc.anchor = GridBagConstraints.WEST;
    gbc.gridx = 0;
    gbc.weightx = 1;
    // set font
    Font regularFont = new Font("Serif", Font.PLAIN, 28);

    /*
     * Warning Content
     */

    // Happy __Festival
    gbc.gridy = 0;
    gbc.insets = new Insets(5, 20, 20, 5);
    JLabel lblHF = new JLabel("Happy " + festivalName + "!");
    lblHF.setFont(new Font("Serif", Font.BOLD, 34));
    lblHF.setForeground(Color.BLACK);
    panel.add(lblHF, gbc);

    // Tomorrow is __Festival
    gbc.gridy = 1;
    gbc.insets = new Insets(5, 20, 0, 5);
    JLabel lblTIF = new JLabel("Tomorrow is " + festivalName + ",");
    lblTIF.setFont(regularFont);
    panel.add(lblTIF, gbc);

    // Please be mindful……
    gbc.gridy = 2;
    JLabel lbltxt = new JLabel(
        "<html><div style='width:350px;'>Please be mindful of additional expenses and adjust daily budget accordingly.</div></html>");
    lbltxt.setFont(regularFont);
    panel.add(lbltxt, gbc);

    // set visible
    frame.setVisible(true);
  }

  /**
   * Main method for testing the festival reminder popup.
   * <p>
   * This uses a fixed date to simulate the reminder.
   * </p>
   *
   * @param args command-line arguments (not used)
   */
  public static void main(String[] args) {
    // LocalDate todayDate = LocalDate.now();
    LocalDate perticularDate = LocalDate.of(2025, 2, 13);
    showReminder(perticularDate);
  }
}
