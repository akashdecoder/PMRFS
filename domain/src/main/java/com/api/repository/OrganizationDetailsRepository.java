package com.api.repository;

import com.api.model.OrganizationDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrganizationDetailsRepository extends JpaRepository<OrganizationDetails, Long> {

    @Query("select o from OrganizationDetails o where o.oRequestId=?1")
    public OrganizationDetails getByRequestId(Long oRequestId);

    @Query("select o from OrganizationDetails o where o.uAadhaar=?1")
    public OrganizationDetails getByAadhaar(String uAadhaar);

    @Query(value = "select * from organization_details o where o.u_aadhaar=?1", nativeQuery = true)
    public List<OrganizationDetails> getAllByAadhaar(String uAadhaar);
}
