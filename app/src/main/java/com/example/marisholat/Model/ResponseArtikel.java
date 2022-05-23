package com.example.marisholat.Model;

import java.util.List;

public class ResponseArtikel {
    private int kode;
    private String pesan;
    private List<DataModelArtikel> data;

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

    public List<DataModelArtikel> getData() {
        return data;
    }

    public void setData(List<DataModelArtikel> data) {
        this.data = data;
    }
}
