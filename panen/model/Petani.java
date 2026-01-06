package panen.model;

public class Petani {
    public String nama;

    public Petani(String nama) {
        this.nama = nama;
    }

    @Override
    public String toString() {
        return nama;
    }
}
