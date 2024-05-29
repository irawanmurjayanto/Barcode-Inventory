package com.example.barcodegcnew;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class warningpass extends Activity {
TextView textwarning;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_warninguserpass);

        textwarning=findViewById(R.id.textwarning);
    }
}