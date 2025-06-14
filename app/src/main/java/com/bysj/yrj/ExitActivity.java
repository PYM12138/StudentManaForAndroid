package com.bysj.yrj;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class ExitActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 不设置内容视图，仅显示对话框
        // setContentView(R.layout.activity_exit);

        // 创建并显示退出确认对话框
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("提示");
        builder.setMessage("是否确定退出程序？");

        // 确定按钮：完全退出应用
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // 关闭当前 Activity 栈中的所有 Activity
                finishAffinity();
                // 可选：强制终止进程，确保完全退出
                // System.exit(0);
            }
        });

        // 取消按钮：关闭对话框，返回上一个 Activity
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // 关闭对话框
                dialog.dismiss();
                // 结束 ExitActivity，返回上一个 Activity
                finish();
            }
        });

        // 创建并显示对话框
        AlertDialog dialog = builder.create();
        dialog.setCancelable(false); // 禁止点击对话框外部或返回键关闭对话框
        dialog.show();
    }
}