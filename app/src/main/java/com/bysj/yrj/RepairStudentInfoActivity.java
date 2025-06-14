package com.bysj.yrj;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import com.bysj.yrj.bean.StudentInfo;
import com.bysj.yrj.dao.AddStudentInfoDao;
import com.bysj.yrj.dao.ComData;

public class RepairStudentInfoActivity extends Activity {
    // 成员变量
    TextView repaireditnum;
    EditText repaireditage, repaireditmark, repaireditname;
    RadioButton repairradiomen, repairradiowomen;
    Spinner repairpro;
    Button butsave, butdel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repair_student_info);

        // 初始化声明的控件对象
        this.init();
        // 显示公共数据区的学生信息
        this.showOldStudentData();

        // 保存按钮
        this.butsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveAction();
            }
        });

        // 删除按钮
        this.butdel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDeleteConfirmationDialog();
            }
        });
    }

    // 初始化声明的控件对象
    private void init() {
        this.repaireditnum = findViewById(R.id.repairnumtext);
        this.repaireditname = findViewById(R.id.repairnametext);
        this.repairradiomen = findViewById(R.id.repairradiomen);
        this.repairradiowomen = findViewById(R.id.repairradiowoman);
        this.repaireditage = findViewById(R.id.repairageedit);
        this.repairpro = findViewById(R.id.spinner1);
        this.repaireditmark = findViewById(R.id.repairmarkedit);
        this.butsave = findViewById(R.id.repairsavebutton);
        this.butdel = findViewById(R.id.repairdelbutton);
    }

    /**
     * 从公共数据区获取数据并显示
     */
    private void showOldStudentData() {
        // 获取存储的数据
        StudentInfo tem = ComData.item;
        if (tem == null) {
            Toast.makeText(this, "学生数据未加载", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // 显示获取的数据
        this.repaireditnum.setText(tem.getNum());
        this.repaireditname.setText(tem.getName());
        this.repaireditage.setText(tem.getAge());

        // 设置性别
        if (tem.getSex().equals("男")) {
            repairradiomen.setChecked(true);
        } else {
            repairradiowomen.setChecked(true);
        }

        // 设置专业（修复 ArrayIndexOutOfBoundsException）
        String pro = tem.getPro();
        String[] proname = getResources().getStringArray(R.array.proname); // 使用 XML 定义的数组
        int n = 0; // 默认选择第一项
        for (int i = 0; i < proname.length; i++) {
            if (pro != null && pro.equals(proname[i])) {
                n = i;
                break;
            }
        }
        this.repairpro.setSelection(n);

        this.repaireditmark.setText(tem.getMark());
    }

    /**
     * 显示删除确认对话框
     */
    private void showDeleteConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("确认删除");
        builder.setMessage("是否确认删除该学生信息？");
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
     * 删除学生信息
     */
    private void deleteAction() {
        String num = ComData.item.getNum();
        AddStudentInfoDao adao = new AddStudentInfoDao(this);
        long n = adao.deleteById(num);
        String mes = "学生信息删除失败";
        if (n > 0) {
            mes = "学生信息删除成功";
            finish(); // 删除成功后返回上一级
        }
        Toast.makeText(this, mes, Toast.LENGTH_LONG).show();
    }

    /**
     * 保存学生信息
     */
    private void saveAction() {
        // 获取用户输入的信息
        String num = this.repaireditnum.getText().toString();
        String name = this.repaireditname.getText().toString();
        String sex = this.repairradiowomen.isChecked() ? "女" : "男";
        String age = this.repaireditage.getText().toString();
        String pro = this.repairpro.getSelectedItem().toString();
        String mark = this.repaireditmark.getText().toString();

        // 修改公共数据区
        ComData.item.setNum(num);
        ComData.item.setName(name);
        ComData.item.setSex(sex);
        ComData.item.setAge(age);
        ComData.item.setPro(pro);
        ComData.item.setMark(mark);

        // 更新数据库
        AddStudentInfoDao adao = new AddStudentInfoDao(this);
        long n = adao.updateById(ComData.item);
        String mes = "学生信息修改失败";
        if (n > 0) {
            mes = "学生信息修改成功";
            setResult(RESULT_OK); // 添加这一行
            finish(); // 修改成功后返回上一级
        }
        Toast.makeText(this, mes, Toast.LENGTH_LONG).show();
    }

   /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.repair_student_info, menu);
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