package com.toron.marunakadeokaimono_test;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

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
    private static String COLUMN_NAME_RUBI = "ruby";
    private static String COLUMN_NAME_PASSWORD ="password";
    private static String COLUMN_NAME_WAON = "waon";
    private static String COLUMN_NAME_SECURITY = "security";
    private static String COLUMN_NAME_STORE_ID = "store_id";
    private static String COLUMN_NAME_POINTS = "points";

    //テーブル作成文の発行
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + TABLE_NANE_TEST + " (" +
                    _ID + " INTEGER PRIMARY KEY," +
                    COLUMN_NAME_TITLE + " TEXT," +
                    COLUMN_NAME_MONEY+ " INTEGER)";

    //ユーザテーブル作成文
    private static final String SQL_CREATE_USER =
            "CREATE TABLE " +  TABLE_NAME_USER + " (" +
                    _ID + " INTEGER PRIMARY KEY  autoincrement," +
                    COLUMN_NAME_USER_ID + " TEXT ," +
                    COLUMN_NAME_LOGIN_ID + " TEXT," +
                    COLUMN_NAME_NAME + " TEXT," +
                    COLUMN_NAME_RUBI + " TEXT," +
                    COLUMN_NAME_PASSWORD + " TEXT," +
                    COLUMN_NAME_WAON + " TEXT," +
                    COLUMN_NAME_SECURITY + " TEXT," +
                    COLUMN_NAME_STORE_ID + " TEXT," +
                    COLUMN_NAME_POINTS + " INTEGER)";

    //保有食品テーブルを作成
    private static final String SQL_CREATE_HOLD =
            "CREATE TABLE " + "hold" + " (" +
                    _ID + " INTEGER PRIMARY KEY," +
                    "product_name" + " TEXT," +
                    "num"+ " INTEGER," +
                    "createDate"+ " NUMERIC)";

    //履歴テーブルを作成
    private static final String SQL_CREATE_HISTORY =
            "CREATE TABLE " + "history" + " (" +
                    _ID + " INTEGER PRIMARY KEY," +
                    "product_name"+ " TEXT,"+
                    "num"+ " INTEGER," +
                    "price"+ " INTEGER," +
                    "createDate"+ " NUMERIC)";

    //店テーブルを作成
    private static final String SQL_CREATE_STORES =
            "CREATE TABLE " + "stores" + " (" +
                    _ID + " INTEGER PRIMARY KEY," +
                    "store_name" + " TEXT)";
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
    public boolean CheckAuthenticationUser(Map<String,Object> mAuthenticationData){
        try{
            //デバッグ
            // 用にローカルデータベースから接続

            return true;

        }catch(NullPointerException e){
            Log.d("debug","nullpointerException for AuthenticationUser" +e.getMessage());
            return false;
        }

    }
    public  List<Map<String,Object>> GetHoldingFoodData(SQLiteDatabase db){
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
