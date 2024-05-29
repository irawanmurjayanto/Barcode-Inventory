package com.example.barcodegcnew;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintManager;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

public class printSalesgroup extends AppCompatActivity {
    private ProgressDialog pDialog;
    private WebView myWebView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_print_mutasigroup);
         WebView web_view = findViewById(R.id.web_view);
        web_view.requestFocus();
        web_view.getSettings().setLightTouchEnabled(true);
        web_view.getSettings().setBuiltInZoomControls(true);
        pDialog = new ProgressDialog(printSalesgroup.this);


      //  WebView  web_view = new WebView(this);

        //  WebSettings webSettings = myWebView.getSettings();
        // webSettings.setJavaScriptEnabled(true);

        // Settings so page loads zoomed-out all the way.
        // webSettings.setLoadWithOverviewMode(true);
        // webSettings.setUseWideViewPort(true);
        // webSettings.setBuiltInZoomControls(true);

     /*   web_view.setWebViewClient(new WebViewClient() {

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
*/


     //   if (Acc_Report.groupho.equals("1")) {
            // String javascript="javascript:document.getElementsByName('viewport')[0].setAttribute('AppConfig.IP_SERVER+\"/stockmutasiHO.php?date1=\" + webhasil1 + \"&date2=\" + webhasil2 + \"&kodecab=\" + kodecabang11', 'initial-scale=1.0,maximum-scale=10.0');";
            //  webView.getSettings().setBuiltInZoomControls(true);
            // webView.getSettings().setDisplayZoomControls(false);
            web_view.loadUrl(AppConfig.IP_SERVER+"gcnew/reportsalesgroup.php?noref="+NURUploadBarcodeBatch.norefall);
            // webView.loadUrl(javascript);
     //   }else {
            //String javascript="javascript:document.getElementsByName('viewport')[0].setAttribute('AppConfig.IP_SERVER+\"/stockmutasicabang.php?date1=\" + webhasil1 + \"&date2=\" + webhasil2 + \"&kodecab=\" + kodecabang11', 'initial-scale=1.0,maximum-scale=10.0');";
            //webView.getSettings().setBuiltInZoomControls(true);
            //webView.getSettings().setDisplayZoomControls(false);
         //   web_view.loadUrl(AppConfig.IP_SERVER+"/stockmutasicabang.php?date1=" +  Acc_Report.webhasil1+ "&date2=" + Acc_Report.webhasil2 + "&kodecab=" + Acc_Report.kodecabang);
            //webView.loadUrl(javascript);
      //  }
       //myWebView = web_view;
         web_view.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {
                if (progress < 100) {
                    showDialog();
                }
                if (progress == 100) {
                    hideDialog();
                }
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

    private void createWebPrintJob(WebView web_view) {

        PrintManager printManager = (PrintManager) this
                .getSystemService(Context.PRINT_SERVICE);

        PrintDocumentAdapter printAdapter =
                web_view.createPrintDocumentAdapter("MyDocument");

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

}