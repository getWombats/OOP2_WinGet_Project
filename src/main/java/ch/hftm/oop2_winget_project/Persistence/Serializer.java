package ch.hftm.oop2_winget_project.Persistence;

import ch.hftm.oop2_winget_project.Model.ListManager;
import ch.hftm.oop2_winget_project.Model.ListManagerDTO;

import java.io.*;

public class Serializer {
    private static String filepath = "./UserData/listManagerDTO.ser";



    public static void serializeListManager() {

        // 1. Convert the ListManager to ListManagerDTO
        ListManager listManager = ListManager.getInstance();

        printModels("Current ListManager: ");
        ListManagerDTO listManagerDTO = DTOConverter.toListManagerDTO(listManager);
        printDTOs("Current ListManagerDTO: ");

        // Serialize the ListManager to .ser file
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filepath))) {
            oos.writeObject(listManagerDTO);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public static void deserializeListManager() {

        ListManagerDTO listManagerDTO = ListManagerDTO.getInstance();

        // 1. Deserialize the ListManagerDTO from .ser file.
//        ListManagerDTO listManagerDTO = ListManagerDTO.getInstance();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filepath))) {
            listManagerDTO = (ListManagerDTO) ois.readObject();
        } catch (ClassNotFoundException | FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // 2. Convert ListManagerDTO back to ListManager.
        printDTOs("Current ListManagerDTO: ");
        DTOConverter.fromListManagerDTO(listManagerDTO);
        printModels("Current ListManager: ");
    }

    private static void printModels(String str) {
        System.out.println("\n" + str);
        ListManager listManager = ListManager.getInstance();
        listManager.getLists().forEach(packageList -> {
            System.out.println("PackageList: " + packageList.getName() + ", Packages: " + packageList.getFXPackages().size());
            packageList.getFXPackages().forEach(winGetPackage -> System.out.println(" - WinGetPackage: " + winGetPackage.getName() + " " +  winGetPackage.getId() + " " + winGetPackage.getVersion() + " " + winGetPackage.getSource()));
        });
    }

    private static void printDTOs(String str) {
        System.out.println("\n" + str);
        ListManagerDTO listManagerDTO = ListManagerDTO.getInstance();
        listManagerDTO.getList().forEach(packageListDTO -> {
            System.out.println("PackageListDTO: " + packageListDTO.getName() + ", Packages: " + packageListDTO.getPackages().size());
            packageListDTO.getPackages().forEach(winGetPackageDTO -> System.out.println(" - WinGetPackageDTO: " + winGetPackageDTO.getName() + " " + winGetPackageDTO.getId() + " " + winGetPackageDTO.getVersion() + " " + winGetPackageDTO.getSource()));
        });
    }
}