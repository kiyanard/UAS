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
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class MasterUserController implements Initializable {

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

    //Table
    @FXML
    private TableColumn<User, String> username;
    @FXML
    private TableColumn<User, String> password;
    @FXML
    private TableColumn<User, Integer> id_user;

    //Texfield
    @FXML
    TableView<User> tbUser;
    @FXML
    private TextField tfUsername;
    @FXML
    private TextField tfPassword;
    @FXML
    private TextField tfIdUser;
    ObservableList<User> listUser = FXCollections.observableArrayList() ;

    boolean flagAdd = true;
    public void pilihUser(MouseEvent mouseEvent) {
        User user = tbUser.getSelectionModel().getSelectedItem();
        tfUsername.setText(user.getUsername());
        tfPassword.setText(user.getPassword());
        tfIdUser.setText(user.getId_user());
    }

    public void add(ActionEvent actionEvent) {
        setButton(false,false,false,true,true);
        clearTeks();
        setTeks(true);
        tfIdUser.requestFocus();
    }

    @FXML
    void edit(ActionEvent event) {
        flagAdd=false;
        setButton(false,false,false,true,true);
        setTeks(true);
        tfIdUser.requestFocus();
    }

    @FXML
    void del(ActionEvent event) {
        Connection conn = DBConnection.getConn();
        String sql="delete from user where id_user=?";
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement(sql);
            st.setString(1,tfIdUser.getText());
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
            String sql="insert into user (username, password, id_user) values (?,?,?)";try {
                PreparedStatement st = conn.prepareStatement(sql);
                st.setString(1,tfUsername.getText());
                st.setString(2,tfPassword.getText());
                st.setString(3,tfIdUser.getText());
                st.executeUpdate();
                loadData();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
//do something..
        } else {
            String sql="update user set username=?,password=? where id_user=?";
            try {
                PreparedStatement st = conn.prepareStatement(sql);
                st.setString(1,tfUsername.getText());
                st.setString(2,tfPassword.getText());
                st.setString(3,tfIdUser.getText());
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
        listUser = DBUtil.getDataUser();
        tbUser.setItems(listUser);
    }

    protected void setButton(Boolean b1,Boolean b2,Boolean b3,Boolean b4,Boolean b5){
        bAdd.setDisable(!b1);
        bEdit.setDisable(!b2);
        bDel.setDisable(!b3);
        bUpdate.setDisable(!b4);
        bCancel.setDisable(!b5);
    }

    protected void clearTeks(){
        tfUsername.setText(null);
        tfPassword.setText(null);
        tfIdUser.setText(null);
    }

    protected void setTeks(Boolean b){
        tfUsername.setEditable(b);
        tfPassword.setEditable(b);
        tfIdUser.setEditable(b);
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

        root = FXMLLoader.load(MasterUserController.class.getResource("Dashboard.fxml"));
        stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    void initTabel(){
        username.setCellValueFactory(new PropertyValueFactory<User,String>("username"));
        password.setCellValueFactory(new PropertyValueFactory<User,String>("password"));
        id_user.setCellValueFactory(new PropertyValueFactory<User, Integer>("id_user"));
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initTabel();
        loadData();
        setButton(true,true,true,false,false);
        setTeks(false);
    }
}
