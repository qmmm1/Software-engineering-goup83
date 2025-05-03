package com.example.service;

import java.util.List;
import com.example.model.Record;
import com.example.utils.AIcontrol;
public class economySuggestion {
public static String BudgetSuggestion(List<Record> records){
    String budgetSuggestion = ""; // 初始化变量
    try {
        budgetSuggestion = AIcontrol.suggestionAppCall(records, "budget");
    } catch (Exception e) {
        e.printStackTrace();
    }
    return budgetSuggestion; 
}
public static String SavingsSuggestion(List<Record> records){
    String savingsSuggestion = ""; 
    try {
        savingsSuggestion = AIcontrol.suggestionAppCall(records, "savings goal");
    } catch (Exception e) {
        e.printStackTrace();
    }
    return savingsSuggestion; 
}
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