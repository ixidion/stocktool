package de.bluemx.stocktool.db.model;

import java.util.Comparator;

public class YearComparator implements Comparator<TableKeyValues> {

    @Override
    public int compare(TableKeyValues o1, TableKeyValues o2) {
        return o1.getTableYear().compareTo(o2.getTableYear());
    }
}
