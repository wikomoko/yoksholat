package com.example.marisholat;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.marisholat.API.APIAmbilSemua;
import com.example.marisholat.API.ServerSumber;
import com.example.marisholat.Adapter.AdapterSemuaData;
import com.example.marisholat.Model.DataModelSemua;
import com.example.marisholat.Model.ResponseCariKota;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CariActivity extends AppCompatActivity {
    private ImageView btn_back_biru;
    private EditText isian_kota;
    private Button tombol_cari_kota;
    private ProgressBar progressBar3;

    private RecyclerView rv_hasil_cari;
    private RecyclerView.Adapter adData;
    private RecyclerView.LayoutManager lmData;
    private List<DataModelSemua> listData = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cari);

        getSupportActionBar().hide();

        rv_hasil_cari = findViewById(R.id.rv_hasil_cari);
        lmData = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false);
        rv_hasil_cari.setLayoutManager(lmData);

        progressBar3 = findViewById(R.id.progressBar3);
        tombol_cari_kota = findViewById(R.id.btn_cari_dertah);
        isian_kota = findViewById(R.id.edt_daerah);
        btn_back_biru = findViewById(R.id.back_btn_biru);
        btn_back_biru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        tombol_cari_kota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String isinya = isian_kota.getText().toString();
                Toast.makeText(getApplicationContext(),isinya,Toast.LENGTH_SHORT).show();

                cariKota(isinya);
            }
        });
    }
    private void cariKota(String isinya) {
        progressBar3.setVisibility(View.VISIBLE);
        APIAmbilSemua ambldata = ServerSumber.konekRetrofit().create(APIAmbilSemua.class);
        Call<ResponseCariKota> tampilData = ambldata.ambilCariKota(isinya);
        tampilData.enqueue(new Callback<ResponseCariKota>() {
            @Override
            public void onResponse(Call<ResponseCariKota> call, Response<ResponseCariKota> response) {

                boolean stat = response.body().isStatus();
                String vn = String.valueOf(stat);
                if(vn.equals("true") ){
                    listData = response.body().getData();
                    adData = new AdapterSemuaData(getApplicationContext(),listData);
                    rv_hasil_cari.setAdapter(adData);
                    adData.notifyDataSetChanged();
                }else{
                    Toast.makeText(getApplicationContext(),"Data Tidak ada",Toast.LENGTH_SHORT).show();
                    AlertDialog.Builder alert = new AlertDialog.Builder(CariActivity.this)
                            .setTitle("Tidak tersedia")
                            .setMessage("Maaf kota yang anda cari tidak tersedia")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.cancel();
                                }
                            })
                            .setCancelable(true);
                    alert.show();
                }
                progressBar3.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<ResponseCariKota> call, Throwable t) {
                Log.d("dapetnya","kosong");
                Toast.makeText(getApplicationContext(),"Gagal server :"+t.getMessage(),Toast.LENGTH_SHORT).show();
                progressBar3.setVisibility(View.VISIBLE);
            }
        });


    }
}