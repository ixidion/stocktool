import de.bluemx.stocktool.db.model.StockquoteBasic;
import de.bluemx.stocktool.mapping.StockquoteMapper;
import de.bluemx.stocktool.model.Index;
import de.bluemx.stocktool.model.StockQuoteData;
import de.bluemx.stocktool.model.YearEstimated;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Year;
import java.util.SortedMap;
import java.util.TreeMap;

class StockquoteMapperTest {

    @Test
    void testBasicMapping() {
        StockquoteMapper map = Mappers.getMapper(StockquoteMapper.class);
        StockQuoteData stockdata = new StockQuoteData("123456789", Index.DAX);
        stockdata.setStockname("STOCKNAME");
        stockdata.setSymbol("SYMBOL");
        stockdata.setFinancialYear(LocalDate.of(0, 12, 31));
        SortedMap<YearEstimated, BigDecimal> treemap = new TreeMap<>();
        treemap.put(new YearEstimated(Year.of(2016), true), new BigDecimal("2.3"));
        stockdata.setEps(treemap);
        StockquoteBasic basic = map.quoteToBasic(stockdata);

    }

}