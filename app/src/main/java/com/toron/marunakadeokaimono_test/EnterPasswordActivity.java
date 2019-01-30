package com.toron.marunakadeokaimono_test;

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

import com.android.volley.RequestQueue;

import java.util.Map;

public class EnterPasswordActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_password);
        Button insertButton = findViewById(R.id.EnterPasswordbutton1);
        insertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                //SetRegistrationUser(waonMessage, secureMessage, idMessage, passMessage, userMessage);
                Log.d("debug","Display");
                updateNewPassword();


            }


        });

    }

    public void updateNewPassword() {
        TextView idText1 = (TextView) findViewById(R.id.ChangePassword_password1);
        String text1 = idText1.getText().toString();
        TextView idText2 = (TextView) findViewById(R.id.ChangePassword_password2);
        String text2 = idText2.getText().toString();
       //String text2 = (findViewById(R.id.ChangePassword_password2)).toString();
        Log.d("debug",text1 +" " + text2 );

        //パスワードの比較
        if (text1.equals(text2)) {
            Log.d("debug","パスワード一致");
            Intent intent = new Intent(this,  FinishChangePasswordActivity.class);
            intent.putExtra("PASS", text1);
            startActivity(intent);
        } else {
            Toast.makeText(this, "パスワードが不一致です．", Toast.LENGTH_SHORT).show();
        }
    }

}
