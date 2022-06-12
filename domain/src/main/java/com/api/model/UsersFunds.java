package com.api.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users_funds")
public class UsersFunds {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private @Getter @Setter Long uFId;

    @Column(nullable = false)
    private @Getter @Setter Long uId;

    @Column(nullable = false)
    private @Getter @Setter String uFirstName;

    @Column(nullable = false, length = 64)
    private @Getter @Setter String uFundsHistory;

    @Column(nullable = false, length = 64)
    private @Getter @Setter String uReason;

    @Column(nullable = false)
    private @Getter @Setter int uIsApproved; // 0: requested, 1: approved

    @Column(nullable = true)
    private @Getter @Setter String uTransactionHash;


}
