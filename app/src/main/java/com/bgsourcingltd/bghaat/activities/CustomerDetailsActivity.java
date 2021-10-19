package com.bgsourcingltd.bghaat.activities;

import androidx.annotation.Nullable;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.aamarpay.library.AamarPay;
import com.aamarpay.library.DialogBuilder;
import com.bgsourcingltd.bghaat.MainActivity;
import com.bgsourcingltd.bghaat.R;
import com.bgsourcingltd.bghaat.helper.ManagementCart;
import com.bgsourcingltd.bghaat.models.NewArrivalModel;
import com.bgsourcingltd.bghaat.models.OrderResponse;
import com.bgsourcingltd.bghaat.network.ApiClient;
import com.bgsourcingltd.bghaat.network.ApiService;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;


import org.json.JSONObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CustomerDetailsActivity extends AppCompatActivity {
    private EditText nameEt,phoneEt,locationEt;
    private MaterialButton submitBtn;
    private List<NewArrivalModel> orderList;
    private ApiService apiService;
    private String json;
    private ProgressDialog progressDialog;
    RadioButton cashRb,onlineRb;
    private AamarPay aamarPay;
    private static final String STORE_ID = "bghaat";
    private static final String SIGNATURE_KEY = "707a3642a524f2c753d02f651c98c742";
    private AlertDialog alertDialog;
    private DialogBuilder dialogBuilder;
    private double totalPrice;
    private AutoCompleteTextView autoCompleteTextView;
    ArrayAdapter<String> adapterItem;
    private String [] item =
            {"Brahmanbaria","Bogra","Bandarban","Borguna","Barisal","Bagerhat","Bhola","Chandpur",
                    "Chittagong", "Chuadanga", "Comilla", "Cox's Bazar", "Dhaka", "Dinajpur",
                    "Faridpur", "Feni","Gaibandha","Gazipur","Gopalganj","Hobiganj","Jaipurhat",
                    "Jamalpur","Jessore","Jhalakathi","Jhinaidah","Khagrachori","Khulna","Kishoreganj",
                    "Kurigram","Kushtia","Lakshmipur","Lalmonirhat","Madaripur","Magura","Manikganj",
                    "Meherpur","Moulvibazar","Munshiganj","Mymensingh","Naogaon","Narayangonj","Narsingonj",
                    "Natore","Nawabgon","Netrokona","Nilphamari","Noakhali","Norail","Pabna","Panchagarh",
                    "Rajshahi","Patukhali","Pirojpur","Rajbari","Rangamati","Rongpur","Satkhira","Shariyatpur",
                    "Sherpur","Sirajgonj","Sunamganj","Sylhet","Tangail","Thakurgaon"

            };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_details);


        dialogBuilder = new DialogBuilder(this,alertDialog);
        nameEt = findViewById(R.id.et_name);
        phoneEt = findViewById(R.id.et_phone);
        locationEt = findViewById(R.id.et_location);
        submitBtn = findViewById(R.id.btn_submit);
        progressDialog = new ProgressDialog(this);
        cashRb = findViewById(R.id.rg_cash_on);
        onlineRb = findViewById(R.id.rg_online_payment);
        autoCompleteTextView = findViewById(R.id.auto_complete);

        aamarPay = new AamarPay(this,STORE_ID,SIGNATURE_KEY);
        aamarPay.testMode(false);
        aamarPay.autoGenerateTransactionID(true);



        //json = new Gson().toJson(managementCart.getListCard());

        /*orderList = (List<NewArrivalModel>) getIntent().getSerializableExtra("order");

        if (orderList != null){


            Toast.makeText(CustomerDetailsActivity.this, ""+json, Toast.LENGTH_LONG).show();
        }*/


        json = getIntent().getStringExtra("cart");
        totalPrice = getIntent().getDoubleExtra("totalAmount",0);
        Toast.makeText(this, ""+totalPrice, Toast.LENGTH_SHORT).show();
        Log.d("catchjson", ""+json);
        apiService = ApiClient.getRetrofit().create(ApiService.class);

        RadioGroup radioGroup = findViewById(R.id.rg);
        locationEt.setText("");
        locationEt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    locationEt.setHint("Ex:Road 7,House-47,Nikunja 1");
                }else{
                    locationEt.setHint("Shipping Address");
                }
            }
        });

        adapterItem = new ArrayAdapter<String>(this,R.layout.list_item,item);
        autoCompleteTextView.setAdapter(adapterItem);

        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                String amountDhaka = "60";
                String amountOutSideDhaka = "130";
                if (item.equals("Dhaka")){
                    showDeliverDialog(amountDhaka);
                }
                else {
                    showDeliverDialog(amountOutSideDhaka);
                }


            }
        });


        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (cashRb.isChecked()){

                String name = nameEt.getText().toString();
                String phone = phoneEt.getText().toString();
                String address = locationEt.getText().toString();

                Log.d("list", "onCreate: " + json);


                Call<OrderResponse> orderResponseCall = apiService.postOrder(name, phone, address, json,"nazmul@gmail.com","Dhaka","Bangladesh","cash on delivery","300");

                progressDialog.show();
                progressDialog.setContentView(R.layout.show_dialog_layout);
                progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                orderResponseCall.enqueue(new Callback<OrderResponse>() {
                    @Override
                    public void onResponse(Call<OrderResponse> call, Response<OrderResponse> response) {
                        OrderResponse orderResponse = response.body();
                        Toast.makeText(CustomerDetailsActivity.this, "" + orderResponse.getMessage(), Toast.LENGTH_SHORT).show();

                        progressDialog.dismiss();
                    }

                    @Override
                    public void onFailure(Call<OrderResponse> call, Throwable t) {
                        Log.e("order", "onFailure " + t.getLocalizedMessage());

                    }
                });

            }
                else if (onlineRb.isChecked()){
                    dialogBuilder.showLoading();
                    aamarPay.setTransactionParameter(String.valueOf(totalPrice),"BDT","BGHaat Shopping");
                    aamarPay.setCustomerDetails("Nazmul","nazmulhoque247@gmail.com","01834462681","51/A/11","Dhaka","Bangladesh");
                    aamarPay.initPGW(new AamarPay.onInitListener() {
                        @Override
                        public void onInitFailure(Boolean error, String message) {
                            Log.d("TEST_IF", message);
                            dialogBuilder.dismissDialog();
                            dialogBuilder.errorPopUp(message);
                        }
                        @Override
                        public void onPaymentSuccess(JSONObject jsonObject) {
                            Log.d("TEST_PS", jsonObject.toString());
                            dialogBuilder.dismissDialog();
                            //if payment is successful send data with server

                        }

                        @Override
                        public void onPaymentFailure(JSONObject jsonObject) {
                            Log.d("TEST_PF", jsonObject.toString());
                            dialogBuilder.dismissDialog();

                        }

                        @Override
                        public void onPaymentProcessingFailed(JSONObject jsonObject) {
                            Log.d("TEST_PPF", jsonObject.toString());
                            dialogBuilder.dismissDialog();

                        }

                        @Override
                        public void onPaymentCancel(JSONObject jsonObject) {

                        }
                    });
                }
                else if (radioGroup.getCheckedRadioButtonId() == -1){
                    Toast.makeText(CustomerDetailsActivity.this, "Select Payment Method", Toast.LENGTH_SHORT).show();
                }
            }

        });

    }

    private void showDeliverDialog(String item) {


        AlertDialog.Builder builder = new AlertDialog.Builder(CustomerDetailsActivity.this,R.style.AlertDialogTheme);
        View view = LayoutInflater.from(this).inflate(R.layout.layout_success_dialog,(ConstraintLayout) findViewById(R.id.layout_dialog_container));
        builder.setView(view);
        ((TextView) view.findViewById(R.id.textTitle)).setText(getResources().getString(R.string.success));
        ((TextView) view.findViewById(R.id.textMessage)).setText("Delivery Charge will be applicable " + item + " tk");
        ((Button) view.findViewById(R.id.btnAction)).setText(getResources().getString(R.string.okey));
        ((ImageView) view.findViewById(R.id.img_icon)).setImageResource(R.drawable.shipping);

        AlertDialog alertDialog = builder.create();
        view.findViewById(R.id.btnAction).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        alertDialog.show();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        CustomerDetailsActivity.this.finish();
    }
}