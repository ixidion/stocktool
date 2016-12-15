package de.bluemx.stocktool.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(value = RetentionPolicy.RUNTIME)
public @interface Extract {
    SearchType searchType();
    String expression();
}
