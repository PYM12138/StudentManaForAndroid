package com.bysj.yrj;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import com.bysj.yrj.bean.StudentInfo;
import com.bysj.yrj.dao.AddStudentInfoDao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShowStudentInfoActivity extends Activity {
    // 成员变量
    Button butall, butshow;
    TextView numedit;
    ListView listshow;
    ArrayList<StudentInfo> adata; // 存储查询的结果

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_student_info);
        // 调用方法进行初始化
        this.init();
        // 为查询所有学生信息按钮添加事件监听
        this.butall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showData();
            }
        });
        // 为根据学号查询按钮添加事件监听
        this.butshow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String num = numedit.getText().toString();
                shownumData(num);
            }
        });
    }

    /*
     * 初始化声明的控件对象
     */
    private void init() {
        this.butall = (Button) findViewById(R.id.showallbutton);
        this.butshow = (Button) findViewById(R.id.showbutton);
        this.numedit = (TextView) findViewById(R.id.numedit);
        this.listshow = (ListView) findViewById(R.id.listView1);
    }

    /**
     * 查询所有学生信息数据并显示
     */
    private void showData() {
        // 1. 调用相关方法查询数据
        AddStudentInfoDao adao = new AddStudentInfoDao(this);
        this.adata = adao.getStudentData();

        // 2. 构建列表需要的数据源
        List<Map<String, String>> sdata = new ArrayList<>();
        for (StudentInfo tem : adata) {
            Map<String, String> item = new HashMap<>();
            item.put("student_id", tem.getNum());
            item.put("student_name", tem.getName());
            item.put("student_gender", tem.getSex());
            item.put("student_age", tem.getAge());
            item.put("student_major", tem.getPro());
            sdata.add(item);
        }

        // 3. 使用 SimpleAdapter 绑定数据到自定义布局
        SimpleAdapter adapter = new SimpleAdapter(
                this,
                sdata,
                R.layout.list_item_student,
                new String[]{"student_id", "student_name", "student_gender", "student_age", "student_major"},
                new int[]{R.id.student_id, R.id.student_name, R.id.student_gender, R.id.student_age, R.id.student_major}
        );

        // 4. 设置列表的适配器
        this.listshow.setAdapter(adapter);
    }

    /**
     * 根据学号查询单个学生信息数据并显示
     *
     * @param num
     */
    public void shownumData(String num) {
        // 1. 调用相关方法查询数据
        AddStudentInfoDao adao = new AddStudentInfoDao(this);
        this.adata = adao.getStudentnumData(num);

        // 2. 构建列表需要的数据源
        List<Map<String, String>> sdata = new ArrayList<>();
        for (StudentInfo tem : adata) {
            Map<String, String> item = new HashMap<>();
            item.put("student_id", tem.getNum());
            item.put("student_name", tem.getName());
            item.put("student_gender", tem.getSex());
            item.put("student_age", tem.getAge());
            item.put("student_major", tem.getPro());
            sdata.add(item);
        }

        // 3. 使用 SimpleAdapter 绑定数据到自定义布局
        SimpleAdapter adapter = new SimpleAdapter(
                this,
                sdata,
                R.layout.list_item_student,
                new String[]{"student_id", "student_name", "student_gender", "student_age", "student_major"},
                new int[]{R.id.student_id, R.id.student_name, R.id.student_gender, R.id.student_age, R.id.student_major}
        );

        // 4. 设置列表的适配器
        this.listshow.setAdapter(adapter);
    }

   /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.show_student_info, menu);
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