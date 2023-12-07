package com.api.model;

import lombok.*;

import jakarta.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "contributor_details")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContributorDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cId;

    @Column(nullable = false)
    private String cName;

    @Column(nullable = false)
    private String cAddress;

    @Column(nullable = false)
    private String cContributionFor;

    @Column(nullable = false)
    private String cAmount;

    @Column(nullable = false)
    private Timestamp cContributionTimestamp;
}
