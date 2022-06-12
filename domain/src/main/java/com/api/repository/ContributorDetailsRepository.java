package com.api.repository;

import com.api.model.ContributorDetails;
import com.api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContributorDetailsRepository extends JpaRepository<ContributorDetails, Long> {

    @Query(value = "select * from contributor_details c where c.c_name=?1", nativeQuery = true)
    public List<ContributorDetails> findAllByName(String name);

    @Query(value = "select * from contributor_details c where c.c_contribution_for=?1", nativeQuery = true)
    public List<ContributorDetails> findAllByContributionFor(String type);
}
