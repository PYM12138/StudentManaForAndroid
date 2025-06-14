package com.bysj.yrj;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class HelpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 可以设置一个布局文件，如果只需要显示对话框，可以不设置内容视图
        // setContentView(R.layout.activity_help);

        // 创建并显示帮助对话框
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("软件使用帮助");
        builder.setMessage("软件共有9个功能：包括学生信息的添加、学生信息编辑、学生信息查询、学生成绩添加" +
                "学生成绩编辑、学生成绩查询、系统管理、使用帮助、软件退出。\n如果在使用过程中有问题请自行解决！" +
                "\n软件作者：彭一鸣、谢博禧\n软件用途：安卓课设\n完成时间：2025年5月");
        // 为“我知道了”按钮添加事件监听
        builder.setPositiveButton("我知道了", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // 点击“我知道了”后关闭对话框
                dialog.dismiss();
                finish();
            }
        });
        //为“联系作者”按钮添加事件监听
        builder.setNegativeButton("联系作者", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int arg1) {
                // 返回结果给 MainActivity
                Intent resultIntent = new Intent();
                resultIntent.putExtra("action", "show_toast");
                setResult(RESULT_OK, resultIntent);
                dialog.dismiss();
                finish(); // 关闭 HelpActivity

            }
        });
        // 创建并显示对话框
        builder.create().show();
    }
}