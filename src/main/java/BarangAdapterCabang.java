package com.example.barcodegcnew;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class BarangAdapterCabang extends RecyclerView.Adapter<BarangAdapterCabang.BarangViewHolder> {



    List<BarangCabang> barangs;
    public BarangAdapterCabang(List<BarangCabang> barangs) {

        this.barangs = barangs;}

    @Override
    public BarangViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.activity_barang_adapter_cabang, viewGroup,false);

        BarangViewHolder barangViewHolder = new BarangViewHolder(v);

        return barangViewHolder;

    }

    @Override

    public void onBindViewHolder(BarangViewHolder barangViewHolder, int i) {

        //SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
       // Date date = format.parse(bundle.getString("starttime"));

      //  SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy", Locale.US);

     //   dateFormatter = new SimpleDateFormat("dd-MM-yyyyHH-mm-ss", Locale.US);
       // Date currentTime = Calendar.getInstance().getTime();
       // textnoreff.setText(a1 + dateFormatter.format(newDate.getTime()))

        //format.format(Date.parse("Your date string"));

    /*    try {
            Date date = df.parse(barangs.get(i).getTgl_rec());
            barangViewHolder.barangTgl.setText(String.valueOf(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }*/

     //   String dateTime = df.format(String.valueOf(barangs.get(i).getTgl_rec()));

        barangViewHolder.barangIDNO.setText(String.valueOf(barangs.get(i).getIdno()));

        barangViewHolder.barangBarcode.setText(barangs.get(i).getBarcode());

        barangViewHolder.barangUser.setText(barangs.get(i).getUser_name());
       barangViewHolder.barangTgl.setText(barangs.get(i).getTgl_rec());


     //   barangViewHolder.barangGroup.setText(barangs.get(i).getGroupho());

      //  barangViewHolder.barangSwitch.setChecked(barangs.get(i).getSwitch());

    }

    @Override

    public int getItemCount() {

        return barangs.size();

    }

    public BarangCabang getItem(int position) {

        return barangs.get(position);

    }

    @Override

    public void onAttachedToRecyclerView(RecyclerView recyclerView) {

        super.onAttachedToRecyclerView(recyclerView);

    }

    public static class BarangViewHolder extends RecyclerView.ViewHolder {

        CardView cv;
        TextView barangIDNO;

        TextView barangBarcode;

        TextView barangTgl;

        TextView barangUser;



      //  TextView barangGroup;

     //   Switch barangSwitch;



        BarangViewHolder(View itemView) {

            super(itemView);

            cv = (CardView) itemView.findViewById(R.id.cv);

            barangIDNO = (TextView) itemView.findViewById(R.id.textIDNO);

            barangBarcode= (TextView) itemView.findViewById(R.id.textBarcodenew);

            barangTgl = (TextView) itemView.findViewById(R.id.textTglnew);

            barangUser = (TextView) itemView.findViewById(R.id.textUsernew);



        //    barangGroup = (TextView) itemView.findViewById(R.id.textGroupho);

         //   barangSwitch=(Switch)  itemView.findViewById(R.id.switch1);

        }

    }


}