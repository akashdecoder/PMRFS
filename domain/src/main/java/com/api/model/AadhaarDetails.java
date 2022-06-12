package com.api.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "aadhaar_details")
@NoArgsConstructor
@AllArgsConstructor
public class AadhaarDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private @Getter @Setter Long aId;

    @Column(nullable = false)
    private @Getter @Setter String uAadhaar;

    @Column(nullable = false)
    private @Getter @Setter String uAddress;
}
