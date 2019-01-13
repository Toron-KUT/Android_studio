package com.toron.marunakadeokaimono_test;

import android.app.MediaRouteButton;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.ArrayMap;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Map;

public class AuthenticationUserActivity extends AppCompatActivity {
    private DatabaseHelper helper;
    private SQLiteDatabase db;
    private EditText editTextUser, editTextPassword;
    private MediaRouteButton HideButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication_user);
        Button readButton = findViewById(R.id.button5);
        editTextUser = findViewById(R.id.edit_text_User);
        editTextPassword = findViewById(R.id.edit_text_User);

        Button HideButton = findViewById(R.id.button8);

        readButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AuthenticationUser();

            }
        });
    }

    private void AuthenticationUser() {
        try {
            if (helper == null) {
                helper = new DatabaseHelper(getApplicationContext());
            }
            if (db == null) {
                db = helper.getReadableDatabase();
            }
            Map<String,Object> mAuthenticationUserData = null;
            String ID= editTextUser.toString();
            String Password = editTextPassword.toString();
            mAuthenticationUserData.put("login_id",ID);
            mAuthenticationUserData.put("password",Password);
            boolean success = helper.CheckAuthenticationUser(mAuthenticationUserData);

            if(success){
                Log.d("debug","ユーザー認証成功");
                TransitionHomeActivity();
            }
            else{
                Log.d("debug","ユーザー認証失敗");
                HideButton.setVisibility(View.VISIBLE);

            }



        }catch(SQLiteException e){
            Log.d("debug","ユーザー認証失敗" + e.getMessage());
        }
    }

    private void TransitionHomeActivity() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);

    }
    public void TransitionAuthenticationUserActivity() {
        Intent intent = new Intent(this, AuthenticationUserActivity.class);
        startActivity(intent);

    }

}
