// /data/user/0/com.example.androideatit
package com.example.androideatit.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.androideatit.Model.Order;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;


public class DatabaseHelper extends SQLiteOpenHelper {
    // If you change the database schema, you must increment the database version.
    private Context context;
    private static final String DATABASE_NAME = "EatItDB.db";
    private static final int DATABASE_VERSION = 1;

    private static String TABLE_NAME="OrderDetail";
    private static String COL_PRODUCTID="ProductId";
    private static String COL_PRODUCTNAME="ProductName";
    private static String COL_QUANTITY="Quantity";
    private static String COL_PRICE="Price";
    private static String COL_DISCOUNT="Discount";


    public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void CreateTable(){
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL("CREATE TABLE IF NOT EXISTS OrderDetail(ID INTEGER PRIMARY KEY, ProductId Varchar2(10), ProductName VarChar2(200), Quantity VarChar2(10), Price Varchar2(10), Discount Varchar2(5))");
    }
    public void AddToCart(Order order){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_PRODUCTID, order.getProductId());
        contentValues.put(COL_PRODUCTNAME, order.getProductName());
        contentValues.put(COL_QUANTITY, order.getQuantity());
        contentValues.put(COL_PRICE, order.getPrice());
        contentValues.put(COL_DISCOUNT, order.getDiscount());
        db.insert(TABLE_NAME, null, contentValues);
    }

    public List<Order> getCarts(){

        SQLiteDatabase db = getWritableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        String[] sqlSelect = {"ProductName", "ProductId", "Quantity", "Price", "Discount"};
        String sqlTable = "OrderDetail";
        qb.setTables(sqlTable);
        Cursor c = qb.query(db, sqlSelect, null, null,null,null,null);
        final List<Order> result = new ArrayList<>();
        if(c.moveToFirst()){
            do{
                result.add(new Order(c.getString(c.getColumnIndex("ProductId")),
                        c.getString(c.getColumnIndex("ProductName")),
                        c.getString(c.getColumnIndex("Quantity")),
                        c.getString(c.getColumnIndex("Price")),
                        c.getString(c.getColumnIndex("Discount"))));
            }while (c.moveToNext());
        }
        return result;
    }

    public void CleanCart(){
        SQLiteDatabase db = getReadableDatabase();
        String query = String.format("Delete From OrderDetail");
        db.execSQL(query);
    }
}
