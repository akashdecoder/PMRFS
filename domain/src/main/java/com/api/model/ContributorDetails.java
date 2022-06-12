package com.api.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "contributor_details")
@NoArgsConstructor
@AllArgsConstructor
public class ContributorDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private @Getter @Setter Long cId;

    @Column(nullable = false)
    private @Getter @Setter String cName;

    @Column(nullable = false)
    private @Getter @Setter String cAddress;

    @Column(nullable = false)
    private @Getter @Setter String cContributionFor;

    @Column(nullable = false)
    private @Getter @Setter String cAmount;
}
