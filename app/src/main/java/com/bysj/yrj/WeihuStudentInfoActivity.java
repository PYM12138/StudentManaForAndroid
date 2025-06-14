package com.bysj.yrj;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import android.widget.AdapterView.OnItemClickListener;
import com.bysj.yrj.bean.StudentInfo;
import com.bysj.yrj.dao.AddStudentInfoDao;
import com.bysj.yrj.dao.ComData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WeihuStudentInfoActivity extends Activity {
    // 定义请求码（用于识别从哪个页面返回）
    private static final int REQUEST_CODE_EDIT = 100;
    // 成员变量
    Button butshow;
    TextView numedit;
    ListView listshow;
    ArrayList<StudentInfo> adata; // 存储查询的结果

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weihu_student_info);
        // 调用方法进行初始化
        this.init();
        // 为根据学号进行查询按钮添加事件监听
        this.butshow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String num = numedit.getText().toString();
                shownumData(num);
            }
        });
        // 为 listshow 控件添加监听
        this.listshow.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                listAction(arg2);
            }
        });
    }

    /*
     * 初始化声明的控件对象
     */
    private void init() {
        this.butshow = (Button) findViewById(R.id.showbutton);
        this.numedit = (TextView) findViewById(R.id.numedit);
        this.listshow = (ListView) findViewById(R.id.listView1);
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
                R.layout.list_item_student, // 自定义布局
                new String[]{"student_id", "student_name", "student_gender", "student_age", "student_major"},
                new int[]{R.id.student_id, R.id.student_name, R.id.student_gender, R.id.student_age, R.id.student_major}
        );

        // 4. 设置列表的适配器
        this.listshow.setAdapter(adapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_EDIT && resultCode == RESULT_OK) {
            // 数据发生变化，刷新当前页面的数据
            String num = numedit.getText().toString(); // 获取当前输入的学号
            shownumData(num); // 重新查询并刷新列表
        }
    }

    /**
     * 用户点击查询出的学生信息列表事件处理
     */
    private void listAction(int num) {
        // 1. 从数据中获取第 num 的数据
        StudentInfo tem = this.adata.get(num);
        // 2. 将获取的数据存储到公共数据区 --- 在学生信息维护界面获取
        ComData.item = tem;
        // 3. 跳转到信息修改删除界面
        Intent intent = new Intent(this, RepairStudentInfoActivity.class);
        startActivityForResult(intent, REQUEST_CODE_EDIT); // 修改这里
    }

   /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.weihu_student_info, menu);
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