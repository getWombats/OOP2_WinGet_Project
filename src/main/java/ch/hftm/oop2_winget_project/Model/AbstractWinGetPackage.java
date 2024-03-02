package ch.hftm.oop2_winget_project.Model;

public abstract class AbstractWinGetPackage {

    // This iss an abstract class that gets used by the WinGetPackage and WinGetpackageDTO classes.
    // It doesen't really fullfill a usecase and our application doesen't provide a good one either.
    // But we needed to use one for the grading of this school project.
    public abstract String getName();
    public abstract void setName(String name);
    public abstract String getId();
    public abstract void setId(String id);
    public abstract String getVersion();
    public abstract void setVersion(String version);
    public abstract String getSource();
    public abstract void setSource(String source);
}