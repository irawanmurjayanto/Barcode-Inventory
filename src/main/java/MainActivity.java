package com.example.barcodegcnew;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.StrictMode;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class MainActivity extends AppCompatActivity {

    TextView textuser_id;
    public static  String user_idbuffer;
    Button butlogout;
    ImageButton butimport,butin,butout,butretin,butretout,butactive,butbad,butadj,butoutlokal,butreport;
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
     /*    android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);*/

        Intent user=new Intent (getBaseContext(),userpass.class);
        startActivityForResult(user,1234);

        textuser_id=findViewById(R.id.user_id);
        butlogout=findViewById(R.id.butlogout);
        butreport=findViewById(R.id.report);

        butreport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userpass.usergroup.equals("admin")) {
                    Intent user = new Intent(getBaseContext(), Report.class);
                    startActivityForResult(user, 1234);
                }else
                {
                    Intent a = new Intent(getBaseContext(), warningumum.class);
                    a.putExtra("warning", "Anda tidak punya akses atas module ini");
                    startActivity(a);
                }
            }
        });



        butlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent user=new Intent (getBaseContext(),userpass.class);
                startActivityForResult(user,1234);
            }
        });


        butimport=findViewById(R.id.m1);

        butout=findViewById(R.id.m3);
      //  butadj=findViewById(R.id.butadj);
        butoutlokal=findViewById(R.id.outlokal);
      //  butretin=findViewById(R.id.m4);

        butactive=findViewById(R.id.m11);
     //   butbad=findViewById(R.id.m12);

        butimport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userpass.usergroup.equals("user") || userpass.usergroup.equals("admin")) {
                    Intent saya = new Intent(getBaseContext(), WrhParis2.class);
                    saya.putExtra("wrhtype", "import");
                    saya.putExtra("judul", "Golden Care Import");
                    startActivityForResult(saya, 111);
                } else {

                    Intent a = new Intent(getBaseContext(), warningumum.class);
                    a.putExtra("warning", "Anda tidak punya akses atas module ini");
                    startActivity(a);
                }
            }
});


        butout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (userpass.usergroup.equals("user") || userpass.usergroup.equals("admin")) {

                    Intent saya = new Intent(getBaseContext(), WrhParisOut.class);
                    saya.putExtra("wrhtype", "OUT");
                    saya.putExtra("judul", "Golden Care OUT");
                    startActivityForResult(saya, 111);
                }
            }
        });

        butoutlokal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if ( userpass.usergroup.equals("admin")) {

                    Intent saya = new Intent(getBaseContext(), WrhParisLokal.class);
                    saya.putExtra("wrhtype", "OUT");
                    saya.putExtra("judul", "OUT LOKAL");
                    startActivityForResult(saya, 117);
                   }else{
                Intent a = new Intent(getBaseContext(), warningumum.class);
                a.putExtra("warning", "Anda tidak punya akses atas module ini");
                startActivity(a);
            }
            }
        });

    /*    butadj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent saya=new Intent(getBaseContext(),WrhParisOutAdj.class);
                saya.putExtra("wrhtype","ADJ");
                saya.putExtra("judul","Adjustment");
                startActivityForResult(saya,112);

            }
        });
*/

     /*   butretin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent saya=new Intent(getBaseContext(),WrhParis.class);
                saya.putExtra("wrhtype","RIN");
                saya.putExtra("judul","Golden Care Return IN");
                startActivityForResult(saya,111);

            }
        });

*/


        butactive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ( userpass.usergroup.equals("admin")) {
                    Intent saya = new Intent(getBaseContext(), MainStockGC.class);
                    saya.putExtra("wrhtype", "stockgc");
                    saya.putExtra("tipestock", "Normal");
                    saya.putExtra("judul", "Stock Active Golden Care ");
                    startActivityForResult(saya, 111);
                }else{
                    Intent a = new Intent(getBaseContext(), warningumum.class);
                    a.putExtra("warning", "Anda tidak punya akses atas module ini");
                    startActivity(a);
                }

            }
        });


    /*    butbad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent saya=new Intent(getBaseContext(),MainStockGC.class);
                saya.putExtra("wrhtype","stockgc");
                saya.putExtra("tipestock","Bad Stock");
                saya.putExtra("judul","Bad Stock Golden Care ");
                startActivityForResult(saya,111);

            }
        });
*/

        verifyStoragePermissions(MainActivity.this);

    }




    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE




            );
        }
    }



    @Override

    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.

        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;

    }

    @Override

    public boolean onOptionsItemSelected(MenuItem item) {

        // Handle action bar item clicks here. The action bar will


        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        if (id == R.id.action_close) {

            finish();

            return true;



        }else  if (id == R.id.action_tentang) {
            // Toast.makeText(getBaseContext(),"buka",Toast.LENGTH_SHORT).show();
             Intent saya=new Intent(this,tentang.class);
            startActivity(saya);
            return true;

        }else  if (id == R.id.stockgc) {
            Intent saya=new Intent(this,MainStockGC.class);
            saya.putExtra("wrhtype","stockgc");
            saya.putExtra("tipestock","Normal");
            saya.putExtra("judul","Golden Care Inventory ");
            startActivityForResult(saya,111);
            return true;

        }else  if (id == R.id.stockgcbad) {
            Intent saya=new Intent(this,MainStockGC.class);
            saya.putExtra("wrhtype","stockgc");
            saya.putExtra("tipestock","Bad Stock");
            saya.putExtra("judul","Bad Stock Golden Care ");
            startActivityForResult(saya,111);
            return true;

        }else  if (id == R.id.gc_import) {
            //Toast.makeText(getBaseContext(),"buka",Toast.LENGTH_SHORT).show();
            Intent saya=new Intent(this,WrhParis.class);
           saya.putExtra("wrhtype","import");
           saya.putExtra("judul","Golden Care Import");
           startActivityForResult(saya,111);
            return true;
        }else  if (id == R.id.gc_IN) {
            Intent saya=new Intent(this,WrhParis.class);
            saya.putExtra("wrhtype","IN");
            saya.putExtra("judul","Golden Care IN ");
            startActivityForResult(saya,111);
            return true;

        }else  if (id == R.id.gc_out) {
            Intent saya=new Intent(this,WrhParisOut.class);
            saya.putExtra("wrhtype","OUT");
            saya.putExtra("judul","Golden Care OUT New");
            startActivityForResult(saya,111);
            return true;

        }else  if (id == R.id.rt_in) {
            Intent saya=new Intent(this,WrhParis.class);
            saya.putExtra("wrhtype","RIN");
            saya.putExtra("judul","Golden Care Return IN");
            startActivityForResult(saya,111);
            return true;

        }else  if (id == R.id.rt_out) {
            Intent saya=new Intent(this,WrhParis.class);
            saya.putExtra("wrhtype","ROUT");
            saya.putExtra("judul","Golden Care Return OUT");
            startActivityForResult(saya,111);
            return true;

        }

        return super.onOptionsItemSelected(item);

    }

    @Override

    protected void onActivityResult ( int requestCode, int resultCode, Intent data){

        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);




        if (resultCode == RESULT_OK && requestCode == 1234) {

//editoven.setText(data.getStringExtra("section"));
            //   textTampung3.setText(String.valueOf(data.getStringExtra("idno")));

            //   loadDataLokal();

textuser_id.setText("( "+data.getStringExtra("user_id")+" )");
user_idbuffer=data.getStringExtra("user_id");
        }


    }



    @Override
    public void onBackPressed() {
      /*  DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }*/
     /*   DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }*/
    }

}