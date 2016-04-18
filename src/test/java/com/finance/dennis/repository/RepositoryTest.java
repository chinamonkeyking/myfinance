package com.finance.dennis.repository;

import org.junit.Before;
import org.junit.After;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.sql.SQLException;

/**
 * Created by XiaoNiuniu on 4/18/2016.
 */

/*
 * This class should be used as a base class for all repository test class
 * It provides some common functions and will rollback changes to the in memory DB
 * so that the data inserted in one test will be discarded and will not impact any
 * other tests
 */
public abstract class RepositoryTest {

    //final static String TRUNCATE_HSQL_SCHEMA = "TRUNCATE SCHEMA PUBLIC AND COMMIT NO CHECK";

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    protected PlatformTransactionManager transactionManager;

    protected TransactionStatus transactionStatus;

    // Create a transaction before each test method
    @Before
    public void beginTransaction() throws SQLException {
        if (transactionStatus != null) throw new RuntimeException("A transaction already created");

        transactionStatus = transactionManager.getTransaction(new DefaultTransactionDefinition());
    }

    // Roll back a transaction after each test method
    @After
    public void rollbackTransaction() throws SQLException {
        try {
            transactionManager.rollback(transactionStatus);
        } catch (TransactionException e) {
            //e.printStackTrace();
        }
        finally {
            transactionStatus = null;
        }
    }

    public void commitTransaction() throws SQLException {
        try {
            transactionManager.commit(transactionStatus);
        } catch (TransactionException e) {
            //e.printStackTrace();
        }
        finally {
            transactionStatus = null;
        }
    }
}
