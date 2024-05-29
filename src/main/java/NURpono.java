package com.example.barcodegcnew;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class NURpono extends Activity {

    EditText editpos1;
    private Button but1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nurpono);
        editpos1=(EditText) findViewById(R.id.editpos1);
        but1=(Button) findViewById(R.id.butpostbedul);

        but1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                final String ambil=editpos1.getText().toString();
               // final String ambilQty=editQty.getText().toString();
                //    final String notransaksi = transaksi_POS.notrans.getText().toString();

                //  saveDataDialogInput();

               Intent balik=new Intent();
                balik.putExtra("baliknobedul","1");
                balik.putExtra("pono",editpos1.getText().toString());
                setResult(RESULT_OK,balik);
              //  Toast.makeText(getBaseContext(), "no "+ambilpono, Toast.LENGTH_SHORT).show();*/
                finish();

            }
        });

    }
}
