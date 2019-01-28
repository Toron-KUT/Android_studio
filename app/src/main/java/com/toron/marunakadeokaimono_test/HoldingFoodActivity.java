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

public class HoldingFoodActivity extends AppCompatActivity {

    private TextView mTextMessage;
    private TextView mHoldingFoodData_Product_Name;
    private TextView mHoldingFoodData_Num;
    private TextView mHoldingFoodData_CreateDate;
    private DatabaseHelper helper;
    private SQLiteDatabase db;
    private RequestQueue mQueue;
    private String PHPURL = "http://172.21.48.127/server_php/Toron_BackEnd/php/getHoldingFood.php";
    // private String PHPURL =   "http://172.21.48.127/server_php/Toron_BackEnd/php/getStore.php";

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    TransitionFavoriteShopActivity();
                    mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_dashboard:
                    TransitionFavoriteShopActivity();
                    mTextMessage.setText(R.string.title_dashboard);
                    return true;
                case R.id.navigation_notifications:
                    TransitionFavoriteShopActivity();
                    mTextMessage.setText(R.string.title_notifications);
                    return true;
                case R.id.navigation_other:
                    TransitionFavoriteShopActivity();
                    mTextMessage.setText(R.string.title_other);
                    return true;
            }
            return false;
        }
    };
    private void TransitionFavoriteShopActivity() {
        Intent intent = new Intent(this, PurchaseHistroryActivity.class);
        startActivity(intent);
    }

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
                                
                                //List<Map<String,Object>> mArrayList = helper.GetSpecialSaleData(mJSONArray);
                                //DisplaySpecialSale(mArrayList);
                                //レスポンスが返ってきたらDisplaySpecialSale()を実行


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




            //List<Map<String,Object>> SpecialSaleList = helper.GetSpecialSaleData(helper,this);
            //if(SpecialSaleList!=null){
            //    Log.d("debug","SpecialSaleList success");
            //    DisplaySpecialSale(SpecialSaleList);
            //}
            //else{
            //    Log.d("debug","SpecialSaleList failed");
            //}
        }catch(NullPointerException e){
            Log.d("debug"," null poirnt exception " + e.getMessage());
        }
    }
    private void DisplayHoldingFood(List<Map<String,Object>> mHoldingFoodList){
        try{
            mHoldingFoodData_Product_Name = findViewById(R.id.textView21);
            mHoldingFoodData_Num = findViewById(R.id.textView22);
            mHoldingFoodData_CreateDate = findViewById(R.id.textView23);
            StringBuilder sbuilder_Product_Name = new StringBuilder();
            StringBuilder sbuilder_Num = new StringBuilder();
            StringBuilder sbuilder_CreateDate= new StringBuilder();

            for(int i = 0;i < mHoldingFoodList.size();i++) {
                Map<String, Object> mFoodData = mHoldingFoodList.get(i);

                sbuilder_Product_Name.append(mFoodData.get("name").toString() + "\n");
                sbuilder_Num.append(mFoodData.get("num").toString() + "\n");
                sbuilder_CreateDate.append(mFoodData.get("createDate").toString() + "\n");
                Log.d("debug","i= " + i);


            }
            mHoldingFoodData_Product_Name.setText(sbuilder_Product_Name.toString());
            mHoldingFoodData_Num.setText(sbuilder_Num.toString());
            mHoldingFoodData_CreateDate.setText(sbuilder_CreateDate.toString());

            Log.d("debug","mHoldingFoodList.size=" + mHoldingFoodList.size());

        }catch(NullPointerException e){
            Log.d("debug","DisplayHoldingFood null poirnt exception " + e.getMessage());
        }
    }

}
