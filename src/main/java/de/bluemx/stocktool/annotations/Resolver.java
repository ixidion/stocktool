package de.bluemx.stocktool.annotations;

import de.bluemx.stocktool.converter.StringConverter;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(value = RetentionPolicy.RUNTIME)
public @interface Resolver {

    String provider();
    Source source();
    Extract[] extractors();
    Class converterClass() default StringConverter.class;

    Validate[] validators() default {};
}
