package de.bluemx.stocktool.analysis;

import com.google.inject.Guice;
import de.bluemx.stocktool.db.dao.StockquoteBasicDAO;
import de.bluemx.stocktool.db.model.StockquoteBasic;
import de.bluemx.stocktool.helper.DefaultInject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import javax.xml.bind.JAXBException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AnalysisTest {

    private StockquoteBasicDAO stockquoteDao;

    @BeforeEach
    void setUp() throws JAXBException {
        this.stockquoteDao = Guice.createInjector(new DefaultInject()).getInstance(StockquoteBasicDAO.class);
    }

    @Test
    @Disabled
    void testRoE() throws JAXBException {
        StockquoteBasic basic = stockquoteDao.fetchByISIN("DE0007664039");
        Analysis analysis = new Analysis();
        int result = analysis.analyseRoE(basic);
        assertEquals(-1, result);
    }

}