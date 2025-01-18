package com.example.demosqlite2.DBHelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDbHelper extends SQLiteOpenHelper {

    public  MyDbHelper(Context context) {
        super(context, "QLBH.db", null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // các lệnh sql tọa bảng trong đây
        String SqlCategory = " CREATE TABLE tb_cat (\n" +
                "   id   INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "   name TEXT    UNIQUE\n" +
                "                NOT NULL\n" +
                ");\n ";

        String SqlProduct = "CREATE TABLE tb_product (\n" +
                "   id     INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "   name   TEXT    UNIQUE\n" +
                "                  NOT NULL,\n" +
                "   price  REAL    DEFAULT (0),\n" +
                "   id_cat INTEGER REFERENCES tb_cat (id)\n" +
                "                  NOT NULL\n" +
                ");\n";

        String SqlBill = "CREATE TABLE tb_bill (\n" +
                "   id          INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "   fullname    TEXT    NOT NULL,\n" +
                "   address     TEXT    NOT NULL,\n" +
                "   create_date TEXT    NOT NULL\n" +
                ");\n";

        String SqlBillDetail = "CREATE TABLE tb_bill_detail (\n" +
                "   id_bill    INTEGER NOT NULL\n" +
                "                      REFERENCES tb_bill (id),\n" +
                "   id_product INTEGER NOT NULL\n" +
                "                      REFERENCES tb_product (id),\n" +
                "   quantity   INTEGER DEFAULT (0)\n" +
                "                      NOT NULL,\n" +
                "   price      REAL    DEFAULT (0),\n" +
                "   PRIMARY KEY (\n" +
                "       id_bill,\n" +
                "       id_product\n" +
                "   )\n" +
                ");\n";

        //thực thi các câu lệnh , tạo bảng bên 1 trước , bên nhều sau
        // lần lượt theo thứ tự từ trên xuống
        sqLiteDatabase.execSQL(SqlCategory);
        sqLiteDatabase.execSQL(SqlProduct);
        sqLiteDatabase.execSQL(SqlBill);
        sqLiteDatabase.execSQL(SqlBillDetail);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        if (i1<i) {
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS tb_bill_detail");
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS tb_product");
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS tb_bill");
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS tb_cat");
           // xóa xong thì chạy lại
            onCreate(sqLiteDatabase);
        }

    }
}
