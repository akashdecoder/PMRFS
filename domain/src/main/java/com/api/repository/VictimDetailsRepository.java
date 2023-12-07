package com.api.repository;

import com.api.model.VictimDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import jakarta.transaction.Transactional;
import java.util.List;

@Repository
public interface VictimDetailsRepository extends JpaRepository<VictimDetails, Long> {

    @Query("select v from VictimDetails v where v.uId = ?1")
    public VictimDetails findByUId(Long uId);

    @Query(value = "select * from victim_details p where p.u_approve_status = ?1", nativeQuery = true)
    public List<VictimDetails> findAllByApprovedStatus(String status);

    @Transactional
    @Modifying
    @Query("update VictimDetails v set v.uApproveStatus = ?1 where v.vId = ?2")
    public void updateVictimDetails(String uStatus, Long vId);

    @Query("select v from VictimDetails v where v.uApproveStatus = ?1")
    public VictimDetails findVictimByApprovedStatus(String status);

    @Query("select v from VictimDetails v where v.uId = ?1 and v.uApproveStatus = ?2")
    public VictimDetails findVictimDetailsByUIdAndApproveStatus(Long uId, String status);
}
