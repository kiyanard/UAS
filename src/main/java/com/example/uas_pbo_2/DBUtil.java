package com.example.uas_pbo_2;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBUtil {

    private static Stage stage;
    private static Scene scene;
    private Parent root;
    public static void loginUser(ActionEvent event, String userName, String password) {
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        String sql;
        Parent root;
        try {
            conn= DBConnection.getConn();
            sql="select * from user where username=?";
            st = conn.prepareStatement(sql);
            st.setString(1, userName);
            rs = st.executeQuery();
            if (!rs.isBeforeFirst()) {
                System.out.println("User not found..");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("User Tdk ditemukan");
                alert.show();
            } else {
                while (rs.next()) {
                    String rpassword=rs.getString("password");
                    if (rpassword.equals(password)) {
                        root = FXMLLoader.load(DBUtil.class.getResource("Dashboard.fxml"));
                        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                        scene = new Scene(root);
                        stage.setScene(scene);
                        stage.show();
                    }else {
                        System.out.println("Password not valid..");
                        Alert alert = new Alert(Alert.AlertType.ERROR);alert.setContentText("Password Salah");
                        alert.show();
                    }
                }
            }
        }
        catch(SQLException | IOException e) {
            e.printStackTrace();
        } finally {
            if (rs!=null) {
                try {
                    rs.close();
                } catch(SQLException e) {
                    e.printStackTrace();
                }
            }
            if (st!=null) {
                try {
                    st.close();
                } catch(SQLException e) {
                    e.printStackTrace();
                }
            }
            if (conn!=null) {
                try {
                    conn.close();
                } catch(SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public static ObservableList<User> getDataUser(){
        ObservableList<User> listUser = FXCollections.observableArrayList();
        try {
            Connection c = DBConnection.getConn();
            String sql="select * from user";
            PreparedStatement ps =c.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                User m = new User(rs.getString("username"),rs.getString("password"), rs.getInt("id_user"));
                listUser.add(m);
            }
            return listUser;
        } catch (SQLException ex) {
            Logger.getLogger(DBUtil.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public static ObservableList<Kamar> getDataKamar(){
        ObservableList<Kamar> listKamar = FXCollections.observableArrayList();
        try {
            Connection c = DBConnection.getConn();
            String sql="select * from masterkamar";
            PreparedStatement ps =c.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Kamar m = new Kamar(rs.getString("kode_kamar"),rs.getInt("no_kamar"), rs.getString("jenis_kamar"), rs.getInt("harga_jual"));
                listKamar.add(m);
            }
            return listKamar;
        } catch (SQLException ex) {
            Logger.getLogger(DBUtil.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public static ObservableList<PesanHotel> getDataPesanHotel(){
        ObservableList<PesanHotel> listPesanHotel = FXCollections.observableArrayList();
        try {
            Connection c = DBConnection.getConn();
            String sql="select * from pesan_hotel";
            PreparedStatement ps =c.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                PesanHotel m = new PesanHotel(rs.getString("id_transaksi"),rs.getString("nama"), rs.getString("kode_kamar"), rs.getInt("lama_menginap"),rs.getString("tanggal"), rs.getInt("total_harga"));
                listPesanHotel.add(m);
            }
            return listPesanHotel;
        } catch (SQLException ex) {
            Logger.getLogger(DBUtil.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
}
