module ch.hftm.oop2_winget_project {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.controlsfx.controls;
    requires java.logging;



    opens ch.hftm.oop2_winget_project to javafx.fxml;
    exports ch.hftm.oop2_winget_project;
    exports ch.hftm.oop2_winget_project.Controller;
    opens ch.hftm.oop2_winget_project.Controller to javafx.fxml;
}