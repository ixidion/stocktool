package de.bluemx.stocktool.mapping;

import de.bluemx.stocktool.model.AnalystsOpinion;

/**
 * Created by teclis on 07.01.17.
 */
public class AnalystsMapper {
    public Integer asInteger(AnalystsOpinion index) {
        if (index == null) return null;
        return index.getValue();
    }
}
