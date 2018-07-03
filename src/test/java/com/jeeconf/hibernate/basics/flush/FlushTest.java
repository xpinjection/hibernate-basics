package com.jeeconf.hibernate.basics.flush;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.jeeconf.hibernate.basics.BaseTest;
import com.jeeconf.hibernate.basics.entity.Account;
import com.jeeconf.hibernate.basics.entity.Client;
import com.jeeconf.hibernate.basics.entity.Report;
import org.junit.Test;
import org.springframework.test.annotation.Commit;

@DatabaseSetup("/data.xml")
public class FlushTest extends BaseTest {

    @Test
    @Commit
    public void showSpecificExecutionOrder() {
        // 1. select client
        Client client = session.get(Client.class, 100);
        // 2. select account
        Account account = client.getAccounts().get(0);
        // 3. delete account
        session.delete(account);
        // 4. update client
        client.setName("Elvis");
        // 5. insert new report
        Report report = new Report();
        report.setDescription("Client has been updated; Account has been removed");
        session.persist(report);
    }

    @Test
    @Commit
    public void showFlushNativeSelect() {
        Client client = new Client();
        client.setName("Bob");
        session.persist(client);
        String sql = "select c.id_client,c.name,c.age from Client c";
        session.createNativeQuery(sql).list();
        //em.createNativeQuery(sql).getResultList();
    }

    @Test
    @Commit
    public void updateAllFields() {
        Client client = session.get(Client.class, 100);
        client.setAge(30);
    }
}
