package ch.hftm.oop2_winget_project.Model;

public abstract class AbstractWinGetPackage {
    public abstract String getName();
    public abstract void setName(String name);
    public abstract String getId();
    public abstract void setId(String id);
    public abstract String getVersion();
    public abstract void setVersion(String version);
    public abstract String getSource();
    public abstract void setSource(String source);
}