package com.example.marisholat.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


import com.example.marisholat.DetailActivity;
import com.example.marisholat.Model.DataModelSemua;
import com.example.marisholat.R;

import java.util.List;

public class AdapterSemuaData extends RecyclerView.Adapter<AdapterSemuaData.HolderData>{
    private Context ctx;
    private List<DataModelSemua> listKota;
    private List<DataModelSemua> listData;

    public AdapterSemuaData(Context ctx, List<DataModelSemua> listKota) {

        this.ctx = ctx;
        this.listData = listKota;
    }

    @NonNull
    @Override
    public HolderData onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.kartu_tatakan, parent, false);
        HolderData holder = new HolderData(layout);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull HolderData holder, int position) {
        DataModelSemua dm = listData.get(position);




            holder.tvNamaKota.setText(dm.getLokasi());

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent kirimData = new Intent(holder.itemView.getContext(), DetailActivity.class);
                    kirimData.putExtra(DetailActivity.kiriman_id, dm.getId().toString());
                    kirimData.putExtra(DetailActivity.buat_status,"a");
                    holder.itemView.getContext().startActivity(kirimData);
                }
            });

            if (position % 4 == 0) {
                holder.kartu.setCardBackgroundColor(Color.parseColor("#F28C8C"));
            } else if (position % 4 == 1) {
                holder.kartu.setCardBackgroundColor(Color.parseColor("#56A4CF"));
            } else if (position % 4 == 2) {
                holder.kartu.setCardBackgroundColor(Color.parseColor("#7573C9"));
            }


    }

    @Override
    public int getItemCount() {
        return listData.size();
    }


    public class HolderData extends RecyclerView.ViewHolder{
        TextView tvNamaKota;
        CardView kartu;
        public HolderData(@NonNull View itemView) {
            super(itemView);
            tvNamaKota = itemView.findViewById(R.id.txt_nama_kota);
            kartu = itemView.findViewById(R.id.kartu_pad);
        }
    }
}
