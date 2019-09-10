package com.example.angela.assap;

import android.Manifest;
import android.content.ContentValues;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Button;
import java.util.*;
import android.view.LayoutInflater;
import android.text.TextUtils;
import android.content.Context;

public class CreateNew extends AppCompatActivity {

    private static final int PERMISSION_CODE = 1000;
    private static final int IMAGE_CAPTURE_CODE = 1001;

    ImageButton cameraButton;
    ImageView photo1;
    Uri image_uri;


    Database db;
    Button add_data;
    EditText editText;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.createnew);


        photo1 = findViewById(R.id.photo1);
        cameraButton = findViewById(R.id.cameraButton);

        cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkSelfPermission(Manifest.permission.CAMERA) ==
                            PackageManager.PERMISSION_DENIED ||
                            checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
                                    PackageManager.PERMISSION_DENIED) {
                        String[] permission = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
                        requestPermissions(permission, PERMISSION_CODE);
                    } else {
                        openCamera();
                    }
                } else {

                }
            }

            private void openCamera() {
                ContentValues values = new ContentValues();
                values.put(MediaStore.Images.Media.TITLE, "New Picture");
                values.put(MediaStore.Images.Media.DESCRIPTION, "From the Camera");
                image_uri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, image_uri);
                startActivityForResult(cameraIntent, IMAGE_CAPTURE_CODE);
            }

            public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
                switch (requestCode) {
                    case PERMISSION_CODE: {
                        if (grantResults.length > 0 && grantResults[0] ==
                                PackageManager.PERMISSION_GRANTED) {
                            openCamera();
                        }
                    }
                }
            }

            protected void onActivityResult(int requestCode, int resultCode, Intent data) {
                if (resultCode == RESULT_OK) {
                    photo1.setImageURI(image_uri);
                }
            }

        });


        add_data = findViewById(R.id.button3);
        editText = findViewById(R.id.editText5);
        db = new Database(this);



        add_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newEntry = editText.getText().toString();

                if(editText.length() != 0){

                    AddData(newEntry);
                    editText.setText("");
                    Intent createNewActivity = new Intent(CreateNew.this, MainReports.class);
                    startActivity(createNewActivity);
                }else{

                    Toast.makeText(CreateNew.this,"Add something",Toast.LENGTH_LONG).show();

                }
            }
        });
    }

    public void AddData(String newEntry){
        boolean insertData = db.addData(newEntry);

        if(insertData==true){
            Toast.makeText(CreateNew.this,"Data added",Toast.LENGTH_LONG).show();
        }else{

            Toast.makeText(CreateNew.this,"Data not added!!!",Toast.LENGTH_LONG).show();
        }
    }
}













