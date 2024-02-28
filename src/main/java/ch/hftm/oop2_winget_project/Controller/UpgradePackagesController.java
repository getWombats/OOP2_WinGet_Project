package ch.hftm.oop2_winget_project.Controller;

import ch.hftm.oop2_winget_project.Api.IControllerBase;
import ch.hftm.oop2_winget_project.Model.WinGetPackage;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class UpgradePackagesController implements IControllerBase, Initializable
{
    @FXML
    public TableView upgradePackagesTableView;
    @FXML
    public TableColumn nameColumn;
    @FXML
    public TableColumn idColumn;
    @FXML
    public TableColumn versionColumn;
    @FXML
    public TableColumn sourceColumn;
    @FXML
    private Label tableViewPlaceholderLabel;
    @FXML
    private VBox placeholderContent;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        placeholderContent = new VBox(10);
        tableViewPlaceholderLabel = new Label();
        setTableViewPlaceholder("List all available updates for installed packages", false);
    }

    @FXML
    private void getAvailableUpdates() {

    }

    @Override
    public void setTableViewData() {

    }

    @Override
    public void setTableViewSource() {

    }

    @Override
    public void refreshTableViewContent() {

    }

    @Override
    public WinGetPackage getObjectFromSelection() {
        return null;
    }

    @Override
    public void addButtonToTableView() {

    }

    private void setTableViewPlaceholder(String labelText, boolean showProgressIndicator){
        tableViewPlaceholderLabel.setText(labelText);
        ProgressIndicator progressIndicator = new ProgressIndicator();
        progressIndicator.setVisible(showProgressIndicator);

        placeholderContent = new VBox(10);
        placeholderContent.getChildren().addAll(progressIndicator, tableViewPlaceholderLabel);
        placeholderContent.setAlignment(Pos.CENTER);

        upgradePackagesTableView.widthProperty().addListener((obs, oldVal, newVal) -> {
            placeholderContent.setPrefWidth(newVal.doubleValue());
        });
        upgradePackagesTableView.heightProperty().addListener((obs, oldVal, newVal) -> {
            placeholderContent.setPrefHeight(newVal.doubleValue());
        });

        upgradePackagesTableView.setPlaceholder(placeholderContent);
    }
}
