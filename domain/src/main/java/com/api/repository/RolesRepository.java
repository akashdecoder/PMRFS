package com.api.repository;

import com.api.model.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RolesRepository extends JpaRepository<Roles, Long> {

    @Query("select r from Roles r where r.rName = ?1")
    public Roles findByRName(String name);
}
