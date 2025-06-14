package com.bysj.yrj;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import com.bysj.yrj.bean.StudentScore;
import com.bysj.yrj.dao.AddStudentScoreDao;
import com.bysj.yrj.dao.ComData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WeihuStudentScoreActivity extends Activity {
    // 成员变量
    Button weihuscorebut;
    TextView weihuscoreedit;
    ListView listView;
    ArrayList<StudentScore> adata; // 存储查询的结果

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weihu_student_score);
        // 调用方法进行初始化
        this.init();
        // 为根据学号进行查询按钮添加事件监听
        this.weihuscorebut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String num = weihuscoreedit.getText().toString();
                shownumscoreData(num);
            }
        });
        // 为 listView 控件添加监听,可以跳转到编辑页面
        this.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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
        this.weihuscorebut = (Button) findViewById(R.id.showscorebutton);
        this.weihuscoreedit = (TextView) findViewById(R.id.numscoreedit);
        this.listView = (ListView) findViewById(R.id.listView12);
    }

    /**
     * 根据学号查询单个学生成绩数据并显示在ListView
     *
     * @param num
     */
    public void shownumscoreData(String num) {
        // 1. 调用相关方法查询数据
        AddStudentScoreDao adao = new AddStudentScoreDao(this);
        this.adata = adao.getScorenumData(num);
        if (adata == null || adata.isEmpty()) {
            return; // 或显示提示
        }

        // 2. 构建列表需要的数据源
        List<Map<String, String>> sdata = new ArrayList<>();
        for (StudentScore tem : adata) {
            Map<String, String> item = new HashMap<>();
            item.put("score_id", tem.getNum());
            item.put("score_name", tem.getName());
            item.put("score_android", tem.getAndroid());
            item.put("score_java", tem.getJava());
            item.put("score_html", tem.getHtml());
            sdata.add(item);
        }

        // 3. 使用 SimpleAdapter 绑定数据到自定义布局，适配器用于对齐数据
        // 参数说明：
        // - this: 当前上下文，通常是一个Activity实例
        // - sdata: 数据源，包含分数信息的列表
        // - R.layout.list_item_score: 每个列表项的布局资源ID
        // - new String[]{"score_id", "score_name", "score_android", "score_java", "score_html"}: 数据源中键的名称，对应于分数数据的字段
        // - new int[]{R.id.score_id, R.id.score_name, R.id.score_android, R.id.score_java, R.id.score_html}: 布局文件中对应的视图ID，用于展示分数数据
        SimpleAdapter adapter = new SimpleAdapter(
                this,
                sdata,
                R.layout.list_item_score,
                new String[]{"score_id", "score_name", "score_android", "score_java", "score_html"},
                new int[]{R.id.score_id, R.id.score_name, R.id.score_android, R.id.score_java, R.id.score_html}
        );

        // 4. 设置列表的适配器
        this.listView.setAdapter(adapter);
    }

    /**
     * 用户点击查询出的学生成绩列表事件处理
     */
    private void listAction(int num) {
        if (adata != null && num < adata.size()) {
            // 1. 从数据中获取第 num 的数据
            StudentScore tem = this.adata.get(num);
            // 2. 将获取的数据存储到公共数据区 --- 在学生成绩维护界面获取
            ComData.stem = tem;
            // 3. 跳转到成绩修改删除界面
            Intent intent = new Intent(this, RepairStudentScoreActivity.class);
            startActivity(intent);
        }
    }

   /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.weihu_student_score, menu);
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