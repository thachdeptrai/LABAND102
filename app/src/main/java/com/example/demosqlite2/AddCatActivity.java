package com.example.demosqlite2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.demosqlite2.DAO.CategoryDAO;
import com.example.demosqlite2.DTO.CategoryDTO;
import com.google.android.material.textfield.TextInputLayout;

public class AddCatActivity extends AppCompatActivity {

    Button btnSave;
    TextInputLayout inputName;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_cat);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        btnSave = findViewById(R.id.btn_save);
        inputName = findViewById(R.id.input_name);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //1. lấy dữ liệu trên form
                //2. gọi lớp DAO thêm vào csdl
                //3.kiểm tra kq và thông báo
                String name = inputName.getEditText().getText().toString();
                CategoryDTO objCat = new CategoryDTO(name);
                CategoryDAO dao = new CategoryDAO(AddCatActivity.this);
                int new_id = dao.InsertRow(objCat);
                if(new_id > 0) {
                    //thêm mới thnafh công , gửi thông báo và intent
                    Intent i = new Intent();
                    i.putExtra("msg" , "thêm mới thành công");
                    setResult(999,i);
                    finish();
                } else  {
                    Toast.makeText(AddCatActivity.this,"Lỗi thêm mới , kiểm tra dữ liệu",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}