package com.api.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "organization_details")
@NoArgsConstructor
@AllArgsConstructor
public class OrganizationDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private @Getter @Setter Long oId;

    @Column(nullable = false)
    private @Getter @Setter Long oRequestId;

    @Column(nullable = false)
    private @Getter @Setter String uAadhaar;

    @Column(nullable = false)
    private @Getter @Setter String oAmount;
}
