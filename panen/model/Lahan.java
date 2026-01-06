package panen.model;

public class Lahan {
    public String nama;
    public Petani petani;

    public Lahan(String nama, Petani petani) {
        this.nama = nama;
        this.petani = petani;
    }

    @Override
    public String toString() {
        return nama;
    }
}
