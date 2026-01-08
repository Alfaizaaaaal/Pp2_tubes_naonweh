package panen.controller;

import panen.model.*;
import java.util.ArrayList;

public class PanenController {

    public ArrayList<Petani> petaniList = new ArrayList<>();
    public ArrayList<Lahan> lahanList = new ArrayList<>();
    public ArrayList<Tanaman> tanamanList = new ArrayList<>();

    // ===== VALIDASI =====
    public void validasiText(String v, String field) {
        if (v == null || v.trim().isEmpty())
            throw new IllegalArgumentException(field + " tidak boleh kosong");
    }

    public int validasiAngka(String v, String field) {
        try {
            int n = Integer.parseInt(v);
            if (n <= 0)
                throw new Exception();
            return n;
        } catch (Exception e) {
            throw new IllegalArgumentException(field + " harus angka > 0");
        }
    }

    // ===== PETANI =====
    public void tambahPetani(String nama) {
        validasiText(nama, "Nama Petani");
        petaniList.add(new Petani(nama));
    }

    public void editPetani(int i, String nama) {
        validasiText(nama, "Nama Petani");
        petaniList.set(i, new Petani(nama));
    }

    public void hapusPetani(int i) {
        petaniList.remove(i);
    }

    // ===== LAHAN =====
    public void tambahLahan(String nama, Petani p) {
        validasiText(nama, "Nama Lahan");
        if (p == null)
            throw new IllegalArgumentException("Petani harus dipilih");
        lahanList.add(new Lahan(nama, p));
    }

    public void editLahan(int i, String nama, Petani p) {
        validasiText(nama, "Nama Lahan");
        lahanList.set(i, new Lahan(nama, p));
    }

    public void hapusLahan(int i) {
        lahanList.remove(i);
    }

    // ===== TANAMAN =====
    public void tambahTanaman(String nama, String jumlah, String tanggal, Lahan l) {
        validasiText(nama, "Nama Tanaman");
        validasiText(tanggal, "Tanggal Panen");
        if (l == null)
            throw new IllegalArgumentException("Lahan harus dipilih");

        int jml = validasiAngka(jumlah, "Jumlah");
        tanamanList.add(new Tanaman(nama, jml, tanggal, l));
    }

    public void editTanaman(int i, String nama, String jumlah, String tanggal, Lahan l) {
        validasiText(nama, "Nama Tanaman");
        int jml = validasiAngka(jumlah, "Jumlah");
        tanamanList.set(i, new Tanaman(nama, jml, tanggal, l));
    }

    public void hapusTanaman(int i) {
        tanamanList.remove(i);
    }

    // ===== TOTAL =====
    public int totalPanenPetani(Petani p) {
        int total = 0;
        for (Tanaman t : tanamanList)
            if (t.lahan.petani == p)
                total += t.jumlah;
        return total;
    }
}
