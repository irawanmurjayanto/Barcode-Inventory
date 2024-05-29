package com.example.barcodegcnew;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
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
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
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

import static android.R.layout.simple_spinner_item;


public class NURUploadBarcodeLokal extends AppCompatActivity {
    public static final String TAG_JSON_ARRAY = "result";
    private RecyclerView mRecyclerView;
    private BarangBarcodeNOREFLokal barang;
    private BarangAdapter rvAdapter;

    private ArrayList<BarangMasterUser> barangCabangArrayList;
    private ArrayList<String> names = new ArrayList<String>();

    private RecyclerView.LayoutManager mLayoutManager;

    private Context context = NURUploadBarcodeLokal.this;

    private static final int REQUEST_CODE_ADD = 1;

    private static final int REQUEST_CODE_EDIT = 2;

    private List<BarangBarcode> barangList = new ArrayList<BarangBarcode>();
    private List<BarangBarcodeNOREFLokal> barangListNoref = new ArrayList<BarangBarcodeNOREFLokal>();
    private ADB_Master adb_master;
    private ADB_Trans adb_trans;

    private ProgressDialog pDialog;
    private EditText cari1, editoven;

    AlertDialog.Builder dialog;
    LayoutInflater inflater;
    View dialogView;
    ImageButton butbarcodeher;
    private static final String DATABASE_NAME = "master.db";
    SQLiteDatabase mDatabase;
    TextView t0, t1, textTampung, textTampung2, textTampung3, textnoreff;
    Button butpos1, butovenname, butmanualbarc, butmanualbarcdel, butdelbarctran, butdelnoref, buterror,butposttest;
    private SimpleDateFormat dateFormatter;
    Spinner spnOven;
    private String a1;
    public static int hitbarc,tot;
    String[] pilihan = {"Sales", "Putra", "Bad Stock"};
    public static String lastscan, jumjumbar, noreflokal, valprogress;
    FloatingActionButton fab;
    private static int hit;
    public static int tagdata;
    EditText editremarks;
    public static String ambilremarks, senddata,ambilnoref;
    ProgressBar simpleprogress;
    private LinearLayout my_view2;
    ViewGroup.LayoutParams params;
    View my_view;
    boolean isUp;
    TextView nilaiof;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory__baranglokal);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        spnOven = (Spinner) findViewById(R.id.spnOven);
        textTampung = (TextView) findViewById(R.id.textTampung);
        textTampung2 = (TextView) findViewById(R.id.textTampung2);
        textTampung3 = (TextView) findViewById(R.id.textTampung3);
        textnoreff = (TextView) findViewById(R.id.textnoref);
        editremarks = (EditText) findViewById(R.id.editremarks);
       // simpleprogress = findViewById(R.id.simpleProgressBar);
        //  nilaiof=findViewById(R.id.nilaiof);
        //my_view = findViewById(R.id.layprogress);
       // params = my_view.getLayoutParams();
        //  params.height=200;


       // simpleprogress.setMax(hitbarc);
       // simpleprogress.setProgress(0);

        //    toolbar.setTitle("Barcode Upload");
        //setSupportActionBar(toolbar);
        cari1 = (EditText) findViewById(R.id.cariText);
        editoven = (EditText) findViewById(R.id.editOven);
        //  butbarcodeher=(ImageButton) findViewById(R.id.butBarcodeher);
        butpos1 = (Button) findViewById(R.id.butpost2);
        butovenname = (Button) findViewById(R.id.butovenname);
        butmanualbarc = (Button) findViewById(R.id.butmanualbarc);
        butmanualbarcdel = (Button) findViewById(R.id.butmanualbarcdel);
        butdelnoref = (Button) findViewById(R.id.butdelnoref);
        butposttest = (Button) findViewById(R.id.butposttest);



        t0 = (TextView) findViewById(R.id.jumbarc1);
        t1 = (TextView) findViewById(R.id.jumbarc2);
        Calendar newDate = Calendar.getInstance();
        buterror = findViewById(R.id.buterror);
        //  newDate.set(year, monthOfYear, dayOfMonth);


        adb_master = new ADB_Master(getBaseContext());
        adb_trans = new ADB_Trans(getBaseContext());
        mDatabase = openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);


        //my_view.setVisibility(View.INVISIBLE);
        //  butpos1.setText("Slide up");
        isUp = false;

        Intent a = getIntent();













        a1 = a.getStringExtra("wrhtype");
        //Toast.makeText(getBaseContext(),a1,Toast.LENGTH_LONG).show();



        butdelnoref.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(getBaseContext(), Emp_RegErrorLokal.class);
                a.putExtra("warning", "Barcode Salah Format");
                startActivity(a);
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
                if (cari1.getText().toString().length() == 9) {
                    insertdata();
                    loadDataLokal();
                    gambarDatakeRecyclerView();
                } else {
                    Intent a = new Intent(getBaseContext(), warningumum.class);
                    a.putExtra("warning", "Barcode Salah Format");
                    startActivity(a);
                    return;

                }

            }
        });

        butovenname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent b = new Intent(getBaseContext(), Emp_RegOvenAss.class);
                b.putExtra("idoven", textTampung2.getText().toString());
                startActivityForResult(b, 222);
            }
        });

        spnOven.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // TODO Auto-generated method stub
                textTampung.setText(spnOven.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });

   /*     ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,pilihan);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spnOven.setAdapter(aa);
*/


       butpos1.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {

            if (editremarks.getText().toString().isEmpty())
            {
                ambilremarks=editremarks.getText().toString();
                ambilnoref=textnoreff.getText().toString();

                Intent a=new Intent(getBaseContext(),warningumum.class);
                a.putExtra("warning","Nama Item tidak boleh kosong");
                startActivity(a);
                return;


            }else {
               // params.height=100;
                ambilremarks=editremarks.getText().toString();
                ambilnoref=textnoreff.getText().toString();
                DialogPOST();

            }

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

                if (editremarks.getText().toString().isEmpty()) {
                    Intent a = new Intent(getBaseContext(), warningumum.class);
                    a.putExtra("warning", "Nama Item tidak boleh kosong");
                    startActivity(a);
                    return;
                } else {
                    ambilremarks = editremarks.getText().toString();
                    //Toast.makeText(getBaseContext(), "Load barcode", Toast.LENGTH_LONG).show();
                    IntentIntegrator integrator = new IntentIntegrator(NURUploadBarcodeLokal.this);
                    integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
                    integrator.setCaptureActivity(AnyOrientationCaptureActivity.class);
                    integrator.setCaptureActivity(TorchOnCaptureActivityLokal.class);
                    integrator.setOrientationLocked(false);
                    integrator.setPrompt("Scan a barcode PT. Paradise");
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
        if (a.hasExtra("barang")) {

            barang = (BarangBarcodeNOREFLokal) a.getSerializableExtra("barang");

            setData(barang);
            noreflokal = barang.getNoref();
            ambilnoref = textnoreff.getText().toString();
        } else {
            dateFormatter = new SimpleDateFormat("ddMMyyyy_HHmmss", Locale.US);
            Date currentTime = Calendar.getInstance().getTime();
            textnoreff.setText(MainActivity.user_idbuffer + dateFormatter.format(newDate.getTime()));
            noreflokal = textnoreff.getText().toString();

            //norefall=MainActivity.user_idbuffer+dateFormatter.format(newDate.getTime());
        }
        // retrieveOven();
        //retrieveSpin1();


        butposttest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertto_test(ambilnoref);
            }
        });

        buterror.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 Intent a = new Intent(getBaseContext(), Emp_RegErrorLokal.class);
                    startActivity(a);
              // updatebarctranbalik(textnoreff.getText().toString());
               // Toast.makeText(getBaseContext(),"Baliktran "+noreflokal,Toast.LENGTH_LONG).show();
            }
        });



        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(NURUploadBarcodeLokal.this, simple_spinner_item, pilihan);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
        spnOven.setAdapter(spinnerArrayAdapter);

        //  removeSimpleProgressDialog();

        String compareValue1 = textTampung.getText().toString();
        if (compareValue1 != null) {
            int spinnerPosition = spinnerArrayAdapter.getPosition(compareValue1);
            spnOven.setSelection(spinnerPosition);
        }


    }


    public void slideUp(View view) {
      //  params.height = 200;
        TranslateAnimation animate = new TranslateAnimation(
                0,                 // fromXDelta
                0,                 // toXDelta
                view.getHeight(),  // fromYDelta
                0);                // toYDelta
        animate.setDuration(500);
        animate.setFillAfter(true);
        view.startAnimation(animate);
        DialogPOST();
    }
  //  params
    // slide the view from its current position to below itself
    public void slideDown(View view) {
       // .height = 0;
        view.setVisibility(View.VISIBLE);
        TranslateAnimation animate = new TranslateAnimation(
                0,                 // fromXDelta
                0,                 // toXDelta
                0,                 // fromYDelta
                view.getHeight()); // toYDelta
        animate.setDuration(500);
        animate.setFillAfter(true);
        view.startAnimation(animate);

    }

    public void onSlideViewButtonClick(View view) {
        if (isUp) {
            // slideDown(my_view);
            butpos1.setText("Post Data+");
        } else {
            slideUp(my_view);
            butpos1.setText("Past Data");
        }
        isUp = !isUp;
    }


    private void setData(BarangBarcodeNOREFLokal barang) {

        // Locale localeID = new Locale("in", "ID");
        //NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);
        //detailHarga.setText(formatRupiah.format((double)hargarumah));

        DecimalFormat formatter = new DecimalFormat("#");
        // totalrp.setText(formatter.format(obj.getDouble("total")));

        textTampung.setText(String.valueOf(barang.getLokoven()));
        textTampung3.setText(String.valueOf(barang.getNamaoven()));
        editremarks.setText(barang.getNamaoven2());
        textnoreff.setText(barang.getNoref());
        editoven.setText(barang.getNamaoven2());
        Toast.makeText(getBaseContext(), "Tujuan :" + barang.getLokoven(), Toast.LENGTH_LONG).show();

      /*   if (Integer.valueOf(t1.getText().toString())>0)
         {
           // spnOven.setEnabled(false);
          //  butovenname.setEnabled(false);
             Toast.makeText(getBaseContext(),"t",Toast.LENGTH_LONG).show();
         }*/

        loadDataLokal();
        gambarDatakeRecyclerView();

    }


    private void retrieveSpin1() {


        //  Intent intent=getIntent();
        //final String kodecabang1=intent.getStringExtra("kodecabang");


        //final String namacari=cari1.getText().toString();
        String url = AppConfig.IP_SERVER+"hrdnew/xx.php";
        //String url ="http://golden-care.co.id/android/listdata.php";
        pDialog.setMessage("Retieve Kondisi...");
        //    Toast.makeText(this,"test masuk load server "+kodecabang1,Toast.LENGTH_LONG).show();
        showDialog();

        StringRequest postRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

            @Override

            public void onResponse(String response) {

                Log.d("MainActivity bedul kubu", "response:" + response);

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
                            DialogErrorNetwork();
                        } else if (error instanceof ServerError) {

                            DialogErrorNetwork();
                        } else if (error instanceof AuthFailureError) {
                            DialogErrorNetwork();
                        } else if (error instanceof ParseError) {
                            DialogErrorNetwork();
                        } else if (error instanceof NoConnectionError) {

                            DialogErrorNetwork();
                        } else if (error instanceof TimeoutError) {

                            DialogErrorNetwork();
                        }


                        error.printStackTrace();

                    }

                }

        ) {

            @Override

            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<>();
                //params.put("noref",MainMap2.norefall);
                // the POST parameters:

                // params.put("idoven",textTampung.getText().toString());
                //    params.put("kodecab",kodecabang1);

                return params;

            }

        };
        postRequest.setRetryPolicy(new DefaultRetryPolicy(100 * 1000, 1, 1.0f));
        Volley.newRequestQueue(this).add(postRequest);

    }

    private void processResponse42(String response) {

        try {

            JSONObject obj = new JSONObject(response);
            // if(obj.optString("status").equals("true")){

            barangCabangArrayList = new ArrayList<>();
            JSONArray dataArray = obj.getJSONArray("data");

            for (int i = 0; i < dataArray.length(); i++) {

                BarangMasterUser playerModel = new BarangMasterUser();
                JSONObject dataobj = dataArray.getJSONObject(i);

                textTampung.setText(String.valueOf(dataobj.getString("banroda")));


                //   edit1.setText(String.valueOf(dataobj.getString("rem_banroda1")));


                // playerModel.setIdoven(Integer.valueOf(dataobj.getString("kondisi")));
                // playerModel.setOvenname(dataobj.getString("kondisi"));

                //  kodecabang.setText(dataobj.getString("kode_cabang"));
                // namacabang.setText(dataobj.getString("nama"));
                                    /*
                                    playerModel.setCountry(dataobj.getString("country"));
                                    playerModel.setCity(dataobj.getString("city"));
                                    playerModel.setImgURL(dataobj.getString("imgURL"));*/

                barangCabangArrayList.add(playerModel);

            }

            //  textTampung.setText(String.valueOf(dataobj.getString("banroda")));

        /*    for (int i = 0; i < barangCabangArrayList.size(); i++){
                names.add(barangCabangArrayList.get(i).getOvenname().toString());
            }
*/
            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(NURUploadBarcodeLokal.this, simple_spinner_item, pilihan);
            spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
            spnOven.setAdapter(spinnerArrayAdapter);


            //  removeSimpleProgressDialog();

            String compareValue1 = textTampung.getText().toString();
            if (compareValue1 != null) {
                int spinnerPosition = spinnerArrayAdapter.getPosition(compareValue1);
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


    private void retrieveOven() {

        //showSimpleProgressDialog(this, "Loading...","Fetching Json",false);
        String URLstring = AppConfig.IP_SERVER+"sawmill/barcsw_ovenloc.php";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URLstring,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d("strrrrr", ">>" + response);

                        try {

                            //  Toast.makeText(getApplicationContext(), "masuk", Toast.LENGTH_SHORT).show();

                            JSONObject obj = new JSONObject(response);
                            // if(obj.optString("status").equals("true")){

                            barangCabangArrayList = new ArrayList<>();
                            JSONArray dataArray = obj.getJSONArray("data");

                            for (int i = 0; i < dataArray.length(); i++) {

                                BarangMasterUser playerModel = new BarangMasterUser();
                                JSONObject dataobj = dataArray.getJSONObject(i);

                                playerModel.setIdoven(Integer.valueOf(dataobj.getString("idno")));
                                playerModel.setOvenname(dataobj.getString("section"));

                                //  kodecabang.setText(dataobj.getString("kode_cabang"));
                                // namacabang.setText(dataobj.getString("nama"));
                                    /*
                                    playerModel.setCountry(dataobj.getString("country"));
                                    playerModel.setCity(dataobj.getString("city"));
                                    playerModel.setImgURL(dataobj.getString("imgURL"));*/

                                barangCabangArrayList.add(playerModel);

                            }

                            for (int i = 0; i < barangCabangArrayList.size(); i++) {
                                names.add(barangCabangArrayList.get(i).getOvenname().toString());
                            }

                            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(NURUploadBarcodeLokal.this, simple_spinner_item, names);
                            spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
                            spnOven.setAdapter(spinnerArrayAdapter);
                            //  removeSimpleProgressDialog();

                            String compareValue = textTampung.getText().toString();
                            if (compareValue != null) {
                                int spinnerPosition = spinnerArrayAdapter.getPosition(compareValue);
                                spnOven.setSelection(spinnerPosition);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

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


                        //displaying the error in toast if occurrs
                        // Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        // request queue

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(100 * 1000, 1, 1.0f));

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        requestQueue.add(stringRequest);
        retrieveOven2();

    }


    private void gambarDatakeRecyclerView() {

        rvAdapter = new BarangAdapter(barangList);

        mRecyclerView.setAdapter(rvAdapter);

        mRecyclerView.addOnItemTouchListener(new RecyclerItemListener(context, new RecyclerItemListener.OnItemClickListener() {

                    @Override

                    public void onItemClick(View view, int position) {

                        Intent intent2 = getIntent();
                        // final String kodecabang1=intent2.getStringExtra("kodecabang");

                        BarangBarcode barang = rvAdapter.getItem(position);


                        // Toast.makeText(getBaseContext(), "Hasil Del "+barang.getBarcode()+"--"+barang.getId(), Toast.LENGTH_SHORT).show();

                        //Intent intent = new Intent(NURUploadBarcode.this, BarangActivity.class);

                        // intent.putExtra("barang", barang);


                        //  startActivityForResult(intent, REQUEST_CODE_EDIT);
                        DialogDelBarcTran(barang.getBarcode(), barang.getId());

                    }

                })

        );

    }

    @Override

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);


        if (scanningResult != null) {

            // textTampung3.setText(String.valueOf(data.getStringExtra("idno")));
            //insertdata();

            if (scanningResult.getContents() != null) {
                //   Toast.makeText(this, "hASIL cANNING"+scanningResult.getContents(), Toast.LENGTH_SHORT).show();
                cari1.setText("");
                cari1.setText(scanningResult.getContents());
                lastscan = scanningResult.getContents();

                //  loadDataLokal();
                //   gambarDatakeRecyclerView();
                //we have a result
                //cari1.setText(scanningResult.getContents());
                // textTampung3.setText(String.valueOf(data.getStringExtra("idno")));
                if (lastscan.length() == 9 || lastscan.length() == 10) {
                    insertdata();
                    loadDataLokal();
                    gambarDatakeRecyclerView();

                } else {
                    Intent a = new Intent(getBaseContext(), warningumum.class);
                    a.putExtra("warning", "Barcode salah format");
                    startActivity(a);
                    return;
                }
            }
        }


        if (resultCode == RESULT_OK && requestCode == 222) {

            editoven.setText(data.getStringExtra("section"));
            textTampung3.setText(String.valueOf(data.getStringExtra("idno")));

            loadDataLokal();

            nilaiof.setText("0 of " + hitbarc);

        }


    }

    //ambil data sever volley
    private void insertdata() {

        String item1 = cari1.getText().toString();
        String lokoven = textTampung.getText().toString();
        String namaoven = textTampung.getText().toString();
        String noref = textnoreff.getText().toString();
        String editoven1 = editoven.getText().toString();
        int tagin = 1;

        SQLiteDatabase db2 = adb_master.getReadableDatabase();

        String selectQuery2 = "SELECT  *  FROM " + ADB_Master.MyColumns_barcode.NamaTabel + " WHERE "
                + ADB_Master.MyColumns_barcode.noref + " = '" + noref + "' and " + ADB_Master.MyColumns_barcode.barcode + " = '" + item1 + "'";
        //   String selectQuery2 = "SELECT  *  FROM " + ADB_Master.MyColumns_item.NamaTabel;
        //String selectQuery2 = "SELECT  *  FROM " + ADB_Trans.MyColumns_trans1sub.NamaTabel;
        Cursor c2 = db2.rawQuery(selectQuery2, null);
        int hit1 = c2.getCount();
        Toast.makeText(this, "hit" + hit1, Toast.LENGTH_SHORT).show();


        if (hit1 > 0) {

            //Toast.makeText(this, "Barcode sudah pernah ada", Toast.LENGTH_SHORT).show();
            // DialogExistitem();
            Intent a = new Intent(getBaseContext(), warningumum.class);
            a.putExtra("warning", "Barcode Sudah pernah di Scan");
            startActivity(a);

            return;
        } else {
            final String namacari = cari1.getText().toString();

            String insertSQL = "INSERT INTO tbl_barctran \n" +
                    "(barcode,noref,lokoven,namaoven,typetran,temp1,temp2,temp3,temp4,temp5,tagtran,namaoven2 )\n" +
                    "VALUES \n" +
                    "(?, ?, ? ,?,?,?,?,?,?,?,?,?);";


            mDatabase.execSQL(insertSQL, new String[]{item1, noref, lokoven, namaoven, a1, MainActivity.user_idbuffer, ambilremarks, "t3", "t4", "t5", "1", editoven1});

            MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.bell);
            mediaPlayer.start();
            fab.performClick();
            Toast.makeText(this, "Add Barcode Succesfully", Toast.LENGTH_SHORT).show();
        }


    }

    private void loadDataLokal() {

     /*   Intent intent=getIntent();
        final String kodecabang1=intent.getStringExtra("kodecabang");
        final String namacari=cari1.getText().toString();*/


        String item1 = cari1.getText().toString();
        String lokoven = textTampung2.getText().toString();
        String namaoven = textTampung3.getText().toString();
        String noref = textnoreff.getText().toString();

        t1 = (TextView) findViewById(R.id.jumbarc2);

        SQLiteDatabase db = adb_master.getReadableDatabase();
        String sql = "select * from tbl_barctran where noref= '" + noref + "'";
        Cursor c = db.rawQuery(sql, null);
        int jum = c.getCount();
        hitbarc = jum;
        jumjumbar = String.valueOf(hitbarc);


        if (jum > 0) {

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


    private void retrieveOven2() {

        //  Intent intent=getIntent();
        //final String kodecabang1=intent.getStringExtra("kodecabang");


        final String namacari = cari1.getText().toString();
        String url = AppConfig.IP_SERVER+"sawmill/barcsw_ovenloc2.php";
        //String url ="http://golden-care.co.id/android/listdata.php";
        pDialog.setMessage("Retieve Data Barang...");
        //    Toast.makeText(this,"test masuk load server "+kodecabang1,Toast.LENGTH_LONG).show();
        showDialog();

        StringRequest postRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

            @Override

            public void onResponse(String response) {

                Log.d("MainActivity bedul kubu", "response:" + response);

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

            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<>();

                // the POST parameters:

                params.put("idoven", textTampung.getText().toString());
                //    params.put("kodecab",kodecabang1);

                return params;

            }

        };
        postRequest.setRetryPolicy(new DefaultRetryPolicy(100 * 1000, 1, 1.0f));
        Volley.newRequestQueue(this).add(postRequest);

    }

    private void processResponse4(String response) {

        try {

            JSONObject obj = new JSONObject(response);
            // if(obj.optString("status").equals("true")){

            barangCabangArrayList = new ArrayList<>();
            JSONArray dataArray = obj.getJSONArray("data");

            for (int i = 0; i < dataArray.length(); i++) {

                BarangMasterUser playerModel = new BarangMasterUser();
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
        String w1 = "Item : " + cari1.getText().toString();
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

        String d1 = "No Ref " + norefxj;
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
                delbarctran_noref(norefxj);
                sqldelnoref(norefxj);

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


    private void DialogDelBarcTran(String barc, final Integer gidno) {
        dialog = new AlertDialog.Builder(this);
        inflater = getLayoutInflater();
        dialogView = inflater.inflate(R.layout.post_warningdelbarctran, null);
        dialog.setView(dialogView);
        //  dialog.setCancelable(true);
        dialog.setIcon(R.mipmap.ic_launcher);

        String d1 = "Data " + barc;
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


                    /*  sqldelnoref(textnoreff.getText().toString());
                      if (hitbarc>0) {
                          inserttoserver(textnoreff.getText().toString());
                      }
*/
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

             //   sqldelnoref(textnoreff.getText().toString());
              //  updatebarctran(ambilnoref);
                Intent a2 = new Intent(getBaseContext(), warningloading.class);
                startActivity(a2);


                //  inserttoserver(textnoreff.getText().toString());
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


    private void sqldelnoref(final String norefs) {



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


        String url = AppConfig.IP_SERVER+"gcnew/lokal_delnoref.php";


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

            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<>();

                // the POST parameters:

                params.put("noref", norefs);


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

    private void processResponsedel(String response) {

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


        inserttoserver(textnoreff.getText().toString());

    }


    private void sqlserverinsert(final String t0, final String t1, final String t2, final String t3, final String t4, final String t5, final String rems) {


        hit = 0;
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


        String url = AppConfig.IP_SERVER+"gcnew/lokal_insertbarctran_test.php";


        StringRequest postRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

            @Override

            public void onResponse(String response) {


                Log.d("BarangActivity", "response :" + response);

                // Toast.makeText(getBaseContext(),"response: "+response, Toast.LENGTH_SHORT).show();
                //   Toast.makeText(getBaseContext(),"Hasil awal: "+macadd1+location1+absen,Toast.LENGTH_SHORT).show();

                processResponse(response);
                // deldatatrans();
                //loadDataServerVolley();

                //    hideDialog();


                //   finish();

            }

        },

                new Response.ErrorListener() {

                    @Override

                    public void onErrorResponse(VolleyError error) {

                        hideDialog();


                        if (error instanceof NetworkError) {
                            //   Toast.makeText(getBaseContext(),
                            //   "Oops. Network Error",
                            //  Toast.LENGTH_LONG).show();
                            DialogErrorNetwork();

                        } else if (error instanceof ServerError) {
                            DialogErrorNetwork();
                            //   Toast.makeText(getBaseContext(),
                            //     "Oops. Server Time out",
                            //   Toast.LENGTH_LONG).show();
                        } else if (error instanceof AuthFailureError) {
                            DialogErrorNetwork();
                        } else if (error instanceof ParseError) {
                            DialogErrorNetwork();
                        } else if (error instanceof NoConnectionError) {
                            DialogErrorNetwork();
                            // Toast.makeText(getBaseContext(),
                            // "Oops. No Connection!",
                            //  Toast.LENGTH_LONG).show();
                        } else if (error instanceof TimeoutError) {
                            DialogErrorNetwork();
                            // Toast.makeText(getBaseContext(),
                            //  "Oops. Timeout error!",
                            // Toast.LENGTH_LONG).show();
                        }


                        error.printStackTrace();

                    }

                }

        ) {

            @Override

            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<>();

                // the POST parameters:

                params.put("barcode2", t0);
                params.put("noref", t1);
                params.put("typetran", t2);
                params.put("lokoven", t3);
                params.put("tgl_rec", t4);
                params.put("user_name", t5);
                params.put("hitbarc", String.valueOf(hitbarc));
                params.put("remarks", editremarks.getText().toString());


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
        // Volley.newRequestQueue(this).getCache().clear();



        // simpleprogress.setProgress(Integer.valueOf(valprogress));
    }

    private void processResponse(String response) {

        try {


            Log.d("TAG", "data response DATA POS: " + response);

            JSONObject jsonObj = new JSONObject(response);


            final String check1 = jsonObj.getString("check1");
            final String check2 = jsonObj.getString("check2");
            final String check3 = jsonObj.getString("check3");







          /*  final Handler handler = new Handler();
            handler.post( new Runnable(){
                private int k = 0;
*/



              //  public void run() {





                    if (check1.equals(String.valueOf(hitbarc))) {

                        Toast.makeText(getBaseContext(),"Jumlah Akhir "+check1+"--"+check2+"--"+hitbarc,Toast.LENGTH_SHORT).show();

                        if (Integer.valueOf(check3)>0) {
                          //  updatebarctran(ambilnoref);
                            Intent a = new Intent(getBaseContext(), Emp_RegErrorLokal.class);
                            startActivity(a);
                            return;
                        }




                        if (check2.equals("0")||check2.equals(null)) {
                            updatebarctran(ambilnoref);

                            Intent a1 = new Intent(getBaseContext(), warningselesai.class);
                            a1.putExtra("warning", "Upload Selesai ...Error: " + check2);
                            //  a1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(a1);



                        } else {


                            Intent a = new Intent(getBaseContext(), Emp_RegErrorLokal.class);
                            startActivity(a);
                        }
                    }



                  //  k++;
                   // if( k <= hitbarc)
                    //{
                        // Here `this` refers to the anonymous `Runnable`

                     //   handler.postDelayed(this, 1000);
                    //}
                //}
            //});














        } catch (JSONException e) {

            Log.d("BarangActivity", "errorJSON");

        }
        //  Toast.makeText(getBaseContext(),"Jumlah Akhir ",Toast.LENGTH_SHORT).show();

        // hideDialog();
        tagdata = 2;


    }


    public void delay2(final int c,final int s) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(c);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                EditText tv= (EditText) findViewById(R.id.editremarks);
                tv.setText("Text = "+s);
                DialogErrorServer();
            }
        }, c);

    }



    public void delay(final int c) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(c);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
               // DialogErrorServer();         //send
            }
        }, c);

    }


    private void DialogErrorNetwork() {
        dialog = new AlertDialog.Builder(NURUploadBarcodeLokal.this);
        inflater = getLayoutInflater();
        dialogView = inflater.inflate(R.layout.error_network, null);
        dialog.setView(dialogView);
        //  dialog.setCancelable(true);
        dialog.setIcon(R.mipmap.ic_launcher);
        dialog.setTitle("Network Error,Pilih Yes untuk mengulangi upload");

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
                sqldelnoref(textnoreff.getText().toString());
                //txt_hasil.setText("Nama : " + nama + "\n" + "Usia : " + usia + "\n" + "Alamat : " + alamat + "\n" + "Status : " + status);
                dialog.dismiss();
                //  finish();
            }
        });

        dialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        dialog.show();
        return;
    }


    private void DialogErrorServer() {
        dialog = new AlertDialog.Builder(NURUploadBarcodeLokal.this);
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


    private void inserttoserver(final String norefx) {
        //del transaction


        //  pDialog.setMessage("Uploading Data...");
        //  pDialog.show();


        SQLiteDatabase db = adb_master.getWritableDatabase();
        String sql1 = "select barcode,noref,typetran,lokoven,tgl_rec,temp1,idno,temp2 from tbl_barctran where noref='" + norefx + "' ";
        Cursor cursor = db.rawQuery(sql1, null);

        cursor.moveToFirst();


////////////              batas del


          int hut = 0;

        if (cursor.moveToFirst()) {
            do {
                //    BarangDialog userin = new BarangDialog(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getDouble(3));
                //  userList2.add(userin);


                sqlserverinsert(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6));
                //Toast.makeText(getBaseContext(),"Att : "+cursor.getString(1),Toast.LENGTH_SHORT).show();


                hut = hut + 1;


      // delay2(1000,hut);



                //  nilaiof.setText(String.valueOf(hit)+" Of "+hitbarc);
                //editremarks.setText(String.valueOf(hit));


            } while (cursor.moveToNext());
        }

//hideDialog();
     /*   int jum= cursor.getCount();
cursor.moveToFirst();

        for (int i = 0; i < jum; i++)
        {
            tagdata=1;
            sqlserverinsert(cursor.getString(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4),cursor.getString(5));


                cursor.moveToNext();

        }
*/


        //  Toast.makeText(getBaseContext(),"Last Barcode:  "+cursor.getString(0),Toast.LENGTH_SHORT).show();
   /*     cursor.moveToFirst();
        Toast.makeText(getBaseContext(),"Completed Transfer Jumlah:  "+i,Toast.LENGTH_SHORT).show();
        updatebarctran(norefx);


    */
        //  return userList2;


        //    loadDataServerVolley();

        //Toast.makeText(getBaseContext(),"Upload Completed",Toast.LENGTH_SHORT).show();
    }




    private void insertto_test(final String norefx) {
        //del transaction


        //  pDialog.setMessage("Uploading Data...");
        //  pDialog.show();


        SQLiteDatabase db = adb_master.getWritableDatabase();
        String sql1 = "select barcode,noref,typetran,lokoven,tgl_rec,temp1,idno,temp2 from tbl_barctran where noref='" + norefx + "' ";
        Cursor cursor = db.rawQuery(sql1, null);

         //delete awal data di tbl barang
        String del1="delete from tbl_barang";
        db.execSQL(del1);

       // cursor.moveToFirst();

        SQLiteDatabase create = adb_master.getWritableDatabase();
        // String selectQuery = "SELECT hak FROM " + ADB_User.MyColumnsHak.NamaTabel;
        //Cursor cursor=create.rawQuery(selectQuery,null);

        ContentValues values = new ContentValues();
        values.put(ADB_Master.MyColumns_item.kode, "x");
        values.put(ADB_Master.MyColumns_item.nama, "x");
        values.put(ADB_Master.MyColumns_item.harga, 1);
        values.put(ADB_Master.MyColumns_item.harga_beli, 1);
        create.insert(ADB_Master.MyColumns_item.NamaTabel, null, values);

       /* String sql12 = "select kode from tbl_barang ";
        Cursor cursor2 = db.rawQuery(sql12, null);

        int jum1=cursor2.getCount();

        Toast.makeText(getBaseContext(),"Last Barcode:  "+jum1,Toast.LENGTH_LONG).show();
*/
////////////              batas del


     //   int hut = 0;

         if (cursor.moveToFirst()) {
            do {
                //    BarangDialog userin = new BarangDialog(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getDouble(3));
                //  userList2.add(userin);

                String sql12 = "select kode from tbl_barang where nama='x'";
                Cursor cursor2 = db.rawQuery(sql12, null);
                cursor2.moveToFirst();

               // SQLiteDatabase db=adb_master.getWritableDatabase();

                String upd1="update tbl_barang set kode='"+cursor2.getString(0)+";"+cursor.getString(0)+"'";
                db.execSQL(upd1);

                int jum=cursor2.getCount();

           //  Toast.makeText(getBaseContext(),"Last Barcode:  "+cursor.getString(0),Toast.LENGTH_LONG).show();


            } while (cursor.moveToNext());

        }

        String sql12 = "select kode from tbl_barang where nama='x'";
        Cursor cursor2 = db.rawQuery(sql12, null);
        cursor2.moveToFirst();
        cursor.moveToLast();
        String upd1="update tbl_barang set kode='"+cursor2.getString(0)+";"+cursor.getString(0)+"'";
        db.execSQL(upd1);

       // editremarks.setText(cursor2.getString(0));

       // Toast.makeText(getBaseContext(),"Last Barcode:  "+cursor2.getString(0),Toast.LENGTH_LONG).show();




        sqlserverinsert(cursor2.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6));

//hideDialog();
     /*   int jum= cursor.getCount();
cursor.moveToFirst();

        for (int i = 0; i < jum; i++)
        {
            tagdata=1;
            sqlserverinsert(cursor.getString(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4),cursor.getString(5));


                cursor.moveToNext();

        }
*/


        //  Toast.makeText(getBaseContext(),"Last Barcode:  "+cursor.getString(0),Toast.LENGTH_SHORT).show();
   /*     cursor.moveToFirst();
        Toast.makeText(getBaseContext(),"Completed Transfer Jumlah:  "+i,Toast.LENGTH_SHORT).show();
        updatebarctran(norefx);


    */
        //  return userList2;


        //    loadDataServerVolley();

       Toast.makeText(getBaseContext(),"Upload Completed",Toast.LENGTH_SHORT).show();
    }




    private void updateTextView(final String s) {
        NURUploadBarcodeLokal.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                EditText tv= (EditText) findViewById(R.id.editremarks);
                tv.setText("Text = "+s);
                tv.invalidate();









            }
        });

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


    private void updatebarctranbalik(final String norefx)
    {
        SQLiteDatabase db=adb_master.getWritableDatabase();
        String upd1="update tbl_barctran set tagtran='1' where noref='"+norefx+"'";
        db.execSQL(upd1);
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
}
