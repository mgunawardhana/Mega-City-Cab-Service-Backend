package com.megacity.backend.driver_management.repository;

import com.megacity.backend.domain.entity.Driver;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DriverRepository extends JpaRepository<Driver, Integer> {}
