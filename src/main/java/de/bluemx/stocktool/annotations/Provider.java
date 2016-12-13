package de.bluemx.stocktool.annotations;

/**
 * Created by Patrick Labonte on 13.12.2016.
 */
public @interface Provider {
    String name();

    String url();

    String required() default "";

    Variable[] variables();
}
