package com.example.demosqlite2.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.demosqlite2.DBHelper.MyDbHelper;

public class billDao {
    MyDbHelper dbHelper;
    SQLiteDatabase db;
    private static final String TABLE_BILL = "tb_bill";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_FULLNAME = "fullname";
    private static final String COLUMN_ADDRESS = "address";
    private static final String COLUMN_CREATED_DATE = "created_date";

    public billDao(Context context) {
        dbHelper = new MyDbHelper(context);
        db = dbHelper.getWritableDatabase();
    }
    // Thêm bản ghi
    public long addBill(String fullname, String address, String createdDate) {
        db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_FULLNAME, fullname);
        values.put(COLUMN_ADDRESS, address);
        values.put(COLUMN_CREATED_DATE, createdDate);

        long result = db.insert(TABLE_BILL, null, values);
        db.close();
        return result;
    }

    // Hiển thị danh sách
    public Cursor getAllBills() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_BILL, null);
    }

    // Sửa bản ghi
    public int updateBill(int id, String fullname, String address, String createdDate) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_FULLNAME, fullname);
        values.put(COLUMN_ADDRESS, address);
        values.put(COLUMN_CREATED_DATE, createdDate);

        int result = db.update(TABLE_BILL, values, COLUMN_ID + "=?", new String[]{String.valueOf(id)});
        db.close();
        return result;
    }

    // Xóa bản ghi
    public int deleteBill(int id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int result = db.delete(TABLE_BILL, COLUMN_ID + "=?", new String[]{String.valueOf(id)});
        db.close();
        return result;
    }
}
