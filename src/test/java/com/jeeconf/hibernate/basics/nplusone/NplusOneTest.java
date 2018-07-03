package com.jeeconf.hibernate.basics.nplusone;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.jeeconf.hibernate.basics.BaseTest;
import com.jeeconf.hibernate.basics.entity.Client;
import org.junit.Test;
import org.springframework.test.annotation.Commit;

import java.util.List;

@DatabaseSetup("/data.xml")
public class NplusOneTest extends BaseTest {

    @Test
    public void nPlusOneIssue() {
        //noinspection unchecked
        List<Client> clients = session.createQuery("select c from Client c").list();
        clients.forEach(c -> c.getAccounts().size());
    }

    @Test
    public void extraLazy() {
        // add @LazyCollection(LazyCollectionOption.EXTRA) to Client accounts
        Client client = session.get(Client.class, 100);
        client.getAccounts().size();
        client.getAccounts().get(0);
    }

    @Test
    @Commit
    public void mergeCollections() {
        Client client = session.get(Client.class, 100);
        //client.setAccounts(new ArrayList<>());
        client.getAccounts().clear();
    }
}
