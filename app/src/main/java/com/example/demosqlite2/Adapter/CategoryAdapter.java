package com.example.demosqlite2.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.demosqlite2.AddProductACtivity;
import com.example.demosqlite2.DTO.CategoryDTO;
import com.example.demosqlite2.EditCatActivity;
import com.example.demosqlite2.ProductActivity;
import com.example.demosqlite2.R;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CatViewHolder> {
    Context context;
    ArrayList<CategoryDTO> lstCat;
    ActivityResultLauncher<Intent> toolLauncher;

    public CategoryAdapter(Context context, ArrayList<CategoryDTO> lstCat) {
        this.context = context;
        this.lstCat = lstCat;

        // Khởi tạo ActivityResultLauncher trong ProductActivity
        if (context instanceof ProductActivity) {
            toolLauncher = ((ProductActivity) context).registerForActivityResult(
                    new ActivityResultContracts.StartActivityForResult(),
                    new ActivityResultCallback<ActivityResult>() {
                        @Override
                        public void onActivityResult(ActivityResult result) {
                            // Xử lý phản hồi từ Activity
                        }
                    }
            );
        } else {

        }
    }

    @NonNull
    @Override
    public CatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View v = inflater.inflate(R.layout.item_product, parent, false); // Sửa lại tên layout cho item của RecyclerView
        return new CatViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CatViewHolder holder, int position) {
        CategoryDTO objCat = lstCat.get(position);
        holder.tvId.setText(String.valueOf(objCat.getId()));
        holder.tvName.setText(objCat.getName());
        holder.tvPrice.setText(objCat.getId() + " ");
        holder.tvEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, EditCatActivity.class);
                i.putExtra("id_cat", objCat.getId());
                i.putExtra("name_cat", objCat.getName());
                i.putExtra("price_cat", objCat.getId());
                toolLauncher.launch(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return lstCat.size();
    }

    public static class CatViewHolder extends RecyclerView.ViewHolder {
        TextView tvId, tvName, tvEdit, tvPrice;

        public CatViewHolder(@NonNull View itemView) {
            super(itemView);
            tvId = itemView.findViewById(R.id.tv_id);
            tvName = itemView.findViewById(R.id.tv_name);
            tvEdit = itemView.findViewById(R.id.tv_edit);
            tvPrice = itemView.findViewById(R.id.tv_price);
        }
    }
}
