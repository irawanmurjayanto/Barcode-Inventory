package com.example.barcodegcnew;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;

public class HistoryBarcode extends AppCompatActivity {
    private WebView myWebView;
    private ProgressDialog pDialog;
    Button  butProcess,butBarcode;
    EditText editbarcode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_barcode);

        editbarcode=findViewById(R.id.editbarcode);
        butProcess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                printWebViewtran();
            }
        });

    }



    private void printWebViewtran() {

        WebView web_view = findViewById(R.id.web_view);
        web_view.requestFocus();
        web_view.getSettings().setLightTouchEnabled(true);
        web_view.getSettings().setBuiltInZoomControls(true);
        pDialog = new ProgressDialog(HistoryBarcode.this);

        // webView.getSettings().setDisplayZoomControls(false);
        web_view.loadUrl(AppConfig.IP_SERVER+"gcnew/reporthistorybarcode.php?barcode="+editbarcode.getText().toString());
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

}