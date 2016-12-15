package de.bluemx.stocktool.annotations;

import de.bluemx.stocktool.converter.StringConverter;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(value = RetentionPolicy.RUNTIME)
public @interface Resolver {

    String name();
    Source source();
    Extract[] extractors();
    Class converterClass() default StringConverter.class;
}
