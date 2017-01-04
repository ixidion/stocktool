package de.bluemx.stocktool.db.dao;

import de.bluemx.stocktool.db.helper.StaticVariables;
import de.bluemx.stocktool.db.model.StockquoteBasic;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;


public class StockquoteBasicDAO {

    private EntityManagerFactory emf;
    private EntityManager em;

    public StockquoteBasicDAO(){
        emf = Persistence.createEntityManagerFactory(StaticVariables.PERISTENCE_UNIT);
        em = emf.createEntityManager();
    }


    public StockquoteBasic merge(StockquoteBasic stockquoteBasic) {
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            stockquoteBasic = em.merge(stockquoteBasic);
            tx.commit();
            return stockquoteBasic;
        } catch (RuntimeException e) {
            tx.rollback();
            throw new DBException("Insert with entity failed.", e, stockquoteBasic);
        }
    }
}
