package com.example.courseproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.courseproject.entity.Purchase;

public class ProductActivity extends AppCompatActivity {
    ImageView imgProduct,img_back;
    TextView txtUnit,txtTotal,txtToolbar,txtNumber;
    EditText editProduct;
    Button btnAdd,btnSub,btnSave;
    Toolbar toolbar;
    PurchaseDatabase purchaseDatabase;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        initUiComponent();
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int number=Integer.parseInt(txtNumber.getText().toString());
                int price=Integer.parseInt(txtUnit.getText().toString());
                number++;
                txtNumber.setText(String.valueOf(number));
                txtTotal.setText(String.valueOf(price*number));

            }
        });
        btnSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int number=Integer.parseInt(txtNumber.getText().toString());
                int price=Integer.parseInt(txtUnit.getText().toString());
                if(number!=1){
                    number--;
                    txtNumber.setText(String.valueOf(number));
                    txtTotal.setText(String.valueOf(price*number));
                }
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username=sharedPreferences.getString("username","");
                Purchase purchase=new Purchase(txtToolbar.getText().toString(),username,txtTotal.getText().toString(),String.valueOf(System.currentTimeMillis()));
              boolean insert=  purchaseDatabase.insertPurchase(purchase);
              if(insert){
                  Toast.makeText(ProductActivity.this, "Added to purchase successfully", Toast.LENGTH_SHORT).show();
              }else{
                  Toast.makeText(ProductActivity.this, " Failed Added to purchase", Toast.LENGTH_SHORT).show();
              }
            }
        });
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void initUiComponent() {
        toolbar=findViewById(R.id.toolbar);
        txtToolbar=toolbar.findViewById(R.id.txt_toolbar);
        img_back=toolbar.findViewById(R.id.img_back);
        imgProduct=findViewById(R.id.product_image);
        txtUnit=findViewById(R.id.txt_unit_price);
        txtTotal=findViewById(R.id.txt_total_price);
        editProduct=findViewById(R.id.edit_details);
        btnAdd=findViewById(R.id.btn_plus);
        btnSub=findViewById(R.id.btn_sub);
        btnSave=findViewById(R.id.btn_save);
        txtToolbar.setText(getIntent().getStringExtra("name"));
        txtUnit.setText(getIntent().getStringExtra("price"));
        txtNumber=findViewById(R.id.txt_number);
        txtTotal.setText(getIntent().getStringExtra("price"));
        editProduct.setText(getIntent().getStringExtra("description"));
        Glide.with(this)
                .load(getIntent().getStringExtra("image"))
                .into(imgProduct);
    purchaseDatabase=new PurchaseDatabase(this);
    sharedPreferences=getSharedPreferences("currentUser",MODE_PRIVATE);
    }
}