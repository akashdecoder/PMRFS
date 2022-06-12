package com.api.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "roles")
@AllArgsConstructor
@NoArgsConstructor
public class Roles {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private @Getter @Setter Long rId;

    @Column
    private @Getter @Setter String rName;

}