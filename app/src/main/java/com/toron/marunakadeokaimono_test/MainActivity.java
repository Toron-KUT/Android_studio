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

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_dashboard:
                    mTextMessage.setText(R.string.title_dashboard);
                    return true;
                case R.id.navigation_notifications:
                    mTextMessage.setText(R.string.title_notifications);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        deleteDatabase("TestDB.db");

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }
    /** Called when the user taps the Send button */

    //テスト用転送 public void sendMessage(View view) {
    //        // Do something in response to button
    //        //遷移先にデータを渡すやり方
    //        //1 ****EditTextでどのフォームからかを特定
    //        //2 ****Messageで文字列を取得
    //        //3 文字列をIntentにputしていく
    //        //遷移先はDisplayMessageActivity
    //        Intent intent = new Intent(this, DisplayMessageActivity.class);
    //
    //        EditText waonEditText = (EditText) findViewById(R.id.editText3);
    //        String waonMessage = waonEditText.getText().toString();
    //        intent.putExtra("WAON", waonMessage);
    //
    //        EditText secureEditText = (EditText) findViewById(R.id.editText4);
    //        String secureMessage = secureEditText.getText().toString();
    //        intent.putExtra("SECURE", secureMessage);
    //
    //        EditText idEditText = (EditText) findViewById(R.id.editText5);
    //        String idMessage = idEditText.getText().toString();
    //        intent.putExtra("ID", idMessage);
    //
    //        EditText passEditText = (EditText) findViewById(R.id.editText6);
    //        String passMessage = passEditText.getText().toString();
    //        intent.putExtra("PASS", passMessage);
    //
    //
    //        EditText userEditText = (EditText) findViewById(R.id.editText7);
    //        String userMessage = userEditText.getText().toString();
    //        intent.putExtra("USER", userMessage);
    //
    //
    //        startActivity(intent);
    //
    //    }
    public void gotest(View view){
        Intent intent = new Intent(this, DatabaseConnectTestActivity.class);
        startActivity(intent);
    }
    public void goregistration(View view){
        Intent intent = new Intent(this, RegistrationUserActivity.class);
        startActivity(intent);
    }
    public void goAuthenticationUser(View view){
        Intent intent = new Intent(this,  AuthenticationUserActivity.class);
        startActivity(intent);
    }


}
