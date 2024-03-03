package Model;

import ch.hftm.oop2_winget_project.Model.ListManagerDTO;
import ch.hftm.oop2_winget_project.Model.PackageListDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ListManagerDTOTest {

    private ListManagerDTO listManagerDTO;

    @BeforeEach
    void setUp() {
        // Reset the singleton for each test to ensure a clean state
        listManagerDTO = ListManagerDTO.getInstance();
        listManagerDTO.setList(new ArrayList<>()); // Ensure it's empty before each test.
    }

    @Test
    void testSingletonInstance() {
        ListManagerDTO anotherInstance = ListManagerDTO.getInstance();
        assertNotNull(listManagerDTO, "ListManagerDTO instance should not be null");
        assertSame(listManagerDTO, anotherInstance, "Instances should be the same (singleton pattern)");
    }

    @Test
    void testSetAndGetList() {
        List<PackageListDTO> newList = new ArrayList<>();
        PackageListDTO packageListDTO = new PackageListDTO();
        packageListDTO.setId("1");
        packageListDTO.setName("TestList");
        packageListDTO.setSize(5);
        newList.add(packageListDTO);

        listManagerDTO.setList(newList);
        assertEquals(newList, listManagerDTO.getList(), "The set and get lists should be the same");
        assertEquals(1, listManagerDTO.getList().size(), "The list size should be 1 after adding one list");
    }

    @Test
    void testListContents() {
        PackageListDTO packageListDTO = new PackageListDTO();
        packageListDTO.setId("2");
        packageListDTO.setName("AnotherTestList");
        packageListDTO.setSize(10);

        listManagerDTO.setList(new ArrayList<>(List.of(packageListDTO)));
        List<PackageListDTO> retrievedList = listManagerDTO.getList();

        assertNotNull(retrievedList, "Retrieved list should not be null");
        assertEquals(1, retrievedList.size(), "Retrieved list should contain one element");
        assertEquals(packageListDTO, retrievedList.get(0), "The element should match the added PackageListDTO");
    }

    @Test
    void testListModification() {
        PackageListDTO packageListDTO1 = new PackageListDTO();
        packageListDTO1.setId("1");
        packageListDTO1.setName("FirstList");
        packageListDTO1.setSize(3);

        PackageListDTO packageListDTO2 = new PackageListDTO();
        packageListDTO2.setId("2");
        packageListDTO2.setName("SecondList");
        packageListDTO2.setSize(7);

        List<PackageListDTO> initialList = new ArrayList<>(List.of(packageListDTO1, packageListDTO2));
        listManagerDTO.setList(initialList);

        // Modify the list after setting it
        List<PackageListDTO> newList = listManagerDTO.getList();
        newList.remove(0); // Remove the first element
        listManagerDTO.setList(newList); // Update the list in DTO

        assertEquals(1, listManagerDTO.getList().size(), "The list size should be reduced to 1 after removal");
        assertEquals(packageListDTO2, listManagerDTO.getList().get(0), "The remaining list item should be 'SecondList'");
    }
}