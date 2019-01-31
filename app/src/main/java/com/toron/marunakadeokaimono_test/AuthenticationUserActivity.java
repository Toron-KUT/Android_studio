package com.toron.marunakadeokaimono_test;

import android.app.Activity;
import android.app.MediaRouteButton;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.ArrayMap;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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
import java.util.Map;

public class AuthenticationUserActivity extends AppCompatActivity {
    private DatabaseHelper helper;
    private SQLiteDatabase db;
    private RequestQueue mQueue;

    private String PHPURL = "http://222.229.69.53/~goohira/toron/php/getLoginInfo.php";
    private EditText editTextUser, editTextPassword;
    private Map<String,String> mAuthenticationUserData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication_user);
        //Button readButton = findViewById(R.id.button5);
        editTextUser = findViewById(R.id.edit_text_User);
        editTextPassword = findViewById(R.id.edit_text_Password);

        Button ReadButton = findViewById(R.id.AuthenticationUser1);

        ReadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AuthenticationUser();

            }
        });
        Button goButton = findViewById(R.id.AuthenticationUser2);

        goButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goRegistration();

            }
        });
    }

    private void AuthenticationUser() {
        try {
            if (helper == null) {
                helper = new DatabaseHelper(getApplicationContext());
            }
            if (db == null) {
                db = helper.getReadableDatabase();
            }
            mAuthenticationUserData = new HashMap<String,String>();
            Log.d("debug","starting AuthenticationUser....");
            //JSON値の格納
            //Map<String,Object> mAuthenticationUser = new HashMap<String,Object>();
            String ID = editTextUser.getText().toString();
            String Password = editTextPassword.getText().toString();
            Log.d("debug","login_id ==  " + ID + "password == " + Password);
            mAuthenticationUserData.put("login_id", ID);
            mAuthenticationUserData.put("password", Password);


            mQueue = Volley.newRequestQueue(this);
            String POST_URL = PHPURL;
            StringRequest stringReq = new StringRequest(Request.Method.POST, POST_URL,

                    //通信成功
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String s) {
                            try {

                                //String mString = s;
                                Log.d("degug", "通信に成功しました 　結果 == " + s);


                                if (s.equals("false")) {
                                    Log.d("debug", "ユーザー認証に失敗しました");

                                    String Message = "ユーザー認証に失敗";

                                } else {
                                    Log.d("debug", "ユーザー認証に成功しました");
                                    String Message = "ユーザー認証に成功．";
                                    JSONObject mJSONObject = new JSONObject(s);
                                    //Log.d("debug","ここまではoK");
                                    JSONArray mJSONArray = mJSONObject.getJSONArray("users");
                                    //JSONObject mmJSONObject = mJSONObject.getJSONObject("");
                                    Log.d("debug","wao");
                                    SuccessAuthenticationUser(mJSONArray);


                                }

                                Log.d("debug", "InsertUserData Volley Success");
                                //List<Map<String,Object>> mArrayList = helper.GetSpecialSaleData(mJSONArray);
                                //DisplaySpecialSale(mArrayList);
                                //レスポンスが返ってきたらDisplaySpecialSale()を実行


                            } catch (JSONException e) {
                                Log.d("degug", "JSONエラー for AuthenticationUserActivity " + e.getMessage());
                            }


                        }
                    },

                    //通信失敗
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d("degug", "通信に失敗しました" + error.getMessage());
                        }
                    }) {

                //送信するデータを設定
                @Override
                protected Map<String, String> getParams() {

                    //今回は[FastText：名前]と[SecondText：内容]を設定

                    return mAuthenticationUserData;
                }
            };

            mQueue.add(stringReq);
            //リクエストキューを発行

        }catch(SQLiteException e){
            Log.d("debug","ユーザー認証失敗" + e.getMessage());
        }//catch(NullPointerException e){
        //    Log.d("debug","null pointer Excepciton " +e.getMessage());
        //}
    }

    private void TransitionHomeActivity() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);

    }
    public void TransitionAuthenticationUserActivity() {
        Intent intent = new Intent(this, AuthenticationUserActivity.class);
        startActivity(intent);

    }
    public void  SuccessAuthenticationUser(JSONArray mJSONArray){
        boolean success = helper.SetAuthenticationUserData(mJSONArray,db);

        if(success == true){

            Log.d("debug","ユーザー情報のセットに成功しました");
            //Intent intent = new Intent(this, HomeActivity.class);
            Intent intent = new Intent(this, HoldingFoodActivity.class);
            startActivity(intent);

        }



    }
    public void goRegistration(){
        Intent intent = new Intent(this, RegistrationUserActivity.class);
        startActivity(intent);
    }

}
