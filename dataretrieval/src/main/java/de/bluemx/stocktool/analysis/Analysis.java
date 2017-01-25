package de.bluemx.stocktool.analysis;

import de.bluemx.stocktool.db.model.ReturnOnEquity;
import de.bluemx.stocktool.db.model.StockquoteBasic;
import de.bluemx.stocktool.db.model.YearComparator;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

/**
 * Created by teclis on 23.01.17.
 */
public class Analysis {


    public int analyseRoE(StockquoteBasic basic) {
        List<ReturnOnEquity> roeList = basic.getFetches().get(0).getReturnOnEquityList();
        Collections.sort(roeList, new YearComparator());
        Collections.reverse(roeList);
        BigDecimal roeValue = null;
        for (ReturnOnEquity roe : roeList) {
            if (!roe.isEstimated()) {
                roeValue = roe.getTableValue();
                if (roeValue == null) throw new IllegalArgumentException("RoE is null.");
            }
        }
        throw new IllegalArgumentException("RoE Analysis failed.");
    }


}
