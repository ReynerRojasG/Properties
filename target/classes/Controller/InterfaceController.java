/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Reyner
 */
public class InterfaceController implements Initializable {

    @FXML
    private TextField name_tf;
    @FXML
    private TextField direction_tf;
    @FXML
    private TextField number_tf;
    @FXML
    private TableView<?> personalData_tv;
    @FXML
    private TableColumn<?, ?> idColumn;
    @FXML
    private TableColumn<?, ?> nameColumn;
    @FXML
    private TableColumn<?, ?> directionColumn;
    @FXML
    private Button add_btn;
    @FXML
    private Button addNumber_btn;
    @FXML
    private ChoiceBox<?> telephone_cb;
    @FXML
    private TableView<?> numberData_tv;
    @FXML
    private TableColumn<?, ?> typeColumn;
    @FXML
    private TableColumn<?, ?> numberColum;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void addPrincipalData(ActionEvent event) {
    }

    @FXML
    private void addPhoneNumber(ActionEvent event) {
    }
    
}
