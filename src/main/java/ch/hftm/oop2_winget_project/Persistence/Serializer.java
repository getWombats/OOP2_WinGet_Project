package ch.hftm.oop2_winget_project.Persistence;

import ch.hftm.oop2_winget_project.Model.ListManager;
import ch.hftm.oop2_winget_project.Model.ListManagerDTO;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Serializer {
//  This class serializes (saves) and deserializes (loads) the listManager instance.
//  Due to the hierarchical structure ListManager > PackageList > WinGetPackage, saving and loading the ListManager automatically
//  does so for all PackageLists and their WinGetPackages.
//  Because those model classes contain JavaFX attributes, they need to get converted into DTO models without Java FX specific elements.
//  The DTOConverter class handles the conversion between model and DTO instances.

    private static final Logger LOGGER = Logger.getLogger(DTOConverter.class.getName());
    private static String filepath = "./UserData/listManagerDTO.ser";

    // Serializes (saves) the listManager.
    public static void serializeListManager() {
        ListManager listManager = ListManager.getInstance();

        // printModels("Current ListManager: "); // For debugging.
        ListManagerDTO listManagerDTO = DTOConverter.toListManagerDTO(listManager); // Convert ListManager to ListManagerDTO.
        // printDTOs("Current ListManagerDTO: "); // For debugging.

        // Serialize the listManager to .ser file.
        LOGGER.log(Level.INFO, "Serialization of ListManager starting.");
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filepath))) {
            oos.writeObject(listManagerDTO);
            LOGGER.log(Level.INFO, "Serialization of ListManager successful.");
        } catch (IOException e) {
            LOGGER.log(Level.WARNING, "Serialization of ListManager failed: {0}", e.getMessage());
        }
    }

    // Deserializes (loads) the listManager.
    public static void deserializeListManager() {
        ListManagerDTO listManagerDTO = ListManagerDTO.getInstance();

        // Deserialize the ListManagerDTO from .ser file.
        LOGGER.log(Level.INFO, "Deserialization of ListManagerDTO starting.");
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filepath))) {
            listManagerDTO = (ListManagerDTO) ois.readObject();
            LOGGER.log(Level.INFO, "Deserialization of ListManagerDTO successful.");
        } catch (ClassNotFoundException | FileNotFoundException e) {
            LOGGER.log(Level.WARNING, "Deserialization of ListManagerDTO failed: {0}", e.getMessage());
        } catch (IOException e) {
            LOGGER.log(Level.WARNING, "Deserialization of ListManagerDTO failed: {0}", e.getMessage());
        }
        // printDTOs("Current ListManagerDTO: "); // For debugging.
        DTOConverter.fromListManagerDTO(listManagerDTO); // Convert ListManagerDTO to ListManager.
        // printModels("Current ListManager: "); // For debugging.
    }

    // Prints out the model instances. For debugging.
    private static void printModels(String str) {
        System.out.println("\n" + str);
        ListManager listManager = ListManager.getInstance();
        listManager.getLists().forEach(packageList -> {
            System.out.println("PackageList: " + packageList.getName() + ", Packages: " + packageList.getFXPackages().size());
            packageList.getFXPackages().forEach(winGetPackage -> System.out.println(" - WinGetPackage: " + winGetPackage.getName() + " " +  winGetPackage.getId() + " " + winGetPackage.getVersion() + " " + winGetPackage.getSource()));
        });
    }

    // Prints out the DTO instances. For debugging.
    private static void printDTOs(String str) {
        System.out.println("\n" + str);
        ListManagerDTO listManagerDTO = ListManagerDTO.getInstance();
        listManagerDTO.getList().forEach(packageListDTO -> {
            System.out.println("PackageListDTO: " + packageListDTO.getName() + ", Packages: " + packageListDTO.getPackages().size());
            packageListDTO.getPackages().forEach(winGetPackageDTO -> System.out.println(" - WinGetPackageDTO: " + winGetPackageDTO.getName() + " " + winGetPackageDTO.getId() + " " + winGetPackageDTO.getVersion() + " " + winGetPackageDTO.getSource()));
        });
    }
}