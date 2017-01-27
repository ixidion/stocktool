package de.bluemx.stocktool.helper;

import java.util.Collection;

/**
 * Check arguments of methods and return exception.
 * Created by teclis on 27.01.17.
 */
public final class ArgsChecker {

    private ArgsChecker() {
    }

    public static void checkForNull(Object aObject) {
        if (aObject == null) {
            throw new NullPointerException();
        }
    }

    public static void checkNumberGreaterThan(int expected, int number) {
        if (number <= expected) {
            throw new IllegalArgumentException(String.format("Number %s is not greater than %s", number, expected));
        }
    }

    public static void collectionHasSize(Collection coll, int size) {
        checkForNull(coll);
        if (coll.size() != size) {
            throw new IllegalArgumentException(String.format("Collection size expected %s but is %s", size, coll.size()));
        }
    }
}
