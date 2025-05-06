package com.example.service;

import java.util.List;
import com.example.model.Record;
import com.example.utils.AIcontrol;
/**
 * @className economySuggestion
 * @Dscription This class is used to suggest economy related suggestions by calling the AI control API based on the user's records.
 */
public class economySuggestion {
/**
 * @methodName BudgetSuggestion
 * @description This method is used to suggest budget related suggestions 
 * @param records Global billing records
 * @return String budgetSuggestion
 */
public static String BudgetSuggestion(List<Record> records){
    String budgetSuggestion = ""; // 初始化变量
    try {
        budgetSuggestion = AIcontrol.suggestionAppCall(records, "budget");
    } catch (Exception e) {
        e.printStackTrace();
    }
    return budgetSuggestion; 
}
/**
 * @methodName SavingsSuggestion
 * @description This method is used to suggest savings related suggestions 
 * @param records Global billing records
 * @return String savingsSuggestion
 */
public static String SavingsSuggestion(List<Record> records){
    String savingsSuggestion = ""; 
    try {
        savingsSuggestion = AIcontrol.suggestionAppCall(records, "savings goal");
    } catch (Exception e) {
        e.printStackTrace();
    }
    return savingsSuggestion; 
}
/**
 * @methodName ExpensesSuggestion
 * @description This method is used to suggest expenses related suggestions 
 * @param records Global billing records
 * @return String expensesSuggestion
 */
public static String ExpensesSuggestion(List<Record> records){
    String expensesSuggestion = ""; 
    try {
        expensesSuggestion = AIcontrol.suggestionAppCall(records, "Save expenses");
    } catch (Exception e) {
        e.printStackTrace();
    }
    return expensesSuggestion; 
}
}