package com.example.booking.domain.repository;

import com.example.booking.domain.model.Block;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlockRepository extends JpaRepository<Block, Long> {
}
