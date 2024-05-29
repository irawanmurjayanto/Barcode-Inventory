package com.example.barcodegcnew;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
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

public class Emp_RegItemdesc extends Activity {
    private RecyclerView mRecyclerViewdlg;
    private barangPersonAdapter rvAdapterdlg;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<barangPerson> barangListDialog = new ArrayList<barangPerson>();
    private Context context = Emp_RegItemdesc.this;
    private static  final int REQUEST_CODE_EDIT =2;

    EditText carinik;
    ProgressDialog pDialog;
    TextView textNIK,item_code,item_desc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emp__reg_itemdesc);
        carinik=(EditText) findViewById(R.id.textcariitem);
        textNIK=(TextView) findViewById(R.id.textNotrans);


        item_desc=(TextView) findViewById(R.id.item_desc);


        mRecyclerViewdlg=(RecyclerView) findViewById(R.id.my_recycler_view_dialog);
        mRecyclerViewdlg.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);

        mRecyclerViewdlg.setLayoutManager(mLayoutManager);

        pDialog=new ProgressDialog(this);
      //  loaddatanik();


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
        rvAdapterdlg = new barangPersonAdapter(barangListDialog);

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

                        balik.putExtra("qty",String.valueOf(barang.getQty()));

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
      // final String getidovn=idovn.getStringExtra("idoven");

      // Toast.makeText(this,"Load Data "+getidovn,Toast.LENGTH_LONG).show();
        String url = AppConfig.IP_SERVER+"gcnew/list_item.php";

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

                params.put("cari",carinik.getText().toString());

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
               //  objectbarang.setIDNO(obj.getInt("idno"));
                objectbarang.setItem_code(obj.getString("item_code"));
                objectbarang.setItem_desc(obj.getString("item_desc"));
                objectbarang.setQty(obj.getDouble("conv_qty"));

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
