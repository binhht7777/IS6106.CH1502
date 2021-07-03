//package com.example.androideatit.Database;
//
//import android.content.ContentValues;
//import android.content.Context;
//import android.database.Cursor;
//import android.database.sqlite.SQLiteDatabase;
//import android.database.sqlite.SQLiteOpenHelper;
//import android.database.sqlite.SQLiteQueryBuilder;
//import android.util.Log;
//
//import com.example.androideatit.MainActivity;
//import com.example.androideatit.Model.Order;
//import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class Database extends SQLiteOpenHelper {
//    private static final String DATABASE_NAME="EatItDB.db";
//    private static final int DATABASE_VERSION=1;
//
//    public Database(Context context) {
//        super(context, DATABASE_NAME, null, DATABASE_VERSION);
//    }
//
//    public List<Order> getCarts(){
//
//        SQLiteDatabase db = getWritableDatabase();
//        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
//
//        String[] sqlSelect = {"ProductName", "ProductId", "Quantity", "Price", "Discount"};
//        String sqlTable = "OrderDetail";
//        qb.setTables(sqlTable);
//        Cursor c = qb.query(db, sqlSelect, null, null,null,null,null);
//        final List<Order> result = new ArrayList<>();
//        if(c.moveToFirst()){
//            do{
//                result.add(new Order(c.getString(c.getColumnIndex("ProductId")),
//                        c.getString(c.getColumnIndex("ProductName")),
//                        c.getString(c.getColumnIndex("Quanity")),
//                        c.getString(c.getColumnIndex("Price")),
//                        c.getString(c.getColumnIndex("Discount"))));
//            }while (c.moveToNext());
//        }
//        return result;
//    }
//
//    public boolean addToCart(Order order){
//        SQLiteDatabase db = this.getWritableDatabase();
//        try {
//
//            ContentValues contentValues = new ContentValues();
//            contentValues.put("ProductId", order.getProductId());
//            contentValues.put("ProductName", order.getProductName());
//            contentValues.put("Quantity", order.getQuantity());
//            contentValues.put("Price", order.getPrice());
//            contentValues.put("Discount", order.getDiscount());
//            long result = db.insert("OrderDetail", null, contentValues);
////            String query = String.format("Insert Into OrderDetail(ProductId, ProductName, Quantity, Price, Discount) Values('%s', '%s', '%s', '%s', '%s');",
////                order.getProductId(),
////                order.getProductName(),
////                order.getQuantity(),
////                order.getPrice(),
////                order.getDiscount());
////            db.execSQL(query);
//            if(result == -1){
//                return false;
//            }else
//                return true;
//        }
//        finally {
//            return true;
//        }
////        String query = String.format("Insert Into OrderDetail(ProductId, ProductName, Quantity, Price, Discount) Values('%s', '%s', '%s', '%s', '%s')",
////            order.getProductId(),
////            order.getProductName(),
////            order.getQuantity(),
////            order.getPrice(),
////            order.getDiscount());
////        db.execSQL(query);
//    }
//
//    public void cleanCart(){
//        SQLiteDatabase db = getReadableDatabase();
//        String query = String.format("Delete From OrderDetail ");
//        db.execSQL(query);
//    }
//
//
//    @Override
//    public void onCreate(SQLiteDatabase db) {
//
//    }
//
//    @Override
//    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//
//    }
//}