package com.api.repository;

import com.api.model.PatientDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface PatientDetailsRepository extends JpaRepository<PatientDetails, Long> {

    @Query("select p from PatientDetails p where p.uId = ?1")
    public PatientDetails findByUId(Long uId);

    @Query(value = "select * from patient_details p where p.u_approve_status = ?1", nativeQuery = true)
    public List<PatientDetails> findAllByApprovedStatus(String status);

    @Transactional
    @Modifying
    @Query("update PatientDetails p set p.uApproveStatus = ?1 where p.pId = ?2")
    public void updatePatientDetails(String uStatus, Long pId);

    @Query("select p from PatientDetails p where p.uApproveStatus = ?1")
    public PatientDetails findPatientByApprovedStatus(String status);

    @Query("select p from PatientDetails p where p.uId = ?1 and p.uApproveStatus = ?2")
    public PatientDetails findPatientDetailsByUIdAndApproveStatus(Long uId, String status);
}
