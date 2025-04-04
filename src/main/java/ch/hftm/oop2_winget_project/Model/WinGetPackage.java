package ch.hftm.oop2_winget_project.Model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;

import java.io.Serializable;

public class WinGetPackage implements Serializable
{
    private static final long serialVersionUID = 1L; // UID resp. Version for serialization.
    private final SimpleStringProperty packageName = new SimpleStringProperty();
    private final SimpleStringProperty packageID = new SimpleStringProperty();
    private final SimpleStringProperty packageVersion = new SimpleStringProperty();
    private final SimpleStringProperty packageSource = new SimpleStringProperty();
//    private final BooleanProperty isFavorite = new SimpleBooleanProperty();
    private boolean isFavorite;
    private boolean isInstalled;

    public WinGetPackage() {
    }

    public WinGetPackage(String packageName, String packageID, String packageVersion, String packageSource)
    {
        this.setPackageName(packageName);
        this.setPackageID(packageID);
        this.setPackageVersion(packageVersion);
        this.setPackageSource(packageSource);
    }

    public String getPackageName()
    {
        return packageName.get();
    }

    public SimpleStringProperty packageNameProperty()
    {
        return packageName;
    }

    private void setPackageName(String packageName)
    {
        this.packageName.set(packageName);
    }

    public String getPackageID()
    {
        return packageID.get();
    }

    public SimpleStringProperty packageIDProperty() {
        return packageID;
    }

    private void setPackageID(String packageID)
    {
        this.packageID.set(packageID);
    }

    public String getPackageVersion()
    {
        return packageVersion.get();
    }

    public SimpleStringProperty packageVersionProperty()
    {
        return packageVersion;
    }

    private void setPackageVersion(String packageVersion)
    {
        this.packageVersion.set(packageVersion);
    }

    public String getPackageSource()
    {
        return packageSource.get();
    }

    public SimpleStringProperty packageSourceProperty()
    {
        return packageSource;
    }

    private void setPackageSource(String packageSource)
    {
        this.packageSource.set(packageSource);
    }

    public boolean isFavorite()
    {
        return isFavorite;
//        return isFavorite.get();
    }

//    public BooleanProperty isFavoriteProperty()
//    {
//        return isFavorite;
//    }

    public void setFavorite(boolean favorite)
    {
//        this.isFavorite.set(favorite);
        isFavorite = favorite;
    }

    public boolean isInstalled()
    {
        return isInstalled;
    }

    public void setInstalled(boolean installed)
    {
        isInstalled = installed;
    }
}
