package com.api.repository;

import com.api.model.PublicServiceDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import jakarta.transaction.Transactional;
import java.util.List;

@Repository
public interface PublicServiceDetailsRepository extends JpaRepository<PublicServiceDetails, Long> {

    @Query("select p from PublicServiceDetails p where p.uId = ?1")
    public PublicServiceDetails findByUId(Long uId);

    @Query(value = "select * from public_service_details p where p.u_approve_status = ?1", nativeQuery = true)
    public List<PublicServiceDetails> findAllByApprovedStatus(String status);

    @Transactional
    @Modifying
    @Query("update PublicServiceDetails p set p.uApproveStatus = ?1 where p.pUId = ?2")
    public void updatePublicServiceDetails(String uStatus, Long pUId);

    @Query("select p from PublicServiceDetails p where p.uApproveStatus = ?1")
    public PublicServiceDetails findPublicServiceByApprovedStatus(String status);

    @Query("select p from PublicServiceDetails p where p.uId = ?1 and p.uApproveStatus = ?2")
    public PublicServiceDetails findPublicServiceDetailsByUIdAndApproveStatus(Long uId, String status);
}
