package com.api.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "pmo_fund_history")
@NoArgsConstructor
@AllArgsConstructor
public class PMOFundHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private @Getter @Setter Long pFId;

    @Column(nullable = false)
    private @Getter @Setter Long uId;

    @Column(nullable = false)
    private @Getter @Setter String pFApprovedAmount;

    @Column(nullable = false)
    private @Getter @Setter String pFApprovedFundReason;

    @Column(nullable = false)
    private @Getter @Setter String pFApprovedFundStatus;

}
