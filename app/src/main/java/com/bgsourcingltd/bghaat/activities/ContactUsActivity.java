package com.bgsourcingltd.bghaat.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bgsourcingltd.bghaat.R;

import java.util.Objects;

import es.dmoral.toasty.Toasty;

public class ContactUsActivity extends AppCompatActivity {

    private Toolbar toolbarContact;
    private ImageView whatsAppIv,facebookIv,instagramIv,youTubeIv,twitter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);

        toolbarContact = findViewById(R.id.toolbar_contact_us);
        whatsAppIv = findViewById(R.id.iv_whatsapp);
        facebookIv = findViewById(R.id.iv_facebook);
        instagramIv = findViewById(R.id.iv_instagram);
        youTubeIv = findViewById(R.id.iv_youtube);
        twitter = findViewById(R.id.iv_twitter);

        toolbarContact.setTitle("Contact Us");
        setSupportActionBar(toolbarContact);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);


        whatsAppIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String number = "+8801787663280";
                Uri uri = Uri.parse(String.format("https://api.whatsapp.com/send?phone=%s",number));
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(uri);
                startActivity(intent);
            }
        });

        facebookIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String sWebLink = "https://www.facebook.com/bghaat";

                    Uri uri = Uri.parse(sWebLink);
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(uri);

                    startActivity(intent);

            }
        });

        instagramIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toasty.info(ContactUsActivity.this,"Coming soon", Toast.LENGTH_SHORT,true).show();
            }
        });

        youTubeIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toasty.info(ContactUsActivity.this,"Coming Soon",Toast.LENGTH_SHORT,true).show();
            }
        });

        twitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toasty.info(ContactUsActivity.this,"Coming Soon",Toast.LENGTH_SHORT,true).show();
            }
        });


    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;

    }
}