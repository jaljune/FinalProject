package com.example.june1.projects;

/**
 * Created by june1 on 2015-12-19.
 */

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class DataBase extends AppCompatActivity {

    // Database 관련 객체들
    SQLiteDatabase db;
    String dbName = "idList.db"; // name of Database;
    String tableName = "idListTable"; // name of Table;
    int dbMode = Context.MODE_PRIVATE;


    // layout object
    EditText mEtName;
    EditText mEtName2;
    EditText Num;
    ListView mList;
    ArrayAdapter<String> musicAdapter;
    ArrayList<String> nameList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database);

        Intent in = this.getIntent();
        Toast.makeText(this.getApplicationContext(), in.getStringExtra("d"), Toast.LENGTH_LONG).show();

        // create databases
        db = openOrCreateDatabase(dbName, dbMode, null);
        // create table;
        createTable();

        mEtName = (EditText) findViewById(R.id.et_text);
        mEtName2 = (EditText) findViewById(R.id.et_text2);
        Num = (EditText) findViewById(R.id.et_num);
        mList = (ListView) findViewById(R.id.list_view);



        // Create listview
        nameList = new ArrayList<String>();
        musicAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, nameList);
        mList.setAdapter(musicAdapter);


    }

    public void myClickHandler(View v) {
        Intent intent = getIntent();
        double lat = intent.getDoubleExtra("lat", 37.498369228313012);
        double lng = intent.getDoubleExtra("lng", 127.0688158);

        switch (v.getId()) {
            case R.id.bt_insert:
                String name =  mEtName2.getText().toString()+ " - " + mEtName.getText().toString() + "("+ lat + "," +lng +")";
                insertData(name);
                break;


            case R.id.bt_read:
                nameList.clear();
                selectAll();
                musicAdapter.notifyDataSetChanged();
                break;

            case R.id.bt_delete:
                int index = Integer.parseInt(Num.getText().toString());
                removeData(index);
                break;

            case R.id.bt_disc :
                nameList.clear();
                sortSelectAll();
                musicAdapter.notifyDataSetChanged();
                break;

            case R.id.bt_update:
                String text = mEtName2.getText().toString()+ " - " + mEtName.getText().toString() + "("+ lat + "," +lng +")";
                int index2 = Integer.parseInt(Num.getText().toString());
                updateData(index2, text);
                break;

            case R.id.bt_drop:
                removeTable();
                finish();
                break;
        }
    }

    // Table 생성
    public void createTable() {
        try {
            String sql = "create table " + tableName + "(id integer primary key autoincrement, " + "name text not null)";
            db.execSQL(sql);
        } catch (android.database.sqlite.SQLiteException e) {
            Log.d("Lab sqlite", "error: " + e);
        }
    }

    // Table 삭제
    public void removeTable() {
        String sql = "drop table " + tableName;
        db.execSQL(sql);
    }

    // Data 추가
    public void insertData(String name) {
        String sql = "insert into " + tableName + " values(NULL, '" + name + "');";
        db.execSQL(sql);
    }

    // Data 업데이트
    public void updateData(int index2, String name) {
        String sql = "update " + tableName + " set name = '" + name + "' where id = " + index2 + ";";
        db.execSQL(sql);
    }

    // Data 삭제
    public void removeData(int index) {
        String sql = "delete from " + tableName + " where id = " + index + ";";
        db.execSQL(sql);
    }

    // Data 읽기(꺼내오기)
    public void selectData(int index) {
        String sql = "select * from " + tableName + " where id = " + index + ";";
        Cursor result = db.rawQuery(sql, null);

        // result(Cursor 객체)가 비어 있으면 false 리턴
        if (result.moveToFirst()) {
            int id = result.getInt(0);
            String name = result.getString(1);
//            Toast.makeText(this, "index= " + id + " name=" + name, Toast.LENGTH_LONG).show();

            Log.d("lab_sqlite", "\"index= \" + id + \" name=\" + name ");
        }
        result.close();
    }


    // 모든 Data 읽기
    public void selectAll() {
        String sql = "select * from " + tableName + ";";
        Cursor results = db.rawQuery(sql, null);
        results.moveToFirst();


        while (!results.isAfterLast()) {
            int id = results.getInt(0);
            String name = results.getString(0) +". "+ results.getString(1);;
//            Toast.makeText(this, "index= " + id + " name=" + name, Toast.LENGTH_LONG).show();
            Log.d("lab_sqlite", "index= " + id + " name=" + name);

            nameList.add(name);
            results.moveToNext();
        }
        results.close();
    }

    public void sortSelectAll() {
        String sql = "select * from " + tableName + " order by id desc" + ";";
        Cursor results = db.rawQuery(sql, null);
        results.moveToFirst();

        while (!results.isAfterLast()) {
            int id = results.getInt(0);
            String name = results.getString(0) +". "+ results.getString(1);;
                    /*results.getString(0) +". "+ results.getString(1);*/
//            Toast.makeText(this, "index= " + id + " name=" + name, Toast.LENGTH_LONG).show();
            Log.d("lab_sqlite", "index= " + id + " name=" + name);

            nameList.add(name);
            results.moveToNext();
        }
        results.close();
    }


}

