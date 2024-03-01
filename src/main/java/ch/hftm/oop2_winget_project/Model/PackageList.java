package ch.hftm.oop2_winget_project.Model;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PackageList {

// Variables
    private String id;
    private SimpleStringProperty name;
    private SimpleIntegerProperty size;
    private ObservableList<WinGetPackage> packages;


//    Constructors
//    public PackageList() {
//        this.packages = FXCollections.observableArrayList();
//    }

    public PackageList(String name){
        this.id = UUID.randomUUID().toString(); // Create random UUID to identify List.
        this.name = new SimpleStringProperty(name);
        this.size = new SimpleIntegerProperty(0);
        this.packages = FXCollections.observableArrayList();

        System.out.println("New Package: (UUID Name)\n" + getId() + " \"" + getName() + "\"");
    }

    // Constructor to match DTO fields
//    public PackageList() {
//        this.id = "";
//        this.name = new SimpleStringProperty();
//        this.size = new SimpleIntegerProperty(0);
//        this.packages = FXCollections.observableArrayList();
//    }

    // Constructor used for creating favourite list with special uuid and name.
    public PackageList(String name, String id){
        this.id = id;
        this.name = new SimpleStringProperty(name);
        this.size = new SimpleIntegerProperty(0);
        this.packages = FXCollections.observableArrayList();
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

    public SimpleStringProperty getFXName() {
        return this.name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public int getSize() {
        return this.size.get();
    }

    public SimpleIntegerProperty getFXSize() {
        return this.size;
    }

    public void setSize(int id) {
        this.size.set(id);
    }

    public List<WinGetPackage> getPackages() {
        return new ArrayList<>(packages);
    }
    public ObservableList<WinGetPackage> getFXPackages() {
        return this.packages;
    }

    public void setPackages(ObservableList<WinGetPackage> packages) {
        this.packages = packages;
    }


//    Manage packages
    public void addPackage(WinGetPackage wgPkg) {
        this.packages.add(wgPkg);
        this.size.set(this.size.get() +1);
    }
    public void removePackage(WinGetPackage wgPkg) {
        packages.remove(wgPkg);
        this.size.set(this.size.get() -1);
    }

    //    Predefined lists as static fields
    private static final ObservableList<WinGetPackage> searchPackageList = FXCollections.observableArrayList();
    private static final ObservableList<WinGetPackage> installedPackageList = FXCollections.observableArrayList();
    private static final ObservableList<WinGetPackage> favoritePackageList = FXCollections.observableArrayList();
    private static final ObservableList<WinGetPackage> upgradePackageList = FXCollections.observableArrayList();

    public static ObservableList<WinGetPackage> getSearchPackageList() {
        return searchPackageList;
    }
    public static ObservableList<WinGetPackage> getInstalledPackageList() {
        return installedPackageList;
    }
    public static ObservableList<WinGetPackage> getFavoritePackageList() {
        return favoritePackageList;
    }
    public static ObservableList<WinGetPackage> getUpgradePackageList() {
        return upgradePackageList;
    }
}