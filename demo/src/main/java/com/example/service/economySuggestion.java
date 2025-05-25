package com.example.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import com.example.model.Record;
import com.example.utils.AIcontrol;
/**
 * This class is used to suggest economy related suggestions by calling the AI control API based on the user's records.
 */
public class economySuggestion {
/**
 * BudgetSuggestion.
 * This method is used to suggest budget related suggestions 
 * @param records Global billing records
 * @param duration The duration of the records to be considered for the suggestion
 * @return String budgetSuggestion
 */
public static String BudgetSuggestion(List<Record> records,int duration){
    String budgetSuggestion = ""; // 初始化变量
    try {
        Calendar calendar = Calendar.getInstance();
        // 减去duration天
        calendar.add(Calendar.DAY_OF_YEAR, -duration);
        Date startDate = calendar.getTime();
         List<Record> filteredRecords = new ArrayList<>();
            for (Record record : records) {
                if (!record.getPaymentDate().before(startDate)) {
                    filteredRecords.add(record);
                }
            }
        budgetSuggestion = AIcontrol.suggestionAppCall(filteredRecords  , "budget");
    } catch (Exception e) {
        e.printStackTrace();
    }
    return budgetSuggestion; 
}
/**
 * SavingsSuggestion.
 * This method is used to suggest savings related suggestions 
 * @param records Global billing records
 * @param duration The duration of the records to be considered for the suggestion
 * @return String savingsSuggestion
 */
public static String SavingsSuggestion(List<Record> records,int duration){
    String savingsSuggestion = ""; 
    try {
        Calendar calendar = Calendar.getInstance();
        // 减去duration天
        calendar.add(Calendar.DAY_OF_YEAR, -duration);
        Date startDate = calendar.getTime();
         List<Record> filteredRecords = new ArrayList<>();
            for (Record record : records) {
                if (!record.getPaymentDate().before(startDate)) {
                    filteredRecords.add(record);
                }
            }
        savingsSuggestion = AIcontrol.suggestionAppCall(filteredRecords, "savings goal");
    } catch (Exception e) {
        e.printStackTrace();
    }
    return savingsSuggestion; 
}
/**
 * ExpensesSuggestion.
 * This method is used to suggest expenses related suggestions 
 * @param records Global billing records
 * @param duration The duration of the records to be considered for the suggestion
 * @return String expensesSuggestion
 */
public static String ExpensesSuggestion(List<Record> records,int duration){
    String expensesSuggestion = ""; 
    try {
         Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, -duration);
        Date startDate = calendar.getTime();
         List<Record> filteredRecords = new ArrayList<>();
            for (Record record : records) {
                if (!record.getPaymentDate().before(startDate)) {
                    filteredRecords.add(record);
                }
            }
        expensesSuggestion = AIcontrol.suggestionAppCall(filteredRecords, "Save expenses");
    } catch (Exception e) {
        e.printStackTrace();
    }
    return expensesSuggestion; 
}
}