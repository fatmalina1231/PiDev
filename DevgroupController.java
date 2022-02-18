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
public class DevgroupController implements Initializable {
 
    PreparedStatement st = null;
    ResultSet rs = null;
    Connection con = null;
    private Label label;
    
    @FXML
    private TextField grpname;
    
    @FXML
    private Button bsave;
    @FXML
    private Button bupdate;
    @FXML
    private Button bdelete;
    @FXML
    private TableView<devgroup> table;
    
     @FXML
    private TableColumn<devgroup, String> colgrpname;
    
    
 
    
   
        
 
    
   
    public ObservableList<devgroup> getdevgroup() throws SQLException {
        ObservableList<devgroup> list = FXCollections.observableArrayList();
 
        String select = "SELECT * from devgroup";
        con =  connexion.getCon();
        try {
            st = con.prepareStatement(select);
            rs = st.executeQuery();
            while (rs.next()) {
                devgroup devgroup = new devgroup();
                
                devgroup.setgrpname(rs.getString("grpname"));
                
                list.add(devgroup);
            }
        } catch (SQLException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
        return list;
 
    }
 
    public void affiche() throws SQLException {
        ObservableList<devgroup> list = getdevgroup();
     
        colgrpname.setCellValueFactory(new PropertyValueFactory<devgroup, String>("grpname"));
        
        table.setItems(list);
 
    }
 
    private void insert() throws SQLException {
        con =  connexion.getCon();
        String insert = "INSERT INTO devgroup (groupname)VALUES(?)";
        try {
            st = con.prepareStatement(insert);
            
            st.setString(1, grpname.getText());
           
            st.executeUpdate();
            affiche();
        } catch (SQLException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
    }
 
    public void delete() throws SQLException {
        con = connexion.getCon();
        String delete = "DELETE FROM devgroup  where groupname = ?";
        try {
            st = con.prepareStatement(delete);
            st.setInt(1, Integer.parseInt(grpname.getText()));
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
                = "UPDATE devgroup SET groupname =?";
        try {
            st = con.prepareStatement(update);
            st.setString(1, grpname.getText());
            
            st.executeUpdate();
            affiche();
        } catch (SQLException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
    }
 
    void clear() {
        
        grpname.setText(null);
        
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
        devgroup devgroup = table.getSelectionModel().getSelectedItem();
        
        grpname.setText(devgroup.getgrpname());
        
        bsave.setDisable(true);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
}
