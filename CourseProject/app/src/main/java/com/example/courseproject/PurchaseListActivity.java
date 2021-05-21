package com.example.courseproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import com.example.courseproject.Adapter.PurchaseAdapter;
import com.example.courseproject.entity.Purchase;

import java.util.ArrayList;
import java.util.List;

public class PurchaseListActivity extends AppCompatActivity {
   Toolbar toolbar;
   TextView txtToolbar;
   RecyclerView recyclerPurchase;
   List<Purchase> purchaseList=new ArrayList<>();
   PurchaseDatabase purchaseDatabase;
   SharedPreferences sharedPreferences;
    PurchaseAdapter purchaseAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase_list);
        toolbar=findViewById(R.id.toolbar);
        txtToolbar=toolbar.findViewById(R.id.txt_toolbar);
        txtToolbar.setText(getString(R.string.all_purhase_list));
        sharedPreferences=getSharedPreferences("currentUser",MODE_PRIVATE);
        recyclerPurchase=findViewById(R.id.recycle_purchase);
        recyclerPurchase.setLayoutManager(new LinearLayoutManager(this));
        purchaseDatabase=new PurchaseDatabase(this);
        purchaseList=purchaseDatabase.getList(sharedPreferences.getString("username",""));
        if(getIntent().getIntExtra(SettingsActivity.INTENT_PURCHASE_EXTRA,0)==SettingsActivity.INTENT_ALL_PURCHASE){
             purchaseAdapter=new PurchaseAdapter(this,purchaseList);

        }else {
            List<Purchase> LastItem=new ArrayList<>();
            if(purchaseList.size()>0) {
                LastItem.add(purchaseList.get(purchaseList.size() - 1));
            }
            purchaseAdapter=new PurchaseAdapter(this,LastItem);

        }
        recyclerPurchase.setAdapter(purchaseAdapter);

    }
}