package com.example.barcodegcnew;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class MainStockGC extends Activity {
    private RecyclerView mRecyclerViewdlg;
    private barangPersonAdapterStock rvAdapterdlg;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<barangPerson> barangListDialog = new ArrayList<barangPerson>();
    private Context context = MainStockGC.this;
    private static  final int REQUEST_CODE_EDIT =2;
    private static int savetag=0;
    private static String url;

    EditText carinik;
    ProgressDialog pDialog;
    TextView textNIK,item_code,item_desc;
    LinearLayout menu1,menu2,menu3;
    ImageButton stockimport,stockputra,stockbad;

    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat dateFormatter2,dateFormatter;
    TextView textdateresult,texthasil1;
    ImageButton tglakses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emp__reg_stock);
        carinik=(EditText) findViewById(R.id.textcariitem);
        textNIK=(TextView) findViewById(R.id.textNotrans);

        Intent j=getIntent();
        String xjudul=j.getStringExtra("judul");

        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Inventory Golden Care");

        tglakses= findViewById(R.id.tglakses);
        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        dateFormatter2 = new SimpleDateFormat("yyyy-MM-dd", Locale.US);

        textdateresult= findViewById(R.id.tglupdate);
        texthasil1= findViewById(R.id.texthasil1);

        Calendar newDate = Calendar.getInstance();
     //   newDate.set(year, monthOfYear, dayOfMonth);


        textdateresult.setText(dateFormatter.format(newDate.getTime()));
        texthasil1.setText(dateFormatter2.format(newDate.getTime()));

        tglakses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateDialog1();

            }
        });


        mRecyclerViewdlg=(RecyclerView) findViewById(R.id.my_recycler_view_dialog);
        mRecyclerViewdlg.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);

        mRecyclerViewdlg.setLayoutManager(mLayoutManager);

        pDialog=new ProgressDialog(this);
      //  loaddatanik();

        menu1=findViewById(R.id.menu1);
        menu2=findViewById(R.id.menu2);
        menu3=findViewById(R.id.menu3);

        final int sdk = android.os.Build.VERSION.SDK_INT;
     if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
         menu3.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.addroundmenuinv2) );
         menu2.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.addroundmenuinv2) );
         menu1.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.addroundmenuinv1) );
         savetag=1;

        } else {
         menu3.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.addroundmenuinv2) );
         menu2.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.addroundmenuinv2) );
         menu1.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.addroundmenuinv1) );
         savetag=1;

        }



        stockimport=findViewById(R.id.stockimport);
        stockbad=findViewById(R.id.stockbad);
        stockputra=findViewById(R.id.stockputra);


        stockimport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                    menu3.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.addroundmenuinv2) );
                    menu2.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.addroundmenuinv2) );
                    menu1.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.addroundmenuinv1) );
                    savetag=1;
                    loaddatanik();
                } else {
                    menu3.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.addroundmenuinv2) );
                    menu2.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.addroundmenuinv2) );
                    menu1.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.addroundmenuinv1) );
                    savetag=1;
                    loaddatanik();
                }


            }
        });


        stockbad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                    menu3.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.addroundmenuinv1) );
                    menu2.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.addroundmenuinv2) );
                    menu1.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.addroundmenuinv2) );
                    savetag=3;
                    loaddatanik();
                } else {
                    menu3.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.addroundmenuinv1) );
                    menu2.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.addroundmenuinv2) );
                    menu1.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.addroundmenuinv2) );
                    savetag=3;
                    loaddatanik();
                }


            }
        });


        stockputra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                    menu3.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.addroundmenuinv2) );
                    menu2.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.addroundmenuinv1) );
                    menu1.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.addroundmenuinv2) );
                    savetag=2;
                    loaddatanik();
                } else {
                    menu3.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.addroundmenuinv2) );
                    menu2.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.addroundmenuinv1) );
                    menu1.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.addroundmenuinv2) );
                    savetag=2;
                    loaddatanik();
                }


            }
        });


        textdateresult.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                loaddatanik();
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




        carinik.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            loaddatanik();
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



        loaddatanik();


    }


    private void gambarDatakeRecyclerView(){
       // final String notrans=textNotrans.getText().toString();
        rvAdapterdlg = new barangPersonAdapterStock(barangListDialog);

        mRecyclerViewdlg.setAdapter(rvAdapterdlg);


        mRecyclerViewdlg.addOnItemTouchListener(new RecyclerItemListener(context, new RecyclerItemListener.OnItemClickListener() {

                    @Override

                    public void onItemClick(View view, int position) {
                        //Toast.makeText(getBaseContext(),"Posisi no: "+position, Toast.LENGTH_SHORT).show();
                        barangPerson barang = rvAdapterdlg.getItem(position);
                       // Toast.makeText(getBaseContext(),"Posisi no: "+barang.getNIK(), Toast.LENGTH_SHORT).show();
                       // Intent intent = new Intent(Emp_RegAss.this, DialogTranPOSInput.class);
                        //startActivity(intent);
                        //intent.putExtra("barang", barang);
                        //intent.putExtra("notrans",notrans);
                        //startActivityForResult(intent, REQUEST_CODE_EDIT);
                        Intent balik=new Intent();
                       // balik.putExtra("nik",barang.getNIK());
                      //  balik.putExtra("nama",barang.getNama());
                        balik.putExtra("item_code",barang.getItem_code());
                      //  balik.putExtra("jabatan",barang.getJabatan());
                        balik.putExtra("item_desc",barang.getItem_desc());
                      //  balik.putExtra("macadd",barang.getMacadd());
                      //  balik.putExtra("statusemp",barang.getStatusemp());
                        setResult(RESULT_OK,balik);
//finish();
                    }

                })

        );

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


                textdateresult.setText(dateFormatter.format(newDate.getTime()));
                texthasil1.setText(dateFormatter2.format(newDate.getTime()));

               // editservice.setText(dateFormatter.format(newDate.getTime()));

            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));


        datePickerDialog.show();



    }



    //load data pos barcode and reset data on new trans
    public void loaddatanik(){

        pDialog.setMessage("Loading Data");
        //final String nama=carinik.getText().toString();
        final Intent idovn=getIntent();
      // final String getidovn=idovn.getStringExtra("idoven");

      // Toast.makeText(this,"Load Data "+getidovn,Toast.LENGTH_LONG).show();
        if (savetag==1) {
           url = AppConfig.IP_SERVER + "gcnew/list_stockactive.php";
        }else if (savetag==2)
        {
              url = AppConfig.IP_SERVER + "gcnew/list_stockputra.php";
        }else
        {
             url = AppConfig.IP_SERVER + "gcnew/list_stockbad.php";
        }

        StringRequest postRequest = new StringRequest(Request.Method.POST, url,new Response.Listener<String>() {

            @Override

            public void onResponse(String response) {

                Log.d("Dialog POS","response:"+response);

                //   hideDialog();

            //   Toast.makeText(Emp_RegAss.this,"MAsuk Respon",Toast.LENGTH_LONG).show();

                // barangListPOS.clear();
                processResponsePOS(response);
                gambarDatakeRecyclerView();


//                pDialog.hide();
               // hideDialog();
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

                        } else if (error instanceof ServerError) {
                            Toast.makeText(getBaseContext(),
                                    "Oops. Server Time out",
                                    Toast.LENGTH_LONG).show();
                        } else if (error instanceof AuthFailureError) {
                        } else if (error instanceof ParseError) {
                        } else if (error instanceof NoConnectionError) {
                            Toast.makeText(getBaseContext(),
                                    "Oops. No Connection!",
                                    Toast.LENGTH_LONG).show();
                        } else if (error instanceof TimeoutError) {
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

                // Toast.makeText(Transaksi_POS.this,"HAS MAP : "+notransaksi,Toast.LENGTH_LONG).show();

                Map<String, String>  params = new HashMap<>();

                // the POST parameters:

                Intent a=getIntent();
              //  String x=a.getStringExtra("tipestock");

                params.put("cari",carinik.getText().toString());
                params.put("tgl",texthasil1.getText().toString());

            //    params.put("tipe",x);

                return params;

            }

        };
        postRequest.setRetryPolicy(new DefaultRetryPolicy(100 * 1000, 1, 1.0f));
        Volley.newRequestQueue(this).add(postRequest);
        //  Toast.makeText(this,"HAS MAP : "+notransaksi,Toast.LENGTH_LONG).show();
    }


    private void processResponsePOS(String response){

        try {

            Log.d("TAG", "data response DATA POS: "+response);

            JSONObject jsonObj = new JSONObject(response);



            //Log.d("TAG", "data length: " + jsonArray.length());

            barangPerson objectbarang = null;

            barangListDialog.clear();

            //NumberFormat numberFormat  = new DecimalFormat("##");

            // DecimalFormat formatter = new DecimalFormat("#,###,###,###");
            //  totalrp.setText(formatter.format(obj.getDouble("total")));
            JSONArray jsonArray = jsonObj.getJSONArray("data");
            for(int i = 0; i < jsonArray.length(); i++){

                JSONObject obj = jsonArray.getJSONObject(i);
                objectbarang= new barangPerson();
               //  objectbarang.setIDNO(obj.getInt("idno"));
                if (savetag==1) {
                    objectbarang.setItem_code(obj.getString("item_code"));
                    objectbarang.setItem_desc(obj.getString("item_desc"));
                    objectbarang.setQty(obj.getDouble("normal"));
                    objectbarang.setQtydamage(obj.getDouble("damage"));
                    objectbarang.setQtytotal(obj.getDouble("total"));
                }else if (savetag==2)
                {
                    objectbarang.setItem_code(obj.getString("item_code"));
                    objectbarang.setItem_desc(obj.getString("item_desc"));
                    objectbarang.setQty(obj.getDouble("normal"));
                    objectbarang.setQtydamage(obj.getDouble("damage"));
                    objectbarang.setQtytotal(obj.getDouble("total"));
                }else
                {
                    objectbarang.setItem_code(obj.getString("item_code"));
                    objectbarang.setItem_desc(obj.getString("item_desc"));
                    objectbarang.setQtydamage(obj.getDouble("normal"));
                    objectbarang.setQty(obj.getDouble("damage"));
                    objectbarang.setQtytotal(obj.getDouble("total"));
                }



                barangListDialog.add(objectbarang);

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

        }
hideDialog();
    }

    private void showDialog() {

        if (!pDialog.isShowing())

            pDialog.show();

    }

    private void hideDialog() {

        if (pDialog.isShowing())

            pDialog.dismiss();

    }





}
