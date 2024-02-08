package ch.hftm.oop2_winget_project.Util;

import ch.hftm.oop2_winget_project.Model.WinGetPackage;
import java.util.ArrayList;
import java.util.List;

public class PackageList {

// Variables
    private String packageListName;
    private ArrayList<WinGetPackage> packages;

//    Constructors
    public PackageList(String packageListName){
        this.packageListName = packageListName;
    }


//    Methods
    public String getName(){
        return packageListName;
    }
}