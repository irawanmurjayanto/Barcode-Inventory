package com.example.barcodegcnew;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

import static android.R.layout.simple_spinner_item;


public class NURUploadBarcodeBatch extends AppCompatActivity {
    public static final String TAG_JSON_ARRAY="result";
    private RecyclerView mRecyclerView;
    private RecyclerView mRecyclerViewpos;
    private BarangAdapterCabangSales rvAdapterpos;
    private BarangAdapter rvAdapter;
    private List<BarangCabang> barangListPOS = new ArrayList<BarangCabang>();
    private ArrayList<BarangMasterUser> barangCabangArrayList;
    private ArrayList<String> names = new ArrayList<String>();
    BarangCabang barang;
    private static String noref;
    public static String jumjumbar;
    public static String norefall;

    private RecyclerView.LayoutManager mLayoutManager;

    private Context context = NURUploadBarcodeBatch.this;

    private static  final int REQUEST_CODE_ADD =1;

    private static  final int REQUEST_CODE_EDIT =2;

    private List<BarangBarcode> barangList = new ArrayList<BarangBarcode>();
    private List<BarangCabang> barangListNoref = new ArrayList<BarangCabang>();
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
    TextView t0,t1,textTampung,textTampung2,textTampung3,textnoreff,textjuminv;
    Button butpos1,butovenname,butmanualbarc,butmanualbarcdel,butdelbarctran,butdelnoref,butsalessum,butinv;
    private SimpleDateFormat dateFormatter;
    Spinner spnOven;
    private String a1;
    private  static int hitbarc;
    FloatingActionButton fab;
    public static String lastscan;
    private ZXingScannerView mScannerView;
    private WebView webView;
    private WebView myWebView;
    EditText editinvno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory__barangbatch);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        spnOven=(Spinner) findViewById(R.id.spnOven);
        textTampung=(TextView)findViewById(R.id.textTampung);
        textTampung2=(TextView)findViewById(R.id.textTampung2);
        textTampung3=(TextView)findViewById(R.id.textTampung3);
        textjuminv=(TextView)findViewById(R.id.jumbarc3);
        textnoreff=(TextView)findViewById(R.id.textnoref);
        butinv=findViewById(R.id.butambilinv);
        editinvno=findViewById(R.id.invno);

     //    toolbar.setTitle("Barcode Upload");
        //setSupportActionBar(toolbar);
        cari1=(EditText) findViewById(R.id.cariText) ;
        editoven=(EditText) findViewById(R.id.editOven) ;
      //  butbarcodeher=(ImageButton) findViewById(R.id.butBarcodeher);
        butpos1=(Button) findViewById(R.id.butpost2);
        butovenname=(Button) findViewById(R.id.butovenname);
        butmanualbarc=(Button) findViewById(R.id.butmanualbarc);
        butmanualbarcdel=(Button) findViewById(R.id.butmanualbarcdel);
        butdelnoref=(Button) findViewById(R.id.butdelnoref);
        butsalessum=(Button) findViewById(R.id.butsalessum);

        butinv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent b=new Intent(getBaseContext(),Emp_reg_inv.class);
               // b.putExtra("idoven",textTampung2.getText().toString());
                startActivityForResult(b,333);
            }
        });


        t0=(TextView) findViewById(R.id.jumbarc1);
        t1=(TextView) findViewById(R.id.jumbarc2);
        Calendar newDate = Calendar.getInstance();
      //  newDate.set(year, monthOfYear, dayOfMonth);


        adb_master=new ADB_Master(getBaseContext());
        adb_trans = new ADB_Trans(getBaseContext());
        mDatabase = openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);


        Intent a=getIntent();



          a1=a.getStringExtra("wrhtype");
      //Toast.makeText(getBaseContext(),a1,Toast.LENGTH_LONG).show();


        butsalessum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a= new Intent(getBaseContext(),printSalesgroup.class);
                startActivity(a);
            }
        });

        butdelnoref.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             DialogDelNoref(textnoreff.getText().toString());
            }
        });

        butmanualbarcdel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
        cari1.setText("");
        cari1.setGravity(0);
            }
        });

        butmanualbarc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               saveDataBarcode();

            }
        });

butovenname.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent b=new Intent(getBaseContext(),Emp_RegOvenAss.class);
        b.putExtra("idoven",textTampung2.getText().toString());
        startActivityForResult(b,222);
    }
});

        spnOven.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // TODO Auto-generated method stub

                textTampung.setText( spnOven.getSelectedItem().toString());
               // textTampung.setText( spnOven.getSelectedItemPosition());
                //  editTextHak.setText( allusers.getSelectedItem().toString());
             //   retrieveOven2();

/*
                adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,android.R.id.text1,users);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                //attach adapter to spinner
                allusers.setAdapter(adapter);
                String compareValue = editTextHak.getText().toString();
                Toast.makeText(getApplicationContext(), "masuk:"+compareValue, Toast.LENGTH_SHORT).show();
                if (compareValue != null) {
                    int spinnerPosition = adapter.getPosition(compareValue);
                    allusers.setSelection(spinnerPosition);
              */


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub

            }
        });



        butpos1.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
            DialogPOST();
           }
       });

    /*    butbarcodeher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getBaseContext(), "Load barcode", Toast.LENGTH_LONG).show();
                IntentIntegrator integrator = new IntentIntegrator(NURUploadBarcode.this);
                integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
                integrator.setCaptureActivity(AnyOrientationCaptureActivity.class);
                integrator.setCaptureActivity(TorchOnCaptureActivity.class);
                integrator.setOrientationLocked(false);
                integrator.setPrompt("Scan a barcode");
                integrator.setCameraId(0);  // Use a specific camera of the device
                integrator.setBeepEnabled(true);
                integrator.initiateScan();
            }
        });*/


    /*    cari1.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == EditorInfo.IME_ACTION_SEARCH ||
                                keyCode == EditorInfo.IME_ACTION_DONE ||
                                event.getAction() == KeyEvent.ACTION_DOWN &&
                                        event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    // Perform action on key press
                    Toast.makeText(NURUploadBarcode.this, "cari kata", Toast.LENGTH_SHORT).show();
                    loadDataServerVolley();
                    gambarDatakeRecyclerView();
                    return true;
                }
                return false;
            }
        });*/

       fab = (FloatingActionButton) findViewById(R.id.fab);

       fab.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View view) {

//cari1.setText("");

              if (editinvno.length()==0)
                {
                    Toast.makeText(getBaseContext(),"No Invoice harus diisi",Toast.LENGTH_LONG).show();
                }else {

                    //Toast.makeText(getBaseContext(), "Load barcode", Toast.LENGTH_LONG).show();
                    IntentIntegrator integrator = new IntentIntegrator(NURUploadBarcodeBatch.this);
                    integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
                    integrator.setCaptureActivity(AnyOrientationCaptureActivity.class);
                    integrator.setCaptureActivity(TorchOnCaptureActivity.class);
                    integrator.setOrientationLocked(true);
                    integrator.setPrompt("PT. Paradise 2021");
                    integrator.setCameraId(0);  // Use a specific camera of the device
                    integrator.setBeepEnabled(true);
                    integrator.initiateScan();
                }

            }

        });

        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);

        mRecyclerView.setLayoutManager(mLayoutManager);

        pDialog = new ProgressDialog(this);

        //loadDataServerVolley();
        if (a.hasExtra("barang")){

            barang=(BarangCabang) a.getSerializableExtra("barang");

            setData(barang);
            norefall=textnoreff.getText().toString();
        }else
        {
            dateFormatter = new SimpleDateFormat("ddMMyyyy_HHmmss", Locale.US);
            Date currentTime = Calendar.getInstance().getTime();
          textnoreff.setText(MainActivity.user_idbuffer+dateFormatter.format(newDate.getTime()));
          norefall=MainActivity.user_idbuffer+dateFormatter.format(newDate.getTime());
        }
       retrieveOven();

        WrhParis2.typeall="";
 //jumjumbar="";
 lastscan="Last Scan";

        Toast.makeText(getBaseContext(),"Loading .."+textTampung.getText().toString(), Toast.LENGTH_SHORT).show();
    }

    private void setData(BarangCabang barang) {

        // Locale localeID = new Locale("in", "ID");
        //NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);
        //detailHarga.setText(formatRupiah.format((double)hargarumah));

        DecimalFormat formatter = new DecimalFormat("#");
        // totalrp.setText(formatter.format(obj.getDouble("total")));
        noref=barang.getNoref();
        textnoreff.setText(barang.getNoref());
        textTampung.setText(barang.getTujuan());
        editinvno.setText(barang.getNoinv());
        DecimalFormat df = new DecimalFormat("#");
        textjuminv.setText(String.valueOf(df.format(barang.getQtygc())));

     /*   textTampung2.setText(String.valueOf(barang.getLokoven()));
        textTampung3.setText(String.valueOf(barang.getNamaoven()));
        textnoreff.setText(barang.getNoref());
        editoven.setText(barang.getNamaoven2());*/

      /*   if (Integer.valueOf(t1.getText().toString())>0)
         {
           // spnOven.setEnabled(false);
          //  butovenname.setEnabled(false);
             Toast.makeText(getBaseContext(),"t",Toast.LENGTH_LONG).show();
         }*/

      loaddatabarcode();

    }




    public void saveDataBarcode() {

        // refreshFlag="1";

        final Intent intent = getIntent();

     //   Toast.makeText(getBaseContext(),"response: "+MainActivity.user_idbuffer+textnoreff.getText().toString()+cari1.getText().toString(), Toast.LENGTH_LONG).show();

      /*  if (cari1.getText().toString().isEmpty()) {
            Toast.makeText(getBaseContext(), "Anda Harus Isi barcode" , Toast.LENGTH_SHORT).show();
            return;

        }*/

        //Toast.makeText(getBaseContext(),"responsexxxx: "+savetag, Toast.LENGTH_SHORT).show();

        pDialog.setMessage("Save Data...");
        showDialog();


        //final String item_code = itemcodepos.getText().toString();

        //final String notransaksi = notrans.getText().toString();
        // final String notransaksi = intent.getStringExtra("notrans");;

        //  final String qty = totqty.getText().toString();
        //   final String disc = totdisc.getText().toString();
        //   Toast.makeText(getBaseContext(),"response: "+editcust., Toast.LENGTH_SHORT).show();
//Toast.makeText(getBaseContext(),"response: "+caribarcode.getText().toString(), Toast.LENGTH_SHORT).show();

        // String url = AppConfig.IP_SERVER+"/savedata.php";

        //      pDialog = new ProgressDialog(Transaksi_POS.this);
        //    pDialog.setMessage("Save Data ...");
        //  pDialog.show();

        //showDialog();
        String urlx = AppConfig.IP_SERVER+"gcnew/savedatagcsales.php";

        StringRequest postRequest1 = new StringRequest(Request.Method.POST, urlx, new Response.Listener<String>() {

            @Override

            public void onResponse(String response) {

                // pDialog.hide();
                // Log.d("Transaksi POS", "response :" + response);

                // Toast.makeText(getBaseContext(),"response: "+response, Toast.LENGTH_LONG).show();

                processResponsesave(response);
                // finish();
                hideDialog();

            }

        },

                new Response.ErrorListener() {

                    @Override

                    public void onErrorResponse(VolleyError error) {

                        hideDialog();


                        if (error instanceof NetworkError) {
                            Toast.makeText(getBaseContext(),
                                    "Oops. Network Error",
                                    Toast.LENGTH_LONG).show();
                            DialogErrorNetwork();

                        } else if (error instanceof ServerError) {
                            DialogErrorServer();
                            Toast.makeText(getBaseContext(),
                                    "Oops. Server Time out",
                                    Toast.LENGTH_LONG).show();
                        } else if (error instanceof AuthFailureError) {
                        } else if (error instanceof ParseError) {
                        } else if (error instanceof NoConnectionError) {
                            DialogErrorNetwork();
                            Toast.makeText(getBaseContext(),
                                    "Oops. No Connection!",
                                    Toast.LENGTH_LONG).show();
                        } else if (error instanceof TimeoutError) {
                            DialogErrorNetwork();
                            Toast.makeText(getBaseContext(),
                                    "Oops. Timeout error!",
                                    Toast.LENGTH_LONG).show();
                        }

                        error.printStackTrace();

                    }

                }

        ) {

            @Override

            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<>();

                // the POST parameters:

                params.put("barcode", cari1.getText().toString());
                params.put("user_name", MainActivity.user_idbuffer);
                params.put("noref",textnoreff.getText().toString());
                params.put("tujuan",textTampung.getText().toString());
                params.put("noinv",editinvno.getText().toString());
                params.put("qtyinv",textjuminv.getText().toString());

//                params.put("hargajual","0");
                //              params.put("diskon","0");
                //            params.put("qty","0");

                //    params.put("totqty",qty);
                //    params.put("totdisc",disc);

               /* params.put("kode",kode);

                params.put("harga",harga);

                params.put("harga_beli",harga_beli);*/

/*
                if (action_flag.equals("add")){

                    params.put("id","0");

                }else{

                    params.put("id",barang.getId());

                }*/

                return params;

            }

        };

        Volley.newRequestQueue(this).add(postRequest1);


    }


    private void processResponsesave(String response) {

        try {
            Log.d("TAG", "data response DATA POS: "+response);
            JSONObject jsonObj = new JSONObject(response);

            BarangCabang objectbarang = null;

            barangListPOS.clear();


            String errormsg = jsonObj.getString("errormsg");

            String jumbarc = jsonObj.getString("jumhit");
          //  String jumdata = jsonObj.getString("jumdata");




            if (errormsg.equals("failed2")) {

                jumjumbar=jumbarc;

                Intent a=new Intent(getBaseContext(),warningumum.class);
                a.putExtra("warning","Barcode belum terdaftar");
                startActivity(a);


                // finish();
            }else if (errormsg.equals("failed1")) {

                jumjumbar=jumbarc;

                Intent a=new Intent(getBaseContext(),warningumum.class);
                a.putExtra("warning","Transaksi Barcode sudah pernah ada");
                startActivity(a);


                // finish();
            }else
            {

                jumjumbar=jumbarc;
                 fab.performClick();
                MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.bell);
                mediaPlayer.start();

                //fab.performClick();
            /*   new CountDownTimer(1100, 1000) {

                    public void onTick(long millisUntilFinished) {
                        //  totalrp.setText("seconds remaining: " + millisUntilFinished / 1000);
                    }

                    public void onFinish() {
                        //mTextField.setText("done!");
                        loaddatabarcode();

                    }
                }.start();*/
            }


            hideDialog();
        } catch (JSONException e) {

            //  Log.d("Trnsakais POS2", "errorJSON");

        }


        loaddatabarcode();

    }




    private void retrieveOven() {


        //  Intent intent=getIntent();
        //final String kodecabang1=intent.getStringExtra("kodecabang");


        final String namacari=cari1.getText().toString();
        String url = AppConfig.IP_SERVER+"gcnew/barcsw_ovenloc2ex.php";
        //String url ="http://golden-care.co.id/android/listdata.php";
        pDialog.setMessage("Retieve Kondisi...");
        //    Toast.makeText(this,"test masuk load server "+kodecabang1,Toast.LENGTH_LONG).show();
        showDialog();

        StringRequest postRequest = new StringRequest(Request.Method.POST, url,new Response.Listener<String>() {

            @Override

            public void onResponse(String response) {

                Log.d("MainActivity bedul kubu","response:"+response);

                hideDialog();



                processResponse42(response);

                // gambarDatakeRecyclerView();

            }

        },

                new Response.ErrorListener() {

                    @Override

                    public void onErrorResponse(VolleyError error) {

                        // pDialog.setMessage("Connection failed");

                        hideDialog();

                        if (error instanceof NetworkError) {
                            DialogKoneksi();
                        } else if (error instanceof ServerError) {

                            DialogKoneksi();
                        } else if (error instanceof AuthFailureError) {
                            DialogKoneksi();
                        } else if (error instanceof ParseError) {
                            DialogKoneksi();
                        } else if (error instanceof NoConnectionError) {

                            DialogKoneksi();
                        } else if (error instanceof TimeoutError) {

                            DialogKoneksi();
                        }




                        error.printStackTrace();

                    }

                }

        ) {

            @Override

            protected Map<String, String> getParams()

            {

                Map<String, String>  params = new HashMap<>();

                // the POST parameters:

                // params.put("idoven",textTampung.getText().toString());
                //    params.put("kodecab",kodecabang1);

                return params;

            }

        };
        postRequest.setRetryPolicy(new DefaultRetryPolicy(100 * 1000, 1, 1.0f));
        Volley.newRequestQueue(this).add(postRequest);

    }

    private void processResponse42(String response){

        try {

            JSONObject obj = new JSONObject(response);
            // if(obj.optString("status").equals("true")){

            barangCabangArrayList = new ArrayList<>();
            JSONArray dataArray  = obj.getJSONArray("data");

            for (int i = 0; i < dataArray.length(); i++) {

                BarangMasterUser playerModel = new  BarangMasterUser();
                JSONObject dataobj = dataArray.getJSONObject(i);

                textTampung2.setText(String.valueOf(dataobj.getString("kondisi")));

                // playerModel.setIdoven(Integer.valueOf(dataobj.getString("kondisi")));
                playerModel.setOvenname(dataobj.getString("kondisi"));

                //  kodecabang.setText(dataobj.getString("kode_cabang"));
                // namacabang.setText(dataobj.getString("nama"));
                                    /*
                                    playerModel.setCountry(dataobj.getString("country"));
                                    playerModel.setCity(dataobj.getString("city"));
                                    playerModel.setImgURL(dataobj.getString("imgURL"));*/

                barangCabangArrayList.add(playerModel);

            }

            for (int i = 0; i < barangCabangArrayList.size(); i++){
                names.add(barangCabangArrayList.get(i).getOvenname().toString());
            }

            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(NURUploadBarcodeBatch.this, simple_spinner_item, names);
            spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
            spnOven.setAdapter(spinnerArrayAdapter);
            //  removeSimpleProgressDialog();

            String compareValue = textTampung.getText().toString();
            if (compareValue != null) {
                int spinnerPosition = spinnerArrayAdapter.getPosition(compareValue);
                spnOven.setSelection(spinnerPosition);
            }






            try {

                //   mPicture = getPictureCallback();
                //   mCamera.takePicture(null, null, mPicture);

            } catch (Exception e1) {
                e1.printStackTrace();
                //texterror.setText("nosupport");
            }






            SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyyyy");
            String date = dateFormat.format(new Date());















            hideDialog();
            // finish();





        } catch (JSONException e) {

            Log.d("BarangActivity", "errorJSON");

        }

    }



    private void gambarDatakeRecyclerView(){

        rvAdapterpos = new BarangAdapterCabangSales(barangListPOS);

        mRecyclerView.setAdapter(rvAdapterpos);

        //  final String test1=ambilsw1.getText().toString();


        mRecyclerView.addOnItemTouchListener(new RecyclerItemListener(context, new RecyclerItemListener.OnItemClickListener() {

                    @Override

                    public void onItemClick(View view, int position) {

                        // Toast.makeText(getBaseContext(),"Posisi no: "+position, Toast.LENGTH_SHORT).show();

                        BarangCabang barang = rvAdapterpos.getItem(position);

                        DialogDelNoref(String.valueOf(barang.getIdno()));
                      /*  Intent intent = new Intent(getBaseContext(), NURUploadBarcodeBatch.class);
                        intent.putExtra("barang", barang);

                        startActivityForResult(intent, REQUEST_CODE_EDIT);*/

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
        pDialog.setMessage("Loading Data");
        showDialog();



        String    url2 = AppConfig.IP_SERVER + "gcnew/android_listbarcode2subout.php";

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

                        hideDialog();


                        if (error instanceof NetworkError) {
                            Toast.makeText(getBaseContext(),
                                    "Oops. Network Error",
                                    Toast.LENGTH_LONG).show();
                            DialogErrorNetwork();

                        } else if (error instanceof ServerError) {
                            DialogErrorServer();
                            Toast.makeText(getBaseContext(),
                                    "Oops. Server Time out",
                                    Toast.LENGTH_LONG).show();
                        } else if (error instanceof AuthFailureError) {
                        } else if (error instanceof ParseError) {
                        } else if (error instanceof NoConnectionError) {
                            DialogErrorNetwork();
                            Toast.makeText(getBaseContext(),
                                    "Oops. No Connection!",
                                    Toast.LENGTH_LONG).show();
                        } else if (error instanceof TimeoutError) {
                            DialogErrorNetwork();
                            Toast.makeText(getBaseContext(),
                                    "Oops. Timeout error!",
                                    Toast.LENGTH_LONG).show();
                        }

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
                //  params.put("typetran",wrhtype_get);
                params.put("noref",norefall);
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
            JSONObject jsonObj2 = new JSONObject(response);


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

          //  JSONArray jsonArray2 = jsonObj2.getJSONArray("jumdata");
           // JSONObject obj2 = jsonArray2.getJSONObject(0);
            String jumdata = jsonObj.getString("jumdata");
            t1.setText(jumdata);
         //   jumjumbar=jumdata;

            for(int i = 0; i < jsonArray.length(); i++) {

               JSONObject obj = jsonArray.getJSONObject(i);
             //   t1.setText(obj.getJSONArray("jumdata").toString());


                objectbarang = new BarangCabang();

                 objectbarang.setIdno(obj.getString("idno"));
               /* objectbarang.setBarcode(obj.getString("barcode"));
                objectbarang.setTgl_rec((obj.getString("tgl_rec")));
                objectbarang.setUser_name(obj.getString("user_name"));
                objectbarang.setItem_code(obj.getString("item_code"));
                objectbarang.setItem_desc(obj.getString("item_desc"));
                objectbarang.setTypetran(obj.getString("typetran"));
                objectbarang.setKondisi(obj.getString("kondisi"));
                objectbarang.setRemarks(obj.getString("remarks"));
                objectbarang.setQtygc(obj.getDouble("qty"));*/
                objectbarang.setItem_code(obj.getString("item_code"));
                objectbarang.setItem_desc(obj.getString("item_desc"));
                objectbarang.setBarcode(obj.getString("barcode"));
                objectbarang.setTgl_rec((obj.getString("tgl_rec")));
                objectbarang.setUser_name(obj.getString("user_name"));

            /*    if (wrhtype_get.equals("import")) {
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
        hideDialog();
    }

    @Override

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);

      //  Toast.makeText(this, "Data inv"+data.getStringExtra("noinv"), Toast.LENGTH_SHORT).show();

if (requestCode==333)
{
    editinvno.setText(data.getStringExtra("noinv"));
    DecimalFormat df = new DecimalFormat("#");
    textjuminv.setText(String.valueOf(data.getStringExtra("qtyrec")));

}

        if (scanningResult != null) {

            // textTampung3.setText(String.valueOf(data.getStringExtra("idno")));
            //insertdata();

            if(scanningResult.getContents() != null) {
             //   Toast.makeText(this, "hASIL cANNING"+scanningResult.getContents(), Toast.LENGTH_SHORT).show();
                cari1.setText("");
                cari1.setText(scanningResult.getContents());
                lastscan=scanningResult.getContents();

//                mScannerView.resumeCameraPreview(getBaseContext());

              //  loadDataLokal();
             //   gambarDatakeRecyclerView();
                //we have a result
                //cari1.setText(scanningResult.getContents());
               // textTampung3.setText(String.valueOf(data.getStringExtra("idno")));
               // insertdata();
              // loadDataLokal();
               // gambarDatakeRecyclerView();
                saveDataBarcode();
             // fab.performClick();



            }


        }else
        {
            jumjumbar="";
            lastscan="";

        }





     /*   if (resultCode == RESULT_OK && requestCode == 222) {

editoven.setText(data.getStringExtra("section"));
            textTampung3.setText(String.valueOf(data.getStringExtra("idno")));

            loadDataLokal();

        }
*/




    }

    //ambil data sever volley
    private void insertdata(){

         String item1=cari1.getText().toString();
         String lokoven=textTampung2.getText().toString();
         String namaoven=textTampung3.getText().toString();
         String noref=textnoreff.getText().toString();
         String editoven1=editoven.getText().toString();
         int tagin=1;

        SQLiteDatabase db2 = adb_master.getReadableDatabase();

        String selectQuery2 = "SELECT  *  FROM " + ADB_Master.MyColumns_barcode.NamaTabel+ " WHERE "
                + ADB_Master.MyColumns_barcode.noref + " = '"+noref+"' and "+ ADB_Master.MyColumns_barcode.barcode+" = '"+item1+"'";
        //   String selectQuery2 = "SELECT  *  FROM " + ADB_Master.MyColumns_item.NamaTabel;
        //String selectQuery2 = "SELECT  *  FROM " + ADB_Trans.MyColumns_trans1sub.NamaTabel;
        Cursor c2 = db2.rawQuery(selectQuery2, null);
        int hit1 = c2.getCount();
        Toast.makeText(this, "hit"+hit1, Toast.LENGTH_SHORT).show();


        if (hit1>0) {

            //Toast.makeText(this, "Barcode sudah pernah ada", Toast.LENGTH_SHORT).show();
            DialogExistitem();
        }else
        {
             final String namacari = cari1.getText().toString();

            String insertSQL = "INSERT INTO tbl_barctran \n" +
                    "(barcode,noref,lokoven,namaoven,typetran,temp1,temp2,temp3,temp4,temp5,tagtran,namaoven2 )\n" +
                    "VALUES \n" +
                    "(?, ?, ? ,?,?,?,?,?,?,?,?,?);";


            mDatabase.execSQL(insertSQL, new String[]{item1,noref, lokoven, namaoven, a1,"t1","t2","t3","t4","t5","1",editoven1});


            Toast.makeText(this, "Add Barcode Succesfully", Toast.LENGTH_SHORT).show();
        }


    }

     private void loadDataLokal(){

     /*   Intent intent=getIntent();
        final String kodecabang1=intent.getStringExtra("kodecabang");
        final String namacari=cari1.getText().toString();*/


         String item1=cari1.getText().toString();
         String lokoven=textTampung2.getText().toString();
         String namaoven=textTampung3.getText().toString();
         String noref=textnoreff.getText().toString();

        t1=(TextView) findViewById(R.id.jumbarc2);

         SQLiteDatabase db=adb_master.getReadableDatabase();
         String sql="select * from tbl_barctran where noref= '"+noref+"'";
         Cursor c=db.rawQuery(sql,null);
         int jum=c.getCount();
         hitbarc=jum;

         if (jum>0)
         {

             spnOven.setEnabled(false);
             butovenname.setEnabled(false);
             editoven.setEnabled(false);

         }

         t1.setText(String.valueOf(jum));
        //  barangListPOS = adb_user.getSemuaUser();
        barangList = adb_master.getSemuaItem(noref);
      //  barangList = adb_trans.getSemuaItemtest();
        gambarDatakeRecyclerView();



    }


    private void retrieveOven2(){

      //  Intent intent=getIntent();
       //final String kodecabang1=intent.getStringExtra("kodecabang");


        final String namacari=cari1.getText().toString();
        String url = AppConfig.IP_SERVER+"sawmill/barcsw_ovenloc2.php";
        //String url ="http://golden-care.co.id/android/listdata.php";
        pDialog.setMessage("Retieve Data Barang...");
        //    Toast.makeText(this,"test masuk load server "+kodecabang1,Toast.LENGTH_LONG).show();
        showDialog();

        StringRequest postRequest = new StringRequest(Request.Method.POST, url,new Response.Listener<String>() {

            @Override

            public void onResponse(String response) {

                Log.d("MainActivity bedul kubu","response:"+response);

                hideDialog();



                processResponse4(response);

                gambarDatakeRecyclerView();

            }

        },

                new Response.ErrorListener() {

                    @Override

                    public void onErrorResponse(VolleyError error) {

                        // pDialog.setMessage("Connection failed");

                        hideDialog();

                        if (error instanceof NetworkError) {
                            DialogKoneksi();
                        } else if (error instanceof ServerError) {

                            DialogKoneksi();
                        } else if (error instanceof AuthFailureError) {
                            DialogKoneksi();
                        } else if (error instanceof ParseError) {
                            DialogKoneksi();
                        } else if (error instanceof NoConnectionError) {

                            DialogKoneksi();
                        } else if (error instanceof TimeoutError) {

                            DialogKoneksi();
                        }




                        error.printStackTrace();

                    }

                }

        ) {

            @Override

            protected Map<String, String> getParams()

            {

                Map<String, String>  params = new HashMap<>();

                // the POST parameters:

                params.put("idoven",textTampung.getText().toString());
                //    params.put("kodecab",kodecabang1);

                return params;

            }

        };
        postRequest.setRetryPolicy(new DefaultRetryPolicy(100 * 1000, 1, 1.0f));
        Volley.newRequestQueue(this).add(postRequest);

    }

    private void processResponse4(String response){

        try {

            JSONObject obj = new JSONObject(response);
            // if(obj.optString("status").equals("true")){

            barangCabangArrayList = new ArrayList<>();
            JSONArray dataArray  = obj.getJSONArray("data");

            for (int i = 0; i < dataArray.length(); i++) {

                BarangMasterUser playerModel = new  BarangMasterUser();
                JSONObject dataobj = dataArray.getJSONObject(i);

                textTampung2.setText(String.valueOf(dataobj.getString("idno")));

               // playerModel.setIdoven(Integer.valueOf(dataobj.getString("idno")));
               // playerModel.setOvenname(dataobj.getString("section"));
                //barangCabangArrayList.add(playerModel);

            }








            try {

                //   mPicture = getPictureCallback();
                //   mCamera.takePicture(null, null, mPicture);

            } catch (Exception e1) {
                e1.printStackTrace();
                //texterror.setText("nosupport");
            }






            SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyyyy");
            String date = dateFormat.format(new Date());















            hideDialog();
            // finish();





        } catch (JSONException e) {

            Log.d("BarangActivity", "errorJSON");

        }

    }



    private void DialogExistitem() {
        dialog = new AlertDialog.Builder(this);
        inflater = getLayoutInflater();
        dialogView = inflater.inflate(R.layout.post_warningitem, null);
        dialog.setView(dialogView);
        //  dialog.setCancelable(true);
        dialog.setIcon(R.mipmap.ic_launcher);
        String w1="Item : "+cari1.getText().toString();
        dialog.setTitle(w1);

        //  txt_nama    = (EditText) dialogView.findViewById(R.id.txt_nama);
        // txt_usia    = (EditText) dialogView.findViewById(R.id.txt_usia);
        //txt_alamat  = (EditText) dialogView.findViewById(R.id.txt_alamat);
        //txt_status = (EditText) dialogView.findViewById(R.id.txt_status);

        // kosong();

        dialog.setPositiveButton("Close", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                //    nama    = txt_nama.getText().toString();
                //  usia    = txt_usia.getText().toString();
                //alamat  = txt_alamat.getText().toString();
                //status = txt_status.getText().toString();
                // deltrans();
                // inserttoserver();
                //Intent bukadialog = new Intent(NURUploadBarcode.this,NURpono.class);
                //   final String notrans3=notrans.getText().toString();
                //   Toast.makeText(getBaseContext(),"No Transaksi: "+notrans3, Toast.LENGTH_SHORT).show();
               // bukadialog.putExtra("posno","posno");
               // startActivityForResult(bukadialog, REQUEST_CODE_EDIT);
                //txt_hasil.setText("Nama : " + nama + "\n" + "Usia : " + usia + "\n" + "Alamat : " + alamat + "\n" + "Status : " + status);
                dialog.dismiss();
                //  finish();
                return;
            }
        });

      /*  dialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });*/

        dialog.show();
    }




    private void DialogDelNoref(final String norefxj) {
        dialog = new AlertDialog.Builder(this);
        inflater = getLayoutInflater();
        dialogView = inflater.inflate(R.layout.post_warningdelbarctran, null);
        dialog.setView(dialogView);
        //  dialog.setCancelable(true);
        dialog.setIcon(R.mipmap.ic_launcher);

        String d1="No Ref "+norefxj;
        dialog.setTitle(d1);

        //  txt_nama    = (EditText) dialogView.findViewById(R.id.txt_nama);
        // txt_usia    = (EditText) dialogView.findViewById(R.id.txt_usia);
        //txt_alamat  = (EditText) dialogView.findViewById(R.id.txt_alamat);
        //txt_status = (EditText) dialogView.findViewById(R.id.txt_status);

        // kosong();

        dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                //    nama    = txt_nama.getText().toString();
                //  usia    = txt_usia.getText().toString();
                //alamat  = txt_alamat.getText().toString();
                //status = txt_status.getText().toString();
                // deltrans();
                //  sqldelnoref(textnoreff.getText().toString());
                //  inserttoserver(textnoreff.getText().toString());
                //  Intent bukadialog = new Intent(NURUploadBarcode.this,NURpono.class);
                //   final String notrans3=notrans.getText().toString();
                //   Toast.makeText(getBaseContext(),"No Transaksi: "+notrans3, Toast.LENGTH_SHORT).show();
                //    bukadialog.putExtra("posno","posno");
                // startActivityForResult(bukadialog, REQUEST_CODE_EDIT);
                //txt_hasil.setText("Nama : " + nama + "\n" + "Usia : " + usia + "\n" + "Alamat : " + alamat + "\n" + "Status : " + status);
                // sqldelnoref(textnoreff.getText().toString());
                //  inserttoserver(textnoreff.getText().toString());
              // delbarctran_noref(norefxj);
               sqldelnoref(norefxj);

            //    finish();
                dialog.dismiss();
                //  finish();
                return;
            }
        });

        dialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }



    private void DialogDelBarcTran(String barc, final Integer gidno) {
        dialog = new AlertDialog.Builder(this);
        inflater = getLayoutInflater();
        dialogView = inflater.inflate(R.layout.post_warningdelbarctran, null);
        dialog.setView(dialogView);
        //  dialog.setCancelable(true);
        dialog.setIcon(R.mipmap.ic_launcher);

        String d1="Data "+barc;
        dialog.setTitle(d1);

        //  txt_nama    = (EditText) dialogView.findViewById(R.id.txt_nama);
        // txt_usia    = (EditText) dialogView.findViewById(R.id.txt_usia);
        //txt_alamat  = (EditText) dialogView.findViewById(R.id.txt_alamat);
        //txt_status = (EditText) dialogView.findViewById(R.id.txt_status);

        // kosong();

        dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                //    nama    = txt_nama.getText().toString();
                //  usia    = txt_usia.getText().toString();
                //alamat  = txt_alamat.getText().toString();
                //status = txt_status.getText().toString();
                // deltrans();
              //  sqldelnoref(textnoreff.getText().toString());
              //  inserttoserver(textnoreff.getText().toString());
                //  Intent bukadialog = new Intent(NURUploadBarcode.this,NURpono.class);
                //   final String notrans3=notrans.getText().toString();
                //   Toast.makeText(getBaseContext(),"No Transaksi: "+notrans3, Toast.LENGTH_SHORT).show();
                //    bukadialog.putExtra("posno","posno");
                // startActivityForResult(bukadialog, REQUEST_CODE_EDIT);
                //txt_hasil.setText("Nama : " + nama + "\n" + "Usia : " + usia + "\n" + "Alamat : " + alamat + "\n" + "Status : " + status);
               // sqldelnoref(textnoreff.getText().toString());
              //  inserttoserver(textnoreff.getText().toString());
                delbarctran(gidno);

                loadDataLokal();


                      sqldelnoref(textnoreff.getText().toString());
                      if (hitbarc>0) {
                          inserttoserver(textnoreff.getText().toString());
                      }

                dialog.dismiss();
                //  finish();
                return;
            }
        });

        dialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }





    private void DialogPOST() {
        dialog = new AlertDialog.Builder(this);
        inflater = getLayoutInflater();
        dialogView = inflater.inflate(R.layout.post_warning, null);
        dialog.setView(dialogView);
        //  dialog.setCancelable(true);
        dialog.setIcon(R.mipmap.ic_launcher);
        dialog.setTitle("POST Warning");

        //  txt_nama    = (EditText) dialogView.findViewById(R.id.txt_nama);
        // txt_usia    = (EditText) dialogView.findViewById(R.id.txt_usia);
        //txt_alamat  = (EditText) dialogView.findViewById(R.id.txt_alamat);
        //txt_status = (EditText) dialogView.findViewById(R.id.txt_status);

        // kosong();

        dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                //    nama    = txt_nama.getText().toString();
                //  usia    = txt_usia.getText().toString();
                //alamat  = txt_alamat.getText().toString();
                //status = txt_status.getText().toString();
               // deltrans();
                sqldelnoref(textnoreff.getText().toString());
                inserttoserver(textnoreff.getText().toString());
              //  Intent bukadialog = new Intent(NURUploadBarcode.this,NURpono.class);
             //   final String notrans3=notrans.getText().toString();
             //   Toast.makeText(getBaseContext(),"No Transaksi: "+notrans3, Toast.LENGTH_SHORT).show();
            //    bukadialog.putExtra("posno","posno");
               // startActivityForResult(bukadialog, REQUEST_CODE_EDIT);
                //txt_hasil.setText("Nama : " + nama + "\n" + "Usia : " + usia + "\n" + "Alamat : " + alamat + "\n" + "Status : " + status);
                dialog.dismiss();
                //  finish();
                return;
            }
        });

        dialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }



    private void sqldelnoref(final String norefs)
    {



    /*    final String macadd1 = textgetmax.getText().toString();
        final String location1 = textlocation.getText().toString();
        final String absen = textabsen.getText().toString();


        if (absen.equals("Masuk."))
        {
            statusabsen="masuk";
        }else
        {
            statusabsen="keluar";
        }*/

        //  final String tglmasuk = textDate.getText().toString();
        // final String jammasuk = textTime.getText().toString();



        String url = AppConfig.IP_SERVER+"gcnew/delnorefout.php";



        StringRequest postRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

            @Override

            public void onResponse(String response) {



                Log.d("BarangActivity", "response :" + response);

                // Toast.makeText(getBaseContext(),"response: "+response, Toast.LENGTH_SHORT).show();
                //   Toast.makeText(getBaseContext(),"Hasil awal: "+macadd1+location1+absen,Toast.LENGTH_SHORT).show();

                processResponsedel(response);
               // deldatatrans();
                //loadDataServerVolley();

                hideDialog();



                //   finish();

            }

        },

                new Response.ErrorListener() {

                    @Override

                    public void onErrorResponse(VolleyError error) {

                        hideDialog();


                        if (error instanceof NetworkError) {
                            Toast.makeText(getBaseContext(),
                                    "Oops. Network Error",
                                    Toast.LENGTH_LONG).show();
                            DialogErrorNetwork();

                        } else if (error instanceof ServerError) {
                            DialogErrorServer();
                            Toast.makeText(getBaseContext(),
                                    "Oops. Server Time out",
                                    Toast.LENGTH_LONG).show();
                        } else if (error instanceof AuthFailureError) {
                        } else if (error instanceof ParseError) {
                        } else if (error instanceof NoConnectionError) {
                            DialogErrorNetwork();
                            Toast.makeText(getBaseContext(),
                                    "Oops. No Connection!",
                                    Toast.LENGTH_LONG).show();
                        } else if (error instanceof TimeoutError) {
                            DialogErrorNetwork();
                            Toast.makeText(getBaseContext(),
                                    "Oops. Timeout error!",
                                    Toast.LENGTH_LONG).show();
                        }


                        error.printStackTrace();

                    }

                }

        ) {

            @Override

            protected Map<String, String> getParams()

            {

                Map<String, String>  params = new HashMap<>();

                // the POST parameters:

                params.put("idno",norefs);
                params.put("noref",norefall);


                // params.put("tglmasuk",tglmasuk);
                // params.put("jammasuk",jammasuk);

                // params.put("nama",nama1);



/*
                if (action_flag.equals("add")){

                    params.put("id","0");

                }else{
                    //for add setup saving bro...
                    params.put("id","99");

                }
*/
                return params;

            }

        };

        Volley.newRequestQueue(this).add(postRequest);


    }

    private void processResponsedel(String response){

        try {


            JSONObject jsonObj = new JSONObject(response);
/*
            String errormsg = jsonObj.getString("errormsg");
            String nama = jsonObj.getString("nama");
            String section = jsonObj.getString("section");
            //  String waktu = jsonObj.getString("waktu");
            Toast.makeText(getBaseContext(),"Att : "+nama+' '+section+" Data Save Succesfully",Toast.LENGTH_LONG).show();

*/


            String jumhit = jsonObj.getString("jumhit");
jumjumbar=jumhit;

        } catch (JSONException e) {

            Log.d("BarangActivity", "errorJSON");

        }
loaddatabarcode();
    }


    private void sqlserverinsert(final String t0,final String t1,final String t2,final String t3,final String t4,final String t5,final String t6)
    {



    /*    final String macadd1 = textgetmax.getText().toString();
        final String location1 = textlocation.getText().toString();
        final String absen = textabsen.getText().toString();


        if (absen.equals("Masuk."))
        {
            statusabsen="masuk";
        }else
        {
            statusabsen="keluar";
        }*/

        //  final String tglmasuk = textDate.getText().toString();
        // final String jammasuk = textTime.getText().toString();



        String url = AppConfig.IP_SERVER+"sawmill/barcsw_insertbarctran.php";



        StringRequest postRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

            @Override

            public void onResponse(String response) {



                Log.d("BarangActivity", "response :" + response);

                // Toast.makeText(getBaseContext(),"response: "+response, Toast.LENGTH_SHORT).show();
                //   Toast.makeText(getBaseContext(),"Hasil awal: "+macadd1+location1+absen,Toast.LENGTH_SHORT).show();

            processResponse(response);
            // deldatatrans();
                //loadDataServerVolley();

                hideDialog();



                //   finish();

            }

        },

                new Response.ErrorListener() {

                    @Override

                    public void onErrorResponse(VolleyError error) {

                        hideDialog();


                        if (error instanceof NetworkError) {
                            Toast.makeText(getBaseContext(),
                                    "Oops. Network Error",
                                    Toast.LENGTH_LONG).show();
                            DialogErrorNetwork();

                        } else if (error instanceof ServerError) {
                            DialogErrorServer();
                            Toast.makeText(getBaseContext(),
                                    "Oops. Server Time out",
                                    Toast.LENGTH_LONG).show();
                        } else if (error instanceof AuthFailureError) {
                        } else if (error instanceof ParseError) {
                        } else if (error instanceof NoConnectionError) {
                            DialogErrorNetwork();
                            Toast.makeText(getBaseContext(),
                                    "Oops. No Connection!",
                                    Toast.LENGTH_LONG).show();
                        } else if (error instanceof TimeoutError) {
                            DialogErrorNetwork();
                            Toast.makeText(getBaseContext(),
                                    "Oops. Timeout error!",
                                    Toast.LENGTH_LONG).show();
                        }


                        error.printStackTrace();

                    }

                }

        ) {

            @Override

            protected Map<String, String> getParams()

            {

                Map<String, String>  params = new HashMap<>();

                // the POST parameters:

                params.put("barcode",t0);
                params.put("noref",t1);
                params.put("typetran",t2);
                params.put("lokoven",t3);
                params.put("namaoven",t4);
                params.put("namaoven2",t5);
                params.put("tgl_rec",t6);

                // params.put("tglmasuk",tglmasuk);
                // params.put("jammasuk",jammasuk);

                // params.put("nama",nama1);



/*
                if (action_flag.equals("add")){

                    params.put("id","0");

                }else{
                    //for add setup saving bro...
                    params.put("id","99");

                }
*/
                return params;

            }

        };

        Volley.newRequestQueue(this).add(postRequest);


    }

    private void processResponse(String response){

        try {


          JSONObject jsonObj = new JSONObject(response);
/*
            String errormsg = jsonObj.getString("errormsg");
            String nama = jsonObj.getString("nama");
            String section = jsonObj.getString("section");
            //  String waktu = jsonObj.getString("waktu");
            Toast.makeText(getBaseContext(),"Att : "+nama+' '+section+" Data Save Succesfully",Toast.LENGTH_LONG).show();

*/





        } catch (JSONException e) {

            Log.d("BarangActivity", "errorJSON");

        }

    }

    private void DialogErrorNetwork() {
        dialog = new AlertDialog.Builder(NURUploadBarcodeBatch.this);
        inflater = getLayoutInflater();
        dialogView = inflater.inflate(R.layout.error_network, null);
        dialog.setView(dialogView);
        //  dialog.setCancelable(true);
        dialog.setIcon(R.mipmap.ic_launcher);
        dialog.setTitle("Network Error");

        //  txt_nama    = (EditText) dialogView.findViewById(R.id.txt_nama);
        // txt_usia    = (EditText) dialogView.findViewById(R.id.txt_usia);
        //txt_alamat  = (EditText) dialogView.findViewById(R.id.txt_alamat);
        //txt_status = (EditText) dialogView.findViewById(R.id.txt_status);

        // kosong();

        dialog.setPositiveButton("Close", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                //    nama    = txt_nama.getText().toString();
                //  usia    = txt_usia.getText().toString();
                //alamat  = txt_alamat.getText().toString();
                //status = txt_status.getText().toString();

                //txt_hasil.setText("Nama : " + nama + "\n" + "Usia : " + usia + "\n" + "Alamat : " + alamat + "\n" + "Status : " + status);
                dialog.dismiss();
              //  finish();
            }
        });
/*
        dialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });*/

        dialog.show();
        return;
    }




    private void DialogErrorServer() {
        dialog = new AlertDialog.Builder(NURUploadBarcodeBatch.this);
        inflater = getLayoutInflater();
        dialogView = inflater.inflate(R.layout.error_server, null);
        dialog.setView(dialogView);
        //  dialog.setCancelable(true);
        dialog.setIcon(R.mipmap.ic_launcher);
        dialog.setTitle("Server Error");

        //  txt_nama    = (EditText) dialogView.findViewById(R.id.txt_nama);
        // txt_usia    = (EditText) dialogView.findViewById(R.id.txt_usia);
        //txt_alamat  = (EditText) dialogView.findViewById(R.id.txt_alamat);
        //txt_status = (EditText) dialogView.findViewById(R.id.txt_status);

        // kosong();

        dialog.setPositiveButton("Close", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                //    nama    = txt_nama.getText().toString();
                //  usia    = txt_usia.getText().toString();
                //alamat  = txt_alamat.getText().toString();
                //status = txt_status.getText().toString();

                //txt_hasil.setText("Nama : " + nama + "\n" + "Usia : " + usia + "\n" + "Alamat : " + alamat + "\n" + "Status : " + status);
                dialog.dismiss();
                finish();
            }
        });
/*
        dialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });*/

        dialog.show();
    }



    private void inserttoserver(final String norefx){
        //del transaction


        SQLiteDatabase db=adb_master.getWritableDatabase();
        String sql1="select barcode,noref,typetran,lokoven,namaoven,namaoven2,tgl_rec from tbl_barctran where noref='"+norefx+"'";
    Cursor cursor = db.rawQuery(sql1,null);
         if (cursor.moveToFirst()){
             do {
            //    BarangDialog userin = new BarangDialog(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getDouble(3));
              //  userList2.add(userin);
             sqlserverinsert(cursor.getString(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4),cursor.getString(5),cursor.getString(6));
             //Toast.makeText(getBaseContext(),"Att : "+cursor.getString(1),Toast.LENGTH_SHORT).show();
            } while (cursor.moveToNext());
        }
        cursor.moveToFirst();
        Toast.makeText(getBaseContext(),"Completed Transfer "+cursor.getString(1),Toast.LENGTH_SHORT).show();
        updatebarctran(norefx);
      //  return userList2;


  //    loadDataServerVolley();
     // Toast.makeText(getBaseContext(),"Upload Completed",Toast.LENGTH_SHORT).show();
}

private void deldatatrans()
{
    SQLiteDatabase db=adb_master.getWritableDatabase();
    String del1="delete from tbl_barang";
    db.execSQL(del1);
}

    private void delbarctran_noref(String norefx)
    {
        SQLiteDatabase db=adb_master.getWritableDatabase();
        String del1="delete from tbl_barctran where noref='"+norefx+"'";
        db.execSQL(del1);
    }

    private void delbarctran(Integer idno)
    {
        SQLiteDatabase db=adb_master.getWritableDatabase();
        String del1="delete from tbl_barctran where idno="+idno;
        db.execSQL(del1);
    }
    private void updatebarctran(final String norefx)
    {
        SQLiteDatabase db=adb_master.getWritableDatabase();
        String upd1="update tbl_barctran set tagtran=2 where noref='"+norefx+"'";
        db.execSQL(upd1);
    }

    private void DialogKoneksi() {
        dialog = new AlertDialog.Builder(this);
        inflater = getLayoutInflater();
        dialogView = inflater.inflate(R.layout.koneksi2_warning, null);
        dialog.setView(dialogView);
        dialog.setCancelable(true);
        dialog.setIcon(R.mipmap.ic_launcher);
        dialog.setTitle("Connection Info");

        //  txt_nama    = (EditText) dialogView.findViewById(R.id.txt_nama);
        // txt_usia    = (EditText) dialogView.findViewById(R.id.txt_usia);
        //txt_alamat  = (EditText) dialogView.findViewById(R.id.txt_alamat);
        //txt_status = (EditText) dialogView.findViewById(R.id.txt_status);

        // kosong();

        dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                //    nama    = txt_nama.getText().toString();
                //  usia    = txt_usia.getText().toString();
                //alamat  = txt_alamat.getText().toString();
                //status = txt_status.getText().toString();

                //txt_hasil.setText("Nama : " + nama + "\n" + "Usia : " + usia + "\n" + "Alamat : " + alamat + "\n" + "Status : " + status);
                //   Intent balikretry=new Intent();
                //  balikretry.putExtra("balikretry","ulang");
                ///    setResult(RESULT_OK,balikretry);

                finish();
                dialog.dismiss();
                //  finish();
                return;
            }
        });


        dialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });


        dialog.show();
    }





    private void showDialog() {

        if (!pDialog.isShowing())

            pDialog.show();

    }

    private void hideDialog() {

        if (pDialog.isShowing())

            pDialog.dismiss();

    }

    @Override

    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.

       getMenuInflater().inflate(R.menu.menu_inventory_master, menu);

       return true;

    }

    @Override

    public boolean onOptionsItemSelected(MenuItem item) {

        // Handle action bar item clicks here. The action bar will

        // automatically handle clicks on the Home/Up button, so long

        // as you specify a parent activity in AndroidManifest.xml.

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        if (id == R.id.action_close) {
            Intent intent=getIntent();
            final String kodecabang1=intent.getStringExtra("kodecabang");
            final String namacabang1=intent.getStringExtra("namacabang");
            final String groupho1=intent.getStringExtra("groupho");
            final String username1=intent.getStringExtra("username");

            Intent balik4=new Intent();
            balik4.putExtra("kodecab",kodecabang1);
            balik4.putExtra("namacab",namacabang1);
            balik4.putExtra("user1",username1);
            balik4.putExtra("groupho",groupho1);
            setResult(RESULT_OK,balik4);
             finish();
            return true;

        }

        return super.onOptionsItemSelected(item);

    }


    @Override
    public void onBackPressed() {
        //Toast.makeText(this,"BackButton",Toast.LENGTH_SHORT).show();

        Intent intent2=getIntent();
        final String kodecabang11=intent2.getStringExtra("kodecabang");
        final String namacabang11=intent2.getStringExtra("namacabang");
        final String groupho11=intent2.getStringExtra("groupho");
        final String username11=intent2.getStringExtra("username");

        Intent balik4=new Intent();
        balik4.putExtra("kodecab",kodecabang11);
        balik4.putExtra("namacab",namacabang11);
        balik4.putExtra("user1",username11);
        balik4.putExtra("groupho",groupho11);
        setResult(RESULT_OK,balik4);
        finish();



    }


    private void printWebViewsales() {
      //  final String webhasil1=hasil1.getText().toString();
        //final String webhasil2=hasil2.getText().toString();

        WebView webView = new WebView(this);
        webView.setWebViewClient(new WebViewClient() {

            public boolean shouldOverrideUrlLoading(WebView view,
                                                    WebResourceRequest request)
            {
                pDialog.setMessage("Loading Data ...");
                showDialog();
                return false;
            }

            @Override
            public void onPageFinished(WebView view, String url)
            {
                hideDialog();
                createWebPrintJob(view);
                myWebView = null;
            }
        });

        String htmlDocument =
                "<html><body><h1>Android Print Test</h1><p>"
                        + "This is some sample content.</p></body></html>";

        // webView.loadDataWithBaseURL("detik.com", htmlDocument,

        //  "text/HTML", "UTF-8", null);
       /* webView.getSettings().setSupportZoom(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setDisplayZoomControls(false);
        // Baris di bawah untuk menambahkan scrollbar di dalam WebView-nya
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl("https://www.codepolitan.com");*/
        //pDialog = new ProgressDialog(Transaksi_POS.this);
        //pDialog.setMessage("Loading Data ...");
        //pDialog.show();
        //Toast.makeText(getBaseContext(),"No Transaksi: "+notrans2, Toast.LENGTH_SHORT).show();
        Intent intent=getIntent();
        final String kodecabang1=intent.getStringExtra("kodecabang");
        String javascript="javascript:document.getElementsByName('viewport')[0].setAttribute('content', 'initial-scale=1.0,maximum-scale=10.0');";
        webView.loadUrl(AppConfig.IP_SERVER+"gcnew/reportsalesgroup.php?noref="+noref);
        myWebView = webView;

    }

    private void createWebPrintJob(WebView webView) {

        PrintManager printManager = (PrintManager) this
                .getSystemService(Context.PRINT_SERVICE);

        PrintDocumentAdapter printAdapter =
                webView.createPrintDocumentAdapter("MyDocument");

        String jobName = getString(R.string.app_name) + " Print Test";

        printManager.print(jobName, printAdapter,
                new PrintAttributes.Builder().build());

        // printJobs.add(printJob);
/*
        // Get a PrintManager instance
        PrintManager printManager = (PrintManager) getActivity()
                .getSystemService(Context.PRINT_SERVICE);
        PrintManager printManager = (PrintManager) Transaksi_POS.getSystemService(Context.PRINT_SERVICE);

        String jobName = getString(R.string.app_name) + " Document";

        // Get a print adapter instance
        PrintDocumentAdapter printAdapter = webView.createPrintDocumentAdapter(jobName);

        // Create a print job with name and adapter instance
        PrintJob printJob = printManager.print(jobName, printAdapter,
                new PrintAttributes.Builder().build());

        // Save the job object for later status checking
        printJobs.add(printJob);
        */

    }

}
