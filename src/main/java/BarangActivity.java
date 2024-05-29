package com.example.barcodegcnew;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.text.DecimalFormat;

public class BarangActivity extends AppCompatActivity {

    private EditText editTextKode, editTextNama, editTextHarga,editTextHarga_beli;


    private Barang barang;

    private String action_flag="add";

    private String refreshFlag="0";

    private static final String TAG="test test";

    private static  final int REQUEST_CODE_ADD =1;

    private static  final int REQUEST_CODE_EDIT =2;

    private ProgressDialog pDialog;
    Spinner spin1;
    Button but1;
    TextView kodecabang,textIDNO, textviewIDNO;
    ImageView butbarc;
    EditText edit1;

    private static final String DATABASE_NAME = "master.db";
    SQLiteDatabase mDatabase;




    @Override

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_barang);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
       // spin1=(Spinner) findViewById(R.id.spnCabang);
      //  kodecabang=(TextView) findViewById(R.id.hasilSpin1);
    //    butbarc=(ImageView) findViewById(R.id.butbarc );
        edit1=(EditText) findViewById(R.id.editTextKode);
        textviewIDNO=(TextView) findViewById(R.id.textViewIDNO2);
        mDatabase = openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);



     /*   butbarc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getBaseContext(), "Load barcode", Toast.LENGTH_LONG).show();
                IntentIntegrator integrator = new IntentIntegrator(BarangActivity.this);
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
        // setSupportActionBar(toolbar);



     //   getSupportActionBar().setDisplayHomeAsUpEnabled(true);

       // barang = new Barang();

        initUI();

        //initEvent();

        Intent intent = getIntent();

        if (intent.hasExtra("barang")) {

            barang = (Barang) intent.getSerializableExtra("barang");

            Log.d(TAG, "Barang : " + barang.toString());

            setData(barang);

            action_flag = "edit";

            editTextKode.setEnabled(true);

        }else{

           // barang = new Barang();

        }

    }

    private void setData(Barang barang) {

       // Locale localeID = new Locale("in", "ID");
        //NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);
        //detailHarga.setText(formatRupiah.format((double)hargarumah));

       // DecimalFormat numberFormat = new DecimalFormat("#,###,###");
       // str = numberFormat.format(-01234567.890);
        DecimalFormat formatter = new DecimalFormat("#");

        editTextKode.setText(barang.getKode());
        textviewIDNO.setText(String.valueOf(barang.getId()));

    /*    editTextNama.setText(barang.getNama());

        editTextHarga.setText(String.valueOf(formatter.format(barang.getHarga())));

        editTextHarga_beli.setText(String.valueOf(barang.getHarga_beli()));

        textviewIDNO.setText(String.valueOf(barang.getId()));*/

    }

    private void initUI() {

        pDialog = new ProgressDialog(BarangActivity.this);
        editTextKode = (EditText) findViewById(R.id.editTextKode);
        textviewIDNO=(TextView) findViewById(R.id.textViewIDNO2);
     /*   editTextNama = (EditText) findViewById(R.id.editTextNama);
        editTextHarga = (EditText) findViewById(R.id.editTextHarga);
        textviewIDNO=(TextView) findViewById(R.id.textViewIDNO2);
        editTextHarga_beli = (EditText) findViewById(R.id.editTextHarga_beli);*/

    }

    @Override

    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.

        getMenuInflater().inflate(R.menu.menu_barang, menu);

        return true;

    }

    @Override

    public boolean onOptionsItemSelected(MenuItem item) {

        // Handle action bar item clicks here. The action bar will

        // automatically handle clicks on the Home/Up button, so long

        // as you specify a parent activity in AndroidManifest.xml.

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement



        if (id == R.id.action_save) {

            saveDataVolley();

            return true;

        }else  if (id == R.id.action_delete) {

            hapusData();

            return true;
/*
        }else  if (id == R.id.action_transfer) {
            Intent bukatransfer=getIntent();
            final String kodecab=bukatransfer.getStringExtra("kodecabang");
            final String kode=editTextKode.getText().toString();
            final String nama1=editTextNama.getText().toString();
            final String hargabeli=editTextHarga_beli.getText().toString();
            final String hargajual=editTextHarga.getText().toString();

            Intent transfer2=new Intent(this,BarangTransfer2.class);
          transfer2.putExtra("kodecabang",kodecab);
            transfer2.putExtra("kode",kode);
            transfer2.putExtra("nama",nama1);
            transfer2.putExtra("hargabeli",hargabeli);
            transfer2.putExtra("hargajual",hargajual);


            startActivityForResult(transfer2,REQUEST_CODE_EDIT);
           // startActivity(transfer2);
            return true;*/

        }else  if (id == R.id.action_close) {

            finish();

            return true;

        }



        return super.onOptionsItemSelected(item);

    }



    private void hapusData() {

        new AlertDialog.Builder(this)

                .setTitle("Data Barang")

                .setMessage("Hapus Data " + barang.getKode() + " ?")

                .setIcon(android.R.drawable.ic_dialog_alert)

                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {

                        hapusDataServer();

                        refreshFlag = "1";

                        //finish();

                    }

                })

                .setNegativeButton(android.R.string.no, null).show();

    }

    @Override

    public void finish() {

        System.gc();

        Intent data = new Intent();

        data.putExtra("deleteData", refreshFlag);

        //  data.putExtra("barang", barang);

        setResult(RESULT_OK, data);

        super.finish();

    }

    private void saveDataVolley(){

         refreshFlag="1";
        Intent intent2=getIntent();
     //  final String kodecabang1=intent2.getStringExtra("kodecabang");

      //  final String nama = editTextNama.getText().toString();
        final String kode = editTextKode.getText().toString();
    //    final String harga = editTextHarga.getText().toString();
     //   final String harga_beli = editTextHarga_beli.getText().toString();
      final String IDNO = textviewIDNO.getText().toString();

        pDialog.setMessage("Save Data Barang...");
        showDialog();


        refreshFlag="1";

        if (action_flag.equals("add")) {


            String insertSQL = "INSERT INTO tbl_barang \n" +
                    "(kode, nama, harga,harga_beli)\n" +
                    "VALUES \n" +
                    "(?, ?, ? ,?);";

            //using the same method execsql for inserting values
            //this time it has two parameters
            //first is the sql string and second is the parameters that is to be binded with the query
            mDatabase.execSQL(insertSQL, new String[]{kode, "x", "0","0"});

            Toast.makeText(this, "Item Master Added Successfully", Toast.LENGTH_SHORT).show();


        }else
        {
            Toast.makeText(this, "kode "+kode+"-"+String.valueOf(IDNO), Toast.LENGTH_SHORT).show();
            String sql = "UPDATE tbl_barang \n" +
                    "SET kode = ?, \n" +
                    "nama = ?, \n" +
                    "harga = ?, \n" +
                    "harga_beli = ? \n" +
                    "WHERE idno = ?;\n";

          mDatabase.execSQL(sql, new String[]{kode, "x", "0","0", String.valueOf(IDNO)});
        }
      //  Toast.makeText(getBaseContext(), "Employee Updated", Toast.LENGTH_SHORT).show();

        //Intent intent2=getIntent();
        //  final String kodecabang11=intent2.getStringExtra("kodecabang");
        // final String namacabang11=intent2.getStringExtra("namacabang");
        //final String groupho11=intent2.getStringExtra("groupho");
        //   final String username11=intent2.getStringExtra("username");

        Intent balik4=new Intent();
        //balik4.putExtra("kodecab",kodecabang11);
        //balik4.putExtra("namacab",namacabang11);
        //  balik4.putExtra("user1",username11);
        //balik4.putExtra("groupho",groupho11);
        setResult(RESULT_OK,balik4);
        finish();

      pDialog.hide();
    }



    private void hapusDataServer(){

        refreshFlag="1";

        refreshFlag="1";

        final String IDNO = textviewIDNO.getText().toString();

        String sql = "DELETE FROM tbl_barang \n" +
                "WHERE idno = ?;\n";

        mDatabase.execSQL(sql, new String[]{ String.valueOf(IDNO)});
        Intent balik4=new Intent();

        //balik4.putExtra("groupho",groupho11);
        setResult(RESULT_OK,balik4);
        finish();


    }

    private void showDialog() {

        if (!pDialog.isShowing())

        pDialog.show();

    }

    private void hideDialog() {

        if (pDialog.isShowing())

        pDialog.dismiss();

    }





    //barcode area
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        // IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);

        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);

        Toast.makeText(getBaseContext(),"test hasil"+requestCode,Toast.LENGTH_LONG).show();

        // EditText tvResult = (EditText) findViewById(R.id.txtEditResult);
        //TextView tvResult2=(TextView) findViewById(R.id.hasilspin3);



        if (scanningResult != null) {
            if(scanningResult.getContents() != null){
                //we have a result
                edit1.setText(scanningResult.getContents());


                new CountDownTimer(1100, 1000) {

                    public void onTick(long millisUntilFinished) {
                        //  totalrp.setText("seconds remaining: " + millisUntilFinished / 1000);
                    }

                    public void onFinish() {
                        //mTextField.setText("done!");
                      //  butRef.performClick();

                    }
                }.start();


                //tvResult.setText(scanningResult.getContents());
                // codeFormat = scanningResult.getFormatName();

                //ambilbarcode=tvResult.getText().toString().trim();
                //tvResult2.setText(intent.getStringExtra("SCAN_RESULT"));

                // display it on screen
                //formatTxt.setText("FORMAT: " + codeFormat);
                //contentTxt.setText("CONTENT: " + codeContent);

            }else{
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
            }
        }else{
            //Toast toast = Toast.makeText(getApplicationContext(),"No scan data received!", Toast.LENGTH_SHORT);
            //toast.show();
        }


        /*
        if (requestCode == 0) {
            //   TextView tvStatus = (TextView) findViewById(R.id.tvStatus);
            EditText tvResult = (EditText) findViewById(R.id.txtEditResult);
            TextView tvResult2=(TextView) findViewById(R.id.hasilspin3);

            if (resultCode == RESULT_OK) {
                //  tvStatus.setText(intent.getStringExtra("SCAN_RESULT_FORMAT"));
                tvResult.setText(intent.getStringExtra("SCAN_RESULT"));
                ambilbarcode=tvResult.getText().toString().trim();
                tvResult2.setText(intent.getStringExtra("SCAN_RESULT"));
            } else if (resultCode == RESULT_CANCELED) {
                //tvStatus.setText("Press a button to start a scan.");
                tvResult.setText("Scan cancelled.");
            }
        }

///btas*/





    }




}

