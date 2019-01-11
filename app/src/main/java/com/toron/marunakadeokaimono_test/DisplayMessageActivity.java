package com.toron.marunakadeokaimono_test;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class DisplayMessageActivity extends AppCompatActivity {
    private DatabaseHelper helper;
    private SQLiteDatabase db;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_message);

        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        final String waonMessage   = intent.getStringExtra("WAON");
        final String secureMessage = intent.getStringExtra("SECURE");
        final String idMessage   = intent.getStringExtra("ID");
        final String passMessage = intent.getStringExtra("PASS");
        final String userMessage   = intent.getStringExtra("USER");

        // Capture the layout's TextView and set the string as its text
        final TextView waon = findViewById(R.id.textView);
        waon.setText(waonMessage);

        final TextView secure = findViewById(R.id.textView2);
        secure.setText(secureMessage);

        final TextView id = findViewById(R.id.textView3);
        id.setText(idMessage);

        final TextView pass = findViewById(R.id.textView4);
        pass.setText(passMessage);

        final TextView user = findViewById(R.id.textView5);
        user.setText(userMessage);

        Button insertButton = findViewById(R.id.button);
        insertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                SetRegistrationUser(waonMessage, secureMessage, idMessage, passMessage, userMessage);
                Log.d("debug","Insert Userdata success");
                GoFinish(v);


            }


        });


    }
    public void SetRegistrationUser(String waon,String secure,String id, String pass, String user ){
        //if(helper == null) {
        helper = new DatabaseHelper(getApplicationContext());
        //}
        //if(db == null ) {
        //deleteDatabase("TestDB.db");
        db = helper.getWritableDatabase();
        //}

        insertData(db, "u", waon, secure, id, pass, user);



    }
    private void insertData(SQLiteDatabase db, String tablename, String waon ,String secure,String id, String pass, String user) {
        try {
            ContentValues values = new ContentValues();
            values.put("user_id", (Integer) 1);
            values.put("waon", waon);
            values.put("security", secure);
            values.put("login_id", id);
            values.put("ruby", (String) null);
            values.put("password", pass);
            values.put("name", user);
            values.put("store_id", (String) null);
            values.put("points", (Integer) null);

            db.insert("u", null, values);
        }catch (SQLiteException e) {
            Log.d("debug","insert error" ) ;
        }


    }


    public void GoFinish(View view){
        Intent intent = new Intent(this, FinishRegistrationUserActivity.class);
        startActivity(intent);
    }
}
