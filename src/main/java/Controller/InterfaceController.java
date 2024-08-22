package Controller;

import Class.Client;
import Class.MappingSQL;
import Class.PropertyClient;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author Reyner
 */
public class InterfaceController implements Initializable {

    @FXML
    private TextField name_tf;
    @FXML
    private Button add_btn;
    @FXML
    private TableColumn<Client, String> nameColumn;
    @FXML
    private TableColumn<Client, String> cedulaColumn;
    @FXML
    private TableColumn<Client, String> idColumn; 
    @FXML
    private TextField cedula_tf;
    @FXML
    private TextField direction_tf;
    @FXML
    private ChoiceBox<String> type_cb;
    @FXML
    private ChoiceBox<String> status_cb;
    @FXML
    private TextField size_tf;
    @FXML
    private Button addProperty_btn;
    @FXML
    private Button deleteProperty_btn;
    @FXML
    private TableView<Client> clientData_tv;
    @FXML
    private Button deleteClient_btn;
    @FXML
    private TableView<PropertyClient> propertyData_tv;
    @FXML
    private TableColumn<PropertyClient, String> typeColumn;
    @FXML
    private TableColumn<PropertyClient, String> statusColumn;
    @FXML
    private TableColumn<PropertyClient, String> directionColumn;
    @FXML
    private TableColumn<PropertyClient, String> sizeColumn;
    private MappingSQL mappingSQL;
    private String[] propertyType = {"Casa", "Apartamento", "Terreno"};
    private String[] propertyStatus = {"Disponible", "Ocupado", "Vendido", "Retirado"};
    Client selectedClient;
    PropertyClient selectedProperty;
    @FXML
    private TextField newName_tf;
    @FXML
    private Button updateClient_btn;
    @FXML
    private TextField newCedula_tf;
    @FXML
    private ChoiceBox<String> newType_cb;
    @FXML
    private ChoiceBox<String> newStatus_cb;
    @FXML
    private TextField newDirection_tf;
    @FXML
    private TextField newSize_tf;
    @FXML
    private Button updateProperty_btn;
    @FXML
    private TextField value_tf;
    @FXML
    private TextField newValue_tf;
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initializePropertyColumn();
        initializeUserColumn();
        
        mappingSQL = new MappingSQL();
        reloadClientTableView();
        initializeChoiceBox();
        initializeUserSelectionListener();
        initializePropertySelectionListener();
        
    }          
    
    private void initializePropertyColumn(){
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        directionColumn.setCellValueFactory(new PropertyValueFactory<>("direction"));
        sizeColumn.setCellValueFactory(new PropertyValueFactory<>("size"));
    }
    
    private void initializeUserColumn(){
        idColumn.setCellValueFactory(new PropertyValueFactory<>("idClient"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        cedulaColumn.setCellValueFactory(new PropertyValueFactory<>("cedula"));
    }
    
    private void initializeChoiceBox(){
        type_cb.getItems().addAll(propertyType);
        newType_cb.getItems().addAll(propertyType);
        status_cb.getItems().addAll(propertyStatus);
        newStatus_cb.getItems().addAll(propertyStatus);
    } 
    
    private void initializeUserSelectionListener(){
        clientData_tv.getSelectionModel().selectedItemProperty().addListener((obs, oldUser, newUser) -> {
            selectedClient = newUser;
            if (selectedClient != null) {
                reloadPropertyTableView(selectedClient.getIdClient());
            } else {
                propertyData_tv.getItems().clear();
            }
        });
    }
    
    private void initializePropertySelectionListener() {
    propertyData_tv.getSelectionModel().selectedItemProperty().addListener((obs, oldProperty, newProperty) -> {
        selectedProperty = newProperty;
    });
}

    
    private void reloadClientTableView() {
        clientData_tv.getItems().clear();
        clientData_tv.getItems().addAll(mappingSQL.loadClientFromDB());
    }
    
    private void reloadPropertyTableView(int clientId) {
        propertyData_tv.getItems().clear();
        propertyData_tv.getItems().addAll(mappingSQL.loadPropertyFromDB(clientId));
    }
    
    @FXML
    private void addClient(ActionEvent event) {
        String nameText = name_tf.getText();
        String cedulaText = cedula_tf.getText();
        
        if(!nameText.isEmpty() && !cedulaText.isEmpty()){
            Client info = new Client(0, cedulaText, nameText);
            int id = mappingSQL.insertClientToDB(info);
            
            if (id != -1) {
                info.setIdClient(id);
                clientData_tv.getItems().add(info);
                name_tf.clear();
                cedula_tf.clear();
            } else {
                System.out.println("Error al insertar datos en la base de datos");
            }
        } else {
            Platform.runLater(() -> {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setHeaderText("Error al agregar");
                alert.setContentText("Alguno de los campos están vacíos.");
                alert.showAndWait();
            });
        }
    }


    @FXML
    private void deleteClient(ActionEvent event) {
        Client selectedClient = clientData_tv.getSelectionModel().getSelectedItem();
        if(selectedClient != null){
            int clientId = selectedClient.getIdClient();
            boolean deletedClient = mappingSQL.deleteClientFromDB(clientId);
                if(deletedClient){
                reloadClientTableView();
                propertyData_tv.getItems().clear();
                }
        }else{
            Platform.runLater(() -> {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setHeaderText("Error al eliminar");
                alert.setContentText("No se pudo eliminar el usuario seleccionado.");
                alert.showAndWait();
                });
        }
        
    }

    @FXML
    private void addProperty(ActionEvent event) {
        String type = type_cb.getValue();
        String status = status_cb.getValue();
        String cedula = cedula_tf.getText();
        String direction = direction_tf.getText();
        String size = size_tf.getText();
        // selectedClient == null || type == null || status == null || cedula.isEmpty() || direction.isEmpty() || size.isEmpty())
        if (selectedClient == null || type == null || status == null) {
            Platform.runLater(() -> {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setHeaderText("Error al agregar");
                alert.setContentText("Por favor seleccione un cliente, y llene todos los campos.");
                alert.showAndWait();
            });
            return;
        }

        PropertyClient property = new PropertyClient(type, status, direction, size, selectedClient.getIdClient(), -1);
        
        boolean inserted = mappingSQL.insertPropertyToDB(property);

        if (inserted) {
            reloadPropertyTableView(selectedClient.getIdClient());
            direction_tf.clear();
            size_tf.clear();
            type_cb.setValue(null);
            status_cb.setValue(null);
        } else {
            Platform.runLater(() -> {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setHeaderText("Error al agregar");
                alert.setContentText("Error al la propiedad en la base de datos.");
                alert.showAndWait();
            });
        }   
    }

    @FXML
    private void deleteProperty(ActionEvent event) {
        PropertyClient selectedProperty = propertyData_tv.getSelectionModel().getSelectedItem();
        
            if(selectedProperty != null){
                boolean eliminated = mappingSQL.deletePropertyFromDB(selectedProperty.getPropertyId());
                if(eliminated){
                    reloadPropertyTableView(selectedClient.getIdClient());
                }else{
                    System.out.println("Error al eliminar");
                }
            }else{
                Platform.runLater(() -> {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setHeaderText("Error al eliminar");
                alert.setContentText("No se selecciono ningun telefono para eliminar.");
                alert.showAndWait();
                });
            }
    }

    @FXML
    private void updateClient(ActionEvent event) {
        if(selectedClient != null){
            String nameUpdated = newName_tf.getText();
            String cedulaUpdated = newCedula_tf.getText();

            if(!nameUpdated.isEmpty() && !cedulaUpdated.isEmpty()){
                selectedClient.setName(nameUpdated);
                selectedClient.setCedula(cedulaUpdated);

                boolean updated = mappingSQL.updateClientFromDB(selectedClient);
                if(updated){
                    reloadClientTableView();
                    newName_tf.clear();
                    newCedula_tf.clear();

                } else {
                    Platform.runLater(() -> {
                        Alert alert = new Alert(AlertType.ERROR);
                        alert.setHeaderText("Error al actualizar");
                        alert.setContentText("No se pudo actualizar el cliente.");
                        alert.showAndWait();
                    });
                }
            }else {
                Platform.runLater(() -> {
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setHeaderText("Error al actualizar");
                    alert.setContentText("Los campos de nombre y cedula no pueden estar vacios.");
                    alert.showAndWait();
                });
             }
        }
    }

    @FXML
    private void updateProperty(ActionEvent event) {
         if(selectedProperty != null){
            String type = newType_cb.getValue();
            String status = newStatus_cb.getValue();
            String direction = newDirection_tf.getText();
            String size = newSize_tf.getText();

            if(!type.isEmpty() && !status.isEmpty() && !direction.isEmpty() && !size.isEmpty()){
           
                selectedProperty.setType(type);
                selectedProperty.setStatus(status);
                selectedProperty.setDirection(direction);
                selectedProperty.setSize(size);
                
                boolean updated = mappingSQL.updatePropertyFromDB(selectedProperty);
                
                if(updated){
                    reloadPropertyTableView(selectedClient.getIdClient());
                    newDirection_tf.clear();
                    newSize_tf.clear();
                    newType_cb.setValue(null);
                    newStatus_cb.setValue(null);
                } else {
                    Platform.runLater(() -> {
                        Alert alert = new Alert(AlertType.ERROR);
                        alert.setHeaderText("Error al actualizar");
                        alert.setContentText("No se pudo actualizar la propiedad.");
                        alert.showAndWait();
                    });
                }
            }else {
                Platform.runLater(() -> {
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setHeaderText("Error al actualizar");
                    alert.setContentText("Los campos no pueden estar vacios.");
                    alert.showAndWait();
                });
             }
        }
    }
    
}
