package com.api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "address_log")
public class AddressLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long aId;

    @Column(nullable = false)
    private Long fId;

    @Column(nullable = true)
    private String accountAddress;
}
