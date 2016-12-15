package de.bluemx.stocktool.converter;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by plabonte on 15.12.2016.
 */
class StringConverterTest {
    @Test
    void convert() {
        StringConverter sc = new StringConverter();
        String str = (String) sc.convert("bla");
        assertEquals("bla", str);

    }

}