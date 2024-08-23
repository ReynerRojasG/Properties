package Controller;

import AppUser.App;
import Class.Client;
import Class.MappingSQL;
import Class.PropertyClient;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author Reyner
 */
public class FilterWindowController implements Initializable {

    @FXML
    private Button back_btn;
    @FXML
    private ChoiceBox<String> filterType_cb;
    @FXML
    private ChoiceBox<String> filterStatus_cb;
    @FXML
    private ChoiceBox<String> filterPrice_cb;
    @FXML
    private Button filterProperty_btn;
    @FXML
    private TableView<PropertyClient> propertyDataFilter_tv;
    @FXML
    private TableColumn<PropertyClient, String> typeColumn;
    @FXML
    private TableColumn<PropertyClient, String> statusColumn;
    @FXML
    private TableColumn<PropertyClient, String> sizeColumn;
    @FXML
    private TableColumn<PropertyClient, String> directionColumn;
    @FXML
    private TableColumn<PropertyClient, Integer> valueColumn;

    private MappingSQL mappingSQL;
    private ObservableList<Client> clientData;
    private ObservableList<PropertyClient> propertyData;
    private String[] propertyFilterType = {"Casa", "Apartamento", "Terreno"};
    private String[] propertyFilterStatus = {"Disponible", "Ocupado", "Vendido", "Retirado"};
    private String[] propertyFilterPrice = {"<1000000", "2000000-6000000", "6000000-10000000", ">10000000"};
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        mappingSQL = new MappingSQL();
        
        propertyData = FXCollections.observableArrayList();
        
        filterType_cb.getItems().addAll(propertyFilterType);
        filterStatus_cb.getItems().addAll(propertyFilterStatus);
        filterPrice_cb.getItems().addAll(propertyFilterPrice);
        
        
        initializeColumns();
    }
    
    private void initializeColumns() {
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        directionColumn.setCellValueFactory(new PropertyValueFactory<>("direction"));
        sizeColumn.setCellValueFactory(new PropertyValueFactory<>("size"));
        valueColumn.setCellValueFactory(new PropertyValueFactory<>("value"));
    }

    @FXML
    private void goBack(ActionEvent event) throws IOException {
        App.goBack();
    }

    @FXML
    private void makeFilter(ActionEvent event) {
        String selectedType = filterType_cb.getValue();
        String selectedStatus = filterStatus_cb.getValue();
        String selectedPriceRange = filterPrice_cb.getValue(); 

        propertyData.clear();

        if (selectedType != null) {
            propertyData.addAll(mappingSQL.filterByType(selectedType));
        }

        if (selectedStatus != null) {
            propertyData.addAll(mappingSQL.filterByStatus(selectedStatus));
        }

        if (selectedPriceRange != null) {
            propertyData.addAll(mappingSQL.filterByPrice(selectedPriceRange));
        }

        propertyDataFilter_tv.setItems(propertyData);

        filterType_cb.setValue(null);
        filterStatus_cb.setValue(null);
        filterPrice_cb.setValue(null);
    }
}
