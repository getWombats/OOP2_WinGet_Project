package Model;

import ch.hftm.oop2_winget_project.Model.ListManager;
import javafx.collections.FXCollections;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ListManagerTest {

    private ListManager listManager;

    @BeforeEach
    void setUp() {
        // Reset ListManager for each test.
        listManager = ListManager.getInstance();
        listManager.setLists(FXCollections.observableArrayList());
    }

    @Test
    void singletonTest() {
        ListManager anotherInstance = ListManager.getInstance();
        assertSame(listManager, anotherInstance, "ListManager should be a singleton.");
    }
}