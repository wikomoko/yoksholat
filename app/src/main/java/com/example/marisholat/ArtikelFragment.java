package com.example.marisholat;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.marisholat.API.APIAmbilSemua;
import com.example.marisholat.API.ServerSendiri;
import com.example.marisholat.Adapter.AdapterArtikel;
import com.example.marisholat.Adapter.AdapterDataDisukai;
import com.example.marisholat.Model.DataModelArtikel;
import com.example.marisholat.Model.DataModelSemua;
import com.example.marisholat.Model.ResponseArtikel;
import com.example.marisholat.Model.ResponseServerSendiri;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ArtikelFragment extends Fragment {

    public RecyclerView.Adapter adData;
    private RecyclerView.LayoutManager lmData;
    private List<DataModelArtikel> listData = new ArrayList<>();

    private RecyclerView rvArtikel;

    public ProgressBar p5;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_artikel, container, false);
        rvArtikel = view.findViewById(R.id.rv_artikel);
        p5 = view.findViewById(R.id.progressBarArt);
        p5.setVisibility(View.GONE);
        lmData = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        rvArtikel.setLayoutManager(lmData);
        ambilData();

        return view;
    }

    public void ambilData() {
      //  p5.setVisibility(View.VISIBLE);
        APIAmbilSemua ambldata = ServerSendiri.konekRetrofit().create(APIAmbilSemua.class);
        Call<ResponseArtikel> tampilData = ambldata.ambilArtikel();
        tampilData.enqueue(new Callback<ResponseArtikel>() {
            @Override
            public void onResponse(Call<ResponseArtikel> call, Response<ResponseArtikel> response) {
                Log.d("artikel","ada "+response.body().getData());
                if(response.body().getData()==null){
                    p5.setVisibility(View.GONE);
                }else{
                    listData.clear();
                    adData = null;
                    listData = response.body().getData();
                    adData = new AdapterArtikel(getContext(), listData);
                    rvArtikel.setAdapter(adData);
                    adData.notifyDataSetChanged();
                    p5.setVisibility(View.GONE);
                }

            }

            @Override
            public void onFailure(Call<ResponseArtikel> call, Throwable t) {
                Log.d("artikel","kosong "+t.getMessage());
            }
        });
    }
}