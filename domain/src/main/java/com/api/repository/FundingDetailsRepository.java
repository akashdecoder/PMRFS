package com.api.repository;

import com.api.model.FundingDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.sql.Timestamp;

@Repository
public interface FundingDetailsRepository extends JpaRepository<FundingDetails, Long> {

    @Transactional
    @Modifying
    @Query(value = "update funding_details set f_approved_amount = ?1, f_approved_timestamp = ?2 where f_id = ?3", nativeQuery = true)
    public void updateFundingDetails(Long approvedAmount, Timestamp approvedTimestamp, Long fId);

    @Transactional
    @Modifying
    @Query(value = "update funding_details set f_account_address = 1? where f_id = 2?", nativeQuery = true)
    public void revokeAccountAddress(String address, Long fId);
}
