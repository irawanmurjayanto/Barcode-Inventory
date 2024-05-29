package com.example.barcodegcnew;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Environment;
import android.os.StrictMode;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.RadioGroup;
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

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

public class Report extends AppCompatActivity {

    private ActionBar toolbar;

    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat dateFormatter,dateFormatter2,dateFormatterdoc;
    private TextView tvDateResult1,tvDateResult2,hasil1,hasil2;
    private ImageButton btDatePicker1,btDatePicker2;
    private ProgressDialog pDialog;
    private WebView myWebView;
    private Button btsubmit;
    private RadioGroup radiogrp;
    private static  final int REQUEST_CODE_ADD =1;
    private static  final int REQUEST_CODE_EDIT =2;
    public static String webhasil1,webhasil2,kodecabang,groupho,tgl1,tgl2;
    private static String nodoc;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        // toolbar.setTitle("Accounting Report");
        //  getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // getSupportActionBar().setHomeButtonEnabled(true);
        pDialog = new ProgressDialog(Report.this);

        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        dateFormatter2 = new SimpleDateFormat("yyyy-MM-dd", Locale.US);

        tvDateResult1 = (TextView) findViewById(R.id.tv_dateresult1);
        tvDateResult2 = (TextView) findViewById(R.id.tv_dateresult2);
        hasil1 = (TextView) findViewById(R.id.textHasil1);
        hasil2 = (TextView) findViewById(R.id.textHasil2);
        btDatePicker1 = (ImageButton) findViewById(R.id.bt_datepicker1);
        btDatePicker2 = (ImageButton) findViewById(R.id.bt_datepicker2);
        btsubmit = (Button) findViewById(R.id.bt_submit);
        radiogrp = (RadioGroup) findViewById(R.id.acc_rpt);


        btDatePicker1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDateDialog1();
            }
        });

        btDatePicker2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDateDialog2();
            }
        });

        Calendar newDate = Calendar.getInstance();

        dateFormatterdoc = new SimpleDateFormat("ddMMyyyy_HHmmss", Locale.US);
        Date currentTime = Calendar.getInstance().getTime();
        nodoc=MainActivity.user_idbuffer+dateFormatterdoc.format(newDate.getTime());


        //  newDate.set(year, monthOfYear, dayOfMonth);


        tvDateResult1.setText(dateFormatter.format(newDate.getTime()));
        hasil1.setText(dateFormatter2.format(newDate.getTime()));



        //   Calendar newDate = Calendar.getInstance();
        // newDate.set(year, monthOfYear, dayOfMonth);


        tvDateResult2.setText(dateFormatter.format(newDate.getTime()));
        hasil2.setText(dateFormatter2.format(newDate.getTime()));





        btsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final int a1=hasil1.length();
                final int a2=hasil2.length();
                tgl1=hasil1.getText().toString();
                tgl2=hasil2.getText().toString();



                int Id = radiogrp.getCheckedRadioButtonId();
                switch(Id){
                    case R.id.omzetDate:
                        if (a1==0||a2==0)
                        {
                            Toast.makeText(getBaseContext(),"Tanggal tidak boleh kosong", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        Intent a= new Intent(getBaseContext(),printSalesgroupDetail.class);
                        startActivity(a);
                        break;

                    case R.id.exportsales:
                        if (a1==0||a2==0)
                        {
                            Toast.makeText(getBaseContext(),"Tanggal tidak boleh kosong", Toast.LENGTH_SHORT).show();
                            return;
                        }
                      DownloadDataTransx();
                        break;

                   /* case R.id.historybarcode:
                        if (a1==0||a2==0)
                        {
                            Toast.makeText(getBaseContext(),"Tanggal tidak boleh kosong", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        Intent a3= new Intent(getBaseContext(),HistoryBarcode.class);
                        startActivity(a3);
                        break;*/

                }

            }
        });



    }


    private void printWebViewtran() {
        final String webhasil1=hasil1.getText().toString();
        final String webhasil2=hasil2.getText().toString();


        // WebView myWebView = (WebView) findViewById(R.id.webView);


        WebView webView = new WebView(this);
        webView.setWebViewClient(new WebViewClient() {

            public boolean shouldOverrideUrlLoading(WebView view,
                                                    WebResourceRequest request)
            {
                pDialog.setMessage("Loading Data ...");
                showDialog();
                return false;
            }

            @Override
            public void onPageFinished(WebView view, String url)
            {
                hideDialog();
                createWebPrintJob(view);
                myWebView = null;
            }
        });

        String htmlDocument =
                "<html><body><h1>Android Print Test</h1><p>"
                        + "This is some sample content.</p></body></html>";

        // webView.loadDataWithBaseURL("detik.com", htmlDocument,

        //  "text/HTML", "UTF-8", null);
       /* webView.getSettings().setSupportZoom(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setDisplayZoomControls(false);
        // Baris di bawah untuk menambahkan scrollbar di dalam WebView-nya
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl("https://www.codepolitan.com");*/
        //pDialog = new ProgressDialog(Transaksi_POS.this);
        //pDialog.setMessage("Loading Data ...");
        //pDialog.show();
        //Toast.makeText(getBaseContext(),"No Transaksi: "+notrans2, Toast.LENGTH_SHORT).show();
        Intent intent=getIntent();
        final String kodecabang1=intent.getStringExtra("kodecabang");
        String javascript="javascript:document.getElementsByName('viewport')[0].setAttribute('content', 'initial-scale=1.0,maximum-scale=10.0');";
        webView.loadUrl(AppConfig.IP_SERVER+"reportomzetbytran.php?date1="+webhasil1+"&date2="+webhasil2+"&kodecab="+kodecabang1);
        myWebView = webView;

    }


    private void printWebViewomzet() {
        final String webhasil1=hasil1.getText().toString();
        final String webhasil2=hasil2.getText().toString();

        WebView webView = new WebView(this);
        webView.setWebViewClient(new WebViewClient() {

            public boolean shouldOverrideUrlLoading(WebView view,
                                                    WebResourceRequest request)
            {
                pDialog.setMessage("Loading Data ...");
                showDialog();
                return false;
            }

            @Override
            public void onPageFinished(WebView view, String url)
            {
                hideDialog();
                createWebPrintJob(view);
                myWebView = null;
            }
        });

        String htmlDocument =
                "<html><body><h1>Android Print Test</h1><p>"
                        + "This is some sample content.</p></body></html>";

        // webView.loadDataWithBaseURL("detik.com", htmlDocument,

        //  "text/HTML", "UTF-8", null);
       /* webView.getSettings().setSupportZoom(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setDisplayZoomControls(false);
        // Baris di bawah untuk menambahkan scrollbar di dalam WebView-nya
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl("https://www.codepolitan.com");*/
        //pDialog = new ProgressDialog(Transaksi_POS.this);
        //pDialog.setMessage("Loading Data ...");
        //pDialog.show();
        //Toast.makeText(getBaseContext(),"No Transaksi: "+notrans2, Toast.LENGTH_SHORT).show();
        Intent intent=getIntent();
        final String kodecabang1=intent.getStringExtra("kodecabang");
        String javascript="javascript:document.getElementsByName('viewport')[0].setAttribute('content', 'initial-scale=1.0,maximum-scale=10.0');";
        webView.loadUrl(AppConfig.IP_SERVER+"reportomzetbydate.php?date1="+webhasil1+"&date2="+webhasil2+"&kodecab="+kodecabang1);
        myWebView = webView;

    }


    private void printWebViewStocksaja() {
        final String webhasil1=hasil1.getText().toString();
        final String webhasil2=hasil2.getText().toString();

        Intent intent2=getIntent();
        final String kodecabang11=intent2.getStringExtra("kodecabang");
        final String namacabang11=intent2.getStringExtra("namacabang");
        final String groupho11=intent2.getStringExtra("groupho");
        final String username11=intent2.getStringExtra("username");

        WebView webView = new WebView(this);
        webView.setWebViewClient(new WebViewClient() {

            public boolean shouldOverrideUrlLoading(WebView view,
                                                    WebResourceRequest request)
            {
                pDialog.setMessage("Loading Data ...");
                showDialog();
                return false;
            }

            @Override
            public void onPageFinished(WebView view, String url)
            {
                hideDialog();
                createWebPrintJob(view);
                myWebView = null;
            }
        });

        String htmlDocument =
                "<html><body><h1>Android Print Test</h1><p>"
                        + "This is some sample content.</p></body></html>";

        // webView.loadDataWithBaseURL("detik.com", htmlDocument,

        //  "text/HTML", "UTF-8", null);
       /* webView.getSettings().setSupportZoom(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setDisplayZoomControls(false);
        // Baris di bawah untuk menambahkan scrollbar di dalam WebView-nya
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl("https://www.codepolitan.com");*/
        //pDialog = new ProgressDialog(Transaksi_POS.this);
        //pDialog.setMessage("Loading Data ...");
        //pDialog.show();
        //Toast.makeText(getBaseContext(),"No Transaksi: "+notrans2, Toast.LENGTH_SHORT).show();
        //  Intent intent=getIntent();
        // final String kodecabang1=intent.getStringExtra("kodecabang");
        Toast.makeText(getBaseContext(),"GroupHO: "+groupho11, Toast.LENGTH_LONG).show();

        if (groupho11.equals("1")) {
            String javascript="javascript:document.getElementsByName('viewport')[0].setAttribute('content', 'initial-scale=1.0,maximum-scale=10.0');";
            webView.loadUrl(AppConfig.IP_SERVER+"laststockho.php?kodecab=" + kodecabang11);

        }else
        {
            String javascript="javascript:document.getElementsByName('viewport')[0].setAttribute('content', 'initial-scale=1.0,maximum-scale=10.0');";
            webView.loadUrl(AppConfig.IP_SERVER+"laststockcabang.php?kodecab="+kodecabang11);
        }
        myWebView = webView;
    }



    private void printWebViewMutasisaja2() {
        webhasil1=hasil1.getText().toString();
        webhasil2=hasil2.getText().toString();

        Intent intent2=getIntent();
        kodecabang=intent2.getStringExtra("kodecabang");
        final String namacabang11=intent2.getStringExtra("namacabang");
        groupho=intent2.getStringExtra("groupho");
        final String username11=intent2.getStringExtra("username");



        // webView.loadDataWithBaseURL("detik.com", htmlDocument,

        //  "text/HTML", "UTF-8", null);
       /* webView.getSettings().setSupportZoom(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setDisplayZoomControls(false);
        // Baris di bawah untuk menambahkan scrollbar di dalam WebView-nya
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl("https://www.codepolitan.com");*/
        //pDialog = new ProgressDialog(Transaksi_POS.this);
        //pDialog.setMessage("Loading Data ...");
        //pDialog.show();
        //Toast.makeText(getBaseContext(),"No Transaksi: "+notrans2, Toast.LENGTH_SHORT).show();
        //  Intent intent=getIntent();
        //final String kodecabang1=intent.getStringExtra("kodecabang");


        //web_view.loadUrl("https://www.tutorialspoint.com/");

     //   Intent a= new Intent(getBaseContext(),printMutasi.class);
       // startActivity(a);

    }






    private void printWebViewMutasisaja() {
        final String webhasil1=hasil1.getText().toString();
        final String webhasil2=hasil2.getText().toString();

        Intent intent2=getIntent();
        final String kodecabang11=intent2.getStringExtra("kodecabang");
        final String namacabang11=intent2.getStringExtra("namacabang");
        final String groupho11=intent2.getStringExtra("groupho");
        final String username11=intent2.getStringExtra("username");

        WebView webView = new WebView(this);

        //  WebSettings webSettings = myWebView.getSettings();
        // webSettings.setJavaScriptEnabled(true);

        // Settings so page loads zoomed-out all the way.
        // webSettings.setLoadWithOverviewMode(true);
        // webSettings.setUseWideViewPort(true);
        // webSettings.setBuiltInZoomControls(true);

        webView.setWebViewClient(new WebViewClient() {

            public boolean shouldOverrideUrlLoading(WebView view,
                                                    WebResourceRequest request)
            {
                pDialog.setMessage("Loading Data ...");
                showDialog();
                return false;
            }

            @Override
            public void onPageFinished(WebView view, String url)
            {



                hideDialog();
                createWebPrintJob(view);
                myWebView = null;
            }
        });

        String htmlDocument =
                "<html><body><h1>Android Print Test</h1><p>"
                        + "This is some sample content.</p></body></html>";

        // webView.loadDataWithBaseURL("detik.com", htmlDocument,

        //  "text/HTML", "UTF-8", null);
       /* webView.getSettings().setSupportZoom(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setDisplayZoomControls(false);
        // Baris di bawah untuk menambahkan scrollbar di dalam WebView-nya
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl("https://www.codepolitan.com");*/
        //pDialog = new ProgressDialog(Transaksi_POS.this);
        //pDialog.setMessage("Loading Data ...");
        //pDialog.show();
        //Toast.makeText(getBaseContext(),"No Transaksi: "+notrans2, Toast.LENGTH_SHORT).show();
        //  Intent intent=getIntent();
        //final String kodecabang1=intent.getStringExtra("kodecabang");
        if (groupho11.equals("1")) {
            // String javascript="javascript:document.getElementsByName('viewport')[0].setAttribute('AppConfig.IP_SERVER+\"/stockmutasiHO.php?date1=\" + webhasil1 + \"&date2=\" + webhasil2 + \"&kodecab=\" + kodecabang11', 'initial-scale=1.0,maximum-scale=10.0');";
            webView.getSettings().setBuiltInZoomControls(true);
            webView.getSettings().setDisplayZoomControls(false);
            webView.loadUrl(AppConfig.IP_SERVER+"stockmutasiHO.php?date1=" + webhasil1 + "&date2=" + webhasil2 + "&kodecab=" + kodecabang11);
            // webView.loadUrl(javascript);
        }else {
            //String javascript="javascript:document.getElementsByName('viewport')[0].setAttribute('AppConfig.IP_SERVER+\"/stockmutasicabang.php?date1=\" + webhasil1 + \"&date2=\" + webhasil2 + \"&kodecab=\" + kodecabang11', 'initial-scale=1.0,maximum-scale=10.0');";
            webView.getSettings().setBuiltInZoomControls(true);
            webView.getSettings().setDisplayZoomControls(false);
            webView.loadUrl(AppConfig.IP_SERVER+"stockmutasicabang.php?date1=" + webhasil1 + "&date2=" + webhasil2 + "&kodecab=" + kodecabang11);
            //webView.loadUrl(javascript);
        }
        myWebView = webView;

    }








    private void showDialog() {

        if (!pDialog.isShowing())

            pDialog.show();

    }

    private void hideDialog() {

        if (pDialog.isShowing())

            pDialog.dismiss();

    }


    private void createWebPrintJob(WebView webView) {

        PrintManager printManager = (PrintManager) this
                .getSystemService(Context.PRINT_SERVICE);

        PrintDocumentAdapter printAdapter =
                webView.createPrintDocumentAdapter("MyDocument");

        String jobName = getString(R.string.app_name) + " Print Test";

        printManager.print(jobName, printAdapter,
                new PrintAttributes.Builder().build());

        // printJobs.add(printJob);
/*
        // Get a PrintManager instance
        PrintManager printManager = (PrintManager) getActivity()
                .getSystemService(Context.PRINT_SERVICE);
        PrintManager printManager = (PrintManager) Transaksi_POS.getSystemService(Context.PRINT_SERVICE);

        String jobName = getString(R.string.app_name) + " Document";

        // Get a print adapter instance
        PrintDocumentAdapter printAdapter = webView.createPrintDocumentAdapter(jobName);

        // Create a print job with name and adapter instance
        PrintJob printJob = printManager.print(jobName, printAdapter,
                new PrintAttributes.Builder().build());

        // Save the job object for later status checking
        printJobs.add(printJob);
        */

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


                tvDateResult1.setText(dateFormatter.format(newDate.getTime()));
                hasil1.setText(dateFormatter2.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));


        datePickerDialog.show();
    }


    private void showDateDialog2(){

        /**
         * Calendar untuk mendapatkan tanggal sekarang
         */
        Calendar newCalendar = Calendar.getInstance();

        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);


                tvDateResult2.setText(dateFormatter.format(newDate.getTime()));
                hasil2.setText(dateFormatter2.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));


        datePickerDialog.show();
    }






    private void DownloadDataTrans2(){
        final String webhasil1=hasil1.getText().toString();
        final String webhasil2=hasil2.getText().toString();
        Intent intent=getIntent();
        final String kodecabang1=intent.getStringExtra("kodecabang");
        //   final String namacari=cari1.getText().toString();
        // String url = AppConfig.IP_SERVER+"/listdata.php";
        String url ="http://golden-care.co.id/android/reportomzetbytrandown.php";
        //   pDialog.setMessage("Download Data Barang...");
        // Toast.makeText(this,"test masuk load server",Toast.LENGTH_LONG).show();
        //showDialog();



        WritableWorkbook workbook;

        final String date1=hasil1.getText().toString();
        final String date2=hasil2.getText().toString();


        String Fnamexls = "itemtrans5" + ".xls";

        File directory = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/exceldata");
        directory.mkdir();

        File file = new File(directory, Fnamexls);


        WorkbookSettings wbSettings = new WorkbookSettings();
        wbSettings.setLocale(new Locale("en", "EN"));
        //   Toast.makeText(getBaseContext(), "no 1", Toast.LENGTH_LONG).show();
        int a = 1;









        //batas atas--------------------------------------------------
        //  WritableWorkbook workbook;
        try {




            workbook = Workbook.createWorkbook(file, wbSettings);
            //     Toast.makeText(getBaseContext(), "no 1a", Toast.LENGTH_LONG).show();
            WritableSheet sheet = workbook.createSheet("First Sheet", 0);


            //Label I1 = new Label(0,i,String.valueOf(a));
            //Label I1 = new Label(0,i+1,String.valueOf(obj.getString("kode")));
            //Label I2 = new Label(1, i+1, String.valueOf(obj.getString("nama")));
            //Label I3 = new Label(2,i+1,String.valueOf(obj.getString("harga")));
            //Label I4 = new Label(3,i+1,String.valueOf(obj.getString("harga_beli")));


            try {

                Toast.makeText(getBaseContext(), "NO 2 " , Toast.LENGTH_LONG).show();
                Date currentTime = Calendar.getInstance().getTime();

                Label H0 = new Label(0, 0, "Download Date : " + date1 + " s/d " + date2);
                Label H1 = new Label(0, 1, "jjjjjjj");
                Label H2 = new Label(1, 1, "xxxx222");
                Label H3 = new Label(2, 1, "Item Code");
                Label H4 = new Label(3, 1, "Item Desc");
                Label H5 = new Label(4, 1, "Qty");
                Label H6 = new Label(5, 1, "Sell Price");
                Label H7 = new Label(6, 1, "Purchase Price");
                Label H8 = new Label(7, 1, "Discount");
                Label H9 = new Label(8, 1, "Pay Type");
                Label H10 = new Label(9, 1, "Pay No");
                Label H11 = new Label(10, 1, "User Name");
                Label H12 = new Label(11, 1, "Kurs");


                sheet.addCell(H0);
                sheet.addCell(H1);
                sheet.addCell(H2);
                sheet.addCell(H3);
                sheet.addCell(H4);
                sheet.addCell(H5);
                sheet.addCell(H6);
                sheet.addCell(H7);
                sheet.addCell(H8);
                sheet.addCell(H9);
                sheet.addCell(H10);
                sheet.addCell(H11);
                sheet.addCell(H12);






          /*      SQLiteDatabase db2 = adb_trans.getReadableDatabase();

                String selectQuery2 = "SELECT  tgl_rec,notrans_link,item_code,item_desc,qty,harga_jual,harga_beli,disc_val,x1,x2,user_name,x3  FROM trans1sub where tgl_rec >= '"+date1+"' and tgl_rec <= '"+date2+" 23:59:59'";
                //   String selectQuery2 = "SELECT  *  FROM " + ADB_Master.MyColumns_item.NamaTabel;
                //String selectQuery2 = "SELECT  *  FROM " + ADB_Trans.MyColumns_trans1sub.NamaTabel;
                Cursor c2 = db2.rawQuery(selectQuery2, null);
                int idno4 = c2.getCount();
                //  StringBuffer ok = new StringBuffer();
                // ok.append(obj.getString("kode"));
                //Toast.makeText(getBaseContext(), "no 1a "+String.valueOf(idno4)+date1+"-"+date2, Toast.LENGTH_LONG).show();



                if ( c2.moveToFirst()) {

                    for (int i = 0; i < idno4; i++) {

                        Label H51 = new Label(0, i + 2, c2.getString(0));
                        Label H61 = new Label(1, i + 2, c2.getString(1));
                        Label H71 = new Label(2, i + 2, c2.getString(2));
                        Label H81 = new Label(3, i + 2, c2.getString(3));
                        Label H101 = new Label(4, i + 2,String.valueOf(c2.getDouble(4)));
                        Label H111 = new Label(5, i + 2, String.valueOf(c2.getDouble(5)));
                        Label H121 = new Label(6, i + 2, String.valueOf(c2.getDouble(6)));
                        Label H131 = new Label(7, i + 2, String.valueOf(c2.getDouble(7)));
                        Label H141 = new Label(8, i + 2, c2.getString(8));
                        Label H151 = new Label(9, i + 2, c2.getString(9));
                        Label H161 = new Label(10, i + 2, c2.getString(10));
                        Label H162 = new Label(11, i + 2, c2.getString(11));


                        sheet.addCell(H51);
                        sheet.addCell(H61);
                        sheet.addCell(H71);
                        sheet.addCell(H81);
                        sheet.addCell(H101);
                        sheet.addCell(H111);
                        sheet.addCell(H121);
                        sheet.addCell(H131);
                        sheet.addCell(H141);
                        sheet.addCell(H151);
                        sheet.addCell(H161);
                        sheet.addCell(H162);


                        c2.moveToNext();

                    }

                }*/


            } catch (RowsExceededException e) {
                e.printStackTrace();
            } catch (WriteException e) {
                e.printStackTrace();
            }

            workbook.write();

            try {
                workbook.close();
            } catch (WriteException e) {

                e.printStackTrace();
            }

        } catch (IOException e) {
            e.printStackTrace();

            Log.d("Excel Detect", "error Excel");

        }
        openDataTrans();
    }









    private void DownloadDataTransx(){
        final String webhasil1=hasil1.getText().toString();
        final String webhasil2=hasil2.getText().toString();
        Intent intent=getIntent();
        final String kodecabang1=intent.getStringExtra("kodecabang");
        //   final String namacari=cari1.getText().toString();
        // String url = AppConfig.IP_SERVER+"/listdata.php";
        String url =AppConfig.IP_SERVER+"gcnew/reportsalesgroupexcel.php";
       // pDialog.setMessage("Download Data Barang...");
        Toast.makeText(this,"test masuk load server",Toast.LENGTH_LONG).show();
       // showDialog();

        StringRequest postRequesttrans = new StringRequest(Request.Method.POST, url,new Response.Listener<String>() {

            @Override

            public void onResponse(String response1) {

                Log.d("MainActivity bedul kubu","response:"+response1);

                hideDialog();



                processResponse1(response1);

                // gambarDatakeRecyclerView();

            }

        },

                new Response.ErrorListener() {

                    @Override

                    public void onErrorResponse(VolleyError error) {

                        // pDialog.setMessage("Connection failed");


                        if (error instanceof NetworkError) {
                            Toast.makeText(Report.this, "Oops. Network Error", Toast.LENGTH_LONG).show();
                        } else if (error instanceof ServerError) {
                            Toast.makeText(Report.this, "Oops. Server Error", Toast.LENGTH_LONG).show();
                        } else if (error instanceof AuthFailureError) {
                        } else if (error instanceof ParseError) {
                        } else if (error instanceof NoConnectionError) {
                            Toast.makeText(Report.this, "Oops. No Connection", Toast.LENGTH_LONG).show();
                        } else if (error instanceof TimeoutError) {
                            Toast.makeText(Report.this, "Oops. Timeout error!", Toast.LENGTH_LONG).show();
                        }


                        hideDialog();

                        error.printStackTrace();

                    }

                }

        ) {

            @Override

            protected Map<String, String> getParams()

            {

                Map<String, String>  params = new HashMap<>();

                // the POST parameters:

                params.put("tgl1",webhasil1);
                params.put("tgl2",webhasil2);
                params.put("kodecab","x");

                return params;

            }

        };

        Volley.newRequestQueue(this).add(postRequesttrans);

    }




    private void processResponse1(String response1){

        WritableWorkbook workbook;

        final String date1=hasil1.getText().toString();
        final String date2=hasil2.getText().toString();

        String Fnamexls = nodoc+ ".xls";
       // String Fnamexls = "itemtrans6" + ".xls";

        File directory = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/exceldata");
        directory.mkdir();

        File file = new File(directory, Fnamexls);


        WorkbookSettings wbSettings = new WorkbookSettings();
        wbSettings.setLocale(new Locale("en", "EN"));
        Toast.makeText(getBaseContext(), "no 1", Toast.LENGTH_LONG).show();
        int a = 1;







     //   try {

          //  Log.d("TAG", "data response bedul kubu: " + response1);

           // JSONObject jsonObj = new JSONObject(response1);

         //   JSONArray jsonArray = jsonObj.getJSONArray("data");







      //      Log.d("TAG", "data length: " + jsonArray.length());

            //  Barang objectbarang = null;

            //barangList.clear();


            //          objectbarang= new Barang();

            //   Locale localeID = new Locale("in", "ID");
            // NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);
            // detailHarga.setText(formatRupiah.format((double)hargarumah));

/*
                objectbarang.setId(obj.getString("id"));
                objectbarang.setNama(obj.getString("nama"));
                objectbarang.setKode(obj.getString("kode"));
                objectbarang.setHarga(obj.getString("harga"));
                objectbarang.setHarga_beli(obj.getString("harga_beli"));
                barangList.add(objectbarang);*/


            //batas atas--------------------------------------------------
            //  WritableWorkbook workbook;



           try {




                 workbook = Workbook.createWorkbook(file, wbSettings);
                Toast.makeText(getBaseContext(), "no 1a", Toast.LENGTH_LONG).show();
                WritableSheet sheet = workbook.createSheet("First Sheet", 0);




                //Label I1 = new Label(0,i,String.valueOf(a));
                //Label I1 = new Label(0,i+1,String.valueOf(obj.getString("kode")));
                //Label I2 = new Label(1, i+1, String.valueOf(obj.getString("nama")));
                //Label I3 = new Label(2,i+1,String.valueOf(obj.getString("harga")));
                //Label I4 = new Label(3,i+1,String.valueOf(obj.getString("harga_beli")));


             try {


                    Date currentTime = Calendar.getInstance().getTime();

                    Label H0 = new Label(0, 0, "Sales Golden Care : "+date1+" s/d "+date2);
                    Label H1 = new Label(0, 1, "Item Code");
                    Label H2 = new Label(1, 1, "Item Desc.");
                    Label H3 = new Label(2, 1, "Qty");
                    Label H4 = new Label(3, 1, "Date");
                    Label H5 = new Label(4, 1, "Tujuan");
                    Label H6 = new Label(4, 1, "No. Inv");
                    //Label H6 = new Label(5, 1, "Harga Jual");
                    //Label H7 = new Label(6, 1, "Harga Beli");
                    //Label H8 = new Label(7, 1, "Discount");


                    sheet.addCell(H0);
                    sheet.addCell(H1);
                    sheet.addCell(H2);
                    sheet.addCell(H3);
                    sheet.addCell(H4);
                    sheet.addCell(H5);
                    sheet.addCell(H6);
                    //sheet.addCell(H6);
                    //sheet.addCell(H7);
                    //sheet.addCell(H8);


                    try{

                        Toast.makeText(getBaseContext(), "NO2 " , Toast.LENGTH_LONG).show();
                        JSONObject jsonObj = new JSONObject(response1);

                        JSONArray jsonArray = jsonObj.getJSONArray("data");


                  for (int i = 0; i < jsonArray.length(); i++) {

                        JSONObject obj = jsonArray.getJSONObject(i);

                        //  StringBuffer ok = new StringBuffer();
                        // ok.append(obj.getString("kode"));


                        Label H51 = new Label(0, i + 2, obj.getString("item_code"));
                        Label H61 = new Label(1, i + 2, obj.getString("item_desc"));
                        Label H71 = new Label(2, i + 2, obj.getString("Qty"));
                        Label H81 = new Label(3, i + 2, obj.getString("tgl"));
                        Label H91 = new Label(4, i + 2, obj.getString("tujuan"));
                        Label H92 = new Label(5, i + 2, obj.getString("noinv"));
                        //Label H101 = new Label(5, i + 2, obj.getString("harga_jual"));
                        //Label H111 = new Label(6, i + 2, obj.getString("harga_beli"));
                        //Label H121 = new Label(7, i + 2, obj.getString("disc_val"));


                        sheet.addCell(H51);
                        sheet.addCell(H61);
                        sheet.addCell(H71);
                        sheet.addCell(H81);
                        sheet.addCell(H91);
                        sheet.addCell(H92);
                        //sheet.addCell(H101);
                        //sheet.addCell(H111);
                        //sheet.addCell(H121);


                    }
                    } catch (JSONException e) {

                        Log.d("MainActivity", "errorJSON");

                        // openDataTrans();

                    }


             } catch (RowsExceededException e) {
                     e.printStackTrace();
                } catch (WriteException e) {
                    e.printStackTrace();
                }

                workbook.write();

                try {
                    workbook.close();
                } catch (WriteException e) {

                    e.printStackTrace();

                    Log.d("Excel Detect", "error Excel");
                }

           } catch(IOException e){
                e.printStackTrace();

                Log.d("Excel Detect", "error Excel");
            }


         openDataTrans2();


//------------------------------------------------batas volley-------------------------------------------//


   /*     } catch (JSONException e) {

            Log.d("MainActivity", "errorJSON");

           // openDataTrans();

        } */
       //openDataTrans();
    }



    private void openDataTrans(){
        File directory = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/exceldata");
        // directory.mkdir();
        String Fnamexls = "itemtrans6" + ".xls";
        File file = new File(directory, Fnamexls);
        //File file = new File( Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/exceldata/itemmaster.xls");
        if (file.exists())
        {
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
           // Toast.makeText(Report.this, "view xls", Toast.LENGTH_LONG).show();
            Intent ambil=new Intent(Intent.ACTION_VIEW);
            Uri uri = Uri.fromFile(file);
            ambil.setDataAndType(uri, "application/vnd.ms-excel");
            ambil.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            ambil.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);


            try
            {
                startActivity(Intent.createChooser(ambil,"Pilih Aplikasi :"));
            }
            catch(ActivityNotFoundException e)
            {
                //Toast.makeText(MainActivity.this, "No Application available to view pdf", Toast.LENGTH_LONG).show();
            }
        }


    }




    private void openDataTrans2(){
        File directory = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/exceldata");
        // directory.mkdir();
        final String date1=tvDateResult1.getText().toString();
        final String date2=tvDateResult2.getText().toString();
     //   String Fnamexls = "Lapsales"+date1+"to"+date2 + ".xls";
       //
        // String Fnamexls = "itemtrans.xls";
      // String Fnamexls = "Lapsales"+date1+"to"+date2 + ".xls";
                String Fnamexls = nodoc+ ".xls";
        File file = new File(directory, Fnamexls);
        //File file = new File( Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/exceldata/itemmaster.xls");
        if (file.exists())
        {
              StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
             StrictMode.setVmPolicy(builder.build());

/*            Toast.makeText(Acc_Report.this, "view xls", Toast.LENGTH_LONG).show();
            Intent ambil=new Intent(Intent.ACTION_VIEW);
            Uri uri = Uri.fromFile(file);
            ambil.setDataAndType(uri, "application/vnd.ms-excel");
            ambil.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            ambil.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
*/


            Intent ambil = new Intent(Intent.ACTION_SEND);
            ambil.setType("application/vnd.ms-excel");
            // ambil.setDataAndType(uri, "application/vnd.ms-excel");
            //  target.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            //target.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            //  target.setFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            // target.setPackage("com.adobe.reader");
            //   Intent intent = Intent.createChooser(target, "Share File To: ");
            ambil.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            ambil.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
            ambil.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));


            try
            {
                startActivity(Intent.createChooser(ambil,"Pilih Aplikasi :"));
            }
            catch(ActivityNotFoundException e)
            {
                //Toast.makeText(MainActivity.this, "No Application available to view pdf", Toast.LENGTH_LONG).show();
            }
        }else
        {
            Toast.makeText(Report.this, "File tidak ada", Toast.LENGTH_LONG).show();
        }


    }


    private void openDataMaster(){
        File directory = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/exceldata");
        // directory.mkdir();
        String Fnamexls = "itemmaster5" + ".xls";
        File file = new File(directory, Fnamexls);
        //File file = new File( Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/exceldata/itemmaster.xls");
        if (file.exists())
        {
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
            Toast.makeText(Report.this, "view xls", Toast.LENGTH_LONG).show();
            Intent ambil=new Intent(Intent.ACTION_VIEW);
            Uri uri = Uri.fromFile(file);
            ambil.setDataAndType(uri, "application/vnd.ms-excel");
            ambil.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            ambil.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);


            try
            {
                startActivity(Intent.createChooser(ambil,"Pilih Aplikasi :"));
            }
            catch(ActivityNotFoundException e)
            {
                //Toast.makeText(MainActivity.this, "No Application available to view pdf", Toast.LENGTH_LONG).show();
            }
        }


    }

    private void DownloadDataMaster(){
        //   final String namacari=cari1.getText().toString();
        // String url = AppConfig.IP_SERVER+"/listdata.php";
        Intent intent=getIntent();
        final String kodecabang1=intent.getStringExtra("kodecabang");
        String url ="http://anugrah.store/pos/listdata.php";
        pDialog.setMessage("Download Data Barang...");
        Toast.makeText(this,"test masuk load server",Toast.LENGTH_LONG).show();
        showDialog();

        StringRequest postRequest = new StringRequest(Request.Method.POST, url,new Response.Listener<String>() {

            @Override

            public void onResponse(String response) {

                Log.d("MainActivity bedul kubu","response:"+response);

                hideDialog();



                processResponse(response);

                // gambarDatakeRecyclerView();

            }

        },

                new Response.ErrorListener() {

                    @Override

                    public void onErrorResponse(VolleyError error) {

                        // pDialog.setMessage("Connection failed");

                        if (error instanceof NetworkError) {
                            Toast.makeText(Report.this, "Oops. Network Error", Toast.LENGTH_LONG).show();
                        } else if (error instanceof ServerError) {
                            Toast.makeText(Report.this, "Oops. Server Error", Toast.LENGTH_LONG).show();
                        } else if (error instanceof AuthFailureError) {
                        } else if (error instanceof ParseError) {
                        } else if (error instanceof NoConnectionError) {
                            Toast.makeText(Report.this, "Oops. No Connection", Toast.LENGTH_LONG).show();
                        } else if (error instanceof TimeoutError) {
                            Toast.makeText(Report.this, "Oops. Timeout error!", Toast.LENGTH_LONG).show();
                        }




                        hideDialog();

                        error.printStackTrace();

                    }

                }

        ) {

            @Override

            protected Map<String, String> getParams()

            {

                Map<String, String>  params = new HashMap<>();

                // the POST parameters:

                params.put("kodecab","C");

                return params;

            }

        };

        Volley.newRequestQueue(this).add(postRequest);

    }

/*
    public void onErrorResponse(VolleyError error) {
        if (error instanceof NetworkError) {
        } else if (error instanceof ServerError) {
        } else if (error instanceof AuthFailureError) {
        } else if (error instanceof ParseError) {
        } else if (error instanceof NoConnectionError) {
        } else if (error instanceof TimeoutError) {
            Toast.makeText(this,
                    "Oops. Timeout error!",
                    Toast.LENGTH_LONG).show();
        }}*/


    private void processResponse(String response){

        WritableWorkbook workbook;


      //  String Fnamexls = "Lapcheckinout"+date1+"to"+date2 + ".xls";
        String Fnamexls = "itemmaster2" + ".xls";

       // String Fnamexls = "Lapsales"+date1+"to"+date2 + ".xls";

        File directory = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/exceldata");
        directory.mkdir();

        File file = new File(directory, Fnamexls);


        WorkbookSettings wbSettings = new WorkbookSettings();
        wbSettings.setLocale(new Locale("en", "EN"));
        Toast.makeText(getBaseContext(), "no 1", Toast.LENGTH_LONG).show();
        int a = 1;







        try {

            Log.d("TAG", "data response bedul kubu: " + response);

            JSONObject jsonObj = new JSONObject(response);

            JSONArray jsonArray = jsonObj.getJSONArray("data");

            Log.d("TAG", "data length: " + jsonArray.length());

            //  Barang objectbarang = null;

            //barangList.clear();


            //          objectbarang= new Barang();

            //   Locale localeID = new Locale("in", "ID");
            // NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);
            // detailHarga.setText(formatRupiah.format((double)hargarumah));

/*
                objectbarang.setId(obj.getString("id"));
                objectbarang.setNama(obj.getString("nama"));
                objectbarang.setKode(obj.getString("kode"));
                objectbarang.setHarga(obj.getString("harga"));
                objectbarang.setHarga_beli(obj.getString("harga_beli"));
                barangList.add(objectbarang);*/


            //batas atas--------------------------------------------------
            //  WritableWorkbook workbook;
            try {
                workbook = Workbook.createWorkbook(file, wbSettings);
                Toast.makeText(getBaseContext(), "no 1a", Toast.LENGTH_LONG).show();
                WritableSheet sheet = workbook.createSheet("First Sheet", 0);




                //Label I1 = new Label(0,i,String.valueOf(a));
                //Label I1 = new Label(0,i+1,String.valueOf(obj.getString("kode")));
                //Label I2 = new Label(1, i+1, String.valueOf(obj.getString("nama")));
                //Label I3 = new Label(2,i+1,String.valueOf(obj.getString("harga")));
                //Label I4 = new Label(3,i+1,String.valueOf(obj.getString("harga_beli")));


                try {

                    //  Toast.makeText(getBaseContext(), "NO I : " + i, Toast.LENGTH_LONG).show();
                    Date currentTime = Calendar.getInstance().getTime();

                    Label H0 = new Label(0, 0, "Tanggal Download : "+currentTime);
                    Label H1 = new Label(0, 1, "KODE");
                    Label H2 = new Label(1, 1, "Nama Product");
                    Label H3 = new Label(2, 1, "Harga Jual");
                    Label H4 = new Label(3, 1, "Harga Beli");

                    sheet.addCell(H0);
                    sheet.addCell(H1);
                    sheet.addCell(H2);
                    sheet.addCell(H3);
                    sheet.addCell(H4);


                    for (int i = 0; i < jsonArray.length(); i++) {

                        JSONObject obj = jsonArray.getJSONObject(i);

                        //  StringBuffer ok = new StringBuffer();
                        // ok.append(obj.getString("kode"));


                        Label H5 = new Label(0, i + 2, obj.getString("kode"));
                        Label H6 = new Label(1, i + 2, obj.getString("nama"));
                        Label H7 = new Label(2, i + 2, obj.getString("harga"));
                        Label H8 = new Label(3, i + 2, obj.getString("harga_beli"));


                        sheet.addCell(H5);
                        sheet.addCell(H6);
                        sheet.addCell(H7);
                        sheet.addCell(H8);
                    }

                } catch (RowsExceededException e) {
                    e.printStackTrace();
                } catch (WriteException e) {
                    e.printStackTrace();
                }

                workbook.write();

                try {
                    workbook.close();
                } catch (WriteException e) {

                    e.printStackTrace();
                }

            } catch(IOException e){
                e.printStackTrace();
            }





//------------------------------------------------batas volley-------------------------------------------//


        } catch (JSONException e) {

            Log.d("MainActivity", "errorJSON");

        }
        openDataMaster();
    }

    @Override

    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.

     //   getMenuInflater().inflate(R.menu.menu_barcodemanual, menu);

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

