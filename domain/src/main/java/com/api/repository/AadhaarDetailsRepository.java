package com.api.repository;

import com.api.model.AadhaarDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AadhaarDetailsRepository extends JpaRepository<AadhaarDetails, Long> {

    @Query("select a.uAadhaar from AadhaarDetails a where a.uAadhaar=?1")
    public String getAddressByAadhaar(String uAadhaar);

    @Query("select a from AadhaarDetails a where a.uAadhaar=?1")
    public AadhaarDetails findByAadhaar(String uAadhaar);
}
