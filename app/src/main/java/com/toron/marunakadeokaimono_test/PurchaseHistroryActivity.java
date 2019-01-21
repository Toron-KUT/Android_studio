package com.toron.marunakadeokaimono_test;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

public class PurchaseHistroryActivity extends AppCompatActivity {

    private TextView mTextMessage;
    private TextView mPurchaseHistoryData_User_ID;
    private TextView mPurchaseHistoryData_Password;
    private DatabaseHelper helper;
    private SQLiteDatabase db;

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
        setContentView(R.layout.activity_purchase_histrory);

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        Button readButton = findViewById(R.id.button10);
        readButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GetPurchaseHistoryData();

            }
        });
    }

    private void GetPurchaseHistoryData() {
        try {
            if (helper == null) {
                helper = new DatabaseHelper(getApplicationContext());
            }
            if (db == null) {
                db = helper.getReadableDatabase();

            }
            Log.d("debug", "test for PurchaseHistory before");


            List<Map<String, Object>> mPurchaseHistory = helper.GetPurchaseHistoryData(this);
            Log.d("debug", "test for PurchaseHistory after");


            if (mPurchaseHistory != null) {
                Log.d("debug", "Test購入履歴 success");
                DisplayPurchaseHistory(mPurchaseHistory);
            } else {
                Log.d("debug", "Tset購入履歴 failed");
            }

        } catch (NullPointerException e) {
            Log.d("debug", " null poirnt exception in GetPurchaceHistoryData for RurchaceHistoryActivity " + e.getMessage());
        }
    }

    private void DisplayPurchaseHistory(List<Map<String, Object>> mPurchaseHistoryList) {
        try {
            mPurchaseHistoryData_User_ID = findViewById(R.id.textView30);
            mPurchaseHistoryData_Password = findViewById(R.id.textView32);

            StringBuilder sbuilder_User_ID = new StringBuilder();
            StringBuilder sbuilder_Password = new StringBuilder();


            for (int i = 0; i < mPurchaseHistoryList.size(); i++) {
                Map<String, Object> mPurchaseHistory = mPurchaseHistoryList.get(i);

                sbuilder_User_ID.append(mPurchaseHistory.get("userid").toString() + "\n");
                sbuilder_Password.append(mPurchaseHistory.get("password").toString() + "\n");

                Log.d("debug", "i= " + i);
                Log.d("debug", " mPurchaseHistoryData_User_ID  ==" + sbuilder_User_ID.toString());


            }
            mPurchaseHistoryData_User_ID.setText(sbuilder_User_ID.toString());
            mPurchaseHistoryData_Password.setText(sbuilder_Password.toString());

            Log.d("debug", "mPurchaseHistory.size=" + mPurchaseHistoryList.size());

        } catch (NullPointerException e) {
            Log.d("debug", "Display PurchaseHistory null poirnt exception " + e.getMessage());
        }
    }

    public void goSpecialSale(View view) {
        Intent intent = new Intent(this, SpecialSaleActivity.class);
        startActivity(intent);
    }

    //onResponce(){

    //}

}
