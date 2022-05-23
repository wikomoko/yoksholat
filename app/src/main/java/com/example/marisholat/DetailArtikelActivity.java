package com.example.marisholat;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class DetailArtikelActivity extends AppCompatActivity {

    public static final String dapet_judul = "kiriman";
    public static final String dapet_isi = "kiriman_isi";
    public static final String dapet_gambar = "gambarnya";


    private ImageView btn_balik_dari_artikel;
    private TextView judul,isi;
    private ImageView gambar;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_artikel);

        getSupportActionBar().hide();

        btn_balik_dari_artikel = findViewById(R.id.balik_dari_artikel);

        btn_balik_dari_artikel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        judul = findViewById(R.id.judul_artikel_di_detail);
        isi = findViewById(R.id.isi_artikel_di_detail);
        gambar = findViewById(R.id.gambar_artikel_di_detail);

        String judulnya = getIntent().getStringExtra(dapet_judul);
        String isinya = getIntent().getStringExtra(dapet_isi);

        String gambarnya = getIntent().getStringExtra(dapet_gambar);

        judul.setText(judulnya);
        isi.setText(isinya);



        Picasso.get().load("http://192.168.43.163/ysholat/gbr_artiker/"+gambarnya).into(gambar);


        gambar.setScaleType(ImageView.ScaleType.CENTER_CROP);

        Log.d("isigambar",gambarnya);


    }
}