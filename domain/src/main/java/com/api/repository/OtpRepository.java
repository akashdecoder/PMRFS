package com.api.repository;

import com.api.model.Otp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OtpRepository extends JpaRepository<Otp, Long> {

    @Query(value = "select * from otp", nativeQuery = true)
    public List<Otp> getOtpList();
}
