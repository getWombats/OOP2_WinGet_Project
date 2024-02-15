package ch.hftm.oop2_winget_project.Persistence;

import ch.hftm.oop2_winget_project.Model.ListManager;
import ch.hftm.oop2_winget_project.Model.PackageList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Serializer {
    private static final String filepath = "listManager.ser";

    public static void serializeListmanager(ListManager listManager) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filepath))) {
            // Convert ObservableList to ArrayList for serialization
            List<PackageList> serializableList = new ArrayList<>(listManager.getLists());
            out.writeObject(serializableList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ListManager deserializeListManager() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filepath))) {
            // Deserialize to ArrayList and then convert to ObservableList
            List<PackageList> list = (List<PackageList>) in.readObject();
            ObservableList<PackageList> observableList = FXCollections.observableArrayList(list);
            ListManager.getInstance().setLists(observableList);
            return ListManager.getInstance();
        } catch (FileNotFoundException e) {
            return ListManager.getInstance();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}
