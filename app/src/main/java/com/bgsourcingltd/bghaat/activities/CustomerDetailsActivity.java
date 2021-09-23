package com.bgsourcingltd.bghaat.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bgsourcingltd.bghaat.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;

public class CustomerDetailsActivity extends AppCompatActivity {
    private EditText nameEt,phoneEt,locationEt;
    private MaterialButton submitBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_details);

        nameEt = findViewById(R.id.et_name);
        phoneEt = findViewById(R.id.et_phone);
        locationEt = findViewById(R.id.et_phone);
        submitBtn = findViewById(R.id.btn_submit);


        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 String name = nameEt.getText().toString();
                 String phone = phoneEt.getText().toString();
                 String location = locationEt.getText().toString();

                Toast.makeText(CustomerDetailsActivity.this, ""+name + phone + location, Toast.LENGTH_SHORT).show();
            }
        });
    }
}