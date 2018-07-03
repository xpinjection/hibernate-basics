package com.jeeconf.hibernate.basics;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import org.hibernate.Session;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Arrays;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
@TestExecutionListeners({
        TransactionalTestExecutionListener.class,
        DependencyInjectionTestExecutionListener.class,
        DbUnitTestExecutionListener.class
})
@Transactional
public abstract class BaseTest {

    private static final String[] DB_UNIT_SET_UP = {"",
            "****************************************************************",
            "*************** DATABASE HAS BEEN ALREADY SET UP ***************",
            "****************************************************************"
    };

    @PersistenceContext
    protected EntityManager em;

    protected Session session;

    @Before
    public void dbAllSet() {
        Arrays.stream(DB_UNIT_SET_UP).forEach(System.out::println);
        session = em.unwrap(Session.class);
    }
}
