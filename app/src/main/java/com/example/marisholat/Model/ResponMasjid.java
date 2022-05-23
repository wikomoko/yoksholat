package com.example.marisholat.Model;

import java.util.List;

public class ResponMasjid {
    private int kode;
    private String pesan;
    private List<DataMasjid> data;

    public int getKode() {
        return kode;
    }

    public void setKode(int kode) {
        this.kode = kode;
    }

    public String getPesan() {
        return pesan;
    }

    public void setPesan(String pesan) {
        this.pesan = pesan;
    }

    public List<DataMasjid> getData() {
        return data;
    }

    public void setData(List<DataMasjid> data) {
        this.data = data;
    }
}
