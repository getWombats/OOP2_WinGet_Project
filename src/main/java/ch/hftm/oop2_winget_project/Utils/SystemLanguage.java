package ch.hftm.oop2_winget_project.Utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class SystemLanguage {

    private static long consoleExitCode;

    public static String getPreferedLanguage() {
        try
        {
            List<String> rawDataList = new ArrayList<>();

            String prefferedLanguage = "";

            ProcessBuilder processBuilderLanguage = new ProcessBuilder("cmd", "/C", "REG QUERY \"HKEY_Current_User\\Control Panel\\International\\User Profile\" /v Languages /t REG_MULTI_SZ /se _");
            processBuilderLanguage.redirectErrorStream(true);
            Process process_getPrefLang = processBuilderLanguage.start();

            if(!process_getPrefLang.isAlive())
            {
                consoleExitCode = 66;
            }


            BufferedReader reader = new BufferedReader(new InputStreamReader(process_getPrefLang.getInputStream()));

            String readerLine;

            while ((readerLine = reader.readLine()) != null) {
                rawDataList.add(readerLine);
            }

            reader.close();

            consoleExitCode = process_getPrefLang.waitFor();


            for(String line : rawDataList)
            {
                if(isLanguageLine(line)){
                    String preferedLanguages = line.substring(line.indexOf("REG_MULTI_SZ") + "REG_MULTI_SZ".length()).trim();
                    prefferedLanguage = preferedLanguages.substring(0, 2);
                }
            }
            return prefferedLanguage;
        }
        catch (IOException ex) {
            ex.printStackTrace();
            return "en";
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }



    public static boolean isLanguageLine(String line){
        return line.contains("Languages");
    }
}