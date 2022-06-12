package com.api.repository;

import com.api.model.PMOFundHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PMOFundHistoryRepository extends JpaRepository<PMOFundHistory, Long> {

    @Query(value = "select * from pmo_fund_history pm where pm.pfapproved_fund_status=?1", nativeQuery = true)
    public List<PMOFundHistory> findAllApprovedStatus(String status);
}
