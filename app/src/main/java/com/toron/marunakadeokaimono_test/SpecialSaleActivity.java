package com.toron.marunakadeokaimono_test;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

public class SpecialSaleActivity extends AppCompatActivity {
    private TextView mTextMessage;
    private TextView mSpecialSaleData_Store_Name;
    private DatabaseHelper helper;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_special_sale);
        Button readButton = findViewById(R.id.button12);
        readButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GetSpecialSaleData();

            }
        });
    }

    private void GetSpecialSaleData(){
        try{
            if (helper == null) {
                helper = new DatabaseHelper(getApplicationContext());
            }
            if (db == null) {
                db = helper.getReadableDatabase();

            }
            List<Map<String,Object>> SpecialSaleList = helper.GetSpecialSaleData(helper,this);
            if(SpecialSaleList!=null){
                Log.d("debug","SpecialSaleList success");
                DisplaySpecialSale(SpecialSaleList);
            }
            else{
                Log.d("debug","SpecialSaleList failed");
            }

        }catch(NullPointerException e){
            Log.d("debug"," null poirnt exception " + e.getMessage());
        }

    }

    private void DisplaySpecialSale(List<Map<String,Object>> mSpecialSaleList){
        try{
            mSpecialSaleData_Store_Name = findViewById(R.id.textView21);

            StringBuilder sbuilder_Store_Name = new StringBuilder();


            for(int i = 0;i < mSpecialSaleList.size();i++) {
                Map<String, Object> mFoodData = mSpecialSaleList.get(i);

                sbuilder_Store_Name.append(mFoodData.get("product_name").toString() + "\n");

                Log.d("debug","i= " + i);
            }
            mSpecialSaleData_Store_Name.setText(sbuilder_Store_Name.toString());


            Log.d("debug","mSpecialSaleList.size=" + mSpecialSaleList.size());

        } catch(NullPointerException e) {
                Log.d("debug","DisplaySpecialSale null poirnt exception " + e.getMessage());
        }
    }
}


