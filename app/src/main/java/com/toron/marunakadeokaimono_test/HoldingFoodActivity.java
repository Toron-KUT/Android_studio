package com.toron.marunakadeokaimono_test;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

public class HoldingFoodActivity extends AppCompatActivity {

    private TextView mTextMessage;
    private TextView mHoldingFoodData_Product_Name;
    private TextView mHoldingFoodData_Num;
    private TextView mHoldingFoodData_CreateDate;
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
        setContentView(R.layout.activity_holding_food);

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        Button readButton = findViewById(R.id.button5);
        readButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GetHoldingFoodData();

            }
        });
    }

    private void GetHoldingFoodData(){
        try{
            if (helper == null) {
                helper = new DatabaseHelper(getApplicationContext());
            }
            if (db == null) {
                db = helper.getReadableDatabase();

            }
            List<Map<String,Object>> HoldList = helper.GetHoldingFoodData(db);
            if(HoldList!=null){
                Log.d("debug","HoldList success");
                DisplayHoldingFood(HoldList);
            }
            else{
                Log.d("debug","Holdlist failed");
            }

        }catch(NullPointerException e){
            Log.d("debug"," null poirnt exception " + e.getMessage());
        }
    }
    private void DisplayHoldingFood(List<Map<String,Object>> mHoldingFoodList){
        try{
            mHoldingFoodData_Product_Name = findViewById(R.id.textView21);
            mHoldingFoodData_Num = findViewById(R.id.textView22);
            mHoldingFoodData_CreateDate = findViewById(R.id.textView23);
            for(int i = 0;i < mHoldingFoodList.size();i++) {
                Map<String, Object> mFoodData = mHoldingFoodList.get(i);
                mHoldingFoodData_Product_Name.setText(mFoodData.get("product_name").toString());
                mHoldingFoodData_Num.setText(mFoodData.get("num").toString());
                mHoldingFoodData_CreateDate.setText(mFoodData.get("product_name").toString());
            }
            Log.d("debug","mHoldingFoodList.size=" + mHoldingFoodList.size());

        }catch(NullPointerException e){
            Log.d("debug","DisplayHoldingFood null poirnt exception " + e.getMessage());
        }
    }

}
