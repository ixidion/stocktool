package de.bluemx.stocktool.helper;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class StringUtil {
    final static Logger log = LoggerFactory.getLogger(StringUtil.class);

    public static  String replacePlaceholder(String text, String searchstring, String replacement) {
        String bracketSearchstring = "{" + searchstring + "}";
        if (StringUtils.countMatches(text, bracketSearchstring) == 1) {
            return StringUtils.replaceOnceIgnoreCase(text, bracketSearchstring, replacement);
        } else {
            log.error("String '{}' not found or found more than one time in text: {}", bracketSearchstring, text);
            throw new IllegalArgumentException("String not found.");
        }
    }
}
