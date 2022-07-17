package com.api.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "roles")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Roles {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rId;

    @Column
    private String rName;

}