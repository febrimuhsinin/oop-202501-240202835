class Mahasiswa {
    String nama;
    int nim;

    Mahasiswa(String n, int u) {
        nama = n;
        nim = u;
    }

    void sapa() {
        System.out.println("Hello word, i am " + nama + "-" + nim);
    }
}

public class HelloOOP {
    public static void main(String[] args) {
        Mahasiswa m = new Mahasiswa("Febri Muhsinin", 240202835);
        m.sapa();
    }
}