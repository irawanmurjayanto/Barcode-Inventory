package com.example.barcodegcnew;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.List;

public class BarangAdapterNOREFLokal extends RecyclerView.Adapter<BarangAdapterNOREFLokal.BarangViewHolder> {

    List<BarangBarcodeNOREFLokal> barangs;
    public BarangAdapterNOREFLokal(List<BarangBarcodeNOREFLokal> barangs) {

        this.barangs = barangs;}

    @Override
    public BarangViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_barangbarcodenoreflokal, viewGroup,false);

        BarangViewHolder barangViewHolder = new BarangViewHolder(v);

        return barangViewHolder;

    }

    @Override

    public void onBindViewHolder(BarangViewHolder barangViewHolder, int i) {

        DecimalFormat formatter = new DecimalFormat("#,###,###");

        barangViewHolder.barangNoref.setText(barangs.get(i).getNoref());
        barangViewHolder.barangTgl.setText(barangs.get(i).getTgl_rec());
    //    barangViewHolder.barangJumlah.setText(String.valueOf(barangs.get(i).getJumbarc()+" / "+));
        barangViewHolder.barangTujuan.setText(barangs.get(i).getLokoven()+" / "+barangs.get(i).getJumbarc());

       // barangViewHolder.barangTagTran.setText(barangs.get(i).getTagtran());

         if (Integer.valueOf(barangs.get(i).getTagtran())==1) {
            barangViewHolder.barangTagTran.setText("NO");
        }else
        {
            barangViewHolder.barangTagTran.setText("Uploaded");
        }
      //  barangViewHolder.barangTgl.setText(barangs.get(i).getTglrec());

        //barangViewHolder.barangIDNO.setText(String.valueOf(barangs.get(i).getId()));

    }

    @Override

    public int getItemCount() {

        return barangs.size();

    }

    public BarangBarcodeNOREFLokal getItem(int position) {

        return barangs.get(position);

    }

    @Override

    public void onAttachedToRecyclerView(RecyclerView recyclerView) {

        super.onAttachedToRecyclerView(recyclerView);

    }

    public static class BarangViewHolder extends RecyclerView.ViewHolder {

        CardView cv;

        TextView barangNoref;
        TextView barangTagTran;
        TextView barangTgl;
        TextView barangTujuan;
        TextView barangJumlah;



        BarangViewHolder(View itemView) {

            super(itemView);

            cv = (CardView) itemView.findViewById(R.id.cv);

            barangNoref = (TextView) itemView.findViewById(R.id.textViewRownoref);
            barangTagTran = (TextView) itemView.findViewById(R.id.textTagTran);
            barangTgl = (TextView) itemView.findViewById(R.id.textTgl);
            barangTujuan = (TextView) itemView.findViewById(R.id.texttujuan);
            barangJumlah = (TextView) itemView.findViewById(R.id.jumlahbarc);


        }

    }

}
