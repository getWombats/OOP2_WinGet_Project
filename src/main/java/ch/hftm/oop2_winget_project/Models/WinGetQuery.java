package ch.hftm.oop2_winget_project.Models;

import ch.hftm.oop2_winget_project.App;
import ch.hftm.oop2_winget_project.Features.QueryType;
import ch.hftm.oop2_winget_project.Features.SourceType;
import ch.hftm.oop2_winget_project.Utils.ConsoleExitCode;
import javafx.collections.ObservableList;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WinGetQuery
{
    private final WinGetSettings winGetSettings = App.winGetSettings;
    private final String columnHeaderIdText = winGetSettings.getColumns().get("columnId");
    private final String columnHeaderVersionText = winGetSettings.getColumns().get("columnVersion");
    private final String columnHeaderAvailableText = winGetSettings.getColumns().get("columnAvailable");
    private final String columnHeaderMatchText = winGetSettings.getColumns().get("columnMatch");
    private final String columnHeaderSourceText = winGetSettings.getColumns().get("columnSource");
    private int headerCounter;
    private long consoleExitCode;
    private final Pattern VALIDLINE_REGEX = Pattern.compile("[-▒█\\\\|]");

    public void QueryToList(QueryType queryType, String keyWord, ObservableList<WinGetPackage> packageList)
    {
        try
        {
            List<String> rawDataList = new ArrayList<>();

            ProcessBuilder processBuilder = new ProcessBuilder("winget", queryType.toString(), keyWord);
            processBuilder.redirectErrorStream(true);
            Process process = processBuilder.start();

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

            String readerLine;

            headerCounter = 0;
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
                        if(containsHanScript(line))
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

                        // Lock the list object
                        synchronized(packageList)
                        {
                            packageList.add(winGetPackage);
                        }
                    }
                }
            }
        }
        catch (IOException | InterruptedException ex)
        {
            // Exceptionhandler
        }
        catch (IndexOutOfBoundsException ex)
        {
            ex.printStackTrace();
        }
    }

    // Unicode Han = Asia
    private boolean containsHanScript(String line)
    {
        return line.codePoints().anyMatch(
                codepoint ->
                        Character.UnicodeScript.of(codepoint) == Character.UnicodeScript.HAN);
    }

    private boolean isHeaderLine(String line)
    {
        Matcher matcher = VALIDLINE_REGEX.matcher(line);
        return headerCounter == 0 && !line.isBlank() && line.toLowerCase().contains(columnHeaderIdText) && !matcher.find();
    }

    private boolean isPackageLine(String line)
    {
        Matcher matcher = VALIDLINE_REGEX.matcher(line);
        return !line.isBlank() && !line.toLowerCase().contains(columnHeaderIdText) && !matcher.find();
    }

    public long getConsoleExitCode()
    {
        return consoleExitCode;
    }
}
