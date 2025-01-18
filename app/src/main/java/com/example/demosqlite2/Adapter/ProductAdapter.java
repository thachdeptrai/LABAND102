package com.example.demosqlite2.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.demosqlite2.CategoryActivity;
import com.example.demosqlite2.DTO.CategoryDTO;
import com.example.demosqlite2.DTO.ProductDTO;
import com.example.demosqlite2.EditCatActivity;
import com.example.demosqlite2.ProductActivity;
import com.example.demosqlite2.R;

import java.util.ArrayList;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.HiViewHolder> {
    Context context ;
    ArrayList<ProductDTO> lstCat;

    ActivityResultLauncher<Intent> toolLauncher ;

    public ProductAdapter(Context context, ArrayList<ProductDTO> lstCat) {
        this.context = context;
        this.lstCat = lstCat;
        toolLauncher = ((ProductActivity) context).registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                        // Cập nhật dữ liệu mới từ kết quả trả về (nếu cần)
                        Intent data = result.getData();
                        // Lấy dữ liệu từ Intent và cập nhật danh sách lstCat
                    }
                }
        );
    }
    @NonNull
    @Override
    public HiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.item_product, parent, false);
        return new HiViewHolder(v); // Đảm bảo trả về đúng HiViewHolder
    }


//    @Override
//    public void onBindViewHolder(@NonNull CategoryAdapter.CatViewHolder holder, int position) {
//
//    }

    @Override
    public void onBindViewHolder(@NonNull HiViewHolder holder, int position) {
        // Lấy thông tin bản ghi
        ProductDTO objCat = lstCat.get(position);

        if (objCat != null) {
            holder.tvId.setText(String.valueOf(objCat.getId())); // Hiển thị ID
            holder.tvName.setText(objCat.getName()); // Hiển thị tên
            holder.tvPrice.setText(String.valueOf(objCat.getPrice())); // Hiển thị giá
            holder.tvEdit.setOnClickListener(v -> {
                Intent i = new Intent(context, EditCatActivity.class);
                i.putExtra("id_cat", objCat.getId());
                i.putExtra("name_cat", objCat.getName());
                i.putExtra("price_cat", objCat.getPrice());
                toolLauncher.launch(i);
            });
        }
    }


    @Override
    public int getItemCount() {
        return lstCat.size();// trả về số lượng ( điều quan trọng để hiển thị dữ liệu lên màn hình )
    }


    //1. Tạo lớp view holder
    class HiViewHolder extends RecyclerView.ViewHolder{
        TextView tvId , tvName , tvPrice , tvEdit ;
        public HiViewHolder(@NonNull View itemView) {
            super(itemView);
            tvId = itemView.findViewById(R.id.tv_id);
            tvName = itemView.findViewById(R.id.tv_name);
            tvPrice = itemView.findViewById(R.id.tv_price);
            tvEdit = itemView.findViewById(R.id.tv_edit);
        }
    }
}
