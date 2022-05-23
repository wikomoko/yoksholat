package com.example.marisholat;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.marisholat.API.APIAmbilSemua;
import com.example.marisholat.API.ServerSendiri;
import com.example.marisholat.API.ServerSumber;
import com.example.marisholat.Model.ModelDetail;
import com.example.marisholat.Model.ResponseServerSendiri;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailActivity extends AppCompatActivity {

    private ImageView btn_back;
    private TextView kotanya, txt_subuh, txt_ashar, txt_dhuhur, txt_maghrib, txt_isya;
    public static final String kiriman_id = "kiriman";
    public static final String buat_status = "a";
    public static final String pesan_masuk ="pesan";

    private Calendar kalender;
    private SimpleDateFormat cariTahun, cariBulan, cariHari;
    private String tahun, bulan, hari;
    private ProgressBar progressBar2;
    private FloatingActionButton ftb, ffg;

    private Button btn_detail;
    private String nmKota;

    private SharedPreferences sharedPref;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        getSupportActionBar().hide();

        btn_back = findViewById(R.id.btn_back);
        kotanya = findViewById(R.id.txt_kota);
        txt_subuh = findViewById(R.id.txt_subuh);
        txt_dhuhur = findViewById(R.id.txt_dhuhur);
        txt_ashar = findViewById(R.id.txt_ashar);
        txt_maghrib = findViewById(R.id.txt_maghrib);
        txt_isya = findViewById(R.id.txt_isya);

        ftb = findViewById(R.id.floatingActionButton);

        progressBar2 = findViewById(R.id.progressBar2);

        getSupportActionBar().hide();

        kalender = Calendar.getInstance();
        cariTahun = new SimpleDateFormat("yyyy");
        cariBulan = new SimpleDateFormat("MM");
        cariHari = new SimpleDateFormat("dd");
        tahun = cariTahun.format(kalender.getTime());
        bulan = cariBulan.format(kalender.getTime());
        hari = cariHari.format(kalender.getTime());

        btn_detail = findViewById(R.id.tomboldetail);

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        String idnya = getIntent().getStringExtra(kiriman_id);
        String statnya = getIntent().getStringExtra(buat_status);
        String pesanya = getIntent().getStringExtra(pesan_masuk);

        if(statnya.equals("b")){
            btn_detail.setVisibility(View.VISIBLE);
        }else{
            btn_detail.setVisibility(View.GONE);
        }


        btn_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent pindah = new Intent(DetailActivity.this,InfoActivity.class);
                pindah.putExtra(InfoActivity.p_msk,pesanya);
                pindah.putExtra(InfoActivity.id_msk,idnya);
                startActivity(pindah);
            }
        });

        tarikData(idnya, tahun, bulan, hari);

        sharedPref = getSharedPreferences(idnya, Context.MODE_PRIVATE);

        boolean readingShared = sharedPref.getBoolean("likedStatus", false);

        if (readingShared == true) {
            ftb.setVisibility(View.GONE);
        } else {
            ftb.setVisibility(View.VISIBLE);
        }

        ftb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "idnya :" + idnya, Toast.LENGTH_SHORT).show();
                //  kirimData(idnya,nmKota);
                // SharedPreferences.Editor setStatusLiked = sharedPref.edit().putBoolean("likedStatus",true);
                // setStatusLiked.apply();
                // ftb.setVisibility(View.GONE);

                tampilAlert(idnya,nmKota);
            }
        });

    }

    private void tampilAlert(String idnya, String nmKota) {
//        AlertDialog.Builder alert = new AlertDialog.Builder(DetailActivity.this)
//                .setTitle("Disukai")
//
//                .setMessage("Kota ini disukai, silahkan periksa halaman 'Disukai' ")
//                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        dialogInterface.cancel();
//                    }
//                })
//                .setCancelable(true);
//                alert.show();
        // Create an alert builder
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Catatan");

        final View customLayout = getLayoutInflater().inflate(R.layout.custom_layout, null);
        builder.setView(customLayout);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                // send data from the
                                // AlertDialog to the Activity
                                EditText inipesanya = customLayout.findViewById(R.id.pesan_masuk);
                                Toast.makeText(DetailActivity.this, inipesanya.getText().toString(), Toast.LENGTH_SHORT).show();
                                kirimData(idnya,nmKota,inipesanya.getText().toString());

                            }
                        });

        // create and show
        // the alert dialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void kirimData(String idnya, String nmKota,String isiNya) {
        APIAmbilSemua krmData = ServerSendiri.konekRetrofit().create(APIAmbilSemua.class);
        Call<ResponseServerSendiri> trkData = krmData.buatData(idnya, nmKota,isiNya);
        trkData.enqueue(new Callback<ResponseServerSendiri>() {
            @Override
            public void onResponse(Call<ResponseServerSendiri> call, Response<ResponseServerSendiri> response) {
                Log.d("kirim", "berhasil " + response.body());
            }

            @Override
            public void onFailure(Call<ResponseServerSendiri> call, Throwable t) {
                Log.d("kirim", "gagal " + t.getMessage());
            }
        });
    }

    private void tarikData(String idnya, String tahunya, String bulanya, String harinya) {
        APIAmbilSemua trkData = ServerSumber.konekRetrofit().create(APIAmbilSemua.class);
        Call<ModelDetail> tampilData = trkData.ambilDetail(idnya, tahunya, bulanya, harinya);

        tampilData.enqueue(new Callback<ModelDetail>() {
            @Override
            public void onResponse(Call<ModelDetail> call, Response<ModelDetail> response) {
                Log.d("masuk", response.body().getData().getDaerah().toString());
                kotanya.setText(response.body().getData().getLokasi());
                txt_subuh.setText(response.body().getData().getJadwal().getSubuh());
                txt_dhuhur.setText(response.body().getData().getJadwal().getDzuhur());
                txt_ashar.setText(response.body().getData().getJadwal().getAshar());
                txt_maghrib.setText(response.body().getData().getJadwal().getMaghrib());
                txt_isya.setText(response.body().getData().getJadwal().getIsya());
                progressBar2.setVisibility(View.GONE);
                nmKota = response.body().getData().getLokasi();
            }

            @Override
            public void onFailure(Call<ModelDetail> call, Throwable t) {
                Log.d("masuk", "gagal " + t.getMessage());
            }
        });


    }

    public void pindahaman() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}