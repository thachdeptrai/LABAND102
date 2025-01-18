package com.example.demosqlite2.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.demosqlite2.DTO.ProductDTO;
import com.example.demosqlite2.DBHelper.MyDbHelper;

import java.util.ArrayList;

public class ProductDAO {
    private SQLiteDatabase db;
    private MyDbHelper dbHelper;

    public ProductDAO(Context context) {
        dbHelper = new MyDbHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    // Thêm mới sản phẩm
    public int insertProduct(ProductDTO product) {
        ContentValues values = new ContentValues();
        values.put("name", product.getName());
        values.put("price", product.getPrice());
        values.put("id_cat", product.getCategoryId()); // id_cat phải được truyền từ bên ngoài

        long result = db.insert("tb_product", null, values);
        return (int) result; // Trả về ID của sản phẩm mới hoặc -1 nếu lỗi
    }

    // Lấy danh sách tất cả sản phẩm
    public ArrayList<ProductDTO> getAllProducts() {
        ArrayList<ProductDTO> productList = new ArrayList<>();
        Cursor cursor = db.query("tb_product", null, null, null, null, null, null);

        if (cursor != null) {
            while (cursor.moveToNext()) {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
                double price = cursor.getDouble(cursor.getColumnIndexOrThrow("price"));
                int categoryId = cursor.getInt(cursor.getColumnIndexOrThrow("id_cat"));

                ProductDTO product = new ProductDTO(id, name, price, categoryId);
                productList.add(product);
            }
            cursor.close();
        }
        return productList;
    }

    // Cập nhật sản phẩm
    public int updateProduct(ProductDTO product) {
        ContentValues values = new ContentValues();
        values.put("name", product.getName());
        values.put("price", product.getPrice());
        values.put("id_cat", product.getCategoryId());

        return db.update("tb_product", values, "id=?", new String[]{String.valueOf(product.getId())});
    }

    // Xóa sản phẩm
    public int deleteProduct(int productId) {
        return db.delete("tb_product", "id=?", new String[]{String.valueOf(productId)});
    }
}
