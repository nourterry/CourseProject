package com.example.courseproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.courseproject.entity.Purchase;

import java.util.ArrayList;

public class PurchaseDatabase extends SQLiteOpenHelper {
    public static final int DB_VERSION = 1;
    public static final String DB_NAME = "purchase.db";
    public static final String TABLE_NAME = "purchase";
    public static final String COLUMN_PRODUCT_ID = "id";
    public static final String COLUMN_PRODUCT_USERNAME = "username";
    public static final String COLUMN_PRODUCT_NAME = "name";
    public static final String COLUMN_PRODUCT_PRICE = "price";
    public static final String COLUMN_PRODUCT_TIMEANDDATE = "timeanddate";
    public static final String SQL_CREATE_PURCHASE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    COLUMN_PRODUCT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    COLUMN_PRODUCT_NAME + " TEXT," +
                    COLUMN_PRODUCT_USERNAME  + " TEXT," +
                    COLUMN_PRODUCT_PRICE + " TEXT," +
                    COLUMN_PRODUCT_TIMEANDDATE + " TEXT)" ;

    public static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + TABLE_NAME;
    public PurchaseDatabase(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_PURCHASE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
    public  boolean insertPurchase(Purchase purchase){
        SQLiteDatabase db=getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(COLUMN_PRODUCT_NAME,purchase.getName());
        values.put(COLUMN_PRODUCT_USERNAME,purchase.getUsername());
        values.put(COLUMN_PRODUCT_PRICE,purchase.getPrice());
        values.put(COLUMN_PRODUCT_TIMEANDDATE,purchase.getTimeanddate());
        long result=  db.insert(TABLE_NAME,null,values);
        db.close();
        return result!=-1;

    }
    public  boolean deletePurchase(String username){
        SQLiteDatabase db=getWritableDatabase();
        String args[]={ username };
        long result=  db.delete(TABLE_NAME,"username LIKE ?",args);
        db.close();
        return result>0;

    }
    public ArrayList<Purchase> getList(String username) {
        ArrayList<Purchase> purchases=new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_PRODUCT_ID));
            String name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PRODUCT_NAME));
            String username_product = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PRODUCT_USERNAME));
            // Log.d("id", id);
            String price = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PRODUCT_PRICE));
            String timeanddate = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PRODUCT_TIMEANDDATE));
            if(username.equals(username_product)) {
                Purchase purchase = new Purchase(id, name, username, price, timeanddate);
                purchases.add(purchase);
            }

        }
        return purchases;
    }

}
