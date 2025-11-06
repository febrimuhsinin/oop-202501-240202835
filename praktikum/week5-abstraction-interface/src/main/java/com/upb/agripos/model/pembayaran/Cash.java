package main.java.com.upb.agripos.model.pembayaran;

import main.java.com.upb.agripos.model.kontrak.Receiptable;

public class Cash extends Pembayaran implements Receiptable {
    private double tunai;

    public Cash(String invoiceNo, double total, double tunai) {
        super(invoiceNo, total);
        this.tunai = tunai;
    }

    @Override
    public double biaya() {
        return 0.0; // tidak ada biaya tambahan
    }

    @Override
    public boolean prosesPembayaran() {
        return tunai >= totalBayar(); // berhasil jika uang cukup
    }

    @Override
    public String cetakStruk() {
        if (prosesPembayaran()) {
            return String.format(
                "INVOICE %s | TOTAL: %.2f | CASH: %.2f | KEMBALI: %.2f | STATUS: BERHASIL",
                invoiceNo, totalBayar(), tunai, (tunai - totalBayar()));
        } else {
            return String.format(
                "INVOICE %s | TOTAL: %.2f | CASH: %.2f | STATUS: GAGAL (UANG KURANG)",
                invoiceNo, totalBayar(), tunai);
        }
    }
}
