/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplication2;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Mega-PC
 */
public class MenuController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }  
    Stage dialogStage = new Stage();
    Scene scene;
    @FXML
    private void userEvent(ActionEvent event) throws SQLException, IOException {
        scene = new Scene(FXMLLoader.load(getClass().getResource("FXML.fxml")));
                dialogStage.setScene(scene);
                dialogStage.show();
    }
    @FXML
    private void userdev(ActionEvent event) throws SQLException, IOException {
        scene = new Scene(FXMLLoader.load(getClass().getResource("dev.fxml")));
                dialogStage.setScene(scene);
                dialogStage.show();
    }
    @FXML
    private void userdevgrp(ActionEvent event) throws SQLException, IOException {
        scene = new Scene(FXMLLoader.load(getClass().getResource("devgroup.fxml")));
                dialogStage.setScene(scene);
                dialogStage.show();
    }
    
}
