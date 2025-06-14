package com.bysj.yrj;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.*;
import com.bysj.yrj.bean.StudentInfo;
import com.bysj.yrj.dao.AddStudentInfoDao;

public class AddStudentInfoActivity extends Activity {
    //成员变量
    EditText editnum, editname, editage, editmark;
    RadioButton radiomen, radiowomen;
    ArrayAdapter<String> proadapter;
    String[] proname = {"计算机应用", "Android开发", "前端脚本开发", "JavaWeb开发"};
    Spinner pro;
    Button butok, butre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student_info);
        //调用init()方法
        this.init();
        //提交按钮事件添加监听
        this.butok.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View V) {
                //提交按钮的时间处理代码
                addaction();
            }
        });
        //为清空按钮事件添加监听
        this.butre.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                qingkongaction();
            }
        });
    }

    //初始化声明的控件对象
    public void init() {
        this.editnum = (EditText) findViewById(R.id.addnumedit);
        this.editname = (EditText) findViewById(R.id.addnameedit);
        this.radiomen = (RadioButton) findViewById(R.id.addradioman);
        this.radiowomen = (RadioButton) findViewById(R.id.addradiowoman);
        this.editage = (EditText) findViewById(R.id.addageedit);
        this.pro = (Spinner) findViewById(R.id.spinner1);
        //构建适配器--数据源--显示格式
        this.proadapter = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, this.proname);
        //设置spinpro 数据源
        this.pro.setAdapter(proadapter);
        this.editmark = (EditText) findViewById(R.id.addmarkedit);
        this.butok = (Button) findViewById(R.id.addbutton);
        this.butre = (Button) findViewById(R.id.resbutton);

    }


    //添加按钮事件处理方法
    public void addaction() {
        // 获取输入值
        String num = this.editnum.getText().toString().trim();
        String name = this.editname.getText().toString().trim();
        String age = this.editage.getText().toString().trim();
        String pro = this.pro.getSelectedItem().toString().trim();

        // 校验必要字段是否为空
        if (num.isEmpty()) {
            Toast.makeText(this, "学号不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (name.isEmpty()) {
            Toast.makeText(this, "姓名不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!radiomen.isChecked() && !radiowomen.isChecked()) {
            Toast.makeText(this, "请选择性别", Toast.LENGTH_SHORT).show();
            return;
        }
        if (age.isEmpty()) {
            Toast.makeText(this, "年龄不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (pro.isEmpty()) {
            Toast.makeText(this, "请选择专业", Toast.LENGTH_SHORT).show();
            return;
        }

        // 继续执行保存
        String sex = radiomen.isChecked() ? "男" : "女";
        String mark = this.editmark.getText().toString().trim();

        StudentInfo tem = new StudentInfo();
        tem.setNum(num);
        tem.setName(name);
        tem.setSex(sex);
        tem.setAge(age);
        tem.setPro(pro);
        tem.setMark(mark);

        AddStudentInfoDao adao = new AddStudentInfoDao(this);
        long n = adao.addStudentInfo(tem);

        String mes = "学生信息添加失败";
        if (n > 0) {
            mes = "学生信息添加成功";
            // 添加成功后弹窗并跳转回主界面
            new android.app.AlertDialog.Builder(this)
                    .setTitle("提示")
                    .setMessage(mes)
                    .setPositiveButton("确定", (dialog, which) -> {
                        // 返回 MainActivity
                        finish();  // 关闭当前 Activity，自动回到 MainActivity
                    })
                    .show();
        } else {
            Toast.makeText(this, mes, Toast.LENGTH_LONG).show();
        }
    }


    //清空按钮事件处理方法
    public void qingkongaction() {
        this.editnum.setText("");
        this.editname.setText("");
        //设置性别默认为男进行选中显示
        this.radiomen.setChecked(true);
        this.editage.setText("");
        //设置下拉选择框的专业为选择框的第一个
        this.pro.setSelection(0);
        this.editmark.setText("");
    }
/*	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_info, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}*/
}
