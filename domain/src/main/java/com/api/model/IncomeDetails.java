package com.api.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "income_details")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class IncomeDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long iId;

    @Column(nullable = false)
    private Long iIncomeId;

    @Column(nullable = false)
    private String iIncome;

    @Column(nullable = false)
    private String uAadhaar;
}
