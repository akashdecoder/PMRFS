package com.api.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "public_service_details")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PublicServiceDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pUId;

    @Column(nullable = false)
    private Long uId;

    @Column(nullable = false)
    private String pUAadhaar;

    @Column(nullable = false)
    private String pUPan;

    @Column(nullable = false)
    private  String pUFundNeed;

    @Column(nullable = false)
    private String pUEmployees;

    @Column(nullable = false)
    private String pUServiceType;

    @Column(nullable = false)
    private String pUOfficialConsentId;

    @Column(nullable = false, columnDefinition = "int default 0")
    private int pAttempts;

    @Column(nullable = false)
    private String uApproveStatus;
}
