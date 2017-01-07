package de.bluemx.stocktool.model;

import de.bluemx.stocktool.annotations.Dataprovider;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by teclis on 23.12.16.
 */
public enum Index {
    DAX("DAX-Index-20735", "20735"),
    SP500("S-P-500-Index-4359526", "4359526"),
    ESTOXX("EURO-STOXX-50-Index-193736", "193736"),
    NIKKEI("Nikkei-Index-60972397", "60972397"),
    SMI("SMI-Index-1555183", "1555183"),
    MDAX("MDAX-Index-323547", "323547"),
    SDAX("SDAX-Index-324724", "324724"),
    TECDAX("TecDAX-Index-6623216", "6623216");

    private Map<Dataprovider, String[]> providerMap = new HashMap<>();


    Index(String... urlParts) {
        providerMap.put(Dataprovider.ONVISTA, new String[]{urlParts[0], urlParts[1]});
    }


    public String[] getIndexConfig(Dataprovider provider) {
        return providerMap.get(provider);
    }

    @Override
    public String toString() {
        return this.name();
    }
}
