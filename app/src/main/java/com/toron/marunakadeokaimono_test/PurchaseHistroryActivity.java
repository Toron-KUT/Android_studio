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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PurchaseHistroryActivity extends AppCompatActivity {

    private TextView mTextMessage;
    private TextView mPurchaseHistoryData_name;
    private TextView mPurchaseHistoryData_num;
    private TextView mPurchaseHistoryData_price;
    private TextView mPurchaseHistoryData_createDate;
    private DatabaseHelper helper;
    private SQLiteDatabase db;
    private RequestQueue mQueue;
    private String PHPURL = "http://222.229.69.53/~goohira/toron/php/getPurchaseHistory.php";

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    TransitionHoldingFoodActivity();
                    mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_dashboard:
                    TransitionPurchaseHistoryActivity();
                    mTextMessage.setText(R.string.title_dashboard);
                    return true;
                case R.id.navigation_notifications:
                    TransitionSpecialSaleActivity();
                    mTextMessage.setText(R.string.title_notifications);
                    return true;
                case R.id.navigation_other:
                    TransitionOtherActivity();
                    mTextMessage.setText(R.string.title_other);
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
        setContentView(R.layout.activity_purchase_histrory);

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        Button readButton = findViewById(R.id.button10);
        readButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GetPurchaseHistoryData();

            }
        });
    }

    private void GetPurchaseHistoryData() {
        try {
            if (helper == null) {
                helper = new DatabaseHelper(getApplicationContext());
            }
            if (db == null) {
                db = helper.getReadableDatabase();

            }
            Log.d("debug", "test for PurchaseHistory before");

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
                                if(mString.equals("false")) {
                                    Log.d("debug","取得失敗");
                                }
                                else{
                                    Log.d("degug", "通信に成功しました 　結果 == " + mString);
                                    JSONObject mJSONObject = new JSONObject(s);
                                    JSONArray mJSONArray = mJSONObject.getJSONArray("purchase");

                                    List<Map<String, Object>> mPurchaseHistory = helper.GetPurchaseHistoryData(mJSONArray);

                                    DisplayPurchaseHistory(mPurchaseHistory);
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

    private void DisplayPurchaseHistory(List<Map<String, Object>> mPurchaseHistoryList) {
        try {
            // TableLayoutのグループを取得
            ViewGroup table = (ViewGroup)findViewById(R.id.table_Purchase);

            // 行を追加
            //mPurchaseHistoryData_name = findViewById(R.id.textView30);
            //mPurchaseHistoryData_num = findViewById(R.id.textView31);
            //mPurchaseHistoryData_price = findViewById(R.id.textView33);
            //mPurchaseHistoryData_createDate = findViewById(R.id.textView32);

            //StringBuilder sbuilder_name = new StringBuilder();
            //StringBuilder sbuilder_num = new StringBuilder();
            //StringBuilder sbuilder_price = new StringBuilder();
            //StringBuilder sbuilder_createDate = new StringBuilder();


            for (int i = 1; i < mPurchaseHistoryList.size(); i++) {
                Map<String, Object> mPurchaseHistory = mPurchaseHistoryList.get(i-1);

                View view = getLayoutInflater().inflate(R.layout.tablelayout_purchase, table);

                //int text_id = 1;
                //TextView text = view.findViewById(R.id.tableView2);
                //text.setId(text_id);
                //text.setText(mPurchaseHistory.get("name").toString());
                // テキストを変更

                int text_name = 10 * i + 1;
                TextView mTextName = view.findViewById(R.id.tableView_Purchase1);
                mTextName.setId(text_name);

                int text_price = 10 * i+ 2;
                TextView mTextPrice = view.findViewById(R.id.tableView_Purchase2);
                mTextPrice.setId(text_price);

                int text_num = 10 * i+ 3;
                TextView mTextNum = view.findViewById(R.id.tableView_Purchase3);
                mTextNum.setId(text_num);

                int text_createDate = 10 * i+ 4;
                TextView mTextCreateDate = view.findViewById(R.id.tableView_Purchase4);
                mTextCreateDate.setId(text_createDate);
                Log.d("debug","mFavoriteShopList.store_id == " + mPurchaseHistory.get("name").toString());

                //int text_name = 10 * Integer.parseInt(mPurchaseHistory.get("store_id").toString() + 2);
                //TextView mTextName = view.findViewById(R.id.tableView_Favorite2);
                //mTextName.setId(text_name);


                //sbuilder_num.append(mPurchaseHistory.get("num").toString() + "\n");
                //sbuilder_name.append(mPurchaseHistory.get("price").toString() + "\n");
                //sbuilder_num.append(mPurchaseHistory.get("createDate").toString() + "\n");
                mTextName.setText(mPurchaseHistory.get("name").toString());
                mTextPrice.setText(mPurchaseHistory.get("price").toString());
                mTextNum.setText(mPurchaseHistory.get("num").toString());
                mTextCreateDate.setText(mPurchaseHistory.get("createDate").toString());


                Log.d("debug", "i= " + i);
                //Log.d("debug", " mPurchaseHistoryData_User_ID  ==" + sbuilder_name.toString());
            }


            Log.d("debug", "mPurchaseHistory.size=" + mPurchaseHistoryList.size());

        } catch (NullPointerException e) {
            Log.d("debug", "Display PurchaseHistory null poirnt exception " + e.getMessage());
        }
    }

    public void goSpecialSale(View view) {
        Intent intent = new Intent(this, SpecialSaleActivity.class);
        startActivity(intent);
    }

    //onResponce(){

    //}

}
