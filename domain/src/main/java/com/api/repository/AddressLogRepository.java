package com.api.repository;

import com.api.model.AddressLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface AddressLogRepository extends JpaRepository<AddressLog, Long> {

    @Query(value = "select * from address_log where f_id = 1? ", nativeQuery = true)
    public AddressLog getByFId(Long fId);

    @Transactional
    @Modifying
    @Query(value = "update address_log set account_address = 1? where f_id = 2?", nativeQuery = true)
    public void revokeAccountAddress(String address, Long fId);
}
