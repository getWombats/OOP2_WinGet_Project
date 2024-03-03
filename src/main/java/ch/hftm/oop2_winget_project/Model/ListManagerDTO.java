package ch.hftm.oop2_winget_project.Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ListManagerDTO implements Serializable {

//    DTO = Data Transfer Object
//    This class is a simpler version of it's corresponding model class.
//    It is used to convert complex data types that are not serializable into simpler datatypes that are serializable.
//    JavaFX Observables are not seralizable.

//    Variables
    private static final long serialVersionUID = 1L;
    private static ListManagerDTO instance;
    private List<PackageListDTO> lists;

    // Instantiation
    public static ListManagerDTO getInstance() {
        if (instance == null) {
            synchronized (ListManagerDTO.class) { // Synchronized to prevent multiple threads checking, returning null and creating multiple instances.
                if (instance == null) {
                    instance = new ListManagerDTO();
                }
            }
        }
        return instance;
    }

    // Constructors
    public ListManagerDTO() {
        this.lists = new ArrayList<>();
    }

    // Methods
    public List<PackageListDTO> getList() {
        return this.lists;
    }
    public void setList(List<PackageListDTO> list) {
        this.lists = list;
    }
}