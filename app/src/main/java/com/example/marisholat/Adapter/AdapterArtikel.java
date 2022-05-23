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
import com.example.marisholat.DetailArtikelActivity;
import com.example.marisholat.Model.DataModelArtikel;
import com.example.marisholat.R;


import java.util.List;

public class AdapterArtikel extends RecyclerView.Adapter<AdapterArtikel.HolderData>{
    private Context ctx;
    private List<DataModelArtikel> listArtikel;
    private List<DataModelArtikel> listData;

    public AdapterArtikel(Context ctx, List<DataModelArtikel> listArtikel) {

        this.ctx = ctx;
        this.listData = listArtikel;
    }

    @NonNull
    @Override
    public AdapterArtikel.HolderData onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.kartu_artikel, parent, false);
        AdapterArtikel.HolderData holder = new AdapterArtikel.HolderData(layout);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterArtikel.HolderData holder, int position) {
        DataModelArtikel dm = listData.get(position);

        holder.tvJdlArtikel.setText(dm.getJudul());

        holder.kartu_disukai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent kirimData = new Intent(holder.itemView.getContext(), DetailArtikelActivity.class);
                kirimData.putExtra(DetailArtikelActivity.dapet_judul,dm.getJudul().toString());
                kirimData.putExtra(DetailArtikelActivity.dapet_isi,dm.getIsi().toString());
                kirimData.putExtra(DetailArtikelActivity.dapet_gambar,dm.getGambar().toString());
                holder.itemView.getContext().startActivity(kirimData);
            }
        });

        if (position % 4 == 0) {
            holder.kartu_disukai.setCardBackgroundColor(Color.parseColor("#F28C8C"));
        } else if (position % 4 == 1) {
            holder.kartu_disukai.setCardBackgroundColor(Color.parseColor("#56A4CF"));
        } else if (position % 4 == 2) {
            holder.kartu_disukai.setCardBackgroundColor(Color.parseColor("#7573C9"));
        }
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    public class HolderData extends RecyclerView.ViewHolder{
        TextView tvJdlArtikel;
        CardView kartu_disukai;
        public HolderData(@NonNull View itemView) {
            super(itemView);
            tvJdlArtikel = itemView.findViewById(R.id.txt_jdl_artikel);
            kartu_disukai = itemView.findViewById(R.id.kartu_pad_artikel);
        }
    }
}
