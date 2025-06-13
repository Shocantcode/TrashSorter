package com.example.apkapplication;

public class SampahData {
    public String waktu;
    public String jenis;

    public SampahData() {} // Diperlukan Firebase

    public SampahData(String waktu, String jenis) {
        this.waktu = waktu;
        this.jenis = jenis;
    }
}
