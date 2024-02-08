package ch.hftm.oop2_winget_project.Model;

import ch.hftm.oop2_winget_project.App;
import ch.hftm.oop2_winget_project.Util.ListManager;
import ch.hftm.oop2_winget_project.Util.QueryType;
import ch.hftm.oop2_winget_project.Util.SourceType;
import ch.hftm.oop2_winget_project.Util.ConsoleExitCode;
import ch.hftm.oop2_winget_project.Util.UniCodeChecker;
import javafx.collections.ObservableList;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WinGetQuery
{
    private final String columnHeaderIdText = App.getAppInstance().getWinGetSettings().getColumns().get("columnId");
    private final String columnHeaderVersionText = App.getAppInstance().getWinGetSettings().getColumns().get("columnVersion");
    private final String columnHeaderAvailableText = App.getAppInstance().getWinGetSettings().getColumns().get("columnAvailable");
    private final String columnHeaderMatchText = App.getAppInstance().getWinGetSettings().getColumns().get("columnMatch");
    private final String columnHeaderSourceText = App.getAppInstance().getWinGetSettings().getColumns().get("columnSource");
    private final Pattern VALIDLINE_REGEX = Pattern.compile("(.*[0-9a-zA-Z]+.*)");
    private long consoleExitCode;

    public void queryToList(QueryType queryType, String keyWord, ObservableList<WinGetPackage> packageList) throws IOException, InterruptedException
    {
        List<String> rawDataList = new ArrayList<>();

        ProcessBuilder processBuilder = new ProcessBuilder("winget", queryType.toString(), keyWord);
        processBuilder.redirectErrorStream(true);
        Process process = processBuilder.start();

        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

        String readerLine;

        int headerCounter = 0;
        int columnSeparatorIndexId = -1;
        int columnSeparatorIndexVersion = -1;
        int columnSeparatorIndexAvailableOrMatch = -1;
        int columnSeparatorIndexSource = -1;
        int maxPackageLineLength = -1;

        while ((readerLine = reader.readLine()) != null)
        {
            rawDataList.add(readerLine);
        }
        reader.close();

        consoleExitCode = process.waitFor();

        if(consoleExitCode != ConsoleExitCode.NO_PACKAGE_FOUND.getValue())
        {
            for(String line : rawDataList)
            {
                // Get index when each column begins as separator if line is table header, only executes once
                if(isHeaderLine(line))
                {
                    columnSeparatorIndexId = line.toLowerCase().indexOf(columnHeaderIdText);
                    columnSeparatorIndexVersion = line.toLowerCase().indexOf(columnHeaderVersionText);

                    if(line.toLowerCase().contains(columnHeaderMatchText))
                    {
                        columnSeparatorIndexAvailableOrMatch = line.toLowerCase().indexOf(columnHeaderMatchText);
                    }
                    else if (line.toLowerCase().contains(columnHeaderAvailableText))
                    {
                        columnSeparatorIndexAvailableOrMatch = line.toLowerCase().indexOf(columnHeaderAvailableText);
                    }

                    columnSeparatorIndexSource = line.toLowerCase().indexOf(columnHeaderSourceText);
                    maxPackageLineLength = columnSeparatorIndexSource + SourceType.MSSTORE.toString().length();

                    headerCounter++;
                }

                // Get actual package information
                if(isPackageLine(line))
                {
                    // Fix some weird asia packages
                    if(UniCodeChecker.containsHanScript(line))
                    {
                        int missingLength = maxPackageLineLength - line.length() - 1;

                        StringBuilder uglyPatch = new StringBuilder();
                        uglyPatch.append(" ".repeat(Math.max(0, missingLength)));

                        line = line.substring(0, columnSeparatorIndexId-missingLength) + uglyPatch + line.substring(columnSeparatorIndexId-missingLength);
                    }

                    String packageVersion;
                    if(columnSeparatorIndexAvailableOrMatch > -1)
                    {
                        packageVersion = line.substring(columnSeparatorIndexVersion, columnSeparatorIndexAvailableOrMatch).trim();
                    }
                    else
                    {
                        packageVersion = line.substring(columnSeparatorIndexVersion, columnSeparatorIndexSource).trim();
                    }

                    WinGetPackage winGetPackage = new WinGetPackage(
                            line.substring(0, columnSeparatorIndexId).trim(), // Package Name
                            line.substring(columnSeparatorIndexId, columnSeparatorIndexVersion).trim(), // Package ID
                            packageVersion,
                            line.substring(columnSeparatorIndexSource).trim() // Package Source
                    );

                    // Compare search results with installed packages to set relating packages as installed
                    if(queryType == QueryType.SEARCH)
                    {
                        for(WinGetPackage installedPackage : ListManager.getInstalledPackageList())
                        {
                            if(installedPackage.getPackageID().equals(winGetPackage.getPackageID()))
                            {
                                winGetPackage.setInstalled(true);
                            }
                        }
                    }

                    // Lock the list object for safe list populating when working on other threads
                    synchronized(packageList)
                    {
                        packageList.add(winGetPackage);
                    }
                }
            }
        }
    }

    private boolean isHeaderLine(String line)
    {
        Matcher matcher = VALIDLINE_REGEX.matcher(line);
        return line.toLowerCase().contains(columnHeaderIdText) && line.toLowerCase().contains(columnHeaderVersionText) && line.toLowerCase().contains(columnHeaderSourceText) && matcher.find();
    }

    private boolean isPackageLine(String line)
    {
        Matcher matcher = VALIDLINE_REGEX.matcher(line);
//        return !line.isBlank() && !line.toLowerCase().contains(columnHeaderIdText) && !line.trim().equals("-") && !line.trim().contains("---") && !line.trim().equals("\\") && !line.trim().contains("▒") && !line.trim().contains("█");
        return !line.toLowerCase().contains(columnHeaderIdText) && !line.toLowerCase().contains(columnHeaderVersionText) && !line.toLowerCase().contains(columnHeaderSourceText) && matcher.find() && !line.contains("▒") && !line.contains("█");
    }

    public long getConsoleExitCode()
    {
        return consoleExitCode;
    }
}
