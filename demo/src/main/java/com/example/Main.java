package com.example;

import com.example.view.mainWindows;
import com.example.view.importData;

public class Main {
    public static void main(String[] args) {
        // 启动主窗口
        mainWindows mainFrame = new mainWindows();

        // 页面跳转逻辑：主窗口 -> 导入数据界面
        mainFrame.getBtnImportData().addActionListener(e -> {
            // 隐藏主窗口
            mainFrame.setVisible(false);

            // 显示导入数据界面
            importData importFrame = new importData();

            // 页面跳转逻辑：导入数据界面 -> 主窗口
            importFrame.getBtnHomepage().addActionListener(e1 -> {
                // 隐藏导入数据界面
                importFrame.setVisible(false);

                // 显示主窗口
                mainFrame.setVisible(true);
            });
        });
    }
}