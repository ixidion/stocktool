package de.bluemx.stocktool.annotations;

/**
 * Created by Patrick Labonte on 13.12.2016.
 */
public enum Source {
    /**
     * The selected text of the tag is extracted.
     */
    RESPONSE_TEXT,
    /**
     * The Tag as whole is extracted.
     */
    RESPONSE_TAG,
    /**
     * The url is extracted.
     */
    URL;
}
