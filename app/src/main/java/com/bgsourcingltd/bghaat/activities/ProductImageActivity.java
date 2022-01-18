package com.bgsourcingltd.bghaat.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.ImageView;

import com.bgsourcingltd.bghaat.R;
import com.bumptech.glide.Glide;

public class ProductImageActivity extends AppCompatActivity {
    private ImageView productDetailsIv,closeIv;
    private ScaleGestureDetector scaleGestureDetector;
    private float Factor = 1.0f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_image);

        productDetailsIv = findViewById(R.id.iv_product_details);
        closeIv = findViewById(R.id.iv_close);


        String imageUrl = getIntent().getStringExtra("image");
        Glide.with(this).load(imageUrl).into(productDetailsIv);


        scaleGestureDetector = new ScaleGestureDetector(this,new ScaleListener());

        closeIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProductImageActivity.this.finish();
            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        scaleGestureDetector.onTouchEvent(event);
        return super.onTouchEvent(event);

    }

    public class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener{

        @Override
        public boolean onScale(ScaleGestureDetector detector) {

            Factor *= detector.getScaleFactor();
            Factor = Math.max(0.1f,Math.min(Factor,10.f));
            productDetailsIv.setScaleX(Factor);
            productDetailsIv.setScaleY(Factor);
            return true;
        }
    }
}