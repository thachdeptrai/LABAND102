package com.example.demosqlite2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.demosqlite2.Adapter.CategoryAdapter;
import com.example.demosqlite2.DAO.CategoryDAO;
import com.example.demosqlite2.DTO.CategoryDTO;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class CategoryActivity extends AppCompatActivity {
    CategoryDAO categoryDAO;
    CategoryDTO objCat;
    ArrayList<CategoryDTO> listCat;
    String TAG = "zzzzzzzzzzzzzzz";
    CategoryAdapter adapter ;
    RecyclerView rcListCat;
    TextView textView;

    ActivityResultLauncher<Intent> toolLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult o) {
                    // đây là chỗ để xử lý tương tác khi AddCatActivity phản hồi lại
                    if(o.getResultCode() == 999 ) {
                        // lấy câu thông báo
                        String msg = o.getData().getStringExtra("msg");
                        Toast.makeText(CategoryActivity.this , msg , Toast.LENGTH_SHORT).show();

                        //update danh sách
                        listCat.clear();
                        listCat.addAll(categoryDAO.getList());
                        adapter.notifyDataSetChanged();
                    }

                }
            }
    );

    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_category);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        //========
        rcListCat = findViewById(R.id.rc_list_cat);


        categoryDAO = new CategoryDAO(this);
        listCat = categoryDAO.getList();
        adapter = new CategoryAdapter(this ,listCat);
        rcListCat.setAdapter(adapter);

        //================
        fab = findViewById(R.id.btn_add_cat);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentAdd = new Intent(CategoryActivity.this , AddCatActivity.class);
                toolLauncher.launch(intentAdd);
            }
        });








    }
}