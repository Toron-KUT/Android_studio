package com.toron.marunakadeokaimono_test;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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

public class FinishRegistrationUserActivity extends AppCompatActivity {
    private TextView textView;
    private DatabaseHelper helper;
    private SQLiteDatabase db;
    private RequestQueue mQueue;
    private  Map<String, String> params;
    private String PHPURL = "http://172.21.48.127/server_php/Toron_BackEnd/php/insertNewUser.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish_registration_user);
        //readData();
        Intent intent = getIntent();
        //送信するデータを設定，格納
        params = new HashMap<String, String>();
        Log.d("debug","debug message user ==  " +intent.getStringExtra("ID") + " - " +intent.getStringExtra("PASS") + " - " + intent.getStringExtra("USER") + " - " +intent.getStringExtra("SECURE"));
        params.put("login_id",intent.getStringExtra("ID"));
        params.put("password",intent.getStringExtra("PASS"));
        params.put("name",intent.getStringExtra("USER"));
        params.put("ruby",intent.getStringExtra("RUBY"));
        params.put("waon",intent.getStringExtra("WAON"));
        params.put("security",intent.getStringExtra("SECURE"));


        //InsertNewUserData(params);
        Button readButton = findViewById(R.id.FinishRegistrationUserbutton1);

        readButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GoAuthenticationUser();
            }
        });
    }

    private void readData() {
        textView = findViewById(R.id.textView13);

        if(helper == null){
            helper = new DatabaseHelper(getApplicationContext());
        }

        if(db == null){
            db = helper.getReadableDatabase();

        }
        Log.d("debug","openUserData");
        //String sql = "select * from u";
        //Cursor cursor = db.rawQuery(sql,null);
        Cursor cursor = db.query(
                "users",
                new String[] { "user_id", "login_id" ,"name","ruby","password","waon","security"} ,
                null,
                null,
                null,
                null,
                null
        );

        cursor.moveToFirst();

        StringBuilder sbuilder = new StringBuilder();


        String[] columnNames = cursor.getColumnNames();
        int length = columnNames.length;
        for (int i = 0; i < cursor.getCount(); i++) {
            String value = null;
            try {
                sbuilder.append(cursor.getInt(0));
                sbuilder.append(": ");
                sbuilder.append(cursor.getString(1));
                sbuilder.append("\n");
                sbuilder.append(cursor.getString(2));
                sbuilder.append("\n");
                sbuilder.append(cursor.getString(3));
                sbuilder.append("\n");
                sbuilder.append(cursor.getString(4));
                sbuilder.append("\n");

                cursor.moveToNext();
            } catch (SQLiteException e) {
                Log.d("debud","SQLError" + e.getMessage());
            }
            Log.d("dumpLastRecord", columnNames[i] + " : " + value);

        }
        cursor.close();

        Log.d("debug","**********"+sbuilder.toString());
        textView.setText(sbuilder.toString());
    }


    private void InsertNewUserData(Map<String, String> mParams){
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
                                    Log.d("debug","ユーザー登録に成功しました");
                                    String Message = "ユーザー情報を登録しました．";
                                    DisplayFinishRegistration(Message);
                                }
                                else{
                                    Log.d("debug","ユーザー登録に失敗しました");
                                    String Message = "ユーザー登録に失敗しました．";
                                    DisplayFinishRegistration(Message);
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

                    //今回は[FastText：名前]と[SecondText：内容]を設定

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

    private void DisplayFinishRegistration(String mMessage){
        final TextView id = findViewById(R.id.FinishRegistrationUserTextView1);
        id.setText(mMessage);
    }

    private void GoAuthenticationUser(){
        Intent intent = new Intent(this, AuthenticationUserActivity.class);
        startActivity(intent);
    }

}
