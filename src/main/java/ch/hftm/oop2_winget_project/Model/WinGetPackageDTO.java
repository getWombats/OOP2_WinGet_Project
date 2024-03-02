package ch.hftm.oop2_winget_project.Model;

import java.io.Serializable;

public class WinGetPackageDTO implements Serializable {

//    DTO = Data Transfer Object
//    This class is a simpler version of it's corresponding model class.
//    It is used to convert complex data types that are not serializable into simpler datatypes that are serializable.
//    JavaFX Observables are not seralizable.

    // Variables
    private static final long serialVersionUID = 1L;
    private String packageName;
    private String packageID;
    private String packageVersion;
    private String packageSource;

    // Constructors
    public WinGetPackageDTO() {
    }

    // Getters, Setters
    public String getName() {
        return packageName;
    }
    public void setName(String packageName) {
        this.packageName = packageName;
    }

    public String getId() {
        return packageID;
    }
    public void setId(String packageID) {
        this.packageID = packageID;
    }

    public String getVersion() {
        return packageVersion;
    }
    public void setVersion(String packageVersion) {
        this.packageVersion = packageVersion;
    }

    public String getSource() {
        return packageSource;
    }
    public void setSource(String packageSource) {
        this.packageSource = packageSource;
    }
}