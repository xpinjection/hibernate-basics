package com.jeeconf.hibernate.basics.dirtychecking;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.jeeconf.hibernate.basics.BaseTest;
import com.jeeconf.hibernate.basics.entity.Account;
import com.jeeconf.hibernate.basics.entity.Client;
import org.hibernate.SessionFactory;
import org.hibernate.StatelessSession;
import org.junit.Test;
import org.springframework.test.annotation.Commit;

import java.util.List;

@DatabaseSetup("/data.xml")
public class DirtyCheckingTest extends BaseTest {
    @Test
    @Commit
    public void dirtyChecking() {
        Account account = session.get(Account.class, 10);
        account.setAmount(100);

        Client client = session.get(Client.class, 100);
    }

    @Test
    @Commit
    public void dirtyCheckingDisableForQuery() {
        // add @Immutable to Client
        Client client = session.get(Client.class, 100);
        // for queries it is also possible
        // getSession().createQuery("select c from Client c").setReadOnly(true).list();
    }

    @Test
    @Commit
    public void allOperationsAreDirectInStatelessSession() {
        SessionFactory sf = session.getSessionFactory();
        StatelessSession statelessSession = sf.openStatelessSession();
        List<Client> clients = statelessSession
                .createQuery("select c from Client c").list();
        clients.forEach(client -> {
            System.out.println(client.getName());
            client.setName("TEST");
            statelessSession.update(client);// direct database low-level operation
        });
    }
}
