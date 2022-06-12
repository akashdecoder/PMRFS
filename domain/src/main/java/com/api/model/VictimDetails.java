package com.api.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "victim_details")
@NoArgsConstructor
@AllArgsConstructor
public class VictimDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private @Getter @Setter Long vId;

    @Column(nullable = false)
    private @Getter @Setter Long uId;

    @Column(nullable = false)
    private @Getter @Setter String vAadhaar;

    @Column(nullable = false)
    private @Getter @Setter String vPan;

    @Column(nullable = false)
    private @Getter @Setter String vFundNeed;

    @Column(nullable = false)
    private @Getter @Setter String vCaseType;

    @Column(nullable = false)
    private @Getter @Setter String vOrganizationId;

    @Column(nullable = false, columnDefinition = "int default 0")
    private @Getter @Setter int vAttempts;

}
