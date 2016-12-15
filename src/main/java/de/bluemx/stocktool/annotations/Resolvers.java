package de.bluemx.stocktool.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Collection;
import java.util.List;

@Retention(value = RetentionPolicy.RUNTIME)
public @interface Resolvers {
    Resolver[] value();
}
