package de.bluemx.stocktool.annotations;

/**
 * Created by Patrick Labonte on 13.12.2016.
 */
public @interface Resolver {

    String name();
    Source source();
    Extract[] extractors();
}
