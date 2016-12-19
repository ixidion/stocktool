package de.bluemx.stocktool.helper;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {
    final static Logger log = LoggerFactory.getLogger(StringUtil.class);

    /**
     * Replaces a variable in a given text. The variable has to be in curly brackets.
     *
     * Example: My text {variable}
     *
     *
     *
     * @param text the text with the variables
     * @param searchstring the variable which should be replaced
     * @param replacement the string which should substitute the variable
     * @return the substituted String
     * @throws IllegalArgumentException if variable is not found or the text contains the same
     * variable more than once
     */
    public static String replacePlaceholder(String text, String searchstring, String replacement) {
        String bracketSearchstring = "{" + searchstring + "}";
        if (StringUtils.countMatches(text, bracketSearchstring) == 1) {
            return StringUtils.replaceOnceIgnoreCase(text, bracketSearchstring, replacement);
        } else {
            log.error("String '{}' not found or found more than one time in text: {}", bracketSearchstring, text);
            throw new IllegalArgumentException("String not found.");
        }
    }

    /**
     * Extract the first hit of the given pattern from a given text.
     * @param text
     * @param pattern
     * @return The found String or null, if nothing found
     */
    public static String extractPatternFromString(String text, String pattern) {
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(text);

        if (m.find()) {
            log.debug("Found Regexp '{}'", m.group(1));
            return m.group(1);
        }
        log.error("Regexp '{}' in String '{}' not found.", pattern, text);
        return null;
    }

}
