package de.bluemx.stocktool.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(value = RetentionPolicy.RUNTIME)
public @interface Resolver {

    String provider();
    Source source();
    Extract[] extractors();
    Validate[] validators() default {};

    Converter converter() default @Converter();
}
