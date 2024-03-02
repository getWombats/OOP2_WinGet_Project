package Model;

import ch.hftm.oop2_winget_project.Model.ListManagerDTO;
import ch.hftm.oop2_winget_project.Model.PackageListDTO;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ListManagerDTOTest {

    @Test
    void testSingletonInstance() {
        ListManagerDTO instance1 = ListManagerDTO.getInstance();
        assertNotNull(instance1, "Singleton instance should not be null.");

        ListManagerDTO instance2 = ListManagerDTO.getInstance();
        assertNotNull(instance2, "Singleton instance should still not be null.");

        assertSame(instance1, instance2, "Singleton instances should be the same.");
    }

    @Test
    void testListProperty() {
        ListManagerDTO instance = ListManagerDTO.getInstance();
        assertNotNull(instance.getList(), "Initially, the list should not be null but empty.");

        List<PackageListDTO> newList = new ArrayList<>();
        PackageListDTO packageListDTO = new PackageListDTO();
        newList.add(packageListDTO);

        instance.setList(newList);
        assertNotNull(instance.getList(), "List should not be null after setting a new list.");
        assertEquals(1, instance.getList().size(), "List should contain one element after setting.");
        assertEquals(packageListDTO, instance.getList().get(0), "The element in the list should be the one that was set.");
    }

    @Test
    void testListModification() {
        ListManagerDTO instance = ListManagerDTO.getInstance();
        instance.setList(new ArrayList<>()); // Reset list to empty

        PackageListDTO newPackageListDTO = new PackageListDTO();
        instance.getList().add(newPackageListDTO); // Modify the list directly

        assertEquals(1, instance.getList().size(), "List should contain one element after modification.");
        assertEquals(newPackageListDTO, instance.getList().get(0), "The list should contain the newly added PackageListDTO.");
    }
}