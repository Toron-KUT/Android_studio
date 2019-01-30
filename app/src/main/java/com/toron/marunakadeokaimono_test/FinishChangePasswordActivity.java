package com.toron.marunakadeokaimono_test;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class FinishChangePasswordActivity extends AppCompatActivity {
    private DatabaseHelper helper;
    private SQLiteDatabase db;
    private RequestQueue mQueue;
    private Map<String, String> params;
    private String PHPURL = "http://222.229.69.53/~goohira/toron/php/updatePassChange.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish_change_password);
        Intent intent = getIntent();
        params = new HashMap<String, String>();
        params.put("password",intent.getStringExtra("PASS"));
        ChangePassword();
    }
    private void ChangePassword(){
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
            StringRequest stringReq=new StringRequest(Request.Method.POST,POST_URL,

                    //通信成功

                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String s) {
                            try{

                                String mString = s;
                                Log.d("degug","通信に成功しました 　結果 == " + mString);

                                if(mString.equals("true")){
                                    Log.d("debug","パスワード更新に成功しました");
                                    String Message = "パスワードを更新しました．";
                                    DisplayFinishChangePassword(Message);
                                }
                                else{
                                    Log.d("debug","ユーザー登録に失敗しました");
                                    String Message = "パスワード更新に失敗しました．";
                                    DisplayFinishChangePassword(Message);
                                }

                                Log.d("debug","InsertUserData Volley Success");
                                //List<Map<String,Object>> mArrayList = helper.GetSpecialSaleData(mJSONArray);
                                //DisplaySpecialSale(mArrayList);
                                //レスポンスが返ってきたらDisplaySpecialSale()を実行


                            }catch(NullPointerException e){
                                Log.d("degug","Nullpointエラー for FinishRegistrationUserActivity " + e.getMessage());
                            }

                        }
                    },

                    //通信失敗
                    new Response.ErrorListener(){
                        @Override
                        public void onErrorResponse(VolleyError error){
                            Log.d("degug","通信に失敗しました" + error.getMessage());
                        }
                    }) {

                //送信するデータを設定
                @Override
                protected Map<String, String> getParams() {

                    //今回はpasswordを設定
                    String mUserID = helper.getUserID(db);
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
            Log.d("debug"," null poirnt exception for FinishRegistrationUserActivity" + e.getMessage());
        }

    }
    private void DisplayFinishChangePassword(String s){
        TextView id = findViewById(R.id.FinishChangePasswordTextView1);
        id.setText(s);
    }
    public void goHolding(View view){
        Intent intent = new Intent(this,  HoldingFoodActivity.class);
        startActivity(intent);
    }
}
