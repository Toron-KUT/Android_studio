package com.toron.marunakadeokaimono_test;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

public class FinishRegistrationUserActivity extends AppCompatActivity {
    private TextView textView;
    private DatabaseHelper helper;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish_registration_user);
        readData();
    }

    private void readData() {
        textView = findViewById(R.id.textView13);

        if(helper == null){
            helper = new DatabaseHelper(getApplicationContext());
        }

        if(db == null){
            db = helper.getReadableDatabase();

        }
        Log.d("debug","openUserData");
        //String sql = "select * from u";
        //Cursor cursor = db.rawQuery(sql,null);
        Cursor cursor = db.query(
                "users",
                new String[] { "user_id", "login_id" ,"name","ruby","password","waon","security"} ,
                null,
                null,
                null,
                null,
                null
        );

        cursor.moveToFirst();

        StringBuilder sbuilder = new StringBuilder();


        String[] columnNames = cursor.getColumnNames();
        int length = columnNames.length;
        for (int i = 0; i < cursor.getCount(); i++) {
            String value = null;
            try {
                sbuilder.append(cursor.getInt(0));
                sbuilder.append(": ");
                sbuilder.append(cursor.getString(1));
                sbuilder.append("\n");
                sbuilder.append(cursor.getString(2));
                sbuilder.append("\n");
                sbuilder.append(cursor.getString(3));
                sbuilder.append("\n");
                sbuilder.append(cursor.getString(4));
                sbuilder.append("\n");

                cursor.moveToNext();
            } catch (SQLiteException e) {
                Log.d("debud","SQLError" + e.getMessage());
            }
            Log.d("dumpLastRecord", columnNames[i] + " : " + value);

        }
        cursor.close();

        Log.d("debug","**********"+sbuilder.toString());
        textView.setText(sbuilder.toString());
    }

}
