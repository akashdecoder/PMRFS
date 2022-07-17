package com.api.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "patient_details")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PatientDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pId;

    @Column(nullable = false)
    private Long uId;

    @Column(nullable = false)
    private String pAadhaar;

    @Column(nullable = false)
    private String pPan;

    @Column(nullable = false)
    private String pOccupation;

    @Column(nullable = false)
    private String pFundNeed;

    @Column(nullable = false)
    private String pCaseType;

    @Column(nullable = false)
    private String pHospitalFundRequestId;

    @Column(nullable = false)
    private String pIncomeId;

    @Column(nullable = false, columnDefinition = "int default 0")
    private int pAttempts;

    @Column(nullable = false)
    private String uApproveStatus;

}
