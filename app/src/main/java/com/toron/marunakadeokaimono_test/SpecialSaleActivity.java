package com.toron.marunakadeokaimono_test;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;

public class SpecialSaleActivity extends AppCompatActivity {
    private TextView mTextMessage;
    private TextView mSpecialSaleData_Store_Name;
    private DatabaseHelper helper;
    private SQLiteDatabase db;
    private RequestQueue mQueue;
    private String PHPURL = "http://172.21.48.127/server_php/Toron_BackEnd/php/getSpecialSale.php";

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    TransitionHoldingFoodActivity();
                    //mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_dashboard:
                    TransitionPurchaseHistoryActivity();
                    //mTextMessage.setText(R.string.title_dashboard);
                    return true;
                case R.id.navigation_notifications:
                    TransitionSpecialSaleActivity();
                    //mTextMessage.setText(R.string.title_notifications);
                    return true;
                case R.id.navigation_other:
                    TransitionOtherActivity();
                    //mTextMessage.setText(R.string.title_other);
                    return true;
            }
            return false;
        }
    };
    private void TransitionHoldingFoodActivity() {
        Intent intent = new Intent(this, HoldingFoodActivity.class);
        startActivity(intent);
    }
    private void TransitionPurchaseHistoryActivity() {
        Intent intent = new Intent(this, PurchaseHistroryActivity.class);
        startActivity(intent);
    }
    private void TransitionSpecialSaleActivity() {
        Intent intent = new Intent(this, SpecialSaleActivity.class);
        startActivity(intent);
    }

    private void TransitionOtherActivity() {
        Intent intent = new Intent(this, OtherActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_special_sale);
        //mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        GetSpecialSaleData();
        //Button readButton = findViewById(R.id.button12);
        //readButton.setOnClickListener(new View.OnClickListener() {
        //    @Override
        //    public void onClick(View v) {
        //        GetSpecialSaleData();

        //    }
        //});
    }

    private void GetSpecialSaleData(){
        try{
            if (helper == null) {
                helper = new DatabaseHelper(getApplicationContext());
            }
            if (db == null) {
                db = helper.getReadableDatabase();

            }
            //通信の発行
            mQueue = Volley.newRequestQueue(this);
            String POST_URL = PHPURL;
            StringRequest stringReq=new StringRequest(Request.Method.GET,POST_URL,

                    //通信成功
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String s) {
                            try{
                                Log.d("degug","通信に成功しました SpecialSale Data = " + s);
                                JSONObject mJSONObject = new JSONObject(s);
                                JSONArray mJSONArray = mJSONObject.getJSONArray("sp_sale");

                                Log.d("debug","getSale Volley Success");
                                List<Map<String,Object>> mArrayList = helper.GetSpecialSaleData(mJSONArray);
                                DisplaySpecialSale(mArrayList);
                                //レスポンスが返ってきたらDisplaySpecialSale()を実行


                            }catch(JSONException e){
                                Log.d("degug","JSONエラー" + e.getMessage());
                            }

                        }
                    },

                    //通信失敗
                    new Response.ErrorListener(){
                        @Override
                        public void onErrorResponse(VolleyError error){
                            Log.d("degug","通信に失敗しました" + error.getMessage());
                        }
                    });

            mQueue.add(stringReq);
            //リクエストキューを発行




            //List<Map<String,Object>> SpecialSaleList = helper.GetSpecialSaleData(helper,this);
            //if(SpecialSaleList!=null){
            //    Log.d("debug","SpecialSaleList success");
            //    DisplaySpecialSale(SpecialSaleList);
            //}
            //else{
            //    Log.d("debug","SpecialSaleList failed");
            //}

        }catch(NullPointerException e){
            Log.d("debug"," null poirnt exception for GetSpecialSaleData" + e.getMessage());
        }

    }

    private void DisplaySpecialSale(List<Map<String,Object>> mSpecialSaleList){
        try{
            ViewGroup table = (ViewGroup) findViewById(R.id.table_SpecialSale);
            Log.d("debug","mSpecialSaleList.size == " +  mSpecialSaleList.size());


            for (int i = 1; i <=  mSpecialSaleList.size(); i++) {
                Map<String, Object> mSpecialSale = mSpecialSaleList.get(i-1);

                View view = getLayoutInflater().inflate(R.layout.tablelayout_specialsale, table);


                int text_name = 10 * i + 1;
                TextView mTextName = view.findViewById(R.id.tableView_SpecialSale1);
                mTextName.setId(text_name);

                int text_price =10 * i + 2;
                TextView mTextPrice = view.findViewById(R.id.tableView_SpecialSale2);
                mTextPrice.setId(text_price);

                int text_Category = 10 * i + 3;
                TextView mTextCategory = view.findViewById(R.id.tableView_SpecialSale3);
                mTextCategory.setId(text_Category);

                Log.d("debug","mSpecialSaleList.store_id == " + mSpecialSale.get("name").toString());

                
                mTextName.setText(mSpecialSale.get("name").toString());
                mTextPrice.setText(mSpecialSale.get("price").toString());
                mTextCategory.setText(mSpecialSale.get("category_name").toString());


                Log.d("debug", "i= " + i);
                //Log.d("debug", " mPurchaseHistoryData_User_ID  ==" + sbuilder_name.toString());
            }


            Log.d("debug","mSpecialSaleList.size=" + mSpecialSaleList.size());

        } catch(NullPointerException e) {
                Log.d("debug"," null poirnt exception for DisplaySpecialSale " + e.getMessage());
        }
    }
}


