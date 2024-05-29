package com.example.barcodegcnew;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.List;

public class BarangAdapterNOREF extends RecyclerView.Adapter<BarangAdapterNOREF.BarangViewHolder> {

    List<BarangCabang> barangs;
    public BarangAdapterNOREF(List<BarangCabang> barangs) {

        this.barangs = barangs;}

    @Override
    public BarangViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_barangbarcodenoref, viewGroup,false);

        BarangViewHolder barangViewHolder = new BarangViewHolder(v);

        return barangViewHolder;

    }

    @Override

    public void onBindViewHolder(BarangViewHolder barangViewHolder, int i) {

        DecimalFormat df = new DecimalFormat("#,###,###");


       /* dateFormatter = new SimpleDateFormat("dd-MM-yyyyHH-mm-ss", Locale.US);
        Date currentTime = Calendar.getInstance().getTime();
        textnoreff.setText(a1 + dateFormatter.format(newDate.getTime()));*/

       // barangViewHolder.barangIDNO.setText(String.valueOf(barangs.get(i).getIdno()));
        barangViewHolder.barangNoref.setText(barangs.get(i).getNoref());
       barangViewHolder.barangTgl.setText(barangs.get(i).getTgl_rec());
       if (barangs.get(i).getNoinv().equals("null"))
       {
           barangViewHolder.barangSales.setText(barangs.get(i).getTujuan() + " / " + barangs.get(i).getJumbarcode());
       }else {
           barangViewHolder.barangSales.setText(barangs.get(i).getTujuan() + " / " + barangs.get(i).getJumbarcode()+"   "+barangs.get(i).getNoinv());
       }
       // barangViewHolder.barangTagTran.setText(barangs.get(i).getTagtran());

       /*  if (Integer.valueOf(barangs.get(i).getTagtran())==1) {
            barangViewHolder.barangTagTran.setText("NO");
        }else
        {
            barangViewHolder.barangTagTran.setText("Uploaded");
        }*/


      //  barangViewHolder.barangTgl.setText(barangs.get(i).getTglrec());

        //barangViewHolder.barangIDNO.setText(String.valueOf(barangs.get(i).getId()));

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
      //  TextView barangIDNO;
        TextView barangNoref;
        TextView barangTgl;
        TextView barangSales;
        //TextView barangUsername;


        BarangViewHolder(View itemView) {

            super(itemView);

            cv = (CardView) itemView.findViewById(R.id.cv);
           // barangIDNO = (TextView) itemView.findViewById(R.id.textIDNO);
            barangNoref = (TextView) itemView.findViewById(R.id.textNoref);
            barangTgl = (TextView) itemView.findViewById(R.id.textTgl);
            barangSales = (TextView) itemView.findViewById(R.id.textSales);

        }

    }

}
