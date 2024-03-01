package ch.hftm.oop2_winget_project.Persistence;

import ch.hftm.oop2_winget_project.Model.ListManager;
import ch.hftm.oop2_winget_project.Model.ListManagerDTO;

import java.io.*;


public class Serializer {
//  This class serializes (saves) and deserializes (loads) the listManager instance.
//  Due to the hierarchical structure ListManager > PackageList > WinGetPackage, saving and loading the ListManager automatically
//  does so for all PackageLists and their WinGetPackages.
//  Because those model classes contain JavaFX attributes, they need to get converted into DTO models without Java FX specific elements.
//  The DTOConverter class handles the conversion between model and DTO instances.
    private static String filepath = "./UserData/listManagerDTO.ser";



    // Serializes (saves) the listManager.
    public static void serializeListManager() {
        ListManager listManager = ListManager.getInstance();

        printModels("Current ListManager: ");
        ListManagerDTO listManagerDTO = DTOConverter.toListManagerDTO(listManager); // Convert ListManager to ListManagerDTO.
        printDTOs("Current ListManagerDTO: ");

        // Serialize the listManager to .ser file.
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filepath))) {
            oos.writeObject(listManagerDTO);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Deserializes (loads) the listManager.
    public static void deserializeListManager() {
        ListManagerDTO listManagerDTO = ListManagerDTO.getInstance();

        // Deserialize the ListManagerDTO from .ser file.
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filepath))) {
            listManagerDTO = (ListManagerDTO) ois.readObject();
        } catch (ClassNotFoundException | FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        printDTOs("Current ListManagerDTO: ");
        DTOConverter.fromListManagerDTO(listManagerDTO); // Convert ListManagerDTO to ListManager.
        printModels("Current ListManager: ");
    }

    // Prints out the model instances.
    private static void printModels(String str) {
        System.out.println("\n" + str);
        ListManager listManager = ListManager.getInstance();
        listManager.getLists().forEach(packageList -> {
            System.out.println("PackageList: " + packageList.getName() + ", Packages: " + packageList.getFXPackages().size());
            packageList.getFXPackages().forEach(winGetPackage -> System.out.println(" - WinGetPackage: " + winGetPackage.getName() + " " +  winGetPackage.getId() + " " + winGetPackage.getVersion() + " " + winGetPackage.getSource()));
        });
    }

    // Prints out the DTO instances.
    private static void printDTOs(String str) {
        System.out.println("\n" + str);
        ListManagerDTO listManagerDTO = ListManagerDTO.getInstance();
        listManagerDTO.getList().forEach(packageListDTO -> {
            System.out.println("PackageListDTO: " + packageListDTO.getName() + ", Packages: " + packageListDTO.getPackages().size());
            packageListDTO.getPackages().forEach(winGetPackageDTO -> System.out.println(" - WinGetPackageDTO: " + winGetPackageDTO.getName() + " " + winGetPackageDTO.getId() + " " + winGetPackageDTO.getVersion() + " " + winGetPackageDTO.getSource()));
        });
    }
}