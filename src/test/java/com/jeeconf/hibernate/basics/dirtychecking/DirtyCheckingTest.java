package com.jeeconf.hibernate.basics.dirtychecking;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.jeeconf.hibernate.basics.BaseTest;
import com.jeeconf.hibernate.basics.entity.Account;
import com.jeeconf.hibernate.basics.entity.Client;
import org.hibernate.ScrollMode;
import org.hibernate.ScrollableResults;
import org.hibernate.SessionFactory;
import org.hibernate.StatelessSession;
import org.junit.Test;
import org.springframework.test.annotation.Commit;

/**
 * Created by Igor Dmitriev / Mikalai Alimenkou on 4/29/16
 */
@DatabaseSetup("/data.xml")
public class DirtyCheckingTest extends BaseTest {
    @Test
    @Commit
    public void dirtyChecking() {
        Account account = getSession().get(Account.class, 10);
        account.setAmount(100);

        Client client = getSession().get(Client.class, 10);
    }

    @Test
    @Commit
    public void dirtyCheckingDisableForQuery() {
        // add @Immutable to Client
        Client client = getSession().get(Client.class, 10);
        // for queries it is also possible
        // getSession().createQuery("select c from Client c").setReadOnly(true).list();
    }

    @Test
    @Commit
    public void statelessSession() {
        SessionFactory sf = getSession().getSessionFactory();
        StatelessSession statelessSession = sf.openStatelessSession();
        ScrollableResults scroll = statelessSession.createQuery("select c from Client c")
                .scroll(ScrollMode.FORWARD_ONLY);
        while (scroll.next()) {
            Client client = (Client) scroll.get(0);
            System.out.println(client.getName());
            client.setName("TEST");
            statelessSession.update(client);// direct database low-level operation
        }
    }
}
