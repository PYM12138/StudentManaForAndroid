package com.bysj.yrj;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.bysj.yrj.bean.StudentScore;
import com.bysj.yrj.dao.AddStudentScoreDao;
import com.bysj.yrj.dao.ComData;

public class RepairStudentScoreActivity extends Activity {
    TextView repairnum, repairname;
    EditText repandroid, repjava, rephtml;
    Button repbut, delbut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repair_student_score);

        // 初始化声明的控件对象
        this.init();
        // 显示公共数据区的学生成绩
        this.showOldStudentData();

        // 保存按钮
        this.repbut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveAction();
            }
        });

        // 删除按钮
        this.delbut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDeleteConfirmationDialog();
            }
        });
    }

    // 初始化声明的控件对象
    private void init() {
        this.repairnum = findViewById(R.id.repscorenumtext);
        this.repairname = findViewById(R.id.repscorenametext);
        this.repandroid = findViewById(R.id.repscoreandroidtext);
        this.repjava = findViewById(R.id.repscorejavatext);
        this.rephtml = findViewById(R.id.repscorehtmltext);
        this.repbut = findViewById(R.id.repscorebutton);
        this.delbut = findViewById(R.id.repdelscorebutton);
    }

    /**
     * 从公共数据区获取数据并显示
     */
    private void showOldStudentData() {
        // 获取存储的数据
        StudentScore tem = ComData.stem;
		/*if (tem == null) {
			Toast.makeText(this, "没有找到成绩记录", Toast.LENGTH_SHORT).show();
			return;
		}*/

        if (tem == null || tem.getNum() == null || tem.getNum().trim().isEmpty()) {
            Toast.makeText(this, "没有找到成绩记录", Toast.LENGTH_LONG).show();
            finish();
            return;
        }


        // 显示获取的数据
        this.repairnum.setText(tem.getNum());
        this.repairname.setText(tem.getName());
        this.repandroid.setText(tem.getAndroid());
        this.repjava.setText(tem.getJava());
        this.rephtml.setText(tem.getHtml());
    }

    /**
     * 显示删除确认对话框
     */
    private void showDeleteConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("确认删除");
        builder.setMessage("是否确认删除该学生成绩？");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deleteAction();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.setCancelable(false);
        dialog.show();
    }

    /**
     * 删除学生成绩
     */
    private void deleteAction() {
        String num = ComData.stem.getNum();
        AddStudentScoreDao adao = new AddStudentScoreDao(this);
        long n = adao.deleteById(num);
        String mes = "学生成绩删除失败";
        if (n > 0) {
            mes = "学生成绩删除成功";
            finish(); // 删除成功后返回上一级
        }
        Toast.makeText(this, mes, Toast.LENGTH_LONG).show();
    }

    /**
     * 保存学生成绩
     */
    private void saveAction() {
        // 获取用户输入的成绩
        String num = this.repairnum.getText().toString();
        String name = this.repairname.getText().toString();
        String android = this.repandroid.getText().toString();
        String java = this.repjava.getText().toString();
        String html = this.rephtml.getText().toString();

        // 验证成绩输入
        if (!validateScore(android) || !validateScore(java) || !validateScore(html)) {
            Toast.makeText(this, "成绩必须为 0-100 的数字", Toast.LENGTH_SHORT).show();
            return;
        }

        // 修改公共数据区
        ComData.stem.setNum(num);
        ComData.stem.setName(name);
        ComData.stem.setAndroid(android);
        ComData.stem.setJava(java);
        ComData.stem.setHtml(html);

        // 更新数据库
        AddStudentScoreDao adao = new AddStudentScoreDao(this);
        long n = adao.updateById(ComData.stem);
        String mes = "学生成绩修改失败";
        if (n > 0) {
            mes = "学生成绩修改成功";
            finish(); // 修改成功后返回上一级
        }
        Toast.makeText(this, mes, Toast.LENGTH_LONG).show();
    }

    /**
     * 验证成绩是否为 0-100 的有效数字
     */
    private boolean validateScore(String score) {
        if (score == null || score.trim().isEmpty()) {
            return false;
        }
        try {
            int value = Integer.parseInt(score);
            return value >= 0 && value <= 100;
        } catch (NumberFormatException e) {
            return false;
        }
    }

  /*  @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.repair_student_score, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }*/
}