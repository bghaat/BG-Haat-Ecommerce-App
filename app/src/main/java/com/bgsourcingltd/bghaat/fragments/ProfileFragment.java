package com.bgsourcingltd.bghaat.fragments;

import android.app.AlertDialog;
import android.app.ProgressDialog;
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
import android.widget.TextView;
import android.widget.Toast;

import com.bgsourcingltd.bghaat.R;
import com.bgsourcingltd.bghaat.activities.CartListActivity;
import com.bgsourcingltd.bghaat.activities.OrderTrackingActivity;
import com.bgsourcingltd.bghaat.activities.UpdateProfileActivity;
import com.bgsourcingltd.bghaat.models.RecordOrderModel;
import com.bgsourcingltd.bghaat.models.UserModel;
import com.bgsourcingltd.bghaat.network.ApiClient;
import com.bgsourcingltd.bghaat.network.ApiService;
import com.bgsourcingltd.bghaat.userauth.UserPhoneAuth;

import java.util.List;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ProfileFragment extends Fragment {
    private LinearLayout layout;
    private Context context;
    private ApiService apiService;
    private Button updateProfileBtn;
    private UserPhoneAuth userPhoneAuth;
    private ProgressDialog progressDialog;
    private TextView userNameTv,userEmailTv,userAddressTv, userPhoneTv;


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
        updateProfileBtn = view.findViewById(R.id.btn_edite_profile);
        apiService = ApiClient.getRetrofit().create(ApiService.class);
        userPhoneAuth = new UserPhoneAuth(context);
        progressDialog = new ProgressDialog(context);
        userNameTv = view.findViewById(R.id.tv_user_name);
        userEmailTv = view.findViewById(R.id.tv_user_email);
        userAddressTv = view.findViewById(R.id.tv_address_value);
        userPhoneTv = view.findViewById(R.id.tv_mobile_no);

        Call<List<UserModel>> listCall = apiService.getCurrentUser(userPhoneAuth.getPhoneNumber());

        progressDialog.show();
        progressDialog.setContentView(R.layout.show_dialog_layout);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        listCall.enqueue(new Callback<List<UserModel>>() {
            @Override
            public void onResponse(Call<List<UserModel>> call, Response<List<UserModel>> response) {
                List<UserModel> userModelList = response.body();
                if (userModelList.isEmpty()){
                    progressDialog.dismiss();
                    Toasty.info(context,"Update Profile", Toast.LENGTH_LONG, true).show();
                }
                userNameTv.setText(userModelList.get(0).getName());
                userEmailTv.setText(userModelList.get(0).getEmail());
                userAddressTv.setText(userModelList.get(0).getAddress());
                userPhoneTv.setText(userModelList.get(0).getPhone());

                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<List<UserModel>> call, Throwable t) {

            }
        });

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

                        Call<List<RecordOrderModel>> listCall = apiService.getOrderRecord(inputOrderValue);
                        listCall.enqueue(new Callback<List<RecordOrderModel>>() {
                            @Override
                            public void onResponse(Call<List<RecordOrderModel>> call, Response<List<RecordOrderModel>> response) {
                                List<RecordOrderModel> recordList = response.body();
                                Intent intent = new Intent(context,OrderTrackingActivity.class);
                                intent.putExtra("flag",recordList.get(0).getPost_status());
                                startActivity(intent);
                            }

                            @Override
                            public void onFailure(Call<List<RecordOrderModel>> call, Throwable t) {

                            }
                        });
                        alertDialog.dismiss();

                    }
                });
                alertDialog.show();
                //startActivity(new Intent(context, OrderTrackingActivity.class));
            }
        });


        updateProfileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                context.startActivity(new Intent(context, UpdateProfileActivity.class));
            }
        });

    }
}