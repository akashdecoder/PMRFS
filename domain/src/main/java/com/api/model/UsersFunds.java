package com.api.model;

import lombok.*;
import jakarta.persistence.*;
import java.sql.Timestamp;

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

    @Column(nullable = false)
    private @Getter @Setter Timestamp uRequestTimestamp;

    @Column(nullable = true)
    private @Getter @Setter Timestamp uApprovedTimestamp;

}
