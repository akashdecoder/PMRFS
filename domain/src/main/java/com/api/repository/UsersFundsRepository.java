package com.api.repository;

import com.api.model.UsersFunds;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.List;

@Repository
public interface UsersFundsRepository extends JpaRepository<UsersFunds, Long> {

    @Query("select u from UsersFunds u where u.uId = ?1")
    public UsersFunds findByUId(Long uId);

    @Query("select u from UsersFunds u where u.uId = ?1")
    public List<UsersFunds> findAllByUId(Long uId);

    @Query(value = "select * from users_funds u where u.u_is_approved = ?1", nativeQuery = true)
    public List<UsersFunds> findAllByApprovedStatus(int status);

    @Query("select u from UsersFunds u where u.uIsApproved = ?1 and u.uId = ?2")
    public UsersFunds findByApprovedStatusAndUid(int status, Long uId);

    @Transactional
    @Modifying
    @Query("update UsersFunds a set a.uIsApproved = ?1, a.uApprovedTimestamp = ?2 where a.uId = ?3 and a.uIsApproved = ?4")
    public void updateUserFunds(int status, Timestamp timestamp, Long uId, int currentStatus);
}
