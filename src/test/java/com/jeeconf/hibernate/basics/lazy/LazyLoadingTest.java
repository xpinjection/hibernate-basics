package com.jeeconf.hibernate.basics.lazy;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.jeeconf.hibernate.basics.BaseTest;
import com.jeeconf.hibernate.basics.entity.Account;
import com.jeeconf.hibernate.basics.entity.Client;
import org.junit.Test;

/**
 * Created by Igor Dmitriev / Mikalai Alimenkou on 4/29/16
 */

@DatabaseSetup("/data.xml")
public class LazyLoadingTest extends BaseTest {

    @Test
    public void lazyCollection() {
        Client client = getSession().get(Client.class, 10);
        System.out.println("<- got client");
        client.getAccounts();
        System.out.println("<- got accounts");
        client.getAccounts().size();
        System.out.println("<- got accounts size");
    }

    @Test
    public void lazyEntity() {
        Account account = getSession().get(Account.class, 10);
        System.out.println("<- got account");
        account.getClient();
        System.out.println("<- got client");
        account.getClient().getId();
        System.out.println("<- got client id");
    }

    @Test
    public void loadMethod() {
        Client client = getSession().load(Client.class, 10);
        System.out.println("<- got client");
        client.getAge();
        System.out.println("<- got age");
    }
}
