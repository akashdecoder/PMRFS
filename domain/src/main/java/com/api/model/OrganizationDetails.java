package com.api.model;

import lombok.*;

import jakarta.persistence.*;

@Entity
@Table(name = "organization_details")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrganizationDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long oId;

    @Column(nullable = false)
    private Long oRequestId;

    @Column(nullable = false)
    private String uAadhaar;

    @Column(nullable = false)
    private String oAmount;

    @Column(nullable = false)
    private String oIsApproved;

}
