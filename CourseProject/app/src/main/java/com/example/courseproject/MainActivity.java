package com.example.courseproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.courseproject.Adapter.ProductsAdapter;
import com.example.courseproject.entity.Product;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
   RecyclerView recyclerView;
   ProductDatabase productDatabase;
   EditText editSearch;
    List<Product> productList;
    ProductsAdapter productsAdapter;
    RadioGroup radioGroup;
    String filterName="";
    int checked_group=-1;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        editSearch=findViewById(R.id.edit_search);
        radioGroup=findViewById(R.id.radiogroup);
        recyclerView=findViewById(R.id.recycle_restaurants);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
         productDatabase=new ProductDatabase(this);
         productList=productDatabase.getList();
//         Log.d("sizeAdapter",productList.get(10).getName()+"");
         productsAdapter=new ProductsAdapter(this, productList, new ProductsAdapter.OnItemClickedListener() {
             @Override
             public void onClick(String name, String description, String price, String isCash, String image) {
                 Intent intent=new Intent(MainActivity.this,ProductActivity.class);
                 intent.putExtra("name",name);
                 intent.putExtra("description",description);
                 intent.putExtra("price",price);
                 intent.putExtra("isCash",isCash);
                 intent.putExtra("image",image);
                startActivity(intent);

             }
         });
        recyclerView.setAdapter(productsAdapter);
        recyclerView.setHasFixedSize(true);

        editSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                filterName=s.toString();
                filterList(filterName,checked_group);
            }
        });
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                Log.d("Checked",checkedId+"");
                if(checkedId==R.id.radio_cash){
                    checked_group=1;
                }else {
                    checked_group=0;
                }
                filterList(filterName,checked_group);
            }
        });
    }

    private void filterList(String filterName,int checked) {
        List<Product> filterProductList=new ArrayList<>();
        if(filterName.equals("")&&checked==-1){
            filterProductList.clear();
            filterProductList=productList;
        }
       else if(filterName.equals("")&&checked!=-1){
            filterProductList.clear();
            for(int i=0;i<productList.size();i++){
                if(productList.get(i).getIsCash().equals(String.valueOf(checked))){
                    filterProductList.add(productList.get(i));
                }
            }
        }else if(!filterName.equals("")&&checked==-1){
            filterProductList.clear();
            for(int i=0;i<productList.size();i++){
                if(productList.get(i).getName().contains(filterName)){
                    filterProductList.add(productList.get(i));
                }
            }
        }
        else{

            filterProductList.clear();
            for(int i=0;i<productList.size();i++){
                if(productList.get(i).getName().contains(filterName)&&productList.get(i).getIsCash().equals(String.valueOf(checked))){
                    filterProductList.add(productList.get(i));
                }
            }
        }
        productsAdapter.setList(filterProductList);
        productsAdapter.notifyDataSetChanged();
        }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.setting_item:
                startActivity(new Intent(MainActivity.this,SettingsActivity.class));
                break;
            case  R.id.logout_item:
                startActivity(new Intent(MainActivity.this,LoginActivity.class));
                finish();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        productList.clear();
        productList=productDatabase.getList();
        productsAdapter.setList(productList);
        productsAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        productList.clear();
        productList=productDatabase.getList();
        productsAdapter.setList(productList);
        productsAdapter.notifyDataSetChanged();
    }
}
