package main.java.com.upb.agripos.model.pembayaran;

import main.java.com.upb.agripos.model.kontrak.Validatable;
import main.java.com.upb.agripos.model.kontrak.Receiptable;

public class TransferBank extends Pembayaran implements Validatable, Receiptable {
    private String norek;
    private String pin;

    public TransferBank(String invoiceNo, double total, String norek, String pin) {
        super(invoiceNo, total);
        this.norek = norek;
        this.pin = pin;
    }

    @Override
    public double biaya() {
        return 3500; // biaya tetap
    }

    @Override
    public boolean validasi() {
        return pin != null && pin.length() == 6;
    }

    @Override
    public boolean prosesPembayaran() {
        return validasi();
    }

    @Override
    public String cetakStruk() {
        return String.format(
            "INVOICE %s | BANK: %s | TOTAL+FEE: %.2f | STATUS: %s",
            invoiceNo, norek, totalBayar(), prosesPembayaran() ? "BERHASIL" : "GAGAL");
    }
}
