package com.example.orderapp;



import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.orderapp.Product;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "products.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "products";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " (id INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT, price REAL, date TEXT, status TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public long addProduct(String title, double price, String date, String status) {
        ContentValues values = new ContentValues();
        values.put("title", title);
        values.put("price", price);
        values.put("date", date);
        values.put("status", status);
        return getWritableDatabase().insert(TABLE_NAME, null, values);
    }

    @SuppressLint("Range")
    public List<Product> getProductsByStatus(String status) {
        List<Product> products = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, null, "status=?", new String[]{status}, null, null, null);
        while (cursor.moveToNext()) {
            products.add(new Product(
                    cursor.getInt(cursor.getColumnIndex("id")),
                    cursor.getString(cursor.getColumnIndex("title")),
                    cursor.getDouble(cursor.getColumnIndex("price")),
                    cursor.getString(cursor.getColumnIndex("date")),
                    cursor.getString(cursor.getColumnIndex("status"))
            ));
        }
        cursor.close();
        return products;
    }

    public void updateProductStatus(int id, String newStatus) {
        ContentValues values = new ContentValues();
        values.put("status", newStatus);
        getWritableDatabase().update(TABLE_NAME, values, "id=?", new String[]{String.valueOf(id)});
    }
}
