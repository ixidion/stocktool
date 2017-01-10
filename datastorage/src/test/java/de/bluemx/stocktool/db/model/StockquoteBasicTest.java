package de.bluemx.stocktool.db.model;

import de.bluemx.stocktool.db.dao.StockquoteBasicDAO;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;


/**
 * These tests don't work in the CI environment, because the database is not mocked
 * Only used during development, to test the code.
 */
@Disabled
class StockquoteBasicTest {
    private static EntityManagerFactory emf;
    private static EntityManager em;

    @BeforeAll
    static void beforeAll() {
        emf = Persistence.createEntityManagerFactory("default");
        em = emf.createEntityManager();
    }

    @Test
    void simpleDBConnect() {
        em.getTransaction().begin();
        StockquoteBasic basic = em.find(StockquoteBasic.class, 1);
        StockquoteDetail detail1 = basic.getFetches().get(1);
        em.getTransaction().commit();
//        assertEquals("DE000A0KFKB3", isin);
    }

    @Test
    void failedInsert() {
        StockquoteBasic basicData = new StockquoteBasic();
        basicData.setIsin("Test");
        StockquoteDetail detail = new StockquoteDetail();
//        detail.setFetch_date(new LocalDate());
        basicData.addDetail(detail);
        StockquoteBasicDAO stockDao = new StockquoteBasicDAO();
        stockDao.merge(basicData);
    }

    @Test
    void testFetchAll() {
        StockquoteBasicDAO dao = new StockquoteBasicDAO();
        List<StockquoteBasic> basic = dao.fetchAll();
    }

}