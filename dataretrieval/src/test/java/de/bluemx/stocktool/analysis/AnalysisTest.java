package de.bluemx.stocktool.analysis;

import com.google.inject.Guice;
import de.bluemx.stocktool.db.dao.StockquoteBasicDAO;
import de.bluemx.stocktool.db.model.ReturnOnEquity;
import de.bluemx.stocktool.db.model.StockquoteBasic;
import de.bluemx.stocktool.db.model.StockquoteDetail;
import de.bluemx.stocktool.helper.DefaultInject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import javax.xml.bind.JAXBException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Disabled
class AnalysisTest {

    private StockquoteBasicDAO stockquoteDao;

    @BeforeEach
    void setUp() throws JAXBException {
        this.stockquoteDao = Guice.createInjector(new DefaultInject()).getInstance(StockquoteBasicDAO.class);
    }

    @Test
    void testRoE() {
        StockquoteBasic basic = new StockquoteBasic();
        StockquoteDetail detail = new StockquoteDetail();
        detail.setBatchRun(new Timestamp(new Date().getTime()));
        basic.addDetail(detail);
        List<ReturnOnEquity> roeList = new Vector<>();
        ReturnOnEquity roe = new ReturnOnEquity();
        roe.setTableYear(LocalDate.of(2015, 1, 1));
        roe.setEstimated(false);
        roeList.add(roe);
        roe = new ReturnOnEquity();
        roe.setTableYear(LocalDate.of(2016, 1, 1));
        roe.setEstimated(false);
        roe.setTableValue(new BigDecimal("23.5"));
        roeList.add(roe);
        roe = new ReturnOnEquity();
        roe.setTableYear(LocalDate.of(2017, 1, 1));
        roe.setEstimated(true);
        detail.setReturnOnEquityList(roeList);
        Analysis analysis = new Analysis();
        AnalysisObject result = analysis.analyseRoE(basic);
        assertEquals(1, result.getResult());
    }


}