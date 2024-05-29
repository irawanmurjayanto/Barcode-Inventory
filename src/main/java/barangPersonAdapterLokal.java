package com.example.barcodegcnew;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class barangPersonAdapterLokal extends RecyclerView.Adapter<barangPersonAdapterLokal.BarangViewHolder> {

    List<barangPerson> barangs;
    public barangPersonAdapterLokal(List<barangPerson> barangs) {

        this.barangs = barangs;}

    @Override
    public BarangViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.activity_barang_person_adapterlokal, viewGroup,false);

        BarangViewHolder barangViewHolder = new BarangViewHolder(v);

        return barangViewHolder;

    }





    @Override

    public void onBindViewHolder(BarangViewHolder barangViewHolder, int i) {

    //    DecimalFormat formatter = new DecimalFormat("#,###,###,###");
        // totalrp.setText(formatter.format(obj.getDouble("total")));
        barangViewHolder.barangBarcode.setText(barangs.get(i).getBarcode());
      //  barangViewHolder.barangKode.setText(barangs.get(i).getNIK());
      //  barangViewHolder.barangNama.setText(barangs.get(i).getNama());
        barangViewHolder.barangRemarks.setText(barangs.get(i).getRemarks());

        barangViewHolder.barangError.setText(barangs.get(i).getNama());
      //  barangViewHolder.barangJabatan.setText(barangs.get(i).getJabatan());
     //   barangViewHolder.barangMacadd.setText(barangs.get(i).getMacadd());
      //  barangViewHolder.barangStatusemp.setText(barangs.get(i).getStatusemp());

    }

    @Override

    public int getItemCount() {

        return barangs.size();

    }

    public barangPerson getItem(int position) {

        return barangs.get(position);

    }

    @Override

    public void onAttachedToRecyclerView(RecyclerView recyclerView) {

        super.onAttachedToRecyclerView(recyclerView);

    }

    public static class BarangViewHolder extends RecyclerView.ViewHolder {

        CardView cv;


        TextView barangBarcode;

        TextView barangRemarks;

        TextView barangError;




        BarangViewHolder(View itemView) {

            super(itemView);

         cv = (CardView) itemView.findViewById(R.id.cv_empreg);



         barangBarcode= (TextView) itemView.findViewById(R.id.barcode);

         barangRemarks = (TextView) itemView.findViewById(R.id.remarks);

         barangError = (TextView) itemView.findViewById(R.id.errornote);



        }

    }

}

