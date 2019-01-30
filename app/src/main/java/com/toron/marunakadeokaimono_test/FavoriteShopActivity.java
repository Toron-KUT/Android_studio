package com.toron.marunakadeokaimono_test;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
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

public class FavoriteShopActivity extends Activity {
    private TextView mTextMessage;
    private TextView mFavoriteShopData_Store_Name;
    private DatabaseHelper helper;
    private SQLiteDatabase db;
    private RequestQueue mQueue;
    private String PHPURL = "http://222.229.69.53/~goohira/toron/php/getStore.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_shop);
        GetFavoriteShop();
    }
    private void GetFavoriteShop(){
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
                                List<Map<String,Object>> mArrayList = helper.GetFavoriteShopData(mJSONArray);
                                DisplayFavoriteShop(mArrayList);
                                //レスポンスが返ってきたらDisplayFavoriteShop()を実行


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




            //List<Map<String,Object>> FavoriteShopList = helper.GetFavoriteShopData(helper,this);
            //if(FavoriteShopList!=null){
            //    Log.d("debug","FavoriteShopList success");
            //    DisplayFavoriteShop(FavoriteShopList);
            //}
            //else{
            //    Log.d("debug","FavoriteShopList failed");
            //}

        }catch(NullPointerException e){
            Log.d("debug"," null poirnt exception for GetFavoriteShopData" + e.getMessage());
        }

    }
    private void DisplayFavoriteShop(List<Map<String,Object>> mFavoriteShopList) {
        try {
            ViewGroup table = (ViewGroup) findViewById(R.id.table_Favorite);
            Log.d("debug","mFavoriteShopList.size == " +  mFavoriteShopList.size());

            // 行を追加
            //mPurchaseHistoryData_name = findViewById(R.id.textView30);
            //mPurchaseHistoryData_num = findViewById(R.id.textView31);
            //mPurchaseHistoryData_price = findViewById(R.id.textView33);
            //mPurchaseHistoryData_createDate = findViewById(R.id.textView32);

            //StringBuilder sbuilder_name = new StringBuilder();
            //StringBuilder sbuilder_num = new StringBuilder();
            //StringBuilder sbuilder_price = new StringBuilder();
            //StringBuilder sbuilder_createDate = new StringBuilder();


            for (int i = 0; i < mFavoriteShopList.size(); i++) {
                Map<String, Object> mFavoriteShop = mFavoriteShopList.get(i);

                View view = getLayoutInflater().inflate(R.layout.tablelayout_favorite, table);
                Log.d("debug","mFavoriteShopList.store_id == " + mFavoriteShop.get("store_id").toString());



                int text_name = 10 * Integer.parseInt(mFavoriteShop.get("store_id").toString() + 1);
                TextView mTextName = view.findViewById(R.id.tableView_Favorite1);
                mTextName.setId(text_name);

                int text_button = 10 * Integer.parseInt(mFavoriteShop.get("store_id").toString() + 2);
                TextView mButton = view.findViewById(R.id.tableView_Favorite2);
                mButton.setId(text_button);

                Log.d("debug","mFavoriteShopList.store_id == " + mFavoriteShop.get("name").toString());

                //text.setText(mPurchaseHistory.get("name").toString());
                // テキストを変更
                //sbuilder_num.append(mPurchaseHistory.get("num").toString() + "\n");
                //sbuilder_name.append(mPurchaseHistory.get("price").toString() + "\n");
                //sbuilder_num.append(mPurchaseHistory.get("createDate").toString() + "\n");
                //mTextId.setText(mFavoriteShop.get("store_id").toString());
                mTextName.setText(mFavoriteShop.get("name").toString());


                Log.d("debug", "i= " + i);
                //Log.d("debug", " mPurchaseHistoryData_User_ID  ==" + sbuilder_name.toString());
            }


            Log.d("debug", "mFavoriteShop.size=" + mFavoriteShopList.size());

        } catch (NullPointerException e) {
            Log.d("debug", "Display FavoriteShop null poirnt exception " + e.getMessage());
        }
    }
    
}
