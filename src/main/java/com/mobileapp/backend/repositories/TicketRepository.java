package com.mobileapp.backend.repositories;

import com.mobileapp.backend.entities.TicketEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepository extends JpaRepository<TicketEntity, Long> {
}
