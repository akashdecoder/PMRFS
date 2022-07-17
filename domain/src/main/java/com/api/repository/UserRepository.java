package com.api.repository;

import com.api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("select u from User u where u.uEmail = ?1")
    User findByEmail(String email);

    @Query("select u from User u where u.uAadhaar = ?1")
    User findByAadhaar(Long aadhaar);

    @Query(value = "select * from users where u_category=?1", nativeQuery = true)
    List<User> findByCategory(String category);

    @Query("select u from User u where u.uCategory=?1")
    User findUserByCategory(String category);

    @Query("select u from User u where u.uPasswordResetToken=?1")
    User findUserByResetPasswordToken(String token);

    @Transactional
    @Modifying
    @Query("update User a set a.uCurrentOutstandingAmount = ?1, a.uCurrentRequestedAmount = ?2, a.uRequestReason=?3 where a.uId = ?4")
    void updateUserFunds(String uCurrentOutstandingAmount, String uCurrentRequestedAmount, String uRequestReason, Long uId);

    @Transactional
    @Modifying
    @Query("update User u set u.uCurrentOutstandingAmount = ?1 where u.uId = ?2")
    void updateApprovedUserFunds(String uCurrentOutstandingAmount, Long uId);

    @Transactional
    @Modifying
    @Query("update User a set a.uDisableVerification = ?1 where a.uId = ?2")
    void updateUserDisableVerification(String status, Long uId);

    @Transactional
    @Modifying
    @Query("update User u set u.uFirstName = ?1, u.uLastName = ?2, u.uEmail = ?3, u.uPhone = ?4, u.uAadhaar = ?5, u.uPan = ?6, u.uDob = ?7, uBankAccountNumber = ?8, uIFSCCode = ?9 where u.uId = ?10")
    void updateUser(String firstName, String lastName, String email, Long phone, Long aadhaar, String pan, String dob, Long bank, String ifsc, Long uId);

    @Transactional
    @Modifying
    @Query("update User u set u.uPasswordResetToken = ?1 where u.uEmail = ?2")
    void updateResetPasswordToken(String token, String email);

    @Transactional
    @Modifying
    @Query("update User u set u.uPassword = ?1 where u.uEmail = ?2")
    void updatePassword(String newPassword, String email);


}
