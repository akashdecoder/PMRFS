package com.api.model;


import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "aadhaar_details")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AadhaarDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long aId;

    @Column(nullable = false)
    private String uAadhaar;

    @Column(nullable = false)
    private String uAddress;
}
