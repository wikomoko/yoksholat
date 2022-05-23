package com.example.marisholat.API;

import com.example.marisholat.Model.DataModelSemua;
import com.example.marisholat.Model.ModelDetail;
import com.example.marisholat.Model.ResponMasjid;
import com.example.marisholat.Model.ResponseArtikel;
import com.example.marisholat.Model.ResponseCariKota;
import com.example.marisholat.Model.ResponsePesan;
import com.example.marisholat.Model.ResponseServerSendiri;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface APIAmbilSemua {
    //fungsi buat ke sumber
    @GET("kota/semua")
    Call<ArrayList<DataModelSemua>> ambilSemuaData();

    @GET("jadwal/{id}/{tahun}/{bulan}/{hari}")
    Call<ModelDetail> ambilDetail(
            @Path("id") String id,
            @Path("tahun") String tahun,
            @Path("bulan") String bulan,
            @Path("hari") String hari
            );

    @GET("kota/cari/{kota}")
    Call<ResponseCariKota> ambilCariKota(
            @Path("kota") String kota
    );

    //fungsi server sendiri
    @GET("read.php")
    Call<ResponseServerSendiri> ambilDataServerSendiri();

    @GET("artikel.php")
    Call<ResponseArtikel> ambilArtikel();

    @FormUrlEncoded
    @POST("create.php")
    Call<ResponseServerSendiri> buatData(
            @Field("id") String id,
            @Field("lokasi") String alamat,
            @Field("isi") String isi

    );

    @FormUrlEncoded
    @POST("delete.php")
    Call<ResponseServerSendiri> hapusDatanya(
            @Field("id") String id
    );

    @FormUrlEncoded
    @POST("pud.php")
    Call<ResponsePesan> dorongData(
        @Field("id") String id,
        @Field("isi") String isi
    );

    @FormUrlEncoded
    @POST("gmasjid.php")
    Call<ResponMasjid> dorongMasjid(
            @Field("id") String id
    );


}
