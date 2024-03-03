package Util;


import ch.hftm.oop2_winget_project.Util.UniCodeChecker;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UniCodeCheckerTest {

    @Test
    public void testContainsHanScriptWithHanCharacters() {
        String hanString = "汉字"; // String with Han characters.
        boolean result = UniCodeChecker.containsHanScript(hanString);
        assertTrue(result, "String should contain Han script");
    }

    @Test
    public void testContainsHanScriptWithNonHanCharacters() {
        String nonHanString = "Hello, World!"; // String without Han characters.
        boolean result = UniCodeChecker.containsHanScript(nonHanString);
        assertFalse(result, "String should not contain Han script");
    }

    @Test
    public void testContainsHanScriptWithMixedCharacters() {
        String mixedString = "汉字 and English"; // String containing both Han and non-Han characters.
        boolean result = UniCodeChecker.containsHanScript(mixedString);
        assertTrue(result, "String should contain Han script");
    }

    @Test
    public void testContainsHanScriptWithEmptyString() {
        String emptyString = ""; // Empty string.
        boolean result = UniCodeChecker.containsHanScript(emptyString);
        assertFalse(result, "Empty string should not contain Han script");
    }

    @Test
    void testContainsHanScriptWithNull() {
        String nullString = null; // Null string.
        boolean result = UniCodeChecker.containsHanScript(nullString);
        assertTrue(result, "Null was passed but did not return true as expected.");
    }
}