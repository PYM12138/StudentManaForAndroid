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
import com.bysj.yrj.bean.StudentScore;
import com.bysj.yrj.dao.AddStudentScoreDao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShowStudentScoreActivity extends Activity {
    // 成员变量
    Button butscoreall, butscoreshow;
    TextView scoreedit;
    ListView listshow;
    ArrayList<StudentScore> adata; // 存储查询的结果

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_student_score);
        // 调用方法进行初始化
        this.init();

        // 为查询所有学生成绩按钮添加事件监听
        this.butscoreall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showscoreData();
            }
        });

        // 为根据学号查询成绩按钮添加事件监听
        this.butscoreshow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String num = scoreedit.getText().toString();
                shownumscoreData(num);
            }
        });
    }

    /*
     * 初始化声明的控件对象
     */
    private void init() {
        this.butscoreall = (Button) findViewById(R.id.showallbutton);
        this.butscoreshow = (Button) findViewById(R.id.showscorebutton);
        this.scoreedit = (TextView) findViewById(R.id.numscoreedit);
        this.listshow = (ListView) findViewById(R.id.listView11);
    }

    /**
     * 查询所有学生成绩数据并显示在ListView
     */
    private void showscoreData() {
        // 1. 调用相关方法查询数据
        AddStudentScoreDao adao = new AddStudentScoreDao(this);
        this.adata = adao.getallscoreData();
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

        // 3. 使用 SimpleAdapter 绑定数据到自定义布局
        SimpleAdapter adapter = new SimpleAdapter(
                this,
                sdata,
                R.layout.list_item_score,
                new String[]{"score_id", "score_name", "score_android", "score_java", "score_html"},
                new int[]{R.id.score_id, R.id.score_name, R.id.score_android, R.id.score_java, R.id.score_html}
        );

        // 4. 设置列表的适配器
        this.listshow.setAdapter(adapter);
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

        // 3. 使用 SimpleAdapter 绑定数据到自定义布局
        SimpleAdapter adapter = new SimpleAdapter(
                this,
                sdata,
                R.layout.list_item_score,
                new String[]{"score_id", "score_name", "score_android", "score_java", "score_html"},
                new int[]{R.id.score_id, R.id.score_name, R.id.score_android, R.id.score_java, R.id.score_html}
        );

        // 4. 设置列表的适配器
        this.listshow.setAdapter(adapter);
    }

   /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.show_student_score, menu);
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