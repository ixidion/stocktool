package de.bluemx.stocktool.annotations;

import java.util.Collection;
import java.util.List;

/**
 * Created by Patrick Labonte on 13.12.2016.
 */
public @interface Resolvers {
    Resolver[] value();
}
