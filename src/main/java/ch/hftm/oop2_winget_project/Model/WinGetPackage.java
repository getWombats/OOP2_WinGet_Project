package ch.hftm.oop2_winget_project.Model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;

public class WinGetPackage
{
    private SimpleStringProperty name = new SimpleStringProperty();
    private SimpleStringProperty id = new SimpleStringProperty();
    private SimpleStringProperty version = new SimpleStringProperty();
    private SimpleStringProperty source = new SimpleStringProperty();
    private boolean isInstalled;

    public WinGetPackage() {
    }

    public WinGetPackage(String name, String id, String version, String source) {
        this.setName(name);
        this.setId(id);
        this.setVersion(version);
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

    public String getName() {
        return this.name.get();
    }

    public SimpleStringProperty getFXName() {
        return this.name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getId() {
        return this.id.get();
    }

    public SimpleStringProperty getFXId() {
        return this.id;
    }

    public void setId(String id) {
        this.id.set(id);
    }

    public String getVersion() {
        return this.version.get();
    }

    public SimpleStringProperty getFXVersion() {
        return this.version;
    }

    public void setVersion(String version) {
        this.version.set(version);
    }

    public String getSource() {
        return this.source.get();
    }

    public SimpleStringProperty getFXSource() {
        return this.source;
    }

    public void setSource(String source) {
        this.source.set(source);
    }

    public boolean isInstalled() {
        return this.isInstalled;
    }

    public void setInstalled(boolean installed) {
        this.isInstalled = installed;
    }
}