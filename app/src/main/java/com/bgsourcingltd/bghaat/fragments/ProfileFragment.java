package com.bgsourcingltd.bghaat.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bgsourcingltd.bghaat.R;
import com.bgsourcingltd.bghaat.activities.OrderTrackingActivity;


public class ProfileFragment extends Fragment {
    private LinearLayout layout;
    private Context context;


    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        layout = view.findViewById(R.id.layout_order);

        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(context);
                View view = getLayoutInflater().inflate(R.layout.custom_dialog,null);
                EditText orderNoEt = view.findViewById(R.id.et_order_no);
                Button cencelBt = view.findViewById(R.id.btn_cencel);
                Button okBtn = view.findViewById(R.id.bt_ok);

                alert.setView(view);

                AlertDialog alertDialog = alert.create();
                alertDialog.setCanceledOnTouchOutside(true);

                cencelBt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        alertDialog.dismiss();

                    }
                });

                okBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String inputOrderValue = orderNoEt.getText().toString();
                        alertDialog.dismiss();

                    }
                });
                alertDialog.show();
                //startActivity(new Intent(context, OrderTrackingActivity.class));
            }
        });
    }
}