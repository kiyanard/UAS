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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class PesanHotelController implements Initializable {

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
    private TableColumn<PesanHotel, String> id_transaksi;
    @FXML
    private TableColumn<PesanHotel, String> nama;
    @FXML
    private TableColumn<PesanHotel, String> kode_kamar;
    @FXML
    private TableColumn<PesanHotel, Integer> lama_menginap;
    @FXML
    private TableColumn<PesanHotel, String> tanggal;
    @FXML
    private TableColumn<PesanHotel, Integer> total_harga;

    //Texfield
    @FXML
    TableView<PesanHotel> tbPesanHotel;
    @FXML
    private TextField tfId_transaksi;
    @FXML
    private TextField tfNama;
    @FXML
    private TextField tfTanggal;
    @FXML
    private TextField tfLama_menginap;
    @FXML
    private TextField tfTotal_harga;
    @FXML
    private DatePicker datePicker;

    ObservableList<PesanHotel> listPesanHotel = FXCollections.observableArrayList() ;
    boolean flagAdd = true;

    public void pilihPesanHotel(MouseEvent mouseEvent) {
        PesanHotel pesanHotel = tbPesanHotel.getSelectionModel().getSelectedItem();
        tfId_transaksi.setText(pesanHotel.getId_transaksi());
        tfNama.setText(pesanHotel.getNama());
        tfLama_menginap.setText(pesanHotel.getLama_menginap());
        tfTotal_harga.setText(pesanHotel.getTotal_harga());
    }

    public void add(ActionEvent actionEvent) {
        setButton(false,false,false,true,true);
        clearTeks();
        setTeks(true);
        tfId_transaksi.requestFocus();
    }

    @FXML
    void edit(ActionEvent event) {
        flagAdd=false;
        setButton(false,false,false,true,true);
        setTeks(true);
        tfId_transaksi.requestFocus();
    }

    @FXML
    void del(ActionEvent event) {
        Connection conn = DBConnection.getConn();
        String sql="delete from pesan_hotel where id_transaksi=?";
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement(sql);
            st.setString(1,tfId_transaksi.getText());
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
            String sql="insert into pesan_hotel (id_transaksi, nama, kode_kamar, lama_menginap, tanggal, total_harga) values (?,?,?,?,?,?)";try {
                PreparedStatement st = conn.prepareStatement(sql);

                //Ambil harga
                String query = "SELECT harga_jual FROM masterkamar WHERE kode_kamar = ?";
                PreparedStatement statement = conn.prepareStatement(query);
                statement.setString(1, comboBox.getValue());
                ResultSet resultSet = statement.executeQuery();

                int hargaJual = 0;
                if (resultSet.next()) {
                    hargaJual = resultSet.getInt("harga_jual");
                }

                st.setString(1, tfId_transaksi.getText());
                st.setString(2, tfNama.getText());
                st.setString(3, comboBox.getValue());
                st.setString(4, tfLama_menginap.getText());
                st.setString(5, String.valueOf(datePicker.getValue()));
                st.setString(6, String.valueOf(hargaJual * Integer.parseInt(String.valueOf(tfLama_menginap.getText()))));
                st.executeUpdate();
                loadData();
            } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
//do something..
        } else {
            String sql="update pesan_hotel set nama=?,kode_kamar=?,lama_menginap=?, tanggal=?, total_harga=? where id_transaksi=?";
            try {
                PreparedStatement st = conn.prepareStatement(sql);
                st.setString(1,tfNama.getText());
                st.setString(2,comboBox.getValue());
                st.setString(3,tfLama_menginap.getText());
                st.setString(4,String.valueOf(datePicker.getValue()));
                st.setString(5,tfTotal_harga.getText());
                st.setString(5,tfId_transaksi.getText());
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
        listPesanHotel = DBUtil.getDataPesanHotel();
        tbPesanHotel.setItems(listPesanHotel);
    }

    protected void setButton(Boolean b1,Boolean b2,Boolean b3,Boolean b4,Boolean b5){
        bAdd.setDisable(!b1);
        bEdit.setDisable(!b2);
        bDel.setDisable(!b3);
        bUpdate.setDisable(!b4);
        bCancel.setDisable(!b5);
    }

    protected void clearTeks(){
        tfId_transaksi.setText(null);
        tfNama.setText(null);
        tfLama_menginap.setText(null);
    }

    protected void setTeks(Boolean b){
        tfId_transaksi.setEditable(b);
        tfNama.setEditable(b);
        tfLama_menginap.setEditable(b);
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

        root = FXMLLoader.load(PesanHotelController.class.getResource("Dashboard.fxml"));
        stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    void initTabel(){
        id_transaksi.setCellValueFactory(new PropertyValueFactory<PesanHotel,String>("id_transaksi"));
        nama.setCellValueFactory(new PropertyValueFactory<PesanHotel,String>("nama"));
        kode_kamar.setCellValueFactory(new PropertyValueFactory<PesanHotel, String>("kode_kamar"));
        lama_menginap.setCellValueFactory(new PropertyValueFactory<PesanHotel, Integer>("lama_menginap"));
        tanggal.setCellValueFactory(new PropertyValueFactory<PesanHotel, String>("tanggal"));
        total_harga.setCellValueFactory(new PropertyValueFactory<PesanHotel, Integer>("total_harga"));
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Connection conn = DBConnection.getConn(); // Mendapatkan koneksi ke database
        String query = "SELECT kode_kamar,harga_jual FROM masterkamar"; // Query untuk mengambil data dari tabel
        PreparedStatement statement = null;
        try {
            statement = conn.prepareStatement(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        ResultSet resultSet = null;
        try {
            resultSet = statement.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        while (true) {
            try {
                if (!resultSet.next()) break;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            String data = null; // Ubah "nama" sesuai dengan kolom yang ingin Anda ambil
            try {
                data = resultSet.getString("kode_kamar");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            comboBox.getItems().add(data);
        }
        initTabel();
        loadData();
        setButton(true,true,true,false,false);
        setTeks(false);
    }

    public void Kode_kamar(ActionEvent actionEvent) {
    }
}
