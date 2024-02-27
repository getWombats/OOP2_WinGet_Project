package ch.hftm.oop2_winget_project.Persistence;
import ch.hftm.oop2_winget_project.Model.ListManager;
import ch.hftm.oop2_winget_project.Model.ListManagerDTO;
import ch.hftm.oop2_winget_project.Model.PackageList;
import ch.hftm.oop2_winget_project.Model.PackageListDTO;
import ch.hftm.oop2_winget_project.Model.WinGetPackage;
import ch.hftm.oop2_winget_project.Model.WinGetPackageDTO;

import javafx.collections.FXCollections;

import java.util.List;
import java.util.stream.Collectors;

public class DTOConverter {

    // Converts ListManager to ListManagerDTO
    public static ListManagerDTO toListManagerDTO(ListManager listManager) {
        System.out.println("\nDTOConverter: ListManager to ListManagerDTO...");
        ListManagerDTO dto = new ListManagerDTO();
        List<PackageListDTO> packageListDTOs = listManager.getLists()
                .stream()
                .map(DTOConverter::toPackageListDTO)
                .collect(Collectors.toList());
        dto.setList(packageListDTOs);
        System.out.println("DTOConverter: ListManager to ListManagerDTO complete with " + packageListDTOs.size() + " PackageListDTOs.\n");
        return dto;
    }

    // Converts ListManagerDTO back to ListManager
    public static ListManager fromListManagerDTO(ListManagerDTO dto) {
        System.out.println("DTOConverter: ListManagerDTO back to ListManager...");
        ListManager listManager = ListManager.getInstance();
        listManager.setLists(FXCollections.observableArrayList(dto.getList().stream()
                .map(DTOConverter::fromPackageListDTO)
                .collect(Collectors.toList())));
        System.out.println("DTOConverter: ListManagerDTO back to ListManager completed with " + dto.getList().size() + " PackageLists.");
        return listManager;
    }

    // Converts PackageList to PackageListDTO
    public static PackageListDTO toPackageListDTO(PackageList packageList) {
        System.out.println("DTOConverter: PackageList to PackageListDTO for: " + packageList.getName() + "...");
        PackageListDTO dto = new PackageListDTO();
        dto.setId(packageList.getId());
        dto.setName(packageList.getName());
        dto.setSize(packageList.getSize());
        List<WinGetPackageDTO> winGetPackageDTOs = packageList.getFXPackages()
                .stream()
                .map(DTOConverter::toWinGetPackageDTO)
                .collect(Collectors.toList());
        dto.setPackages(winGetPackageDTOs);
        System.out.println("DTOConverter: PackageList to PackageListDTO completed for '" + packageList.getName() + "' with " + winGetPackageDTOs.size() + " WinGetPackageDTOs.");
        return dto;
    }

    // Converts PackageListDTO back to PackageList
    public static PackageList fromPackageListDTO(PackageListDTO dto) {
        System.out.println("DTOConverter: PackageListDTO back to PackageList for: " + dto.getName() + "...");
        PackageList packageList = new PackageList(dto.getName());
        packageList.setId(dto.getId());
//        packageList.setName(dto.getName());
        packageList.setSize(dto.getSize());
        packageList.setPackages(dto.getPackages().stream()
                .map(DTOConverter::fromWinGetPackageDTO)
                .collect(Collectors.toCollection(FXCollections::observableArrayList)));
        System.out.println("DTOConverter: PackageListDTO '" + dto.getName() + "' back to PackageList completed with " + dto.getPackages().size() + " WinGetPackages.");
        return packageList;
    }

    // Converts WinGetPackage to WinGetPackageDTO
    public static WinGetPackageDTO toWinGetPackageDTO(WinGetPackage winGetPackage) {
        System.out.println("DTOConverter: WinGetPackage to WinGetPackageDTO for: " + winGetPackage.getName() + "...");
        WinGetPackageDTO dto = new WinGetPackageDTO();
        dto.setName(winGetPackage.getName());
        dto.setId(winGetPackage.getId());
        dto.setVersion(winGetPackage.getVersion());
        dto.setSource(winGetPackage.getSource());
        System.out.println("DTOConverter: WinGetPackage '" + winGetPackage.getName() + "' to WinGetPackageDTO completed.");
        return dto;
    }

    // Converts WinGetPackageDTO back to WinGetPackage
    public static WinGetPackage fromWinGetPackageDTO(WinGetPackageDTO dto) {
        System.out.println("DTOConverter: WinGetPackageDTO back to WinGetPackage for: " + dto.getName() + "...");
        WinGetPackage winGetPackage = new WinGetPackage();
        winGetPackage.setName(dto.getName());
        winGetPackage.setId(dto.getId());
        winGetPackage.setVersion(dto.getVersion());
        winGetPackage.setSource(dto.getSource());
        System.out.println("DTOConverter: WinGetPackageDTO '" + dto.getName() + "' back to WinGetPackage completed.");
        return winGetPackage;
    }
}