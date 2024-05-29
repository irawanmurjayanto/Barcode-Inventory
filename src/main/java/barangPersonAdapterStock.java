package com.example.barcodegcnew;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class barangPersonAdapterStock extends RecyclerView.Adapter<barangPersonAdapterStock.BarangViewHolder> {

    List<barangPerson> barangs;
    public barangPersonAdapterStock(List<barangPerson> barangs) {

        this.barangs = barangs;}

    @Override
    public BarangViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.activity_barang_person_adapterstock, viewGroup,false);

        BarangViewHolder barangViewHolder = new BarangViewHolder(v);

        return barangViewHolder;

    }





    @Override

    public void onBindViewHolder(BarangViewHolder barangViewHolder, int i) {

    //    DecimalFormat formatter = new DecimalFormat("#,###,###,###");
        // totalrp.setText(formatter.format(obj.getDouble("total")));
        barangViewHolder.barangKode.setText(barangs.get(i).getItem_code());
      //  barangViewHolder.barangKode.setText(barangs.get(i).getNIK());
      //  barangViewHolder.barangNama.setText(barangs.get(i).getNama());
        barangViewHolder.barangNama.setText(barangs.get(i).getItem_desc());

        barangViewHolder.barangQty.setText(String.valueOf(barangs.get(i).getQty()));
        barangViewHolder.barangDamage.setText(String.valueOf(barangs.get(i).getQtydamage()));
        barangViewHolder.barangTotal.setText(String.valueOf(barangs.get(i).getQtytotal()));
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


        TextView barangKode;

        TextView barangNama;

        TextView barangQty;

        TextView barangDamage;

        TextView barangTotal;




        BarangViewHolder(View itemView) {

            super(itemView);

         cv = (CardView) itemView.findViewById(R.id.cv_empreg);



         barangKode = (TextView) itemView.findViewById(R.id.item_code);

         barangNama = (TextView) itemView.findViewById(R.id.item_desc);

            barangQty = (TextView) itemView.findViewById(R.id.qtystock);
            barangDamage = (TextView) itemView.findViewById(R.id.qtydamage);
            barangTotal = (TextView) itemView.findViewById(R.id.qtytotal);




        }

    }

}

