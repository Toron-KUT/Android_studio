package com.toron.marunakadeokaimono_test;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
    private String PHPURL = "http://172.21.48.127/server_php/Toron_BackEnd/php/getStore.php";

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
            //通信の発行
            mQueue = Volley.newRequestQueue(this);
            String POST_URL = PHPURL;
            StringRequest stringReq=new StringRequest(Request.Method.GET,POST_URL,

                    //通信成功
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String s) {
                            try{
                                Log.d("degug","通信に成功しました");
                                JSONObject mJSONObject = new JSONObject(s);
                                JSONArray mJSONArray = mJSONObject.getJSONArray("stores");

                                Log.d("debug","getSotre Volley Success");
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
            mSpecialSaleData_Store_Name = findViewById(R.id.textView34);

            StringBuilder sbuilder_Store_Name = new StringBuilder();


            for(int i = 0;i < mSpecialSaleList.size();i++) {
                Map<String, Object> mFoodData = mSpecialSaleList.get(i);

                sbuilder_Store_Name.append(mFoodData.get("name").toString() + "\n");

                Log.d("debug","i= " + i);
            }
            mSpecialSaleData_Store_Name.setText(sbuilder_Store_Name.toString());


            Log.d("debug","mSpecialSaleList.size=" + mSpecialSaleList.size());

        } catch(NullPointerException e) {
                Log.d("debug"," null poirnt exception for DisplaySpecialSale " + e.getMessage());
        }
    }
}


