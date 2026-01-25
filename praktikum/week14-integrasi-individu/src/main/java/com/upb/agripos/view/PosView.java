package com.upb.agripos.view;

import com.upb.agripos.controller.PosController;
import com.upb.agripos.model.CartItem;
import com.upb.agripos.model.Product;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;

public class PosView extends BorderPane {
    private final PosController controller = new PosController();
    private TableView<Product> table = new TableView<>();
    private ListView<CartItem> cartList = new ListView<>();
    private Label lblTotal = new Label("Total: Rp 0");

    // Input Fields
    private TextField txtCode = new TextField();
    private TextField txtName = new TextField();
    private TextField txtPrice = new TextField();
    private TextField txtStock = new TextField();

    public PosView() {
        setPadding(new Insets(15));

        // --- BAGIAN KIRI: Inventory Gudang ---
        VBox left = new VBox(10);
        left.setPrefWidth(500); // Diperlebar agar tabel muat
        setupTable(); // Memanggil setup tabel yang sudah diperbaiki
        
        GridPane inputGrid = new GridPane();
        inputGrid.setHgap(5); inputGrid.setVgap(5);
        inputGrid.addRow(0, new Label("Kode:"), txtCode, new Label("Nama:"), txtName);
        inputGrid.addRow(1, new Label("Harga:"), txtPrice, new Label("Stok:"), txtStock);

        Button btnAdd = new Button("Tambah ke DB");
        Button btnDel = new Button("Hapus dari DB");
        Button btnToCart = new Button("Masuk Keranjang >>");

        HBox crudBox = new HBox(5, btnAdd, btnDel);
        left.getChildren().addAll(new Label("INVENTORY GUDANG"), table, inputGrid, crudBox, new Separator(), btnToCart);

        // --- BAGIAN KANAN: Kasir ---
        VBox right = new VBox(10);
        right.setPrefWidth(300);
        Button btnPay = new Button("Bayar / Checkout");
        right.getChildren().addAll(new Label("KASIR / KERANJANG"), cartList, lblTotal, btnPay);

        setCenter(left);
        setRight(right);

        // Load data awal
        refreshData();

        // --- EVENT HANDLERS ---
        btnAdd.setOnAction(e -> {
            try {
                controller.addProductToDb(
                    txtCode.getText(), 
                    txtName.getText(), 
                    Double.parseDouble(txtPrice.getText()), 
                    Integer.parseInt(txtStock.getText())
                );
                refreshData(); clearFields();
            } catch (Exception ex) { showAlert("Error: " + ex.getMessage()); }
        });

        btnDel.setOnAction(e -> {
            Product p = table.getSelectionModel().getSelectedItem();
            if (p != null) {
                try { controller.deleteProduct(p.getCode()); refreshData(); } 
                catch (Exception ex) { showAlert("Error: " + ex.getMessage()); }
            } else { showAlert("Pilih produk dulu!"); }
        });

        btnToCart.setOnAction(e -> {
            Product p = table.getSelectionModel().getSelectedItem();
            if (p != null) {
                controller.addToCart(p, 1);
                refreshCart();
            } else { showAlert("Pilih produk dulu!"); }
        });

        btnPay.setOnAction(e -> {
            controller.checkout();
            refreshCart();
            showAlert("Transaksi Berhasil!");
        });
    }

    // --- PERBAIKAN UTAMA DI SINI ---
    private void setupTable() {
        TableColumn<Product, String> c1 = new TableColumn<>("Kode");
        c1.setCellValueFactory(new PropertyValueFactory<>("code"));
        
        TableColumn<Product, String> c2 = new TableColumn<>("Nama");
        c2.setCellValueFactory(new PropertyValueFactory<>("name"));
        c2.setMinWidth(150);
        
        TableColumn<Product, Double> c3 = new TableColumn<>("Harga");
        c3.setCellValueFactory(new PropertyValueFactory<>("price"));

        // Kolom Stok (Wajib ada)
        TableColumn<Product, Integer> c4 = new TableColumn<>("Stok");
        c4.setCellValueFactory(new PropertyValueFactory<>("stock"));

        table.getColumns().addAll(c1, c2, c3, c4);
    }

    private void refreshData() { table.setItems(controller.loadProducts()); }
    private void refreshCart() {
        cartList.setItems(controller.getCartItems());
        lblTotal.setText("Total: Rp " + controller.getCartTotal());
    }
    private void clearFields() { txtCode.clear(); txtName.clear(); txtPrice.clear(); txtStock.clear(); }
    private void showAlert(String m) { new Alert(Alert.AlertType.INFORMATION, m).show(); }
}