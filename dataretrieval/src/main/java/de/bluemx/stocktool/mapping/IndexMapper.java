package de.bluemx.stocktool.mapping;

import de.bluemx.stocktool.model.Index;

public class IndexMapper {

    public Index asEnum(String index) {
        return Index.valueOf(index);
    }

    public String asString(Index index) {
        return index.toString();
    }

}
