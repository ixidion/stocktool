package de.bluemx.stocktool.db.dao;

import de.bluemx.stocktool.db.helper.StaticVariables;
import de.bluemx.stocktool.db.model.StockquoteBasic;

import javax.persistence.*;
import java.util.List;


public class StockquoteBasicDAO {

    private EntityManagerFactory emf;
    private EntityManager em;

    public StockquoteBasicDAO(){
        emf = Persistence.createEntityManagerFactory(StaticVariables.PERISTENCE_UNIT);
        em = emf.createEntityManager();
    }

    public List<StockquoteBasic> fetchAll() {
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            TypedQuery<StockquoteBasic> query = em.createNamedQuery("StockquoteBasic.findAll", StockquoteBasic.class);
            List<StockquoteBasic> resultList = query.getResultList();
            tx.commit();
            return resultList;
        } catch (RuntimeException e) {
            throw new DBException("Entities could not be loaded.", e, null);
        }
    }


    public StockquoteBasic merge(StockquoteBasic stockquoteBasic) {
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            stockquoteBasic = em.merge(stockquoteBasic);
            em.flush();
            tx.commit();
            return stockquoteBasic;
        } catch (RuntimeException e) {
            tx.rollback();
            throw new DBException("Insert with entity failed.", e, stockquoteBasic);
        }
    }
}
