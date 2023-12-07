package com.api.model;


import jakarta.persistence.*;
import lombok.*;

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
