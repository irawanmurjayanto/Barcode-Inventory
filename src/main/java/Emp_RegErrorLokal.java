package com.example.barcodegcnew;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.barcodegcnew.NURUploadBarcodeLokal.ambilnoref;
import static com.example.barcodegcnew.NURUploadBarcodeLokal.hitbarc;
import static com.example.barcodegcnew.NURUploadBarcodeLokal.valprogress;

public class Emp_RegErrorLokal extends Activity {
    private RecyclerView mRecyclerViewdlg;
    private barangPersonAdapterLokal rvAdapterdlg;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<barangPerson> barangListDialog = new ArrayList<barangPerson>();
    private Context context = Emp_RegErrorLokal.this;
    private static  final int REQUEST_CODE_EDIT =2;

    EditText carinik;
    ProgressDialog pDialog;
    TextView textNIK;
    Button butposulang,butposulangclose;

    AlertDialog.Builder dialog;
    LayoutInflater inflater;
    View dialogView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emp__reg_lokal);
        carinik=(EditText) findViewById(R.id.textItemcode);
        textNIK=(TextView) findViewById(R.id.textNotrans);
        butposulang=findViewById(R.id.butposulang);
        butposulangclose=findViewById(R.id.butposulangclose);

        mRecyclerViewdlg=(RecyclerView) findViewById(R.id.my_recycler_view_dialog);
        mRecyclerViewdlg.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);

        mRecyclerViewdlg.setLayoutManager(mLayoutManager);

        pDialog=new ProgressDialog(this);
      //  loaddatanik();


        butposulang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogPOST();
            }
        });

        butposulangclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
finish();
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



    private void sqlserverinsert(final String norefx) {



        //  hit = 0;
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


        String url = AppConfig.IP_SERVER+"gcnew/lokal_insertbarctran_error.php";


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
                params.put("noref", norefx);
             /*   params.put("barcode", t0);
                params.put("noref", t1);
                params.put("typetran", t2);
                params.put("lokoven", t3);
                params.put("tgl_rec", t4);
                params.put("user_name", t5);
                params.put("hitbarc", String.valueOf(hitbarc));
                params.put("remarks", NURUploadBarcodeLokal.ambilremarks);
*/



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
            String check2 = jsonObj.getString("check2");









            // Toast.makeText(getBaseContext(),"Uploading Status : "+String.valueOf(Double.valueOf((Integer.valueOf(check1)/hitbarc)*100))+"%",Toast.LENGTH_LONG).show();
            //   pDialog.setMessage("Uploading Data...");
            //  showDialog();




            if (check1.equals("error")) {

                //   hideDialog();


                Intent a1 = new Intent(getBaseContext(), warningselesai.class);
                a1.putExtra("warning", "Item Masih error,Mohon dicheck Ulang " + check2);
                // a1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(a1);




            }


            //    JSONArray jsonArray = jsonObj.getJSONArray("data");

            // Log.d("TAG", "data length: " + jsonArray.length());

            //  BarangCabang objectbarang = null;

            //barangListPOS.clear();

            //NumberFormat numberFormat  = new DecimalFormat("##");

            // DecimalFormat formatter = new DecimalFormat("#,###,###,###");
            //  totalrp.setText(formatter.format(obj.getDouble("total")));


            //    Log.d("TAG", "data length: " + jsonArray.length());

          /*  SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
            JSONArray jsonArray = jsonObj.getJSONArray("data");
            for(int i = 0; i < jsonArray.length(); i++) {

                JSONObject obj = jsonArray.getJSONObject(i);

                objectbarang = new BarangCabang();

                objectbarang.setIdno(obj.getString("idno"));


                Toast.makeText(getBaseContext(),"status"+test1,Toast.LENGTH_LONG).show();
*/

          /*      if (wrhtype_get.equals("import")) {
                    String s="";
                }else
                {
                    objectbarang.setTujuan(obj.getString("tujuan"));
                }*/

            //   barangListPOS.add(objectbarang);


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


        } catch (JSONException e) {

            Log.d("BarangActivity", "errorJSON");

        }
        //  Toast.makeText(getBaseContext(),"Jumlah Akhir ",Toast.LENGTH_SHORT).show();

        // hideDialog();
        // tagdata = 2;


    }



    private void DialogErrorNetwork() {
        dialog = new AlertDialog.Builder(Emp_RegErrorLokal.this);
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
                //textwarning.setText("Delete No ref : "+ambilnoref);
                return;
                // sqldelnoref(ambilnoref);
                //txt_hasil.setText("Nama : " + nama + "\n" + "Usia : " + usia + "\n" + "Alamat : " + alamat + "\n" + "Status : " + status);
                //dialog.dismiss();
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
        //        Intent a2 = new Intent(getBaseContext(), warningloading.class);
          //      startActivity(a2);


                Toast.makeText(getBaseContext(),"No ref: "+ambilnoref, Toast.LENGTH_SHORT).show();

sqlserverinsert(ambilnoref);
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


    private void gambarDatakeRecyclerView(){
       // final String notrans=textNotrans.getText().toString();
        rvAdapterdlg = new barangPersonAdapterLokal(barangListDialog);

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
                        balik.putExtra("idno",String.valueOf(barang.getIDNO()));
                      //  balik.putExtra("jabatan",barang.getJabatan());
                        balik.putExtra("section",barang.getSection1());
                      //  balik.putExtra("macadd",barang.getMacadd());
                      //  balik.putExtra("statusemp",barang.getStatusemp());
                        setResult(RESULT_OK,balik);
finish();
                    }

                })

        );

    }









    //load data pos barcode and reset data on new trans
    public void loaddatanik(){

        //final String nama=carinik.getText().toString();
        final Intent idovn=getIntent();
       final String getidovn=idovn.getStringExtra("idoven");

       Toast.makeText(this,"Load Data "+getidovn,Toast.LENGTH_LONG).show();
        String url = AppConfig.IP_SERVER+"gcnew/android_listbarcodelokalerror.php";

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

                params.put("noref",NURUploadBarcodeLokal.noreflokal);

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

            JSONArray jsonArray = jsonObj.getJSONArray("data");

            Log.d("TAG", "data length: " + jsonArray.length());

            barangPerson objectbarang = null;

            barangListDialog.clear();

            //NumberFormat numberFormat  = new DecimalFormat("##");

            // DecimalFormat formatter = new DecimalFormat("#,###,###,###");
            //  totalrp.setText(formatter.format(obj.getDouble("total")));

            for(int i = 0; i < jsonArray.length(); i++){

                JSONObject obj = jsonArray.getJSONObject(i);
                objectbarang= new barangPerson();
                 objectbarang.setBarcode(obj.getString("barcode"));
                objectbarang.setRemarks(obj.getString("remarks"));
                objectbarang.setNama(obj.getString("error_note"));

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
