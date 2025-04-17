package com.example.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Calendar;
import java.util.Date;

class RecordTest {

    @Test
    void testGetDetails() {
        // 创建一个 Record 对象
        Date paymentDate = new Date();
        Record record = new Record("1", paymentDate, 100.0, "food", "Alice");
        // 验证 getDetails 方法的输出
        String expectedDetails = "Payment ID: 1, Date: " + paymentDate + ", Amount: 100.0, Type: food, Account ID: Alice";
        assertEquals(expectedDetails, record.getDetails(), "getDetails should return the correct details");
    }

    @Test
    void testUpdatePayment() {
        // 创建一个 Record 对象
        Date paymentDate = new Date();
        Record record = new Record("1", paymentDate, 100.0, "food", "Alice");
        // 更新支付信息
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, -1); // 昨天的日期
        Date newPaymentDate = calendar.getTime();
        record.updatePayment(newPaymentDate, 200.0, "transportation", "Bob");
        // 验证更新后的值
        assertEquals(newPaymentDate, record.getPaymentDate(), "Payment date should be updated");
        assertEquals(200.0, record.getAmount(), "Amount should be updated");
        assertEquals("transportation", record.getCategory(), "Category should be updated");
        assertEquals("Bob", record.getPayee(), "Payee should be updated");
    }

    @Test
    void testValidatePayment_Valid() {
        // 创建一个有效的支付记录
        Date paymentDate = new Date();
        Record record = new Record("1", paymentDate, 100.0, "food", "Alice");
        assertTrue(record.validatePayment(), "Valid payment should return true");
    }

    @Test
    void testValidatePayment_InvalidAmount() {
        // 创建一个金额为负的支付记录
        Date paymentDate = new Date();
        Record record = new Record("1", paymentDate, -50.0, "food", "Alice");
        assertFalse(record.validatePayment(), "Invalid amount should return false");
    }

    @Test
    void testValidatePayment_InvalidCategory() {
        // 创建一个类别无效的支付记录
        Date paymentDate = new Date();
        Record record = new Record("1", paymentDate, 100.0, "invalid_category", "Alice");
        assertFalse(record.validatePayment(), "Invalid category should return false");
    }

    @Test
    void testValidatePayment_FutureDate() {
        // 创建一个日期为未来的支付记录
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, 1); // 明天的日期
        Date futureDate = calendar.getTime();
        Record record = new Record("1", futureDate, 100.0, "food", "Alice");
        assertFalse(record.validatePayment(), "Future date should return false");
    }

    @Test
    void testValidatePayment_EmptyPayee() {
        // 创建一个收款人为空的支付记录
        Date paymentDate = new Date();
        Record record = new Record("1", paymentDate, 100.0, "food", "");
        assertFalse(record.validatePayment(), "Empty payee should return false");
    }

    @Test
    void testValidatePayment_NullDate() {
        // 创建一个日期为 null 的支付记录
        Record record = new Record("1", null, 100.0, "food", "Alice");
        assertFalse(record.validatePayment(), "Null date should return false");
    }
}