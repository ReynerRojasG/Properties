package AppUser;

import Class.ConnectionSingleTon;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Stack;

public class App extends Application {
    private static Scene scene;
    private static Stage primaryStage;
    private static Stack<Parent> sceneStack = new Stack<>();

    @Override
    public void start(Stage stage) throws IOException {
        primaryStage = stage;
        scene = new Scene(loadFXML("interface"), 1200, 800);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void setRoot(String fxml) throws IOException {
        Parent newRoot = loadFXML(fxml);
        sceneStack.push(scene.getRoot());
        scene.setRoot(newRoot);
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }
    
    public static void goBack() throws IOException {
        if (!sceneStack.isEmpty()) {
            scene.setRoot(sceneStack.pop()); 
        } else {
            System.out.println("No hay escenas anteriores.");
        }
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