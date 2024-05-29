package com.example.barcodegcnew;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class BarcodeEntry extends AppCompatActivity {

    TextView hasilscan;
    Button butsave,butcancel;
    ProgressDialog pDialog;
    EditText pj,lb,tg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barcode_entry);
        pDialog=new ProgressDialog(this);

        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Barcode Entry");
        setSupportActionBar(toolbar);

        Intent ambil=getIntent();
        String scan1=ambil.getStringExtra("barcode");
        hasilscan=findViewById(R.id.hasilscan);
        hasilscan.setText(scan1);
        butsave=findViewById(R.id.btsave);
        butcancel=findViewById(R.id.btcancel);
        pj=findViewById(R.id.p);
        lb=findViewById(R.id.l);
        tg=findViewById(R.id.t);


        butsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              savedatavolley();

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

    private void savedatavolley(){

      //  refreshFlag="1";




     //   final  String pi1=editJudul.getText().toString();
     //   final  String idno2=editIDNO.getText().toString();




        String url = AppConfig.IP_SERVER+"sawmill/androidsave_saw1.php";

        pDialog.setMessage("Save Data Transaksi");

        showDialog();

        StringRequest postRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

            @Override

            public void onResponse(String response) {

                hideDialog();

                Log.d("BarangActivity", "response :" + response);

                // Toast.makeText(getBaseContext(),"response: "+response, Toast.LENGTH_SHORT).show();

                processResponse("Save Data",response);

                //   finish();

            }

        },

                new Response.ErrorListener() {

                    @Override

                    public void onErrorResponse(VolleyError error) {




                        if (error instanceof NetworkError) {
                            hideDialog();
                            Toast.makeText(getBaseContext(),
                                    "Oops. Network Error",
                                    Toast.LENGTH_LONG).show();

                        } else if (error instanceof ServerError) {
                            hideDialog();
                            Toast.makeText(getBaseContext(),
                                    "Oops. Server Time out",
                                    Toast.LENGTH_LONG).show();
                        } else if (error instanceof AuthFailureError) {
                            hideDialog();
                        } else if (error instanceof ParseError) {
                            hideDialog();
                        } else if (error instanceof NoConnectionError) {
                            hideDialog();
                            Toast.makeText(getBaseContext(),
                                    "Oops. No Connection!",
                                    Toast.LENGTH_LONG).show();
                        } else if (error instanceof TimeoutError) {
                            hideDialog();
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

                params.put("p",pj.getText().toString());

                params.put("l",lb.getText().toString());

                params.put("t",tg.getText().toString());
                //params.put("grade",tg.getText().toString());

                // params.put("nama",nama1);




              /*  if (action_flag.equals("add")){

                    params.put("id","0");

                }else{
                    //for add setup saving bro...
                    params.put("id","99");

                }*/

                return params;

            }

        };

        Volley.newRequestQueue(this).add(postRequest);
        postRequest.setRetryPolicy(new DefaultRetryPolicy(100 * 1000, 1, 1.0f));

    }



    public void onErrorResponse(VolleyError error) {
        if (error instanceof NetworkError) {
            //  DialogErrorNetwork();
            Toast.makeText(this,
                    "Oops. Error Network",
                    Toast.LENGTH_LONG).show();
        } else if (error instanceof ServerError) {
            //  DialogErrorServer();
            Toast.makeText(this,
                    "Oops. Server Time out",
                    Toast.LENGTH_LONG).show();
        } else if (error instanceof AuthFailureError) {
        } else if (error instanceof ParseError) {
        } else if (error instanceof NoConnectionError) {
            //  DialogErrorNetwork();
            Toast.makeText(this,
                    "Oops. No Connection!",
                    Toast.LENGTH_LONG).show();
        } else if (error instanceof TimeoutError) {
            //   DialogErrorNetwork();
            Toast.makeText(this,
                    "Oops. Timeout error!",
                    Toast.LENGTH_LONG).show();
        }}



    private void processResponse(String paction, String response){

        try {

            JSONObject jsonObj = new JSONObject(response);

            String errormsg = jsonObj.getString("errormsg");

       /*     if (errormsg.equals("fail"))
            {
                hideDialog();
                DialogForm();

            }else {

                hideDialog();
                finish();

            }*/

            //Toast.makeText(getBaseContext(),paction+" "+errormsg,Toast.LENGTH_SHORT).show();

        } catch (JSONException e) {

            Log.d("BarangActivity", "errorJSON");

        }

    }


}