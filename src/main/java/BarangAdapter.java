package com.example.barcodegcnew;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.List;

public class BarangAdapter extends RecyclerView.Adapter<BarangAdapter.BarangViewHolder> {

    List<BarangBarcode> barangs;
    public BarangAdapter(List<BarangBarcode> barangs) {

        this.barangs = barangs;}

    @Override
    public BarangViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_barangbarcode, viewGroup,false);

        BarangViewHolder barangViewHolder = new BarangViewHolder(v);

        return barangViewHolder;

    }

    @Override

    public void onBindViewHolder(BarangViewHolder barangViewHolder, int i) {

        DecimalFormat formatter = new DecimalFormat("#,###,###");

        barangViewHolder.barangBarcode.setText(barangs.get(i).getBarcode());

        barangViewHolder.barangTgl.setText(barangs.get(i).getTglrec()+"  ("+barangs.get(i).getTagtran()+")");

        barangViewHolder.barangIDNO.setText(String.valueOf(barangs.get(i).getId()));

    }

    @Override

    public int getItemCount() {

        return barangs.size();

    }

    public BarangBarcode getItem(int position) {

        return barangs.get(position);

    }

    @Override

    public void onAttachedToRecyclerView(RecyclerView recyclerView) {

        super.onAttachedToRecyclerView(recyclerView);

    }

    public static class BarangViewHolder extends RecyclerView.ViewHolder {

        CardView cv;

        TextView barangBarcode;

        TextView barangTgl;

        TextView barangIDNO;

        BarangViewHolder(View itemView) {

            super(itemView);

            cv = (CardView) itemView.findViewById(R.id.cv);

            barangBarcode = (TextView) itemView.findViewById(R.id.textViewRowbarcode);
            barangTgl = (TextView) itemView.findViewById(R.id.textViewRowTgl);
            barangIDNO = (TextView) itemView.findViewById(R.id.textViewRowIDNO);

        }

    }

}
