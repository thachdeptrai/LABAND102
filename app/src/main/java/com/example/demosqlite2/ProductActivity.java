package com.example.demosqlite2;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
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
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

public class ProductActivity extends AppCompatActivity {
    ProductDAO productDAO;
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
                        listProduct.addAll(productDAO.getAllProducts());
                        adapter.notifyDataSetChanged();
                    }
                }
            }
    );

    FloatingActionButton fab,btnAddProductDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product); // Đảm bảo sử dụng đúng layout
        rcListProduct = findViewById(R.id.rc_list_product);
        fab = findViewById(R.id.btn_add_product);
        btnAddProductDialog = findViewById(R.id.btnShowDialog);
        // Khởi tạo DAO và lấy danh sách sản phẩm
        productDAO = new ProductDAO(this);
        listProduct = productDAO.getAllProducts();

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
        // Xử lý sự kiện FloatingActionButton để hiển thị Dialog
        btnAddProductDialog.setOnClickListener(v -> showAddProductDialog());
    }
    private void showAddProductDialog() {

        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_add_product);
        dialog.setCancelable(true);

        TextInputLayout inputTen = dialog.findViewById(R.id.input_ten);
        TextInputLayout inputPrice = dialog.findViewById(R.id.input_price);
        Spinner spinnerIdCat = dialog.findViewById(R.id.spinner_idcat);
        Button btnLuu = dialog.findViewById(R.id.btnluu);

        CategoryDAO categoryDAO = new CategoryDAO(this);
        ArrayList<CategoryDTO> categoryList = categoryDAO.getList();
        ArrayAdapter<CategoryDTO> categoryAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categoryList);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerIdCat.setAdapter(categoryAdapter);

        btnLuu.setOnClickListener(view -> {
            String name = inputTen.getEditText().getText().toString();
            String priceStr = inputPrice.getEditText().getText().toString();
            CategoryDTO selectedCategory = (CategoryDTO) spinnerIdCat.getSelectedItem();

            if (name.isEmpty() || priceStr.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập đủ thông tin!", Toast.LENGTH_SHORT).show();
                return;
            }

            double price = Double.parseDouble(priceStr);
            int categoryId = selectedCategory.getId();

            ProductDTO product = new ProductDTO(name, price, categoryId);
            int newId = productDAO.insertProduct(product);

            if (newId > 0) {
                listProduct.clear();
                listProduct.addAll(productDAO.getAllProducts());
                adapter.notifyDataSetChanged();
                Toast.makeText(this, "Thêm sản phẩm thành công!", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            } else {
                Toast.makeText(this, "Lỗi khi thêm sản phẩm!", Toast.LENGTH_SHORT).show();
            }
        });
        dialog.show();
    }
}