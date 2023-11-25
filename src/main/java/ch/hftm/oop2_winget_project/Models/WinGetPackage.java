package ch.hftm.oop2_winget_project.Models;

import javafx.beans.property.SimpleStringProperty;

public class WinGetPackage
{
    private SimpleStringProperty packageName = new SimpleStringProperty();
    private SimpleStringProperty packageID = new SimpleStringProperty();
    private SimpleStringProperty packageVersion = new SimpleStringProperty();
    private SimpleStringProperty packageSource = new SimpleStringProperty();

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

    public SimpleStringProperty packageIDProperty()
    {
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
}
