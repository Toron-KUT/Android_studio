package com.toron.marunakadeokaimono_test;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {
    // データーベースのバージョン
    private static final int DATABASE_VERSION = 1;
    // データーベース名
    private static String DATABASE_NAME = "TestDB.db";
    private static String TABLE_NANE_TEST = "testdb";
    private static String TABLE_NAME_USER = "u";
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

    //テーブル削除文の発行

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + TABLE_NANE_TEST;
    private static final String SQL_DELETE_USERS =
            "DROP TABLE IF EXISTS " + TABLE_NAME_USER;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    private void createTable_Entries(SQLiteDatabase db) {
        try {
            db.execSQL(SQL_CREATE_ENTRIES);

        }catch(SQLiteException e){
             Log.d("debug",e.getMessage());
        }finally{
        Log.d("debug", "test table create sccess");
        }
    }
    private void createTable_User(SQLiteDatabase db) {
        try {
            db.execSQL(SQL_CREATE_USER);
        } catch (SQLiteException e) {
            Log.d("debug", e.getMessage());
        } finally {
            Log.d("debug", "user table create sccess");
        }
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


    @Override
    public void onCreate(SQLiteDatabase db) {
        //テーブル作成
        Log.d(DATABASE_NAME , "onCreate version : " + db.getVersion());
        createTable_Entries(db);
        createTable_User(db);
        Log.d(DATABASE_NAME , "onCreated version : " + db.getVersion());
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        Log.d("debug","Openeed");
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
