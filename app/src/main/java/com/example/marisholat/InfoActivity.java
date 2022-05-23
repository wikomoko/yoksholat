package com.example.marisholat;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.marisholat.API.APIAmbilSemua;
import com.example.marisholat.API.ServerSendiri;
import com.example.marisholat.Model.DataMasjid;
import com.example.marisholat.Model.ResponMasjid;
import com.example.marisholat.Model.ResponsePesan;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InfoActivity extends AppCompatActivity {

    public static final String p_msk = "kosong";
    public static final String id_msk = "1";
    private TextView d_nm_kota,d_nm_prov,d_nm_mjd,d_isi;
    private ImageView d_gbr;

    private Button bbu;

    private EditText tp;

    private ImageView bbk;

    private TextView thi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        getSupportActionBar().hide();


        String pnya = getIntent().getStringExtra(p_msk);
        String idnya = getIntent().getStringExtra(id_msk);

        bbk = findViewById(R.id.bbk);
        bbu = findViewById(R.id.bbu);
        tp = findViewById(R.id.t_pesan);
        d_nm_kota = findViewById(R.id.d_nm_kota);
        d_nm_prov = findViewById(R.id.d_nm_prov);
        d_nm_mjd = findViewById(R.id.d_nm_masjid);
        d_gbr = findViewById(R.id.d_gbr_masjid);
        d_isi = findViewById(R.id.d_p_mjd);

        tp.setText(pnya);

        cariMasjid(idnya);


        bbk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        bbu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editisi(idnya,tp.getText().toString());
            }
        });



    }

    private void cariMasjid(String idnya) {
        APIAmbilSemua xdata = ServerSendiri.konekRetrofit().create(APIAmbilSemua.class);
        Call<ResponMasjid> trxData = xdata.dorongMasjid(idnya);

        trxData.enqueue(new Callback<ResponMasjid>() {
            @Override
            public void onResponse(Call<ResponMasjid> call, Response<ResponMasjid> response) {
                Log.d("mas", "berhasil " + response.body());
                Toast.makeText(InfoActivity.this, response.body().getPesan(), Toast.LENGTH_SHORT).show();
                List<DataMasjid> inik = response.body().getData();
                d_nm_kota.setText(inik.get(0).getLokasi());
                d_nm_prov.setText(inik.get(0).getProvinsi());
                d_nm_mjd.setText(inik.get(0).getNmasjid());
                Picasso.get().load("http://192.168.43.163/ysholat/gbr_masjid/"+inik.get(0).getGmasjid()).into(d_gbr);
                d_isi.setText(inik.get(0).getKet());
            }

            @Override
            public void onFailure(Call<ResponMasjid> call, Throwable t) {
                Log.d("mas", "gagal " + t.getMessage());
            }
        });

    }

    private void editisi(String idnya,String isiedit) {
        APIAmbilSemua udata = ServerSendiri.konekRetrofit().create(APIAmbilSemua.class);
        Call<ResponsePesan> trkData = udata.dorongData(idnya,isiedit);

        trkData.enqueue(new Callback<ResponsePesan>() {
            @Override
            public void onResponse(Call<ResponsePesan> call, Response<ResponsePesan> response) {
                Log.d("trus", "berhasil " + response.body());
                Toast.makeText(InfoActivity.this, "Data Berhasil Diubah", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<ResponsePesan> call, Throwable t) {
                Log.d("trus", "gagal " + t.getMessage());
            }
        });
    }
}