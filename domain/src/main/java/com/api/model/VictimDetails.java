package com.api.model;

import lombok.*;
import jakarta.persistence.*;

@Entity
@Table(name = "victim_details")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VictimDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long vId;

    @Column(nullable = false)
    private Long uId;

    @Column(nullable = false)
    private String vAadhaar;

    @Column(nullable = false)
    private String vPan;

    @Column(nullable = false)
    private String vFundNeed;

    @Column(nullable = false)
    private String vCaseType;

    @Column(nullable = false)
    private String vOrganizationId;

    @Column(nullable = false, columnDefinition = "int default 0")
    private int vAttempts;

    @Column(nullable = false)
    private String uApproveStatus;

}
