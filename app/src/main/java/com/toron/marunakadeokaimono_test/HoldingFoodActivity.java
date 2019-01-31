package com.toron.marunakadeokaimono_test;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HoldingFoodActivity extends AppCompatActivity  implements View.OnClickListener{

    private TextView mTextMessage;
    private TextView mHoldingFoodData_Product_Name;
    private TextView mHoldingFoodData_Num;
    private TextView mHoldingFoodData_CreateDate;
    private DatabaseHelper helper;
    private SQLiteDatabase db;
    private RequestQueue mQueue;
    private Map<String,String> mHoldingFoodData;
    private String PHPURL = "http://222.229.69.53/~goohira/toron/php/getHoldingFood.php";
    // private String PHPURL =   "http://172.21.48.127/server_php/Toron_BackEnd/php/getStore.php";

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
        setContentView(R.layout.activity_holding_food);

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        GetHoldingFoodData();




        //Button readButton = findViewById(R.id.button5);
        //readButton.setOnClickListener(new View.OnClickListener() {
       //     @Override
        //    public void onClick(View v) {
         //       GetHoldingFoodData();
//
         //   }
        //});
    }

    private void GetHoldingFoodData(){
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
            //Log.d("debug","URL for Holdingfood = " + POST_URL);
            StringRequest stringReq=new StringRequest(Request.Method.POST,POST_URL,

                    //通信成功
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String s) {
                            try{

                                String mString = s;
                                Log.d("degug", "通信に成功しました 　結果 == " + mString);
                                if(mString.equals("false")) {
                                    Log.d("debug","取得失敗");
                                }
                                else{
                                    Log.d("degug", "通信に成功しました 　結果 == " + mString);
                                    JSONObject mJSONObject = new JSONObject(s);
                                    JSONArray mJSONArray = mJSONObject.getJSONArray("hold");

                                    List<Map<String, Object>> mHoldList = helper.GetHoldingFoodData(mJSONArray);

                                    DisplayHoldingFood(mHoldList);
                                }
                                
                                //List<Map<String,Object>> mArrayList = helper.GetHoldingFoodData(mJSONArray);
                                //DisplayHoldingFood(mArrayList);
                                //レスポンスが返ってきたらDisplayHoldingFood()を実行


                            }catch(NullPointerException e){
                                Log.d("degug","Nullpointエラー for HoldingFoodActivity " + e.getMessage());
                            } catch (JSONException e1) {
                                e1.printStackTrace();
                            }

                        }
                    },

                    //通信失敗
                    new Response.ErrorListener(){
                        @Override
                        public void onErrorResponse(VolleyError error){
                            Log.d("degug","通信に失敗しました " + error.getMessage());
                        }
                    }) {

                //送信するデータを設定
                @Override
                protected Map<String, String> getParams() {
                    //今回はUserIDを渡す

                    Map<String,String>params = new HashMap<String, String>();

                    String mUserID = helper.getUserID(db);
                    //String mUserID = "12";
                    Log.d("debug","getting UserID...   " + mUserID );
                    params.put("user_id",mUserID);
                    return params;

                }
            };
            mQueue.add(stringReq);
            //リクエストキューを発行




            //List<Map<String,Object>> HoldingFoodList = helper.GetHoldingFoodData(helper,this);
            //if(HoldingFoodList!=null){
            //    Log.d("debug","HoldingFoodList success");
            //    DisplayHoldingFood(HoldingFoodList);
            //}
            //else{
            //    Log.d("debug","HoldingFoodList failed");
            //}
        }catch(NullPointerException e){
            Log.d("debug"," null poirnt exception " + e.getMessage());
        }
    }
    private void DisplayHoldingFood(List<Map<String,Object>> mHoldingFoodList){
        try{
            ViewGroup table = (ViewGroup) findViewById(R.id.table_HoldingFood);
            Log.d("debug","mHoldingFoodList.size == " +  mHoldingFoodList.size());


            for (int i = 1; i <=  mHoldingFoodList.size(); i++) {
                Map<String, Object> mHoldingFood = mHoldingFoodList.get(i - 1);

                View view = getLayoutInflater().inflate(R.layout.tablelayout_holdingfood, table);


                int text_name = 10 * i + 1;
                TextView mTextName = view.findViewById(R.id.tableView_HoldingFood1);
                mTextName.setId(text_name);

                int text_price = 10 * i + 2;
                TextView mTextPrice = view.findViewById(R.id.tableView_HoldingFood2);
                mTextPrice.setId(text_price);

                int text_Category = 10 * i + 3;
                TextView mTextCategory = view.findViewById(R.id.tableView_HoldingFood3);
                mTextCategory.setId(text_Category);

                int DeleteButton = 10 * i + 4;
                Button mDeleteButton = view.findViewById(R.id.tableView_HoldingFood4);
                mDeleteButton.setId(DeleteButton);

                Log.d("debug", "mHoldingFoodList.store_id == " + mHoldingFood.get("name").toString());


                mTextName.setText(mHoldingFood.get("name").toString());
                mTextPrice.setText(mHoldingFood.get("num").toString());
                mTextCategory.setText(mHoldingFood.get("createDate").toString());
                //Map<String,Object> list = new HashMap<String,Object>();



                Log.d("debug", "i= " + i);
            }


            Log.d("debug","mHoldingFoodList.size=" + mHoldingFoodList.size());
            settingButton(mHoldingFoodList);

        }catch(NullPointerException e){
            Log.d("debug","DisplayHoldingFood null poirnt exception " + e.getMessage());
        }
    }
    private void settingButton(final List<Map<String,Object>> mHoldingFoodList) {
        try{
            for(int i=1;i<=mHoldingFoodList.size();i++) {
                // display_menu_listからメニュー情報を取得
                final Map<String, Object> mHoldingFood = mHoldingFoodList.get(i - 1);
                Log.d("debug"," " + mHoldingFoodList.size());
                findViewById(i * 10 + 4).setOnClickListener(this);
                findViewById(i * 10 + 4).setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        Log.d("debug","クリックされました " + v.getId() + " " +mHoldingFood.get("name").toString());
                        mHoldingFoodData = new HashMap<String,String>();
                        mHoldingFoodData.put("product_name", mHoldingFood.get("name").toString());
                        mHoldingFoodData.put("createDate", mHoldingFood.get("createDate").toString());

                        Delete();
                        return true;
                    }
                });
            }
        }catch(ClassCastException e){
            Log.d("debug","error for " + e.getMessage());
        }
    }
    public void Delete(){
        try{
            if (helper == null) {
                helper = new DatabaseHelper(getApplicationContext());
            }
            if (db == null) {
                db = helper.getReadableDatabase();

            }
            //通信の発行
            mQueue = Volley.newRequestQueue(this);
            String POST_URL = "http://222.229.69.53/~goohira/toron/php/deleteHoldingFood.php";
            //Log.d("debug","URL for Holdingfood = " + POST_URL);
            StringRequest stringReq=new StringRequest(Request.Method.POST,POST_URL,

                    //通信成功
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String s) {
                            try{

                                String mString = s;
                                Log.d("degug", "通信に成功しました 　結果 == " + mString);
                                if(mString.equals("false")) {
                                    Log.d("debug","取得失敗");
                                }
                                else{
                                    Log.d("degug", "通信に成功しました 　結果 == " + mString);

                                    resetHoldingFood();

                                }

                                //List<Map<String,Object>> mArrayList = helper.GetHoldingFoodData(mJSONArray);
                                //DisplayHoldingFood(mArrayList);
                                //レスポンスが返ってきたらDisplayHoldingFood()を実行


                            }catch(NullPointerException e){
                                Log.d("degug","Nullpointエラー for HoldingFoodActivity " + e.getMessage());
                            }

                        }
                    },

                    //通信失敗
                    new Response.ErrorListener(){
                        @Override
                        public void onErrorResponse(VolleyError error){
                            Log.d("degug","通信に失敗しました " + error.getMessage());
                        }
                    }) {

                //送信するデータを設定
                @Override
                protected Map<String, String> getParams() {
                    //今回はUserIDを渡す



                    String mUserID = helper.getUserID(db);
                    //String mUserID = "12";
                    Log.d("debug","getting UserID...   " + mUserID );
                    mHoldingFoodData.put("user_id",mUserID);
                    return mHoldingFoodData;

                }
            };
            mQueue.add(stringReq);
            //リクエストキューを発行




            //List<Map<String,Object>> HoldingFoodList = helper.GetHoldingFoodData(helper,this);
            //if(HoldingFoodList!=null){
            //    Log.d("debug","HoldingFoodList success");
            //    DisplayHoldingFood(HoldingFoodList);
            //}
            //else{
            //    Log.d("debug","HoldingFoodList failed");
            //}
        }catch(NullPointerException e){
            Log.d("debug"," null poirnt exception " + e.getMessage());
        }


    }
    public void  resetHoldingFood(){
        Intent intent = new Intent(this,HoldingFoodActivity.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View view) {

    }
}
