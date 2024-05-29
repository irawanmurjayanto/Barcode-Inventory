package com.example.barcodegcnew;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class WrhParis2 extends AppCompatActivity {

    public static final String TAG_JSON_ARRAY="result";
    private RecyclerView mRecyclerView;

    private RecyclerView mRecyclerViewpos;
    private BarangAdapterCabangPutra rvAdapterpos;

    private BarangAdapterNOREF rvAdapter;

    private ArrayList<BarangMasterUser> barangCabangArrayList;
    private ArrayList<String> names = new ArrayList<String>();

    private RecyclerView.LayoutManager mLayoutManager;

    private Context context = WrhParis2.this;

    private static  final int REQUEST_CODE_ADD =1;

    private static  final int REQUEST_CODE_EDIT =2;

    private List<BarangCabang> barangListPOS = new ArrayList<BarangCabang>();
    private List<BarangBarcodeNOREF> barangList = new ArrayList<BarangBarcodeNOREF>();
    private ADB_Master adb_master;
    private ADB_Trans adb_trans;

    private ProgressDialog pDialog;
    private EditText cari1,editoven;

    AlertDialog.Builder dialog;
    LayoutInflater inflater;
    View dialogView;
    ImageButton butbarcodeher;
    private static final String DATABASE_NAME = "master.db";
    SQLiteDatabase mDatabase;
    TextView t1,textTampung,textTampung2,textTampung3,textnoreff;
    Button butpos1,butovenname,butrefresh;
    private SimpleDateFormat dateFormatter;
    Spinner spnOven;
    private static String url2;
private String wrhtype_get;

    Button butprocwrh,butprocwrh2;
    EditText caribarcode;

    public static  String typeall,itemcode,itemdesc,qtygc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wrh_paris2);








        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        Intent wrhsatu=getIntent();
        final String judul=wrhsatu.getStringExtra("judul");
           wrhtype_get=wrhsatu.getStringExtra("wrhtype");

           typeall=wrhtype_get;

        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(judul);
        setSupportActionBar(toolbar);


        adb_master=new ADB_Master(getBaseContext());
        adb_trans = new ADB_Trans(getBaseContext());
        mDatabase = openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);

        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);

        mRecyclerView.setLayoutManager(mLayoutManager);

        pDialog = new ProgressDialog(this);


        butrefresh=findViewById(R.id.butrefresh);


        butprocwrh=findViewById(R.id.butprocwrh);

        caribarcode=findViewById(R.id.editcari);







        caribarcode.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                loaddatabarcode();
                // TODO Auto-generated method stub
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable s) {

                // TODO Auto-generated method stub
            }
        });


        butrefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loaddatabarcode();

            }
        });



        butprocwrh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

         //       if (wrhtype_get.equals("import")) {

                    Intent a = new Intent(getBaseContext(), NURUploadBarcodeNew.class);
                    //  Intent a=new Intent(getBaseContext(),Emp_RegItemdesc.class);
                    a.putExtra("wrhtype", wrhtype_get);
                    startActivityForResult(a, 111);
            //    }else
             /*   {
                    Intent a = new Intent(getBaseContext(), NURUploadBarcodeNew2.class);
                    //  Intent a=new Intent(getBaseContext(),Emp_RegItemdesc.class);
                    a.putExtra("wrhtype", wrhtype_get);
                    startActivityForResult(a, 111);
                }*/
               // Toast.makeText(getBaseContext(),"Posisi no: ", Toast.LENGTH_SHORT).show();

            }
        });

        loaddatabarcode();
    }


    private void gambarDatakeRecyclerView(){

        rvAdapterpos = new BarangAdapterCabangPutra(barangListPOS);

        mRecyclerView.setAdapter(rvAdapterpos);

        //  final String test1=ambilsw1.getText().toString();


        mRecyclerView.addOnItemTouchListener(new RecyclerItemListener(context, new RecyclerItemListener.OnItemClickListener() {

                    @Override

                    public void onItemClick(View view, int position) {

                       // Toast.makeText(getBaseContext(),"Posisi no: "+position, Toast.LENGTH_SHORT).show();

                        BarangCabang barang = rvAdapterpos.getItem(position);



                            Intent intent = new Intent(getBaseContext(), WrhParis2Sambung.class);
                            intent.putExtra("barang", barang);

                            startActivityForResult(intent, REQUEST_CODE_EDIT);

                        //startActivity(intent);



                    }

                })

        );

    }


    public void loaddatabarcode(){

        //  final String notransaksi= notrans.getText().toString();
        Intent intent=getIntent();
        // final String kodecabang1=intent.getStringExtra("kodecabang");

//        Toast.makeText(this,"Load Barcode : "+notransaksi,Toast.LENGTH_LONG).show();
      //  Toast.makeText(getBaseContext(),"Loading .."+wrhtype_get, Toast.LENGTH_SHORT).show();

       // if (wrhtype_get.equals("import")) {

          url2 = AppConfig.IP_SERVER + "gcnew/android_listbarcode2putra.php";
        //}else
        //{
         // url2 = AppConfig.IP_SERVER + "/gcnew/android_listbarcode2sub.php";
       // }
        //String url ="http://golden-care.co.id/android/listdata.php";
//        pDialog = new ProgressDialog(Transaksi_POS.this);
        //      pDialog.setMessage("Retieve Data Barang...");
        // Toast.makeText(this,"Load Barcode : "+notransaksi,Toast.LENGTH_LONG).show();
        //    pDialog.show();

        //  pDialog.setMessage("Retrieve Data...");
        //  showDialog();

        StringRequest postRequest = new StringRequest(Request.Method.POST, url2,new Response.Listener<String>() {

            @Override

            public void onResponse(String response) {

                // Log.d("MainActivity bedul kubu","response:"+response);

                //   hideDialog();



                // barangListPOS.clear();
                processResponsePOS(response);

              gambarDatakeRecyclerView();

                // final String status1=ambilsw1.getText().toString();

//  pDialog.hide();
                //   hideDialog();
            }

        },

                new Response.ErrorListener() {

                    @Override

                    public void onErrorResponse(VolleyError error) {

                        //   hideDialog();

                        error.printStackTrace();

                    }

                }

        ) {

            @Override

            protected Map<String, String> getParams() {

                // Toast.makeText(Transaksi_POS.this,"HAS MAP : "+notransaksi,Toast.LENGTH_LONG).show();

                Map<String, String> params = new HashMap<>();

                // the POST parameters:


               // final String nama=edit1.getText().toString();
                params.put("typetran",wrhtype_get);
                params.put("cari",caribarcode.getText().toString());
                //    params.put("kodecab",kodecabang1);

                return params;

            }

        };

        Volley.newRequestQueue(this).add(postRequest);
        //  Toast.makeText(this,"HAS MAP : "+notransaksi,Toast.LENGTH_LONG).show();
    }


    private void processResponsePOS(String response){

        try {

            Log.d("TAG", "data response DATA POS: "+response);

            JSONObject jsonObj = new JSONObject(response);


            //    JSONArray jsonArray = jsonObj.getJSONArray("data");

            // Log.d("TAG", "data length: " + jsonArray.length());

            BarangCabang objectbarang = null;

            barangListPOS.clear();

            //NumberFormat numberFormat  = new DecimalFormat("##");

            // DecimalFormat formatter = new DecimalFormat("#,###,###,###");
            //  totalrp.setText(formatter.format(obj.getDouble("total")));


            //    Log.d("TAG", "data length: " + jsonArray.length());

            SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
            JSONArray jsonArray = jsonObj.getJSONArray("data");
            for(int i = 0; i < jsonArray.length(); i++) {

                JSONObject obj = jsonArray.getJSONObject(i);

                objectbarang = new BarangCabang();

                objectbarang.setIdno(obj.getString("idno"));
                objectbarang.setItem_code(obj.getString("item_code"));
                objectbarang.setItem_desc(obj.getString("item_desc"));
                objectbarang.setBarcode(obj.getString("item_desc"));
                objectbarang.setQtygc(obj.getDouble("conv_qty"));
                itemcode=obj.getString("item_code");
                itemdesc=obj.getString("item_desc");
                qtygc=String.valueOf(obj.getDouble("conv_qty"));

          /*      if (wrhtype_get.equals("import")) {
                    String s="";
                }else
                {
                    objectbarang.setTujuan(obj.getString("tujuan"));
                }*/

                barangListPOS.add(objectbarang);


                // Toast.makeText(getBaseContext(),"status"+checked,Toast.LENGTH_LONG).show();
                //sw1.setChecked(true);
                //



       /*         String test1=obj.getString("groupho");
                if (test1.equals("1")) {
                    // Toast.makeText(getBaseContext(),"status"+test1,Toast.LENGTH_LONG).show();
                    //    objectbarang.setGroupho("Head Office");
                    objectbarang.setSwitch(true);
                }else
                {
                    //  Toast.makeText(getBaseContext(),"status"+test1,Toast.LENGTH_LONG).show();
                    objectbarang.setSwitch(false);
                }


            }

            String errormsg = jsonObj.getString("errormsg");

            if (errormsg.equals("failed"))
            {

                Toast.makeText(this,"CARI CARI",Toast.LENGTH_LONG).show();

                barangListPOS.clear();
            }
*/
            }

//pDialog.hide();
        } catch (JSONException e) {

            Log.d("MainActivity", "errorJSON");

        }

    }


    //barcode area
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        // IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);

        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);

        Toast.makeText(getBaseContext(),"test hasil"+requestCode,Toast.LENGTH_LONG).show();

        // EditText tvResult = (EditText) findViewById(R.id.txtEditResult);
        //TextView tvResult2=(TextView) findViewById(R.id.hasilspin3);


       // if (resultCode == RESULT_OK ) {

            // if (intent.getStringExtra("refreshflag").equals("1")) {

            loaddatabarcode();

            //}

      //  }


    /*    new CountDownTimer(1100, 1000) {

            public void onTick(long millisUntilFinished) {
                //  totalrp.setText("seconds remaining: " + millisUntilFinished / 1000);
            }

            public void onFinish() {
                //mTextField.setText("done!");
                butrefresh.performClick();

            }
        }.start();

*/

    }



}