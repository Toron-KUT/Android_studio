package com.toron.marunakadeokaimono_test;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "com.toron.marunakadeokaimono_test.MESSAGE";
    private TextView mTextMessage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        deleteDatabase("TestDB.db");
        goAuthenticationUser();

        //mTextMessage = (TextView) findViewById(R.id.message);
        //BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        //navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }
    /** Called when the user taps the Send button */

    public void gotest(View view){
        Intent intent = new Intent(this, DatabaseConnectTestActivity.class);
        startActivity(intent);
    }
    public void goregistration(View view){
        Intent intent = new Intent(this, RegistrationUserActivity.class);
        startActivity(intent);
    }
    public void goAuthenticationUser(){
        Intent intent = new Intent(this,  AuthenticationUserActivity.class);
        startActivity(intent);
    }
    public void goHome(View view){
        Intent intent = new Intent(this,  HomeActivity.class);
    startActivity(intent);
}


}
