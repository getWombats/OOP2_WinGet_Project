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
            ListManagerDTO listManagerDTO = new ListManagerDTO();
            List<PackageListDTO> packageListDTOs = listManager.getLists()
                    .stream()
                    .map(DTOConverter::toPackageListDTO)
                    .collect(Collectors.toList());
            listManagerDTO.setList(packageListDTOs);
            LOGGER.log(Level.INFO, "Conversion ListManager to ListManagerDTO successful.");
            return listManagerDTO;
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "Conversion ListManager to ListManagerDTO failed: {0}", e.getMessage());
            return null;
        }
    }

    // Converts ListManagerDTO back to ListManager.
    public static ListManager fromListManagerDTO(ListManagerDTO listManagerDTO) {
        LOGGER.log(Level.INFO, "Conversion ListManagerDTO to ListManager starting.");
        try {
            ListManager listManager = ListManager.getInstance();
            listManager.setLists(FXCollections.observableArrayList(listManagerDTO.getList().stream()
                    .map(DTOConverter::fromPackageListDTO)
                    .collect(Collectors.toList())));
            LOGGER.log(Level.INFO, "Conversion ListManagerDTO to ListManager successful.");
            return listManager;
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "Conversion ListManagerDTO to ListManager failed: {0}", e.getMessage());
            return null;
        }
    }

    // Converts PackageList to PackageListDTO.
    public static PackageListDTO toPackageListDTO(PackageList packageList) {
        LOGGER.log(Level.INFO, "Conversion PackageList to PackageListDTO for {0} starting.", packageList != null ? packageList.getName() : "null");
        try {
            PackageListDTO packageListDTO = new PackageListDTO();
            packageListDTO.setId(packageList.getId());
            packageListDTO.setName(packageList.getName());
            packageListDTO.setSize(packageList.getSize());
            List<WinGetPackageDTO> winGetPackageDTOs = packageList.getFXPackages()
                    .stream()
                    .map(DTOConverter::toWinGetPackageDTO)
                    .collect(Collectors.toList());
            packageListDTO.setPackages(winGetPackageDTOs);
            LOGGER.log(Level.INFO, "Conversion PackageList to PackageListDTO for {0} successful.", packageList.getName());
            return packageListDTO;
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "Conversion PackageList to PackageListDTO failed: {0}", e.getMessage());
            return null;
        }
    }

    // Converts PackageListDTO back to PackageList.
    public static PackageList fromPackageListDTO(PackageListDTO packageListDTO) {
        LOGGER.log(Level.INFO, "Conversion PackageListDTO to PackageList for {0} starting.", packageListDTO != null ? packageListDTO.getName() : "null");
        try {
            PackageList packageList = new PackageList(packageListDTO.getName());
            packageList.setId(packageListDTO.getId());
            packageList.setName(packageListDTO.getName());
            packageList.setSize(packageListDTO.getSize());
            packageList.setPackages(packageListDTO.getPackages().stream()
                    .map(DTOConverter::fromWinGetPackageDTO)
                    .collect(Collectors.toCollection(FXCollections::observableArrayList)));
            LOGGER.log(Level.INFO, "Conversion PackageList to PackageListDTO for {0} successful.", packageListDTO.getName());
            return packageList;
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "Conversion PackageListDTO to PackageList failed: {0}", e.getMessage());
            return null;
        }

    }

    // Converts WinGetPackage to WinGetPackageDTO.
    public static WinGetPackageDTO toWinGetPackageDTO(WinGetPackage winGetPackage) {
        LOGGER.log(Level.INFO, "Conversion WinGetPackage to WinGetPackageDTO for {0} starting.", winGetPackage != null ? winGetPackage.getName() : "null");
        try {
            WinGetPackageDTO winGetPackageDTO = new WinGetPackageDTO();
            winGetPackageDTO.setName(winGetPackage.getName());
            winGetPackageDTO.setId(winGetPackage.getId());
            winGetPackageDTO.setVersion(winGetPackage.getVersion());
            winGetPackageDTO.setSource(winGetPackage.getSource());
            LOGGER.log(Level.INFO, "Conversion WinGetPackage to WinGetPackageDTO for {0} successful.", winGetPackage.getName());
            return winGetPackageDTO;
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "Conversion WinGetPackage to WinGetPackageDTO failed: {0}", e.getMessage());
            return null;
        }
    }

    // Converts WinGetPackageDTO back to WinGetPackage.
    public static WinGetPackage fromWinGetPackageDTO(WinGetPackageDTO winGetPackageDTO) {
        LOGGER.log(Level.INFO, "Conversion WinGetPackageDTO to WinGetPackage for {0} starting.", winGetPackageDTO != null ? winGetPackageDTO.getName() : "null");
        try {
            WinGetPackage winGetPackage = new WinGetPackage();
            winGetPackage.setName(winGetPackageDTO.getName());
            winGetPackage.setId(winGetPackageDTO.getId());
            winGetPackage.setVersion(winGetPackageDTO.getVersion());
            winGetPackage.setSource(winGetPackageDTO.getSource());
            LOGGER.log(Level.INFO, "Conversion WinGetPackageDTO to WinGetPackage for {0} successful.", winGetPackageDTO.getName());
            return winGetPackage;
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "Conversion WinGetPackageDTO to WinGetPackage failed: {0}", e.getMessage());
            return null;
        }
    }
}