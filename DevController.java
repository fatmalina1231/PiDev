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
public class DevController implements Initializable {
 
    PreparedStatement st = null;
    ResultSet rs = null;
    Connection con = null;
    private Label label;
    @FXML
    private TextField id;
    @FXML
    private TextField grpname;
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
    private TableView<dev> table;
    @FXML
    private TableColumn<dev, Integer> colid;
     @FXML
    private TableColumn<dev, String> colgrpname;
    @FXML
    private TableColumn<dev, String> colfullname;
    @FXML
    private TableColumn<dev, String> colmail;
     @FXML
    private TableColumn<dev, String> colgender;
    
 
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
    
 
    public ObservableList<dev> getdev() throws SQLException {
        ObservableList<dev> list = FXCollections.observableArrayList();
 
        String select = "SELECT * from dev";
        con =  connexion.getCon();
        try {
            st = con.prepareStatement(select);
            rs = st.executeQuery();
            while (rs.next()) {
                dev dev = new dev();
                dev.setId(rs.getInt("id"));
                dev.setgrpname(rs.getString("grpname"));
                dev.setfullname(rs.getString("fullname"));
                dev.setmail(rs.getString("mail"));
                dev.setgender(rs.getString("gender"));
                list.add(dev);
            }
        } catch (SQLException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
        return list;
 
    }
 
    public void affiche() throws SQLException {
        ObservableList<dev> list = getdev();
        colid.setCellValueFactory(new PropertyValueFactory<dev, Integer>("id"));
        colgrpname.setCellValueFactory(new PropertyValueFactory<dev, String>("grpname"));
        colfullname.setCellValueFactory(new PropertyValueFactory<dev, String>("fullname"));
        colmail.setCellValueFactory(new PropertyValueFactory<dev, String>("mail"));
        colgender.setCellValueFactory(new PropertyValueFactory<dev, String>("gender"));
        table.setItems(list);
 
    }
 
    private void insert() throws SQLException {
        con =  connexion.getCon();
        String insert = "INSERT INTO dev (grpname,fullname,mail,gender)VALUES(?,?,?,?)";
        try {
            st = con.prepareStatement(insert);
            
            st.setString(1, grpname.getText());
            st.setString(2, fullname.getText());
            st.setString(3, mail.getText());
            st.setString(4, gender.getSelectionModel().getSelectedItem());
            st.executeUpdate();
            affiche();
        } catch (SQLException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
    }
 
    public void delete() throws SQLException {
        con = connexion.getCon();
        String delete = "DELETE FROM dev  where id = ?";
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
                = "UPDATE dev SET grpname =?, fullname =?,mail =?, gender=? where id =?";
        try {
            st = con.prepareStatement(update);
            st.setString(1, grpname.getText());
            st.setString(2, fullname.getText());
            st.setString(3, mail.getText());
            st.setString(4, gender.getSelectionModel().getSelectedItem());
            st.setInt(5, Integer.parseInt(id.getText()));
            st.executeUpdate();
            affiche();
        } catch (SQLException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
    }
 
    void clear() {
        id.setText(null);
        grpname.setText(null);
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
        dev dev = table.getSelectionModel().getSelectedItem();
        id.setText(String.valueOf(dev.getId()));
        grpname.setText(dev.getgrpname());
        fullname.setText(dev.getfullname());
        mail.setText(dev.getmail());
        gender.getSelectionModel().select(dev.getgender());
        bsave.setDisable(true);
    }

    
}
