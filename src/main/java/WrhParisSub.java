package com.example.barcodegcnew;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static android.R.layout.simple_spinner_item;

public class WrhParisSub extends AppCompatActivity {

    public static final String TAG_JSON_ARRAY="result";
    private RecyclerView mRecyclerView;

    private RecyclerView mRecyclerViewpos;
    private BarangAdapterCabangImport rvAdapterpos;

    private BarangAdapterNOREF rvAdapter;

    private BarangCabang barang;

    private ArrayList<BarangMasterUser> barangCabangArrayList;
    private ArrayList<String> names = new ArrayList<String>();

    private RecyclerView.LayoutManager mLayoutManager;

    private Context context = WrhParisSub.this;

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
    private static String url2,barcodehasil;
    FloatingActionButton fab;
private String wrhtype_get;

    Button butprocwrh,butprocwrh2;
    EditText caribarcode;
    private static String item_code,qtygc,item_desc,user_name,idno,noref,kondisi;

TextView jumbarcode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wrh_parissubutama);
        pDialog = new ProgressDialog(getBaseContext());


      //  Intent aj = new Intent(getBaseContext(), warning.class);
        //  Intent a=new Intent(getBaseContext(),Emp_RegItemdesc.class);
       //startActivity(aj);


        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }



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
        textTampung = (TextView) findViewById(R.id.textTampung);
        spnOven=(Spinner) findViewById(R.id.spnOven);
jumbarcode=findViewById(R.id.jumbarcode);
       textnoreff = (TextView) findViewById(R.id.textnoref);
        textnoreff.setText(noref);


//Toast.makeText(getBaseContext(),"nilai :"+noref+"-"+item_code+"-"+kondisi+"qtygc:"+WrhParis2.qtygc,Toast.LENGTH_LONG).show();

        retrieveOven2();



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




        //ini untuk import gc
        Intent a = getIntent();
        if (a.hasExtra("barang")) {

            barang = (BarangCabang) a.getSerializableExtra("barang");
            //item_code=barang.getItem_code();
            item_code=WrhParis2Sambung.item_code;
            qtygc=WrhParis2Sambung.qtygc;
            item_desc=WrhParis2Sambung.item_desc; //pengganti itemdesc pakai barcode
            user_name=String.valueOf(barang.getUser_name());
            idno=String.valueOf(barang.getIdno());
            noref=barang.getNoref();
            kondisi=barang.getTujuan();

            android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
            toolbar.setTitle(item_code+"/"+qtygc+"/"+item_desc);
            setSupportActionBar(toolbar);


        }else
        {

            Calendar newDate = Calendar.getInstance();
            dateFormatter = new SimpleDateFormat("ddMMyyyy_HHmmss", Locale.US);
            Date currentTime = Calendar.getInstance().getTime();
            textnoreff.setText(MainActivity.user_idbuffer+dateFormatter.format(newDate.getTime()));
            noref=MainActivity.user_idbuffer+dateFormatter.format(newDate.getTime());
            item_code=WrhParis2Sambung.item_code;
            qtygc=WrhParis2Sambung.qtygc;
            item_desc=WrhParis2Sambung.item_desc;
kondisi=textTampung.getText().toString();
            android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
            toolbar.setTitle(item_code+"/"+qtygc+"/"+item_desc);
            setSupportActionBar(toolbar);
        }




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

                if (wrhtype_get.equals("import")) {

                    Intent a = new Intent(getBaseContext(), NURUploadBarcodeNew.class);
                    //  Intent a=new Intent(getBaseContext(),Emp_RegItemdesc.class);
                    a.putExtra("wrhtype", wrhtype_get);
                    startActivityForResult(a, 111);
                }else
                {
                    Intent a = new Intent(getBaseContext(), NURUploadBarcodeNew2.class);
                    //  Intent a=new Intent(getBaseContext(),Emp_RegItemdesc.class);
                    a.putExtra("wrhtype", wrhtype_get);
                    startActivityForResult(a, 111);
                }
               // Toast.makeText(getBaseContext(),"Posisi no: ", Toast.LENGTH_SHORT).show();

            }
        });







       fab = (FloatingActionButton) findViewById(R.id.fab);


        fab.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View view) {


                //Toast.makeText(getBaseContext(), "Load barcode", Toast.LENGTH_LONG).show();
                IntentIntegrator integrator = new IntentIntegrator(WrhParisSub.this);
                integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
                integrator.setCaptureActivity(AnyOrientationCaptureActivity.class);
                integrator.setCaptureActivity(TorchOnCaptureActivity.class);
                integrator.setOrientationLocked(true);
                integrator.setPrompt("Scan a barcode PT. Paradise");
                integrator.setCameraId(0);  // Use a specific camera of the device
                integrator.setBeepEnabled(true);
                integrator.initiateScan();

            }

        });


       // retrieveOven2();
        loaddatabarcode();
        Toast.makeText(getBaseContext(),"Loading .."+item_code+"-"+noref+"-"+kondisi, Toast.LENGTH_SHORT).show();
    }



    private void retrieveOven2(){

        //  Intent intent=getIntent();
        //final String kodecabang1=intent.getStringExtra("kodecabang");


      //  final String namacari=cari1.getText().toString();
        String url = AppConfig.IP_SERVER+"gcnew/barcsw_ovenloc2.php";
        //String url ="http://golden-care.co.id/android/listdata.php";
        pDialog.setMessage("Retieve Kondisi...");
        //    Toast.makeText(this,"test masuk load server "+kodecabang1,Toast.LENGTH_LONG).show();
        showDialog();

        StringRequest postRequest = new StringRequest(Request.Method.POST, url,new Response.Listener<String>() {

            @Override

            public void onResponse(String response) {

                Log.d("MainActivity bedul kubu","response:"+response);

                hideDialog();



                processResponse4(response);

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

    private void processResponse4(String response){

        try {

            JSONObject obj = new JSONObject(response);
            // if(obj.optString("status").equals("true")){

            barangCabangArrayList = new ArrayList<>();
            JSONArray dataArray  = obj.getJSONArray("data");

            for (int i = 0; i < dataArray.length(); i++) {

                BarangMasterUser playerModel = new  BarangMasterUser();
                JSONObject dataobj = dataArray.getJSONObject(i);

            // textTampung.setText(String.valueOf(dataobj.getString("kondisi")));

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

            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(getBaseContext(), simple_spinner_item, names);
            spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
            spnOven.setAdapter(spinnerArrayAdapter);
            //  removeSimpleProgressDialog();
           textTampung.setText(kondisi);
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

        dialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

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


    private void DialogErrorNetwork() {
        dialog = new AlertDialog.Builder(WrhParisSub.this);
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
        dialog = new AlertDialog.Builder(WrhParisSub.this);
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
        return;
    }



    private void sqldelnoref(final String norefxj)
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



        String url = AppConfig.IP_SERVER+"gcnew/delnoref.php";



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

                params.put("idno",norefxj);


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





        } catch (JSONException e) {

            Log.d("BarangActivity", "errorJSON");

        }
        loaddatabarcode();
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
                //   delbarctran_noref(norefxj);
                sqldelnoref(norefxj);
                Intent a=new Intent();
                setResult(RESULT_OK,a);
               // finish();

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




    private void gambarDatakeRecyclerView(){

        rvAdapterpos = new BarangAdapterCabangImport(barangListPOS);

        mRecyclerView.setAdapter(rvAdapterpos);

        //  final String test1=ambilsw1.getText().toString();


        mRecyclerView.addOnItemTouchListener(new RecyclerItemListener(context, new RecyclerItemListener.OnItemClickListener() {

                    @Override

                    public void onItemClick(View view, int position) {



                        BarangCabang barang = rvAdapterpos.getItem(position);

                        Toast.makeText(getBaseContext(),"Posisi no: "+barang.getIdno(), Toast.LENGTH_SHORT).show();

                        DialogDelNoref(String.valueOf(barang.getIdno()));

                    /*    if (wrhtype_get.equals("import")) {

                            Intent intent = new Intent(getBaseContext(), NURUploadBarcodeNew.class);
                            intent.putExtra("barang", barang);

                            startActivityForResult(intent, REQUEST_CODE_EDIT);
                        }else
                        {
                            Intent intent = new Intent(getBaseContext(), NURUploadBarcodeNew2.class);
                            intent.putExtra("barang", barang);

                            startActivityForResult(intent, REQUEST_CODE_EDIT);
                        }*/
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
     //  Toast.makeText(getBaseContext(),"Loading .."+item_code+"-"+noref+"-"+kondisi, Toast.LENGTH_SHORT).show();


          url2 = AppConfig.IP_SERVER + "gcnew/android_listbarcode2item.php";

        //String url ="http://golden-care.co.id/android/listdata.php";
//        pDialog = new ProgressDialog(Transaksi_POS.this);
          pDialog.setMessage("Retieve Data Barang...");
        showDialog();
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
                params.put("item_code",item_code);
                params.put("noref",noref);
                params.put("kondisi",kondisi);
                params.put("cari",caribarcode.getText().toString());

               // params.put("cari",caribarcode.getText().toString());
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

           // SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy", Locale.US);



            JSONArray jsonArray = jsonObj.getJSONArray("data");

            String jumhit1 = jsonObj.getString("jumhit");
            jumbarcode.setText("Jumlah Barcode : " +jumhit1);

            for(int i = 0; i < jsonArray.length(); i++) {

                JSONObject obj = jsonArray.getJSONObject(i);

                objectbarang = new BarangCabang();
                 objectbarang.setIdno(obj.getString("idno"));
                objectbarang.setBarcode(obj.getString("barcode"));
                objectbarang.setTgl_rec((obj.getString("tgl_rec")));
                objectbarang.setUser_name(obj.getString("user_name"));
                objectbarang.setItem_code(obj.getString("item_code"));
                objectbarang.setItem_desc(obj.getString("item_desc"));
              //  objectbarang.setTypetran(obj.getString("typetran"));
                objectbarang.setKondisi(obj.getString("kondisi"));
                barangListPOS.add(objectbarang);

             //   objectbarang.setRemarks(obj.getString("remarks"));
              //  objectbarang.setQtygc(obj.getDouble("qty"));

        /*        if (wrhtype_get.equals("import")) {
                    String s="";
                }else
                {
                    objectbarang.setTujuan(obj.getString("tujuan"));
                }

                barangListPOS.add(objectbarang);
*/

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



    public void saveDataBarcode() {

        // refreshFlag="1";

        final Intent intent = getIntent();

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
        String urlx = AppConfig.IP_SERVER+"gcnew/savedatagcimport.php";

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

                params.put("barcode", barcodehasil);
                params.put("typetran","import");
                params.put("qty",qtygc );
                params.put("item_code", item_code);
                params.put("item_desc", item_desc);
                params.put("remarks", "remarks");
                params.put("user_name", MainActivity.user_idbuffer);
                params.put("kondisi", textTampung.getText().toString());
                params.put("savetag", "1");
                params.put("noref", textnoreff.getText().toString());
             //   params.put("idno",idno);

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
            String jumhit1 = jsonObj.getString("jumhit");



            if (errormsg.equals("failed")) {

                Intent a=new Intent(getBaseContext(),warningumum.class);
                a.putExtra("warning","Barcode Sudah Pernah ada");
                startActivity(a);


                // finish();
            }else
            {

                 jumbarcode.setText("Jumlah Barcode :" +jumhit1);

                 if (jumhit1=="0")
                 {


                     new CountDownTimer(1100, 1000) {

                         public void onTick(long millisUntilFinished) {
                             //  totalrp.setText("seconds remaining: " + millisUntilFinished / 1000);
                         }

                         public void onFinish() {
                             //mTextField.setText("done!");
                             butrefresh.performClick();

                         }
                     }.start();


                 }

               MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.bell);
               mediaPlayer.start();

                 //    fab.performClick();


            }

            hideDialog();
        } catch (JSONException e) {

            //  Log.d("Trnsakais POS2", "errorJSON");

        }


        loaddatabarcode();

    }


    private void DialogPerson() {
        dialog = new AlertDialog.Builder(this);
        inflater = getLayoutInflater();
        dialogView = inflater.inflate(R.layout.person_warning, null);
        dialog.setView(dialogView);
        //  dialog.setCancelable(true);
        dialog.setIcon(R.mipmap.ic_launcher);
        dialog.setTitle("Security Warning");

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
                return;
            }
        });

    }


    //barcode area
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        // IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);

        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);




        if (scanningResult != null) {

            // textTampung3.setText(String.valueOf(data.getStringExtra("idno")));
            //insertdata();

            if (scanningResult.getContents() != null) {
                //   Toast.makeText(this, "hASIL cANNING"+scanningResult.getContents(), Toast.LENGTH_SHORT).show();
            //    cari1.setText("");
              // caribarcode.setText(scanningResult.getContents());
                barcodehasil=scanningResult.getContents();

                //NURUploadBarcodeBatch.lastscan=barcodehasil;
                if (barcodehasil.length()==9||barcodehasil.length()==10)
                {
                   saveDataBarcode();
                }else
                {
                    Intent a=new Intent(getBaseContext(),warningumum.class);
                    a.putExtra("warning","Barcode Salah Format");
                    startActivity(a);
                    return;
                }

              //  fab.performClick();
                //  loadDataLokal();
                //   gambarDatakeRecyclerView();
                //we have a result
                //cari1.setText(scanningResult.getContents());
                // textTampung3.setText(String.valueOf(data.getStringExtra("idno")));
              //  Toast.makeText(getBaseContext(),"test hasil"+requestCode,Toast.LENGTH_LONG).show();

          //  fab.performClick();


                //      loaddatavolley();
                //   gambarDatakeRecyclerView();
              //  caridatavolley();

            }
        }

        // EditText tvResult = (EditText) findViewById(R.id.txtEditResult);
        //TextView tvResult2=(TextView) findViewById(R.id.hasilspin3);


       // if (resultCode == RESULT_OK ) {

            // if (intent.getStringExtra("refreshflag").equals("1")) {

          //  loaddatabarcode();

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