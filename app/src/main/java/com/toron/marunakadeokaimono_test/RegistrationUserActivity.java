package com.toron.marunakadeokaimono_test;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class RegistrationUserActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_user);
        Button readButton = findViewById(R.id.RegistrationButton1);
        readButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage();


            }
        });
    }
    public void sendMessage() {
        // Do something in response to button
        //遷移先にデータを渡すやり方
        //1 ****EditTextでどのフォームからかを特定
        //2 ****Messageで文字列を取得
        //3 文字列をIntentにputしていく
        //遷移先はDisplayMessageActivity
        Intent intent = new Intent(this, DisplayMessageActivity.class);

        EditText idEditText = (EditText) findViewById(R.id.RegistrationEditText1);
        String idMessage = idEditText.getText().toString();
        intent.putExtra("ID", idMessage);


        EditText passEditText = (EditText) findViewById(R.id.RegistrationEditText2);
        String passMessage = passEditText.getText().toString();
        intent.putExtra("PASS", passMessage);

        EditText userEditText = (EditText) findViewById(R.id.RegistrationEditText3);
        String userMessage = userEditText.getText().toString();
        intent.putExtra("USER", userMessage);

        EditText rubyEditText = (EditText) findViewById(R.id.RegistrationEditText4);
        String rubyMessage = rubyEditText.getText().toString();
        intent.putExtra("RUBY", rubyMessage);

        EditText waonEditText = (EditText) findViewById(R.id.RegistrationEditText5);
        String waonMessage = waonEditText.getText().toString();
        intent.putExtra("WAON", waonMessage);

        EditText secureEditText = (EditText) findViewById(R.id.RegistrationEditText6);
        String secureMessage = secureEditText.getText().toString();
        intent.putExtra("SECURE", secureMessage);

        startActivity(intent);

    }
}
