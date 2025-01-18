package com.example.demosqlite2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.demosqlite2.Adapter.CategoryAdapter;
import com.example.demosqlite2.Adapter.ProductAdapter;
import com.example.demosqlite2.DAO.CategoryDAO;
import com.example.demosqlite2.DAO.ProductDAO;
import com.example.demosqlite2.DTO.CategoryDTO;
import com.example.demosqlite2.DTO.ProductDTO;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class ProductActivity extends AppCompatActivity {
    ProductDAO categoryDAO;
    ArrayList<ProductDTO> listProduct;
    ProductAdapter adapter;
    RecyclerView rcListProduct;

    ActivityResultLauncher<Intent> toolLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    // Xử lý phản hồi từ AddProductActivity
                    if (result.getResultCode() == 999 && result.getData() != null) {
                        // Lấy câu thông báo từ AddProductActivity
                        String msg = result.getData().getStringExtra("msg");
                        Toast.makeText(ProductActivity.this, msg, Toast.LENGTH_SHORT).show();

                        // Cập nhật danh sách sản phẩm
                        listProduct.clear();
                        listProduct.addAll(categoryDAO.getAllProducts());
                        adapter.notifyDataSetChanged();
                    }
                }
            }
    );

    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product); // Đảm bảo sử dụng đúng layout
        rcListProduct = findViewById(R.id.rc_list_product);
        fab = findViewById(R.id.btn_add_product);

        // Khởi tạo DAO và lấy danh sách sản phẩm
        categoryDAO = new ProductDAO(this);
        listProduct = categoryDAO.getAllProducts();

        // Thiết lập RecyclerView và adapter
        adapter = new ProductAdapter(this, listProduct); // Truyền toolLauncher vào adapter
        rcListProduct.setLayoutManager(new LinearLayoutManager(this));
        rcListProduct.setAdapter(adapter);

        // Xử lý sự kiện FloatingActionButton để mở AddProductActivity
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentAdd = new Intent(ProductActivity.this, AddProductACtivity.class);
                toolLauncher.launch(intentAdd);
            }
        });
    }
}