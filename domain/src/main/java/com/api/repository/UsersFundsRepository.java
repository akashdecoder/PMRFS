package com.api.repository;

import com.api.model.UsersFunds;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface UsersFundsRepository extends JpaRepository<UsersFunds, Long> {

    @Query("select u from UsersFunds u where u.uId = ?1")
    public UsersFunds findByUId(Long uId);

    @Query("select u from UsersFunds u where u.uId = ?1")
    public List<UsersFunds> findAllByUId(Long uId);

    @Transactional
    @Modifying
    @Query("update UsersFunds a set a.uIsApproved = ?1 where a.uId = ?2")
    public void updateUserFunds(int status, Long uId);
}
