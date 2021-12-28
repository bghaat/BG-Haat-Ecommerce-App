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
import com.bgsourcingltd.bghaat.userauth.UserPhoneAuth;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;



import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CustomerDetailsActivity extends AppCompatActivity {
    private EditText nameEt,phoneEt,locationEt,emailEt;
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
    private double totalPrice,deliveryAndTotalPrice;
    private UserPhoneAuth phoneAuth;

    private String name,phone,address,email,city;

    private AutoCompleteTextView autoCompleteTextView,areaAutoComplete;
    ArrayAdapter<String> adapterItem,areaAdapterItem;
    private String paymentStatus = "";
    private String [] item =
            {"Brahmanbaria","Bogra","Bandarban","Borguna","Barisal","Bagerhat","Bhola","Chandpur",
                    "Chittagong", "Chuadanga", "Comilla", "Cox's Bazar", "Dhaka", "Dinajpur",
                    "Faridpur", "Feni","Gaibandha","Gazipur","Gopalganj","Hobiganj","Jaipurhat",
                    "Jamalpur","Jessore","Jhalakathi","Jhinaidah","Khagrachori","Khulna","Kishoreganj",
                    "Kurigram","Kushtia","Lakshmipur","Lalmonirhat","Madaripur","Magura","Manikganj",
                    "Meherpur","Moulvibazar","Munshiganj","Mymensingh","Naogaon","Narayangonj","Narshingdi",
                    "Natore","Nawabgon","Netrokona","Nilphamari","Noakhali","Norail","Pabna","Panchagarh",
                    "Rajshahi","Patukhali","Pirojpur","Rajbari","Rangamati","Rongpur","Satkhira","Shariyatpur",
                    "Sherpur","Sirajgonj","Sunamganj","Sylhet","Tangail","Thakurgaon"};

    private List<String> area = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_details);


        dialogBuilder = new DialogBuilder(this,alertDialog);
        nameEt = findViewById(R.id.et_name);
        phoneEt = findViewById(R.id.et_phone);
        locationEt = findViewById(R.id.et_location);
        emailEt = findViewById(R.id.et_email);
        submitBtn = findViewById(R.id.btn_submit);
        progressDialog = new ProgressDialog(this);
        cashRb = findViewById(R.id.rg_cash_on);
        onlineRb = findViewById(R.id.rg_online_payment);
        autoCompleteTextView = findViewById(R.id.auto_complete);
        areaAutoComplete = findViewById(R.id.auto_complete_area);
        RadioGroup radioGroup = findViewById(R.id.rg);
        phoneAuth = new UserPhoneAuth(this);
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
        Log.d("catchjson", ""+json);
        apiService = ApiClient.getRetrofit().create(ApiService.class);


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
        phoneEt.setText("0"+phoneAuth.getPhoneNumber());

        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                city = parent.getItemAtPosition(position).toString();
                String amountDhaka = "60";
                String amountOutSideDhaka = "130";


                 if (city.equals("Feni")){

                    area.clear();

                    area.add("Chhagalniya");
                    area.add("Daganbhuiyan");
                    area.add("Feni Sadar");
                    area.add("Fulgazi");
                    area.add("Porshuram");
                    area.add("Sonagazi");

                    fillArea();
                    showDeliverDialog(amountOutSideDhaka);
                }
                else if (city.equals("Borguna")){
                    area.clear();

                    area.add("Amtali");
                    area.add("Bamna");
                    area.add("Barguna Sadar");
                    area.add("Betagi");
                    area.add("Patharghata");
                    area.add("Taltali");

                    fillArea();
                    showDeliverDialog(amountOutSideDhaka);
                }
                else if (city.equals("Barisal")){

                    area.clear();

                    area.add("Agailjhara");
                    area.add("Babuganj");
                    area.add("Bakerganj");
                    area.add("Banaripara");
                    area.add("Barishal Sadar");
                    area.add("Gouranadi");
                    area.add("Hizla");
                    area.add("Mehendiganj");
                    area.add("Muladi");
                    area.add("Uzirpur");

                    fillArea();
                    showDeliverDialog(amountOutSideDhaka);
                }
                else if (city.equals("Bhola")){
                    area.clear();

                    area.add("Bhola Sadar");
                    area.add("Borhanuddin");
                    area.add("Charfassion");
                    area.add("Daulatkhan");
                    area.add("Lalmohan");
                    area.add("Monpura");
                    area.add("Tazumuddin");

                    fillArea();
                    showDeliverDialog(amountOutSideDhaka);
                }
                else if (city.equals("Jhalakathi")){

                    area.clear();

                    area.add("Jhalokathi Sadar");
                    area.add("Kathalia");
                    area.add("Nalchity");
                    area.add("Rajapur");

                    fillArea();
                    showDeliverDialog(amountOutSideDhaka);
                }
                else if (city.equals("Patuakhali")){
                    area.clear();

                    area.add("Bauphal");
                    area.add("Dashmina");
                    area.add("Dumki");
                    area.add("Galachipa");
                    area.add("Kalapara");
                    area.add("Mirjaganj");
                    area.add("Patuakhali Sadar");
                    area.add("Rangabali");

                    fillArea();
                    showDeliverDialog(amountOutSideDhaka);
                }

                else if (city.equals("Pirojpur")){

                    area.clear();

                    area.add("Bhandaria");
                    area.add("Kawkhali");
                    area.add("Mothbaria");
                    area.add("Nazirpur");
                    area.add("Nesarabad");
                    area.add("Pirojpur Sadar");
                    area.add("Zianagar");

                    fillArea();
                    showDeliverDialog(amountOutSideDhaka);

                }
                else if (city.equals("Brahmanbaria")){

                    area.clear();

                    area.add("Akhaura");
                    area.add("Ashuganj");
                    area.add("Brahmanbaria Sadar");
                    area.add("Bancharampur");
                    area.add("Bijoynagar");
                    area.add("Kasba");
                    area.add("Nabinagar");
                    area.add("Nasirnagar");
                    area.add("Sarail");

                    fillArea();
                    showDeliverDialog(amountOutSideDhaka);

                }

                else if (city.equals("Bandarban")){
                    area.clear();

                    area.add("Alikadam");
                    area.add("Bandarban Sadar");
                    area.add("Lama");
                    area.add("Rowangchari");
                    area.add("Ruma");
                    area.add("Thanchi");

                    fillArea();
                    showDeliverDialog(amountOutSideDhaka);
                }

                else if (city.equals("Chandpur")){
                    area.clear();

                    area.add("Chandpur Sadar");
                    area.add("Faridganj");
                    area.add("Haimchar");
                    area.add("Haziganj");
                    area.add("Kachua");
                    area.add("Matlab (Dakshin)");
                    area.add("Matlab (Uttar)");
                    area.add("Shahrasti");

                    fillArea();
                    showDeliverDialog(amountOutSideDhaka);
                }

                else if (city.equals("Chittagong")){
                    area.clear();

                    area.add("Anwara");
                    area.add("Banskhali");
                    area.add("Boalkhali");
                    area.add("Chandanish");
                    area.add("Fatikchari");
                    area.add("Hathazari");
                    area.add("Karnaphuli");
                    area.add("Lohagara");
                    area.add("Mirsharai");
                    area.add("Patiya");
                    area.add("Rangunia");
                    area.add("Raojan");
                    area.add("Sandwip");
                    area.add("Satkania");
                    area.add("Sitakunda");

                    fillArea();
                    showDeliverDialog(amountOutSideDhaka);
                }

                else if (city.equals("Cox's Bazar")){
                    area.clear();

                    area.add("Chakoria");
                    area.add("Cox's Bazar Sadar");
                    area.add("Kutubdia");
                    area.add("Moheskhali");
                    area.add("Pekua");
                    area.add("Ramu");
                    area.add("Teknaf");
                    area.add("Ukhiya");

                    fillArea();
                    showDeliverDialog(amountOutSideDhaka);

                }
                else if (city.equals("Comilla")){
                    area.clear();

                    area.add("Barura");
                    area.add("Brahmanpara");
                    area.add("Burichong");
                    area.add("Chandina");
                    area.add("Chouddagram");
                    area.add("Comilla Sadar");
                    area.add("Comilla-S Daksin");
                    area.add("Daudkandi");
                    area.add("Debidwar");
                    area.add("Homna");
                    area.add("Laksham");
                    area.add("Lalmai");
                    area.add("Meghna");
                    area.add("Monohorganj");
                    area.add("Muradnagar");
                    area.add("Nangalkot");
                    area.add("Titas");

                    fillArea();
                    showDeliverDialog(amountOutSideDhaka);
                }

                else if (city.equals("Khagrachori")){
                    area.clear();

                    area.add("Dighinala");
                    area.add("Guimara");
                    area.add("Khagrachorib Sadar");
                    area.add("Guimara");
                    area.add("Laxmichari");
                    area.add("Mahalchari");
                    area.add("Matiranga");
                    area.add("Panchari");
                    area.add("Ramgarh");

                    fillArea();
                    showDeliverDialog(amountOutSideDhaka);
                }

                else if (city.equals("Laxmipur")){
                    area.clear();

                    area.add("Komol Nagar");
                    area.add("Laxmipur Sadar");
                    area.add("Raipur");
                    area.add("Ramganj");
                    area.add("Ramgati");

                    fillArea();
                    showDeliverDialog(amountOutSideDhaka);

                }

                else if (city.equals("Noakhali")){
                    area.clear();

                    area.add("Begumganj");
                    area.add("Chatkhil");
                    area.add("Companiganj");
                    area.add("Hatiya");
                    area.add("Kabir Hat");
                    area.add("Noakhali Sadar");
                    area.add("Senbag");
                    area.add("Sonaimuri");
                    area.add("Subarna Char");

                    fillArea();
                    showDeliverDialog(amountOutSideDhaka);
                }

                else if (city.equals("Rangamati")){
                    area.clear();

                    area.add("Baghaichari");
                    area.add("Barkal");
                    area.add("Belaichari");
                    area.add("Juraichari");
                    area.add("Kaptai");
                    area.add("Kaukhali");
                    area.add("Langadu");
                    area.add("Nanniarchar");
                    area.add("Rajosthali");

                    fillArea();
                    showDeliverDialog(amountOutSideDhaka);
                }

                else if (city.equals("Faridpur")){
                    area.clear();

                    area.add("Alfadanga");
                    area.add("Bhanga");
                    area.add("Boalmari");
                    area.add("Charbhadrasan");
                    area.add("Faridpur Sadar");
                    area.add("Madhukhali");
                    area.add("Nagarkanda");
                    area.add("Sadarpur");
                    area.add("Saltha");

                    fillArea();
                    showDeliverDialog(amountOutSideDhaka);
                }

                else if (city.equals("Gazipur")){
                    area.clear();

                    area.add("Gazipur Sadar");
                    area.add("Kaliakoir");
                    area.add("Kaliganj");
                    area.add("Kapasia");
                    area.add("Sreepur");

                    fillArea();
                    showDeliverDialog(amountOutSideDhaka);
                }

                else if (city.equals("Gopalganj")){
                    area.clear();

                    area.add("Gopalganj Sadar");
                    area.add("Kasiani");
                    area.add("Kotwalipara");
                    area.add("Muksudpur");
                    area.add("Tungipara");

                    fillArea();
                    showDeliverDialog(amountOutSideDhaka);
                }

                else if (city.equals("Kishoreganj")){
                    area.clear();

                    area.add("Kishoreganj Sadar");
                    area.add("Austagram");
                    area.add("Bajitpur");
                    area.add("Bhairab");
                    area.add("Hossainpur");
                    area.add("Itna");
                    area.add("Karimganj");
                    area.add("Katiadi");
                    area.add("Kuliarchar");
                    area.add("Mithamoin");
                    area.add("Nikli");
                    area.add("Pakundia");
                    area.add("Tarail");

                    fillArea();
                    showDeliverDialog(amountOutSideDhaka);
                }

                else if (city.equals("Madaripur")){

                    area.clear();

                    area.add("Kalkini");
                    area.add("Madaripur Sadar");
                    area.add("Rajoir");
                    area.add("Shibchar");

                    fillArea();
                    showDeliverDialog(amountOutSideDhaka);

                }

                else if (city.equals("Manikganj")){
                    area.clear();

                    area.add("Daulatpur");
                    area.add("Ghior");
                    area.add("Harirampur");
                    area.add("Manikganj Sadar");
                    area.add("Saturia");
                    area.add("Shivalaya");
                    area.add("Singair");

                    fillArea();
                    showDeliverDialog(amountOutSideDhaka);
                }
                else if (city.equals("Dhaka")){
                    area.clear();

                    area.add("Adabor");
                    area.add("Agargaon");
                    area.add("Azimpur");
                    area.add("Badda");
                    area.add("Bakshi Bazar");
                    area.add("Banani");
                    area.add("Banani DOHS");
                    area.add("Banasree");
                    area.add("Bangla Bazar");
                    area.add("Bangla Motor");
                    area.add("Baridhara");
                    area.add("Baridhara DOHS");
                    area.add("Basabo");
                    area.add("Basundhara");
                    area.add("Bimanbondor Thana");
                    area.add("Bongshal");
                    area.add("Buet Campus");
                    area.add("Cantonment");
                    area.add("Chowk Bazar");
                    area.add("Dakshin Khan");
                    area.add("Demra");
                    area.add("Dhamrai");
                    area.add("Dhanmondi");
                    area.add("Dohar");
                    area.add("Doyagonj");
                    area.add("DU Campus");
                    area.add("Elephant Road");
                    area.add("Eskaton");
                    area.add("Farmgate");
                    area.add("Gabtoli");
                    area.add("Gendaria");
                    area.add("Green Road");
                    area.add("Gulistan");
                    area.add("Gulsan 1");
                    area.add("Gulsan 2");
                    area.add("Hatirpool");
                    area.add("Hazaribag");
                    area.add("Hemayetpur");
                    area.add("Ibrahimpur");
                    area.add("Islampur");
                    area.add("Islampur");
                    area.add("Jatrabari");
                    area.add("Jurain");
                    area.add("Kaprul");
                    area.add("Kakrail");
                    area.add("Kalabagan");
                    area.add("Kallyanpur");
                    area.add("Kamrangirchar");
                    area.add("Kathalbagan");
                    area.add("Kawran  Bazar");
                    area.add("Kazipara");
                    area.add("Keraniganj");
                    area.add("Khilgaon");
                    area.add("Khilkhet");
                    area.add("Kodomtoli");
                    area.add("Kotoyali");
                    area.add("Kotwali");
                    area.add("Lalbagh");
                    area.add("Lalmatia");
                    area.add("Maghbazar");
                    area.add("Malibagh");
                    area.add("Mirpur");
                    area.add("Mirpur 1");
                    area.add("Mirpur 10");
                    area.add("Mirpur 11");
                    area.add("Mirpur 12");
                    area.add("Mirpur 2");
                    area.add("Mirpur DOHS");
                    area.add("Moddho Badda");
                    area.add("Moddho Batara");
                    area.add("Mohakhali");
                    area.add("Mohakhali DOHS");
                    area.add("Mohammadpur");
                    area.add("Motijheel");
                    area.add("Mugda");
                    area.add("Nakhalpara");
                    area.add("Narinda");
                    area.add("Nawabganj");
                    area.add("New Eskaton");
                    area.add("New Market");
                    area.add("Niketon");
                    area.add("Nekunja");
                    area.add("Nilkhet");
                    area.add("Noya Palton");
                    area.add("Palashi");
                    area.add("Pallabi");
                    area.add("Panthapath");
                    area.add("Postogola");
                    area.add("Purana Paltan");
                    area.add("Purbachal");
                    area.add("Raja Bazar");
                    area.add("Rajarbag");
                    area.add("Ramna");
                    area.add("Rampura");
                    area.add("Rayer bazar");
                    area.add("Rupnagar");
                    area.add("Sabujbag");
                    area.add("Sadarghat");
                    area.add("Savar");
                    area.add("Segun Bagicha");
                    area.add("Shahbag");
                    area.add("Shahjadpur");
                    area.add("Shahjahanpur");
                    area.add("Shampur");
                    area.add("Sher-e-bangla Nagar");
                    area.add("Shimrail");
                    area.add("Sukrabad");
                    area.add("Shyamoli");
                    area.add("Shddheshwari");
                    area.add("Shutrapur");
                    area.add("Tejgaon");
                    area.add("Tejkunipara");
                    area.add("Tikatuli");
                    area.add("Turag");
                    area.add("Uttar Khan");
                    area.add("Uttara");
                    area.add("Wari");
                    area.add("Zigatola");

                    fillArea();
                    showDeliverDialog(amountDhaka);
                }

                else if (city.equals("Munshiganj")){
                    area.clear();

                    area.add("Gazaria");
                    area.add("Lauhajong");
                    area.add("Munshiganj Sadar");
                    area.add("Sirajdikhan");
                    area.add("Sreenagar");
                    area.add("Tongibari");


                    fillArea();
                    showDeliverDialog(amountOutSideDhaka);
                }

                else if (city.equals("Narayangonj")){
                    area.clear();

                    area.add("Araihazar");
                    area.add("Bandar");
                    area.add("Narayangonj Sadar");
                    area.add("Rupganj");
                    area.add("Sonargaon");

                    fillArea();
                    showDeliverDialog(amountOutSideDhaka);
                }
                else if (city.equals("Narshingdi")){
                    area.clear();

                    area.add("Belabo");
                    area.add("Monohardi");
                    area.add("Narshingdi Sadar");
                    area.add("Palash");
                    area.add("Raipura");
                    area.add("Shibpur");
                    area.add("");

                    fillArea();
                    showDeliverDialog(amountOutSideDhaka);

                }

                else if (city.equals("Rajbari")){
                    area.clear();

                    area.add("Baliakandi");
                    area.add("Goalanda");
                    area.add("Kalukhali");
                    area.add("Pangsha");
                    area.add("Rajbari Sadar");

                    fillArea();
                    showDeliverDialog(amountOutSideDhaka);
                }

                else if (city.equals("Shariyatpur")){
                    area.clear();

                    area.add("Bhedarganj");
                    area.add("Damuddya");
                    area.add("Goshairhat");
                    area.add("Janjira");
                    area.add("Naria");
                    area.add("Shariyatpur Sadar");

                    fillArea();
                    showDeliverDialog(amountOutSideDhaka);
                }

                else if (city.equals("Tangail")){
                    area.clear();

                    area.add("Basail");
                    area.add("Bhuapur");
                    area.add("Delduar");
                    area.add("Dhanbari");
                    area.add("Ghatail");
                    area.add("Gopalpur");
                    area.add("Kalihati");
                    area.add("Madhupur");
                    area.add("Mirzapur");
                    area.add("Nagarpur");
                    area.add("Shakhipur");
                    area.add("Tangail Sadar");

                    fillArea();
                    showDeliverDialog(amountOutSideDhaka);
                }

                else if (city.equals("Bagerhat")){

                    area.clear();

                    area.add("Bagerhat");
                    area.add("Chitalmari");
                    area.add("Fakirhat");
                    area.add("Kachua");
                    area.add("Mollahat");
                    area.add("Mongla");
                    area.add("Morrelganj");
                    area.add("Rampal");
                    area.add("Sharankhola");
                    area.add("Alamdanga");

                    fillArea();
                    showDeliverDialog(amountOutSideDhaka);

                }

                else if (city.equals("Chuadanga")){

                    area.clear();

                    area.add("Alamdanga");
                    area.add("Chuadanga Sadar");
                    area.add("Damurhuda");
                    area.add("Jibannagar");
                    area.add("Alamdanga");

                    fillArea();
                    showDeliverDialog(amountOutSideDhaka);
                }

                else if (city.equals("Jessore")){
                    area.clear();

                    area.add("Abhoynagar");
                    area.add("Bagherpara");
                    area.add("Chowgacha");
                    area.add("Jessore Sadar");
                    area.add("Jhikargacha");
                    area.add("Keshabpur");
                    area.add("Sarsha");

                    fillArea();
                    showDeliverDialog(amountOutSideDhaka);
                }

                else if (city.equals("Jhinaidah")){
                    area.clear();

                    area.add("Harinakunda");
                    area.add("Jhinaidah Sadar");
                    area.add("Kaliganj");
                    area.add("Kotchandpur");
                    area.add("Moheshpur");
                    area.add("Shailkupa");


                    fillArea();
                    showDeliverDialog(amountOutSideDhaka);
                }

                else if (city.equals("Khulna")){
                    area.clear();

                    area.add("Batiaghata");
                    area.add("Dacope");
                    area.add("Dighalia");
                    area.add("Dumuria");
                    area.add("Koira");
                    area.add("Paikgacha");
                    area.add("Phultala");
                    area.add("Rupsa");
                    area.add("Terokhada");

                    fillArea();
                    showDeliverDialog(amountOutSideDhaka);
                }

                else if (city.equals("Kushtia")){
                    area.clear();

                    area.add("Bheramara");
                    area.add("Daulatpur");
                    area.add("Khoksha");
                    area.add("Kumarkhali");
                    area.add("Kushtia Sadar");
                    area.add("Mirpur");
                    area.add("Magura Sadar");
                    area.add("Mohammadpur");
                    area.add("Salikha");
                    area.add("Sreepur");

                    fillArea();
                    showDeliverDialog(amountOutSideDhaka);
                }
                else if (city.equals("Magura")){
                    area.clear();

                    area.add("Magura Sadar");
                    area.add("Mohammadpur");
                    area.add("Salikha");
                    area.add("Sreepur");

                    fillArea();
                    showDeliverDialog(amountOutSideDhaka);

                }

                else if (city.equals("Meherpur")){
                    area.clear();

                    area.add("Gangni");
                    area.add("Meherpur Sadar");
                    area.add(" Mujib Nagar");

                    fillArea();
                    showDeliverDialog(amountOutSideDhaka);
                }

                else if (city.equals("Norail")){
                    area.clear();

                    area.add("Kalia");
                    area.add("Lohagara");
                    area.add("Narail Sadar");

                    fillArea();
                    showDeliverDialog(amountOutSideDhaka);
                }

                else if (city.equals("Satkhira")){

                    area.clear();

                    area.add("Assasuni");
                    area.add("Debhata");
                    area.add("Kalaroa");
                    area.add("Kaliganj");
                    area.add("Satkhira Sadar");
                    area.add("Shyamnagar");
                    area.add("Tala");

                    fillArea();
                    showDeliverDialog(amountOutSideDhaka);
                }

                else if (city.equals("Jamalpur")){
                    area.clear();

                    area.add("Bakshiganj");
                    area.add("Dewanganj");
                    area.add("Islampur");
                    area.add("Madarganj");
                    area.add("Jamalpur Sadar");
                    area.add("Melendah");
                    area.add("Sarishabari");

                    fillArea();
                    showDeliverDialog(amountOutSideDhaka);
                }

                else if (city.equals("Mymensingh")){
                    area.clear();

                    area.add("Bhaluka");
                    area.add("Dhobaura");
                    area.add("Fulbaria");
                    area.add("Gaffargaon");
                    area.add("Gouripur");
                    area.add("Haluaghat");
                    area.add("Ishwarganj");
                    area.add("Muktagacha");
                    area.add("Ishwarganj");
                    area.add("Mymensingh Sadar");
                    area.add("Nandail");
                    area.add("Phulpur");
                    area.add("Tarakanda");
                    area.add("Trishal");

                    fillArea();
                    showDeliverDialog(amountOutSideDhaka);
                }

                else if (city.equals("Netrokona")){
                    area.clear();

                    area.add("Atpara");
                    area.add("Barhatta");
                    area.add("Durgapur");
                    area.add("Kalmakanda");
                    area.add("Kendua");
                    area.add("Khaliajuri");
                    area.add("Madan");
                    area.add("Mohanganj");
                    area.add("Netrokona Sadar");
                    area.add("Purbadhala");

                    fillArea();
                    showDeliverDialog(amountOutSideDhaka);
                }

                else if (city.equals("Sherpur")){
                    area.clear();

                    area.add("Jhenaigati");
                    area.add("Nakla");
                    area.add("Nalitabari");
                    area.add("Sherpur Sadar");
                    area.add("Sreebordi");

                    fillArea();
                    showDeliverDialog(amountOutSideDhaka);
                }

                else if (city.equals("Bogra")){
                    area.clear();

                    area.add("Adamdighi");
                    area.add("Bogura Sadar");
                    area.add("Dhunot");
                    area.add("Dhupchancia");
                    area.add("Gabtali");
                    area.add("Kahaloo");
                    area.add("Nandigram");
                    area.add("Sariakandi");
                    area.add("Shajahanpur");
                    area.add("Shibganj");
                    area.add("Sherpur");
                    area.add("Sonatala");

                    fillArea();
                    showDeliverDialog(amountOutSideDhaka);

                }

                else if (city.equals("Jaipurhat")){

                    area.clear();

                    area.add("Akkelpur");
                    area.add("Jaipurhat Sadar");
                    area.add("Kalai");
                    area.add("Khetlal");
                    area.add("Panchbibi");

                    fillArea();
                    showDeliverDialog(amountOutSideDhaka);
                }

                else if (city.equals("Naogaon")){
                    area.clear();

                    area.add("Badalgachi");
                    area.add("Dhamoirhat");
                    area.add("Manda");
                    area.add("Mohadevpur");
                    area.add("Naogaon Sadar");
                    area.add("Niamatpur");
                    area.add("Patnitala");
                    area.add("Porsha");
                    area.add("Raninagar");
                    area.add("Shapahar");

                    fillArea();
                    showDeliverDialog(amountOutSideDhaka);
                }

                else if (city.equals("Natore")){
                    area.clear();

                    area.add("Bagatipara");
                    area.add("Baraigram");
                    area.add("Gurudaspur");
                    area.add("Lalpur");
                    area.add("Naldanga");
                    area.add("Natore Sadar");
                    area.add("Singra");

                    fillArea();
                    showDeliverDialog(amountOutSideDhaka);
                }

                else if (city.equals("Pabna")){
                    area.clear();

                    area.add("Atghoria");
                    area.add("Bera");
                    area.add("Bhangura");
                    area.add("Chatmohar");
                    area.add("Faridpur");
                    area.add("Ishwardi");
                    area.add("Santhia");
                    area.add("Sujanagar");

                    fillArea();
                    showDeliverDialog(amountOutSideDhaka);

                }

                else if (city.equals("Rajshahi")){

                    area.clear();

                    area.add("Bagha");
                    area.add("Bagmara");
                    area.add("Charghat");
                    area.add("Durgapur");
                    area.add("Godagari");
                    area.add("Mohanpur");
                    area.add("Paba");
                    area.add("Puthia");
                    area.add("Tanore");

                    fillArea();
                    showDeliverDialog(amountOutSideDhaka);
                }

                else if (city.equals("Sirajgonj")){

                    area.clear();

                    area.add("Belkuchi");
                    area.add("Chowhali");
                    area.add("Kamarkhand");
                    area.add("Kazipur");
                    area.add("Raiganj");
                    area.add("Shahzadpur");
                    area.add("Sirajgonj Sadar");
                    area.add("Tarash");
                    area.add("Ullapara");

                    fillArea();
                    showDeliverDialog(amountOutSideDhaka);
                }

                else if (city.equals("Dinajpur")){

                    area.clear();

                    area.add("Birampur");
                    area.add("Birganj");
                    area.add("Birol");
                    area.add("Bochaganj");
                    area.add("Chirirbandar");
                    area.add("Dinajpur Sadar");
                    area.add("Fulbari");
                    area.add("Ghoraghat");
                    area.add("Hakimpur");
                    area.add("Kaharol");
                    area.add("Khanshama");
                    area.add("Nawabganj");
                    area.add("Parbatipur");

                    fillArea();
                    showDeliverDialog(amountOutSideDhaka);
                }

                else if (city.equals("Gaibandha")){

                    area.clear();

                    area.add("Gaibandha Sadar");
                    area.add("Fulchari");
                    area.add("Gobindaganj");
                    area.add("Palashbari");
                    area.add("Sadullapur");
                    area.add("Saghata");
                    area.add("Sundarganj");

                    fillArea();
                    showDeliverDialog(amountOutSideDhaka);
                }
                else if (city.equals("Kurigram")){
                    area.clear();

                    area.add("Bhurungamari");
                    area.add("Chilmari");
                    area.add("Fulbari");
                    area.add("Kurigram Sadar");
                    area.add("Nageswari");
                    area.add("Rajarhat");
                    area.add("Rajibpur");
                    area.add("Rowmari");
                    area.add("Ulipur");

                    fillArea();
                    showDeliverDialog(amountOutSideDhaka);

                }

                else if (city.equals("Lalmonirhat")){

                    area.clear();

                    area.add("Aditmari");
                    area.add("Hatibandha");
                    area.add("Kaliganj");
                    area.add("Lalmonirhat Sadar");
                    area.add("Patgram");


                    fillArea();
                    showDeliverDialog(amountOutSideDhaka);
                }

                else if (city.equals("Nilphamari")){
                     area.clear();

                     area.add("Dimla");
                     area.add("Domar");
                     area.add("Jaldhaka");
                     area.add("Kishoreganj");
                     area.add("Nilphamari Sadar");
                     area.add("Sayedpur");

                     fillArea();
                     showDeliverDialog(amountOutSideDhaka);
                }

                else if (city.equals("Panchagarh")){
                    area.clear();

                    area.add("Atwari");
                    area.add("Boda");
                    area.add("Debiganj");
                    area.add("Panchagarh Sadar");
                    area.add("Tetulia");

                    fillArea();
                    showDeliverDialog(amountOutSideDhaka);

                }

                else if (city.equals("Rongpur")){
                    area.clear();

                    area.add("Badarganj");
                    area.add("Gangachara");
                    area.add("Kaunia");
                    area.add("Mithapukur");
                    area.add("Pirgacha");
                    area.add("Pirganj");
                    area.add("Rongpur Sadar");
                    area.add("Taraganj");

                    fillArea();
                    showDeliverDialog(amountOutSideDhaka);
                }

                else if (city.equals("Thakurgaon")){

                    area.clear();

                    area.add("Baliadangi");
                    area.add("Haripur");
                    area.add("Pirganj");
                    area.add("Ranisankail");
                    area.add("Thakurgaon Sadar");

                    fillArea();
                    showDeliverDialog(amountOutSideDhaka);
                }


                else if (city.equals("Hobiganj")){
                    area.clear();

                    area.add("Azmiriganj");
                    area.add("Bahubal");
                    area.add("Baniachong");
                    area.add("Chunarughat");
                    area.add("Hobiganj Sadar");
                    area.add("Lakhai");
                    area.add("Madhabpur");
                    area.add("Nabiganj");
                    area.add("Sayestaganj");

                    fillArea();
                    showDeliverDialog(amountOutSideDhaka);
                }

                else if (city.equals("Moulvibazar")){
                    area.clear();

                    area.add("Barlekha");
                    area.add("Juri");
                    area.add("Kamalganj");
                    area.add("Kulaura");
                    area.add("Moulvibazar Sadar");
                    area.add("Rajnagar");
                    area.add("Sreemangal");

                    fillArea();
                    showDeliverDialog(amountOutSideDhaka);
                }


                else if (city.equals("Sunamganj")){

                    area.clear();

                    area.add("Biswamvarpur");
                    area.add("Chatak");
                    area.add("Dakhin Sunamgan");
                    area.add("Derai");
                    area.add("Dharmapasha");
                    area.add("Doarabazar");
                    area.add("Jagannathpur");
                    area.add("Jamalganj");
                    area.add("Sulla");
                    area.add("Sunamganj Sadar");
                    area.add("Tahirpur");

                    fillArea();
                    showDeliverDialog(amountOutSideDhaka);
                }

                else if (city.equals("Sylhet")){

                    area.clear();

                    area.add("Balaganj");
                    area.add("Beanibazar");
                    area.add("Biswanath");
                    area.add("Companiganj");
                    area.add("Dakshin Surma");
                    area.add("Fenchuganj");
                    area.add("Golapganj");
                    area.add("Gowainghat");
                    area.add("Jointiapur");
                    area.add("Kanaighat");
                    area.add("Osmaninagar");
                    area.add("Sylhet Sadar");
                    area.add("Zakiganj");

                    fillArea();
                    showDeliverDialog(amountOutSideDhaka);
                }


            }
        });


        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                name = nameEt.getText().toString();
                phone = phoneEt.getText().toString();
                address = locationEt.getText().toString();
                email = emailEt.getText().toString();

                if (name.isEmpty()){
                    Toasty.warning(CustomerDetailsActivity.this, "Enter Name", Toast.LENGTH_LONG, true).show();

                }
                else if (phone.isEmpty() && phone.length() < 10){
                    Toasty.warning(CustomerDetailsActivity.this, "Enter valid number", Toast.LENGTH_LONG, true).show();
                }
                else if (address.isEmpty()){
                    Toasty.warning(CustomerDetailsActivity.this, "give your home address", Toast.LENGTH_LONG, true).show();

                }
                else if (email.isEmpty()){
                    Toasty.warning(CustomerDetailsActivity.this, "give email", Toast.LENGTH_LONG, true).show();

                }

                else if (city.isEmpty()){
                    Toasty.warning(CustomerDetailsActivity.this, "Please Select Your District", Toast.LENGTH_LONG, true).show();

                }


                else if (cashRb.isChecked()){
                Log.d("list", "onCreate: " + json);
                    paymentStatus = cashRb.getText().toString();
                    Log.e("paymentStatus", "onClick: "+paymentStatus );


                Call<OrderResponse> orderResponseCall = apiService.postOrder(name, phone, address, json,email,city,"Bangladesh",paymentStatus,String.valueOf(deliveryAndTotalPrice));

                progressDialog.show();
                progressDialog.setContentView(R.layout.show_dialog_layout);
                progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                orderResponseCall.enqueue(new Callback<OrderResponse>() {
                    @Override
                    public void onResponse(Call<OrderResponse> call, Response<OrderResponse> response) {
                        OrderResponse orderResponse = response.body();
                        Toasty.success(CustomerDetailsActivity.this, "Thanks For Order", Toast.LENGTH_LONG, true).show();

                        progressDialog.dismiss();
                        Intent intent = new Intent(CustomerDetailsActivity.this,OrderTrackingActivity.class);
                        intent.putExtra("flag","Customer");
                        startActivity(intent);

                    }

                    @Override
                    public void onFailure(Call<OrderResponse> call, Throwable t) {
                        Log.e("order", "onFailure " + t.getLocalizedMessage());

                    }
                });

            }
                else if (onlineRb.isChecked()){
                    paymentStatus = onlineRb.getText().toString();
                    Log.e("paymentStatus", "onClick: "+paymentStatus );
                    dialogBuilder.showLoading();
                    aamarPay.setTransactionParameter(String.valueOf(deliveryAndTotalPrice),"BDT","BGHaat Shopping");
                    aamarPay.setCustomerDetails(name,email,phone,address,city,"Bangladesh");

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

                            Call<OrderResponse> orderResponseCall = apiService.postOrder(name, phone, address, json,email,city,"Bangladesh",paymentStatus,String.valueOf(deliveryAndTotalPrice));

                            orderResponseCall.enqueue(new Callback<OrderResponse>() {
                                @Override
                                public void onResponse(Call<OrderResponse> call, Response<OrderResponse> response) {

                                    OrderResponse orderResponse = response.body();
                                    Toast.makeText(CustomerDetailsActivity.this, "" + orderResponse.getMessage(), Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onFailure(Call<OrderResponse> call, Throwable t) {

                                }
                            });

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

    private void fillArea() {

        areaAdapterItem = new ArrayAdapter<>(this,R.layout.list_item,area);
        areaAutoComplete.setAdapter(areaAdapterItem);

    }

    private void showDeliverDialog(String item) {

        deliveryAndTotalPrice = Double.parseDouble(item)+totalPrice;

        AlertDialog.Builder builder = new AlertDialog.Builder(CustomerDetailsActivity.this,R.style.AlertDialogTheme);
        View view = LayoutInflater.from(this).inflate(R.layout.layout_success_dialog,(ConstraintLayout) findViewById(R.id.layout_dialog_container));
        builder.setView(view);
        ((TextView) view.findViewById(R.id.textTitle)).setText(getResources().getString(R.string.success));
        ((TextView) view.findViewById(R.id.textMessage)).setText("Delivery Charge will be applicable " + item + " tk" + "Total cost "+ deliveryAndTotalPrice);
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