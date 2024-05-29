package com.example.barcodegcnew;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
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

import static android.R.layout.simple_spinner_item;


public class NURUploadBarcode extends AppCompatActivity {
    public static final String TAG_JSON_ARRAY = "result";
    private RecyclerView mRecyclerView;
    private BarangCabang barang;
    private BarangAdapter rvAdapter;
    private List<BarangCabang> barangListPOS = new ArrayList<BarangCabang>();

    private BarangAdapterNOREF rvAdapterpos;

    private ProgressDialog pDialog;

    private ArrayList<BarangMasterUser> barangCabangArrayList;
    private ArrayList<String> names = new ArrayList<String>();

    private RecyclerView.LayoutManager mLayoutManager;

    private Context context = NURUploadBarcode.this;

    private static final int REQUEST_CODE_ADD = 1;

    private static final int REQUEST_CODE_EDIT = 2;

    private List<BarangBarcode> barangList = new ArrayList<BarangBarcode>();
    private List<BarangBarcodeNOREF> barangListNoref = new ArrayList<BarangBarcodeNOREF>();
    private ADB_Master adb_master;
    private ADB_Trans adb_trans;


    private EditText cari1, editoven;

    AlertDialog.Builder dialog;
    LayoutInflater inflater;
    View dialogView;
    ImageButton butbarcodeher;
    private static final String DATABASE_NAME = "master.db";
    SQLiteDatabase mDatabase;
    TextView t0, t1, textTampung, textTampung2, textTampung3, textnoreff, statustran;
    Button butpos1, butovenname, butmanualbarc, butmanualbarcdel, butdelbarctran, butdelnoref;
    private SimpleDateFormat dateFormatter;
    Spinner spnOven;
    private String a1;
    private static int hitbarc;
    private static final String Json_URL_POS = AppConfig.IP_SERVER + "gcnew/savebarcode.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory__barang);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        // spnOven=(Spinner) findViewById(R.id.spnOven);
        textTampung = (TextView) findViewById(R.id.textTampung);
        textTampung2 = (TextView) findViewById(R.id.textTampung2);
        // textTampung3=(TextView)findViewById(R.id.textTampung3);
        textnoreff = (TextView) findViewById(R.id.textnoref);
        statustran = (TextView) findViewById(R.id.statustrans);
        //    toolbar.setTitle("Barcode Upload");
        //setSupportActionBar(toolbar);
        cari1 = (EditText) findViewById(R.id.cariText);
        //  editoven=(EditText) findViewById(R.id.editOven) ;
        //  butbarcodeher=(ImageButton) findViewById(R.id.butBarcodeher);
        // butpos1=(Button) findViewById(R.id.butpost2);
        //  butovenname=(Button) findViewById(R.id.butovenname);
        butmanualbarc = (Button) findViewById(R.id.butmanualbarc);
        butmanualbarcdel = (Button) findViewById(R.id.butmanualbarcdel);
        butdelnoref = (Button) findViewById(R.id.butdelnoref);


        t0 = (TextView) findViewById(R.id.jumbarc1);
        t1 = (TextView) findViewById(R.id.jumbarc2);
        Calendar newDate = Calendar.getInstance();
        //  newDate.set(year, monthOfYear, dayOfMonth);


        adb_master = new ADB_Master(getBaseContext());
        adb_trans = new ADB_Trans(getBaseContext());
        mDatabase = openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);

        pDialog = new ProgressDialog(getBaseContext());

      //  Intent a = getIntent();


        //a1 = a.getStringExtra("wrhtype");
        //Toast.makeText(getBaseContext(),a1,Toast.LENGTH_LONG).show();
        //statustran.setText(a1);

        butdelnoref.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // DialogDelNoref(textnoreff.getText().toString());
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
               // Toast.makeText(getBaseContext(),"nilai:"+cari1.getText().toString()+textnoreff.getText().toString()+statustran.getText().t)
                saveDataBarcode();
                loaddatavolley();
             /*  insertdata();
                loadDataLokal();
                gambarDatakeRecyclerView();*/

            }
        });



    /*    spnOven.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // TODO Auto-generated method stub

                textTampung.setText( spnOven.getSelectedItem().toString());
               // textTampung.setText( spnOven.getSelectedItemPosition());
                //  editTextHak.setText( allusers.getSelectedItem().toString());
             //   retrieveOven2();


                adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,android.R.id.text1,users);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                //attach adapter to spinner
                allusers.setAdapter(adapter);
                String compareValue = editTextHak.getText().toString();
                Toast.makeText(getApplicationContext(), "masuk:"+compareValue, Toast.LENGTH_SHORT).show();
                if (compareValue != null) {
                    int spinnerPosition = adapter.getPosition(compareValue);
                    allusers.setSelection(spinnerPosition);


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub

            }
        });

*/

     /*   butpos1.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
            DialogPOST();
           }
       });

      butbarcodeher.setOnClickListener(new View.OnClickListener() {
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

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View view) {


                //Toast.makeText(getBaseContext(), "Load barcode", Toast.LENGTH_LONG).show();
                IntentIntegrator integrator = new IntentIntegrator(NURUploadBarcode.this);
                integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
                integrator.setCaptureActivity(AnyOrientationCaptureActivity.class);
                integrator.setCaptureActivity(TorchOnCaptureActivity.class);
                integrator.setOrientationLocked(false);
                integrator.setPrompt("Scan a barcode PT. Paradise");
                integrator.setCameraId(0);  // Use a specific camera of the device
                integrator.setBeepEnabled(true);
                integrator.initiateScan();

            }

        });

        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);

        mRecyclerView.setLayoutManager(mLayoutManager);

        pDialog = new ProgressDialog(this);

        Intent a = getIntent();
        //loadDataServerVolley();
        if (a.hasExtra("barang")) {

            barang = (BarangCabang) a.getSerializableExtra("barang");

             textnoreff.setText(barang.getNoref());
             statustran.setText(barang.getTypetran());
        } else {
            dateFormatter = new SimpleDateFormat("dd-MM-yyyyHH-mm-ss", Locale.US);
            Date currentTime = Calendar.getInstance().getTime();
            textnoreff.setText(a1 + dateFormatter.format(newDate.getTime()));
            a1 = a.getStringExtra("wrhtype");
            statustran.setText(a1);

        }
        //   retrieveOven2();

        loaddatavolley();
    }




    public void saveDataBarcode() {

        // refreshFlag="1";

        final Intent intent = getIntent();

        Toast.makeText(getBaseContext(),"responsexxxx: "+cari1.getText().toString()+statustran.getText().toString()+textnoreff.getText().toString(), Toast.LENGTH_SHORT).show();

        pDialog.setMessage("Save Data...");
        showDialog();


        //final String item_code = itemcodepos.getText().toString();

        //final String notransaksi = notrans.getText().toString();
        // final String notransaksi = intent.getStringExtra("notrans");;

        //  final String qty = totqty.getText().toString();
        //   final String disc = totdisc.getText().toString();
        //   Toast.makeText(getBaseContext(),"response: "+editcust., Toast.LENGTH_SHORT).show();

        // Toast.makeText(getBaseContext(),"response: "+editcustid.getText().toString()+editcustname.getText().toString(), Toast.LENGTH_SHORT).show();

        // String url = AppConfig.IP_SERVER+"/savedata.php";

        //      pDialog = new ProgressDialog(Transaksi_POS.this);
        //    pDialog.setMessage("Save Data ...");
        //  pDialog.show();

        //showDialog();

        StringRequest postRequest1 = new StringRequest(Request.Method.POST, Json_URL_POS, new Response.Listener<String>() {

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
//                        pDialog.hide();

                        error.printStackTrace();

                    }

                }

        ) {

            @Override

            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<>();

                // the POST parameters:

                params.put("barcode", cari1.getText().toString());
                params.put("typetran",statustran.getText().toString());
                params.put("noref", textnoreff.getText().toString());

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

            JSONObject jsonObj = new JSONObject(response);

            String errormsg = jsonObj.getString("errormsg");

            Toast.makeText(getBaseContext(), "hasil " + errormsg, Toast.LENGTH_LONG).show();

            if (errormsg.equals("failed")) {
                DialogPerson();
                // finish();
            }

            hideDialog();
        } catch (JSONException e) {

            //  Log.d("Trnsakais POS2", "errorJSON");

        }

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


        @Override

        protected void onActivityResult ( int requestCode, int resultCode, Intent data){

            IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);


            if (scanningResult != null) {

                // textTampung3.setText(String.valueOf(data.getStringExtra("idno")));
                //insertdata();

                if (scanningResult.getContents() != null) {
                    //   Toast.makeText(this, "hASIL cANNING"+scanningResult.getContents(), Toast.LENGTH_SHORT).show();
                    cari1.setText("");
                    cari1.setText(scanningResult.getContents());
                    //  loadDataLokal();
                    //   gambarDatakeRecyclerView();
                    //we have a result
                    //cari1.setText(scanningResult.getContents());
                    // textTampung3.setText(String.valueOf(data.getStringExtra("idno")));

                    saveDataBarcode();
                    loaddatavolley();
                    //   gambarDatakeRecyclerView();

                }
            }


            if (resultCode == RESULT_OK && requestCode == 222) {

//editoven.setText(data.getStringExtra("section"));
                //   textTampung3.setText(String.valueOf(data.getStringExtra("idno")));

                //   loadDataLokal();

            }


        }

        //ambil data sever volley




    private void gambarDatakeRecyclerView(){

        rvAdapterpos = new BarangAdapterNOREF(barangListPOS);

        mRecyclerView.setAdapter(rvAdapterpos);

        //  final String test1=ambilsw1.getText().toString();


        mRecyclerView.addOnItemTouchListener(new RecyclerItemListener(context, new RecyclerItemListener.OnItemClickListener() {

                    @Override

                    public void onItemClick(View view, int position) {

                        // Toast.makeText(getBaseContext(),"Posisi no: "+position, Toast.LENGTH_SHORT).show();

                        BarangCabang barang = rvAdapterpos.getItem(position);

                        Intent intent = new Intent(getBaseContext(), NURUploadBarcode.class);
                        //startActivity(intent);

                        intent.putExtra("barang", barang);

                        startActivityForResult(intent, REQUEST_CODE_EDIT);

                    }

                })

        );

    }




    public void loaddatavolley(){

       // final String notransaksi= notrans.getText().toString();
       // Intent intent=getIntent();
       // final String kodecabang1=intent.getStringExtra("kodecabang");
//        Toast.makeText(this,"Load Barcode : "+notransaksi,Toast.LENGTH_LONG).show();
        String url = AppConfig.IP_SERVER+"gcnew/android_listbarcode2.php";
        //String url ="http://golden-care.co.id/android/listdata.php";
//        pDialog = new ProgressDialog(Transaksi_POS.this);
        //      pDialog.setMessage("Retieve Data Barang...");
        // Toast.makeText(this,"Load Barcode : "+notransaksi,Toast.LENGTH_LONG).show();
        //    pDialog.show();

        pDialog.setMessage("Retrieve Data...");
        showDialog();

        StringRequest postRequest = new StringRequest(Request.Method.POST, url,new Response.Listener<String>() {

            @Override

            public void onResponse(String response) {

                // Log.d("MainActivity bedul kubu","response:"+response);

                //   hideDialog();



                // barangListPOS.clear();
                processResponsePOS(response);
                 gambarDatakeRecyclerView();
//                pDialog.hide();
                hideDialog();
            }

        },

                new Response.ErrorListener() {

                    @Override

                    public void onErrorResponse(VolleyError error) {

                        hideDialog();

                        error.printStackTrace();

                    }

                }

        ) {

            @Override

            protected Map<String, String> getParams()

            {

                // Toast.makeText(Transaksi_POS.this,"HAS MAP : "+notransaksi,Toast.LENGTH_LONG).show();

                Map<String, String>  params = new HashMap<>();

                // the POST parameters:

               // params.put("nopos",notransaksi);
                params.put("typetran",statustran.getText().toString());

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

            JSONArray jsonArray = jsonObj.getJSONArray("data");

            Log.d("TAG", "data length: " + jsonArray.length());

            BarangCabang objectbarang = null;

            barangListPOS.clear();

            //NumberFormat numberFormat  = new DecimalFormat("##");

            // DecimalFormat formatter = new DecimalFormat("#,###,###,###");
            //  totalrp.setText(formatter.format(obj.getDouble("total")));

            for(int i = 0; i < jsonArray.length(); i++){

                JSONObject obj = jsonArray.getJSONObject(i);

                objectbarang= new BarangCabang();

                objectbarang.setBarcode(obj.getString("barcode"));
                objectbarang.setItem_code(obj.getString("item_code"));
                objectbarang.setItem_desc(obj.getString("item_desc"));
                objectbarang.setKondisi(obj.getString("kondisi"));
                objectbarang.setRemarks(obj.getString("remarks"));
                objectbarang.setNoref(obj.getString("noref"));
                objectbarang.setTypetran(obj.getString("typetran"));
                objectbarang.setQtygc(obj.getDouble("qty"));
                objectbarang.setUser_name(obj.getString("user_name"));


               /* objectbarang.setIDNO(obj.getInt("idno"));
                objectbarang.setProduct(obj.getString("item_desc"));
                objectbarang.setHarga_jual(obj.getDouble("harga_jual"));
                objectbarang.setQty(obj.getDouble("qty"));
                objectbarang.setDiscount(obj.getDouble("disc_val"));
                objectbarang.setSubtot(obj.getDouble("subtot"));
                objectbarang.setQtyret(obj.getInt("qtyreturn"));*/
                barangListPOS.add(objectbarang);


            }
//pDialog.hide();
        } catch (JSONException e) {

            Log.d("MainActivity", "errorJSON");

        }

    }




    private void retrieveOven2(){

      //  Intent intent=getIntent();
       //final String kodecabang1=intent.getStringExtra("kodecabang");


        final String namacari=cari1.getText().toString();
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

            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(NURUploadBarcode.this, simple_spinner_item, names);
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

              //  loadDataLokal();


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



        String url = AppConfig.IP_SERVER+"sawmill/barcsw_delnoref.php";



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

                params.put("noref",norefs);


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
        dialog = new AlertDialog.Builder(NURUploadBarcode.this);
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
    }




    private void DialogErrorServer() {
        dialog = new AlertDialog.Builder(NURUploadBarcode.this);
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
}
