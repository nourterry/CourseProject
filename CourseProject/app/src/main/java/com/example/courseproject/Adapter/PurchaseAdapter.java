package com.example.courseproject.Adapter;

import android.content.Context;
import android.util.Log;
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
import com.example.courseproject.entity.Purchase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class PurchaseAdapter extends RecyclerView.Adapter<PurchaseAdapter.PurchaseHolder> {
    Context context;
    List<Purchase> purchases;

    public PurchaseAdapter(Context context, List<Purchase> purchases) {
        this.context = context;
        this.purchases = purchases;
    }

    @NonNull
    @Override
    public PurchaseAdapter.PurchaseHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.purchase_list_cell,parent,false);
        return new PurchaseAdapter.PurchaseHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PurchaseAdapter.PurchaseHolder holder, int position) {
        Purchase purchase=purchases.get(position);
        holder.txtName.setText(purchase.getName());
        Log.d("price",purchase.getPrice());
        holder.txtPrice.setText(purchase.getPrice());
        long yourmilliseconds = Long.parseLong(purchase.getTimeanddate());
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd,yyyy  HH:mm");
        Date resultdate = new Date(yourmilliseconds);
        holder.txtDate.setText(sdf.format(resultdate));
    }




    @Override
    public int getItemCount() {
        return purchases.size();
    }


    public class PurchaseHolder extends RecyclerView.ViewHolder{
        TextView txtName,txtPrice,txtDate;
        public PurchaseHolder(@NonNull View itemView) {
            super(itemView);
            txtName=itemView.findViewById(R.id.purchase_name);
            txtPrice=itemView.findViewById(R.id.purchase_price);
            txtDate=itemView.findViewById(R.id.purchase_date);

        }
    }
}

