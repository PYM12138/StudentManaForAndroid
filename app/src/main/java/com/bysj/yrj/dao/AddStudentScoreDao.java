package com.bysj.yrj.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import com.bysj.yrj.bean.StudentScore;

import java.util.ArrayList;

public class AddStudentScoreDao {

    MySqitHelper myhelper;
    SQLiteDatabase st;//
    String TB_NAME = "AddScoretb";
    String COL1 = "_id";
    String COL2 = "num";
    String COL3 = "name";
    String COL4 = "android";
    String COL5 = "java";
    String COL6 = "html";

    public AddStudentScoreDao(Context context) {

        this.myhelper = new MySqitHelper(context);

        try {

            this.st = this.myhelper.getWritableDatabase();
        } catch (Exception e) {

            this.st = this.myhelper.getReadableDatabase();
        }

        try {
            String sql = "create table if not exists " + TB_NAME + "(" + COL1 + "   integer primary key autoincrement , " +
                    COL2 + "  varchar(10)," + COL3 + "  varchar(10)," + COL4 + "  varchar(10)," + COL5 + "" +
                    "  varchar(10)," + COL6 + "  varchar(10))";

            this.st.execSQL(sql);
        } catch (Exception e) {
            Log.e("学生成绩表创建异常", e.toString());
        }
    }

    /**
     * 向数据库中添加学生分数记录
     *
     * 此方法接收一个StudentScore对象作为参数，将其属性值插入到数据库中
     * 它首先创建一个ContentValues对象来存储学生分数的各项数据，
     * 然后调用SQLite数据库的insert方法将这些数据插入到指定表中
     *
     * @param tem 包含学生分数信息的对象，包括学生的编号、姓名及各科目分数
     * @return 返回插入操作的行数，如果是-1表示插入失败
     */
    public long addStudentScore(StudentScore tem) {
        // 创建一个ContentValues对象，用于存储待插入的数据
        ContentValues values = new ContentValues();
        // 将学生分数的各项数据放入ContentValues中
        values.put(COL2, tem.getNum());
        values.put(COL3, tem.getName());
        values.put(COL4, tem.getAndroid());
        values.put(COL5, tem.getJava());
        values.put(COL6, tem.getHtml());

        // 调用SQLite数据库的insert方法，将数据插入到指定表中，并返回插入操作的行数
        long n = this.st.insert(TB_NAME, null, values);
        // 释放资源，确保数据库操作完成后关闭相关对象
        this.free();
        // 返回插入操作的行数，以供调用者判断插入是否成功
        return n;
    }


    /**
     * 获取所有学生成绩数据
     *
     * 从数据库中查询并返回所有学生的成绩信息，包括学生ID、学号、姓名、Android成绩、Java成绩和HTML成绩
     *
     * @return 包含所有学生成绩信息的ArrayList
     */
    public ArrayList<StudentScore> getallscoreData() {
        // 创建一个ArrayList用于存储学生成绩数据
        ArrayList<StudentScore> adata = new ArrayList<StudentScore>();

        // 定义SQL查询语句，查询数据库中所有列的数据
        String sql = "select * from  " + TB_NAME;

        // 执行SQL查询，获取查询结果的Cursor对象
        Cursor cursor = this.st.rawQuery(sql, null);

        // 遍历查询结果，将每条记录的数据封装到StudentScore对象中，并添加到ArrayList中
        while (cursor.moveToNext()) {
            // 获取每条记录的各个字段的值
            int id = cursor.getInt(cursor.getColumnIndex(COL1));
            String num = cursor.getString(cursor.getColumnIndex(COL2));
            String name = cursor.getString(cursor.getColumnIndex(COL3));
            String android = cursor.getString(cursor.getColumnIndex(COL4));
            String java = cursor.getString(cursor.getColumnIndex(COL5));
            String html = cursor.getString(cursor.getColumnIndex(COL6));

            // 创建一个新的StudentScore对象，并设置其属性值
            StudentScore tem = new StudentScore();
            tem.setId(id);
            tem.setNum(num);
            tem.setName(name);
            tem.setAndroid(android);
            tem.setJava(java);
            tem.setHtml(html);

            // 将封装好的StudentScore对象添加到ArrayList中
            adata.add(tem);
        }

        // 关闭Cursor，释放资源
        cursor.close();
        // 关闭数据库读取操作
        this.st.close();
        // 关闭数据库帮助器
        this.myhelper.close();

        // 返回包含所有学生成绩信息的ArrayList
        return adata;
    }

    public ArrayList<StudentScore> getScorenumData(String num0) {
        ArrayList<StudentScore> adata = new ArrayList<StudentScore>();

        String sql = "select * from  " + TB_NAME + "  where " + COL2 + "=?";

        Cursor cursor = this.st.rawQuery(sql, new String[]{num0});

        if (cursor.moveToNext()) {

            int id = cursor.getInt(cursor.getColumnIndex(COL1));
            String num = cursor.getString(cursor.getColumnIndex(COL2));
            String name = cursor.getString(cursor.getColumnIndex(COL3));
            String android = cursor.getString(cursor.getColumnIndex(COL4));
            String java = cursor.getString(cursor.getColumnIndex(COL5));
            String html = cursor.getString(cursor.getColumnIndex(COL6));

            StudentScore tem = new StudentScore();

            tem.setId(id);
            tem.setNum(num);
            tem.setName(name);
            tem.setAndroid(android);
            tem.setJava(java);
            tem.setHtml(html);

            adata.add(tem);
        }
        cursor.close();
        this.st.close();
        this.myhelper.close();
        return adata;
    }

    public long deleteById(String num) {
        long n = this.st.delete(TB_NAME, COL2 + "=?", new String[]{num});
        this.st.close();
        this.myhelper.close();
        return n;
    }

    public long updateById(StudentScore tem) {
        ContentValues values = new ContentValues();

        values.put(COL2, tem.getNum());
        values.put(COL3, tem.getName());
        values.put(COL4, tem.getAndroid());
        values.put(COL5, tem.getJava());
        values.put(COL6, tem.getHtml());

        long n = this.st.update(TB_NAME, values, COL2 + "=?", new String[]{String.valueOf(tem.getNum())});
        this.free();
        return n;
    }


    public void free() {
        this.st.close();
        this.myhelper.close();
    }
}