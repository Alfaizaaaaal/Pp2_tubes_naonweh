package panen.model;

public class Tanaman {
    public String nama;
    public int jumlah;
    public String tanggal;
    public Lahan lahan;

    public Tanaman(String nama, int jumlah, String tanggal, Lahan lahan) {
        this.nama = nama;
        this.jumlah = jumlah;
        this.tanggal = tanggal;
        this.lahan = lahan;
    }
}
