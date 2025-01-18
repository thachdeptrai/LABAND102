package com.example.demosqlite2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.demosqlite2.DAO.CategoryDAO;
import com.example.demosqlite2.DAO.ProductDAO;
import com.example.demosqlite2.DTO.CategoryDTO;
import com.example.demosqlite2.DTO.ProductDTO;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

public class AddProductACtivity extends AppCompatActivity {
    Button btnluu;
    Spinner spinnerIdCat;
    TextInputLayout inputten , inputPrice , inputIdcat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_product);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btnluu = findViewById(R.id.btnluu);
        inputten = findViewById(R.id.input_ten);
        inputPrice = findViewById(R.id.input_price);
        spinnerIdCat = findViewById(R.id.spinner_idcat);
        // lấy danh sách từ cathegory thiết lập vào spiner

        CategoryDAO categoryDAO = new CategoryDAO(this);
        ArrayList<CategoryDTO> categoryList = categoryDAO.getList();
        ArrayAdapter<CategoryDTO> categoryAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categoryList);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerIdCat.setAdapter(categoryAdapter);

        btnluu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = inputten.getEditText().getText().toString();
                String priceStr = inputPrice.getEditText().getText().toString();
                CategoryDTO selectedCategory = (CategoryDTO) spinnerIdCat.getSelectedItem();
                int categoryId = selectedCategory.getId();

                if (name.isEmpty() || priceStr.isEmpty()) {
                    Toast.makeText(AddProductACtivity.this, "Vui lòng nhập đủ thông tin!", Toast.LENGTH_SHORT).show();
                    return;
                }

                double price = Double.parseDouble(priceStr);

                ProductDTO product = new ProductDTO(name, price, categoryId);
                ProductDAO productDAO = new ProductDAO(AddProductACtivity.this);
                int newId = productDAO.insertProduct(product);

                if (newId > 0) {
                    Intent i = new Intent();
                    i.putExtra("msg", "Thêm mới sản phẩm thành công");
                    setResult(RESULT_OK, i);
                    finish();
                } else {
                    Toast.makeText(AddProductACtivity.this, "Lỗi thêm mới sản phẩm!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}

