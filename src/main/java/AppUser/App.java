package AppUser;

import Class.ConnectionSingleTon;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.sql.Connection;
import java.io.IOException;

public class App extends Application {
    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("interface"), 1200, 800);
        stage.setScene(scene);
        stage.show();
    }

    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        
        ConnectionSingleTon connectionInstance = ConnectionSingleTon.getInstance();
        
        if (connectionInstance.conn != null) {
            System.out.println("Conexion a MySQL exitosa!");
        } else {
            System.out.println("Fallo la conexion a MySQL.");
        }
        launch();
    }

}