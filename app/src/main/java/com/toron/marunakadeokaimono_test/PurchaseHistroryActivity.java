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
    private String PHPURL = "http://172.21.48.127/server_php/Toron_BackEnd/php/getPurchaseHistory.php";

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_dashboard:
                    mTextMessage.setText(R.string.title_dashboard);
                    return true;
                case R.id.navigation_notifications:
                    mTextMessage.setText(R.string.title_notifications);
                    return true;
                case R.id.navigation_other:
                    //TransitionFavoriteShopActivity();
                    mTextMessage.setText(R.string.title_other);
                    return true;
            }
            return false;
        }
    };

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


            for (int i = 0; i < mPurchaseHistoryList.size(); i++) {
                Map<String, Object> mPurchaseHistory = mPurchaseHistoryList.get(i);

                View view = getLayoutInflater().inflate(R.layout.tablelayout, table);

                //int text_id = 1;
                //TextView text = view.findViewById(R.id.tableView2);
                //text.setId(text_id);
                //text.setText(mPurchaseHistory.get("name").toString());
                // テキストを変更
                mPurchaseHistoryData_name = view.findViewById(R.id.tableView1);
                mPurchaseHistoryData_num = view.findViewById(R.id.tableView2);
                mPurchaseHistoryData_price = view.findViewById(R.id.tableView3);
                mPurchaseHistoryData_createDate = view.findViewById(R.id.tableView4);




                //sbuilder_num.append(mPurchaseHistory.get("num").toString() + "\n");
                //sbuilder_name.append(mPurchaseHistory.get("price").toString() + "\n");
                //sbuilder_num.append(mPurchaseHistory.get("createDate").toString() + "\n");
                mPurchaseHistoryData_name.setText(mPurchaseHistory.get("name").toString());
                mPurchaseHistoryData_num.setText(mPurchaseHistory.get("num").toString());
                mPurchaseHistoryData_price.setText(mPurchaseHistory.get("price").toString());
                mPurchaseHistoryData_createDate.setText(mPurchaseHistory.get("createDate").toString());

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
