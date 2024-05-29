package com.example.barcodegcnew;



import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
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

public class MainActivity2 extends AppCompatActivity {
TextView hasilsect,hasilidno,hasiltgl1,hasiltgl2;
    private ImageButton btDatePicker1,btDatePicker2;
    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat dateFormatter,dateFormatter2;
    private List<POJOData> barangListData2 = new ArrayList<POJOData>();
    private AdapterData2 rvAdapterData2;
    private RecyclerView mRecyclerViewData;

    ProgressDialog pDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        mRecyclerViewData= (RecyclerView) findViewById(R.id.bedulkubu);

        pDialog=new ProgressDialog(this);
      //  pDialog.setMessage("Opening Program..");
     //   pDialog.show();


        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Barcode Sawmill");
        setSupportActionBar(toolbar);

        Button barc1=findViewById(R.id.barc1);
        Button getsec=findViewById(R.id.getsection);
          hasilsect=findViewById(R.id.hasilsection);
          hasilidno=findViewById(R.id.hasilidno);
          hasiltgl1=findViewById(R.id.tv_dateresult1);
        hasiltgl2=findViewById(R.id.tv_dateresult2);
        btDatePicker1 = (ImageButton) findViewById(R.id.bt_datepicker1);



        SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMYYYY");
        String date = dateFormat.format(new Date());


        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        dateFormatter2 = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        Calendar newDate = Calendar.getInstance();
       // newDate.set(year, monthOfYear, dayOfMonth);
        hasiltgl1.setText(dateFormatter.format(newDate.getTime()));
        hasiltgl2.setText(dateFormatter2.format(newDate.getTime()));

        btDatePicker1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDateDialog1();
            }
        });



        getsec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                        Intent saya = new Intent(getBaseContext(), Emp_RegAssSaw.class);
                        //saya.putExtra("macadd",textgetmax.getText().toString());
                        startActivityForResult(saya, 111);



            }
        });


        barc1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                Toast.makeText(getBaseContext(), "Load barcode", Toast.LENGTH_LONG).show();
                IntentIntegrator integrator = new IntentIntegrator(MainActivity2.this);
                integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
                integrator.setCaptureActivity(AnyOrientationCaptureActivity.class);
                integrator.setCaptureActivity(TorchOnCaptureActivity.class);
                integrator.setOrientationLocked(false);
                integrator.setPrompt("Sawmill Barcode PT.Paradise");
                integrator.setCameraId(0);  // Use a specific camera of the device
                integrator.setBeepEnabled(true);
                integrator.initiateScan();

            }
        });


    }

    private void showDateDialog1(){

        /**
         * Calendar untuk mendapatkan tanggal sekarang
         */
        Calendar newCalendar = Calendar.getInstance();

        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);


                hasiltgl1.setText(dateFormatter.format(newDate.getTime()));
                hasiltgl2.setText(dateFormatter2.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));


        datePickerDialog.show();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        // IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);

         IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);

        // Toast.makeText(getBaseContext(),"test hasil"+requestCode,Toast.LENGTH_LONG).show();

        // EditText tvResult = (EditText) findViewById(R.id.txtEditResult);
        //TextView tvResult2=(TextView) findViewById(R.id.hasilspin3);
        if (scanningResult != null) {
            if(scanningResult.getContents() != null) {
                //we have a result
              //  cari1.setText(scanningResult.getContents());
              //  insertdata();
              //  loadDataServerVolley();
              //  gambarDatakeRecyclerView();
                Intent saya = new Intent(getBaseContext(), BarcodeEntry.class);
                //saya.putExtra("macadd",textgetmax.getText().toString());
                saya.putExtra("barcode",scanningResult.getContents());
                startActivityForResult(saya, 222);

            }}

        if (requestCode == 222) {
            Toast.makeText(getBaseContext(),"Load Data",Toast.LENGTH_LONG).show();

        }



        if (requestCode == 111) {





                    hasilidno.setText (intent.getStringExtra("idno"));
                    hasilsect.setText(intent.getStringExtra("section"));
                    Toast.makeText(getBaseContext(),"nik: "+hasilsect.getText().toString()+"xx"+hasilidno.getText().toString(),Toast.LENGTH_LONG).show();







        }
    }

    private void showDialog() {

        if (!pDialog.isShowing())

            pDialog.show();

    }

    private void hideDialog() {

        if (pDialog.isShowing())

            pDialog.dismiss();

    }



    private void gambarDatakeRecyclerViewIDNO2() {

        // rvAdapterpos = new BarangAdapterMasterUser(barangListPOS);

        //mRecyclerViewpos.setAdapter(rvAdapterpos);
        //rvAdapterpos.notifyDataSetChanged();



        rvAdapterData2 = new AdapterData2(barangListData2);
        mRecyclerViewData.setAdapter(rvAdapterData2);
        rvAdapterData2.notifyDataSetChanged();

        hideDialog();

        mRecyclerViewData.addOnItemTouchListener(new RecyclerItemListener(getBaseContext(), new RecyclerItemListener.OnItemClickListener() {

                    @Override

                    public void onItemClick(View view, int position) {



                        // Toast.makeText(getBaseContext(), "Posisi no: " + position, Toast.LENGTH_SHORT).show();

                        POJOData barang = rvAdapterData2.getItem(position);

                        Intent intent = new Intent(getBaseContext(), BarcodeEntry.class);
                        //   Intent intent = new Intent(MasterUser.this, ADB_TambahUser.class);
                        //startActivity(intent);

                        intent.putExtra("barang", barang);

                        startActivityForResult(intent, 350);


                    }

                })

        );

    }



    public void loaddatavolley(){
        // pDialog=new ProgressDialog(MainActivity2.this);
        //pDialog.setMessage("Opening Data..");
        //showDialog();

        // Toast.makeText(this,"Load Data "+kode,Toast.LENGTH_LONG).show();
        //String url = "http://kinipi.net/hrd/newtbmaster.php";
        String url = AppConfig.IP_SERVER+"sawmill/androidload_saw1.php";
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,new Response.Listener<String>() {

            @Override

            public void onResponse(String response) {

                Log.d("Dialog POS","response:"+response);

                //   hideDialog();

                //   Toast.makeText(Emp_RegAss.this,"MAsuk Respon",Toast.LENGTH_LONG).show();

                ///xxxxx        barangListData2.clear();
                processResponsePOSIDNO2(response);
                ///xxxxx      gambarDatakeRecyclerViewIDNO2();


//                pDialog.hide();
                // hideDialog();
            }

        },

                new Response.ErrorListener() {

                    @Override

                    public void onErrorResponse(VolleyError error) {





                        if (error instanceof NetworkError) {
                            hideDialog();
                            Intent a=new Intent(getBaseContext(),koneksi.class);
                            startActivity(a);

                        } else if (error instanceof ServerError) {
                            hideDialog();
                            Intent a=new Intent(getBaseContext(),koneksi.class);
                            startActivity(a);
                        } else if (error instanceof AuthFailureError) {
                            hideDialog();
                        } else if (error instanceof ParseError) {
                            hideDialog();
                        } else if (error instanceof NoConnectionError) {
                            hideDialog();
                            Intent a=new Intent(getBaseContext(),koneksi.class);
                            startActivity(a);
                        } else if (error instanceof TimeoutError) {
                            hideDialog();
                            Intent a=new Intent(getBaseContext(),koneksi.class);
                            startActivity(a);
                        }


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

                //params.put("pino",editcari.getText().toString());

                return params;

            }

        };

        Volley.newRequestQueue(this).add(postRequest);
        //   hideDialog();
        //  Toast.makeText(this,"HAS MAP : "+notransaksi,Toast.LENGTH_LONG).show();
    }


    private void processResponsePOSIDNO2(String response){

        try {

            Log.d("TAG", "data response DATA POS: "+response);

            JSONObject jsonObj = new JSONObject(response);

            JSONArray jsonArray = jsonObj.getJSONArray("data");

            Log.d("TAG", "data length: " + jsonArray.length());

            POJOData objectbarangPI = null;

           barangListData2.clear();

            //NumberFormat numberFormat  = new DecimalFormat("##");

            // DecimalFormat formatter = new DecimalFormat("#,###,###,###");
            //  totalrp.setText(formatter.format(obj.getDouble("total")));

            //  myBitmap = BitmapFactory.decodeFile(imageUri);
            //    ImageView ivBasicImage = (ImageView) findViewById(R.id.photoabsen4);
            //  ivBasicImage.setImageBitmap(myBitmap);

            // Picasso.get().load("http://kinipi.net/hrd/uploademp/11001keluar22112019.jpg").into(ivBasicImage);

            // Glide.with(this).load(imageUri).into(ivBasicImage);
            //  ImageView img_add = (ImageView) findViewById(R.id.photoabsen4);


            //  img_add.setImageBitmap(getBitmapFromURL("http://kinipi.net/hrd/uploademp/11001keluar22112019.jpg"));

        /*    ImageView bindImage = (ImageView)findViewById(R.id.photoabsen4);
            String pathToFile = "http://kinipi.net/hrd/uploademp/11001keluar22112019.jpg";
            DownloadImageWithURLTask downloadTask = new DownloadImageWithURLTask(bindImage);
            downloadTask.execute(pathToFile);*/


            for(int i = 0; i < jsonArray.length(); i++){

                JSONObject obj = jsonArray.getJSONObject(i);


                objectbarangPI= new POJOData();
                // objectbarangAtt.setIDNO(obj.getInt("idno"));
                //  objectbarangAtt.setNIK(obj.getString("nik"));
                //  final String pi2=obj.getString("pisaja");
                objectbarangPI.setJudul(obj.getString("pisaja"));
                objectbarangPI.setTgl(obj.getString("tgl_rec")+"  ("+obj.getString("nama_cust")+" )");
                objectbarangPI.setIDNO( obj.getInt("idno2"));
                objectbarangPI.setRemarks( obj.getString("remarks"));
                // String imageUri = "http://kinipi.net/hrd/uploademp/"+obj.getString("photo");
                //objectbarangAtt.setPhoto(imageUri);
                //textSection.setText( obj.getString("section")+" / ");
                // objectbarangAtt.setPhoto(imageUri);


           /*     ImageView bindImage = (ImageView)findViewById(R.id.loadimage);
                String pathToFile = "http://kinipi.net/hrd/uploademp/11001keluar22112019.jpg";
                DownloadImageWithURLTask downloadTask = new DownloadImageWithURLTask(bindImage);
                downloadTask.execute(pathToFile);*/



                 barangListData2.add(objectbarangPI);

                //  carinik.setText(obj.getString("nik"));
                // Toast.makeText(this,"NO NIK : "+obj.getString("nik"),Toast.LENGTH_LONG).show();
                //    final ArrayAdapter<String> myAdapter = new ArrayAdapter(this, R.layout.activity_dialog_tran_posedit);
                //  list1.setAdapter(myAdapter);

                /*
                ListAdapter adapter = new SimpleAdapter(
                        DialogTranPOS.this, R.layout.activity_dialog_tran_posedit,
                        new String[]{objectbarang.setKode(obj.getString("kode")),objectbarang.setNama(obj.getString("nama"))},
                        new int[]{R.id.textBarcode,R.id.textItem});*/


            }
//pDialog.hide();
        } catch (JSONException e) {

            Log.d("MainActivity", "errorJSON");
            hideDialog();

        }
        hideDialog();
    }
}