/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplication2;

import javafx.scene.input.MouseEvent;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;


/**
 *
 * @author Mega-PC
 */
public class FXMLDocumentController implements Initializable {
 
    PreparedStatement st = null;
    ResultSet rs = null;
    Connection con = null;
    private Label label;
    @FXML
    private TextField id;
    @FXML
    private TextField fullname;
    @FXML
    private TextField mail;
    @FXML
    private ComboBox<String> gender;
    @FXML
    private Button bsave;
    @FXML
    private Button bupdate;
    @FXML
    private Button bdelete;
    @FXML
    private TableView<user> table;
    @FXML
    private TableColumn<user, Integer> colid;
    @FXML
    private TableColumn<user, String> colfullname;
    @FXML
    private TableColumn<user, String> colmail;
     @FXML
    private TableColumn<user, String> colgender;
    
 
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ObservableList<String> genderl
                = FXCollections.observableArrayList("Masculin", "Feminin","autre");
        gender.setItems(genderl);
        gender.setValue("autre");
        try {
            // TODO
            affiche();
        } catch (SQLException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
 
    /**
     *
     * @param event
     */
    
 
    public ObservableList<user> getuser() throws SQLException {
        ObservableList<user> list = FXCollections.observableArrayList();
 
        String select = "SELECT * from user";
        con =  connexion.getCon();
        try {
            st = con.prepareStatement(select);
            rs = st.executeQuery();
            while (rs.next()) {
                user usr = new user();
                usr.setId(rs.getInt("id"));
                usr.setfullname(rs.getString("fullname"));
                usr.setmail(rs.getString("mail"));
                usr.setgender(rs.getString("gender"));
                list.add(usr);
            }
        } catch (SQLException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
        return list;
 
    }
 
    public void affiche() throws SQLException {
        ObservableList<user> list = getuser();
        colid.setCellValueFactory(new PropertyValueFactory<user, Integer>("id"));
        colfullname.setCellValueFactory(new PropertyValueFactory<user, String>("fullname"));
        colmail.setCellValueFactory(new PropertyValueFactory<user, String>("mail"));
        colgender.setCellValueFactory(new PropertyValueFactory<user, String>("gender"));
        table.setItems(list);
 
    }
 
    private void insert() throws SQLException {
        con =  connexion.getCon();
        String insert = "INSERT INTO user (fullname,mail,gender)VALUES(?,?,?)";
        try {
            st = con.prepareStatement(insert);
            
            st.setString(1, fullname.getText());
            st.setString(2, mail.getText());
            st.setString(3, gender.getSelectionModel().getSelectedItem());
            st.executeUpdate();
            affiche();
        } catch (SQLException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
    }
 
    public void delete() throws SQLException {
        con = connexion.getCon();
        String delete = "DELETE FROM user  where id = ?";
        try {
            st = con.prepareStatement(delete);
            st.setInt(1, Integer.parseInt(id.getText()));
            st.executeUpdate();
            affiche();
        } catch (SQLException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
    }
 
    private void update() throws SQLException {
        con = connexion.getCon();
        String update
                = "UPDATE user SET fullname =?,mail =?, gender=? where id =?";
        try {
            st = con.prepareStatement(update);
            st.setString(1, fullname.getText());
            st.setString(2, mail.getText());
            st.setString(3, gender.getSelectionModel().getSelectedItem());
            st.setInt(4, Integer.parseInt(id.getText()));
            st.executeUpdate();
            affiche();
        } catch (SQLException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
    }
 
    void clear() {
        id.setText(null);
        fullname.setText(null);
        mail.setText(null);
        gender.getSelectionModel().selectFirst();
        bsave.setDisable(false);
    }
 
    @FXML
    private void saveEvent(ActionEvent event) throws SQLException {
        insert();
        clear();
    }
 
    @FXML
    private void updateEvent(ActionEvent event) throws SQLException {
        update();
        clear();
        bsave.setDisable(false);
    }
 
    @FXML
    private void deleteEvent(ActionEvent event) throws SQLException {
        delete();
        clear();
    }
 
    @FXML
    private void clearEvent(ActionEvent event) {
        clear();
    }

    @FXML
    private void tablehandleButtonAction(MouseEvent event) {
        user usr = table.getSelectionModel().getSelectedItem();
        id.setText(String.valueOf(usr.getId()));
        fullname.setText(usr.getfullname());
        mail.setText(usr.getmail());
        gender.getSelectionModel().select(usr.getgender());
        bsave.setDisable(true);
    }

    
}