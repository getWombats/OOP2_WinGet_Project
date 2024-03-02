package Util;

import ch.hftm.oop2_winget_project.Util.SystemLanguage;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SystemLanguageTest {

    @Test
    void testGetPreferredLanguageNonEmpty() {
        String language = SystemLanguage.getPreferredLanguage();
        assertNotNull(language);  // Check that we get a non-null response.
        assertFalse(language.isEmpty()); // Check that the response is not empty.
    }

    @Test
    void testConsoleExitCode() {
        SystemLanguage.getPreferredLanguage();
        assertEquals(0, SystemLanguage.getPromptExitCode());
    }
}