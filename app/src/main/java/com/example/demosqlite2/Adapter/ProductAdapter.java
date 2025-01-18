package com.example.demosqlite2.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.demosqlite2.CategoryActivity;
import com.example.demosqlite2.DAO.ProductDAO;
import com.example.demosqlite2.DTO.CategoryDTO;
import com.example.demosqlite2.DTO.ProductDTO;
import com.example.demosqlite2.EditCatActivity;
import com.example.demosqlite2.EditProduct;
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
                        // Nhận dữ liệu trả về
                        Intent data = result.getData();
                        int id = data.getIntExtra("id_cat", -1);
                        String name = data.getStringExtra("name_cat");
                        double price = data.getDoubleExtra("price_cat", 0.0);

                        // Tìm và cập nhật sản phẩm trong danh sách
                        for (int i = 0; i < lstCat.size(); i++) {
                            if (lstCat.get(i).getId() == id) {
                                lstCat.get(i).setName(name);
                                lstCat.get(i).setPrice(price);
                                notifyItemChanged(i); // Cập nhật lại UI
                                break;
                            }
                        }
                    }
                }
        );
    }
    @NonNull
    @Override
    public HiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.layout_row_category, parent, false);
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
            holder.tvIdcat.setText(String.valueOf(objCat.getCategoryId()));
            holder.tvEdit.setOnClickListener(v -> {
                Intent i = new Intent(context, EditProduct.class);
                i.putExtra("id_cat", objCat.getId());
                i.putExtra("name_cat", objCat.getName());
                i.putExtra("price_cat", objCat.getPrice());
                toolLauncher.launch(i);
            });
            holder.btnDelete.setOnClickListener(v -> {
                // Xóa sản phẩm
                ProductDAO productDAO = new ProductDAO(context);
                int isDeleted = productDAO.deleteProduct(objCat.getId());

                if (isDeleted == 1) { // Thành công
                    lstCat.remove(position); // Xóa sản phẩm khỏi danh sách
                    notifyItemRemoved(position); // Cập nhật lại RecyclerView
                    Toast.makeText(context, "Sản phẩm đã được xóa", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Có lỗi xảy ra khi xóa sản phẩm", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return lstCat.size();// trả về số lượng ( điều quan trọng để hiển thị dữ liệu lên màn hình )
    }


    //1. Tạo lớp view holder
    class HiViewHolder extends RecyclerView.ViewHolder{
        TextView tvId , tvName , tvPrice , tvIdcat,tvEdit ;
        ImageButton btnDelete;
        public HiViewHolder(@NonNull View itemView) {
            super(itemView);
            tvId = itemView.findViewById(R.id.tv_id);
            tvName = itemView.findViewById(R.id.tv_name);
            tvPrice = itemView.findViewById(R.id.tv_price);
            tvIdcat = itemView.findViewById(R.id.tv_idCat);
            tvEdit = itemView.findViewById(R.id.tv_edit);
            btnDelete = itemView.findViewById(R.id.btn_delete);
        }
    }
}
