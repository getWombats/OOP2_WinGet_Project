package ch.hftm.oop2_winget_project.Persistence;

import ch.hftm.oop2_winget_project.Model.ListManager;
import ch.hftm.oop2_winget_project.Model.ListManagerDTO;
import ch.hftm.oop2_winget_project.Model.PackageList;
import ch.hftm.oop2_winget_project.Model.PackageListDTO;
import ch.hftm.oop2_winget_project.Model.WinGetPackage;
import ch.hftm.oop2_winget_project.Model.WinGetPackageDTO;

import javafx.collections.FXCollections;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class DTOConverter {
//  DTO: Data Transfer Object (An object used to transfer data from one system to another).
//  This class converts the instances of ListManager > PackageList > WinGetPackage to ListManagerDTO > PackageListDTO > WinGetPackageDTO and back.
//  This conversion was implemented because the model classes contain JavaFX elements, which can not be serialized.
//  Converting to an DTO model replaces the JavaFX elements with regular attributes and vice-versa if a DTO model gets converted back.

    private static final Logger LOGGER = Logger.getLogger(DTOConverter.class.getName());

    // Converts ListManager to ListManagerDTO.
    public static ListManagerDTO toListManagerDTO(ListManager listManager) {
        LOGGER.log(Level.INFO, "Conversion ListManager to ListManagerDTO starting.");
        try {
//            System.out.println("\nDTOConverter: ListManager to ListManagerDTO...");
            ListManagerDTO dto = new ListManagerDTO();
            List<PackageListDTO> packageListDTOs = listManager.getLists()
                    .stream()
                    .map(DTOConverter::toPackageListDTO)
                    .collect(Collectors.toList());
            dto.setList(packageListDTOs);
            LOGGER.log(Level.INFO, "Conversion ListManager to ListManagerDTO successful.");
//            System.out.println("DTOConverter: ListManager to ListManagerDTO complete with " + packageListDTOs.size() + " PackageListDTOs.\n");
            return dto;
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "Conversion ListManager to ListManagerDTO failed: {1}", e.getMessage());
            return null;
        }
    }

    // Converts ListManagerDTO back to ListManager.
    public static ListManager fromListManagerDTO(ListManagerDTO dto) {
        // System.out.println("DTOConverter: ListManagerDTO back to ListManager...");
        LOGGER.log(Level.INFO, "Conversion ListManagerDTO to ListManager starting.");
        try {
            ListManager listManager = ListManager.getInstance();
            listManager.setLists(FXCollections.observableArrayList(dto.getList().stream()
                    .map(DTOConverter::fromPackageListDTO)
                    .collect(Collectors.toList())));
            // System.out.println("DTOConverter: ListManagerDTO back to ListManager completed with " + dto.getList().size() + " PackageLists.");
            LOGGER.log(Level.INFO, "Conversion ListManagerDTO to ListManager successful.");
            return listManager;
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "Conversion ListManagerDTO to ListManager failed: {1}", e.getMessage());
            return null;
        }
    }

    // Converts PackageList to PackageListDTO.
    public static PackageListDTO toPackageListDTO(PackageList packageList) {
        try {
//            LOGGER.log(Logger.Level.INFO, "Conversion PackageList to PackageListDTO for '{0}' [{1}] starting...", new Object[]{packageList.getName(), packageList.getId()});

            LOGGER.log(Level.INFO, "Converting PackageList to PackageListDTO for: '{0}'", packageList.getName());
//            System.out.println("DTOConverter: PackageList to PackageListDTO for: " + packageList.getName() + "...");
            PackageListDTO dto = new PackageListDTO();
            dto.setId(packageList.getId());
            dto.setName(packageList.getName());
            dto.setSize(packageList.getSize());
            List<WinGetPackageDTO> winGetPackageDTOs = packageList.getFXPackages()
                    .stream()
                    .map(DTOConverter::toWinGetPackageDTO)
                    .collect(Collectors.toList());
            dto.setPackages(winGetPackageDTOs);
//            System.out.println("DTOConverter: PackageList to PackageListDTO completed for '" + packageList.getName() + "' with " + winGetPackageDTOs.size() + " WinGetPackageDTOs.");
            LOGGER.log(Level.INFO, "Conversion PackageList to PackageListDTO for '{0}' successful.", packageList.getName());
            return dto;
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "Conversion PackageList to PackageListDTO failed: {1}", e.getMessage());
            return null;
        }
    }

    // Converts PackageListDTO back to PackageList.
    public static PackageList fromPackageListDTO(PackageListDTO dto) {
        try {
            LOGGER.log(Level.INFO, "Converting PackageListDTO to PackageList for: '{0}'", dto.getName());
//            System.out.println("DTOConverter: PackageListDTO back to PackageList for: " + dto.getName() + "...");
            PackageList packageList = new PackageList(dto.getName());
            packageList.setId(dto.getId());
            packageList.setName(dto.getName());
            packageList.setSize(dto.getSize());
            packageList.setPackages(dto.getPackages().stream()
                    .map(DTOConverter::fromWinGetPackageDTO)
                    .collect(Collectors.toCollection(FXCollections::observableArrayList)));
//            System.out.println("DTOConverter: PackageListDTO '" + dto.getName() + "' back to PackageList completed with " + dto.getPackages().size() + " WinGetPackages.");
            LOGGER.log(Level.INFO, "Conversion PackageList to PackageListDTO for '{0}' successful.", dto.getName());
            return packageList;
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "Conversion PackageListDTO to PackageList failed: {1}", e.getMessage());
            return null;
        }

    }

    // Converts WinGetPackage to WinGetPackageDTO.
    public static WinGetPackageDTO toWinGetPackageDTO(WinGetPackage winGetPackage) {
        try {
            LOGGER.log(Level.INFO, "Converting WinGetPackage to WinGetPackageDTO for: '{0}'", winGetPackage.getName());
//            System.out.println("DTOConverter: WinGetPackage to WinGetPackageDTO for: " + winGetPackage.getName() + "...");
            WinGetPackageDTO dto = new WinGetPackageDTO();
            dto.setName(winGetPackage.getName());
            dto.setId(winGetPackage.getId());
            dto.setVersion(winGetPackage.getVersion());
            dto.setSource(winGetPackage.getSource());
//            System.out.println("DTOConverter: WinGetPackage '" + winGetPackage.getName() + "' to WinGetPackageDTO completed.");
            LOGGER.log(Level.INFO, "Conversion WinGetPackage to WinGetPackageDTO for '{0}' successful.", winGetPackage.getName());
            return dto;
        } catch (Exception e) {
//            LOGGER.log(Logger.Level.ERROR, "Failed to convert WinGetPackage '{0}': {1}", new Object[]{winGetPackage != null ? winGetPackage.getName() : "null", e.getMessage()});
            LOGGER.log(Level.WARNING, "Conversion WinGetPackage to WinGetPackageDTO failed: {1}", e.getMessage());
            return null;
        }
    }

    // Converts WinGetPackageDTO back to WinGetPackage.
    public static WinGetPackage fromWinGetPackageDTO(WinGetPackageDTO dto) {
        try {
            LOGGER.log(Level.INFO, "Converting WinGetPackageDTO to WinGetPackage for: '{0}'", dto.getName());
            // System.out.println("DTOConverter: WinGetPackageDTO back to WinGetPackage for: " + dto.getName() + "...");
            WinGetPackage winGetPackage = new WinGetPackage();
            winGetPackage.setName(dto.getName());
            winGetPackage.setId(dto.getId());
            winGetPackage.setVersion(dto.getVersion());
            winGetPackage.setSource(dto.getSource());
            // System.out.println("DTOConverter: WinGetPackageDTO '" + dto.getName() + "' back to WinGetPackage completed.");
            LOGGER.log(Level.INFO, "Conversion WinGetPackageDTO to WinGetPackage for '{0}' successful.", dto.getName());
            return winGetPackage;
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "Conversion WinGetPackageDTO to WinGetPackage failed: {1}", e.getMessage());
            return null;
        }
    }
}