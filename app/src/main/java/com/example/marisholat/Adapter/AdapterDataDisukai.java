package com.example.marisholat.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.marisholat.API.APIAmbilSemua;
import com.example.marisholat.API.ServerSendiri;
import com.example.marisholat.BerandaFragment;
import com.example.marisholat.DetailActivity;
import com.example.marisholat.DisukaiFragment;
import com.example.marisholat.MainActivity;
import com.example.marisholat.Model.DataModelSemua;
import com.example.marisholat.Model.ResponseServerSendiri;
import com.example.marisholat.R;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdapterDataDisukai extends RecyclerView.Adapter<AdapterDataDisukai.HolderData>{

    private Context ctx;
    private List<DataModelSemua> listKota;
    private List<DataModelSemua> listData;



    public AdapterDataDisukai(Context ctx, List<DataModelSemua> listKota) {

        this.ctx = ctx;
        this.listData = listKota;
    }

    @NonNull
    @Override
    public HolderData onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.kartu_tatakan_disukai, parent, false);
        HolderData holder = new HolderData(layout);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull HolderData holder, int position) {
        DataModelSemua dm = listData.get(position);

        holder.tvNamaKotaDisukai.setText(dm.getLokasi());

//        SharedPreferences sharedPref = holder.itemView.getContext().getSharedPreferences(dm.getId(), Context.MODE_PRIVATE);
//        SharedPreferences.Editor setStatusLiked = sharedPref.edit().putBoolean("likedStatus",false);
//        setStatusLiked.apply();

        holder.isi_pesan.setText(dm.getIsi());


        holder.kartu_disukai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent kirimData = new Intent(holder.itemView.getContext(), DetailActivity.class);
                kirimData.putExtra(DetailActivity.kiriman_id,dm.getId().toString());
                kirimData.putExtra(DetailActivity.buat_status,"b");
                kirimData.putExtra(DetailActivity.pesan_masuk,dm.getIsi().toString());
                holder.itemView.getContext().startActivity(kirimData);
            }
        });

        holder.btn_hapus_suka.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(holder.itemView.getContext(), "data :"+dm.getId(), Toast.LENGTH_SHORT).show();
                hapusData(dm.getId());
                ref(position);

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

    public void hapusData(String id) {

        APIAmbilSemua konekData = ServerSendiri.konekRetrofit().create(APIAmbilSemua.class);
        Call<ResponseServerSendiri>hapusData = konekData.hapusDatanya(id);

        hapusData.enqueue(new Callback<ResponseServerSendiri>() {
            @Override
            public void onResponse(Call<ResponseServerSendiri> call, Response<ResponseServerSendiri> response) {
                Log.d("hapus","berhasil");


            }

            @Override
            public void onFailure(Call<ResponseServerSendiri> call, Throwable t) {
                Log.d("hapus","gagal apus :"+t.getMessage());
            }
        });
    }

    private void ref(int id) {
       listData.remove(id);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }


    public class HolderData extends RecyclerView.ViewHolder{
        TextView tvNamaKotaDisukai;
        ImageView btn_hapus_suka;
        CardView kartu_disukai;
        TextView isi_pesan;
        public HolderData(@NonNull View itemView) {
            super(itemView);
            tvNamaKotaDisukai = itemView.findViewById(R.id.txt_nama_kota_disukai);
            btn_hapus_suka = itemView.findViewById(R.id.btn_hapus_suka);
            kartu_disukai = itemView.findViewById(R.id.kartu_pad_disukai);
            isi_pesan = itemView.findViewById(R.id.txt_isi_pesan);
        }
    }
}
