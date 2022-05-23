package com.example.marisholat;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.marisholat.API.APIAmbilSemua;
import com.example.marisholat.API.ServerSumber;
import com.example.marisholat.Adapter.AdapterSemuaData;
import com.example.marisholat.Model.DataModelSemua;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class BerandaFragment extends Fragment {

    private Calendar calendar;
    private SimpleDateFormat dateFormat;
    private String date;


    private RecyclerView.Adapter adData;
    private RecyclerView.LayoutManager lmData;
    private List<DataModelSemua> listData = new ArrayList<>();

    private  RecyclerView rvSemuaKota;
    private  TextView tampiljam;
    private ProgressBar progressBar;

    private ImageView btn_cari;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_beranda, container, false);
        rvSemuaKota = view.findViewById(R.id.rv_semua_kota);
        tampiljam = view.findViewById(R.id.tampil_jam);
        progressBar = view.findViewById(R.id.progressBar);

        btn_cari = view.findViewById(R.id.btn_cari);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {



        calendar = Calendar.getInstance();
        dateFormat = new SimpleDateFormat("EEEE, dd MMMM yyyy");
        date = dateFormat.format(calendar.getTime());
        tampiljam.setText(date);

        lmData = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        rvSemuaKota.setLayoutManager(lmData);
        ambilData();

        btn_cari.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent pindah = new Intent(getContext(), CariActivity.class);
                startActivity(pindah);
            }
        });
        super.onViewCreated(view, savedInstanceState);
    }

    private void ambilData() {
        APIAmbilSemua ambldata = ServerSumber.konekRetrofit().create(APIAmbilSemua.class);
        Call<ArrayList<DataModelSemua>> tampilData= ambldata.ambilSemuaData();

        tampilData.enqueue(new Callback<ArrayList<DataModelSemua>>() {
            @Override
            public void onResponse(Call<ArrayList<DataModelSemua>> call, Response<ArrayList<DataModelSemua>> response) {
                Log.d("hasil","ada isi");



                listData = response.body();
                adData = new AdapterSemuaData(getContext(), listData);
                rvSemuaKota.setAdapter(adData);
                adData.notifyDataSetChanged();
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<ArrayList<DataModelSemua>> call, Throwable t) {
                Log.d("hasil","kosong");
                Toast.makeText(getContext(),"Gagal server :"+t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });

    }


}