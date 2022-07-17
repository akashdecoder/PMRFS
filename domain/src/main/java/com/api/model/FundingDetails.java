package com.api.model;

import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "funding_details")
public class FundingDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long fId;

    @Column(nullable = false)
    private Long uId;

    @Column(nullable = false)
    private Long uBankAccountNumber;

    @Column(nullable = false)
    private Long fRequestedAmount;

    @Column(nullable = true)
    private Long fApprovedAmount;

    @Column(nullable = false)
    private Timestamp fRequestedTimestamp;

    @Column(nullable = true)
    private Timestamp fApprovedTimestamp;

    @Column(nullable = false)
    private String fRequestReason;

    @Column(nullable = true)
    private String fAccountAddress;

    @Column(nullable = true)
    private String fTransactionHash;

    @Column(nullable = true)
    private String fAccountAddressUsed;


}
