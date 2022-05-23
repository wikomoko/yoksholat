package com.example.marisholat.Model;

public class IsiDataDetail {
    private String id;
    private String lokasi;
    private String daerah;
    private JadwalDetail jadwal;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLokasi() {
        return lokasi;
    }

    public void setLokasi(String lokasi) {
        this.lokasi = lokasi;
    }

    public String getDaerah() {
        return daerah;
    }

    public void setDaerah(String daerah) {
        this.daerah = daerah;
    }

    public JadwalDetail getJadwal() {
        return jadwal;
    }

    public void setJadwal(JadwalDetail jadwal) {
        this.jadwal = jadwal;
    }
}
