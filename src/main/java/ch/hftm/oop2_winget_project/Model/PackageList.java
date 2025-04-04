package ch.hftm.oop2_winget_project.Model;

import ch.hftm.oop2_winget_project.Model.WinGetPackage;
import javafx.beans.binding.Bindings;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

public class PackageList implements Serializable {

// Variables
private static final long serialVersionUID = 1L; // UID resp. Version for serialization.
    private String packageListId;
    private StringProperty packageListName;
    private IntegerProperty packageListSize;
    private ListProperty<WinGetPackage> packages;


//    Constructors
    public PackageList(String packageListName){
        this.packageListId = UUID.randomUUID().toString(); // Create random UUID to identify List.
        this.packageListName = new SimpleStringProperty(packageListName);
        this.packageListSize = new SimpleIntegerProperty(0);
        this.packages = new SimpleListProperty<>(FXCollections.observableArrayList());
        // Bind the size property to the size of the packages observable list.
        this.packageListSize.bind(Bindings.size(this.packages).asObject());

        System.out.println("New Package: (UUID Name)\n" + getId() + " \"" + getName() + "\"");
    }


//    Methods
//    Manage PackageList Properties

    public String getId() {
        return packageListId;
    }
    public String getName(){
        return packageListName.get();
    }
    public StringProperty getNameProperty() {
        return packageListName;
    }
    public void setName(String packageListName) {
        this.packageListName.set(packageListName);
    }
    public int getSize() {
        return packageListSize.get();
    }
    public IntegerProperty getSizeProperty() {
        return packageListSize;
    }
    public ObservableList<WinGetPackage> getPackages() {
        return packages.get();
    } // Returns PackageList
    public ListProperty<WinGetPackage> getPackagesProperty() {
        return packages;
    } // Returns PackageList as Property


//    Manage packages
    public void addPackage(WinGetPackage wgPkg) {
        packages.add(wgPkg);
    }
    public void removePackage(WinGetPackage wgPkg) {
        packages.remove(wgPkg);
    }
    public List<WinGetPackage> getPackageList() {
        return packages;
    }




    //    Predefined lists as static fields
    private static final ObservableList<WinGetPackage> searchPackageList = FXCollections.observableArrayList();
    private static final ObservableList<WinGetPackage> installedPackageList = FXCollections.observableArrayList();
    private static final ObservableList<WinGetPackage> favoritePackageList = FXCollections.observableArrayList();

    public static ObservableList<WinGetPackage> getSearchPackageList() {
        return searchPackageList;
    }
    public static ObservableList<WinGetPackage> getInstalledPackageList() {
        return installedPackageList;
    }
    public static ObservableList<WinGetPackage> getFavoritePackageList() {
        return favoritePackageList;
    }
}