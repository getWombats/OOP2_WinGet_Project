package ch.hftm.oop2_winget_project.Model;

import javafx.beans.binding.Bindings;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PackageList {

// Variables
    private String id;
    private StringProperty name;
    private IntegerProperty size;
    private ListProperty<WinGetPackage> packages;


//    Constructors
    public PackageList(String packageListName){
        this.id = UUID.randomUUID().toString(); // Create random UUID to identify List.
        this.name = new SimpleStringProperty(packageListName);
        this.size = new SimpleIntegerProperty(0);
        this.packages = new SimpleListProperty<>(FXCollections.observableArrayList());
        // Bind the size property to the size of the packages observable list.
        this.size.bind(Bindings.size(this.packages).asObject());

        System.out.println("New Package: (UUID Name)\n" + getId() + " \"" + getName() + "\"");
    }

    // Constructor to match DTO fields
    public PackageList(String id, String name, List<WinGetPackage> packages) {
        this.id = id;
        this.name = new SimpleStringProperty(name);
        this.packages = new SimpleListProperty<>(FXCollections.observableArrayList(packages));
    }


//    Methods
//    Manage PackageList Properties

    public String getId() {
        return this.id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public String getName(){
        return this.name.get();
    }

    public StringProperty getNameProperty() {
        return this.name;
    }
    public void setName(String packageListName) {
        this.name.set(packageListName);
    }

    public int getSize() {
        return this.size.get();
    }

    public IntegerProperty getSizeProperty() {
        return this.size;
    }

    public ObservableList<WinGetPackage> getPackages() {
        return this.packages.get();
    } // Returns PackageList

    public void setPackages(ObservableList<WinGetPackage> packages) {
        this.packages = new SimpleListProperty<>(FXCollections.observableArrayList(packages));
    }

//    public void setLists(ObservableList<PackageList> lists) {
//        this.lists = lists;
//    }


    public ListProperty<WinGetPackage> getPackagesProperty() {
        return this.packages;
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