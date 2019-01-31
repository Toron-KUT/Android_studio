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
import android.widget.ImageView;
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

import android.app.Activity;

//public class SpecialSaleActivity extends AppCompatActivity {
public class SpecialSaleActivity extends Activity {
    private TextView mTextMessage;
    private TextView mSpecialSaleData_Store_Name;
    private DatabaseHelper helper;
    private SQLiteDatabase db;
    private RequestQueue mQueue;
    private String PHPURL = "http://222.229.69.53/~goohira/toron/php/getSpecialSale.php";

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
            //タイトルに格納
            //Map<String,String> map = helper.setTopBar(db);
            //Log.d("debug","げっと完了");
            //TextView vi = findViewById(R.id.TopBar_Name);
            //vi.setText("ようこそ　" +map.get("name")+"さん");
            //TextView v2 = findViewById(R.id.TopBar_Point);
            //v2.setText("所有ポイント " + map.get("point"));


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


                    int text_Img = 10 * i + 1;
                    ImageView mImageView = view.findViewById(R.id.SpecialSale_Img1);
                    mImageView.setId(text_Img);

                    int text_name =10 * i + 2;
                    TextView mTextName = view.findViewById(R.id.SpecialSale_Name1);
                    mTextName.setId(text_name);

                    int text_Price = 10 * i + 3;
                    TextView mTextPrice = view.findViewById(R.id.SpecialSale_Price1);
                    mTextPrice.setId(text_Price);

                    Log.d("debug","mSpecialSaleList.store_id == " + mSpecialSale.get("product_name").toString());

                    //String ID = helper.changeIDtoPhoto();
                    if(mSpecialSale.get("category_name").toString().equals("野菜・果物")) {
                        mImageView.setImageResource((R.drawable.vegetables));
                    }
                    else if(mSpecialSale.get("category_name").toString().equals("肉・卵")) {
                        mImageView.setImageResource((R.drawable.meat));
                    }
                    else if(mSpecialSale.get("category_name").toString().equals("魚介類")) {
                        mImageView.setImageResource((R.drawable.fish));
                    }
                    else if(mSpecialSale.get("category_name").toString().equals("米・パン・粉類")) {
                        mImageView.setImageResource((R.drawable.bread));
                    }
                    else if(mSpecialSale.get("category_name").toString().equals("乳製品")) {
                        mImageView.setImageResource((R.drawable.milk));
                    }
                    else if(mSpecialSale.get("category_name").toString().equals("惣菜")) {
                        mImageView.setImageResource((R.drawable.sidedish));
                    }
                    else if(mSpecialSale.get("category_name").toString().equals("インスタント・レトルト")) {
                        mImageView.setImageResource((R.drawable.instant));
                    }
                    else if(mSpecialSale.get("category_name").toString().equals("菓子・冷凍")) {
                        mImageView.setImageResource((R.drawable.kasi));
                    }
                    else if(mSpecialSale.get("category_name").toString().equals("飲料水")) {
                        mImageView.setImageResource((R.drawable.drink));
                    }
                    else if(mSpecialSale.get("category_name").toString().equals("その他(食品)")) {
                        mImageView.setImageResource((R.drawable.other));
                    }
                    else{
                        mImageView.setImageResource((R.drawable.marunaka));
                }


                    //mImageView.setImageResource((R.drawable.marunaka));
                    mTextName.setText(mSpecialSale.get("product_name").toString());
                    mTextPrice.setText(mSpecialSale.get("price").toString()+"円");


                Log.d("debug", "i= " + i);
                //Log.d("debug", " mPurchaseHistoryData_User_ID  ==" + sbuilder_name.toString());
            }


            Log.d("debug","mSpecialSaleList.size=" + mSpecialSaleList.size());

        } catch(NullPointerException e) {
                Log.d("debug"," null poirnt exception for DisplaySpecialSale " + e.getMessage());
        }
    }
}


