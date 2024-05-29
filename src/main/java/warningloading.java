package com.example.barcodegcnew;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import static android.os.SystemClock.sleep;
import static com.example.barcodegcnew.NURUploadBarcodeLokal.ambilnoref;
import static com.example.barcodegcnew.NURUploadBarcodeLokal.hitbarc;
import static com.example.barcodegcnew.NURUploadBarcodeLokal.noreflokal;
import static com.example.barcodegcnew.NURUploadBarcodeLokal.valprogress;

public class warningloading extends Activity   {
TextView textwarning;
Button butclose,butopen;
    private ProgressDialog pDialog;
    private ADB_Master adb_master;
    private ADB_Trans adb_trans;
    SQLiteDatabase mDatabase;
    private static final String DATABASE_NAME = "master.db";
    AlertDialog.Builder dialog;
    LayoutInflater inflater;
    View dialogView;
    private static String jumhit,barc;
    private static int jumhit2;
   private static Cursor cursor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_warningloading);

        // Intent a=getIntent();
         //String b=a.getStringExtra("warning");
      // delay2(1000,NURUploadBarcodeLokal.valprogress);
        //set us to non-modal, so that others can receive the outside touch events.
       // getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL, WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL);

        //and watch for outside touch events too
    //   getWindow().setFlags(WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH, WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH);

        setFinishOnTouchOutside(false);
      //  getWindow().setCanceledOnTouchOutside(false);


        butclose=findViewById(R.id.butclose);
        butclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



        adb_master = new ADB_Master(getBaseContext());
        adb_trans = new ADB_Trans(getBaseContext());
        mDatabase = openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);
        pDialog = new ProgressDialog(this);
        textwarning=(TextView) findViewById(R.id.textloading);
      // butopen=findViewById(R.id.butopen);




        textwarning.setText("Upload Data No ref : "+ambilnoref+"Jumkah :"+hitbarc);




     /*   BroadcastReceiver broadcast_reciever = new BroadcastReceiver() {

            @Override
            public void onReceive(Context arg0, Intent intent) {
                String action = intent.getAction();
                if (action.equals("checkin3")) {
//finishing the activity
                    // finish();
                   // butopen.performClick();
                }
            }
        };
        registerReceiver(broadcast_reciever, new IntentFilter("checkin3"));*/


    /* new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {



                Handler mainHandler = new Handler(Looper.getMainLooper());

                Runnable myRunnable = new Runnable() {
                    @Override
                    public void run() {

                        Intent intent13 = new Intent("checkin3");
                        sendBroadcast(intent13);

                    }
                };
                mainHandler.post(myRunnable);



            }
        }, 0, 1000); */





    /*    Handler mHandler = new Handler(Looper.getMainLooper());

        mHandler.post(new Runnable() {
            @Override
            public void run() {
                // this will run in the main thread



            }
        });
        sleep(30);*/



   /* for (int i = 0; i < 10; i++)
        {
final int i2=i;
            Handler mHandler = new Handler(Looper.getMainLooper());

            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    // this will run in the main thread

textwarning.setText(String.valueOf(i2));

                }
            });
            sleep(30);
           // delay(1000);
        }*/

        sqldelnoref(ambilnoref);





    }




    public void someMethod(){
        Intent i = new Intent();
        i.setAction("Some key");
        sendBroadcast(i);
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

                params.put("noref", ambilnoref);


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


       inserttoserver2(ambilnoref);

    }


    private void sqlserverinsert(final String t0, final String t1, final String t2, final String t3, final String t4, final String t5, final String rems) {



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


        String url = AppConfig.IP_SERVER+"gcnew/lokal_insertbarctran_bak.php";


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

                params.put("barcode", t0);
                params.put("noref", t1);
                params.put("typetran", t2);
                params.put("lokoven", t3);
                params.put("tgl_rec", t4);
                params.put("user_name", t5);
                params.put("hitbarc", String.valueOf(hitbarc));
                params.put("remarks", NURUploadBarcodeLokal.ambilremarks);




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

valprogress=check1;







            // Toast.makeText(getBaseContext(),"Uploading Status : "+String.valueOf(Double.valueOf((Integer.valueOf(check1)/hitbarc)*100))+"%",Toast.LENGTH_LONG).show();
            //   pDialog.setMessage("Uploading Data...");
            //  showDialog();




            if (check1 == String.valueOf(hitbarc)) {

                //   hideDialog();


                if (check2.equals("0")) {
                    updatebarctran(ambilnoref);
                    //   hideDialog();
                   //  Intent a1 = new Intent(getBaseContext(), warningselesai.class);
                   //  a1.putExtra("warning", "Upload Selesai ...Error: " + check2);
                   //  a1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                  //  startActivity(a1);
                  //  textwarning.setText(" Upload Selesai,Jumlah Data : "+check1);
                    //  params.height = 0;


                } else {

                   /*  textwarning.setText("Ada Error Mohon dibuka tombol Error: " + check2);

                     Intent a1 = new Intent(getBaseContext(), warningselesai.class);
                     a1.putExtra("warning", "Ada Error Mohon dibuka tombol Error: " + check2);
                    // a1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                     startActivity(a1);*/
                    Intent a = new Intent(getBaseContext(), Emp_RegErrorLokal.class);
                    startActivity(a);
                }


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


    private void updatebarctranbalik(final String norefx)
    {
        SQLiteDatabase db=adb_master.getWritableDatabase();
        String upd1="update tbl_barctran set tagtran=1 where noref='"+norefx+"'";
        db.execSQL(upd1);
    }

    private void updatebarctran(final String norefx)
    {
        SQLiteDatabase db=adb_master.getWritableDatabase();
        String upd1="update tbl_barctran set tagtran=2 where noref='"+norefx+"'";
        db.execSQL(upd1);
    }

    public void delay2(final int c,final String s) {
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
               textwarning.setText(s);
               // DialogErrorServer();
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
        dialog = new AlertDialog.Builder(warningloading.this);
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


    private void DialogErrorServer() {
        dialog = new AlertDialog.Builder(warningloading.this);
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


        jumhit2 = 0;

        if (cursor.moveToFirst()) {
            do {
                //    BarangDialog userin = new BarangDialog(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getDouble(3));
                //  userList2.add(userin);



                //Toast.makeText(getBaseContext(),"Att : "+cursor.getString(1),Toast.LENGTH_SHORT).show();
       //    butopen.performClick();
         //       delay(2000);

             //   if (hitbarc < 30) {

               //     delay(5000);
                //    textwarning.setText("X");
                  sqlserverinsert(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6));

             //   } else {
                //    delay(6500);
                 //   textwarning.setText("X");
                   // sqlserverinsert(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6));
              //  }



                jumhit2 = jumhit2 + 1;


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




    private void inserttoserver2(final String norefx) {
        SQLiteDatabase db = adb_master.getWritableDatabase();
        String sql1 = "select barcode,noref,typetran,lokoven,tgl_rec,temp1,idno,temp2 from tbl_barctran where noref='" + ambilnoref + "' ";
        cursor = db.rawQuery(sql1, null);
        cursor.moveToFirst();


        final Handler handler = new Handler();
        handler.post( new Runnable(){
            private int k = 0;




            public void run() {

                final TextView progess = (TextView) findViewById(R.id.textloading);
                double numberOfCells = (k / hitbarc) * 100;

                int num3 = k+1;
                Double result = Double.valueOf( Math.floor ((num3 / 13) * 100));
               // double rounded = (double) Math.round((k+1) * 1000000) / 1000000;
                double rounded = (double) Math.round((k+1))/hitbarc * 100;
                String str = String.format("%.0f", rounded );
                //TextView counter2 = (TextView) findViewById(R.id.textView16);
             //   String abcString2 = Integer.toString (result);


                if ((k+1)==hitbarc)
                {
                    progess.setText(String.valueOf("Completed "+ " No: "+String.valueOf(k+1)+ " of "+String.valueOf(hitbarc)+"\n("+100+"%"+")"));
                    updatebarctran(ambilnoref);
                }else
                {
                    DecimalFormat f = new DecimalFormat("##.000000");
                    double n2 = (k / hitbarc)*100;
                   // String fr = f.format(n2);
                    String str2 = String.format("%.6f", n2 );
                     //double amount = Double.parseDouble(k+1);
                    progess.setSingleLine(false);
                    progess.setText(String.valueOf("Uploading Data No: "+String.valueOf(k+1)+ " of "+String.valueOf(hitbarc)+"\n("+String.valueOf(str)+"%"+")"));


                }





             sqlserverinsert(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6));



                cursor.moveToNext();




                k++;
                if( k < hitbarc)
                {
                    // Here `this` refers to the anonymous `Runnable`

                    handler.postDelayed(this, 1000);
                }
            }
        });

    }





    private void updateTextView(final String s) {
        warningloading.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                EditText tv= (EditText) findViewById(R.id.editremarks);
                tv.setText("Text = "+s);
                tv.invalidate();









            }
        });

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