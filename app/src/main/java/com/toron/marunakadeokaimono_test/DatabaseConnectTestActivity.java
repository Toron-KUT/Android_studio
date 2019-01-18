package com.toron.marunakadeokaimono_test;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.concurrent.ExecutionException;

public class DatabaseConnectTestActivity extends AppCompatActivity {

    private TextView textView;
    private EditText editTextKey, editTextValue;
    private DatabaseHelper helper;
    private SQLiteDatabase db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database_connect_test);

        //レイアウトファイルからテキストを取得
        //(findViewByID)
        editTextKey = findViewById(R.id.edit_text_key);
        editTextValue = findViewById(R.id.edit_text_value);

        textView = findViewById(R.id.text_view);


        //ボタンが押された時の挙動を記述
        //データベースヘルパーがなければ作成
        Button insertButton = findViewById(R.id.button_insert);
        insertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //deleteDatabase("TestDB.db");

                if (helper == null) {
                    helper = new DatabaseHelper(getApplicationContext());
                }
                if (db == null) {
                    db = helper.getWritableDatabase();

                }

                String key = editTextKey.getText().toString();
                String value = editTextValue.getText().toString();

                insertData(db, key, Integer.valueOf(value));
            }
        });

        Button readButton = findViewById(R.id.button_read);
        readButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readData();
            }
        });
    }
    private void readData(){
        if(helper == null){
            helper = new DatabaseHelper(getApplicationContext());
        }

        if(db == null){
            db = helper.getReadableDatabase();

        }
        Log.d("debug","*************Cursor");

        Cursor cursor = db.query(
                "testdb",
                new String[] { "company", "stockprice" }
                ,
                null,
                null,
                null,
                null,
                null
        );
        cursor.moveToFirst();

        StringBuilder sbuilder = new StringBuilder();


        for (int i = 0; i < cursor.getCount(); i++) {
            sbuilder.append(cursor.getString(0));
            sbuilder.append(": ");
            sbuilder.append(cursor.getInt(1));
            sbuilder.append("\n");
            cursor.moveToNext();
        }
        cursor.close();

        Log.d("debug","**********"+sbuilder.toString());
        textView.setText(sbuilder.toString());
    }

    private void insertData(SQLiteDatabase db, String com, int price){

        ContentValues values = new ContentValues();
        values.put("company", com);
        values.put("stockprice", price);

        db.insert("testdb", null, values);
    }

    public void SetTestTable(View v){
        try{
            if (helper == null) {
                helper = new DatabaseHelper(getApplicationContext());
            }
            if (db == null) {
                db = helper.getWritableDatabase();

            }
            db.execSQL("insert into hold(product_name,num,createDate) values('Yamada',10,'2001/4/1')");
            db.execSQL("insert into history(product_name,num,price,createDate) values('大根',10,200,'2001/4/1')");
            db.execSQL("insert into stores(store_name) values('マルナカ高知店')");


            final TextView log = findViewById(R.id.textView15);
            log.setText("create success");

        }catch(SQLiteException e){
            Log.d("debug", "create test data failed " + e.getMessage());
        }finally{
            Log.d("debug", "test table insert data sccess");
        }




    }
    public void phpconnectiontest(View v){
        try {
            if (helper == null) {
                helper = new DatabaseHelper(getApplicationContext());
            }
            if (db == null) {
                db = helper.getWritableDatabase();

            }
            JSONArray mJSONArray = helper.StartVolley("SDataPostPHP3.php",this);
            String title = mJSONArray.getJSONObject(0).getString("userid");
            Log.d("debug","DatabaseConnect Test  tile =  " + title);
            //helper.readVolly(this);
        }catch(NullPointerException e){
            Log.d("debug","out!!");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void goHistory(View view){
        Intent intent = new Intent(this, PurchaseHistroryActivity.class);
        startActivity(intent);
    }


}
