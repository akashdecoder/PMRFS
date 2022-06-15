package com.api.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "patient_details")
@NoArgsConstructor
@AllArgsConstructor
public class PatientDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private @Getter @Setter Long pId;

    @Column(nullable = false)
    private @Getter @Setter Long uId;

    @Column(nullable = false)
    private @Getter @Setter String pAadhaar;

    @Column(nullable = false)
    private @Getter @Setter String pPan;

    @Column(nullable = false)
    private @Getter @Setter String pOccupation;

    @Column(nullable = false)
    private @Getter @Setter String pFundNeed;

    @Column(nullable = false)
    private @Getter @Setter String pCaseType;

    @Column(nullable = false)
    private @Getter @Setter String pHospitalFundRequestId;

    @Column(nullable = false)
    private @Getter @Setter String pIncomeId;

    @Column(nullable = false, columnDefinition = "int default 0")
    private @Getter @Setter int pAttempts;

    @Column(nullable = false)
    private @Getter @Setter String uApproveStatus;

}
