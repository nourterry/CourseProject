package com.example.courseproject.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.courseproject.R;
import com.example.courseproject.entity.Product;

import java.util.List;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ProductHolder> {
    Context context;
    List<Product> products;
    OnItemClickedListener onItemClickedListener;
     public interface OnItemClickedListener{
          void onClick(String name,String description,String price,String isCash,String image );
      }

    public ProductsAdapter(Context context, List<Product> products,OnItemClickedListener onItemClickedListener) {
        this.context = context;
        this.products = products;
        this.onItemClickedListener=onItemClickedListener;
    }

    @NonNull
    @Override
    public ProductHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.products_cell,parent,false);
        return new ProductHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductHolder holder, int position) {
        Product product=products.get(position);
        holder.txtName.setText(product.getName());
        holder.txtPrice.setText(product.getPrice());
        int isCash=Integer.parseInt(product.getIsCash());
        if(isCash==1){
           holder.txtCash.setText(R.string.cash);
        }else{
            holder.txtCash.setText(R.string.installment);

        }
        Glide
        .with(context)
        .load(product.getImage())
        .centerCrop()
        .into(holder.imgProduct);
       //  .onLoadFailed(R.drawable.load_image_error);
     holder.itemView.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Product product=products.get(position);
            onItemClickedListener.onClick(product.getName(),product.getDescription(),product.getPrice(),product.getIsCash(),product.getImage());

        }
    });


    }

    @Override
    public int getItemCount() {
        return products.size();
    }
    public void setList(List<Product> products){
        this.products=products;

    }

    public class ProductHolder extends RecyclerView.ViewHolder{
         ImageView imgProduct;
         TextView txtName,txtCash,txtPrice;
        public ProductHolder(@NonNull View itemView) {
            super(itemView);
            imgProduct=itemView.findViewById(R.id.img_products);
            txtName=itemView.findViewById(R.id.txt_name_product);
            txtCash=itemView.findViewById(R.id.txt_cash);
            txtPrice=itemView.findViewById(R.id.txt_price);

        }
    }

}
