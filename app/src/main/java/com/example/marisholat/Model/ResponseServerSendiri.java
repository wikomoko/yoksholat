package com.example.marisholat.Model;

import java.util.List;

public class ResponseServerSendiri {
    private int kode;
    private String pesan;
    private List<DataModelSemua> data;

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

    public List<DataModelSemua> getData() {
        return data;
    }

    public void setData(List<DataModelSemua> data) {
        this.data = data;
    }
}
