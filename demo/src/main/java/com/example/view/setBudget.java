package com.example.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;
import com.example.model.Setting;
import com.example.Main; // 新增导入
import com.example.utils.SettingControl;

/**
 * The {@code setBudget} class represents a Swing JFrame
 * that allows users to select a budget period (one week or one month),
 * enter a budget amount, and save the budget settings.
 * <p>
 * It provides UI components for selecting duration, displaying start and end
 * dates,
 * inputting the budget amount, and buttons to navigate to Homepage, AI
 * Assistant, and Records View.
 * </p>
 * <p>
 * The class uses GridBagLayout for flexible component placement
 * and includes custom RoundButton for homepage navigation.
 * </p>
 *
 * @author
 * @version 1.0
 * @since 2025-05-23
 */
public class setBudget extends JFrame {

  /**
   * RoundButton is a custom JButton with circular shape
   * and customized font and size.
   */
  static class RoundButton extends JButton {
    /**
     * Constructs a RoundButton with specified text.
     * 
     * @param text The text displayed on the button.
     */
    public RoundButton(String text) {
      super(text);
      setContentAreaFilled(false);
      setFocusPainted(false);
      setBorderPainted(false);
      setFont(new Font("Serif", Font.BOLD, 20)); // increase font size
      setPreferredSize(new Dimension(220, 120)); // increase button size
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
      // do not paint default border
    }
  }

  /*
   * Define the Variables
   */

  private JLabel lblTodayDate;
  private JButton btnOneWeek, btnOneMonth;
  private JLabel lblEndDate;
  private JTextField txtInputBudget;

  private LocalDate startDate;
  private LocalDate endDate; // calculate the end date
  private double userBudget = 0.0; // store user's budget
  private int is_Week_Month = 0; // 0-week, 1-month

  private JButton btnAIAssistant;
  private RoundButton btnHomepage;
  private JButton btnRecordsView;
  private Setting setting;

  /**
   * Constructs the setBudget JFrame window with the given Setting object.
   * Initializes UI components, layouts, and event listeners.
   * 
   * @param orignSetting The Setting object to store and persist budget
   *                     configurations.
   */
  public setBudget(Setting orignSetting) {

    this.setting = orignSetting;

    /*
     * Set the Frame
     */

    setTitle("setBudget");
    setSize(800, 600);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLocationRelativeTo(null);
    setLayout(new GridBagLayout());

    GridBagConstraints gbc = new GridBagConstraints();
    gbc.fill = GridBagConstraints.HORIZONTAL;
    gbc.insets = new Insets(10, 10, 10, 10); // distance between components
    gbc.weightx = 1;
    gbc.weighty = 0;

    // set font
    Font regularFont = new Font("Serif", Font.BOLD, 24);

    // set format of date
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM d, yyyy", Locale.ENGLISH);

    /*
     * Today is MONTH DAY YEAR
     */

    // creat a panel
    JPanel todayDatePanel = new JPanel(new GridBagLayout());
    // to control the layout
    GridBagConstraints tdGbc = new GridBagConstraints();
    tdGbc.gridx = 0;
    tdGbc.gridy = 0;
    tdGbc.anchor = GridBagConstraints.CENTER;
    tdGbc.insets = new Insets(20, 0, 5, 0);
    // define a label
    startDate = LocalDate.now();
    lblTodayDate = new JLabel("Today is " + startDate.format(formatter));
    lblTodayDate.setFont(new Font("Serif", Font.BOLD, 30));
    // add label to panel
    todayDatePanel.add(lblTodayDate, tdGbc);
    // add panel to frame
    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.gridwidth = 2; // occupies 2 columns
    gbc.anchor = GridBagConstraints.CENTER;
    add(todayDatePanel, gbc);

    /*
     * Choose Your Length of Time
     */

    JPanel cylotPanel = new JPanel(new GridBagLayout());
    GridBagConstraints cylotGbc = new GridBagConstraints();
    // show the sentence "Choose your length of time:"
    JLabel lblCYLOT = new JLabel("Choose your length of time:");
    lblCYLOT.setFont(regularFont);
    cylotGbc.gridx = 0;
    cylotGbc.gridy = 0;
    cylotGbc.gridwidth = 2;
    cylotGbc.anchor = GridBagConstraints.WEST;
    cylotGbc.insets = new Insets(0, 30, 20, 0);
    cylotPanel.add(lblCYLOT, cylotGbc);
    // one week button
    btnOneWeek = new JButton("One Week");
    btnOneWeek.setFont(regularFont);
    cylotGbc.gridx = 0;
    cylotGbc.gridy = 1;
    cylotGbc.gridwidth = 1;
    cylotGbc.weightx = 0.5;
    cylotGbc.anchor = GridBagConstraints.EAST;
    cylotGbc.insets = new Insets(0, 25, 0, 25);
    cylotPanel.add(btnOneWeek, cylotGbc);
    // one month button
    btnOneMonth = new JButton("One Month");
    btnOneMonth.setFont(regularFont);
    cylotGbc.gridx = 1;
    cylotGbc.gridy = 1;
    cylotGbc.anchor = GridBagConstraints.WEST;
    cylotPanel.add(btnOneMonth, cylotGbc);
    // add panel to frame
    gbc.gridx = 0;
    gbc.gridy = 1;

    /*
     * So Your End Date Is
     */

    // show the sentence "So your end date is:"
    JLabel lblSYEDI = new JLabel("So your end date is:");
    lblSYEDI.setFont(regularFont);
    cylotGbc.gridx = 0;
    cylotGbc.gridy = 2;
    cylotGbc.gridwidth = 2;
    cylotGbc.weightx = 0.5;
    cylotGbc.insets = new Insets(15, 30, 10, 0);
    cylotPanel.add(lblSYEDI, cylotGbc);
    // end date label
    lblEndDate = new JLabel("  MONTH   DAY   YEAR  "); // initial placeholder
    lblEndDate.setBorder(BorderFactory.createLineBorder(Color.BLACK));
    lblEndDate.setFont(new Font("Serif", Font.PLAIN, 24));
    cylotGbc.gridx = 0;
    cylotGbc.gridy = 3;
    cylotGbc.anchor = GridBagConstraints.CENTER;
    cylotGbc.insets = new Insets(10, 0, 10, 0);
    cylotPanel.add(lblEndDate, cylotGbc);
    // add panel to frame
    gbc.gridx = 0;
    gbc.gridy = 2;
    add(cylotPanel, gbc);

    /*
     * Set Your Budget Below
     */

    JPanel sybbPanel = new JPanel(new GridBagLayout());
    GridBagConstraints sybbGbc = new GridBagConstraints();
    // show the sentence "Set your Budget below"
    JLabel lblSYBB = new JLabel("Set your Budget below:");
    lblSYBB.setFont(regularFont);
    sybbGbc.gridx = 0;
    sybbGbc.gridy = 0;
    sybbGbc.gridwidth = 2;
    sybbGbc.weightx = 0.5;
    sybbGbc.anchor = GridBagConstraints.WEST;
    sybbGbc.insets = new Insets(0, 30, 20, 0);
    sybbPanel.add(lblSYBB, sybbGbc);
    // input budget text field
    txtInputBudget = new JTextField("The amount of money", 20);
    txtInputBudget.setFont(new Font("Serif", Font.PLAIN, 20));
    sybbGbc.gridx = 0;
    sybbGbc.gridy = 1;
    sybbGbc.anchor = GridBagConstraints.CENTER;
    sybbGbc.insets = new Insets(0, 0, 10, 0);
    sybbPanel.add(txtInputBudget, sybbGbc);
    // press enter to confirm
    JLabel lblPETC = new JLabel("(press enter to confirm)");
    lblPETC.setFont(new Font("Serif", Font.PLAIN, 20));
    sybbGbc.gridy = 2;
    sybbPanel.add(lblPETC, sybbGbc);
    // add panel to frame
    gbc.gridx = 0;
    gbc.gridy = 3;
    add(sybbPanel, gbc);

    /*
     * AI Assistant & Homepage & Record View
     */

    gbc.gridy = 7;
    gbc.gridx = 0;
    gbc.gridwidth = 2;
    // creat a panel
    JPanel bottomButtonPanel = new JPanel(new GridBagLayout()) {
      @Override
      public Dimension getPreferredSize() {
        return new Dimension(800, 120); // 增加面板高度
      }
    };
    // to control the layout of buttons
    GridBagConstraints btnGbc = new GridBagConstraints();
    btnGbc.insets = new Insets(0, 20, 0, 20); // 调整水平间距
    btnGbc.weightx = 1.0;
    btnGbc.fill = GridBagConstraints.NONE;
    // define buttons
    btnAIAssistant = new JButton("AI Assistant");
    btnHomepage = new RoundButton("Homepage");
    btnRecordsView = new JButton("Records View");
    // set button size and font size
    btnAIAssistant.setPreferredSize(new Dimension(200, 60));
    btnAIAssistant.setFont(new Font("Serif", Font.BOLD, 18));
    btnRecordsView.setPreferredSize(new Dimension(200, 60));
    btnRecordsView.setFont(new Font("Serif", Font.BOLD, 18));
    // set homepage button's color -> background white, font black
    btnHomepage.setBackground(Color.WHITE);
    btnHomepage.setForeground(Color.BLACK);
    // add buttons to panel
    btnGbc.gridx = 0;
    bottomButtonPanel.add(btnAIAssistant, btnGbc);
    btnGbc.gridx = 1;
    bottomButtonPanel.add(btnHomepage, btnGbc);
    btnGbc.gridx = 2;
    bottomButtonPanel.add(btnRecordsView, btnGbc);
    // add panel to frame
    gbc.gridx = 0;
    gbc.gridy = 4;
    gbc.weighty = 0.15;
    gbc.anchor = GridBagConstraints.SOUTH;
    gbc.insets = new Insets(5, 0, 5, 0); // reduce bottom margin
    add(bottomButtonPanel, gbc);

    /*
     * Define listeners
     */

    // listener for btnOneWeek
    btnOneWeek.addActionListener(e -> {
      // if btnOneWeek is selected, release btnOneMonth
      btnOneWeek.setEnabled(false); // turn gray
      btnOneMonth.setEnabled(true);
      // calculate the date after a week
      endDate = LocalDate.now().plusWeeks(1);
      // show the end date
      updateEndDateLabel();
      is_Week_Month = 0;

      lblSYEDI.requestFocus();
    });

    // listener for btnOneMonth
    btnOneMonth.addActionListener(e -> {
      // if btnOneMonth is selected, release btnOneWeek
      btnOneWeek.setEnabled(true);
      btnOneMonth.setEnabled(false); // turn gray
      // calculate the date after a month
      endDate = LocalDate.now().plusMonths(1);
      // show the end date
      updateEndDateLabel();
      is_Week_Month = 1;

      lblSYEDI.requestFocus();
    });

    // listener for txtInputBudget
    txtInputBudget.addMouseListener(new MouseAdapter() {
      public void mouseClicked(MouseEvent e) {
        // when clicked
        if (txtInputBudget.getText().equals("The amount of money")) { // clear the text field.
          txtInputBudget.setText("");
        }
      }
    });

    txtInputBudget.addActionListener(e -> {
      String userInput = txtInputBudget.getText();

      if (userInput.isEmpty() || userInput.equals("The amount of money")) {
        txtInputBudget.setText("The amount of money");
        return;
      }

      try {
        userBudget = Double.parseDouble(userInput);
        // budget must > 0
        if (userBudget <= 0) {
          JOptionPane.showMessageDialog(null, "Please enter a positive number.");
          txtInputBudget.setText("The amount of money");
          return;
        }

        // 设置预算到 Setting 中
        this.setting.setAmount(userBudget);

        // 设置周期
        if (is_Week_Month == 0) {
          this.setting.setduration_week();
        } else {
          this.setting.setduration_month();
        }

        Date startDateConverted = Date.from(startDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        this.setting.setStartDate(startDateConverted);

        // 更新并保存设置到 setting.txt 文件
        SettingControl.writeSettingToFile(this.setting);

        // 更新界面按钮显示等
        Main.updateBudgetButtonText(userBudget);

        JOptionPane.showMessageDialog(null, "Budget set to: " + userBudget);
      } catch (NumberFormatException ex) {
        JOptionPane.showMessageDialog(null, "Please enter a valid number.");
        txtInputBudget.setText("The amount of money");
      } catch (IOException ex) {
        JOptionPane.showMessageDialog(null, "Error saving settings to file.");
        ex.printStackTrace();
      }
    });

    // listener for btnHomepage
    btnHomepage.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        System.out.println("Back to Homepage");
      }
    });

    // listener for btnAIAssistant
    btnAIAssistant.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        System.out.println("Go to AIAssistant");
      }
    });

    // listener for btnRecordsView
    btnRecordsView.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        System.out.println("Go to Records View");
      }
    });

    /*
     * Set Visible
     */

    setVisible(true);
  }

  /*
   * Methods
   */

  /**
   * Updates the end date label to show the formatted end date.
   */
  private void updateEndDateLabel() {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("  MMMM  d,   yyyy  ", Locale.ENGLISH);
    lblEndDate.setText(endDate.format(formatter));
    lblEndDate.setFont(new Font("Serif", Font.BOLD, 24));
  }

  /**
   * Returns the homepage button component.
   * 
   * @return the homepage RoundButton.
   */
  public JButton getBtnHomepage() {
    return btnHomepage;
  }

  /**
   * Returns the AI Assistant button component.
   * 
   * @return the AI Assistant JButton.
   */
  public JButton getBtnAIAssistant() {
    return btnAIAssistant;
  }

  /**
   * Returns the Records View button component.
   * 
   * @return the Records View JButton.
   */
  public JButton getBtnRecordsView() {
    return btnRecordsView;
  }

  /**
   * Returns whether the user selected week or month duration.
   * 
   * @return 0 for week, 1 for month.
   */
  public int get_is_Week_Month() {
    return is_Week_Month;
  }

  /**
   * Returns the budget amount input by the user.
   * 
   * @return budget as double.
   */
  public double getUserBudget() {
    return userBudget;
  }

  /**
   * Returns the start date of the budget period.
   * 
   * @return start date as LocalDate.
   */
  public LocalDate getStartDate() {
    return startDate;
  }

  /**
   * Returns the end date of the budget period.
   * 
   * @return end date as LocalDate.
   */
  public LocalDate getEndDate() {
    return endDate;
  }

}
