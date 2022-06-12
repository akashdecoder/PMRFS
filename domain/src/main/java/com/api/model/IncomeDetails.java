package com.api.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "income_details")
@NoArgsConstructor
@AllArgsConstructor
public class IncomeDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private @Getter @Setter Long iId;

    @Column(nullable = false)
    private @Getter @Setter Long iIncomeId;

    @Column(nullable = false)
    private @Getter @Setter String iIncome;

    @Column(nullable = false)
    private @Getter @Setter String uAadhaar;
}
