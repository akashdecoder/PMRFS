package com.api.repository;

import com.api.model.IncomeDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IncomeDetailsRepository extends JpaRepository<IncomeDetails, Long> {

    @Query("select i from IncomeDetails i where i.iIncomeId=?1")
    public IncomeDetails getByIncomeId(Long iIncomeId);

    @Query("select i from IncomeDetails i where i.uAadhaar=?1")
    public IncomeDetails getByAadhaar(String uAadhaar);
}
