module AppUser {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;
    requires java.sql;

    opens AppUser to javafx.fxml;
    opens Controller to javafx.fxml;
    opens Class to javafx.base;
    exports AppUser;
}
