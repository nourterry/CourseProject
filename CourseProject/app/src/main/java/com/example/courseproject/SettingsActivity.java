package com.example.courseproject;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SettingsActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String INTENT_PURCHASE_EXTRA ="purchase" ;
    public static final int INTENT_ALL_PURCHASE =0 ;
    public static final int INTENT_LAST_PURCHASE =1 ;
    Toolbar toolbar;
   TextView txtToolbar;
   CardView cardAllPurchase,cardLastPurchase,cardChangePassword,cardClearPurchase,cardAddItem;
   SharedPreferences sharedPreferences;
   PurchaseDatabase purchaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        toolbar=findViewById(R.id.toolbar);
        txtToolbar=toolbar.findViewById(R.id.txt_toolbar);
        cardAllPurchase=findViewById(R.id.card_all_purchase);
        cardLastPurchase=findViewById(R.id.card_last_purchase);
        cardChangePassword=findViewById(R.id.card_change_password);
        cardClearPurchase=findViewById(R.id.card_clear_purchase);
        cardAddItem=findViewById(R.id.card_add_item);
        txtToolbar.setText(getString(R.string.Settings));
        setSupportActionBar(toolbar);
        sharedPreferences=getSharedPreferences("currentUser",MODE_PRIVATE);
        if(sharedPreferences.getBoolean("administrator",false)){
            cardAddItem.setVisibility(View.VISIBLE);
        }else{
            cardAddItem.setVisibility(View.GONE);
        }

        cardAllPurchase.setOnClickListener(this);
        cardLastPurchase.setOnClickListener(this);
        cardChangePassword.setOnClickListener(this);
        cardClearPurchase.setOnClickListener(this);
        cardAddItem.setOnClickListener(this);
       purchaseDatabase=new PurchaseDatabase(this);



    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case  R.id.card_all_purchase:
                Intent intent=new Intent(SettingsActivity.this,PurchaseListActivity.class);
                intent.putExtra(INTENT_PURCHASE_EXTRA,INTENT_ALL_PURCHASE);
                startActivity(intent);
                break;
            case  R.id.card_last_purchase:
                Intent intent1=new Intent(SettingsActivity.this,PurchaseListActivity.class);
                intent1.putExtra(INTENT_PURCHASE_EXTRA,INTENT_LAST_PURCHASE);
                startActivity(intent1);
                break;
            case  R.id.card_change_password:
                  showDialog();
                break;
            case  R.id.card_clear_purchase:
                SharedPreferences sharedPreferences=getSharedPreferences("currentUser",MODE_PRIVATE);
               boolean result= purchaseDatabase.deletePurchase(sharedPreferences.getString("username",""));
               if(result){
                   Toast.makeText(this, "Clear All purchase Done", Toast.LENGTH_SHORT).show();
               }
                break;
            case  R.id.card_add_item:
                startActivity(new Intent(SettingsActivity.this,AddItemActivity.class));

                break;
        }

    }

    private void showDialog() {
        AlertDialog alertDialog;
        View view= LayoutInflater.from(this).inflate(R.layout.change_password,null,false);
        EditText editNewPassword=view.findViewById(R.id.new_password);
        EditText editRewritePassword=view.findViewById(R.id.rewrite_password);
        TextView txtMatch=view.findViewById(R.id.password_not_matched);
        Button btnSave=view.findViewById(R.id.btn_save);

        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setView(view);
        builder.setCancelable(true);
        alertDialog=builder.create();
        alertDialog.show();
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!editNewPassword.getText().toString().equals("")&&!editRewritePassword.getText().toString().equals("")){
                 if(editNewPassword.getText().toString().equals(editRewritePassword.getText().toString())){
                     editSharedPreference(editNewPassword.getText().toString());
                     txtMatch.setVisibility(View.GONE);
                     alertDialog.dismiss();

                 }else {
                     txtMatch.setVisibility(View.VISIBLE);
                 }
                }
            }
        });
    }

    private void editSharedPreference(String password) {
        SharedPreferences sharedCurrentUser=getSharedPreferences("currentUser",MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString("password",password);
        editor.apply();
        String username=sharedCurrentUser.getString("username","");
        SharedPreferences sharedPreferences=getSharedPreferences("Users",MODE_PRIVATE);
        String s = sharedPreferences.getString("users", null);
        JSONArray jsonArray=new JSONArray();
        if (s != null) {
            try {
                 jsonArray=new JSONArray(s);
                for(int i=0;i<jsonArray.length();i++){
                    JSONObject jsonObject= (JSONObject) jsonArray.get(i);
                    if(((JSONObject) jsonArray.get(i)).getString("username").equals(username)){
                        ((JSONObject) jsonArray.get(i)).put("password",password);
                        break;
                    }
                }

            }catch (JSONException e){
                Log.d("Error in json Array",e.getMessage());
            }
        }
        SharedPreferences.Editor editor1 = sharedPreferences.edit();
        editor1.putString("users", jsonArray.toString());
        editor1.apply();
        sharedPreferences=getSharedPreferences("savedUser",MODE_PRIVATE);
        if(sharedPreferences.getString("username","").equals(username)) {
            SharedPreferences.Editor editor2 = sharedPreferences.edit();
            editor2.putString("password", password);
            editor2.apply();
        }
    }
}