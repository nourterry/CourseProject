package com.example.courseproject;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class SignUpActivity extends AppCompatActivity {
    TextInputEditText editFullName,editEmail,editUserName,editPassword,editConfirmPassword,editBirthdate,editPhone,editAddress;
    Spinner Country;
    CheckBox checkAdministrator;
    RadioButton radioMale,radioFemale;
    Button btnSignUp;
    TextView txtFill,txtRewrite;
    String countryList[]={"GazaStrip","South Gaza","North Gaza"};
    Calendar calendar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        initUiComponent();
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignUpUser();
            }
        });
    }



    private void initUiComponent() {
        editFullName=findViewById(R.id.edit_fullname);
        editEmail=findViewById(R.id.edit_username_login);
        editUserName=findViewById(R.id.edit_username);
        editPassword=findViewById(R.id.edit_password);
        editConfirmPassword=findViewById(R.id.edit_confirm_password);
        editBirthdate=findViewById(R.id.edit_birthdate);
        editPhone=findViewById(R.id.edit_phone);
        editAddress=findViewById(R.id.edit_address);
        Country=findViewById(R.id.spinnerCountry);
        radioMale=findViewById(R.id.radio_male);
        checkAdministrator=findViewById(R.id.checkbox);
        radioFemale=findViewById(R.id.radio_female);
        btnSignUp=findViewById(R.id.btn_signup);
        txtFill=findViewById(R.id.txt_fill_field);
        txtRewrite=findViewById(R.id.txt_rewrite_correctly);
        Country.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,countryList));
        Country.setSelection(0);
        calendar=Calendar.getInstance();
        DatePickerDialog.OnDateSetListener date=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR,year);
                calendar.set(Calendar.YEAR,year);
                calendar.set(Calendar.MONTH,month);
                calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);
                upDateEditBirthDay();


            }
        };
        editBirthdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(SignUpActivity.this,date,calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)).show();

            }
        });



    }


    private void SignUpUser() {
     //check if all field is filled
  if(editFullName.getText().toString().equals("")&&editEmail.getText().toString().equals("")&&editUserName.getText().toString().equals("")
        &&editPassword.getText().toString().equals("")&&editConfirmPassword.getText().toString().equals("")&&editBirthdate.getText().toString().equals("")
        &&editPhone.getText().toString().equals("")&&editAddress.getText().toString().equals("")&&(radioFemale.isChecked()||radioMale.isChecked())){
        txtFill.setVisibility(View.VISIBLE);
        txtRewrite.setVisibility(View.GONE);
        }else{
      txtFill.setVisibility(View.GONE);
      if(!editConfirmPassword.getText().toString().equals(editPassword.getText().toString())){
          txtRewrite.setVisibility(View.VISIBLE);
      }else{
          txtRewrite.setVisibility(View.GONE);
          saveUserDataInSharedPreference();


      }
  }

    }

    private void saveUserDataInSharedPreference() {
        JSONArray jsonArray;
        SharedPreferences sharedPreferences=getSharedPreferences("Users",MODE_PRIVATE);
        try {

            Log.d("TAG","Start initail json Array");
            if(sharedPreferences.getString("users", null)==null){
                jsonArray=new JSONArray();

            }else {
                jsonArray= new JSONArray(sharedPreferences.getString("users", ""));
            }
            JSONObject jsonObject = getJsonObject();
            Log.d("TAG","get json object correct");
            jsonArray.put(jsonObject);
            Log.d("TAG","put json object correct");
            Log.d("TAG", jsonArray.toString());
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("users", jsonArray.toString());
            editor.apply();
            Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show();
            gotheLoginActivity();
        }catch (JSONException e){
            Log.d("Error in json Array",e.getMessage());
        }

        //saved in sharedPreference
        //save the data and return it to login
    }



    private JSONObject getJsonObject() {
        //save user data in json object
        JSONObject jsonObject = new JSONObject();
        try {
            Log.d("TAG","Start put data");
            jsonObject.put("fullname", editFullName.getText().toString());
            jsonObject.put("email",editEmail.getText().toString());
            jsonObject.put("username",editUserName.getText().toString());
            jsonObject.put("password",editPassword.getText().toString());
            jsonObject.put("phone",editPhone.getText().toString());
            jsonObject.put("country",Country.getSelectedItem().toString());
            jsonObject.put("address",editAddress.getText().toString());
            jsonObject.put("birthdate",editBirthdate.getText().toString());
            boolean gender=radioFemale.isChecked();
            jsonObject.put("gender",gender);
            jsonObject.put("administrator",checkAdministrator.isChecked());
            Log.d("TAG","put data end correctly");

        } catch (JSONException e) {
            Log.d("Error in json object",e.getMessage());

        }
        return jsonObject;
    }
    private void upDateEditBirthDay() {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.getDefault());
        editBirthdate.setText(sdf.format(calendar.getTime()));
    }
    private void gotheLoginActivity() {
     SharedPreferences sharedPreferences=getSharedPreferences("savedUser",MODE_PRIVATE);
     SharedPreferences.Editor editor=sharedPreferences.edit();
     editor.putString("username",editUserName.getText().toString());
     editor.putString("password",editPassword.getText().toString());
     editor.apply();
     startActivity(new Intent(SignUpActivity.this,LoginActivity.class));
     finish();
    }

}