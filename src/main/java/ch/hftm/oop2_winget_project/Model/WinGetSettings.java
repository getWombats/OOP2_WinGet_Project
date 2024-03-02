package ch.hftm.oop2_winget_project.Model;

import ch.hftm.oop2_winget_project.Util.SystemLanguage;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class WinGetSettings
{
    private String columnHeaderIdText;
    private String columnHeaderVersionText;
    private String columnHeaderAvailableText;
    private String columnHeaderMatchText;
    private String columnHeaderSourceText;
    private Map<String, String> columns = new HashMap<>();
    private String directoryPath = System.getenv("LOCALAPPDATA") + File.separator + "WinGetProject";
    private String logPath = directoryPath + File.separator + "log";
    private String serializePath = directoryPath + File.separator + "listManagerDTO.ser";

    public void setWinGetLanguage()
    {
        String preferredLanguage = SystemLanguage.getPreferredLanguage();

        switch(preferredLanguage)
        {
            case "de":
                columnHeaderIdText = "id";
                columnHeaderVersionText = "version";
                columnHeaderMatchText = "übereinstimmung";
                columnHeaderAvailableText = "verfügbar";
                columnHeaderSourceText = "quelle";
                break;
            case "en":
                columnHeaderIdText = "id";
                columnHeaderVersionText = "version";
                columnHeaderMatchText = "match";
                columnHeaderAvailableText = "available";
                columnHeaderSourceText = "source";
                break;
            case "fr":
                columnHeaderIdText = "id";
                columnHeaderVersionText = "version";
                columnHeaderMatchText = "concordance";
                columnHeaderAvailableText = "disponsible";
                columnHeaderSourceText = "source";
                break;
        }

        columns.put("columnId", columnHeaderIdText);
        columns.put("columnVersion", columnHeaderVersionText);
        columns.put("columnMatch", columnHeaderAvailableText);
        columns.put("columnAvailable", columnHeaderMatchText);
        columns.put("columnSource", columnHeaderSourceText);
    }

    public Map<String, String> getColumns()
    {
        return columns;
    }

    public String getDirectoryPath() {
        return this.directoryPath;
    }
    public String getLogPath() {
        return this.logPath;
    }
    public String getSerializePath() {
        return this.serializePath;

    }
}