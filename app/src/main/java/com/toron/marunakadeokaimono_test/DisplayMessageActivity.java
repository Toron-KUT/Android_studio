package com.toron.marunakadeokaimono_test;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class DisplayMessageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_message);

        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        String waonMessage   = intent.getStringExtra("WAON");
        String secureMessage = intent.getStringExtra("SECURE");
        String idMessage   = intent.getStringExtra("ID");
        String passMessage = intent.getStringExtra("PASS");
        String userMessage   = intent.getStringExtra("USER");

        // Capture the layout's TextView and set the string as its text
        TextView waon = findViewById(R.id.textView);
        waon.setText(waonMessage);

        TextView secure = findViewById(R.id.textView2);
        secure.setText(secureMessage);

        TextView id = findViewById(R.id.textView3);
        id.setText(idMessage);

        TextView pass = findViewById(R.id.textView4);
        pass.setText(passMessage);

        TextView user = findViewById(R.id.textView5);
        user.setText(userMessage);


    }
}
