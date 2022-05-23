package com.example.marisholat;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import com.example.marisholat.API.APIAmbilSemua;
import com.example.marisholat.API.ServerSendiri;
import com.example.marisholat.Adapter.AdapterDataDisukai;
import com.example.marisholat.Model.DataModelSemua;
import com.example.marisholat.Model.ResponseServerSendiri;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DisukaiFragment extends Fragment {

    public RecyclerView.Adapter adData;
    private RecyclerView.LayoutManager lmData;
    private List<DataModelSemua> listData = new ArrayList<>();

    private RecyclerView rvDisukai;

    public ProgressBar p4;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_disukai, container, false);
        rvDisukai = view.findViewById(R.id.rv_disukai);
        p4 = view.findViewById(R.id.progressBar4);
        p4.setVisibility(View.GONE);
        lmData = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        rvDisukai.setLayoutManager(lmData);
        ambilData();

        return view;

    }

    public void ambilData() {
        p4.setVisibility(View.VISIBLE);
        APIAmbilSemua ambldata = ServerSendiri.konekRetrofit().create(APIAmbilSemua.class);
        Call<ResponseServerSendiri> tampilData = ambldata.ambilDataServerSendiri();

        tampilData.enqueue(new Callback<ResponseServerSendiri>() {
            @Override
            public void onResponse(Call<ResponseServerSendiri> call, Response<ResponseServerSendiri> response) {
                Log.d("sendiri","ada "+response.body().getData());

                if(response.body().getData()==null){
                    p4.setVisibility(View.GONE);
                }else{
                    listData.clear();
                    adData = null;
                    listData = response.body().getData();
                    adData = new AdapterDataDisukai(getContext(), listData);
                    rvDisukai.setAdapter(adData);
                    adData.notifyDataSetChanged();
                    p4.setVisibility(View.GONE);
                }


            }

            @Override
            public void onFailure(Call<ResponseServerSendiri> call, Throwable t) {
                Log.d("sendiri","kosong "+t.getMessage());
                p4.setVisibility(View.GONE);
            }
        });
    }

    public void mulaiLagi() {

       siap();
    }

    public void siap(){
        ((MainActivity) getActivity()).getFragmentPage(new BerandaFragment());
    }



}