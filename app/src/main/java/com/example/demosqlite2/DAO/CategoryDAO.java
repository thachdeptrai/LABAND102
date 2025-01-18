package com.example.demosqlite2.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.demosqlite2.DBHelper.MyDbHelper;
import com.example.demosqlite2.DTO.CategoryDTO;

import java.util.ArrayList;

public class CategoryDAO {
    MyDbHelper dbHelper;
    SQLiteDatabase db;

    public CategoryDAO(Context context) {
        dbHelper = new MyDbHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public int InsertRow(CategoryDTO objCat) {
        ContentValues v = new ContentValues();
        v.put("name", objCat.getName());
        int kq = (int) db.insert("tb_cat", null, v);
        int keq = (int) db.insert("tb_product", null, v);
        return kq;// nếu thêm thành công thì kq là số nguyên > 0 là id ms sinh
    }

    public boolean UpdateRow(CategoryDTO objCat) {
        ContentValues v = new ContentValues();
        v.put("name", objCat.getName());
        // điều kiện update
        String[] dk = {String.valueOf(objCat.getId())};
        long kq = db.update("tb_cat", v, "id = ?", dk);
        long keq = db.update("tb_product", v, "id = ?", dk);
        // kq là số lượng bản ghi đc update
        return kq > 0;
    }

    public Boolean DeleteRow(CategoryDTO objCat) {
        String[] dk = {String.valueOf(objCat.getId())};
        long kq = db.delete("tb_cat", "id = ?", dk);
        long keq = db.delete("tb_product", "id = ?", dk);
        return kq > 0;
    }

    public ArrayList<CategoryDTO> getList() {
        // lấy danh sách
        ArrayList<CategoryDTO> listCat = new ArrayList<>();
        Cursor c = db.rawQuery("SELECT id, name FROM tb_cat", null);
        if (c != null && c.getCount() > 0) {
            c.moveToFirst();
            do {
                // lấy dữ liệu
                int id = c.getInt(0);
                String name = c.getString(1);
                CategoryDTO categoryDTO = new CategoryDTO(id,name);
                listCat.add(categoryDTO);
            } while (c.moveToNext());
        }
        return listCat;

    }

    public ArrayList<CategoryDTO> getListfromcat() {
        // lấy danh sách
        ArrayList<CategoryDTO> listCat = new ArrayList<>();
        Cursor c = db.rawQuery("SELECT id, name  FROM tb_cat", null);
        if (c != null && c.getCount() > 0) {
            c.moveToFirst();
            do {
                // lấy dữ liệu
                int id = c.getInt(0);
                String name = c.getString(1);
                CategoryDTO categoryDTO = new CategoryDTO(id,name);
                listCat.add(categoryDTO);
            } while (c.moveToNext());
        }
        return listCat;

    }


}