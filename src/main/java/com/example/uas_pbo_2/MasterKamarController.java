package com.example.uas_pbo_2;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class MasterKamarController implements Initializable {

    //Tombol
    @FXML
    private Button bAdd;
    @FXML
    private Button bCancel;
    @FXML
    private Button bDel;
    @FXML
    private Button bEdit;
    @FXML
    private Button bUpdate;
    @FXML
    ComboBox<String> comboBox = new ComboBox<>();

    //Table
    @FXML
    private TableColumn<Kamar, String> kode_kamar;
    @FXML
    private TableColumn<Kamar, Integer> no_kamar;
    @FXML
    private TableColumn<Kamar, String> jenis_kamar;
    @FXML
    private TableColumn<Kamar, Integer> harga_jual;

    //Texfield
    @FXML
    TableView<Kamar> tbKamar;
    @FXML
    private TextField tfKode_kamar;
    @FXML
    private TextField tfNo_kamar;
    @FXML
    private TextField tfHarga_jual;

    ObservableList<Kamar> listKamar = FXCollections.observableArrayList() ;

    boolean flagAdd = true;

    public void pilihKamar(MouseEvent mouseEvent) {
        Kamar kamar = tbKamar.getSelectionModel().getSelectedItem();
        tfKode_kamar.setText(kamar.getKode_kamar());
        tfNo_kamar.setText(kamar.getNo_kamar());
        tfHarga_jual.setText(kamar.getHarga_jual());
    }

    public void add(ActionEvent actionEvent) {
        setButton(false,false,false,true,true);
        clearTeks();
        setTeks(true);
        tfKode_kamar.requestFocus();
    }

    @FXML
    void edit(ActionEvent event) {
        flagAdd=false;
        setButton(false,false,false,true,true);
        setTeks(true);
        tfKode_kamar.requestFocus();
    }

    @FXML
    void del(ActionEvent event) {
        Connection conn = DBConnection.getConn();
        String sql="delete from masterkamar where kode_kamar=?";
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement(sql);
            st.setString(1,tfKode_kamar.getText());
            st.executeUpdate();
            loadData();
            clearTeks();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void update(ActionEvent event) {
        Connection conn = DBConnection.getConn();
        if (flagAdd){
            String sql="insert into masterkamar (kode_kamar, no_kamar, jenis_kamar, harga_jual) values (?,?,?,?)";try {
                PreparedStatement st = conn.prepareStatement(sql);
                st.setString(1,tfKode_kamar.getText());
                st.setString(2,tfNo_kamar.getText());
                st.setString(3,comboBox.getValue());
                st.setString(4,tfHarga_jual.getText());
                st.executeUpdate();
                loadData();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
//do something..
        } else {
            String sql="update masterkamar set no_kamar=?,jenis_kamar=?,harga_jual=? where kode_kamar=?";
            try {
                PreparedStatement st = conn.prepareStatement(sql);
                st.setString(1,tfNo_kamar.getText());
                st.setString(2,comboBox.getValue());
                st.setString(3,tfHarga_jual.getText());
                st.setString(4,tfKode_kamar.getText());
                st.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        loadData();
        setButton(true,true,true,false,false);
        clearTeks();
    }

    void loadData(){
        listKamar = DBUtil.getDataKamar();
        tbKamar.setItems(listKamar);
    }

    protected void setButton(Boolean b1,Boolean b2,Boolean b3,Boolean b4,Boolean b5){
        bAdd.setDisable(!b1);
        bEdit.setDisable(!b2);
        bDel.setDisable(!b3);
        bUpdate.setDisable(!b4);
        bCancel.setDisable(!b5);
    }

    protected void clearTeks(){
        tfKode_kamar.setText(null);
        tfNo_kamar.setText(null);
        tfHarga_jual.setText(null);
    }

    protected void setTeks(Boolean b){
        tfKode_kamar.setEditable(b);
        tfNo_kamar.setEditable(b);
        tfHarga_jual.setEditable(b);
    }

    @FXML
    void cancel(ActionEvent event) {
        setButton(true,true,true,false,false);
        clearTeks();
    }

    public void back(ActionEvent actionEvent) throws IOException {
        Stage stage;
        Scene scene;
        Parent root;

        root = FXMLLoader.load(MasterKamarController.class.getResource("Dashboard.fxml"));
        stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    void initTabel(){
        kode_kamar.setCellValueFactory(new PropertyValueFactory<Kamar,String>("kode_kamar"));
        no_kamar.setCellValueFactory(new PropertyValueFactory<Kamar,Integer>("no_kamar"));
        harga_jual.setCellValueFactory(new PropertyValueFactory<Kamar, Integer>("harga_jual"));
        jenis_kamar.setCellValueFactory(new PropertyValueFactory<Kamar, String>("jenis_kamar"));
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        comboBox.getItems().addAll("Suite", "Deluxe","Biasa");

        initTabel();
        loadData();
        setButton(true,true,true,false,false);
        setTeks(false);
    }

    public void jenis_kamar(ActionEvent actionEvent) {


    }
}
