package com.example.courseproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {
   TextInputEditText editUserName,editPassword;
   SharedPreferences sharedPreferences;
   TextView txt_error;
    CheckBox checkRemember;
    boolean isFound;
    boolean isAdministrator=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initUiComponent();
        findViewById(R.id.signup_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,SignUpActivity.class));
            }
        });
        findViewById(R.id.login_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkIfUserRegister();

            }
        });
    }

    private void initUiComponent() {
        editUserName=findViewById(R.id.edit_username_login);
        editPassword=findViewById(R.id.edit_password);
        sharedPreferences=getSharedPreferences("savedUser",MODE_PRIVATE);
        editUserName.setText(sharedPreferences.getString("username",""));
        editPassword.setText(sharedPreferences.getString("password",""));
        txt_error=findViewById(R.id.txt_error_login);
        checkRemember=findViewById(R.id.chechbox_remember);

    }
    private void checkIfUserRegister() {
        sharedPreferences = getSharedPreferences("Users", MODE_PRIVATE);
        String s = sharedPreferences.getString("users", null);
        if (s != null) {
            txt_error.setVisibility(View.GONE);
            try {
                JSONArray jsonArray=new JSONArray(s);
                for(int i=0;i<jsonArray.length();i++){
                    JSONObject jsonObject= (JSONObject) jsonArray.get(i);
                    if(jsonObject.getString("username").equals(editUserName.getText().toString())
                       &&jsonObject.getString("password").equals(editPassword.getText().toString())){
                        isFound=true;
                        isAdministrator=jsonObject.getBoolean("administrator");
                        goToMainActivity();
                        break;
                    }
                }
                if(!isFound)
                txt_error.setVisibility(View.VISIBLE);
            }catch (JSONException e){
                Log.d("Error in json Array",e.getMessage());
            }
        }else{
            txt_error.setVisibility(View.VISIBLE);

        }
    }

    private void goToMainActivity() {
        if(checkRemember.isChecked()){
            sharedPreferences=getSharedPreferences("savedUser",MODE_PRIVATE);
            SharedPreferences.Editor editor=sharedPreferences.edit();
            editor.putString("username",editUserName.getText().toString());
            editor.putString("password",editPassword.getText().toString());
            editor.apply();
        }
        sharedPreferences=getSharedPreferences("currentUser",MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString("username",editUserName.getText().toString());
        editor.putString("password",editPassword.getText().toString());
        editor.putBoolean("administrator",isAdministrator);
        editor.apply();
        startActivity(new Intent(LoginActivity.this,MainActivity.class));

    }
}