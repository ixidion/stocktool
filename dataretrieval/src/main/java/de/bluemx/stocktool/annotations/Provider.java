package de.bluemx.stocktool.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(value = RetentionPolicy.RUNTIME)
public @interface Provider {
    String name();

    String url();

    String required() default "";

    Dataprovider dataprovider();

    Variable[] variables();
}
