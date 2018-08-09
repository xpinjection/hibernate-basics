package com.jeeconf.hibernate.basics.lazy;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.jeeconf.hibernate.basics.BaseTest;
import com.jeeconf.hibernate.basics.entity.Account;
import com.jeeconf.hibernate.basics.entity.Client;
import org.junit.Test;

import java.util.List;

@DatabaseSetup("/data.xml")
public class LazyLoadingTest extends BaseTest {

    @Test
    public void lazyCollection() {
        Client client = session.get(Client.class, 100);
        System.out.println("<- got client");
        List<Account> accounts = client.getAccounts();
        System.out.println("<- got accounts");
        accounts.size();
        System.out.println("<- got accounts size");
    }

    @Test
    public void lazyEntity() {
        Account account = session.get(Account.class, 10);
        System.out.println("<- got account");
        Client client = account.getClient();
        System.out.println("<- got client");
        client.getId();
        System.out.println("<- got client id");
    }

    @Test
    public void loadMethod() {
        Client client = session.load(Client.class, 100);
        System.out.println("<- got client");
        client.getAge();
        System.out.println("<- got age");
    }
}
