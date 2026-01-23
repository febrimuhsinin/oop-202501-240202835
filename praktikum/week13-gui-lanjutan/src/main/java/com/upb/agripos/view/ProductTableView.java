package com.upb.agripos.view;

import com.upb.agripos.controller.ProductController;
import com.upb.agripos.model.Product;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class ProductTableView extends VBox {
    private final ProductController controller = new ProductController();
    
    // Komponen GUI
    private TableView<Product> table = new TableView<>();
    private TextField txtCode = new TextField();
    private TextField txtName = new TextField();
    private TextField txtPrice = new TextField();
    private TextField txtStock = new TextField();

    public ProductTableView() {
        this.setPadding(new Insets(20));
        this.setSpacing(10);

        // 1. Setup Form Input
        GridPane formGrid = new GridPane();
        formGrid.setHgap(10); formGrid.setVgap(10);
        
        formGrid.add(new Label("Kode:"), 0, 0); formGrid.add(txtCode, 1, 0);
        formGrid.add(new Label("Nama:"), 0, 1); formGrid.add(txtName, 1, 1);
        formGrid.add(new Label("Harga:"), 0, 2); formGrid.add(txtPrice, 1, 2);
        formGrid.add(new Label("Stok:"), 0, 3); formGrid.add(txtStock, 1, 3);

        Button btnAdd = new Button("Tambah Produk");
        Button btnDelete = new Button("Hapus Produk");
        
        HBox buttonBox = new HBox(10, btnAdd, btnDelete);
        formGrid.add(buttonBox, 1, 4);

        // 2. Setup TableView
        setupTable();
        loadData(); // Load data awal dari DB

        // 3. Event Handling dengan Lambda Expression
        
        // Aksi Tambah
        btnAdd.setOnAction(e -> {
            try {
                controller.addProduct(
                    txtCode.getText(), txtName.getText(),
                    Double.parseDouble(txtPrice.getText()),
                    Integer.parseInt(txtStock.getText())
                );
                loadData(); // Reload tabel setelah insert
                clearFields();
            } catch (Exception ex) {
                showAlert("Error", ex.getMessage());
            }
        });

        // Aksi Hapus (Lambda)
        btnDelete.setOnAction(e -> {
            Product selected = table.getSelectionModel().getSelectedItem();
            if (selected != null) {
                try {
                    controller.deleteProduct(selected.getCode());
                    loadData(); // Reload tabel setelah delete
                } catch (Exception ex) {
                    showAlert("Error", "Gagal hapus: " + ex.getMessage());
                }
            } else {
                showAlert("Peringatan", "Pilih produk di tabel untuk dihapus!");
            }
        });

        this.getChildren().addAll(new Label("Manajemen Produk Agri-POS (Week 13)"), formGrid, table);
    }

    @SuppressWarnings("unchecked")
    private void setupTable() {
        // Kolom Kode
        TableColumn<Product, String> colCode = new TableColumn<>("Kode");
        colCode.setCellValueFactory(new PropertyValueFactory<>("code"));

        // Kolom Nama
        TableColumn<Product, String> colName = new TableColumn<>("Nama Produk");
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colName.setMinWidth(200);

        // Kolom Harga
        TableColumn<Product, Double> colPrice = new TableColumn<>("Harga");
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

        // Kolom Stok
        TableColumn<Product, Integer> colStock = new TableColumn<>("Stok");
        colStock.setCellValueFactory(new PropertyValueFactory<>("stock"));

        table.getColumns().addAll(colCode, colName, colPrice, colStock);
    }

    private void loadData() {
        // Mengintegrasikan Koleksi Objek dengan GUI
        table.setItems(controller.loadProducts());
    }

    private void clearFields() {
        txtCode.clear(); txtName.clear(); txtPrice.clear(); txtStock.clear();
    }

    private void showAlert(String title, String message) {
        new Alert(Alert.AlertType.INFORMATION, message).show();
    }
}