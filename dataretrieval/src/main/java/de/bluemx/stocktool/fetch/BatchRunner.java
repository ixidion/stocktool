package de.bluemx.stocktool.fetch;

import com.google.inject.Inject;
import de.bluemx.stocktool.db.dao.StockquoteBasicDAO;
import de.bluemx.stocktool.db.model.StockquoteBasic;
import de.bluemx.stocktool.mapping.StockquoteMapper;
import org.mapstruct.factory.Mappers;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * Created by teclis on 10.01.17.
 */
public class BatchRunner {

    private StockquoteBasicDAO stockquoteDao;
    private StockquoteMapper map = Mappers.getMapper(StockquoteMapper.class);
    private Timestamp batchRun;

    @Inject
    public BatchRunner(StockquoteBasicDAO dao) {
        this.stockquoteDao = dao;
    }

    public void updateDatabase() {
        this.batchRun = new Timestamp(new Date().getTime());
        ThreadPool pool = new ThreadPool(20);
        List<StockquoteBasic> basicList = stockquoteDao.fetchAll();
        for (StockquoteBasic basic : basicList) {
            FetchTask fetchTask = new FetchTask(basic, batchRun);
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
