package de.bluemx.stocktool.fetch;

import com.google.inject.Inject;
import de.bluemx.stocktool.db.dao.StockquoteBasicDAO;
import de.bluemx.stocktool.db.model.StockquoteBasic;
import de.bluemx.stocktool.mapping.StockquoteMapper;
import de.bluemx.stocktool.model.Index;
import de.bluemx.stocktool.model.StockQuoteData;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * Created by teclis on 10.01.17.
 */
public class BatchRunner {

    private StockquoteBasicDAO stockquoteDao;
    private IsinFetcher fetcher;
    private StockquoteMapper map = Mappers.getMapper(StockquoteMapper.class);

    @Inject
    public BatchRunner(StockquoteBasicDAO dao, IsinFetcher fetcher) {
        this.stockquoteDao = dao;
        this.fetcher = fetcher;
    }

    public void updateDatabase() {
        List<StockquoteBasic> basicList = stockquoteDao.fetchAll();
        if (basicList.size() > 1) {
            StockquoteBasic basic = basicList.get(0);
            StockQuoteData stockdata = fetcher.populateByIsin(basic.getIsin(), Index.DAX);
            basic = map.quoteToBasic(stockdata, basic);
            basic = stockquoteDao.merge(basic);
            System.out.println("bla");
        }
    }
}
