package de.bluemx.stocktool.db.model;

import de.bluemx.stocktool.db.dao.StockquoteBasicDAO;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by teclis on 02.01.17.
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
    @Disabled
    void simpleDBConnect() {
        em.getTransaction().begin();
        StockquoteBasic basic = em.find(StockquoteBasic.class, 1);
        StockquoteDetail detail1 = basic.getFetches().get(1);
        em.getTransaction().commit();
//        assertEquals("DE000A0KFKB3", isin);
    }

    @Test
    @Disabled
    void failedInsert() {
        StockquoteBasic basicData = new StockquoteBasic();
        basicData.setIsin("Test");
        StockquoteDetail detail = new StockquoteDetail();
        detail.setFetch_date(new Date(0));
        basicData.addDetail(detail);
        StockquoteBasicDAO stockDao = new StockquoteBasicDAO();
        stockDao.merge(basicData);
    }

}