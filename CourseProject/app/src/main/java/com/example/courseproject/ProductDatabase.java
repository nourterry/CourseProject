package com.example.courseproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.courseproject.entity.Product;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;

public class ProductDatabase extends SQLiteAssetHelper {
    public static final int DB_VERSION = 1;
    public static final String DB_NAME = "products.db";
    public static final String TABLE_NAME = "products";
    public static final String COLUMN_PRODUCT_ID = "id";
    public static final String COLUMN_PRODUCT_NAME = "name";
    public static final String COLUMN_PRODUCT_DESCRIPTION = "description";
    public static final String COLUMN_PRODUCT_PRICE = "price";
    public static final String COLUMN_PRODUCT_ISCASH = "isCash";
    public static final String COLUMN_PRODUCT_IMAGE = "image";
    public static final String SQL_CREATE_USERS =
            "CREATE TABLE " + ProductDatabase.TABLE_NAME + " (" +
                    COLUMN_PRODUCT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    COLUMN_PRODUCT_NAME + " TEXT," +
                    COLUMN_PRODUCT_DESCRIPTION + " TEXT," +
                    COLUMN_PRODUCT_PRICE + " TEXT," +
                    COLUMN_PRODUCT_ISCASH + " TEXT," +
                    COLUMN_PRODUCT_IMAGE + " TEXT)" ;

    public static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + TABLE_NAME;
    public ProductDatabase(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

   /* @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_USERS);
   //     db.execSQL(UserDatabase.UserProduct.SQL_CREATE_PRODUCTS);
    }*/

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
 /*   @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }*/
    public  boolean insertProduct(Product product){
        SQLiteDatabase db=getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(COLUMN_PRODUCT_NAME,product.getName());
        values.put(COLUMN_PRODUCT_DESCRIPTION,product.getDescription());
        values.put(COLUMN_PRODUCT_PRICE,product.getPrice());
        values.put(COLUMN_PRODUCT_ISCASH,product.getIsCash());
        values.put(COLUMN_PRODUCT_IMAGE,product.getImage());
        long result=  db.insert(TABLE_NAME,null,values);
        db.close();
        return result!=-1;

    }
    public ArrayList<Product> getList() {
        ArrayList<Product> products=new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_PRODUCT_ID));
                String name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PRODUCT_NAME));
                String description = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PRODUCT_DESCRIPTION));
                // Log.d("id", id);
                String price = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PRODUCT_PRICE));
                String isCash = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PRODUCT_ISCASH));
                String image = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PRODUCT_IMAGE));
                Product product = new Product(id, name, description, price, isCash, image);
                products.add(product);
                cursor.moveToNext();
            }
        }
        Log.d("size",products.size()+"");
        return products;
    }

}
