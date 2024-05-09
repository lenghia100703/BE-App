package com.mobileapp.backend.repositories;

import com.mobileapp.backend.entities.TicketEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface TicketRepository extends JpaRepository<TicketEntity, Long> {

    @Query("SELECT u FROM TicketEntity u WHERE u.expiry < :current_time")
    List<TicketEntity> findAllTicketExpired(@Param("current_time") Date currentTime);

    @Query("SELECT u FROM TicketEntity u WHERE u.transactionId.userId.id = :id")
    List<TicketEntity> findTicketByUserId(@Param("id") Long id);
}
