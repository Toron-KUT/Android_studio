package com.toron.marunakadeokaimono_test;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DatabaseHelper extends SQLiteOpenHelper {
    // データーベースのバージョン
    private static final int DATABASE_VERSION = 1;
    // データーベース名
    private static String DATABASE_NAME = "TestDB.db";
    private static String TABLE_NANE_TEST = "testdb";
    private static String TABLE_NAME_USER = "users";
    private static String _ID = "_id";
    private static String COLUMN_NAME_TITLE = "company";
    private static String COLUMN_NAME_MONEY = "stockprice";
    //ユーザテーブル作成
    private static String COLUMN_NAME_USER_ID = "user_id";
    private static String COLUMN_NAME_LOGIN_ID = "login_id";
    private static String COLUMN_NAME_NAME = "name";
   // private static String COLUMN_NAME_RUBI = "ruby";
   // private static String COLUMN_NAME_PASSWORD ="password";
   // private static String COLUMN_NAME_WAON = "waon";
   // private static String COLUMN_NAME_SECURITY = "security";
    private static String COLUMN_NAME_STORE_ID = "store_id";
    private static String COLUMN_NAME_POINTS = "point";

    private RequestQueue mQueue;
    private JSONArray mArrayList = null;

    //テーブル作成文の発行
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + TABLE_NANE_TEST + " (" +
                    _ID + " INTEGER PRIMARY KEY," +
                    COLUMN_NAME_TITLE + " TEXT," +
                    COLUMN_NAME_MONEY+ " INTEGER)";

    //ユーザテーブル作成文
    private static final String SQL_CREATE_USER =
            "CREATE TABLE " +  TABLE_NAME_USER + " (" +
                    COLUMN_NAME_USER_ID + " TEXT PRIMARY KEY," +
                    COLUMN_NAME_LOGIN_ID + " TEXT," +
                    COLUMN_NAME_NAME + " TEXT," +
                    COLUMN_NAME_POINTS + " INTEGER," +
                    COLUMN_NAME_STORE_ID + " TEXT)";

    //保有食品テーブルを作成
    private static final String SQL_CREATE_HOLD =
            "CREATE TABLE " + "hold" + " (" +
                    _ID + " INTEGER PRIMARY KEY," +
                    "product_name" + " TEXT," +
                    "num"+ " INTEGER," +
                    "createDate"+ " NUMERIC," +
                    "updateDate"+ " NUMERIC)";

    //履歴テーブルを作成
    private static final String SQL_CREATE_HISTORY =
            "CREATE TABLE " + "history" + " (" +
                    _ID + " INTEGER PRIMARY KEY," +
                    "product_name"+ " TEXT,"+
                    "num"+ " INTEGER," +
                    "price"+ " INTEGER," +
                    "createDate"+ " NUMERIC," +
                    "updateDate"+ " NUMERIC)";

    //店テーブルを作成
    private static final String SQL_CREATE_STORES =
            "CREATE TABLE " + "stores" + " (" +
                    _ID + " INTEGER PRIMARY KEY," +
                    "store_name" + " TEXT," +
                    "createDate"+ " NUMERIC," +
                    "updateDate"+ " NUMERIC)";
    //テーブル削除文の発行

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + TABLE_NANE_TEST;
    private static final String SQL_DELETE_USERS =
            "DROP TABLE IF EXISTS " + TABLE_NAME_USER;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    private void deleteTable(SQLiteDatabase db) {
        try{
            db.execSQL(SQL_DELETE_ENTRIES);
            db.execSQL(SQL_DELETE_USERS);
        }catch (SQLiteException e) {
            Log.d("debug", e.getMessage());
        } finally {
            Log.d("debug", "all table delete sccess");
        }
    }

    public String getUserID(SQLiteDatabase db){
        String UserID = null;
        Cursor cursor = null;
        try{
            Log.d("debug","checking user_id data....");
            cursor = db.query(
                    "users",
                    new String[] { "user_id"} ,
                    null,
                    null,
                    null,
                    null,
                    null
            );

            cursor.moveToFirst();

            UserID = cursor.getString(0);

            cursor.close();

        }catch(SQLiteException e){
            Log.d("debug","user get error" + e.getMessage());
            return null;
        }finally{
            cursor.close();
            return UserID;
        }

    }
    public JSONArray getJSONArray(){
        return mArrayList;
    }

    public JSONArray StartVolley(String PHPURL,Context c) {
        Log.d("debug" , "StartVolley started....");
        //mArrayList = null;


        //queue


        RequestQueue postQueue = Volley.newRequestQueue(c);
        //postQueue.stop();
        //postQueue.start();

        //サーバーのアドレス任意

        //テスト用に以下を止める．本来は以下の表現で行う
        //String POST_URL="http://172.21.48.131/test/" + PHPURL;

        String POST_URL = PHPURL;

        StringRequest stringReq=new StringRequest(Request.Method.GET,POST_URL,

                //通信成功
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        try{
                            Log.d("degug","通信に成功しました for ");
                            JSONArray mJSONArray = new JSONArray(s);
                            this.setArrayList(mJSONArray);

                            //JSONArray result = mJSONArray.getJSONArray(0);
                            //JSONArray userList = result.getJSONArray(1);
                            //JSONArray mJSONArray = new JSONArray(s);
                            //String title = mJSONArray.getJSONObject(0).getString("userid");
                            //String title2 = mJSONArray.getJSONObject(0).getString("password");
                            //Log.d("debug", "userid:     "+ title + " :  password   :  " +  title2  );
                            Log.d("debug","StartVolley Success");

                            //if (mJSONArray[0]["userid"] =="eat") {
                            //    Log.d("debug","万歳太郎");
                            //}
                        }catch(JSONException e){
                            Log.d("degug","JSONエラー" + e.getMessage());
                        }

                    }

                    private void setArrayList(JSONArray mJSONArray) {
                        mArrayList = mJSONArray;
                    }
                },

                //通信失敗
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error){
                        Log.d("degug","通信に失敗しました" + error.getMessage());
                    }
                });

        postQueue.add(stringReq);
        //while(mArrayList == null){
        //    Log.d("debug","test for startvolley");
        //}
        return mArrayList;

    }

    public JSONArray ReturnVolley(String PHPURL,Context c) {

        //queue


        RequestQueue postQueue = Volley.newRequestQueue(c);
        postQueue.stop();
        postQueue.start();

        //サーバーのアドレス任意

        //テスト用に以下を止める．本来は以下の表現で行う
        //String POST_URL="http://172.21.48.131/test/" + PHPURL;

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
                            this.setArrayList(mJSONArray);
                            //JSONArray result = mJSONArray.getJSONArray(0);
                            //JSONArray userList = result.getJSONArray(1);
                            //JSONArray mJSONArray = new JSONArray(s);
                            //String title = mJSONArray.getJSONObject(0).getString("userid");
                            //String title2 = mJSONArray.getJSONObject(0).getString("password");
                            //Log.d("debug", "userid:     "+ title + " :  password   :  " +  title2  );
                            Log.d("debug","StartVolley Success");

                            //if (mJSONArray[0]["userid"] =="eat") {
                            //    Log.d("debug","万歳太郎");
                            //}
                        }catch(JSONException e){
                            Log.d("degug","JSONエラー" + e.getMessage());
                        }

                    }

                    private void setArrayList(JSONArray mJSONArray) {

                        mArrayList = mJSONArray;

                    }
                },

                //通信失敗
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error){
                        Log.d("degug","通信に失敗しました" + error.getMessage());
                    }
                });

        postQueue.add(stringReq);
        return mArrayList;
    }



    public boolean SetAuthenticationUserData(JSONArray mJSONArray ,SQLiteDatabase db){
        try{
            //デバッグ
            // 用にローカルデータベースから接続
            //DatabaseHelper helper =this;
            db.execSQL("DELETE FROM users");
            ContentValues values = new ContentValues();
            JSONObject mJSONObject = mJSONArray.getJSONObject(0);
            values.put("user_id", mJSONObject.getString("user_id"));
            values.put("login_id", mJSONObject.getString("login_id"));
            values.put("name", mJSONObject.getString("name"));
            values.put("point", mJSONObject.getInt("point"));
            values.put("store_id", mJSONObject.getString("store_id"));

            //nullの回避
            //int store = mJSONObject.getString("store_id");
            //if(store != null) {
            //    values.put("store_id", mJSONObject.getInt("store_id"));
            //}


            db.insert("users", null, values);


            Log.d("debug","無事ユーザー情報をセットできました　for SetAuthenticationUserData");

            // cour = db.rawQuery("Insert into users values",null);
            //cour.close();

        }catch(NullPointerException e){
            Log.d("debug","nullpointerException for SetAuthenticationUser in DatabaseHelper  "   +e.getMessage());
            return false;
        }catch(SQLiteException e){
            Log.d("debug","SQLiteException for SetAuthenticationUser in DatabaseHelper  " +e.getMessage());
            return false;
        } catch (JSONException e) {
            e.printStackTrace();
            return false;
        }finally{
            return true;
        }

    }

    public  List<Map<String,Object>> GetHoldingFoodData_old(SQLiteDatabase db){
        List<Map<String,Object>>HoldList = new ArrayList<Map<String,Object>>();
        Map<String,Object> mFoodData;
        Cursor c = null;
        int f = 0;
        try{
            c = db.rawQuery("Select * from hold",null);

            if(c.moveToFirst()){
                do{
                    String mProduct_Name = c.getString(c.getColumnIndex("product_name"));
                    int mNum = c.getInt(c.getColumnIndex("num"));
                    double mCreateDate = c.getDouble(c.getColumnIndex("createDate"));

                    mFoodData = new HashMap<String,Object>();
                    mFoodData.put("product_name",mProduct_Name);
                    mFoodData.put("num",mNum);
                    mFoodData.put("createdate",mCreateDate);


                    HoldList.add(mFoodData);

                    f++;

                }
                while(c.moveToNext());
            }
            else{
                HoldList = null;
                Log.d("debug","List = null");
            }
            Log.d("debug","List get count = " +f);



        }catch(SQLiteException e){
            Log.d("debug","get holdtable data failed" + e.getMessage());
        }finally{
            c.close();
        }
        return HoldList;



    }
    public List<Map<String,Object>> GetHoldingFoodData(JSONArray mJSONArray){
        List<Map<String,Object>> mHoldingFoodList = new ArrayList<Map<String,Object>>();
        Map<String,Object> mHoldingFood;
        //DatabaseHelper mHelper = this;
        //String mPHPURL = "http://172.21.48.127/server_php/Toron_BackEnd/php/getStore.php";

        int f = 0;
        try{
            //JSONArray mJSONArray = mHelper.ReturnVolley(mPHPURL,c);
            if(mJSONArray.length()!= 0){
                do{
                    JSONObject mJSONObject = mJSONArray.getJSONObject(f);
                    String mHold_Name = mJSONObject.getString("name");
                    String mHold_Num = mJSONObject.getString("num");
                    String mHold_CreateDate = mJSONObject.getString("createDate");
                    Log.d("debug","mStore_Name ==  " + mHold_Name);

                    mHoldingFood = new HashMap<String,Object>();
                    mHoldingFood.put("name",mHold_Name);
                    mHoldingFood.put("num",mHold_Num);
                    mHoldingFood.put("createDate",mHold_CreateDate);

                    mHoldingFoodList.add(mHoldingFood);

                    f++;
                }while(f<mJSONArray.length());
                Log.d("debug","special sale data set finish");
            }
            else{
                Log.d("debug","特売情報がありませんでした");
                return null;
            }

        }catch(SQLiteException e){
            Log.d("debug","get specialfood data failed" + e.getMessage());
            return null;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        } finally{
            return mHoldingFoodList;
        }
    }

    public  List<Map<String,Object>> GetPurchaseHistoryData(JSONArray mJSONArray){
        Log.d("debug","GetPurchaseHistoryData for DatavaseHelper  startd...");
        List<Map<String,Object>> mPurchaseHistoryList = new ArrayList<Map<String,Object>>();
        Map<String,Object> mPurchaseHistory;
        int f = 0;
        try{
            //テスト用

            //while(true){
            //    mmJSONArray = getJSONArray();
            //    if(mmJSONArray != null)break;
            //}
            if(mJSONArray.length()!= 0){
                do{
                    JSONObject mJSONObject = mJSONArray.getJSONObject(f);
                    String mName = mJSONObject.getString("name");
                    String mNum = mJSONObject.getString("num");
                    String mPrice = mJSONObject.getString("price");
                    String mCreteDate = mJSONObject.getString("createDate");
                    Log.d("debug","GetPurchaceHistory Data userID for databaseHelper == " + mName);



                    mPurchaseHistory = new HashMap<String,Object>();
                    mPurchaseHistory.put("name",mName);
                    mPurchaseHistory.put("num",mNum);
                    mPurchaseHistory.put("price",mPrice);
                    mPurchaseHistory.put("createDate",mCreteDate);

                    mPurchaseHistoryList.add(mPurchaseHistory);
                    Map<String, Object> mPurchaseHistoryData = mPurchaseHistoryList.get(f);
                    Log.d("debug","debug get code on Arraylist for databasehelper  == " +  mPurchaseHistoryData.get("num").toString());
                    f++;
                }while(f<mJSONArray.length());
                Log.d("debug","test data set finish");
            }
            else{
                Log.d("debug","テストデータがありませんでした");
                return null;
            }

        }catch(SQLiteException e){
            Log.d("debug","get test data failed" + e.getMessage());
        } catch (JSONException e) {
            e.printStackTrace();
        }catch(NullPointerException e){
            Log.d("debug"," null poirnt exception in GetPurchaceHistoryData for DatabaseHelper " + e.getMessage());
        }
        finally{

        }
        return mPurchaseHistoryList;

    }


    public  List<Map<String,Object>> GetFavoriteShopData( JSONArray mJSONArray){
        List<Map<String,Object>> mFavoriteShopList = new ArrayList<Map<String,Object>>();
        Map<String,Object> mFavoriteShop;
        //DatabaseHelper mHelper = this;
        //String mPHPURL = "http://172.21.48.127/server_php/Toron_BackEnd/php/getStore.php";

        int f = 0;
        try{
            //JSONArray mJSONArray = mHelper.ReturnVolley(mPHPURL,c);
            if(mJSONArray.length()!= 0){
                do{
                    JSONObject mJSONObject = mJSONArray.getJSONObject(f);

                    String mStore_ID = mJSONObject.getString("store_id");
                    String mStore_Name = mJSONObject.getString("name");
                    Log.d("debug","mStore_Name ==  " + mStore_Name);


                    mFavoriteShop = new HashMap<String,Object>();
                    mFavoriteShop.put("store_id",mStore_ID);
                    mFavoriteShop.put("name",mStore_Name);
                    mFavoriteShopList.add(mFavoriteShop);

                    f++;
                }while(f<mJSONArray.length());
                Log.d("debug","FavoriteShop data set finish");
            }
            else{
                Log.d("debug","特売情報がありませんでした");
                return null;
            }

        }catch(SQLiteException e){
            Log.d("debug","get FavoriteShop data failed" + e.getMessage());
            return null;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        } finally{
            return mFavoriteShopList;
        }
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        //テーブル作成
        try{
            Log.d(DATABASE_NAME , "onCreate version : " + db.getVersion());
            db.execSQL(SQL_CREATE_ENTRIES);
            db.execSQL(SQL_CREATE_USER);
            db.execSQL(SQL_CREATE_HOLD);
            db.execSQL(SQL_CREATE_HISTORY);
            db.execSQL(SQL_CREATE_STORES);
            Log.d(DATABASE_NAME , "onCreated version : " + db.getVersion());

        }catch (SQLiteException e) {
            Log.d("debug", e.getMessage());
        } finally {
            Log.d("debug", "all table create sccess");
        }
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        Log.d("debug","Opened");
    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int currentVersion) {
        deleteTable(db);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
