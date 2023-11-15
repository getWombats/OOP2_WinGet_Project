module ch.hftm.oop2_winget_project {
    requires javafx.controls;
    requires javafx.fxml;


    opens ch.hftm.oop2_winget_project to javafx.fxml;
    exports ch.hftm.oop2_winget_project;
    exports ch.hftm.oop2_winget_project.Controller;
    opens ch.hftm.oop2_winget_project.Controller to javafx.fxml;
}