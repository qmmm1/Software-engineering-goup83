# Software-engineering-goup83
# 分工：
# 前端GUI：胡孜砚（hhh659） 陈颖欣（chenyx2004）
# 后端、前后端连接： 魏青蓝（qmmm1） 郑今铭（xggggwqafsfasda）
# 测试：李晨（lichenxd） 齐心玫（qixinmei）
# AI-Empowered Personal Finance Tracker- Software Development Using Agile Methods
# I. Project Overview
# This project is a prototype AI-based personal finance management app designed to help users track spending, categorize spending, and make budget recommendations more efficiently. The software combines AI automated analysis with manual user verification to improve the intelligence and reliability of financial management. Adapt to local festivals and consumption habits in China, such as seasonal consumption peaks such as the Spring Festival, to improve practicality and scalability. This software has completed the implementation of core functions, adopted Java + Swing development, and uses local csv files for data interaction.
# Core features implemented:
# 1. Manual and automatic data entry:
# Support users to manually enter income and expenditure records through the GUI;
# Support importing structured CSV files to load transaction data such as Alipay and WeChat bills in batches;
# After importing, the data is automatically parsed and added to the unified record list.
# 2. AI Classification and Manual Correction:
# Introduce AI classification modules (such as using the DashScope SDK) to preliminarily classify consumption records.
# The classification results can be manually adjusted and corrected by the user in the GUI interface;
# Categories include: food, transportation, entertainment, education, living expenses, other.
# 3. Consumption analysis and intelligent recommendation:
# Provide monthly consumption statistics charts (pie charts, bar charts);
# Through the analysis of historical consumption data, the budget amount and saving strategy are intelligently suggested.
# It can detect and prompt high-frequency consumption or abnormal consumption.
# 4. Customization of local culture and festivals:
# Integrated lunar calendar module (Hutool + LunarCalendar);
# Automatically identify Chinese holidays (e.g., Chinese New Year) and add seasonal spending alerts to budget forecasts;
# 
# 
# 
# II. Software dependencies and configurations
# The software is developed in Java language, uses Maven as a build tool, and the graphical interface is based on Java Swing. Make sure that the following environment is installed on the machine:
# 
# 
# System requirements
# Operating System: Windows/macOS/Linux
# Java environment: JDK 17 or later
# Build tool: Maven 3.8+
# 
# 
# Functional modules used:
# 1. Java Standard Library
# Graphical interface: Use javax.swing and java.awt to implement interactive components such as windows, tables, buttons, and more.
# Date and time processing: Use the java.time series to process consumption dates, holidays, and month statistics.
# File reading and writing: Use java.io and java.nio to import and save CSV/JSON data.
# Collections and utility classes: Use lists, mappings, sorting, and streaming operations in java.util to support data management and statistics.
# Formatting and exception handling: such as amount formatting, date parsing, input validation, etc.
# 
# 
# 2. Third-party libraries (automatically managed by Maven, no need to download manually):
# JFreeChart: A chart (such as pie charts and bar charts) that can be used to generate consumption analysis to help users intuitively understand the spending structure.
# OpenCSV: Used to efficiently read and write CSV files, compatible with bank/payment platform bill formats.
# org.json: Used to process data in JSON format, supporting structured output and visual analysis.
# JUnit 5: used for unit testing and functional verification to ensure program stability.
# DashScope SDK (Alibaba Cloud): Invokes AI APIs to implement automatic consumption classification and budget recommendations.
# Hutool + LunarCalendar: Used for lunar festivals and China-specific date processing, making it easy to identify spending peaks such as Chinese New Year.
# 
# 
# These libraries are defined in pom.xml and are automatically downloaded and configured by Maven. Ordinary users do not need to install additional dependencies.
# 
# 
# 
# III. How to Run the Software
# The steps for this software to run are as follows:
# Before using it for the first time, make sure that:
# Java development environment installed (JDK 17 or higher)
# Maven build tools installed (optional, but recommended)
# It is recommended to use a development tool such as IntelliJ IDEA or Eclipse, which automatically handles all dependent libraries.
# 
# 
# Running mode: Open and run with development tools
# If you use an IDE, open the IDE and then open the folder where the project is located.
# The software automatically recognizes pom.xml and loads dependencies.
# Find Main.java or the main class, right-click and select Run.
# The graphical interface will automatically pop up and you can start using the software.
