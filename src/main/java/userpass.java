package com.example.barcodegcnew;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static android.R.layout.simple_spinner_item;

public class userpass extends Activity {

    private String URLstring = AppConfig.IP_SERVER+"caricabang.php";
   // private String URLstringuser = "http://golden-care.co.id/android/cariuser.php";
    private static ProgressDialog mProgressDialog;
    private ArrayList<BarangCabang> barangCabangArrayList;
    private ArrayList<String> names = new ArrayList<String>();
   // List<Data> listPendidikan = new ArrayList<Data>();
    private Spinner spinner;
    EditText user11,pass11;
    ProgressDialog pDialog;
    Spinner spin1;
    TextView kodecabang,namacabang,grp;
    private JSONArray result;
    public static String usergroup;

    AlertDialog.Builder dialog;
    LayoutInflater inflater;
    View dialogView;
    ImageView butpass1;
    Boolean turnon=false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DisplayMetrics metrics = getResources().getDisplayMetrics();
        int screenWidth = (int) (metrics.widthPixels * 0.80);


        setContentView(R.layout.activity_userpass);
      //  getWindow().setLayout(screenWidth, ActionBar.LayoutParams.WRAP_CONTENT);
       // requestWindowFeature(Window.FEATURE_ACTION_BAR);
        //this.setFinishOnTouchOutside(false);

        spinner = findViewById(R.id.spnCabang);
        user11=(EditText) findViewById(R.id.editUsername);
        pass11=(EditText) findViewById(R.id.editPass);
        Button but1=(Button) findViewById(R.id.butProcess);
        spin1=(Spinner) findViewById(R.id.spnCabang);
        kodecabang=(TextView) findViewById(R.id.hasilSpin1);
        namacabang=(TextView) findViewById(R.id.hasilSpin2);
        grp=(TextView) findViewById(R.id.hasilSpin3);
        butpass1=(ImageView) findViewById(R.id.butpass1);




        butpass1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!turnon)
                {
                    butpass1.setImageResource(R.drawable.eyeopen);
                    // editTextPasswrod.setInputType(InputType.TYPE_CLASS_TEXT);
                 //   Toast.makeText(getBaseContext(),"melek",Toast.LENGTH_LONG).show();
                    pass11.setTransformationMethod(HideReturnsTransformationMethod.getInstance());

                    //    ((TransitionDrawable)butpass1.getDrawable()).startTransition(10000);
                    turnon=true;
                }else {
                    butpass1.setImageResource(R.drawable.eyeclose);
                 //   Toast.makeText(getBaseContext(),"merem",Toast.LENGTH_LONG).show();
                    //editTextPasswrod.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    pass11.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    //   ((TransitionDrawable)butpass1.getDrawable()).startTransition(10000);
                    turnon=false;
                }
            }
        });




        but1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                retrieveUser();

        }});





        spin1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // TODO Auto-generated method stub
               namacabang.setText( barangCabangArrayList.get(position).getCabang().toString());
               kodecabang.setText( barangCabangArrayList.get(position).getKodecab().toString());
               grp.setText( barangCabangArrayList.get(position).getGroupho().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub

            }
        });

     //   adapter = new Adapter(MainActivity.this, listPendidikan);
       // spinner_pendidikan.setAdapter(adapter);

      //  retrieveJSON();





}


    @Override
    public void onBackPressed() {
    //Toast.makeText(this,"BackButton",Toast.LENGTH_SHORT).show();
    }

    private void retrieveUser(){

        final String user1 = user11.getText().toString();

        final String pass1 = pass11.getText().toString();

    //Toast.makeText(getBaseContext(),"response: "+user1+pass1, Toast.LENGTH_SHORT).show();



        String url = AppConfig.IP_SERVER+"gcnew/userpass.php";

      //  pDialog.setMessage("Connecting Server..");

       // showDialog();

        StringRequest postRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

            @Override

            public void onResponse(String response) {



                Log.d("BarangActivity", "response :" + response);

             //   Toast.makeText(getBaseContext(),"response: "+response, Toast.LENGTH_SHORT).show();

                processResponse("Connecting Server",response);

                //   finish();

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






                        error.printStackTrace();

                    }

                }

        ) {

            @Override

            protected Map<String, String> getParams()

            {

                Map<String, String>  params = new HashMap<>();

                // the POST parameters:

                params.put("user_id",user1);

                params.put("pass_id",pass1);







                return params;

            }

        };

        Volley.newRequestQueue(this).add(postRequest);


    }



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
        }}



    private void processResponse(String paction, String response){

        try {

            JSONObject jsonObj = new JSONObject(response);

            String errormsg = jsonObj.getString("errormsg");

            if (errormsg.equals("failed"))
            {

                       Intent k=new Intent(getBaseContext(),warningpass.class);
                       startActivity(k);

            }else {


                final String user1 = user11.getText().toString();
//check usergroup
usergroup=errormsg;
                Toast.makeText(getBaseContext(),"User/Pass Succesfully/Group:"+usergroup,Toast.LENGTH_SHORT).show();
                Intent balik=new Intent();

                balik.putExtra("user_id",user1);
                setResult(RESULT_OK,balik);

                finish();
            }



        } catch (JSONException e) {

            Log.d("BarangActivity", "errorJSON");

        }

    }






    private void retrieveJSON() {

        //showSimpleProgressDialog(this, "Loading...","Fetching Json",false);


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
                                JSONArray dataArray  = obj.getJSONArray("data");

                                for (int i = 0; i < dataArray.length(); i++) {

                                    BarangCabang playerModel = new  BarangCabang();
                                    JSONObject dataobj = dataArray.getJSONObject(i);

                                    playerModel.setcabang(dataobj.getString("nama"));
                                    playerModel.setKodecab(dataobj.getString("kode_cabang"));
                                    playerModel.setGroupho(dataobj.getString("groupho"));
                                  //  kodecabang.setText(dataobj.getString("kode_cabang"));
                                   // namacabang.setText(dataobj.getString("nama"));
                                    /*
                                    playerModel.setCountry(dataobj.getString("country"));
                                    playerModel.setCity(dataobj.getString("city"));
                                    playerModel.setImgURL(dataobj.getString("imgURL"));*/

                                    barangCabangArrayList.add(playerModel);

                                }

                                for (int i = 0; i < barangCabangArrayList.size(); i++){
                                    names.add(barangCabangArrayList.get(i).getCabang().toString());


                                }

                                ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(userpass.this, simple_spinner_item, names);
                                spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
                                spinner.setAdapter(spinnerArrayAdapter);
                              //  removeSimpleProgressDialog();



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
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        requestQueue.add(stringRequest);


    }

    /*

    private String getKodecabang(int position){
        String kodecabang="";
        try {

            //Getting object of given index

            JSONObject json = result.getJSONObject(position);

            //Fetching name from that object
            kodecabang = json.getString(config.TAG_kode_cabang);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //Returning the name
        return kodecabang;
    }

    private String getName(int position){
        String name="";
        try {

            //Getting object of given index

            JSONObject json = result.getJSONObject(position);

            //Fetching name from that object
            name = json.getString(config.TAG_NAME);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //Returning the name
        return name;
    }
*/






    public static void removeSimpleProgressDialog() {
        try {
            if (mProgressDialog != null) {
                if (mProgressDialog.isShowing()) {
                    mProgressDialog.dismiss();
                    mProgressDialog = null;
                }
            }
        } catch (IllegalArgumentException ie) {
            ie.printStackTrace();

        } catch (RuntimeException re) {
            re.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void showSimpleProgressDialog(Context context, String title,
                                                String msg, boolean isCancelable) {
        try {
            if (mProgressDialog == null) {
                mProgressDialog = ProgressDialog.show(context, title, msg);
                mProgressDialog.setCancelable(isCancelable);
            }

            if (!mProgressDialog.isShowing()) {
                mProgressDialog.show();
            }

        } catch (IllegalArgumentException ie) {
            ie.printStackTrace();
        } catch (RuntimeException re) {
            re.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
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


    private void DialogKoneksi() {
        dialog = new AlertDialog.Builder(this);
        inflater = getLayoutInflater();
        dialogView = inflater.inflate(R.layout.koneksi_warning, null);
        dialog.setView(dialogView);
        //  dialog.setCancelable(true);
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
                Intent balikretry=new Intent();
                balikretry.putExtra("balikretry","ulang");
                setResult(RESULT_OK,balikretry);

                finish();
                dialog.dismiss();
                //  finish();
                return;
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

    private void DialogServer() {
        dialog = new AlertDialog.Builder(this);
        inflater = getLayoutInflater();
        dialogView = inflater.inflate(R.layout.server_warning, null);
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
/*
        dialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });*/

        dialog.show();
    }

}
