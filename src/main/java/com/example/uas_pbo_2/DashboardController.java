package com.example.uas_pbo_2;

import javafx.event.ActionEvent;
import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class DashboardController {
    private Stage stage;
    private Scene scene;
    private Parent root;

    public void logout(ActionEvent actionEvent) throws IOException {
        root = FXMLLoader.load(DashboardController.class.getResource("Login.fxml"));
        stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public void masteruser(ActionEvent actionEvent) throws IOException {
        root = FXMLLoader.load(DashboardController.class.getResource("MasterUser.fxml"));
        stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public void masterkamar(ActionEvent actionEvent) throws IOException {
        root = FXMLLoader.load(DashboardController.class.getResource("MasterKamar.fxml"));
        stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void informasipesanan(ActionEvent actionEvent) {
    }

    public void pesanhotel(ActionEvent actionEvent) throws IOException{
        root = FXMLLoader.load(DashboardController.class.getResource("Pesan_Hotel.fxml"));
        stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
