package com.api.model;

import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "pmo_fund_history")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PMOFundHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pFId;

    @Column(nullable = false)
    private Long uId;

    @Column(nullable = false)
    private String pFApprovedAmount;

    @Column(nullable = false)
    private String pFApprovedFundReason;

    @Column(nullable = false)
    private String pFApprovedFundStatus;

    @Column(nullable = false)
    private Timestamp uRequestTimestamp;

    @Column(nullable = true)
    private Timestamp uApprovedTimestamp;

}
