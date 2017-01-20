package de.bluemx.stocktool.fetch;

import com.google.inject.Inject;
import de.bluemx.stocktool.db.dao.StockquoteBasicDAO;
import de.bluemx.stocktool.db.model.StockquoteBasic;
import de.bluemx.stocktool.mapping.StockquoteMapper;
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
        ThreadPool pool = new ThreadPool(20);
        List<StockquoteBasic> basicList = stockquoteDao.fetchAll();
        for (StockquoteBasic basic : basicList) {
            FetchTask fetchTask = new FetchTask(basic);
            pool.execute(fetchTask);
        }
        while (pool.getQueueSize() > 0) {
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
