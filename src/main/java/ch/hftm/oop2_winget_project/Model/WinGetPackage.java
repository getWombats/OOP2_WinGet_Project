package ch.hftm.oop2_winget_project.Model;

import ch.hftm.oop2_winget_project.Api.AbstractWinGetPackage;
import javafx.beans.property.SimpleStringProperty;

public class WinGetPackage extends AbstractWinGetPackage {


    // Variables
    private SimpleStringProperty name = new SimpleStringProperty();
    private SimpleStringProperty id = new SimpleStringProperty();
    private SimpleStringProperty version = new SimpleStringProperty();
    private SimpleStringProperty updateVersion = new SimpleStringProperty();
    private SimpleStringProperty source = new SimpleStringProperty();
    private boolean isInstalled;


    //Constructors
    public WinGetPackage() {
    }

    public WinGetPackage(String name, String id, String version, String source) {
        this.setName(name);
        this.setId(id);
        this.setVersion(version);
        this.setSource(source);
    }

    public WinGetPackage(String name, String id, String version, String updateVersion, String source) {
        this.setName(name);
        this.setId(id);
        this.setVersion(version);
        this.setUpdateVersion(updateVersion);
        this.setSource(source);
    }

    // Adjusted Constructor to match DTO fields
    public WinGetPackage(String name, String id, String version, String source, boolean isInstalled) {
        this.name = new SimpleStringProperty(name);
        this.id = new SimpleStringProperty(id);
        this.version = new SimpleStringProperty(version);
        this.source = new SimpleStringProperty(source);
        this.isInstalled = isInstalled;
    }


    // Getters, Setters
    public String getName() {
        return this.name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public SimpleStringProperty getFXName() {
        return this.name;
    }

    public void setFXName(SimpleStringProperty name) {
        this.name = name;
    }


    public String getId() {
        return this.id.get();
    }

    public void setId(String id) {
        this.id.set(id);
    }

    public SimpleStringProperty getFXId() {
        return this.id;
    }

    public void setFXID(SimpleStringProperty id) {
        this.id = id;
    }


    public String getVersion() {
        return this.version.get();
    }

    public void setVersion(String version) {
        this.version.set(version);
    }

    public SimpleStringProperty getFXVersion() {
        return this.version;
    }

    public void setFXVersion(SimpleStringProperty version) {
        this.version = version;
    }


    public String getUpdateVersion() {
        return this.updateVersion.get();
    }

    public void setUpdateVersion(String updateVersion) {
        this.updateVersion.set(updateVersion);
    }

    public SimpleStringProperty getFXUpdateVersion() {
        return this.updateVersion;
    }

    public void setFXUpdateVersion(SimpleStringProperty updateVersion) {
        this.updateVersion = updateVersion;
    }


    public String getSource() {
        return this.source.get();
    }

    public void setSource(String source) {
        this.source.set(source);
    }

    public SimpleStringProperty getFXSource() {
        return this.source;
    }

    public void setFXSource(SimpleStringProperty source) {
        this.source = source;
    }


    public boolean isInstalled() {
        return this.isInstalled;
    }

    public void setInstalled(boolean installed) {
        this.isInstalled = installed;
    }
}