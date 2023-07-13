package com.example.uas_pbo_2;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController{
    @FXML
private Button btnLogin;
    @FXML
    private Button btnsignUp;
    @FXML
    private TextField tfPassword;
    @FXML
    private TextField tfUser;
    @FXML
    public void prosesLogin(ActionEvent e){
        DBUtil.loginUser(e,tfUser.getText(),tfPassword.getText());
    }

}
