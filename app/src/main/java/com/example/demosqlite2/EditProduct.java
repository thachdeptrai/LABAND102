package com.example.demosqlite2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.*;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class EditProduct extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_product);

        // Nhận dữ liệu từ Intent
        Intent intent = getIntent();
        int id = intent.getIntExtra("id_cat", -1);
        String name = intent.getStringExtra("name_cat");
        double price = intent.getDoubleExtra("price_cat", 0.0);

        // Ánh xạ các view
        EditText edtName = findViewById(R.id.edt_name);
        EditText edtPrice = findViewById(R.id.edt_price);

        Button btnSave = findViewById(R.id.btn_save);

        // Đặt giá trị vào các view
        edtName.setText(name);
        edtPrice.setText(String.valueOf(price));

        // Xử lý sự kiện lưu
        btnSave.setOnClickListener(v -> {
            String newName = edtName.getText().toString();
            double newPrice = Double.parseDouble(edtPrice.getText().toString());

            // Trả dữ liệu về cho Activity gọi nó
            Intent resultIntent = new Intent();
            resultIntent.putExtra("id_cat", id);
            resultIntent.putExtra("name_cat", newName);
            resultIntent.putExtra("price_cat", newPrice);
            setResult(RESULT_OK, resultIntent);
            finish();
        });
}
}