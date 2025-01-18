package com.example.demosqlite2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.demosqlite2.DAO.CategoryDAO;
import com.example.demosqlite2.DTO.CategoryDTO;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    CategoryDAO categoryDAO;
    CategoryDTO objCat;
    ArrayList<CategoryDTO> listCat;
    String TAG = "zzzzzzzzzzzzzzz";
    Button  btnCategory , btnProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        //=================
        btnCategory = findViewById(R.id.btn_category);
        btnCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity( new Intent(MainActivity.this , CategoryActivity.class));
            }
        });

        btnProduct = findViewById(R.id.btn_product);
        btnProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ProductActivity.class));
            }
        });




//        categoryDAO = new CategoryDAO(this);
//        listCat = categoryDAO.getList();
//        // xem log danh sách trước khi thêm dữ liệu
//        Log.d(TAG, "onCreate: Trước khi thêm " + listCat.size());
//        for (int i = 0; i<listCat.size(); i++) {
//         objCat = listCat.get(i);
//         Log.d(TAG, "onCreate: " + i + "==> " +objCat.toString());
//        }
//        // thêm mới bản ghi
//        objCat = new CategoryDTO("Tivi2");
//        categoryDAO.InsertRow(objCat);
//        objCat = new CategoryDTO("Tủ lạnh2");
//        categoryDAO.InsertRow(objCat);
//
//        // xem log danh sách trước khi thêm dữ liệu
//        Log.d(TAG, "onCreate: Sau khi thêm " + listCat.size());
//        for (int i = 0; i<listCat.size(); i++) {
//            objCat = listCat.get(i);
//            Log.d(TAG, "onCreate: " + i + "==> " +objCat.toString());
//        }

    }
}