package com.bysj.yrj;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_HELP = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //隐藏标题栏
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }


        int[] ids = {
                R.id.menu1, R.id.menu2, R.id.menu3, R.id.menu4,
                R.id.menu5, R.id.menu6, R.id.menu7, R.id.menu8, R.id.menu9
        };

        Class[] activities = {
                AddStudentInfoActivity.class, WeihuStudentInfoActivity.class, ShowStudentInfoActivity.class,
                AddStudentScoreActivity.class, WeihuStudentScoreActivity.class, ShowStudentScoreActivity.class,
                PassRepairActivity.class, HelpActivity.class, ExitActivity.class
        };

        for (int i = 0; i < ids.length; i++) {
            LinearLayout layout = findViewById(ids[i]);
            int finalI = i;
            layout.setOnClickListener(v -> {
                // 特殊处理 HelpActivity，使用 startActivityForResult
                if (activities[finalI] == HelpActivity.class) {
                    Intent intent = new Intent(MainActivity.this, HelpActivity.class);
                    startActivityForResult(intent, REQUEST_CODE_HELP);
                } else {
                    startActivity(new Intent(MainActivity.this, activities[finalI]));
                }
            });
        }
    }

    /**
     * 处理活动结果回调
     * 当启动的Activity完成并返回结果时，该方法被调用
     *
     * @param requestCode 请求码，表示启动Activity时传入的请求码
     * @param resultCode  结果码，表示Activity退出时传回的结果码
     * @param data        返回的数据，包含Activity退出时传回的数据
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 检查请求码、结果码以及返回数据是否符合预期
        if (requestCode == REQUEST_CODE_HELP && resultCode == RESULT_OK && data != null) {
            // 根据返回数据中的动作标识判断需要执行的操作
            if ("show_toast".equals(data.getStringExtra("action"))) {
                // 在 MainActivity 中显示 Toast
                Toast.makeText(this, "作者已经删库跑路", Toast.LENGTH_LONG).show();
            }
        }
    }
}

