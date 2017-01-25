package de.bluemx.stocktool.fetch;

import com.google.inject.Guice;
import de.bluemx.stocktool.db.dao.StockquoteBasicDAO;
import de.bluemx.stocktool.db.model.StockquoteBasic;
import de.bluemx.stocktool.helper.DefaultInject;
import de.bluemx.stocktool.mapping.StockquoteMapper;
import de.bluemx.stocktool.model.Index;
import de.bluemx.stocktool.model.StockQuoteData;
import org.mapstruct.factory.Mappers;

import java.sql.Timestamp;

/**
 * Created by teclis on 17.01.17.
 */
public class FetchTask implements Runnable {
    private StockquoteBasic basic;
    private IsinFetcher fetcher;
    private StockquoteBasicDAO stockquoteDao;
    private StockquoteMapper map = Mappers.getMapper(StockquoteMapper.class);
    private Timestamp batchRun;

    public FetchTask(StockquoteBasic basic, Timestamp batchRun) {
        this.basic = basic;
        this.batchRun = batchRun;
        this.fetcher = Guice.createInjector(new DefaultInject()).getInstance(IsinFetcher.class);
        this.stockquoteDao = Guice.createInjector(new DefaultInject()).getInstance(StockquoteBasicDAO.class);
    }

    @Override
    public void run() {
        StockQuoteData stockdata = fetcher.populateByIsin(basic.getIsin(), Index.DAX);
        basic = map.quoteToBasic(stockdata, basic);
        basic.getFetches().get(0).setBatchRun(batchRun);
        stockquoteDao.merge(basic);
    }
}
