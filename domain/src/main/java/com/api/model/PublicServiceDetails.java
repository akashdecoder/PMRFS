package com.api.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "public_service_details")
@NoArgsConstructor
@AllArgsConstructor
public class PublicServiceDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private @Getter @Setter Long pUId;

    @Column(nullable = false)
    private @Getter @Setter Long uId;

    @Column(nullable = false)
    private @Getter @Setter String pUAadhaar;

    @Column(nullable = false)
    private @Getter @Setter String pUPan;

    @Column(nullable = false)
    private @Getter @Setter String pUFundNeed;

    @Column(nullable = false)
    private @Getter @Setter String pUEmployees;

    @Column(nullable = false)
    private @Getter @Setter String pUServiceType;

    @Column(nullable = false)
    private @Getter @Setter String pUOfficialConsentId;

    @Column(nullable = false, columnDefinition = "int default 0")
    private @Getter @Setter int pAttempts;

    @Column(nullable = false)
    private @Getter @Setter String uApproveStatus;
}
