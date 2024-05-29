package com.example.barcodegcnew;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;


public class AdapterData2 extends RecyclerView.Adapter<com.example.barcodegcnew.AdapterData2.BarangViewHolder> {


    List<POJOData> barangs;

    public AdapterData2(List<POJOData> barangs) {

        this.barangs = barangs;
    }

    @Override
    public BarangViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.test2, viewGroup, false);

        BarangViewHolder barangViewHolder = new BarangViewHolder(v);

        return barangViewHolder;

    }

    @Override

    public void onBindViewHolder(BarangViewHolder barangViewHolder, int i) {
        barangViewHolder.barangIDNO.setText(String.valueOf(barangs.get(i).getIDNO()));
        barangViewHolder.barangJudul.setText(barangs.get(i).getJudul());
        barangViewHolder.barangTgl.setText(barangs.get(i).getTgl());
        barangViewHolder.barangRemarks.setText(barangs.get(i).getRemarks());
        barangViewHolder.barangTag.setText(String.valueOf(barangs.get(i).getTagstatus()));
         barangViewHolder.barangitemcode.setText(barangs.get(i).getItemcode());
    }

    @Override

    public int getItemCount() {

        return barangs.size();

    }

    public POJOData getItem(int position) {

        return barangs.get(position);

    }

    @Override

    public void onAttachedToRecyclerView(RecyclerView recyclerView) {

        super.onAttachedToRecyclerView(recyclerView);

    }

    public static class BarangViewHolder extends RecyclerView.ViewHolder {

        CardView cv;
        TextView barangIDNO;
        TextView barangJudul;
        TextView barangTgl;
        TextView barangRemarks;
        TextView barangTag;
        TextView barangitemcode;




        BarangViewHolder(View itemView) {

            super(itemView);

            cv = (CardView) itemView.findViewById(R.id.cv);

            barangIDNO = (TextView) itemView.findViewById(R.id.textIDNO);

            barangJudul = (TextView) itemView.findViewById(R.id.textJudul);

            barangTgl = (TextView) itemView.findViewById(R.id.textTgl2);

            barangRemarks = (TextView) itemView.findViewById(R.id.textRemarks);

            barangTag = (TextView) itemView.findViewById(R.id.textTag);

            barangitemcode = (TextView) itemView.findViewById(R.id.textitemcode3);



        }

    }


}
