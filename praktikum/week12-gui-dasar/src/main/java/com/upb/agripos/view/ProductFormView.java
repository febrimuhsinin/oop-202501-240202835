package com.upb.agripos.view;

import com.upb.agripos.controller.ProductController;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class ProductFormView extends VBox {
    private final ProductController controller = new ProductController();
    private ListView<String> listView = new ListView<>();

    public ProductFormView() {
        this.setPadding(new Insets(20));
        this.setSpacing(10);

        GridPane grid = new GridPane();
        grid.setHgap(10); grid.setVgap(10);

        TextField txtCode = new TextField(), txtName = new TextField(), 
                  txtPrice = new TextField(), txtStock = new TextField();
        Button btnAdd = new Button("Tambah Produk");

        grid.add(new Label("Kode:"), 0, 0); grid.add(txtCode, 1, 0);
        grid.add(new Label("Nama:"), 0, 1); grid.add(txtName, 1, 1);
        grid.add(new Label("Harga:"), 0, 2); grid.add(txtPrice, 1, 2);
        grid.add(new Label("Stok:"), 0, 3); grid.add(txtStock, 1, 3);
        grid.add(btnAdd, 1, 4);

        // Event Handling
        btnAdd.setOnAction(event -> {
            try {
                controller.add(txtCode.getText(), txtName.getText(), 
                               txtPrice.getText(), txtStock.getText());
                listView.getItems().add(txtCode.getText() + " - " + txtName.getText());
                txtCode.clear(); txtName.clear(); txtPrice.clear(); txtStock.clear();
            } catch (Exception e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }
        });

        this.getChildren().addAll(new Label("Form Input Produk"), grid, new Label("Daftar:"), listView);
    }
}