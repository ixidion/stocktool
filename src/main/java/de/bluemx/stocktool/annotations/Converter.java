package de.bluemx.stocktool.annotations;

import de.bluemx.stocktool.converter.StringConverter;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(value = RetentionPolicy.RUNTIME)
public @interface Converter {

    Class converterClass() default StringConverter.class;

    String[] variables() default {};
}
