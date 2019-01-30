package com.toron.marunakadeokaimono_test;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.Map;

public class ChangePasswordActivity extends Activity {
    private String ChangePasswordID;
    private String ChangePasswordWaon;
    private String ChangePasswordSecurity;
    private DatabaseHelper helper;
    private SQLiteDatabase db;
    private RequestQueue mQueue;
    private String PHPURL = "http://222.229.69.53/~goohira/toron/php/insertNewUser.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        Button readButton = findViewById(R.id.ChangePasswordButton1);
        readButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText idEditText = (EditText) findViewById(R.id.ChangePasswordEditText1);
                ChangePasswordID = idEditText.getText().toString();
                EditText waonEditText = (EditText) findViewById(R.id.ChangePasswordEditText2);
                ChangePasswordWaon = waonEditText.getText().toString();
                EditText securityEditText = (EditText) findViewById(R.id.ChangePasswordEditText3);
                ChangePasswordSecurity = securityEditText.getText().toString();

                CheckUserData();

            }
        });
    }
    private void CheckUserData(){
        if(helper == null){
            helper = new DatabaseHelper(getApplicationContext());
        }

        if(db == null){
            db = helper.getReadableDatabase();

        }
        boolean success = helper.checkUserData(db,ChangePasswordID,ChangePasswordWaon,ChangePasswordSecurity);
        Log.d("debug","CheckUserData");
        System.out.println(success);

        if(success == true){
            Toast.makeText(this,"ユーザー情報認証に成功しました",Toast.LENGTH_SHORT).show();
            Log.d("debug","ユーザー情報認証に成功しました");

            Intent intent = new Intent(this,  EnterPasswordActivity.class);
            startActivity(intent);
        }
        else{
            Toast.makeText(this,"通信に失敗しました。",Toast.LENGTH_SHORT).show();

        }


    }
    //public void sendMessage() {


       // Intent intent = new Intent(this, DisplayMessageActivity.class);

       // EditText idEditText = (EditText) findViewById(R.id.ChangePasswordEditText1);
       // String idMessage = idEditText.getText().toString();
       // intent.putExtra("ID", idMessage);

      //  EditText waonEditText = (EditText) findViewById(R.id.ChangePasswordEditText2);
      //  String waonMessage = waonEditText.getText().toString();
     ///   intent.putExtra("WAON", waonMessage);

      //  EditText secureEditText = (EditText) findViewById(R.id.ChangePasswordEditText3);
      //  String secureMessage = secureEditText.getText().toString();
      //  intent.putExtra("SECURE", secureMessage);

      //  startActivity(intent);

    //}
}
