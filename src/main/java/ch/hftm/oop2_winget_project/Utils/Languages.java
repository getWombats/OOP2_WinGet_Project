package ch.hftm.oop2_winget_project.Utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public enum Languages {


    en {
        public static String getColumnHeaderIdText()
        {
            return "id";
        }
        public static String getColumnHeaderVersionText()
        {
            return "version";
        }
        public static String getColumnHeaderAvailableText()
        {
            return "match";
        }
        public static String getColumnHeaderMatchText()
        {
            return "available";
        }
        public static String getColumnHeaderSourceText()
        {
            return "source";
        }

    },
    de {
        public static String getColumnHeaderIdText()
        {
            return "id";
        }
        public static String getColumnHeaderVersionText()
        {
            return "version";
        }
        public static String getColumnHeaderAvailableText()
        {
            return "übereinstimmung";
        }
        public static String getColumnHeaderMatchText()
        {
            return "verfügbar";
        }
        public static String getColumnHeaderSourceText()
        {
            return "quelle";
        }
    },
    fr {
        public static String getColumnHeaderIdText()
        {
            return "id";
        }
        public static String getColumnHeaderVersionText()
        {
            return "version";
        }
        public static String getColumnHeaderAvailableText()
        {
            return "concordance";
        }
        public static String getColumnHeaderMatchText()
        {
            return "disponsible";
        }
        public static String getColumnHeaderSourceText()
        {
            return "source";
        }
    };

//    public static String getPrefferedLanguage(){
//        try
//        {
//            String prefferedLanguage = "en";
//            ProcessBuilder processBuilder = new ProcessBuilder("REG QUERY \"HKEY_Current_User\\Control Panel\\International\\User Profile\" /v Languages /t REG_MULTI_SZ /se _");
//            processBuilder.redirectErrorStream(true);
//            Process process_getPrefLang = processBuilder.start();
//
//            BufferedReader reader = new BufferedReader(new InputStreamReader(process_getPrefLang.getInputStream()));
//
//            String rawDataStream;
//
//            while ((rawDataStream = reader.readLine()) != null) {
//                System.out.println(rawDataStream);
//            }
//
//            reader.close();
//            System.out.println(rawDataStream);
////            consoleExitCode = process_getPrefLang.waitFor();
//
//            if (process_getPrefLang.waitFor() == 0) {
//                String preferedLanguages = rawDataStream.substring(rawDataStream.indexOf("REG_MULTI_SZ") + "REG_MULTI_SZ".length()).trim();
//                System.out.println(preferedLanguages);
//                prefferedLanguage = preferedLanguages.substring(0, 2);
//
//            }
//
//            switch (prefferedLanguage) {
//                case "en": // English
//                    return "EN";
//                case "fr": // French
//                    return "FR";
//                case "de": // German
//                    return "DE";
//                default:
//                    return "EN"; // English
//            }
//        }
//
//        catch (IOException | InterruptedException ex) {
//            ex.fillInStackTrace();
//            return "EN";
//        }
//    }
}