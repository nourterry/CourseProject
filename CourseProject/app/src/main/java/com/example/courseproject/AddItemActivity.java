package com.example.courseproject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.courseproject.entity.Product;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;

public class AddItemActivity extends AppCompatActivity {
    private static final int PICK_IMAGE_REQ_CODE =1 ;
    private static final int PERMISSION_REQUEST_CODE = 200;
    Toolbar toolbar;
   TextView txtToolbar;
   ImageView imgProduct;
   EditText editName,editPrice,editDescription;
   Uri imgUri;
   Button btnSave;
   RadioButton radioCash;
   TextView txtPickPhoto,txtFillField;
   ProductDatabase productDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);
        toolbar = findViewById(R.id.toolbar);
        txtToolbar = toolbar.findViewById(R.id.txt_toolbar);
        txtToolbar.setText(getString(R.string.add_new_item));
        imgProduct = findViewById(R.id.img_new_product);
        editName = findViewById(R.id.name_new_product);
        editPrice = findViewById(R.id.price_new_product);
        editDescription = findViewById(R.id.description_new_product);
        txtPickPhoto = findViewById(R.id.pick_photo);
        txtFillField = findViewById(R.id.fill_field);
        btnSave = findViewById(R.id.btn_save_product);
        radioCash = findViewById(R.id.radio_cash);
        radioCash.setChecked(true);
        productDatabase = new ProductDatabase(this);
        imgProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkPermission()) {
                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, PICK_IMAGE_REQ_CODE);

                } else {
                    requestPermission();

                }
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imgUri == null) {
                    txtPickPhoto.setVisibility(View.VISIBLE);

                } else {
                    txtPickPhoto.setVisibility(View.GONE);
                    if (editName.getText().toString().equals("") || editPrice.getText().toString().equals("") || editDescription.getText().toString().equals("")) {
                        txtFillField.setVisibility(View.VISIBLE);
                        Log.d("added", "Filed empty");
                    } else {
                        txtFillField.setVisibility(View.GONE);
                        Log.d("added", "Filed not empty");
                        int checked;
                        if (radioCash.isChecked()) {
                            checked = 1;
                        } else {
                            checked = 0;
                        }
                        Product product = new Product(editName.getText().toString(), editDescription.getText().toString()
                                , editPrice.getText().toString(), checked + "", imgUri.toString());
                        boolean insert = productDatabase.insertProduct(product);
                        Log.d("added", insert + "");
                        if (insert) {
                            Toast.makeText(AddItemActivity.this, "Added Successfully", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(AddItemActivity.this, "Added failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });
    }
        private boolean checkPermission() {
            int result = ContextCompat.checkSelfPermission(getApplicationContext(), READ_EXTERNAL_STORAGE);

            return result == PackageManager.PERMISSION_GRANTED;
        }

        private void requestPermission() {

            ActivityCompat.requestPermissions(this, new String[]{READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);

        }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==PICK_IMAGE_REQ_CODE&&resultCode==RESULT_OK){
            if(data!=null){
                imgUri=data.getData();
                imgProduct.setImageURI(imgUri);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0) {

                    boolean readExternalAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;

                    if (readExternalAccepted )
                        Toast.makeText(this, "Permission Granted, Now you can access external storage", Toast.LENGTH_SHORT).show();
                    else {

                        Toast.makeText(this, "Permission Denied, You cannot access external storage", Toast.LENGTH_SHORT).show();
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            if (shouldShowRequestPermissionRationale(READ_EXTERNAL_STORAGE)) {
                                showMessageOKCancel("You need to allow access  the permissions",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                    requestPermissions(new String[]{READ_EXTERNAL_STORAGE},
                                                            PERMISSION_REQUEST_CODE);
                                                }
                                            }
                                        });
                                return;
                            }
                        }

                    }
                }


                break;
        }
    }
    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(AddItemActivity.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

}