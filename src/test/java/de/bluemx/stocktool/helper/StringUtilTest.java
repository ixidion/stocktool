package de.bluemx.stocktool.helper;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by teclis on 15.12.16.
 */
class StringUtilTest {

    @Test
    void replacePlaceholder() {
        String testString = "TEXT1 {to_replace} TEXT3";
        String search = "to_replace";
        String result = "TEXT1 TEXT2 TEXT3";
        String replacement = "TEXT2";
        String actual = StringUtil.replacePlaceholder(testString, search, replacement);
        Assertions.assertEquals(result, actual);
    }

}