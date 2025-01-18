//package com.example.demosqlite2;
//
//import android.os.Bundle;
//import android.widget.*;
//
//import androidx.activity.EdgeToEdge;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.core.graphics.Insets;
//import androidx.core.view.ViewCompat;
//import androidx.core.view.WindowInsetsCompat;
//
//import com.example.demosqlite2.DAO.billDao;
//import com.example.demosqlite2.DTO.BillDTO;
//
//public class AddBillActivity extends AppCompatActivity {
//
//    private EditText etFullname, etAddress, etCreatedDate;
//    private Button btnSave;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_add_bill);
//
//        // Ánh xạ các thành phần
//        etFullname = findViewById(R.id.etFullname);
//        etAddress = findViewById(R.id.etAddress);
//        etCreatedDate = findViewById(R.id.etCreatedDate);
//        btnSave = findViewById(R.id.btnSave);
//
//        // Lưu hóa đơn
//        btnSave.setOnClickListener(v -> {
//            String fullname = etFullname.getText().toString().trim();
//            String address = etAddress.getText().toString().trim();
//            String createdDate = etCreatedDate.getText().toString().trim();
//
//            if (fullname.isEmpty() || address.isEmpty() || createdDate.isEmpty()) {
//                Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
//                return;
//            }
//
//            BillDTO bill = new BillDTO(fullname, address, createdDate);
//            billDao billa = new billDao(this);
//            billa.addBill(bill); // Phương thức thêm vào database
//
//            Toast.makeText(this, "Hóa đơn được thêm thành công!", Toast.LENGTH_SHORT).show();
//            finish(); // Quay lại màn hình trước
//        });
//    }
//}
