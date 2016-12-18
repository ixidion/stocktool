package de.bluemx.stocktool.helper;

import com.google.inject.Inject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StringUtilTest {

    private StringUtil stringUtil;

    @BeforeEach
    void beforeEach() {
        stringUtil = new StringUtil();
    }

    /**
     * Tests if placeholder will work
     */
    @Test
    void testReplacePlaceholder() {
        String testString = "TEXT1 {to_replace} TEXT3";
        String search = "to_replace";
        String result = "TEXT1 TEXT2 TEXT3";
        String replacement = "TEXT2";
        String actual = stringUtil.replacePlaceholder(testString, search, replacement);
        Assertions.assertEquals(result, actual);
    }

    /**
     * Tests Assertion if no placeholder exists
     */
    @Test
    void testAssertionNoPlaceholder() {
        String testString = "TEXT1 TEXT3";
        String search = "to_replace";
        String replacement = "TEXT2";
        assertThrows(IllegalArgumentException.class,
                () -> stringUtil.replacePlaceholder(testString, search, replacement));
    }

    /**
     * Tests assertion if more than one variable with same name exists.
     */
    @Test
    void testAssertionTooMuchPlaceholders() {
        String testString = "TEXT1 {to_replace} TEXT3 {to_replace}";
        String search = "to_replace";
        String replacement = "TEXT2";
        assertThrows(IllegalArgumentException.class,
                () -> stringUtil.replacePlaceholder(testString, search, replacement));
    }

}