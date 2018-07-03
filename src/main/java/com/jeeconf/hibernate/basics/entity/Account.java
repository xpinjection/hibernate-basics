package com.jeeconf.hibernate.basics.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Account {
    @Id
    @GeneratedValue
    @Column(name = "id_account")
    private Integer id;

    private int amount;
    private String currency;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_client")
    private Client client;

}
